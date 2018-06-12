package com.kingguanzhang.dealsites.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//@Configuration//暂时用不到Druid的后台管理
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return  new DruidDataSource();
    }


    /**
     * 配置Druid的监控
     * 配置一个管理后台的Servlet
     * @return
     */
    // @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","admin");
        initParams.put("loginPassword","123456");
        initParams.put("allow","");

        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 配置一个web监控的拦截器
     * @return
     */
    // @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();

        //设置一个过滤器
        bean.setFilter(new WebStatFilter());

        //设置初始化参数
        Map<String,String> initParams = new HashMap<>();
        //在初始化参数里设置不拦截的url
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);

        //设置要拦截的url
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
