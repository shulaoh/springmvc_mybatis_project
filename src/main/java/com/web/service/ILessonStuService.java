package com.web.service;

import com.web.data.pojo.LessionStu;
import com.web.utils.Page;
import java.util.List;

public abstract interface ILessonStuService
{
  public abstract List<LessionStu> getLessonStus(Page paramPage, String paramString1, String paramString2, String paramString3);

  public abstract int lessonStuApprove(String paramString1, String paramString2, int paramInt, String paramString3);

  public abstract int deleteLessonStu(String paramString);
}