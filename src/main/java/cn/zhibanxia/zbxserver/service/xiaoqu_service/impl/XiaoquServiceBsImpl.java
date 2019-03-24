package cn.zhibanxia.zbxserver.service.xiaoqu_service.impl;

import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.ListXiaoquServiceReq;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceSimpleVo;
import cn.zhibanxia.zbxserver.controller.xiaoqu_service.param.XiaoquServiceVo;
import cn.zhibanxia.zbxserver.dao.xiaoqu_service.XiaoquServiceDao;
import cn.zhibanxia.zbxserver.entity.XiaoquServiceInfoEntity;
import cn.zhibanxia.zbxserver.service.xiaoqu_service.XiaoquServiceBs;
import cn.zhibanxia.zbxserver.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zzy on  2019/03/23 16:11
 */
@Service
public class XiaoquServiceBsImpl implements XiaoquServiceBs {

    @Autowired
    private XiaoquServiceDao xiaoquServiceDao;

    @Override
    public List<XiaoquServiceSimpleVo> selectList(ListXiaoquServiceReq listXiaoquServiceReq) {
        return BeanUtil.copyList(xiaoquServiceDao.findList(listXiaoquServiceReq.getType(), listXiaoquServiceReq.getPage(), listXiaoquServiceReq.getSize()), XiaoquServiceSimpleVo.class);
    }

    @Override
    public XiaoquServiceVo findById(Long id) {
        return BeanUtil.copy(xiaoquServiceDao.findById(id), XiaoquServiceVo.class);
    }

    @Override
    public int deleteById(Long id) {
        return xiaoquServiceDao.delete(id);
    }

    @Override
    public Long insert(XiaoquServiceVo vo) {
        XiaoquServiceInfoEntity entity = BeanUtil.copy(vo, XiaoquServiceInfoEntity.class);
        xiaoquServiceDao.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(XiaoquServiceVo vo) {
        return xiaoquServiceDao.update(BeanUtil.copy(vo, XiaoquServiceInfoEntity.class));
    }
}
