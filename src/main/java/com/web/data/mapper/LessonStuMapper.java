package com.web.data.mapper;

import com.web.data.pojo.LessionStu;
import java.util.HashMap;
import java.util.List;

public abstract interface LessonStuMapper
{
  public abstract List<LessionStu> selectLessionStusListPage(HashMap<String, Object> paramHashMap);

  public abstract int updateLessonStu(HashMap<String, Object> paramHashMap);

  public abstract int deleteLessonStu(HashMap<String, Object> paramHashMap);
}