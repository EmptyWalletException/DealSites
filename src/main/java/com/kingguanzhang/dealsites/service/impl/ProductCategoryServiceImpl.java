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


    /**
     * 获取分类列表;
     * @return
     */
    @Override
    public List<ProductCategory> getCategory() {
        //这里需要按照priority排序,这是为了方便在首页的分类列表进行划分;
        ProductCategoryExample productCategoryExample = new ProductCategoryExample();
        productCategoryExample.setOrderByClause("priority ASC");
        List<ProductCategory> productCategoryList = productCategoryMapper.selectByExample(productCategoryExample);
        return productCategoryList;
    }

    /**
     * 新增分类;
     * @param productCategory
     * @return
     */
    @Override
    public Integer addProductCategroy(ProductCategory productCategory) {
        if (null == productCategory){
            throw new RuntimeException("未能获取到分类信息");
        }
        int i=0;
        try{
            i = productCategoryMapper.insertSelective(productCategory);
        }catch (Exception e){
            throw new RuntimeException("新增分类失败");
        }
        return i;
    }

    /**
     * 更新分类
     * @param productCategory
     * @return
     */
    @Override
    public Integer updateProductCategory(ProductCategory productCategory) {
        if (null == productCategory){
            throw new RuntimeException("未能获取到分类信息");
        }
        int i=0;
        try{
           i = productCategoryMapper.updateByPrimaryKeySelective(productCategory);
        }catch (Exception e){
            throw new RuntimeException("更新分类失败");
        }
        return i;
    }

    /**
     * 删除分类;
     * @param id
     * @return
     */
    @Override
    public Integer deleteProductCategroy(Integer id) {
        if (null == id){
            throw new RuntimeException("未能获取到分类Id");
        }
        int i=0;
        try{
            //删除分类之前先将分类关联的所有商品的分类设置成NULL;
            productMapper.updateProductCategoryIdToNullByProductCategoryId(id);
          i =  productCategoryMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            throw new RuntimeException("删除分类失败");
        }

        return i;
    }
}
