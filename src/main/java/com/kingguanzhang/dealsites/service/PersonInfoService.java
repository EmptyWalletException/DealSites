package com.kingguanzhang.dealsites.service;

import com.kingguanzhang.dealsites.pojo.PersonInfo;

import java.util.List;

public interface PersonInfoService {
    int addPersonInfo(PersonInfo personInfo);

    List<PersonInfo> getByName(String inputValue);
}
