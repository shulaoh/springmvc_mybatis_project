package com.web.data.mapper;

import java.util.List;

import com.web.data.pojo.AccessToken;

public interface AccessTokenMapper {
    int deleteByPrimaryKey(String id);

    int insert(AccessTokenMapper record);

    int insertSelective(AccessTokenMapper record);

    AccessTokenMapper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccessTokenMapper record);

    int updateByPrimaryKey(AccessTokenMapper record);
    
    List<AccessToken> selectAccessToken();
}