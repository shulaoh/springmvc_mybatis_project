package com.web.data.mapper;

import java.util.List;

import com.web.data.pojo.PersonalInfo;

public interface PersonalInfoMapper {
    int deleteByPrimaryKey(String personId);

    int insert(PersonalInfo record);

    int insertSelective(PersonalInfo record);

    PersonalInfo selectByPrimaryKey(String personId);

    int updateByPrimaryKeySelective(PersonalInfo record);
    
    int updateByUserIdSelective(PersonalInfo record);

    int updateByPrimaryKey(PersonalInfo record);

	int insertBatch(List<PersonalInfo> createPInfoList);
}