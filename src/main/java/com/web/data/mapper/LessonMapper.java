package com.web.data.mapper;

import com.web.data.pojo.Lession;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public abstract interface LessonMapper
{
  public abstract List<Lession> selectMyStuLessionsListPage(HashMap<String, Object> paramHashMap);

  public abstract List<Lession> selectAllLessionsListPage(HashMap<String, Object> paramHashMap);

  public abstract List<Lession> selectLessionsByStuTypeListPage(HashMap<String, Object> paramHashMap);

  public abstract List<Lession> selectLessionsByTEATypeListPage(HashMap<String, Object> paramHashMap);

  List<Lession> selectLessonByAdminId(HashMap<String,Object> hashMap);

  List<Lession> selectAllLessonByUserId(HashMap<String, Object> hashmap);

  public abstract List<Lession> selectLessionsByUserIdListPage(HashMap<String, Object> paramHashMap);

  public abstract int getcommentFlag(HashMap<String, Object> paramHashMap);

  public abstract Lession selectLessionById(HashMap<String, Object> paramHashMap);

  public abstract int updateLessonStatus(Lession lesson);

  public abstract int updateByPrimaryKeySelective(Lession paramLession);

  public abstract String getLessonStuStatus(HashMap<String, Object> paramHashMap);

  public abstract int insertLessonStuStatus(HashMap<String, Object> paramHashMap);

  public abstract int updateLessonStuStatus(HashMap<String, Object> paramHashMap);

  public abstract int insertLess(Lession paramLession);

  public abstract int insertLessStu(HashMap<String, Object> paramHashMap);

  public abstract int countHaveLeave(HashMap<String, Object> paramHashMap);
  
  String getCurUserRole(@Param("userId") String userId, @Param("lessonId") String lessonId,@Param("schId") String schId);
}