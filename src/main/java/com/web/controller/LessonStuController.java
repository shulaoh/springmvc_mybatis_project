package com.web.controller;

import com.web.data.pojo.SysUser;
import com.web.service.ILessonStuService;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LessonStuController
{

  @Resource
  private ILessonStuService lessonStuService;
  private static Logger logger = Logger.getLogger(LessonStuController.class);

  @RequestMapping({"/getLessonStu"})
  @ResponseBody
  public Map<String, Object> getLessonStu(@RequestParam String lessonId, @RequestParam String stuStatus, @RequestParam Integer showCount, @RequestParam Integer pageNum, HttpSession session)
  {
    String tid = "tid_get_lesson_stu";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    Page page = new Page();
    page.setCurrentPage(pageNum.intValue());
    page.setShowCount(showCount.intValue());
    try
    {
      DataDesc datadesc = new DataDesc();
      HashMap data = new HashMap();
      data.put("lessonStus", this.lessonStuService.getLessonStus(page, lessonId, stuStatus, ((SysUser)session.getAttribute("userSession")).getUserId()));
      boolean paging = page.caculatePageing();
      datadesc.setPaging(paging);
      datadesc.setPage(page);
      map.put("datadesc", datadesc);
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取课程学员信息失败");
    }
    return map;
  }

  @RequestMapping({"/lessonStuApprove"})
  @ResponseBody
  public Map<String, Object> lessonStuApprove(@RequestParam String lessonId, @RequestParam String userId, @RequestParam int oprType, String rejReason)
  {
    String tid = "tid_lesson_stu_approve";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    if (this.lessonStuService.lessonStuApprove(userId, lessonId, oprType, rejReason) > 0)
    {
      result.setRetcode(1);
    }
    else
    {
      result.setRetcode(-1);
      result.setRetmsg("修改个人信息失败");
    }
    return map;
  }
}