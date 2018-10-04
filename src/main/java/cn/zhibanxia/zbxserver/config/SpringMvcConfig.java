package cn.zhibanxia.zbxserver.config;

import cn.zhibanxia.zbxserver.filter.LoginFilter;
import cn.zhibanxia.zbxserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Created by suyuanlong on 16/8/24.
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userService;
    @Autowired
    private ZbxConfig zbxConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginFilter(userService, zbxConfig)).addPathPatterns("/rest/**").excludePathPatterns(excludePathUri());
    }

    private String[] excludePathUri() {
        return new String[]{
                "/swagger-resources/**",
                "/v2/api-docs"
        };
    }
}
