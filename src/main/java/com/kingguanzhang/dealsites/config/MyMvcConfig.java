package com.kingguanzhang.dealsites.config;

import com.kingguanzhang.dealsites.component.MyLocaleResolver;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 扩展springMvc
 */
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
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



   // @Override
 //   public void addInterceptors(InterceptorRegistry registry) {
 //       registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
 //               .excludePathPatterns("/","/index","/login","/index.html","/login.html","/user/login","**/**/*.css","**/**/*.js","**/**/*.jpg");

 //   }

    //配置uri资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /*
         * 说明：增加虚拟路径(经过本人测试：在此处配置的虚拟路径，用springboot内置的tomcat时有效，
         * 用外部的tomcat也有效;所以用到外部的tomcat时不需在tomcat/config下的相应文件配置虚拟路径了,阿里云linux也没问题)
         */
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/projectdev/images/upload/");
        //阿里云(映射路径去除盘符)
        //registry.addResourceHandler("/ueditor/image/**").addResourceLocations("/upload/image/");
        //registry.addResourceHandler("/ueditor/video/**").addResourceLocations("/upload/video/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

/*    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setMaxInMemorySize(409600);
        resolver.setMaxUploadSize(50*1024*1024);//上传文件大小 50M 50*1024*1024
        return resolver;
    }*/

}
