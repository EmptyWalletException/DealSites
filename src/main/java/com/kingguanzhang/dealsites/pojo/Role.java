package com.kingguanzhang.dealsites.pojo;

public class Role {
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