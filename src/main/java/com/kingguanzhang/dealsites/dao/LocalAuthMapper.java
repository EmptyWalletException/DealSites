package com.kingguanzhang.dealsites.dao;


import com.kingguanzhang.dealsites.pojo.LocalAuth;
import com.kingguanzhang.dealsites.pojo.LocalAuthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface LocalAuthMapper {
    long countByExample(LocalAuthExample example);

    int deleteByExample(LocalAuthExample example);

    int deleteByPrimaryKey(Integer localAuthId);

    int insert(LocalAuth record);

    int insertSelective(LocalAuth record);

    List<LocalAuth> selectByExample(LocalAuthExample example);

    LocalAuth selectByPrimaryKey(Integer localAuthId);

    int updateByExampleSelective(@Param("record") LocalAuth record, @Param("example") LocalAuthExample example);

    int updateByExample(@Param("record") LocalAuth record, @Param("example") LocalAuthExample example);

    int updateByPrimaryKeySelective(LocalAuth record);

    int updateByPrimaryKey(LocalAuth record);
}