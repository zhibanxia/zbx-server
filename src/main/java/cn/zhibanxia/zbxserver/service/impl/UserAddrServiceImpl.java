package cn.zhibanxia.zbxserver.service.impl;

import cn.zhibanxia.zbxserver.config.ZbxConfig;
import cn.zhibanxia.zbxserver.dao.UserAddressDao;
import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.exception.BizException;
import cn.zhibanxia.zbxserver.service.UserAddrService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by zzy on  2018/10/03 16:57
 */
@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private ZbxConfig zbxConfig;

    @Override
    public boolean addOrUpdateOnlyAddr(UserAddressEntity userAddressEntity) {
        if (userAddressEntity == null) {
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
            if (userAddressEntity.getAddrDetail() != null) {
                existOne.setAddrDetail(userAddressEntity.getAddrDetail());
                needUpdate = true;
            }
            if (needUpdate) {
                return userAddressDao.update(existOne);
            }
        }
        return false;
    }

    @Override
    public boolean batchAddAddr(Long uid, List<UserAddressEntity> userAddressEntityList) throws BizException {
        if (CollectionUtils.isEmpty(userAddressEntityList)) {
            return false;
        }

        // 区分更新和插入
        Map<Long, UserAddressEntity> map = null;
        List<Long> ids = userAddressEntityList.stream().filter(e -> e.getId() != null).map(UserAddressEntity::getId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(ids)) {
            List<UserAddressEntity> tempList = userAddressDao.batchFind(ids);
            if (CollectionUtils.isNotEmpty(tempList)) {
                map = tempList.stream().collect(Collectors.toMap(UserAddressEntity::getId, e -> e));
            }
        }

        if (map == null) {
            return insertWithCheck(uid, userAddressEntityList);
        }

        final Map<Long, UserAddressEntity> internelMap = map;
        final List<UserAddressEntity> insertList = new ArrayList<>();
        userAddressEntityList.stream().forEach(e -> {
            if (e.getId() == null) {
                insertList.add(e);
                return;
            }
            UserAddressEntity entity = internelMap.get(e.getId());
            if (entity == null) {
                insertList.add(e);
            } else {
                userAddressDao.update(e);
            }
        });
        if (insertList.isEmpty()) {
            return true;
        }

        return insertWithCheck(uid, insertList);
    }

    private boolean insertWithCheck(Long uid, List<UserAddressEntity> userAddressEntityList) {
        int count = userAddressDao.countAddr(uid, UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
        if (zbxConfig.getMaxFocusAddrNum() - count < userAddressEntityList.size()) {
            List<UserAddressEntity> existAddrs = userAddressDao.findAddrs(uid, UserAddressEntity.BIZ_TYPE_HUISHOU_FOCUS);
            if (userAddressEntityList.size() < zbxConfig.getMaxFocusAddrNum()) {
                int removeCnt = userAddressEntityList.size() + count - zbxConfig.getMaxFocusAddrNum();
                List<UserAddressEntity> delAddrs = existAddrs.subList(existAddrs.size() - removeCnt, existAddrs.size());
                userAddressDao.batchDelete(delAddrs.stream().map(UserAddressEntity::getId).collect(Collectors.toList()));
            } else {
                userAddressEntityList = userAddressEntityList.subList(0, zbxConfig.getMaxFocusAddrNum());
                userAddressDao.batchDelete(existAddrs.stream().map(UserAddressEntity::getId).collect(Collectors.toList()));
            }
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
