package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.dao.UserAddressDao;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by zzy on  2018/10/03 16:57
 */
@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Autowired
    private UserAddressDao userAddressDao;

    @Override
    public boolean addOrUpdateOnlyAddr(UserAddressEntity userAddressEntity) {
        if (userAddressEntity == null || userAddressEntity.getId() == null) {
            throw new IllegalArgumentException("null args");
        }
        if (userAddressEntity.getBizType() == null || Objects.equals(UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS, userAddressEntity.getBizType())) {
            return false;
        }
        List<UserAddressEntity> userAddressEntityList = userAddressDao.findAddrs(userAddressEntity.getUserId(), userAddressEntity.getBizType());
        if (userAddressEntityList == null || userAddressEntityList.isEmpty()) {
            return userAddressDao.insert(userAddressEntity) != null;
        } else {
            UserAddressEntity existOne = userAddressEntityList.get(0);
            boolean needUpdate = false;
            if (userAddressEntity.getProvinceId() != null) {
                existOne.setProvinceId(userAddressEntity.getProvinceId());
                needUpdate = true;
            }
            if (userAddressEntity.getCityId() != null) {
                existOne.setCityId(userAddressEntity.getCityId());
                needUpdate = true;
            }
            if (userAddressEntity.getAreaId() != null) {
                existOne.setAreaId(userAddressEntity.getAreaId());
                needUpdate = true;
            }
            if (userAddressEntity.getSubdistrictId() != null) {
                existOne.setSubdistrictId(userAddressEntity.getSubdistrictId());
                needUpdate = true;
            }
            if (userAddressEntity.getAreaDetail() != null) {
                existOne.setAreaDetail(userAddressEntity.getAreaDetail());
                needUpdate = true;
            }
            if (needUpdate) {
                return userAddressDao.update(existOne);
            }
        }
        return false;
    }

    @Override
    public boolean batchAddAddr(List<UserAddressEntity> userAddressEntityList) {
        if (CollectionUtils.isEmpty(userAddressEntityList)) {
            return false;
        }
        return userAddressDao.batchInsert(userAddressEntityList);
    }

    @Override
    public UserAddressEntity findOnlyAddr(Long userId, int bizType) {
        if (userId == null || (!Objects.equals(UserAddressEntity.BIZ_TYPE_YEZHU, bizType) && !Objects.equals(UserAddressEntity.BIZ_TYPE_HUISHOU, bizType))) {
            return null;
        }
        List<UserAddressEntity> userAddressEntityList = userAddressDao.findAddrs(userId, bizType);
        if (CollectionUtils.isEmpty(userAddressEntityList)) {
            return null;
        }
        return userAddressEntityList.get(0);
    }

    @Override
    public List<UserAddressEntity> findFocusAddrs(Long userId) {
        return userAddressDao.findAddrs(userId, UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
    }
}
