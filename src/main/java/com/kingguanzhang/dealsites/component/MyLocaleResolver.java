package com.kingguanzhang.dealsites.component;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String parameter = httpServletRequest.getParameter("language");
        //先将系统默认的locale赋值给我们定义的locale,如果下面的if代码没执行,则会返回系统默认的设置;
        Locale locale = Locale.getDefault();
        if(!org.springframework.util.StringUtils.isEmpty(parameter)){
            String[] str = parameter.split("_");
            //Locale的构造器中第一个参数为语言代码,即en_Us中的en,第二个对象为国家代码US;
            locale = new Locale(str[0],str[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}