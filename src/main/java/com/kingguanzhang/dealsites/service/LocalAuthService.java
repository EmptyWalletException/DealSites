package com.kingguanzhang.dealsites.service;


import com.kingguanzhang.dealsites.pojo.LocalAuth;

import java.util.List;

public interface LocalAuthService {
    List<LocalAuth> getLocalAuthByLoginUsername(String username);

    int addLocalAuth(LocalAuth localAuth);



}
