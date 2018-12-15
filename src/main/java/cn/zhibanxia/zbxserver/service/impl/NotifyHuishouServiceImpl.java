package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.bo.WxTemplateMsgReqBo;
import cn.zhibanxia.zbxserver.config.WxPropConfig;
import cn.zhibanxia.zbxserver.config.ZbxConfig;
import cn.zhibanxia.zbxserver.dao.UserDao;
import cn.zhibanxia.zbxserver.entity.ComplexEntity;
import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;
import cn.zhibanxia.zbxserver.entity.UserEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.ComplexService;
import cn.zhibanxia.zbxserver.service.NotifyHuishouService;
import cn.zhibanxia.zbxserver.service.WxApiService;
import cn.zhibanxia.zbxserver.util.DateUtil;
import cn.zhibanxia.zbxserver.util.HttpClientUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/11/26 23:23
 */
@Service
public class NotifyHuishouServiceImpl implements NotifyHuishouService, InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(NotifyHuishouServiceImpl.class);
    /**
     * 重定向页面
     */
    private static final String redirectUrl = "https://wx.zhibanxia.cn/weixin/redirect?url={0}";
    /**
     * 详情页
     */
    private static final String detailUrl = "https://wx.zhibanxia.cn/#/recyler/detail/?id={0}&biztype=1";

    @Autowired
    private UserDao userDao;

    @Autowired
    private WxPropConfig wxPropConfig;

    @Autowired
    private WxApiService wxApiService;

    @Autowired
    private ComplexService complexService;

    @Autowired
    private ZbxConfig zbxConfig;

    private ExecutorService executorService;

    private final Cache<String, AtomicInteger> sentTempleatWxOpenId = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.HOURS).maximumSize(1000).build();


    @Override
    public void afterPropertiesSet() throws Exception {
        executorService = new ThreadPoolExecutor(4, 10, 1L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000));
    }

    @Override
    public void notifyHuishou(RecycleRequestEntity recycleRequestEntity, List<Long> huishouUids) {
        List<UserEntity> userEntityList = userDao.selectByIds(huishouUids);
        Set<String> wxOpenIds = userEntityList.stream().map(UserEntity::getWxOpenId).collect(Collectors.toSet());
        ImmutableMap<String, AtomicInteger> map = sentTempleatWxOpenId.getAllPresent(wxOpenIds);
        if (MapUtils.isNotEmpty(map)) {
            Set<String> needRemoveSet = map.entrySet().stream().filter(e -> {
                if (e.getValue() == null) {
                    return false;
                }
                return e.getValue().get() >= 5;
            }).map(e -> e.getKey()).collect(Collectors.toSet());
            wxOpenIds.removeAll(needRemoveSet);
        }
        if (wxOpenIds.isEmpty()) {
            logger.warn("no huishou need to push.");
            return;
        }

        WxTemplateMsgReqBo wxTemplateMsgReqBo = new WxTemplateMsgReqBo();
        wxTemplateMsgReqBo.setTemplate_id(zbxConfig.getTemplateId());

        wxTemplateMsgReqBo.setFirst("你关注的小区有新的回收请求。");
        //回收小区
        wxTemplateMsgReqBo.setKeyword1(

                getComplexStr(recycleRequestEntity.getComplexId()));
        //回收类型
        wxTemplateMsgReqBo.setKeyword2(

                getResTypeStr(recycleRequestEntity.getResType()));
        //重量
        wxTemplateMsgReqBo.setKeyword3(

                getHuishouWeight(recycleRequestEntity.getResAmount()));
        //上门时间
        wxTemplateMsgReqBo.setKeyword4(

                getServTime(recycleRequestEntity.getDoorServStartTime(), recycleRequestEntity.

                        getDoorServEndTime()));
        //详情
        wxTemplateMsgReqBo.setRemark("点击查看详情。");

        try {
            wxTemplateMsgReqBo.setUrl(MessageFormat.format(redirectUrl, URLEncoder.encode(MessageFormat.format(detailUrl, String.valueOf(recycleRequestEntity.getId())), "utf-8")));
        } catch (
                UnsupportedEncodingException e) {
            logger.warn("", e);
        }

        executorService.execute(() ->
                wxOpenIds.forEach(id -> {
                    try {
                        String url = MessageFormat.format(wxPropConfig.getSendTemplateUrl(), wxApiService.getAccessToken());
                        wxTemplateMsgReqBo.setTouser(id);
                        HttpClientUtil.post(url, JSONObject.toJSONString(wxTemplateMsgReqBo));
                    } catch (BizException e) {
                        logger.warn("", e);
                    } finally {
                        try {
                            AtomicInteger atomicInteger = sentTempleatWxOpenId.get(id, () -> new AtomicInteger(0));
                            atomicInteger.incrementAndGet();
                        } catch (Exception e) {
                            logger.warn("", e);
                        }
                    }
                }));
    }

    private String getResTypeStr(Integer resType) {
        if (Objects.equals(RecycleRequestEntity.RES_TYPE_ZHIBAN, resType)) {
            return "纸板";
        } else if (Objects.equals(RecycleRequestEntity.RES_TYPE_SULIAOPING, resType)) {
            return "塑料瓶";
        } else if (Objects.equals(RecycleRequestEntity.RES_TYPE_ZHIBAN_AND_SULIAOPING, resType)) {
            return "纸板和塑料瓶";
        }
        return "其他";
    }

    private String getServTime(Date start, Date end) {
        if (start == null || end == null) {
            return "任意时间";
        }
        String startStr = DateUtil.getMinuteStr(start);
        String endStr = DateUtil.getMinuteStr(end);
        return startStr + " ~ " + endStr;
    }

    private String getComplexStr(Long complex) {
        ComplexEntity complexEntity = complexService.find(complex);
        if (complexEntity == null) {
            return "未知小区";
        }
        return complexEntity.getComplexName();
    }

    private String getHuishouWeight(Integer resAmount) {
        if (Objects.equals(1, resAmount)) {
            return "0 ~ 0.5KG";
        } else if (Objects.equals(2, resAmount)) {
            return "0.5 ~ 1KG";
        } else if (Objects.equals(3, resAmount)) {
            return "1 ~ 3KG";
        } else if (Objects.equals(4, resAmount)) {
            return "3 ~ 5KG";
        } else if (Objects.equals(5, resAmount)) {
            return "5KG以上";
        }
        return "未知数量";
    }

    @Override
    public void destroy() {
        executorService.shutdown();
    }
}
