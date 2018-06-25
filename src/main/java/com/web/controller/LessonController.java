package com.web.controller;

import com.web.data.pojo.Lession;
import com.web.data.pojo.SysUser;
import com.web.service.ILessonService;
import com.web.service.ILessonStuService;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.Tools;
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
public class LessonController
{

  @Resource
  private ILessonService lessonService;

  @Resource
  private ILessonStuService lessonStuService;
  private static Logger logger = Logger.getLogger(LessonController.class);

  @RequestMapping({"/getLessons1"})
  @ResponseBody
  public Map<String, Object> getLessons(@RequestParam String lessonType, @RequestParam String oprType, String lessonNameKeyword, @RequestParam Integer showCount, @RequestParam Integer pageNum, HttpSession session)
  {
    String tid = "tid_get_lessons";
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
      data.put("lessons", this.lessonService.getLessons(page, ((SysUser)session.getAttribute("userSession")).getUserId(), oprType, lessonType, lessonNameKeyword));
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
      result.setRetmsg("获取课程列表失败");
    }
    return map;
  }

  @RequestMapping({"/addLesson"})
  @ResponseBody
  public Map<String, Object> addLesson(@RequestParam String lessonName, @RequestParam String lessonType, @RequestParam String place, String lessonInfo, String purl, @RequestParam String teacherId, @RequestParam String creatorId, @RequestParam String allCommFlag, @RequestParam String lessPicUrl, @RequestParam String lessCycPicUrl, String inviUserIds, String commTempIds, HttpSession session)
  {
    String tid = "tid_add_lesson";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    if (purl == null) purl = "";
    if (lessPicUrl == null) lessPicUrl = "DEFAULT_LESS_PIC.PNG";
    if (lessCycPicUrl == null) { lessCycPicUrl = "DEFAULT_LESS_CYC_PIC.PNG";
    }

    String lessId = Tools.generateID();
    Lession less = new Lession();
    less.setLessonId(lessId);
    less.setLessonName(lessonName);
    less.setLessonType(lessonType);

    less.setPlace(place);
    less.setLessonInfo(lessonInfo);
    less.setPurl(purl);
    less.setTeacherId(teacherId);
    less.setCreatorId(creatorId);
    less.setAllCommFlag(allCommFlag);
    less.setLessPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessPicUrl);
    less.setLessCycPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessCycPicUrl);
    less.setCommTempIds(commTempIds);
    try
    {
      int ret = this.lessonService.insertLess(less);
      if (ret > 0)
      {
        if ((inviUserIds != null) && (inviUserIds.trim().length() > 0))
        {
          String[] users = inviUserIds.split(",");
          for (int i = 0; i < users.length; ++i)
          {
            this.lessonService.insertLessStu(lessId, users[i]);
          }
        }
        result.setRetcode(1);
        result.setRetmsg(lessId);
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("添加日程错误");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("添加日程错误" + e);
    }
    return map;
  }

  @RequestMapping({"/updateLesson"})
  @ResponseBody
  public Map<String, Object> updateLesson(String lessonId, String lessonName, String lessonType, String place, String lessonInfo, String purl, String teacherId, String allCommFlag, String lessPicUrl, String lessCycPicUrl, String inviUserIds, String commTempIds, HttpSession session)
  {
    String tid = "tid_update_lesson";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    if (purl == null) purl = "";
    if (lessPicUrl == null) lessPicUrl = "";
    if (lessCycPicUrl == null) lessCycPicUrl = "";

    Lession less = new Lession();
    less.setLessonId(lessonId);
    less.setLessonName(lessonName);
    less.setLessonType(lessonType);

    less.setPlace(place);
    less.setLessonInfo(lessonInfo);
    less.setPurl(purl);
    less.setTeacherId(teacherId);
    less.setAllCommFlag(allCommFlag);
    less.setLessPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessPicUrl);
    less.setLessCycPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessCycPicUrl);
    less.setCommTempIds(commTempIds);
    try
    {
      int ret = this.lessonService.updateByPrimaryKeySelective(less);
      if (ret > 0)
      {
        if ((inviUserIds != null) && (inviUserIds.trim().length() > 0))
        {
          this.lessonStuService.deleteLessonStu(lessonId);
          String[] users = inviUserIds.split(",");
          for (int i = 0; i < users.length; ++i)
          {
            this.lessonService.insertLessStu(lessonId, users[i]);
          }
        }
        result.setRetcode(1);
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("修改课程错误");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("修改课程错误" + e);
    }
    return map;
  }

  @RequestMapping({"/joinLesson1"})
  @ResponseBody
  public Map<String, Object> joinLesson(@RequestParam String lessonId, HttpSession session)
  {
    String tid = "tid_join_lesson";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      String userId = ((SysUser)session.getAttribute("userSession")).getUserId();
      int ret = this.lessonService.joinLesson(userId, lessonId);
      result.setRetcode(ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("参与课程失败");
    }
    return map;
  }

  @RequestMapping({"/getLessonById"})
  @ResponseBody
  public Map<String, Object> getLessonById(@RequestParam String lessonId, String lessonStatus) {
    String tid = "tid_get_lesson_by_id";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    result.setRetcode(1);
    try
    {
      map.put("result", result);
      map.put("datadesc", new DataDesc());
      HashMap data = new HashMap();
      data.put("lesson", this.lessonService.selectLessionById(lessonId, lessonStatus));
      map.put("data", data);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("通过课程ID获取课程信息失败");
    }
    return map;
  }

  @RequestMapping({"/updateLessStatus"})
  @ResponseBody
  public Map<String, Object> updateLessStatus(@RequestParam String lessonId, @RequestParam int lessonStatus)
  {
    String tid = "tid_update_lesson_status";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      int ret = this.lessonService.updateLessonStatus(lessonStatus, lessonId);
      result.setRetcode(ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("修改课程状态失败");
    }
    return map;
  }

  @RequestMapping({"/getAllLessons"})
  @ResponseBody
  public Map<String, Object> getAllLessons(String creatorId, @RequestParam Integer showCount, @RequestParam Integer pageNum)
  {
    String tid = "tid_get_all_lessons";
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
      data.put("lessons", this.lessonService.selectLessionsByUserIdListPage(page, creatorId));
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
      result.setRetmsg("获取课程列表失败");
    }
    return map;
  }
}