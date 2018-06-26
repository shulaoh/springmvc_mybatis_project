package com.web.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.data.bean.CommImportItem;
import com.web.data.pojo.TCommTemplateGroup;

public interface TCommTemplateGroupMapper {
    int deleteByPrimaryKey(String tempGroupId);

    int insert(TCommTemplateGroup record);

    int insertSelective(TCommTemplateGroup record);

    TCommTemplateGroup selectByPrimaryKey(String tempGroupId);

    int updateByPrimaryKeySelective(TCommTemplateGroup record);

    int updateByPrimaryKey(TCommTemplateGroup record);
    
    int insertBatch(List<TCommTemplateGroup> records);

	List<TCommTemplateGroup> selectAll(@Param("tempGroupOwerId") String tempGroupOwerId);
	
	List<CommImportItem> selectCommGroupItem(@Param("tempGroupId") String tempGroupId);
}