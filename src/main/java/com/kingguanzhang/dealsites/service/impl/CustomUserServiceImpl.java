package com.kingguanzhang.dealsites.service.impl;


import com.kingguanzhang.dealsites.dao.LocalAuthMapper;
import com.kingguanzhang.dealsites.dao.RoleLocalauthMapper;
import com.kingguanzhang.dealsites.dao.RoleMapper;
import com.kingguanzhang.dealsites.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用于security加载用户角色权限的service;
 */
@Service
public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private LocalAuthMapper localAuthMapper;
    @Autowired
    private RoleLocalauthMapper roleLocalauthMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (null == username || "" == username.trim() ){
            throw new RuntimeException("未能获取到账号");
        }

        //通过传入的username获取数据库中查出后封装的本地账号(就是页面上的登录名和密码)所在的实体类;
        LocalAuthExample localAuthExample = new LocalAuthExample();
        localAuthExample.createCriteria().andUsernameEqualTo(username);
        List<LocalAuth> localAuthList = localAuthMapper.selectByExample(localAuthExample);//查出的实际上只有一条数据
        if(0 == localAuthList.size()){
            throw new UsernameNotFoundException("用户不存在");
        }
        LocalAuth localAuth = localAuthList.get(0);
        Integer id = localAuth.getUserId();

        //然后通过账号id查出账号所拥有的角色Id;
        RoleLocalauthExample roleLocalauthExample = new RoleLocalauthExample();
        roleLocalauthExample.createCriteria().andLocalauthIdEqualTo(id);
        List<RoleLocalauth> roleLocalauthList = roleLocalauthMapper.selectByExample(roleLocalauthExample);

        //遍历出所有的角色Id装进一个列表;
        List<Integer> idList = new ArrayList<>();
        for(RoleLocalauth roleLocalauth:roleLocalauthList){
            idList.add(roleLocalauth.getRoleId());
        }

        //通过所有遍历出来的角色Id查出角色;
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andRoleIdIn(idList);
        List<Role> roleList = roleMapper.selectByExample(roleExample);

        //新建一个ArrayList用于装载角色信息;
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        //用于添加用户的权限。只要把遍历出来的角色添加到authorities 就万事大吉。
        int i =0;
        for(Role role:roleList) {

            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            System.out.println("角色service得到第"+i +"个角色信息"+role.getRoleName());
            i++;
        }

        String password = new BCryptPasswordEncoder().encode(localAuth.getPassword());
        return new org.springframework.security.core.userdetails.User(localAuth.getUsername(),
                password, authorities);//构造器依次传入(登录账号,密码,角色列表),注意这里密码仍然需要加密,否则报错:id = null;
    }
}
