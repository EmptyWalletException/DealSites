package com.kingguanzhang.dealsites.service;


import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Shop;

import java.io.InputStream;
import java.util.List;


public interface ShopService {
    public Msg addShop(Shop shop, InputStream shopImgInputStream, String fileName);


    public Shop getShop(Integer shopId);

    public Msg updateShop(Shop shop, InputStream shopImgInputStream, String fileName);

    Shop getShopByUserId(Integer userId);

    List<Shop> getAllShop();
    Msg updateShopWithoutImg(Shop shop);
}
