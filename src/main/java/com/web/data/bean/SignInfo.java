package com.web.data.bean;

import java.io.Serializable;
import java.util.Date;

public class SignInfo implements Serializable {
	private String schId;
	private String lessName;
	private String schName;
	private Date schStime;
	private Date signTime;
	private String place;
	private String userName;
	private String signPicUrl;
	private String phone;
	private String email;
	private String dept;
	private String companyName;
	private String remark;
	private String cancelFlag;
	private String reason;
	private String isApproved;
	private String approveUserName;
	
	public String getIsApprove() {
		return isApproved;
	}
	public void setIsApprove(String isApprove) {
		this.isApproved = isApprove;
	}
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSchId() {
		return schId;
	}
	public void setSchId(String schId) {
		this.schId = schId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLessName() {
		return lessName;
	}
	public void setLessName(String lessName) {
		this.lessName = lessName;
	}
	public String getSchName() {
		return schName;
	}
	public void setSchName(String schName) {
		this.schName = schName;
	}
	public Date getSchStime() {
		return schStime;
	}
	public void setSchStime(Date schStime) {
		this.schStime = schStime;
	}
	public Date getSignTime() {
		return signTime;
	}
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSignPicUrl() {
		return signPicUrl;
	}
	public void setSignPicUrl(String signPicUrl) {
		this.signPicUrl = signPicUrl;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
}
