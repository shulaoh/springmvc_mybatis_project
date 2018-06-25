package com.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.data.mapper.CompanyMapper;
import com.web.data.mapper.PersonalInfoMapper;
import com.web.data.pojo.Company;
import com.web.data.pojo.PersonalInfo;
@Service("personalService")
public class PersonalServiceImpl implements IPersonalService {

	@Resource
	private PersonalInfoMapper pInfoMapper;
	
	@Resource
	private CompanyMapper companyMapper;
	@Override
	public int updateByPrimaryKeySelective(PersonalInfo pInfo) {
		return pInfoMapper.updateByPrimaryKeySelective(pInfo);
	}

	@Override
	public int insert(PersonalInfo personalInfo) {
		return pInfoMapper.insert(personalInfo);
	}

	@Override
	public PersonalInfo getUserById(String pId) {
		// TODO Auto-generated method stub
		return pInfoMapper.selectByPrimaryKey(pId);
	}

	@Override
	public PersonalInfo getUserByUserId(String uId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getAllCompany() {
		// TODO Auto-generated method stub
		return companyMapper.selectAll();
	}

	
}
