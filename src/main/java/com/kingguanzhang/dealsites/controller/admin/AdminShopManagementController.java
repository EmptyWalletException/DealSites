package com.kingguanzhang.dealsites.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Shop;
import com.kingguanzhang.dealsites.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class AdminShopManagementController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/ajax/admin/getAllShop",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllShop(@RequestParam(value = "pn",defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,8);
        List<Shop> shopList =shopService.getAllShop();
        PageInfo pageInfo = new PageInfo(shopList,5);
        return Msg.success().setMsg("获取店铺列表成功").add("pageInfo",pageInfo);
    }

    /**
     * 跳转到店铺详情页
     * @return
     */
    @RequestMapping("/admin/showShopDetails/{shopId}")
    public String adminShowShopDetails(){
        return "admin/shopShop";
    }

    /**
     * 跳转到店铺编辑页
     * @return
     */
    @RequestMapping("/admin/showEditShop/{shopId}")
    public String adminShowEditShop(@PathVariable("shopId") Integer shopId, HttpServletRequest request){
      //将店铺Id写入session,在之后店铺编辑的页面加载时会从session中取出Id
        request.getSession().setAttribute("shopId",shopId);
        return "admin/editShop";
    }

}
