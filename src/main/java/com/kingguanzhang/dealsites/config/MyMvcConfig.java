package com.kingguanzhang.dealsites.config;

import com.kingguanzhang.dealsites.component.LoginHandlerInterceptor;
import com.kingguanzhang.dealsites.component.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 扩展springMvc
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    /*注册视图解析器,所有在localhost:8080后面加上的路径会被springMvc以返回的字符串解析
    并在templates路径下寻找对应的html*/
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/login.html").setViewName("login");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/","/index","/login","/index.html","/login.html","/user/login","**/**/*.css","**/**/*.js","**/**/*.jpg");

    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
}
