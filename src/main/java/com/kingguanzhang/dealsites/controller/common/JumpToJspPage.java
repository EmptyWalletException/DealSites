package com.kingguanzhang.dealsites.controller.common;


import com.kingguanzhang.dealsites.controller.seller.AreaController;
import com.kingguanzhang.dealsites.pojo.Area;
import com.kingguanzhang.dealsites.pojo.LocalAuth;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Shop;
import com.kingguanzhang.dealsites.service.AreaService;
import com.kingguanzhang.dealsites.service.LocalAuthService;
import com.kingguanzhang.dealsites.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class JumpToJspPage {

    @Autowired
    private LocalAuthService localAuthService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;

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


    /**
     * 跳转到登录页
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String showLogin(){
        return "common/login";
    }

    /**
     * 跳转到注册页面
     * @return
     */
    @RequestMapping("/register/registerUser")
    public String showRegisterUser(){
        return "common/registerUser";
    }

    /**
     * 跳转到店铺详情页;用get方式;
     * @return
     */
    @RequestMapping("/common/shopDetails/{shopId}")
    public String showShopDetails(@PathVariable("shopId") Integer shopId, HttpServletRequest request){
        request.getSession().setAttribute("shopId",shopId);
        return "common/shopDetails";
    }

    //以下是卖家后台管理页面跳转的方法;
    @RequestMapping("/register")
    public String showRegister(){
        return "seller/registerShop";
    }

    @RequestMapping("/editShop")
    public String showEditShop(HttpServletRequest request, Model model){
        Integer shopId = (Integer) request.getSession().getAttribute("shopId");
        Shop shop = shopService.getShop(shopId);
        List<Area> areaList = areaService.getAreas();
        model.addAttribute("shop",shop);
        model.addAttribute("areaList",areaList);
        return "seller/editShop";
    }

    @RequestMapping("/productManagement")
    public String showProductManagement(){
        return "seller/productManagement1";
    }

    @RequestMapping("/productManagement2")
    public String showProductManagement2(){
        return "seller/productManagement2";
    }

    @RequestMapping("/addProduct")
    public String addProduct(){
        return "seller/addProduct";
    }

    /**
     * 跳转到卖家后台管理首页的方法,需要在此时就在session中写入shopId,后期会改到登录时写入;
     * @param request
     * @return
     */
    @RequestMapping("/shopManagement")
    public String showShopManagement( HttpServletRequest request){
       //在首页时就已经在session中写入了personInfo;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        //通过用户Id得到店铺;
        Shop shop = shopService.getShopByUserId(personInfo.getUserId());
        request.getSession().setAttribute("shopId",shop.getShopId());
        return "seller/shopManagement";
    }

    //以下是管理员的页面跳转
    @RequestMapping("/admin/shopManagement")
    public String showAdminShopManagement(){
        return "admin/shopManagement";
    }

    @RequestMapping("/admin/productCategoryManagement")
    public String showAdminProductCategory(){
        return "admin/productCategoryManagement";
    }

    @RequestMapping("admin/editShop")
    public String showAdminEditShop(){
        return "admin/editShop";
    }



}
