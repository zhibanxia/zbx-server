package cn.zhibanxia.zbxserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by sunchangji on 2018/9/5.
 */
@Component
@ConfigurationProperties(prefix = "zbx.oss")
public class OssConfigBean {

    /**
     * 阿里云API的内或外网域名
     */
    private String endPoint;

    /**
     * 阿里云API的密钥Access Key ID
     */
    private String accessId;

    /**
     * 阿里云API的密钥Access Key Secret
     */
    private String accessKey;

    /**
     * 阿里云API的bucket名称
     */
    private String bucketName;

    /**
     * 资源路径
     */
    private String objectPath;

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectPath() {
        return objectPath;
    }

    public void setObjectPath(String objectPath) {
        this.objectPath = objectPath;
    }
}
