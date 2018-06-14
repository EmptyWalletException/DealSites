package com.kingguanzhang.dealsites.pojo;

public class FavoriteProduct {
    private Integer favoriteProductId;

    private Integer productId;

    private Integer personinfoId;

    private PersonInfo personInfo;
    private Product product;

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getFavoriteProductId() {
        return favoriteProductId;
    }

    public void setFavoriteProductId(Integer favoriteProductId) {
        this.favoriteProductId = favoriteProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPersoninfoId() {
        return personinfoId;
    }

    public void setPersoninfoId(Integer personinfoId) {
        this.personinfoId = personinfoId;
    }
}