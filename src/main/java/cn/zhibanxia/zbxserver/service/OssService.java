package cn.zhibanxia.zbxserver.service;

import cn.zhibanxia.zbxserver.exception.BizException;

/**
 * Created by zzy on  2018/10/04 21:06
 */
public interface OssService {
    /**
     * 上传文件
     *
     * @param data
     * @param fileName
     * @return
     */
    String uploadFile(byte[] data, String fileName) throws BizException;
}
