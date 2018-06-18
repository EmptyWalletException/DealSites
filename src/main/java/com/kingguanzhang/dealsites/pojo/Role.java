package com.kingguanzhang.dealsites.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1332643889208178232L;
    @Id
    private Integer roleId;

    private String roleName;

    private String rolePetName;

    private Integer roleAuthorityLevel;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRolePetName() {
        return rolePetName;
    }

    public void setRolePetName(String rolePetName) {
        this.rolePetName = rolePetName == null ? null : rolePetName.trim();
    }

    public Integer getRoleAuthorityLevel() {
        return roleAuthorityLevel;
    }

    public void setRoleAuthorityLevel(Integer roleAuthorityLevel) {
        this.roleAuthorityLevel = roleAuthorityLevel;
    }
}