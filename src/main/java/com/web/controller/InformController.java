package com.web.controller;

import com.web.data.pojo.SysUser;
import com.web.service.IInformService;
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
public class InformController
{

  @Resource
  private IInformService informService;
  private static Logger logger = Logger.getLogger(InformController.class);

  @RequestMapping({"/getInforms"})
  @ResponseBody
  public Map<String, Object> getInforms(@RequestParam Integer showCount, @RequestParam Integer pageNum, HttpSession session)
  {
    String tid = "tid_get_Informs";
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
      data.put("Informs", this.informService.getInforms(page, ((SysUser)session.getAttribute("userSession")).getUserId()));
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
      result.setRetmsg("获取通知列表失败" + e);
    }
    return map;
  }

  @RequestMapping({"/getDefPics"})
  @ResponseBody
  public Map<String, Object> getDefPics(@RequestParam String picType, String subType, HttpSession session)
  {
    String tid = "tid_get_def_pics";
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
      data.put("temps", this.informService.selectDefPics(picType, subType));
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取默认列表失败" + e);
    }
    return map;
  }
}