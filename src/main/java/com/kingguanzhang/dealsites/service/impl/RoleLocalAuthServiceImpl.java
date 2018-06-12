package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.RoleLocalauthMapper;
import com.kingguanzhang.dealsites.pojo.RoleLocalauth;
import com.kingguanzhang.dealsites.service.RoleLocalauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleLocalAuthServiceImpl implements RoleLocalauthService {

    @Autowired
    private RoleLocalauthMapper roleLocalauthMapper;

    @Override
    public int addRoleLocalauth(RoleLocalauth roleLocalauth) {
        int i = roleLocalauthMapper.insertSelective(roleLocalauth);
        if (0 > i){
            throw new RuntimeException("账号授权失败");
        }
        return i;
    }
}
