package cn.zhibanxia.zbxserver.task;

import cn.zhibanxia.zbxserver.bo.WxRecommHuishouNotifyReqBo;
import cn.zhibanxia.zbxserver.config.WxPropConfig;
import cn.zhibanxia.zbxserver.config.ZbxConfig;
import cn.zhibanxia.zbxserver.entity.HuishouComplexRelationEntity;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.HuishouComplexRelationService;
import cn.zhibanxia.zbxserver.service.RecycleRequestService;
import cn.zhibanxia.zbxserver.service.UserService;
import cn.zhibanxia.zbxserver.service.WxApiService;
import cn.zhibanxia.zbxserver.util.DateUtil;
import cn.zhibanxia.zbxserver.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时给长时间未回收的请求推送推荐的回收人员
 * Created by zzy on  2019/03/24 16:39
 */
@Configuration
@Component
@EnableScheduling
public class RecommHuishouTask implements InitializingBean, DisposableBean {
    private static Logger logger = LoggerFactory.getLogger(RecommHuishouTask.class);

    /**
     * 重定向页面
     */
    private static final String redirectUrl = "https://wx.zhibanxia.cn/weixin/redirectYezhu?url={0}";
    /**
     * 详情页
     */
    private static final String detailUrl = "https://wx.zhibanxia.cn/#/owner/warn/{0}";

    @Autowired
    private RecycleRequestService recycleRequestService;

    @Autowired
    private ZbxConfig zbxConfig;

    @Autowired
    private WxPropConfig wxPropConfig;

    @Autowired
    private WxApiService wxApiService;

    @Autowired
    private UserService userService;

    @Autowired
    private HuishouComplexRelationService huishouComplexRelationService;

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = new ThreadPoolExecutor(4, 10, 1L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));
    }

    // 每隔10分钟检测一次
    @Scheduled(fixedRate = 1200000)
    public void doWork() {
        // 找出过去12小时内创建的，仍然处于已发布，没有回收人员确认的，处于非删除状态的，并且没有推荐过的订单
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(12);
        Date startTime = Date.from(localDateTime.toInstant(ZoneOffset.ofHours(+8)));
        List<RecycleRequestEntity> list = recycleRequestService.selectRecomm(startTime);
        if (CollectionUtils.isEmpty(list)) {
            logger.info("没有符合条件的回收订单，不需要通知");
            return;
        }
        for (RecycleRequestEntity entity : list) {
            try {
                if (entity.getDoorServStartTime() != null) {
                    LocalDateTime doorStartTime = entity.getDoorServStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    // 距离上门不足1小时，或者已经超过了上面时间
                    if (LocalDateTime.now().isBefore(doorStartTime.plusHours(1))) {

                        // 如果所在的小区没有候选回收员，放弃推送
                        List<HuishouComplexRelationEntity> huishouComplexRelationEntityList = huishouComplexRelationService.findByComplexId(entity.getComplexId());
                        if (CollectionUtils.isEmpty(huishouComplexRelationEntityList)) {
                            logger.warn("回收订单({})所在的小区({})没有匹配到回收员，放弃推送", entity.getId(), entity.getComplexId());
                            continue;
                        }

                        logger.info("准备通知回收请求:{}", entity.getId());
                        // 通知
                        sendWxNotify(entity);
                        // 修改为已经通知
                        recycleRequestService.updateRecommed(entity.getId());
                    }
                } else {
                    // 或者发布时间超过了4小时
                    LocalDateTime gmtCreate = entity.getGmtCreate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if (gmtCreate.plusHours(4).isBefore(LocalDateTime.now())) {
                        // 如果所在的小区没有候选回收员，放弃推送
                        List<HuishouComplexRelationEntity> huishouComplexRelationEntityList = huishouComplexRelationService.findByComplexId(entity.getComplexId());
                        if (CollectionUtils.isEmpty(huishouComplexRelationEntityList)) {
                            logger.warn("回收订单({})所在的小区({})没有匹配到回收员，放弃推送", entity.getId(), entity.getComplexId());
                            continue;
                        }
                        logger.info("准备通知回收请求:{}", entity.getId());
                        // 通知
                        sendWxNotify(entity);
                        // 修改为已经通知
                        recycleRequestService.updateRecommed(entity.getId());
                    }
                }
            } catch (Exception e) {
                logger.error("push message failed. entity={}", JSONObject.toJSONString(entity), e);
            }
        }
    }


    public void sendWxNotify(RecycleRequestEntity entity) {
        WxRecommHuishouNotifyReqBo wxRecommHuishouNotifyReqBo = new WxRecommHuishouNotifyReqBo();
        wxRecommHuishouNotifyReqBo.setTemplate_id(zbxConfig.getRecommHuishouYuanTemplateId());

        wxRecommHuishouNotifyReqBo.setFirst("亲爱的用户，我们留意到您发布的回收订单在较长时间没有回收员接单，我们为您推荐了附近的回收员。");
        //类型
        wxRecommHuishouNotifyReqBo.setKeyword1(getResType(entity.getResType()));
        //订单号
        wxRecommHuishouNotifyReqBo.setKeyword2(String.valueOf(entity.getId()));
        //提醒时间
        wxRecommHuishouNotifyReqBo.setKeyword3(DateUtil.getMinuteStr(new Date()));
        //详情
        wxRecommHuishouNotifyReqBo.setRemark("点击查看推荐的回收员列表。");

        try {
            wxRecommHuishouNotifyReqBo.setUrl(MessageFormat.format(redirectUrl, URLEncoder.encode(MessageFormat.format(detailUrl, String.valueOf(entity.getId())), "utf-8")));
        } catch (
                UnsupportedEncodingException e) {
            logger.warn("", e);
        }

        UserEntity userEntity = userService.findById(entity.getCreateUserId());
        if (userEntity == null) {
            logger.error("用户({})不存在！", entity.getCreateUserId());
            return;
        }
        String wxOpenId = userEntity.getWxOpenId();

        try {
            String url = MessageFormat.format(wxPropConfig.getSendTemplateUrl(), wxApiService.getAccessToken());
            wxRecommHuishouNotifyReqBo.setTouser(wxOpenId);
            HttpClientUtil.post(url, JSONObject.toJSONString(wxRecommHuishouNotifyReqBo));
        } catch (BizException e) {
            logger.warn("", e);
        }
    }

    private String getResType(Integer type) {
        if (type == null) {
            return "未知类型";
        }
        if (type == RecycleRequestEntity.RES_TYPE_ZHIBAN) {
            return "纸板";
        }
        if (type == RecycleRequestEntity.RES_TYPE_SULIAOPING) {
            return "塑料瓶";
        }
        if (type == RecycleRequestEntity.RES_TYPE_ZHIBAN_AND_SULIAOPING) {
            return "纸板和塑料瓶";
        }
        return "未知类型";
    }


    @Override
    public void destroy() {
        executorService.shutdown();
    }
}
