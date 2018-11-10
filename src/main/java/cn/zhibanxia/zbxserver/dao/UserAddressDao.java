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

    /**
     * 查询用户地址数量
     *
     * @param userId
     * @param bizType
     * @return
     */
    int countAddr(Long userId, int bizType);

    /**
     * 批量插入
     *
     * @param userAddressEntityList
     * @return
     */
    boolean batchInsert(List<UserAddressEntity> userAddressEntityList);

    /**
     * @param userAddressEntity
     * @return
     */
    boolean update(UserAddressEntity userAddressEntity);

    /**
     * 批量查询
     *
     * @param ids
     * @return
     */
    List<UserAddressEntity> batchFind(List<Long> ids);


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean batchDelete(List<Long> ids);
}
