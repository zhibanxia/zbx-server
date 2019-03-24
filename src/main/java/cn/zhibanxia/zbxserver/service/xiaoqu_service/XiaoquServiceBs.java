package cn.zhibanxia.zbxserver.service.xiaoqu_service;

import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.ListXiaoquServiceReq;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceSimpleVo;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceVo;

import java.util.List;

/**
 * Created by zzy on  2019/03/23 15:57
 */
public interface XiaoquServiceBs {
    /**
     * 根据类型分页查询
     *
     * @param listXiaoquServiceReq
     * @return
     */
    List<XiaoquServiceSimpleVo> selectList(ListXiaoquServiceReq listXiaoquServiceReq);

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    XiaoquServiceVo findById(Long id);

    /**
     * 根据id删除单个
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 新增服务
     *
     * @param vo
     * @return
     */
    Long insert(XiaoquServiceVo vo);


    /**
     * 根据服务
     *
     * @param vo
     * @return
     */
    int update(XiaoquServiceVo vo);
}
