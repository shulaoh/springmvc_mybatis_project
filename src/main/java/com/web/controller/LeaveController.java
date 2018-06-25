package com.web.controller;

import com.web.data.pojo.Leave;
import com.web.data.pojo.SysUser;
import com.web.service.ILeaveService;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LeaveController
{
  private static Logger logger = Logger.getLogger(LeaveController.class);

  @Resource
  private ILeaveService leaveService;

  @RequestMapping({"/addLeave"})
  @ResponseBody
  public Map<String, Object> addLeave(Leave leave, HttpSession session)
  {
    String tid = "tid_add_leave";
    String userId = ((SysUser)session.getAttribute("userSession")).getUserId();

    HashMap paramMap = new HashMap();
    paramMap.put("userId", userId);
    paramMap.put("schId", leave.getSchId());
    paramMap.put("reason", leave.getReason());
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      int ret = this.leaveService.addLeave(paramMap);
      if (ret > 0)
      {
        result.setRetcode(ret);
        logger.info("用户(userId=" + userId + ")请假成功，日程ID：" + leave.getSchId());
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("请假错误");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("添加请假信息失败");
    }
    return map;
  }

  @RequestMapping({"/updateLeave"})
  @ResponseBody
  public Map<String, Object> updateLeave(Leave leave, HttpSession session)
  {
    String tid = "tid_update_leave";
    String userId = ((SysUser)session.getAttribute("userSession")).getUserId();
    leave.setUserId(userId);
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      int ret = this.leaveService.updateLeave(leave);
      if (ret > 0)
      {
        result.setRetcode(ret);
        logger.info("用户(userId=" + userId + ")修改请假成功，日程ID：" + leave.getSchId());
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("修改请假错误");

      result.setRetcode(ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("修改请假信息失败");
    }
    return map;
  }

  @RequestMapping({"/getSchLeaves"})
  @ResponseBody
  public Map<String, Object> getSchLeaves(@RequestParam String schId) {
    String tid = "tid_get_leaves";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      List ret = this.leaveService.selectLeaveList(schId);
      result.setRetcode(1);
      map.put("data", ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("修改课程状态失败");
    }
    return map;
  }
}