package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.PersonInfoMapper;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.PersonInfoExample;
import com.kingguanzhang.dealsites.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public int addPersonInfo(PersonInfo personInfo) {
        personInfo.setCreateTime(new Date(System.currentTimeMillis()));
        personInfo.setEditTime(new Date(System.currentTimeMillis()));
        personInfo.setUserType(2);//userType是预留字段,具体意义留着以后再定义
        personInfo.setEnableStatus(1);//用户状态,预留给以后记录用户是否封禁等;
        int personInfoId = personInfoMapper.insertSelective(personInfo);
        if (0 > personInfoId){
            throw new RuntimeException("用户信息保存失败!");
        }
        return personInfoId;
    }

    @Override
    public List<PersonInfo> getByName(String inputValue) {
        PersonInfoExample personInfoExample = new PersonInfoExample();
        PersonInfoExample.Criteria criteria = personInfoExample.createCriteria();
        criteria.andNameEqualTo(inputValue);
        List<PersonInfo> personInfoList = personInfoMapper.selectByExample(personInfoExample);
        return personInfoList;
    }
}
