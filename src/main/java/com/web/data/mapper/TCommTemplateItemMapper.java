package com.web.data.mapper;

import java.util.HashMap;
import java.util.List;

import com.web.data.bean.CommType;
import com.web.data.pojo.Comment;
import com.web.data.pojo.TCommTemplateItem;

public interface TCommTemplateItemMapper {
    int deleteByPrimaryKey(String tempItemId);

    int insert(TCommTemplateItem record);

    int insertSelective(TCommTemplateItem record);

    TCommTemplateItem selectByPrimaryKey(String tempItemId);

    int updateByPrimaryKeySelective(TCommTemplateItem record);

    int updateByPrimaryKey(TCommTemplateItem record);
    
    /**
     * 后台WEB获取当前添加/修改的的课程和日程的评价可关联的评价模板列表
     * @param paramHashMap
     * @return
     */
    List<TCommTemplateItem> selectCommTemps(HashMap<String, String> paramHashMap);
    /**
     * 小程序获取当前用户针对指定评价对象的评价类型信息,指导小程序根据模板生成评价页面
     * @param paramHashMap
     * @return
     */
    List<CommType> selectCommTypes(HashMap<String, String> paramHashMap);
    
    int insertBatch(List<TCommTemplateItem> records);
}