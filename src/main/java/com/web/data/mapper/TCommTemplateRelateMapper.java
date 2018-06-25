package com.web.data.mapper;

import java.util.List;

import com.web.data.pojo.TCommTemplateRelate;

public interface TCommTemplateRelateMapper {
    int deleteByPrimaryKey(String tempRelateId);

    int insert(TCommTemplateRelate record);

    int insertSelective(TCommTemplateRelate record);

    TCommTemplateRelate selectByPrimaryKey(String tempRelateId);

    int updateByPrimaryKeySelective(TCommTemplateRelate record);

    int updateByPrimaryKey(TCommTemplateRelate record);
    
    int insertBatch(List<TCommTemplateRelate> records);
}