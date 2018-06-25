package com.web.service;

import com.web.data.pojo.FileContent;
import com.web.data.pojo.LessonCommon;
import com.web.data.pojo.Schedule;
import com.web.data.pojo.SysUser;
import com.web.utils.Page;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ILessonManageService {
    List<LessonCommon> getLessonListByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    LessonCommon getLessonByLessonId(String lessonId);

    List<LessonCommon> getLessonList(String lessonId, String userId, int offset, int size);

    Map<String, Object> deleteLesson(String lessonId);

    List<Schedule> getSchedule(String lessonId, String scheduleId);

    int putLesson(LessonCommon lesson);

    int getLessonsCount(String lessonId, String userId);

	List<LessonCommon> getLessonList(String searchKey, String userId, Page page);

	java.sql.Timestamp getLessonStartDatetime(String lessonId);

    int getLessonStatus(String lessonId);

    List<FileContent> getFilesByLessonId(Map<String, Object> map);

    int getFilesCountByLessonId(Map<String, Object> map);
}
