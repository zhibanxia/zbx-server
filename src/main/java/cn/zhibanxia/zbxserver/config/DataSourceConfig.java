package cn.zhibanxia.zbxserver.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@Component
public class DataSourceConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * <bean id="ckvtableSqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
     * <constructor-arg>
     * <bean class="org.mybatis.spring.SqlSessionFactoryBean">
     * <property name="dataSource" ref="ckvtableDataSource"/>
     * <property name="mapperLocations">
     * <array>
     * <value>classpath*:mybatis/ckvtable/*.xml</value>
     * </array>
     * </property>
     * <property name="configLocation" value="classpath:mybatis/sqlMapConfig.xml"/>
     * </bean>
     * </constructor-arg>
     * </bean>
     * <p>
     * <!-- 配置Spring的事务管理器 -->
     * <bean id="ckvtableTransactionManager"
     * class="cn.com.duiba.wolf.spring.datasource.AutoRoutingDataSourceTransactionManager">
     * <property name="dataSource" ref="ckvtableDataSource"/>
     * <qualifier value="ckvtable"/>
     * </bean>
     */
    @Bean
    public SqlSessionTemplate zhibanxiaSqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(zhibanxiaSqlSessionFactoryBean().getObject());
        return sqlSessionTemplate;
    }

    @Bean
    public SqlSessionFactoryBean zhibanxiaSqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            sqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath*:mybatis/zhibanxia/*.xml"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        sqlSessionFactoryBean.setConfigLocation(pathMatchingResourcePatternResolver.getResource("classpath:mybatis/sqlMapConfig.xml"));
        return sqlSessionFactoryBean;
    }

    @Bean
    @Qualifier("zhibanxia")
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }
}
