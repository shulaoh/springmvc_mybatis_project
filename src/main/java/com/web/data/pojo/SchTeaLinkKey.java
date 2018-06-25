package com.web.data.pojo;

public class SchTeaLinkKey {
    private String schId;

    private String userId;

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId == null ? null : schId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}