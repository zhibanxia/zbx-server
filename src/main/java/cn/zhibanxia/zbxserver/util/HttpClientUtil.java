package cn.zhibanxia.zbxserver.util;

import cn.zhibanxia.zbxserver.constant.ErrorCodeConstant;
import cn.zhibanxia.zbxserver.exception.BizException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by zzy on  2018/10/02 11:30
 */
public class HttpClientUtil {

    private static Logger httpRequestlogger = LoggerFactory.getLogger("httpRequest");
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setConnectTimeout(1000).setSocketTimeout(2000).build();
    private static CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) throws BizException {
        HttpGet httpGet = new HttpGet(url);
        String responseBody = null;
        boolean success = false;
        try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet)
        ) {
            responseBody = EntityUtils.toString(closeableHttpResponse.getEntity(), "utf-8");
            success = true;
            return responseBody;
        } catch (Exception e) {
            logger.warn("http get fail, url={}", url, e);
            throw new BizException(ErrorCodeConstant.CODE_HTTP_GET_ERROR, e.getMessage(), e);
        } finally {
            // 日志格式：时间|请求方式|url|参数|结果|response
            httpRequestlogger.info("GET|{}||{}|{}", url, success, responseBody);
        }
    }
}
