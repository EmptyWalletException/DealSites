package com.kingguanzhang.dealsites.pojo;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class Area implements Serializable {
    private static final long serialVersionUID = 1332643889208978232L;
    @Id
    private Integer areaId;

    private String areaName;

    private Integer priority;

    private Date createTime;

    private Date editTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }
}