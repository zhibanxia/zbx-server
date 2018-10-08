package cn.zhibanxia.zbxserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zzy on  2018/10/08 22:53
 */
public class LoggerUtil {

    /**
     * http 请求记录
     *
     * @return
     */
    public static Logger getHttpRequestLogger() {
        return LoggerFactory.getLogger("httpRequest");
    }

    /**
     * 管理员访问记录
     *
     * @return
     */
    public static Logger getAdminAccessLogger() {
        return LoggerFactory.getLogger("adminAccess");
    }
}
