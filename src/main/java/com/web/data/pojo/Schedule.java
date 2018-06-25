package com.web.data.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Schedule {
	String lessonId;
	String schId;
	String schName;
	String schPlace;
	String schIntro;
	String ssTime;
	String schLastMNum;
	String signFlag;
	String signSTime;
	String signETime;
	String commentFlag;
	String allCommFlag;
	String haveCommFlag;
	String haveSign;
	String haveLeave;
	String tutorId;
	String commTempIds;
	  private Timestamp createTime;
	  private Timestamp updateTime;
	
	private Teacher tutor;

	private List<FileContent> fileContentList;

	private List<SysUser> teachers;

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<SysUser> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<SysUser> teachers) {
		this.teachers = teachers;
	}

	public List<FileContent> getFileContentList() {
		return fileContentList;
	}

	public void setFileContentList(List<FileContent> fileContentList) {
		this.fileContentList = fileContentList;
	}

	public String getCommTempIds() {
		return this.commTempIds;
	}

	public void setCommTempIds(String commTempIds) {
		this.commTempIds = commTempIds;
	}

	public String getHaveLeave() {
		return this.haveLeave;
	}

	public void setHaveLeave(String haveLeave) {
		this.haveLeave = haveLeave;
	}

	public String getTutorId() {
		return this.tutorId;
	}

	public void setTutorId(String tutorId) {
		this.tutorId = tutorId;
	}

	public Teacher getTutor() {
		return this.tutor;
	}

	public void setTutor(Teacher tutor) {
		this.tutor = tutor;
	}

	public String getHaveSign() {
		return this.haveSign;
	}

	public void setHaveSign(String haveSign) {
		this.haveSign = haveSign;
	}

	public String getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public String getSchId() {
		return this.schId;
	}

	public void setSchId(String schId) {
		this.schId = schId;
	}

	public String getSchName() {
		return this.schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	public String getSchIntro() {
		return this.schIntro;
	}

	public void setSchIntro(String schIntro) {
		this.schIntro = schIntro;
	}

	public String getSchLastMNum() {
		return this.schLastMNum;
	}

	public void setSchLastMNum(String schLastMNum) {
		this.schLastMNum = schLastMNum;
	}

	public String getSignFlag() {
		return this.signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	public String getSsTime() {
		return this.ssTime;
	}

	public void setSsTime(String ssTime) {
		this.ssTime = ssTime;
	}

	public String getSignSTime() {
		return this.signSTime;
	}

	public void setSignSTime(String signSTime) {
		this.signSTime = signSTime;
	}

	public String getSignETime() {
		return this.signETime;
	}

	public void setSignETime(String signETime) {
		this.signETime = signETime;
	}

	public String getCommentFlag() {
		return this.commentFlag;
	}

	public void setCommentFlag(String commentFlag) {
		this.commentFlag = commentFlag;
	}

	public String getAllCommFlag() {
		return this.allCommFlag;
	}

	public void setAllCommFlag(String allCommFlag) {
		this.allCommFlag = allCommFlag;
	}

	public String getHaveCommFlag() {
		return this.haveCommFlag;
	}

	public void setHaveCommFlag(String haveCommFlag) {
		this.haveCommFlag = haveCommFlag;
	}

	public String getSchPlace() {
		return this.schPlace;
	}

	public void setSchPlace(String schPlace) {
		this.schPlace = schPlace;
	}
}