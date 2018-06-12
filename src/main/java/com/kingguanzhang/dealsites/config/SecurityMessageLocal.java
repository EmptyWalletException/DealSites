package com.kingguanzhang.dealsites.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class SecurityMessageLocal {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.CHINA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages_zh_CN");
        // 此处是自定义的security错误信息db文件
       // messageSource.addBasenames("classpath:security/messages_zh_CN");

        return messageSource;
    }
}

