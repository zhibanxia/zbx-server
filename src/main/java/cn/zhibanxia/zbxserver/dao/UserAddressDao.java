package cn.zhibanxia.zbxserver.dao;

import cn.zhibanxia.zbxserver.entity.UserAddressEntity;

import java.util.List;

/**
 * Created by zzy on  2018/09/24 21:31
 */
public interface UserAddressDao {
    
    /**
     * 插入
     *
     * @param userAddressEntity
     * @return
     */
    Long insert(UserAddressEntity userAddressEntity);

    /**
     * 查询用户地址
     *
     * @param userId
     * @param bizType
     * @return
     */
    List<UserAddressEntity> findAddrs(Long userId, int bizType);
}
