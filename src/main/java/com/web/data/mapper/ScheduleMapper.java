package com.web.data.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.data.bean.SchRemainInfo;
import com.web.data.bean.ScheduleInfo;
import com.web.data.pojo.FileContent;
import com.web.data.pojo.Schedule;

public abstract interface ScheduleMapper
{
  public abstract List<Schedule> selectSchedulesListPage(HashMap<String, Object> paramHashMap);

  public abstract int updateByPrimaryKeySelective(Schedule paramSchedule);

  public abstract int insert(Schedule paramSchedule);

  public abstract Schedule getSingleScheduleByScheduleId(String paramString);

  List<FileContent> getFileContentListByScheduleId(String scheduleId);
  
  public int deleteScheduleById(String scheduleId);
  
  public List<Schedule> getSchedulesListByLessonId(HashMap<String, Object> paramHashMap);
  
  List<ScheduleInfo> getScheduleByTeaIds(List<String> teaIds);
  
  List<SchRemainInfo> getRemainInfo(@Param("startTime") String startTime, @Param("startTime") String endTime);
  
  int hasSchModRole(@Param("userId") String userId, @Param("lessonId") String lessonId);

  String getLessonIdByScheduleId(String scheduleId);

  int getScheduleCountByLessonId(String lessonId);

}