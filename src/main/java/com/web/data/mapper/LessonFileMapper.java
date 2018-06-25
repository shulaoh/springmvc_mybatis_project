package com.web.data.mapper;

import com.web.data.pojo.LessionFile;
import java.util.HashMap;
import java.util.List;

public abstract interface LessonFileMapper
{
  public abstract int insert(LessionFile paramLessionFile);

  public abstract List<LessionFile> selectLessonFileListPage(HashMap<String, Object> paramHashMap);
}