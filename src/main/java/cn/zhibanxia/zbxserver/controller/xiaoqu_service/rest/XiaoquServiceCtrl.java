package cn.zhibanxia.zbxserver.controller.xiaoqu_service.rest;

import cn.zhibanxia.zbxserver.constant.ErrorCode;
import cn.zhibanxia.zbxserver.controller.param.Result;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.DeleteXiaoquServiceReq;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.DetailXiaoquServiceReq;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.ListXiaoquServiceReq;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceSimpleVo;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceVo;
import cn.zhibanxia.zbxserver.filter.RequestLocal;
import cn.zhibanxia.zbxserver.service.xiaoqu_service.XiaoquServiceBs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zzy on  2019/03/17 15:49
 */
@RestController
@RequestMapping("/rest/xqservice")
public class XiaoquServiceCtrl {

    private static Logger logger = LoggerFactory.getLogger(XiaoquServiceCtrl.class);
    @Autowired
    private XiaoquServiceBs xiaoquServiceBs;

    /**
     * 根据条件分页查询小区服务列表
     *
     * @return
     */
    @PostMapping("list")
    public Result<List<XiaoquServiceSimpleVo>> list(@RequestBody ListXiaoquServiceReq listXiaoquServiceReq) {
        try {
            return Result.ResultBuilder.success(doList(listXiaoquServiceReq));
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    /**
     * 管理员列表
     *
     * @return
     */
    @PostMapping("list4Admin")
    public Result<List<XiaoquServiceSimpleVo>> list4Admin(@RequestBody ListXiaoquServiceReq listXiaoquServiceReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            return Result.ResultBuilder.success(doList(listXiaoquServiceReq));
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }


    private List<XiaoquServiceSimpleVo> doList(ListXiaoquServiceReq listXiaoquServiceReq) {
        return xiaoquServiceBs.selectList(listXiaoquServiceReq);
    }

    @PostMapping("detail")
    public Result<XiaoquServiceVo> detail(@RequestBody DetailXiaoquServiceReq detailXiaoquServiceReq) {
        try {
            return Result.ResultBuilder.success(doFindById(detailXiaoquServiceReq.getId()));
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("detail4Admin")
    public Result<XiaoquServiceVo> detail4Admin(@RequestBody DetailXiaoquServiceReq detailXiaoquServiceReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            return Result.ResultBuilder.success(doFindById(detailXiaoquServiceReq.getId()));
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    private XiaoquServiceVo doFindById(Long id) {
        return xiaoquServiceBs.findById(id);
    }

    @PostMapping("delete")
    public Result<Void> delete(@RequestBody DeleteXiaoquServiceReq deleteXiaoquServiceReq) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            xiaoquServiceBs.deleteById(deleteXiaoquServiceReq.getId());
            return Result.ResultBuilder.success(null);
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("modify")
    public Result<Void> modify(@RequestBody XiaoquServiceVo xiaoquServiceVo) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            xiaoquServiceBs.update(xiaoquServiceVo);
            return Result.ResultBuilder.success(null);
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

    @PostMapping("add")
    public Result<Long> add(@RequestBody XiaoquServiceVo xiaoquServiceVo) {
        if (!RequestLocal.get().isAdmin()) {
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNSUPPORTED_OPERATION_ERROR);
        }
        try {
            return Result.ResultBuilder.success(xiaoquServiceBs.insert(xiaoquServiceVo));
        } catch (Exception e) {
            logger.error("", e);
            return Result.ResultBuilder.fail(ErrorCode.CODE_UNKONWN_ERROR);
        }
    }

}
