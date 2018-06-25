package com.web.data.pojo;

public class Sign
{
  private String userId;
  private String lessonId;
  private String schId;
  private String sTime;
  private String signPicUrl;
  private SysUser user;

  public String getUserId()
  {
    return this.userId; }

  public void setUserId(String userId) {
    this.userId = userId; }

  public String getLessonId() {
    return this.lessonId; }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId; }

  public String getSchId() {
    return this.schId; }

  public void setSchId(String schId) {
    this.schId = schId; }

  public String getsTime() {
    return this.sTime; }

  public void setsTime(String sTime) {
    this.sTime = sTime; }

  public String getSignPicUrl() {
    return this.signPicUrl; }

  public void setSignPicUrl(String signPicUrl) {
    this.signPicUrl = signPicUrl; }

  public SysUser getUser() {
    return this.user; }

  public void setUser(SysUser user) {
    this.user = user;
  }
}