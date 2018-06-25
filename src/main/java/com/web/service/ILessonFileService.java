package com.web.service;

import com.web.data.pojo.LessionFile;
import com.web.utils.Page;
import java.util.List;

public abstract interface ILessonFileService
{
  public abstract List<LessionFile> selectLessonFileListPage(Page paramPage, String paramString1, String paramString2);

  public abstract int insert(LessionFile paramLessionFile);
}