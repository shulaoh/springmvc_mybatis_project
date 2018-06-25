package com.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;
import com.web.service.IUserService;
import com.web.service.IUserViewService;
import com.web.utils.DataDesc;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.WeChatUtil;

@Controller
public class LoginController
{
  private static Logger logger = Logger.getLogger(LoginController.class);

  @Resource
  private IUserService userService;
  
	@Resource
	private IUserViewService userViewService;

  @RequestMapping({"/login"})
  @ResponseBody
  public Map<String, Object> login(@RequestParam String loginId, @RequestParam String password, HttpSession session)
  {
    String tid = "tid_login";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    UserView user = this.userViewService.userCheckIn(loginId);
    if (user != null)
    {
      if (user.getPassword().equals(password))
      {
        session.setAttribute("userSession", user);
        result.setRetcode(1);
        result.setRetmsg(session.getId());
        return map;
      }

      result.setRetcode(-1);
      result.setRetmsg("密码错误");
    }
    else
    {
      result.setRetcode(-1);
      result.setRetmsg("用户未注册");
    }
    return map;
  }

  @RequestMapping({"/loginByWeChatId"})
  @ResponseBody
  public Map<String, Object> loginByWeChatId(@RequestParam String jscode, HttpSession session)
  {
    String tid = "tid_loginByWeChatId";

    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    String wechatId = null;
    wechatId = WeChatUtil.getOpenIdByCode(jscode);
    if (wechatId == null)
    {
      result.setRetcode(-1);
      result.setRetmsg("获取微信ID失败");
      return map;
    }
    UserView user = this.userViewService.userCheckInByWeixinID(wechatId);
    if (user != null)
    {
      session.setAttribute("userSession", user);
      result.setRetcode(1);
      result.setRetmsg(session.getId());
      return map;
    }

    result.setRetcode(-2);
    result.setRetmsg(wechatId);

    return map;
  }

  @RequestMapping({"/logOut"})
  @ResponseBody
  public Map<String, Object> logOut(HttpSession session) {
    String tid = "tid_logOut";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    session.removeAttribute("userSession");
    result.setRetcode(1);
    return map;
  }

  @RequestMapping({"/getSysConfigs"})
  @ResponseBody
  public Map<String, Object> getSysConfigs(@RequestParam String roleType, @RequestParam String targetType, HttpSession session)
  {
    String tid = "tid_get_sysconfigs";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    DataDesc datadesc = new DataDesc();
    datadesc.setPaging(false);
    datadesc.setPage(null);
    map.put("datadesc", datadesc);
    try
    {
      HashMap data = new HashMap();
      data.put("sysconfigs", this.userService.getSysConfig());
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取系统配置信息列表失败" + e);
    }
    return map;
  }
}