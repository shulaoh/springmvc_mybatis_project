package com.web.service;

import com.web.data.pojo.Schedule;
import com.web.utils.Page;

import java.util.List;
import java.util.Map;

public abstract interface IScheduleService {
	public abstract List<Schedule> selectSchedulesListPage(Page paramPage, String paramString1, String paramString2);

	public abstract int updateByPrimaryKeySelective(Schedule paramSchedule);

	public abstract int insert(Schedule paramSchedule);

	public abstract Schedule getSingleScheduleByScheduleId(String paramString);

	public Map<String, Object> putSchedule(Schedule schedule);

	public int deleteSchedule(String scheduleId);
	
	boolean hasSchModRole(String userId, String lessId);

    String getLessonIdByScheduleId(String scheduleId);

    int getScheduleCountByLessonId(String lessonId);


}