package com.kingguanzhang.dealsites.service;


import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.Product;

import java.io.InputStream;
import java.util.List;

public interface ProductService {

    public List<Product> getProductList(Integer shopId);

    Product getProduct(Integer productId);

    void addProduct(Product product, InputStream inputStream, String originalFilename);

    int updateProduct(Product product);

    Integer removeProduct(Integer productId);

    Integer shelveProduct(Integer productId);

    Integer unShelveProduct(Integer productId);

    List<Product> getShelveProductList(Integer shopId);

    List<Product> getUnShelveProduct(Integer shopId);

    Integer deleteProducts(List<Integer> productIdList);

    Integer putawayProducts(List<Integer> productIdList);

    Integer soldoutProducts(List<Integer> productIdList);

    List<Product> getAllProductList();

    List<Product> getAllPutawayProductList();

    List<Product> getAllSoldoutProduct();

    Msg updateProductWithImg(Product product, InputStream inputStream, String originalFilename);

    List<Product> getOnSellProductListByCategoryIdAndShopId(Integer categoryId, Integer shopId);

    List<Product> getAllOnSellProductList();

    List<Product> getOnSellProductListByCategoryId(Integer categoryId);
}
