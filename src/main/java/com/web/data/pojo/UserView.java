package com.web.data.pojo;

import java.util.Date;

public class UserView extends SysUser{
    private String userId;

    private String wechatId;

    private String phone;

    private String email;

    private String userName;

    private String password;

    private String dept;
    
    private String companyId;
    private String companyName;

    private String timeStamp;

    private String adminFlag;

    private String sign;

    private String uStatus;

    private String userPicUrl;

    private String remark;

    private String remark1;

    private String personId;
    
    private String education;
    private String pposition;
    private Date pbirthday;
    
    /**值来源于adminFlag 只有当是班主任时为TEA*/
    private String role;

    public String getRole() {
		if (role == null) {
			return adminFlag;
		}
    	return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

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

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


    public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp == null ? null : timeStamp.trim();
    }

    public String getAdminFlag() {
        return adminFlag;
    }

    public void setAdminFlag(String adminFlag) {
        this.adminFlag = adminFlag == null ? null : adminFlag.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus == null ? null : uStatus.trim();
    }

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl == null ? null : userPicUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId == null ? null : personId.trim();
    }

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getPposition() {
		return pposition;
	}

	public void setPposition(String pposition) {
		this.pposition = pposition;
	}

	public Date getPbirthday() {
		return pbirthday;
	}

	public void setPbirthday(Date pbirthday) {
		this.pbirthday = pbirthday;
	}
}