package com.kingguanzhang.dealsites.controller.seller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Shop;
import com.kingguanzhang.dealsites.service.FavoriteShopService;
import com.kingguanzhang.dealsites.service.ShopService;
import com.kingguanzhang.dealsites.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private FavoriteShopService favoriteShopService;


    @RequestMapping(value = "/getShop",method = RequestMethod.GET)//这里的shopId需要修改成通过session中用户id来查询绑定的商店id
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

    /**
     * 更新店铺;
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateShop",method = RequestMethod.POST)
    @ResponseBody
    public Msg updateShop(HttpServletRequest request) {

        //从前端传来的请求中获取键为shopStr的值;
        String shopStr = RequestUtil.parserString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;

        try {
            //将前端传来的商店信息转换为shop实体类;
            System.out.print("shopStr的值是:" + shopStr);
            shop = objectMapper.readValue(shopStr, Shop.class);

            /*这里需要注意,shopId需要小心处理,建议页面上一步查询时就写入session,防止用户在前端修改id导致处理了错误的数据;*/
            int shopId = (int)request.getSession().getAttribute("shopId");//从session中取出商店id
            shop.setShopId(shopId);

        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().setMsg("店铺信息不能正确解析");
        }

        //从request中解析出上传的文件图片;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            return Msg.fail().setMsg("图片文件解析失败");
        }

        if (null != shopImg){
            //使用文件.getOriginalFilename可以获取带后缀.jpg的全名;或者文件.getItem.getName也可以获取带后缀的文件名;否则只能取到不带后缀的文件名;
            try {
                shopService.updateShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                return Msg.fail().setMsg("更新店铺失败");
            }
        }else {
            shopService.updateShopWithoutImg(shop);
        }


        shop =shopService.getShop(shop.getShopId());
        return Msg.success().setMsg("更新店铺成功").add("shop",shop);

    }


    /**
     * 注册店铺的方法;
     * @param request
     * @return
     */
    @RequestMapping(value = "/addShop",method = RequestMethod.POST)
    @ResponseBody
    private Msg registerShop(HttpServletRequest request) {

        //从前端传来的请求中获取键为shopStr的值;
        String shopStr = RequestUtil.parserString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;

        try {
            //将前端传来的商店信息转换为shop实体类;
            System.out.print("shopStr的值是:" + shopStr);
            shop = objectMapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().setMsg("设置店铺信息失败!");
        }

        //从request中解析出上传的文件图片;
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            return Msg.fail().setMsg("解析图片出错了!");
        }

        //注册店铺,尽可能的减少从前端获取的值;
        if (null != shop && null != shopImg) {

      //      shop.setOwnerId(1);//这个店铺主人id本应该是从前端传过来的session中获取,但是暂时还不会;


            try {
               //使用文件.getOriginalFilename可以获取带后缀.jpg的全名;或者文件.getItem.getName也可以获取带后缀的文件名;否则只能取到不带后缀的文件名;
                shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
            } catch (IOException e) {
                System.out.print("异常信息"+e.getMessage());
                return Msg.fail().setMsg("图片保存出错了");
            }
            //返回注册店铺的最终结果;
            return Msg.success().setMsg("注册成功");
        } else {
            return Msg.fail().setMsg("注册失败,店铺信息不完整!");
        }
    }


}
