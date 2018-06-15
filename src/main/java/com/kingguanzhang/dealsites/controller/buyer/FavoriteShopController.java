package com.kingguanzhang.dealsites.controller.buyer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.FavoriteShop;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.service.FavoriteShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/buyer")
@Controller
public class FavoriteShopController {
    
    @Autowired
    private FavoriteShopService favoriteShopService;

    /**
     * 收藏店铺
     * @param shopId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteShop/add",method = RequestMethod.POST)
    @ResponseBody
    private Msg addFavoriteShop(@RequestParam("shopId")Integer shopId, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        //先查询一下防止数据库表中出现重复记录;
        Integer integer = favoriteShopService.getFavoriteShop(personInfo.getUserId(),shopId);
        if (0 < integer){
            return Msg.success().setMsg("此店铺已经在您的收藏夹中了");
        }
        int i = 0;
        try{
             i = favoriteShopService.addFavoriteShop(shopId,personInfo.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().setMsg("收藏失败");
        }
        if (0 >= i){
            return Msg.fail().setMsg("收藏失败,请重新登录后再尝试");
        }
        return Msg.success().setMsg("收藏成功");
    }

    /**
     * 取消收藏店铺
     * @param shopId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteShop/delete",method = RequestMethod.POST)
    @ResponseBody
    private Msg removeFavoriteShop(@RequestParam("shopId")Integer shopId, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        int i = 0;
        try{
             i = favoriteShopService.removeFavoriteShop(shopId,personInfo.getUserId());
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail().setMsg("取消收藏失败");
        }
        if (0 >= i){
            return Msg.fail().setMsg("取消收藏失败,请重新登录后再尝试");
        }
        return Msg.success().setMsg("取消收藏成功");
    }

    /**
     * 获取收藏的店铺列表
     * @param pn
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteShop/all",method = RequestMethod.POST)
    @ResponseBody
    private Msg getFavoriteShopList(@RequestParam("pn")Integer pn, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        PageHelper.startPage(pn,8);
        List<FavoriteShop> productList = favoriteShopService.getFavoriteShopList(personInfo.getUserId());
        PageInfo pageInfo = new PageInfo(productList,5);
        return Msg.success().setMsg("获取收藏的商品成功").add("pageInfo",pageInfo);
    }
}
