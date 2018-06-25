package com.web.controller;

import com.web.data.pojo.Comment;
import com.web.data.pojo.SysUser;
import com.web.service.ICommentService;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.Tools;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class CommentController
{

//  @Resource
//  private ICommentService commentService;
//  private static Logger logger = Logger.getLogger(CommentController.class);
//
//  @RequestMapping({"/getCommTypes"})
//  @ResponseBody
//  public Map<String, Object> getCommTypes(@RequestParam String roleType, @RequestParam String targetType, @RequestParam String commTempIds, HttpSession session)
//  {
//    String tid = "tid_get_commtypes";
//    ResourceDesc rdesc = new ResourceDesc();
//    HashMap map = new HashMap();
//    Result result = new Result();
//    map.put("result", result);
//    DataDesc datadesc = new DataDesc();
//    datadesc.setPaging(false);
//    datadesc.setPage(null);
//    map.put("datadesc", datadesc);
//    try
//    {
//      HashMap data = new HashMap();
//      data.put("targets", this.commentService.selectTargets(roleType, targetType, commTempIds));
//      map.put("data", data);
//      result.setRetcode(1);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//      result.setRetcode(-1);
//      result.setRetmsg("获取评价类型列表失败" + e);
//    }
//    return map;
//  }
//
//  @RequestMapping({"/comment"})
//  @ResponseBody
//  public Map<String, Object> comment(@RequestParam String lessonId, String schId, @RequestParam String targetType, @RequestParam String targetId, @RequestParam String comments, HttpSession session)
//  {
//    String tid = "tid_comment";
//    ResourceDesc rdesc = new ResourceDesc();
//    HashMap map = new HashMap();
//    Result result = new Result();
//    map.put("result", result);
//    String userId = ((SysUser)session.getAttribute("userSession")).getUserId();
//
//    System.out.println("comments=" + comments);
//    String[] tempStr = comments.split("##");
//    System.out.println("length=" + tempStr.length);
//    try
//    {
//      for (int i = 0; i < tempStr.length; ++i)
//      {
//        String[] tempStrArg = tempStr[i].split("#");
//        String typeKey = tempStrArg[0].substring(tempStrArg[0].indexOf("=") + 1, tempStrArg[0].length());
//        String score = tempStrArg[1].substring(tempStrArg[1].indexOf("=") + 1, tempStrArg[1].length());
//        String comment = tempStrArg[2].substring(tempStrArg[2].indexOf("=") + 1, tempStrArg[2].length());
//        String tempId = tempStrArg[3].substring(tempStrArg[3].indexOf("=") + 1, tempStrArg[3].length());
//        Comment comm = new Comment();
//        comm.setTypeKey(typeKey);
//        comm.setScore(score);
//        comm.setComment(comment);
//        comm.setUserId(userId);
//        comm.setTargetId(targetId);
//        comm.setTargetType(targetType);
//        comm.setLessonId(lessonId);
//        comm.setCommentId(Tools.generateID());
//        comm.setTempId(tempId);
//
//        this.commentService.insert(comm);
//      }
//      result.setRetcode(1);
//      logger.info("评价成功");
//      return map;
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//      result.setRetcode(-1);
//      logger.error("评价失败!" + e);
//      result.setRetmsg("评价失败" + e);
//
//      return map;
//    }
//  }
//
//  @RequestMapping({"/getComments"})
//  @ResponseBody
//  public Map<String, Object> getComments(@RequestParam String lessonId, String schId, @RequestParam String targetId, @RequestParam String targetType, @RequestParam Integer showCount, @RequestParam Integer pageNum, HttpSession session)
//  {
//    String tid = "tid_get_comments";
//    ResourceDesc rdesc = new ResourceDesc();
//    HashMap map = new HashMap();
//    Result result = new Result();
//    map.put("result", result);
//    Page page = new Page();
//    page.setCurrentPage(pageNum.intValue());
//    page.setShowCount(showCount.intValue());
//    try
//    {
//      DataDesc datadesc = new DataDesc();
//      HashMap data = new HashMap();
//      data.put("Comments", this.commentService.selectCommentsListPage(page, lessonId, schId, targetId, targetType));
//      boolean paging = page.caculatePageing();
//      datadesc.setPaging(paging);
//      datadesc.setPage(page);
//      map.put("datadesc", datadesc);
//      map.put("data", data);
//      result.setRetcode(1);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//      result.setRetcode(-1);
//      result.setRetmsg("获取评价列表失败" + e);
//    }
//    return map;
//  }
//
//  @RequestMapping({"/getCommTemps"})
//  @ResponseBody
//  public Map<String, Object> getCommTemps(@RequestParam String roleType, @RequestParam String targetType, HttpSession session)
//  {
//    String tid = "tid_get_comm_temps";
//    ResourceDesc rdesc = new ResourceDesc();
//    HashMap map = new HashMap();
//    Result result = new Result();
//    map.put("result", result);
//    DataDesc datadesc = new DataDesc();
//    datadesc.setPaging(false);
//    datadesc.setPage(null);
//    map.put("datadesc", datadesc);
//    try
//    {
//      HashMap data = new HashMap();
//      data.put("temps", this.commentService.selectTemplates(roleType, targetType));
//      map.put("data", data);
//      result.setRetcode(1);
//    }
//    catch (Exception e)
//    {
//      e.printStackTrace();
//      result.setRetcode(-1);
//      result.setRetmsg("获取评价类型列表失败" + e);
//    }
//    return map;
//  }
}