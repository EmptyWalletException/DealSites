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

    /**
     * 新增用户;
     * @param personInfo
     * @return
     */
    @Override
    public int addPersonInfo(PersonInfo personInfo) {
        if (null == personInfo){
            throw new RuntimeException("未能获取到用户信息");
        }
        personInfo.setCreateTime(new Date(System.currentTimeMillis()));
        personInfo.setEditTime(new Date(System.currentTimeMillis()));
        personInfo.setUserType(2);//userType是预留字段,具体意义留着以后再定义
        personInfo.setEnableStatus(1);//用户状态,预留给以后记录用户是否封禁等;
        int personInfoId=-1;
        try{
            personInfoId = personInfoMapper.insertSelective(personInfo);
        }catch (Exception e){
            throw new RuntimeException("用户信息保存失败");
        }
        return personInfoId;
    }

    /**
     * 查询用户
     * @param inputValue
     * @return
     */
    @Override
    public List<PersonInfo> getByName(String inputValue) {
        if (null == inputValue){
            throw new RuntimeException("未能获取到用户名字");
        }
        PersonInfoExample personInfoExample = new PersonInfoExample();
        PersonInfoExample.Criteria criteria = personInfoExample.createCriteria();
        criteria.andNameEqualTo(inputValue);
        List<PersonInfo> personInfoList = personInfoMapper.selectByExample(personInfoExample);
        return personInfoList;
    }
}
