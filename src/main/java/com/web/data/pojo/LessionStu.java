package com.web.data.pojo;

public class LessionStu
{
  String userId;
  String lessonId;
  String name;
  String dept;
  String phone;
  String email;
  String stuStatus;
  String rejReason;
  String haveCommFlag;

  public String getHaveCommFlag()
  {
    return this.haveCommFlag; }

  public void setHaveCommFlag(String haveCommFlag) {
    this.haveCommFlag = haveCommFlag; }

  public String getRejReason() {
    return this.rejReason; }

  public void setRejReason(String rejReason) {
    this.rejReason = rejReason; }

  public String getUserId() {
    return this.userId; }

  public void setUserId(String userId) {
    this.userId = userId; }

  public String getLessonId() {
    return this.lessonId; }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId; }

  public String getName() {
    return this.name; }

  public void setName(String name) {
    this.name = name; }

  public String getDept() {
    return this.dept; }

  public void setDept(String dept) {
    this.dept = dept; }

  public String getPhone() {
    return this.phone; }

  public void setPhone(String phone) {
    this.phone = phone; }

  public String getEmail() {
    return this.email; }

  public void setEmail(String email) {
    this.email = email; }

  public String getStuStatus() {
    return this.stuStatus; }

  public void setStuStatus(String stuStatus) {
    this.stuStatus = stuStatus;
  }
}