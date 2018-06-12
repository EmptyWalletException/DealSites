package com.kingguanzhang.dealsites.controller.buyer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingguanzhang.dealsites.dto.Msg;
import com.kingguanzhang.dealsites.pojo.LocalAuth;
import com.kingguanzhang.dealsites.pojo.PersonInfo;
import com.kingguanzhang.dealsites.pojo.RoleLocalauth;
import com.kingguanzhang.dealsites.service.LocalAuthService;
import com.kingguanzhang.dealsites.service.PersonInfoService;
import com.kingguanzhang.dealsites.service.RoleLocalauthService;
import com.kingguanzhang.dealsites.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/register")//增加这个注解是为了防止相关url被security拦截
@Controller
public class RegisterUserController {

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    @Autowired
    private RoleLocalauthService roleLocalauthService;

    /**
     * ajax检查账号是否已经被占用
     * @param inputValue
     * @return
     */
    @RequestMapping(value = "/checkUsername")
    @ResponseBody
    public Msg checkUsername(@RequestParam("inputValue") String inputValue){
        List<LocalAuth> localAuthList = localAuthService.getLocalAuthByLoginUsername(inputValue);
        if (0 == localAuthList.size()){
            return Msg.success().setMsg("账号未被占用");
        }
        return Msg.fail().setMsg("账号已经被占用");
    }

    /**
     * ajax检查昵称是否已经被占用
     * @param inputValue
     * @return
     */
    @RequestMapping(value = "/checkPetname",method = RequestMethod.POST)
    @ResponseBody
    public Msg checkPetname(@RequestParam("inputValue") String inputValue){
       List<PersonInfo> personInfoList = personInfoService.getByName(inputValue);
        if (0 == personInfoList.size()){
            return Msg.success().setMsg("昵称未被占用");
        }
        return Msg.fail().setMsg("昵称已经被占用");
    }

    /**
     *  注册用户;
     */
    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    @ResponseBody
    public Msg registerUser(HttpServletRequest request){
        LocalAuth localAuth = null;
        PersonInfo personInfo = null;
        RoleLocalauth roleLocalauth = new RoleLocalauth();
        ObjectMapper objectMapper = new ObjectMapper();

        //将request中的Jason转成字符串
        String localAuthStr = RequestUtil.parserString(request,"localAuth");
        String personInfoStr = RequestUtil.parserString(request,"personInfo");

        System.out.println("注册"+localAuthStr);
        System.out.println("注册"+personInfoStr);

        try {
            localAuth = objectMapper.readValue(localAuthStr,LocalAuth.class);
            personInfo = objectMapper.readValue(personInfoStr,PersonInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.fail().setMsg("设置账号信息失败!");
        }

        if (null != localAuth && null != personInfo) {
            if ("" != localAuth.getUsername().trim() && "" != localAuth.getPassword().trim() &&
                    "" != personInfo.getEmail().trim() && "" != personInfo.getName().trim() &&
                    "" != personInfo.getGender().trim() ){
                try{
                    //先向数据库中添加PersonInfo,然后返回id再设置进本地帐户中;
                    //返回受影响的行数,同时由于修改了Mapper文件,会返回在数据库中自增的id,需要在实体类中用get方法取出来;
                    int row=personInfoService.addPersonInfo(personInfo);
                    Integer userId = personInfo.getUserId();
                  //  localAuth.setUserId(userId);

                    //返回受影响的行数,同时由于修改了Mapper文件,会返回在数据库中自增的id,需要在实体类中用get方法取出来;
                    row = localAuthService.addLocalAuth(localAuth);
                    Integer localAuthId = localAuth.getLocalAuthId();

                    //开始创建用户与角色的关联信息实体类,roleId为2代表普通用户;
                    roleLocalauth.setRoleId(2);
                    //将刚刚返回的帐户在数据库中的Id填入;
                    roleLocalauth.setLocalauthId(localAuthId);
                    //返回值是受影响的行数;
                    roleLocalauthService.addRoleLocalauth(roleLocalauth);

                    return Msg.success().setMsg("注册成功");
                }catch (Exception e){
                    e.printStackTrace();
                    return  Msg.fail().setMsg("保存用户信息或帐户信息失败!");
                }
            }
        }
        return Msg.fail().setMsg("注册失败!");
    }
}
