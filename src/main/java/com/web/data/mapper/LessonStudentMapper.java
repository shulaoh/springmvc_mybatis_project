package com.web.data.mapper;

import com.web.data.pojo.LessonStudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonStudentMapper {
    void insertLessonStudent(LessonStudent student);
    void deleteLessonStudent(@Param("lessonId") String lessonId, @Param("userId") String userId);
    void deleteLessonStudentNotInList(@Param("lessonId") String lessonId, @Param("list") List<String> list);
}
