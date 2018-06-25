package com.web.service;

import com.web.data.mapper.LessonMapper;
import com.web.data.mapper.LessonStuMapper;
import com.web.data.pojo.LessionStu;
import com.web.utils.Page;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("lessonStuService")
public class LessonStuServiceImpl
  implements ILessonStuService
{

  @Resource
  private LessonStuMapper lessonStuMapper;

  @Resource
  private LessonMapper lessonMapper;

  public List<LessionStu> getLessonStus(Page page, String lessonId, String stuStatus, String userId)
  {
    HashMap map = new HashMap();
    map.put("page", page);
    map.put("lessonId", lessonId);
    if (!(stuStatus.equalsIgnoreCase("ALL")))
    {
      map.put("stuStatus", stuStatus);
    }
    List list = this.lessonStuMapper.selectLessionStusListPage(map);
    for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { LessionStu temp = (LessionStu)localIterator.next();

      map.put("lessonId", temp.getLessonId());
      map.put("targetType", "STU");
      map.put("targetId", temp.getUserId());
      map.put("userId", userId);
      int commentFlag = this.lessonMapper.getcommentFlag(map);
      if (commentFlag > 0)
      {
        temp.setHaveCommFlag("Y");
      } else {
    	  
    	  temp.setHaveCommFlag("N");
      }

    }

    return list;
  }

  public int lessonStuApprove(String userId, String lessonId, int oprType, String rejReason)
  {
    HashMap map = new HashMap();
    map.put("lessonId", lessonId);
    map.put("userId", userId);
    if (oprType == 0)
    {
      map.put("status", "CONF");
    }
    else if (oprType == 1)
    {
      map.put("status", "REJ");
      if (rejReason != null)
      {
        map.put("rejReason", rejReason);
      }
    }
    return this.lessonStuMapper.updateLessonStu(map);
  }

  public int deleteLessonStu(String lessId)
  {
    HashMap map = new HashMap();
    map.put("lessonId", lessId);
    return this.lessonStuMapper.deleteLessonStu(map);
  }
}