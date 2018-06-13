package com.kingguanzhang.dealsites.service;

import com.kingguanzhang.dealsites.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartList(Integer userId);

    Integer removeProductFromCart(Integer userId, Integer productId);

    Integer removeProductFromCartBatch(Integer userId, List<Integer> productIdList);

    Integer addProductToCart(Integer userId, Integer productId);

    Integer addProductToCartBatch(Integer userId, List<Integer> productIdList);

    Integer getProductItem(Integer userId, Integer productId);

    Integer updateAddProductItem(Integer userId, Integer productId);

    Integer updateMinusProductItem(Integer userId, Integer productId);
}
