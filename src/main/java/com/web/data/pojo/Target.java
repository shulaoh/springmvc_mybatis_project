package com.web.data.pojo;

public class Target
{
  private String tempId;
  private String typeKey;
  private String CommTypeName;
  private String roleType;
  private String targetType;

  public String getTempId()
  {
    return this.tempId; }

  public void setTempId(String tempId) {
    this.tempId = tempId; }

  public String getTypeKey() {
    return this.typeKey; }

  public void setTypeKey(String typeKey) {
    this.typeKey = typeKey; }

  public String getCommTypeName() {
    return this.CommTypeName; }

  public void setCommTypeName(String commTypeName) {
    this.CommTypeName = commTypeName; }

  public String getRoleType() {
    return this.roleType; }

  public void setRoleType(String roleType) {
    this.roleType = roleType; }

  public String getTargetType() {
    return this.targetType; }

  public void setTargetType(String targetType) {
    this.targetType = targetType;
  }
}