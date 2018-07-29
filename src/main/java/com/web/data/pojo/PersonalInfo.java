package com.web.data.pojo;

import java.util.Date;

public class PersonalInfo {
    private String personId;

    private String userId;

    private String phone;

    private String email;

    private String userName;

    private Boolean psex;

    private Date pbirthday;
    
    private String companyId;

    private String dept;

    private String subdept;

    private String pposition;

    private String remark;
    
    private String education;

    public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Boolean getPsex() {
        return psex;
    }

    public void setPsex(Boolean psex) {
        this.psex = psex;
    }

    public Date getPbirthday() {
        return pbirthday;
    }

    public void setPbirthday(Date pbirthday) {
        this.pbirthday = pbirthday;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getSubdept() {
        return subdept;
    }

    public void setSubdept(String subdept) {
        this.subdept = subdept == null ? null : subdept.trim();
    }

    public String getPposition() {
        return pposition;
    }

    public void setPposition(String pposition) {
        this.pposition = pposition == null ? null : pposition.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}