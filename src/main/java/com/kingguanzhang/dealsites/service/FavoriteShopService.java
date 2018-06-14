package com.kingguanzhang.dealsites.service;

import com.kingguanzhang.dealsites.pojo.FavoriteShop;

import java.util.List;

public interface FavoriteShopService {
    Integer addFavoriteShop(Integer shopId, Integer userId);

    Integer removeFavoriteShop(Integer shopId, Integer userId);

    List<FavoriteShop> getFavoriteShopList(Integer userId);

    Integer getFavoriteShop(Integer userId, Integer shopId);
}
