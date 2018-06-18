package com.kingguanzhang.dealsites.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class RoleLocalauth implements Serializable {
    private static final long serialVersionUID = 133264388920897812L;
    @Id
    private Integer roleLocalauthId;

    private Integer roleId;

    private Integer localauthId;

    public Integer getRoleLocalauthId() {
        return roleLocalauthId;
    }

    public void setRoleLocalauthId(Integer roleLocalauthId) {
        this.roleLocalauthId = roleLocalauthId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getLocalauthId() {
        return localauthId;
    }

    public void setLocalauthId(Integer localauthId) {
        this.localauthId = localauthId;
    }
}