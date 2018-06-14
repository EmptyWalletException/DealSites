package com.kingguanzhang.dealsites.service;

import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.Product;

import java.util.List;

public interface FavoriteProductService {
    Integer addFavoriteProduct(Integer productId, Integer userId);

    Integer removeFavoriteProduct(Integer productId, Integer userId);

    List<FavoriteProduct> getFavoriteProductList(Integer personInfoId);

    Integer getFavoriteProduct(Integer personInfoId, Integer productId);
}
