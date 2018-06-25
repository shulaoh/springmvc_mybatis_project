package com.web.data.mapper;

import java.util.List;
import java.util.Map;

import com.web.data.bean.CommentList;
import com.web.data.pojo.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(String commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(String commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
    
    List<CommentList> selectCommentsListPage(Map params);
    
    int insertBatch(List<Comment> records);
}