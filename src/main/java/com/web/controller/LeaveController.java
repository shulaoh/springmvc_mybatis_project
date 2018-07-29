package com.web.controller;

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

import com.web.data.pojo.Leave;
import com.web.data.pojo.SysUser;
import com.web.service.ILeaveService;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;

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

  /**
   * {"userId":"3f223a4a618f4a108fbd7758dd4af314","schId":"dd63d1c13e534943a5f564859257762d","isApproved":"1","approveUserId":"51e268e5623548298917813b0abf3c15"}
   * {"userId":"51e268e5623548298917813b0abf3c15","schId":"057ca75b55f54e0185b40da0ae527934","isApproved":"2","approveUserId":"51e268e5623548298917813b0abf3c15","remark":"no way"}
   * @param leave
   * @param session
   * @return
   */
  @RequestMapping({"/updateLeave"})
  @ResponseBody
  public Map<String, Object> updateLeave(Leave leave, HttpSession session)
  {
    String tid = "tid_update_leave";
//    String userId = ((SysUser)session.getAttribute("userSession")).getUserId();
//    leave.setUserId(userId);
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      int ret = this.leaveService.updateLeave(leave);
      if (ret > 0)
      {
        result.setRetcode(1);
        result.setRetmsg("操作成功");
        logger.info("用户(userId=" + leave.getUserId() + ")修改请假成功，日程ID：" + leave.getSchId());
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

  /**
   * {"schId":"dd63d1c13e534943a5f564859257762d"}
   * @param schId
   * @param session
   * @return
   */
  @RequestMapping({"/getMyLeave"})
  @ResponseBody
  public Map<String, Object> getMyLeave(@RequestParam String schId,HttpSession session) {
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      List ret = this.leaveService.getMyLeave(schId, ((SysUser) session.getAttribute("userSession")).getUserId());
      result.setRetcode(1);
      map.put("data", ret);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取我的请假状态失败");
    }
    return map;
  }
  
}