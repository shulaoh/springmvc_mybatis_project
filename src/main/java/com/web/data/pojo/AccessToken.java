package com.web.data.pojo;

public class AccessToken {
    private String id;

    private String accessToken;

    private Integer expiresIn;

    private Long expiresAfter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getExpiresAfter() {
        return expiresAfter;
    }

    public void setExpiresAfter(Long expiresAfter) {
        this.expiresAfter = expiresAfter;
    }
}