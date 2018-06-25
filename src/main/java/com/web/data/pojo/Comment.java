package com.web.data.pojo;

import java.util.Date;

public class Comment {
    private String commentId;

    private String userId;

    private String tempRelateId;

    private String tempItemId;

    private String targetType;

    private String lessId;

    private String schId;

    private String score;

    private String targetId;

    private String typeKey;

    private Date commTime;

    private String tempId;

    private String remark;

    private String remark1;

    private String remark2;

    private String comment;
    
	public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId == null ? null : commentId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getTempRelateId() {
        return tempRelateId;
    }

    public void setTempRelateId(String tempRelateId) {
        this.tempRelateId = tempRelateId == null ? null : tempRelateId.trim();
    }

    public String getTempItemId() {
        return tempItemId;
    }

    public void setTempItemId(String tempItemId) {
        this.tempItemId = tempItemId == null ? null : tempItemId.trim();
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType == null ? null : targetType.trim();
    }

    public String getLessId() {
        return lessId;
    }

    public void setLessId(String lessId) {
        this.lessId = lessId == null ? null : lessId.trim();
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId == null ? null : schId.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId == null ? null : targetId.trim();
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey == null ? null : typeKey.trim();
    }

    public Date getCommTime() {
        return commTime;
    }

    public void setCommTime(Date commTime) {
        this.commTime = commTime;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId == null ? null : tempId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}