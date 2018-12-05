package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.entity.RecycleRequestEntity;

import java.util.List;

/**
 * Created by zzy on  2018/11/26 23:05
 */
public interface NotifyHuishouService {

    /**
     * @param recycleRequestEntity
     * @param huishouUids
     */
    void notifyHuishou(RecycleRequestEntity recycleRequestEntity, List<Long> huishouUids);
}
