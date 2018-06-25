package com.web.service;

import java.util.List;

import com.web.data.pojo.Company;
import com.web.data.pojo.PersonalInfo;

public abstract interface IPersonalService
{

	int updateByPrimaryKeySelective(PersonalInfo pInfo);
	
	int insert(PersonalInfo personalInfo);
	
	PersonalInfo getUserById(String pId);
	
	PersonalInfo getUserByUserId(String uId);
	
	List<Company> getAllCompany();
}