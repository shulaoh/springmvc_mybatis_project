package com.web.data.mapper;

import com.web.data.pojo.Sign;
import com.web.utils.Page;
import com.web.data.bean.SignInfo;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public abstract interface SignMapper
{
  public abstract int insert(Sign paramSign);

  public abstract int getSignFlag(HashMap<String, Object> paramHashMap);

  List<SignInfo> selectSignListPage(@Param("lessId") String lessId, @Param("schId") String schId, @Param("page") Page page);
  
  List<SignInfo> selectLessSignList(@Param("lessId") String lessId);

  SignInfo getSignByScheduleIdForCurrentUser(@Param("lessonId") String lessonId, @Param("scheduleId") String scheduleId, @Param("userId") String userId);
}