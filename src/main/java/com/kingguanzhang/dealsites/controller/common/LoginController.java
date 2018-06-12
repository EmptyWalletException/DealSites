package com.kingguanzhang.dealsites.controller.common;


import com.kingguanzhang.dealsites.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    ShopService shopService;


    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String  login(@RequestParam("username") String username, HttpServletRequest request){

        //使用security无法进入此方法,需要深入了解security;
        System.out.println("进入post登录方法");
        //将shopId直接写入session,方便后续网页内容的查询;
        //request.getSession().setAttribute("username",username);
        return "common/index";
    }
}
