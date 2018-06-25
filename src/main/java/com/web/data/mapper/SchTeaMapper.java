package com.web.data.mapper;

import java.util.List;

import com.web.data.pojo.SchTeaLink;
import com.web.data.pojo.SchTeaLinkKey;

public interface SchTeaMapper {
    int deleteByPrimaryKey(SchTeaLinkKey key);

    int insert(SchTeaLink record);

    int insertSelective(SchTeaLink record);

    SchTeaLink selectByPrimaryKey(SchTeaLinkKey key);

    int updateByPrimaryKeySelective(SchTeaLink record);

    int updateByPrimaryKey(SchTeaLink record);

	void deleteBySchId(String schId);

	void insertBatch(List<SchTeaLink> stLinkList);
}