package com.kingguanzhang.dealsites.controller.buyer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.service.FavoriteProductService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/buyer")
@Controller
public class FavoriteProductController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    /**
     * 跳转到收藏夹页面;
     * @return
     */
    @RequestMapping(value = "/favorite/favoritePage",method = RequestMethod.GET)
    private String showFavoritePage(){
        return "/buyer/favorite";
    }

    /**
     * 收藏商品
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteProduct/add",method = RequestMethod.POST)
    @ResponseBody
    private Msg addFavoriteProduct(@RequestParam("productId")Integer productId, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        //添加之前查一下数据库此商品是否已经被此用户收藏了,防止出现重复收藏的bug
        Integer integer = favoriteProductService.getFavoriteProduct(personInfo.getUserId(),productId);
        if (0 >= integer){
            int i = 0;
            try{
                 i = favoriteProductService.addFavoriteProduct(productId,personInfo.getUserId());
            }catch (Exception e){
                e.printStackTrace();
                return Msg.fail().setMsg("收藏失败");
            }
            if (0 >= i){
                return Msg.fail().setMsg("收藏失败,请重新登录后再尝试");
            }
            return Msg.success().setMsg("收藏成功");
        }
        return Msg.success().setMsg("此商品已经在您的收藏夹中了");

    }

    /**
     * 取消收藏商品
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteProduct/delete",method = RequestMethod.POST)
    @ResponseBody
    private Msg removeFavoriteProduct(@RequestParam("productId")Integer productId, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        int i = 0;
        try{
             i = favoriteProductService.removeFavoriteProduct(productId,personInfo.getUserId());
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
     * 获取收藏的商品列表
     * @param pn
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/favoriteProduct/all",method = RequestMethod.POST)
    @ResponseBody
    private Msg getFavoriteProductList(@RequestParam("pn")Integer pn, HttpServletRequest request){
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        PageHelper.startPage(pn,8);
        List<FavoriteProduct> productList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
        PageInfo pageInfo = new PageInfo(productList,5);
        return Msg.success().setMsg("获取收藏的商品成功").add("pageInfo",pageInfo);
    }
}
