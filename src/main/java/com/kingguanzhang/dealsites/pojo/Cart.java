package com.kingguanzhang.dealsites.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Cart implements Serializable {
    private static final long serialVersionUID = 1332643889208978236L;
    @Id
    private Integer cartId;

    private Integer personInfoId;

    private Integer productId;

    private Integer productCount;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getPersonInfoId() {
        return personInfoId;
    }

    public void setPersonInfoId(Integer personInfoId) {
        this.personInfoId = personInfoId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}