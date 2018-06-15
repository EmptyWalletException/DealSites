package com.kingguanzhang.dealsites.service.impl;

import com.kingguanzhang.dealsites.dao.CartMapper;
import com.kingguanzhang.dealsites.pojo.Cart;
import com.kingguanzhang.dealsites.pojo.CartExample;
import com.kingguanzhang.dealsites.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;


    /**
     * 通过用户id获取购物车表中的商品List
     * @param userId
     * @return
     */
    @Override
    public List<Cart> getCartList(Integer userId) {
        if (null == userId){
            throw new RuntimeException("未能获取到用户Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andPersonInfoIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        return cartList;
    }

    /**
     * 通过用户id和商品id从购物车表中移除单个商品;
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public Integer removeProductFromCart(Integer userId, Integer productId) {
        if (null == userId || null == productId){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andPersonInfoIdEqualTo(userId).andProductIdEqualTo(productId);
        int i =0 ;
        try{
            i = cartMapper.deleteByExample(cartExample);
        }catch (Exception e){
            throw new RuntimeException("删除商品失败:"+e.getMessage());
        }
        return i;
    }

    /**
     * 通过用户id和商品id集合从购物车表总移除批量商品;
     * @param userId
     * @param productIdList
     * @return
     */
    @Override
    public Integer removeProductFromCartBatch(Integer userId, List<Integer> productIdList) {
        if (null == userId || 0 == productIdList.size()){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andPersonInfoIdEqualTo(userId).andProductIdIn(productIdList);
        int i =0;
        try{
            i = cartMapper.deleteByExample(cartExample);
        }catch (Exception e){
            throw new RuntimeException("删除商品失败:"+e.getMessage());
        }
        return i;
    }

    /**
     * 通过用户Id和商品Id添加单个商品进购物车表
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public Integer addProductToCart(Integer userId, Integer productId) {
        if (null == userId || null == productId){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        Cart cart = new Cart();
        cart.setPersonInfoId(userId);
        cart.setProductId(productId);
        cart.setProductCount(1);
        int i = 0;
        try {
             i= cartMapper.insertSelective(cart);
        }catch (Exception e){
            throw new RuntimeException("添加商品失败"+e.getMessage());
        }
        return i;
    }

    /**
     * 通过用户Id和商品Id集合添加批量商品进购物车表
     * @param userId
     * @param productIdList
     * @return
     */
    @Override
    public Integer addProductToCartBatch(Integer userId, List<Integer> productIdList) {
        if (null == userId || 0 == productIdList.size()){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        Cart cart = new Cart();
        cart.setPersonInfoId(userId);
        cart.setProductCount(1);
        int row = 0;
        for (int i = 0 ; i <= productIdList.size(); i++){
            cart.setProductId(productIdList.get(i));
            try {
                row= cartMapper.insert(cart);
            }catch (Exception e){
                throw new RuntimeException("添加商品失败: "+e.getMessage());
            }
        }
        return row;
    }

    /**
     * 数据中查询购物车中是否已经有此商品;
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public Integer getProductItem(Integer userId, Integer productId) {
        if (null == userId || null == productId){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdEqualTo(productId).andPersonInfoIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        return cartList.size();
    }

    /**
     * 在数据库中将此商品的计数增加一个;
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public Integer updateAddProductItem(Integer userId, Integer productId) {
        if (null == userId || null == productId){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdEqualTo(productId).andPersonInfoIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        if (0 == cartList.size()){
            return 0;
        }
         Cart cart = cartList.get(0);
        Integer productCount = cart.getProductCount();
           if (0 >= productCount){
               cart.setProductCount(1);
           }else {
               cart.setProductCount(productCount+1);
           }
            int i=0;
           try{
                i = cartMapper.updateByPrimaryKeySelective(cart);
               
           }catch (Exception e){
               throw  new RuntimeException("更新商品数量失败!");
           }
        return i;
    }

    /**
     * 在数据库中将此商品的计数减少一个;
     * @param userId
     * @param productId
     * @return
     */
    @Override
    public Integer updateMinusProductItem(Integer userId, Integer productId) {
        if (null == userId || null == productId){
            throw new RuntimeException("未能获取到用户Id或商品Id");
        }
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andProductIdEqualTo(productId).andPersonInfoIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        if (0 == cartList.size()){
            return 0;
        }
        Cart cart = cartList.get(0);
        Integer productCount = cart.getProductCount();
        //做一个判断防止数据库中的productCount计数为0
        if (0 >= productCount){
            cart.setProductCount(1);
        }else {
            if(1 < productCount) {
                cart.setProductCount(productCount - 1);
            }else{
                return 1;
            }
        }

        int i=0;
        try{
            i = cartMapper.updateByPrimaryKeySelective(cart);

        }catch (Exception e){
            throw  new RuntimeException("更新商品数量失败!");
        }
        return i;
    }
}
