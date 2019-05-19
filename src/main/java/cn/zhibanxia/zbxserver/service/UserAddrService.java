package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.UserAddressEntity;
import cn.zhibanxia.zbxserver.exception.BizException;

import java.util.List;
import java.util.Set;

/**
 * Created by zzy on  2018/10/03 16:54
 */
public interface UserAddrService {
    /**
     * 新增或者更新用户住处，是唯一的
     *
     * @param userAddressEntity
     * @return
     */
    boolean addOrUpdateOnlyAddr(UserAddressEntity userAddressEntity);

    /**
     * 批量添加地址
     *
     * @param userAddressEntityList
     * @return
     */
    boolean batchAddAddr(Long uid, List<UserAddressEntity> userAddressEntityList) throws BizException;

    /**
     * 批量更新地址
     *
     * @param uid
     * @param exitsIds
     * @param userAddressEntityList
     * @return
     */
    boolean batchUpdateFocusAddr(Long uid, Set<Long> exitsIds, List<UserAddressEntity> userAddressEntityList) throws BizException;


    /**
     * 获取默认住址
     *
     * @param userId
     * @param bizType
     * @return
     */
    UserAddressEntity findOnlyAddr(Long userId, int bizType);


    /**
     * 查询用户地址
     *
     * @param userId
     * @return
     */
    List<UserAddressEntity> findFocusAddrs(Long userId);


    /**
     * 根据主键id查询
     *
     * @param id 主键id
     * @return
     */
    UserAddressEntity find(Long id);

    /**
     * 更新单条用户地址信息
     *
     * @param userAddressEntity id不能为空
     * @return
     * @throws
     */
    boolean updateUserAddr(UserAddressEntity userAddressEntity) throws BizException;

    /**
     * 删除关注的小区
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

}
