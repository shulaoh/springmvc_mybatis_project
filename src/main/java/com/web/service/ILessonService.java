package com.web.service;

import com.web.data.pojo.Lession;
import com.web.utils.Page;
import java.util.List;

public abstract interface ILessonService
{
  public abstract List<Lession> getLessons(Page paramPage, String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract List<Lession> selectLessionsByUserIdListPage(Page paramPage, String paramString);

  public abstract int joinLesson(String paramString1, String paramString2);

  public abstract int updateLessonStatus(int paramInt, String paramString);

  public abstract Lession selectLessionById(String paramString1, String paramString2);

  public abstract int insertLess(Lession paramLession);

  public abstract int insertLessStu(String paramString1, String paramString2);

  public abstract int updateByPrimaryKeySelective(Lession paramLession);
}