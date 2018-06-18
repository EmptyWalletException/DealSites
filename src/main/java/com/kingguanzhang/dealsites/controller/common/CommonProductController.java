package com.kingguanzhang.dealsites.controller.common;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Product;
import com.kingguanzhang.dealsites.service.FavoriteProductService;
import com.kingguanzhang.dealsites.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/common")
@Controller
public class CommonProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FavoriteProductService favoriteProductService;

    /**
     *通过分类名和店铺Id获取分类下所有在售商品;
     * @param categoryId
     * @param pn
     * @return
     */
    @RequestMapping(value = "/ajax/product/allOnSalesByShopIdAndCategoryId",method = RequestMethod.POST)
    @ResponseBody
    public Msg getOnSellProductListByShopIdAndCategoryId(@RequestParam("categoryId") Integer categoryId, @RequestParam("shopId")Integer shopId,@RequestParam(value = "pn",defaultValue = "1") Integer pn,HttpServletRequest request) {
        PageHelper.startPage(pn,8);
        List<Product> productList = productService.getOnSellProductListByCategoryIdAndShopId(categoryId,shopId);
        PageInfo pageInfo = new PageInfo(productList,5);
        Msg msg =Msg.success().setMsg("获取商品集合成功").add("pageInfo", pageInfo);
        //查询出用户收藏的商品的Id,为了在首页的商品卡牌中判断是显示收藏还是取消收藏按钮;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null != personInfo){
            List<FavoriteProduct> favoriteProductList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
            msg.add("favoriteProductList",favoriteProductList);
        }

        return msg;
    }


    /**
     * 查询店铺内所有上架中的商品列表;
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/product/allOnSalesByShopId",method = RequestMethod.POST)
    @ResponseBody
    public Msg getShelveProductList(@RequestParam(value = "pn",defaultValue = "1")Integer pn, @RequestParam("shopId")Integer shopId, HttpServletRequest request) {
        //使用分页插件官方推荐的第二种方式开启分页查询;
        PageHelper.startPage(pn, 8);
        //然后紧跟的查询就是分页查询;
        List<Product> productList = productService.getShelveProductList(shopId);
        //查询之后使用PageInfo来包装,方便在页面视图中处理页码,下面用的构造器第二个参数是页面底部可供点击的连续页码数;
        PageInfo pageInfo = new PageInfo(productList,5);
        Msg msg =Msg.success().setMsg("获取商品集合成功").add("pageInfo", pageInfo);
        //查询出用户收藏的商品的Id,为了在首页的商品卡牌中判断是显示收藏还是取消收藏按钮;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null != personInfo){
            List<FavoriteProduct> favoriteProductList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
            msg.add("favoriteProductList",favoriteProductList);
        }

        return msg;
    }


    /**
     * 在首页查询所有店铺内所有上架中的商品列表;
     * @param
     * @return
     */


    @RequestMapping(value = "/ajax/product/allOnSales",method = RequestMethod.POST)
    @ResponseBody
    public Msg getAllOnSalesProductList(@RequestParam(value = "pn",defaultValue = "1")Integer pn, HttpServletRequest request) {
        //使用分页插件官方推荐的第二种方式开启分页查询;
        PageHelper.startPage(pn, 16);
        //然后紧跟的查询就是分页查询;
        List<Product> productList =productService.getAllOnSellProductList();
        //查询之后使用PageInfo来包装,方便在页面视图中处理页码,下面用的构造器第二个参数是页面底部可供点击的连续页码数;
        PageInfo pageInfo = new PageInfo(productList,5);
        Msg msg =Msg.success().setMsg("获取商品集合成功").add("pageInfo", pageInfo);

        //查询出用户收藏的商品的Id,为了在首页的商品卡牌中判断是显示收藏还是取消收藏按钮;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null != personInfo){
            List<FavoriteProduct> favoriteProductList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
            msg.add("favoriteProductList",favoriteProductList);
        }

        return msg;
    }

    /**
     *通过分类名获取分类下所有在售的商品;
     * @param categoryId
     * @param pn
     * @return
     */
    @RequestMapping(value = "/ajax/product/allOnSalesByCategoryId",method = RequestMethod.POST)
    @ResponseBody
    public Msg getOnSellProductListByCategoryId(@RequestParam("categoryId") Integer categoryId, @RequestParam(value = "pn",defaultValue = "1") Integer pn,HttpServletRequest request) {
        PageHelper.startPage(pn,8);
        List<Product> productList = productService.getOnSellProductListByCategoryId(categoryId);
        PageInfo pageInfo = new PageInfo(productList,5);
        Msg msg =Msg.success().setMsg("获取商品集合成功").add("pageInfo", pageInfo);

        //查询出用户收藏的商品的Id,为了在首页的商品卡牌中判断是显示收藏还是取消收藏按钮;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null != personInfo){
            List<FavoriteProduct> favoriteProductList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
            msg.add("favoriteProductList",favoriteProductList);
        }

        return msg;
    }
}
