package com.kingguanzhang.dealsites.service.impl;

import com.kingguanzhang.dealsites.dao.FavoriteProductMapper;
import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.FavoriteProductExample;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteProductServiceImpl implements com.kingguanzhang.dealsites.service.FavoriteProductService {

    @Autowired
    private FavoriteProductMapper favoriteProductMapper;

    /**
     * 收藏商品的方法
     * @param productId
     * @param userId
     * @return
     */
    @Override
    public Integer addFavoriteProduct(Integer productId, Integer userId) {
        FavoriteProduct favoriteProduct = new FavoriteProduct();
        favoriteProduct.setProductId(productId);
        favoriteProduct.setPersoninfoId(userId);
        int i = favoriteProductMapper.insertSelective(favoriteProduct);
        return i;
    }

    /**
     * 取消收藏商品的方法;
     * @param productId
     * @param userId
     * @return
     */
    @Override
    public Integer removeFavoriteProduct(Integer productId, Integer userId) {
        FavoriteProductExample favoriteProductExample = new FavoriteProductExample();
        favoriteProductExample.createCriteria().andProductIdEqualTo(productId).andPersoninfoIdEqualTo(userId);
        int i = favoriteProductMapper.deleteByExample(favoriteProductExample);
        return i;
    }

    /**
     * 获取收藏的商品列表;
     * @param personInfoId
     * @return
     */
    @Override
    public List<FavoriteProduct> getFavoriteProductList(Integer personInfoId) {
        FavoriteProductExample favoriteProductExample = new FavoriteProductExample();
        favoriteProductExample.createCriteria().andPersoninfoIdEqualTo(personInfoId);
        List<FavoriteProduct> favoriteProductList = favoriteProductMapper.selectByExample(favoriteProductExample);
        return favoriteProductList;
    }

    /**
     * 在数据库查询此商品是否已经存在;
     * @param personInfo
     * @param productId
     * @return
     */
    @Override
    public Integer getFavoriteProduct(Integer personInfoId, Integer productId) {
        FavoriteProductExample favoriteProductExample = new FavoriteProductExample();
        favoriteProductExample.createCriteria().andPersoninfoIdEqualTo(personInfoId).andProductIdEqualTo(productId);
        List<FavoriteProduct> favoriteProductList = favoriteProductMapper.selectByExample(favoriteProductExample);
        return favoriteProductList.size();
    }
}
