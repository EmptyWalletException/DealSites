package com.kingguanzhang.dealsites.dao;


import com.kingguanzhang.dealsites.pojo.RoleLocalauth;
import com.kingguanzhang.dealsites.pojo.RoleLocalauthExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RoleLocalauthMapper {
    long countByExample(RoleLocalauthExample example);

    int deleteByExample(RoleLocalauthExample example);

    int deleteByPrimaryKey(Integer roleLocalauthId);

    int insert(RoleLocalauth record);

    int insertSelective(RoleLocalauth record);

    List<RoleLocalauth> selectByExample(RoleLocalauthExample example);

    RoleLocalauth selectByPrimaryKey(Integer roleLocalauthId);

    int updateByExampleSelective(@Param("record") RoleLocalauth record, @Param("example") RoleLocalauthExample example);

    int updateByExample(@Param("record") RoleLocalauth record, @Param("example") RoleLocalauthExample example);

    int updateByPrimaryKeySelective(RoleLocalauth record);

    int updateByPrimaryKey(RoleLocalauth record);
}