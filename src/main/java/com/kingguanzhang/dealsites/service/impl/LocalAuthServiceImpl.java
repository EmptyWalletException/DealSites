package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.LocalAuthMapper;
import com.kingguanzhang.dealsites.pojo.LocalAuth;
import com.kingguanzhang.dealsites.pojo.LocalAuthExample;
import com.kingguanzhang.dealsites.service.LocalAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthMapper localAuthMapper;

    /**
     * 查询用户账号信息;
     * @param username
     * @return
     */
    @Override
    public List<LocalAuth> getLocalAuthByLoginUsername(String username) {
        if (null == username){
            throw new RuntimeException("未能获取到账号");
        }
        LocalAuthExample localAuthExample = new LocalAuthExample();
        LocalAuthExample.Criteria criteria = localAuthExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<LocalAuth> localAuths = localAuthMapper.selectByExample(localAuthExample);

        return localAuths;
    }

    /**
     * 保存用户账号信息到数据库;
     * @param localAuth
     * @return
     */
    @Override
    public int addLocalAuth(LocalAuth localAuth) {
        if (null == localAuth){
            throw new RuntimeException("未能获取到账号信息");
        }
        localAuth.setCreateTime(new Date(System.currentTimeMillis()));
        localAuth.setEditTime(new Date(System.currentTimeMillis()));
        int i=0;
        try{
             i = localAuthMapper.insertSelective(localAuth);
        }catch (Exception e){
            throw new RuntimeException("保存用户信息失败");
        }
        return i;
    }


}
