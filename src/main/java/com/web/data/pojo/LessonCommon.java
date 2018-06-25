package com.web.data.pojo;

import org.apache.poi.poifs.crypt.dsig.services.TimeStampService;

import com.web.utils.Tools;

import java.sql.Timestamp;
import java.util.List;

public class LessonCommon extends Lession {

    public List<SysUser> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<SysUser> studentList) {
        this.studentList = studentList;
    }

    private List<SysUser> studentList;

    public List<SysUser> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<SysUser> adminList) {
        this.adminList = adminList;
    }

    private List<SysUser> adminList;

    private Timestamp startDateTime;

    private Timestamp endDateTime;

    private String teacherName;


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
        this.setStime(Tools.date2Str(startDateTime, "yyyy-MM-dd HH:mm"));
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
        this.setEtime(Tools.date2Str(endDateTime, "yyyy-MM-dd HH:mm"));
    }
}
