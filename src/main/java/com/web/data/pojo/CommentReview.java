package com.web.data.pojo;

import java.util.Date;

public class CommentReview {

    private String lessonId;

    private String scheduleId;

    private String userId;

    private String commentCategory;

    private int score;

    private String comment;

    private int commentType;

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    private Date commentDatetime;

    private LessonCommon lesson;

    private Schedule schedule;

    private SysUser user;

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentCategory() {
        return commentCategory;
    }

    public void setCommentCategory(String comentCategory) {
        this.commentCategory = comentCategory;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(Date commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    public LessonCommon getLesson() {
        return lesson;
    }

    public void setLesson(LessonCommon lesson) {
        this.lesson = lesson;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
