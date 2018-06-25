package com.web.controller;

import com.web.data.pojo.LessionFile;
import com.web.service.ILessonFileService;
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
public class LessonFileController
{

  @Resource
  private ILessonFileService lessonFileService;
  private static Logger logger = Logger.getLogger(LessonFileController.class);

  @RequestMapping({"/getLessonFile"})
  @ResponseBody
  public Map<String, Object> getLessonFile(@RequestParam String lessonId, String schid, @RequestParam Integer showCount, @RequestParam Integer pageNum)
  {
    String tid = "tid_get_lesson_file";
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
      data.put("lessonFiles", this.lessonFileService.selectLessonFileListPage(page, lessonId, schid));
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
      result.setRetmsg("获取课程资料失败");
    }
    return map;
  }

  @RequestMapping({"/addLessFile"})
  @ResponseBody
  public Map<String, Object> addLessFile(@RequestParam String lessonId, String schId, @RequestParam String url, @RequestParam String name, String intro, HttpSession session)
  {
    String tid = "tid_add_less_file";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    if (schId == null) schId = "";
    LessionFile file = new LessionFile();
    file.setIntro(intro);
    file.setLessonId(lessonId);
    file.setName(name);
    file.setUrl("https://lpg.tianmengit.com/cnooc_training/image/" + url);
    file.setSchId(schId);
    try
    {
      int ret = this.lessonFileService.insert(file);
      if (ret > 0)
      {
        result.setRetcode(1);
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("附件记录错误");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("附件记录错误" + e);
    }
    return map;
  }
}