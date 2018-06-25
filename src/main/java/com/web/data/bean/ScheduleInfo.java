package com.web.data.bean;

import java.io.Serializable;

public class ScheduleInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String schId;
	private String ssTime;
	private String schLastMNum;
	private String schPlace;
	private String schName;
	
	public String getSchName() {
		return schName;
	}
	public void setSchName(String schName) {
		this.schName = schName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSchId() {
		return schId;
	}
	public void setSchId(String schId) {
		this.schId = schId;
	}
	public String getSsTime() {
		return ssTime;
	}
	public void setSsTime(String ssTime) {
		this.ssTime = ssTime;
	}
	public String getSchLastMNum() {
		return schLastMNum;
	}
	public void setSchLastMNum(String schLastMNum) {
		this.schLastMNum = schLastMNum;
	}
	public String getSchPlace() {
		return schPlace;
	}
	public void setSchPlace(String schPlace) {
		this.schPlace = schPlace;
	}
}
