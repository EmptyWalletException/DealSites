package com.kingguanzhang.dealsites.service;

import com.kingguanzhang.dealsites.pojo.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getCategory();

    Integer addProductCategroy(ProductCategory productCategory);

    Integer updateProductCategory(ProductCategory productCategory);

    Integer deleteProductCategroy(Integer id);
}
