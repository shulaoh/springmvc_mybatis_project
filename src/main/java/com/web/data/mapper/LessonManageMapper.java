package com.web.data.mapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.data.pojo.FileContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.web.data.pojo.LessonCommon;
import com.web.data.pojo.Schedule;
import com.web.data.pojo.SysUser;
import com.web.utils.Page;

@Repository
public interface LessonManageMapper {

    List<LessonCommon> getLessonListByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("size") int size);

    LessonCommon getLessonByLessonId(String lessonId);

    int deleteLesson(String lessonId);

    List<SysUser> getStudentListByLessonId(String lessonId);

    List<SysUser> getAdminListByLessonId(String lessonId);

    Schedule getScheduleByScheduleId(String scheduleId);

    List<Schedule> getScheduleByLessonId(String lessonId);

    Timestamp getStartDateByLessonId(String lessonId);

    Timestamp getEndDatetimeByLessonId(String lessonId);

    void insertLesson(LessonCommon lesson);

    void updateLesson(LessonCommon lesson);

    int getLessonsCount(@Param("userId") String userId);

    SysUser getTeacherByLessonId(String lessonId);

    List<LessonCommon> getLessonsStartTomorrow();

    List<LessonCommon> getLessonsStart2Hours();

    int getLessonStatus(String lessonId);
    
    List<LessonCommon>searchByRoleListPage(@Param("userId")String userId, @Param("searchKey")String searchKey, @Param("page")Page page);

    List<FileContent> getFilesByLessonId(Map<String, Object> paramHashMap);

    int getFilesCountByLessonId(Map<String, Object> paramHashMap);

}
