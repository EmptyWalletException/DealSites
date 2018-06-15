package com.kingguanzhang.dealsites.controller.common;

import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Shop;
import com.kingguanzhang.dealsites.service.FavoriteShopService;
import com.kingguanzhang.dealsites.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/common")
@Controller
public class CommonShopController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private FavoriteShopService favoriteShopService;

    /**
     * 跳转到店铺详情页;用get方式;
     * @return
     */
    @RequestMapping(value = "/shop/shopDetailsPage/{shopId}",method = RequestMethod.GET)
    public String showShopDetails(@PathVariable("shopId") Integer shopId, HttpServletRequest request){
        request.getSession().setAttribute("shopId",shopId);
        return "common/shopDetails";
    }

    /**
     * ajax 获取店铺详情;
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/shop/get",method = RequestMethod.GET)//这里的shopId需要修改成通过session中用户id来查询绑定的商店id
    @ResponseBody
    public Msg getShop(HttpServletRequest request){

        Integer shopId = (Integer) request.getSession().getAttribute("shopId");
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        //将shopId直接写入session,方便后续网页内容的查询;
        if (null == shopId){
            return Msg.fail().setMsg("获取商店信息失败!");
        }
        Shop shop = shopService.getShop(shopId);
        Msg msg =Msg.success().add("shop",shop);

        //查询当前用户的收藏店铺,用于判断页面是否显示收藏店铺按钮
        if (null != personInfo) {
            Integer integer = favoriteShopService.getFavoriteShop(personInfo.getUserId(), shopId);
            if (0 < integer){
                return msg.add("isFavorite",true);
            }
        }
        return msg.add("isFavorite",false);
    }
}
