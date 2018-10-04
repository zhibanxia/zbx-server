package cn.zhibanxia.zbxserver.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zzy on  2018/10/04 21:09
 */
@Configuration
public class ServiceExtBeanConfig {
    @Autowired
    private OssConfigBean ossConfigBean;

    @Bean(destroyMethod = "shutdown")
    public OSS getOSSClient() {
        return new OSSClientBuilder().build(ossConfigBean.getEndPoint(), ossConfigBean.getAccessId(), ossConfigBean.getAccessKey());
    }

}
