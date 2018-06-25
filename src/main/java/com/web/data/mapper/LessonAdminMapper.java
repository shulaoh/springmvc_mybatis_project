package com.web.data.mapper;

import com.web.data.pojo.LessonAdmin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LessonAdminMapper {
    void insertLessonAdmin(LessonAdmin admin);
    void deleteLessonAdmin(@Param("lessonId") String lessonId, @Param("userId") String userId);
    void deleteLessonAdminNotInList(@Param("lessonId") String lessonId, @Param("list") List<String> list);
}
