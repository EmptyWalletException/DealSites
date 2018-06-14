package com.kingguanzhang.dealsites.dao;

import com.kingguanzhang.dealsites.pojo.FavoriteShop;
import com.kingguanzhang.dealsites.pojo.FavoriteShopExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavoriteShopMapper {
    long countByExample(FavoriteShopExample example);

    int deleteByExample(FavoriteShopExample example);

    int deleteByPrimaryKey(Integer favoriteShopId);

    int insert(FavoriteShop record);

    int insertSelective(FavoriteShop record);

    List<FavoriteShop> selectByExample(FavoriteShopExample example);

    FavoriteShop selectByPrimaryKey(Integer favoriteShopId);

    int updateByExampleSelective(@Param("record") FavoriteShop record, @Param("example") FavoriteShopExample example);

    int updateByExample(@Param("record") FavoriteShop record, @Param("example") FavoriteShopExample example);

    int updateByPrimaryKeySelective(FavoriteShop record);

    int updateByPrimaryKey(FavoriteShop record);
}