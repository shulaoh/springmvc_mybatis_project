package com.web.data.bean;

import java.sql.Timestamp;

public class SchRemainInfo {
	private String schPlace;
	private String schName;
	private Timestamp schStime;
	private String phone;

	private String email;

	private String userName;
	private String userType;

	public String getSchPlace() {
		return schPlace;
	}

	public void setSchPlace(String schPlace) {
		this.schPlace = schPlace;
	}

	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public Timestamp getSchStime() {
		return schStime;
	}

	public void setSchStime(Timestamp schStime) {
		this.schStime = schStime;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
