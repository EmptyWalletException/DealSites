package com.kingguanzhang.dealsites.controller.buyer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Cart;
import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.service.CartService;
import com.kingguanzhang.dealsites.service.FavoriteProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/buyer")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private FavoriteProductService favoriteProductService;

    @RequestMapping(value = "/cart",method = RequestMethod.GET)
    private String showCartPage(){
        return "/buyer/cart";
    }

    /**
     * 查询购物车里所有商品
     * @param pn
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/getCartList",method = RequestMethod.POST)
    @ResponseBody
    private Msg getCartList(@RequestParam(value = "pn",defaultValue = "1") Integer pn, HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null == personInfo){
            return Msg.fail().setMsg("查询失败,请重新登录");
        }
        PageHelper.startPage(pn,16);
        List<Cart> cartList = cartService.getCartList(personInfo.getUserId());
        if (0 == cartList.size()){
            return Msg.fail().setMsg("购物车为空");
        }

        PageInfo pageInfo = new PageInfo(cartList,5);
        Msg msg =Msg.success().setMsg("获取商品集合成功").add("pageInfo", pageInfo);

        //查询出用户收藏的商品的Id,为了在首页的商品卡牌中判断是显示收藏还是取消收藏按钮;
        if (null != personInfo){
            List<FavoriteProduct> favoriteProductList = favoriteProductService.getFavoriteProductList(personInfo.getUserId());
            msg.add("favoriteProductList",favoriteProductList);
        }
        return msg;
    }

    /**
     * 从购物车中移除单个商品
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/removeProductFromCart",method = RequestMethod.POST)
    @ResponseBody
    private Msg removeProductFromCart(@RequestParam("productId")Integer productId, HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        Integer i = cartService.removeProductFromCart(personInfo.getUserId(),productId);
        if (0 == i){
            return Msg.fail().setMsg("移除商品失败,请重新登录后再尝试!");
        }
        return Msg.success().setMsg("移除商品成功");
    }


    /**
     * 从购物车中移除批量商品
     * @param productIds
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/removeProductFromCartBatch",method = RequestMethod.POST)
    @ResponseBody
    private Msg removeProductFromCartBatch(@RequestParam("productIds")String productIds, HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        String[] productIdArray = productIds.split(",");
        List<Integer> productIdList = new ArrayList<>();
        for (String id:productIdArray){
            productIdList.add(Integer.parseInt(id));
        }
        Integer i = cartService.removeProductFromCartBatch(personInfo.getUserId(),productIdList);
        if (0 >= i){
            return Msg.fail().setMsg("移除商品失败,请重新登录后再尝试!");
        }
        return Msg.success().setMsg("移除商品成功");
    }

    /**
     * 添加单个商品进购物车
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/addProductToCart",method = RequestMethod.POST)
    @ResponseBody
    private Msg addProductToCart(@RequestParam("productId")Integer productId,HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (null == personInfo){
            return Msg.fail().setMsg("操作异常,请重新退出账号重新登录后再尝试");
        }
        //添加之前先要查一下购物车里是否已经有这个商品,有则将计数加一,否则就新增一行数据;
        Integer i =0 ;
        i = cartService.getProductItem(personInfo.getUserId(),productId);
        if (0 < i){
            i =cartService.updateAddProductItem(personInfo.getUserId(),productId);
        }else {
            i = cartService.addProductToCart(personInfo.getUserId(), productId);
        }
        if (0 >= i){
            return Msg.fail().setMsg("添加商品进购物车失败");
        }
        return Msg.success().setMsg("添加商品进购物车成功");
    }


    @RequestMapping(value = "/ajax/addProductToCartBatch",method = RequestMethod.POST)
    @ResponseBody
    private Msg addProductToCartBatch(@RequestParam("productId")String productId,HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        String[] productIds = productId.split(",");
        List<Integer> productIdList = new ArrayList<>();
        for (String id:productIds){
            productIdList.add(Integer.parseInt(id));
        }
        Integer i = cartService.addProductToCartBatch(personInfo.getUserId(),productIdList);
        if (0 >= i){
            return Msg.fail().setMsg("添加商品进购物车失败");
        }
        return Msg.success().setMsg("添加商品进构成成功");
    }

    /**
     * 购物车商品数量加一
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/updateAddProduct",method = RequestMethod.POST)
    @ResponseBody
    private Msg updateAddProduct(@RequestParam("productId")Integer productId,HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        Integer integer = cartService.updateAddProductItem(personInfo.getUserId(), productId);
        if (0 >= integer){
            return Msg.fail().setMsg("增加商品数量失败");
        }
        return Msg.success().setMsg("增加商品数量成功");
    }

    /**
     * 购物车商品数量减一
     * @param productId
     * @param request
     * @return
     */
    @RequestMapping(value = "/ajax/updateMinusProduct",method = RequestMethod.POST)
    @ResponseBody
    private Msg updateMinusProduct(@RequestParam("productId")Integer productId,HttpServletRequest request){
        //在用户登录成功跳转到首页的时候就已经在session中写入了用户信息;
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        Integer integer = cartService.updateMinusProductItem(personInfo.getUserId(), productId);
        if (0 >= integer){
            return Msg.fail().setMsg("增加商品数量失败");
        }
        return Msg.success().setMsg("增加商品数量成功");
    }
}
