package com.kingguanzhang.dealsites.dao;

import com.kingguanzhang.dealsites.pojo.FavoriteProduct;
import com.kingguanzhang.dealsites.pojo.FavoriteProductExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FavoriteProductMapper {
    long countByExample(FavoriteProductExample example);

    int deleteByExample(FavoriteProductExample example);

    int deleteByPrimaryKey(Integer favoriteProductId);

    int insert(FavoriteProduct record);

    int insertSelective(FavoriteProduct record);

    List<FavoriteProduct> selectByExample(FavoriteProductExample example);

    FavoriteProduct selectByPrimaryKey(Integer favoriteProductId);

    int updateByExampleSelective(@Param("record") FavoriteProduct record, @Param("example") FavoriteProductExample example);

    int updateByExample(@Param("record") FavoriteProduct record, @Param("example") FavoriteProductExample example);

    int updateByPrimaryKeySelective(FavoriteProduct record);

    int updateByPrimaryKey(FavoriteProduct record);
}