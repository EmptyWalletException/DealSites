package com.kingguanzhang.dealsites.dao;

import com.kingguanzhang.dealsites.pojo.Cart;
import com.kingguanzhang.dealsites.pojo.CartExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartMapper {
    long countByExample(CartExample example);

    int deleteByExample(CartExample example);

    int deleteByPrimaryKey(Integer cartId);

    int insert(Cart record);

    int insertSelective(Cart record);

    List<Cart> selectByExample(CartExample example);

    Cart selectByPrimaryKey(Integer cartId);

    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}