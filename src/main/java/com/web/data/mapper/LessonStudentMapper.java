package com.web.data.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.web.data.bean.SchStuInfo;
import com.web.data.pojo.LessonStudent;
import com.web.utils.Page;

@Repository
public interface LessonStudentMapper {
    void insertLessonStudent(LessonStudent student);
    void deleteLessonStudent(@Param("lessonId") String lessonId, @Param("userId") String userId);
    void deleteLessonStudentNotInList(@Param("lessonId") String lessonId, @Param("list") List<String> list);
    void deleteAllLessonStudent(@Param("lessonId") String lessonId);
    
    List<SchStuInfo> getStuListPage(@Param("schId") String schId, @Param("page") Page page);
}
