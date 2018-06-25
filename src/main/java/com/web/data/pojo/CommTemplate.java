package com.web.data.pojo;

import java.util.List;

public class CommTemplate
{
  private String tempId;
  private String tempName;
  private String tempInfo;
  private String roleType;
  private String targetType;
  private List<Target> tagets;

  public String getTempId()
  {
    return this.tempId; }

  public void setTempId(String tempId) {
    this.tempId = tempId; }

  public String getTempName() {
    return this.tempName; }

  public void setTempName(String tempName) {
    this.tempName = tempName; }

  public String getTempInfo() {
    return this.tempInfo; }

  public void setTempInfo(String tempInfo) {
    this.tempInfo = tempInfo; }

  public String getRoleType() {
    return this.roleType; }

  public void setRoleType(String roleType) {
    this.roleType = roleType; }

  public String getTargetType() {
    return this.targetType; }

  public void setTargetType(String targetType) {
    this.targetType = targetType; }

  public List<Target> getTagets() {
    return this.tagets; }

  public void setTagets(List<Target> tagets) {
    this.tagets = tagets;
  }
}