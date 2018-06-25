package com.web.data.pojo;

import java.sql.Timestamp;

public class Lession {
	private String lessonId;
	private String lessonName;
	private String lessonType;
	private int lessonStatus;
	private String stime;
	private String etime;
	private String place;
	private String lessonInfo;
	private String purl;
	private String joinStatus;
	private String allCommFlag;
	private String commentFlag;
	private String teacherId;
	private Teacher teacher;
	private String rejReason;
	private Teacher creator;
	private String creatorId;
	private String lessPicUrl;
	private String lessCycPicUrl;
	private String commTempIds;

	private Timestamp createTime;
	private Timestamp updateTime;

	public SysUser getInstructor() {
		return instructor;
	}

	public void setInstructor(SysUser instructor) {
		this.instructor = instructor;
	}

	private SysUser instructor;

	public Timestamp getCreateTime() {
		if (createTime == null) {
			createTime = new Timestamp(System.currentTimeMillis());
		}
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		if (updateTime == null) {
			if (createTime == null) {
				createTime = new Timestamp(System.currentTimeMillis());
			}
			updateTime = createTime;
		}
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCommTempIds() {
		return this.commTempIds;
	}

	public void setCommTempIds(String commTempIds) {
		this.commTempIds = commTempIds;
	}

	public String getLessPicUrl() {
		return this.lessPicUrl;
	}

	public void setLessPicUrl(String lessPicUrl) {
		this.lessPicUrl = lessPicUrl;
	}

	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getLessCycPicUrl() {
		return this.lessCycPicUrl;
	}

	public void setLessCycPicUrl(String lessCycPicUrl) {
		this.lessCycPicUrl = lessCycPicUrl;
	}

	public Teacher getCreator() {
		return this.creator;
	}

	public void setCreator(Teacher creator) {
		this.creator = creator;
	}

	public String getRejReason() {
		return this.rejReason;
	}

	public void setRejReason(String rejReason) {
		this.rejReason = rejReason;
	}

	public String getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public String getLessonName() {
		return this.lessonName;
	}

	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	public String getLessonType() {
		return this.lessonType;
	}

	public void setLessonType(String lessonType) {
		this.lessonType = lessonType;
	}

	public int getLessonStatus() {
		return this.lessonStatus;
	}

	public void setLessonStatus(int lessonStatus) {
		this.lessonStatus = lessonStatus;
	}

	public String getStime() {
		return this.stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return this.etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getLessonInfo() {
		return this.lessonInfo;
	}

	public void setLessonInfo(String lessonInfo) {
		this.lessonInfo = lessonInfo;
	}

	public String getPurl() {
		return this.purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getJoinStatus() {
		return this.joinStatus;
	}

	public void setJoinStatus(String joinStatus) {
		this.joinStatus = joinStatus;
	}

	public String getAllCommFlag() {
		return this.allCommFlag;
	}

	public void setAllCommFlag(String allCommFlag) {
		this.allCommFlag = allCommFlag;
	}

	public String getCommentFlag() {
		return this.commentFlag;
	}

	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}
}