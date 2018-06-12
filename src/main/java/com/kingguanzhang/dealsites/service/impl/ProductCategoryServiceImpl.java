package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.ProductCategoryMapper;
import com.kingguanzhang.dealsites.dao.ProductMapper;
import com.kingguanzhang.dealsites.pojo.ProductCategory;
import com.kingguanzhang.dealsites.pojo.ProductCategoryExample;
import com.kingguanzhang.dealsites.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;




    @Override
    public List<ProductCategory> getCategory() {
        //这里需要按照priority排序,这是为了方便在首页的分类列表进行划分;
        ProductCategoryExample productCategoryExample = new ProductCategoryExample();
        productCategoryExample.setOrderByClause("priority ASC");
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);
        return productCategoryList;
    }

    @Override
    public Integer addProductCategroy(ProductCategory productCategory) {
        return productCategoryMapper.insert(productCategory);
    }

    @Override
    public Integer editProductCategroy(ProductCategory productCategory) {
        return productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public Integer deleteProductCategroy(Integer id) {
        //删除分类之前先将分类关联的所有商品的分类设置成NULL;
        productMapper.updateProductCategoryIdToNullByProductCategoryId(id);
        return productCategoryMapper.deleteByPrimaryKey(id);
    }
}
