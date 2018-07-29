package com.web.service;

import java.util.List;

import com.web.data.bean.SchStuInfo;
import com.web.data.pojo.LessionStu;
import com.web.utils.Page;

public abstract interface ILessonStuService
{
  public abstract List<LessionStu> getLessonStus(Page paramPage, String paramString1, String paramString2, String paramString3);

  public abstract int lessonStuApprove(String paramString1, String paramString2, int paramInt, String paramString3);

  public abstract int deleteLessonStu(String paramString);

public abstract List<SchStuInfo> getStuListBySch(String schId, Page page);
}