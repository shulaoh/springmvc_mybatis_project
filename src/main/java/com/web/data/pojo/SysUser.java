package com.web.data.pojo;

public class SysUser
{
  private String userId;
  private String userName;
  private String password;
  private String sign;
  private String adminFlag;
  private String ustatus;
  private String email;
  private String companyId;
  private String companyName;
  private String dept;
  private String phone;
  private String timeStamp;
  private String userPicUrl;
  private String wechatId;
  private String remark;

  public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public String getWechatId()
  {
    return this.wechatId;
  }

  public void setWechatId(String wechatId) {
    this.wechatId = wechatId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSign() {
    return this.sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getAdminFlag() {
    return this.adminFlag;
  }

  public void setAdminFlag(String adminFlag) {
    this.adminFlag = adminFlag;
  }

  public String getUstatus() {
    return this.ustatus;
  }

  public void setUstatus(String ustatus) {
    this.ustatus = ustatus;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDept() {
    return this.dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getTimeStamp() {
    return this.timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getUserPicUrl() {
    return this.userPicUrl;
  }

  public void setUserPicUrl(String userPicUrl) {
    this.userPicUrl = userPicUrl;
  }
}