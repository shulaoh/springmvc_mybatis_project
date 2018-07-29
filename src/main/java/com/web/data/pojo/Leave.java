package com.web.data.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class Leave {
	private String userId;
	private String schId;
	private String time;
	private String cancelFlag;
	private String reason;
	private Teacher user;
	private String remark;

	private String isApproved;
	private Timestamp approveDate;
	private String approveUserId;
	private Teacher approveUser;

	public String getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(String isApproved) {
		this.isApproved = isApproved;
	}

	public Timestamp getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}

	public String getApproveUserId() {
		return approveUserId;
	}

	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

	public Teacher getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(Teacher approveUser) {
		this.approveUser = approveUser;
	}

	public Teacher getUser() {
		return this.user;
	}

	public void setUser(Teacher user) {
		this.user = user;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSchId() {
		return this.schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCancelFlag() {
		return this.cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}