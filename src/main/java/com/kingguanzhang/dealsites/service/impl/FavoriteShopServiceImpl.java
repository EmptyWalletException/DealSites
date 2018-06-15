package com.kingguanzhang.dealsites.service.impl;

import com.kingguanzhang.dealsites.dao.FavoriteShopMapper;
import com.kingguanzhang.dealsites.pojo.FavoriteShop;
import com.kingguanzhang.dealsites.pojo.FavoriteShopExample;
import com.kingguanzhang.dealsites.service.FavoriteShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteShopServiceImpl implements FavoriteShopService {
    
    @Autowired
    private FavoriteShopMapper favoriteShopMapper;

    /**
     * 收藏店铺的方法
     * @param productId
     * @param userId
     * @return
     */
    @Override
    public Integer addFavoriteShop(Integer productId, Integer userId) {
        if (null == productId || null== userId){
            throw new RuntimeException("未能获取到商品Id或用户Id");
        }
        FavoriteShop favoriteShop = new FavoriteShop();
        favoriteShop.setShopId(productId);
        favoriteShop.setPersoninfoId(userId);
        int i=0;
        try{
             i = favoriteShopMapper.insertSelective(favoriteShop);
        }catch (Exception e){
            throw new RuntimeException("收藏店铺失败");
        }
        return i;
    }

    /**
     * 取消收藏店铺的方法;
     * @param productId
     * @param userId
     * @return
     */
    @Override
    public Integer removeFavoriteShop(Integer productId, Integer userId) {
        if (null == productId || null== userId){
            throw new RuntimeException("未能获取到商品Id或用户Id");
        }
        FavoriteShopExample favoriteShopExample = new FavoriteShopExample();
        favoriteShopExample.createCriteria().andShopIdEqualTo(productId).andPersoninfoIdEqualTo(userId);
        int i=0;
        try{
            i = favoriteShopMapper.deleteByExample(favoriteShopExample);
        }catch (Exception e){
            throw new RuntimeException("取消收藏失败");
        }
        return i;
    }

    /**
     * 获取收藏的店铺列表;
     * @param personInfoId
     * @return
     */
    @Override
    public List<FavoriteShop> getFavoriteShopList(Integer personInfoId) {
        if (null == personInfoId ){
            throw new RuntimeException("未能获取到用户Id");
        }
        FavoriteShopExample favoriteShopExample = new FavoriteShopExample();
        favoriteShopExample.createCriteria().andPersoninfoIdEqualTo(personInfoId);
        List<FavoriteShop> favoriteShopList = favoriteShopMapper.selectByExample(favoriteShopExample);
        return favoriteShopList;
    }

    /**
     * 查询数据库表中是否已经存在此店铺
     * @param userId
     * @param shopId
     * @return
     */
    @Override
    public Integer getFavoriteShop(Integer userId, Integer shopId) {
        if (null == shopId || null == userId ){
            throw new RuntimeException("未能获取到用户Id或店铺Id");
        }
        FavoriteShopExample favoriteShopExample = new FavoriteShopExample();
        favoriteShopExample.createCriteria().andPersoninfoIdEqualTo(userId).andShopIdEqualTo(shopId);
        List<FavoriteShop> favoriteShopList = favoriteShopMapper.selectByExample(favoriteShopExample);
        return favoriteShopList.size();
    }
}
