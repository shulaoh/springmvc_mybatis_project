package com.web.data.bean;

import java.util.Date;

public class CommentList {
	private String commentId;

	private String tempItemId;

	private String targetType;

	private String score;

	private Date commTime;

	private String comment;

	private String userName;

	private String sign;
	
	private String tempItemName;
	

	public String getTempItemName() {
		return tempItemName;
	}

	public void setTempItemName(String tempItemName) {
		this.tempItemName = tempItemName;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getTempItemId() {
		return tempItemId;
	}

	public void setTempItemId(String tempItemId) {
		this.tempItemId = tempItemId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Date getCommTime() {
		return commTime;
	}

	public void setCommTime(Date commTime) {
		this.commTime = commTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}