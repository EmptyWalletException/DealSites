package com.kingguanzhang.dealsites.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class FavoriteShop implements Serializable {
    private static final long serialVersionUID = 1332643889208971232L;
    @Id
    private Integer favoriteShopId;

    private Integer shopId;

    private Integer personinfoId;

    private PersonInfo personInfo;
    private Shop shop;

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Integer getFavoriteShopId() {
        return favoriteShopId;
    }

    public void setFavoriteShopId(Integer favoriteShopId) {
        this.favoriteShopId = favoriteShopId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getPersoninfoId() {
        return personinfoId;
    }

    public void setPersoninfoId(Integer personinfoId) {
        this.personinfoId = personinfoId;
    }
}