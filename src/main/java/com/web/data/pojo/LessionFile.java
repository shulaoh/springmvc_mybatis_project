package com.web.data.pojo;

public class LessionFile
{
  String lessonId;
  String url;
  String intro;
  String name;
  String schName;
  String schId;

  public String getLessonId()
  {
    return this.lessonId; }

  public void setLessonId(String lessonId) {
    this.lessonId = lessonId; }

  public String getUrl() {
    return this.url; }

  public void setUrl(String url) {
    this.url = url; }

  public String getIntro() {
    return this.intro; }

  public void setIntro(String intro) {
    this.intro = intro; }

  public String getName() {
    return this.name; }

  public void setName(String name) {
    this.name = name; }

  public String getSchName() {
    return this.schName; }

  public void setSchName(String schName) {
    this.schName = schName; }

  public String getSchId() {
    return this.schId; }

  public void setSchId(String schId) {
    this.schId = schId;
  }
}