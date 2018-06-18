package com.kingguanzhang.dealsites.controller.common;

import com.kingguanzhang.dealsites.pojo.LocalAuth;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 跳转到首页
     * @param request
     * @return
     */

    @RequestMapping("/index")
    public String showIndex(HttpServletRequest request){
        //使用security在session中取出用户信息;
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (null != securityContextImpl) {
            String username = securityContextImpl.getAuthentication().getName();
            if (null != username) {
                System.out.println("shopManagement:" + username);
            } else {
                System.out.println("没有用户名");
            }
            //通过用户账号名得到用户
            List<LocalAuth> localAuthList = localAuthService.getLocalAuthByLoginUsername(username);
            PersonInfo personInfo = localAuthList.get(0).getPersonInfo();
            //System.out.println(personInfo.getUserId());
            //将用户信息写入session
            request.getSession().setAttribute("personInfo", personInfo);
        }
        return "common/index";
    }
}
