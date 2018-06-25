package com.web.data.pojo;

public class TCommTemplateRelate {
    private String tempRelateId;

    private String tempGroupId;

    private String tempItemId;

    private String roleType;

    private String targetType;

    private Integer tempRelateEnable;

    public String getTempRelateId() {
        return tempRelateId;
    }

    public void setTempRelateId(String tempRelateId) {
        this.tempRelateId = tempRelateId == null ? null : tempRelateId.trim();
    }

    public String getTempGroupId() {
        return tempGroupId;
    }

    public void setTempGroupId(String tempGroupId) {
        this.tempGroupId = tempGroupId == null ? null : tempGroupId.trim();
    }

    public String getTempItemId() {
        return tempItemId;
    }

    public void setTempItemId(String tempItemId) {
        this.tempItemId = tempItemId == null ? null : tempItemId.trim();
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType == null ? null : roleType.trim();
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType == null ? null : targetType.trim();
    }

    public Integer getTempRelateEnable() {
        return tempRelateEnable;
    }

    public void setTempRelateEnable(Integer tempRelateEnable) {
        this.tempRelateEnable = tempRelateEnable;
    }
}