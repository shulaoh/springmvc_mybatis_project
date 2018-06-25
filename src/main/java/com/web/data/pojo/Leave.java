package com.web.data.pojo;

public class Leave
{
  private String userId;
  private String schId;
  private String time;
  private String cancelFlag;
  private String reason;
  private Teacher user;
  private String remark;

  public Teacher getUser()
  {
    return this.user; }

  public void setUser(Teacher user) {
    this.user = user; }

  public String getReason() {
    return this.reason; }

  public void setReason(String reason) {
    this.reason = reason; }

  public String getUserId() {
    return this.userId; }

  public void setUserId(String userId) {
    this.userId = userId; }

  public String getSchId() {
    return this.schId; }

  public void setSchId(String schId) {
    this.schId = schId; }

  public String getTime() {
    return this.time; }

  public void setTime(String time) {
    this.time = time; }

  public String getCancelFlag() {
    return this.cancelFlag; }

  public void setCancelFlag(String cancelFlag) {
    this.cancelFlag = cancelFlag; }

  public String getRemark() {
    return this.remark; }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}