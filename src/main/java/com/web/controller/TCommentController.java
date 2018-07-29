package com.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.web.data.bean.CommImportItem;
import com.web.data.pojo.Comment;
import com.web.data.pojo.SysUser;
import com.web.data.pojo.TCommTemplateGroup;
import com.web.data.pojo.UserView;
import com.web.service.ICommentService;
import com.web.service.ILessonService;
import com.web.utils.DataDesc;
import com.web.utils.ExcelUtil;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.Tools;

@Controller
public class TCommentController
{

  @Resource
  private ICommentService commentService;
  
  @Resource
  private ILessonService lessonService;
  private static Logger logger = Logger.getLogger(TCommentController.class);

  // 获取评价类型信息
  @RequestMapping({"/getCommTypes"})
  @ResponseBody
  public Map<String, Object> getCommTypes(String roleType, String targetType, @RequestParam String commTempIds, HttpSession session)
  {
    String tid = "tid_get_commtypes";
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
      data.put("targets", this.commentService.selectTargets(roleType, targetType, commTempIds));
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取评价类型列表失败" + e);
    }
    return map;
  }

  @RequestMapping({"/comment"})
  @ResponseBody
  public Map<String, Object> comment(@RequestParam String lessonId, String schId, @RequestParam String targetType, @RequestParam String targetId, @RequestParam String comments, HttpSession session)
  {
    String tid = "tid_comment";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    String userId = ((SysUser)session.getAttribute("userSession")).getUserId();

    System.out.println("comments=" + comments);
    String[] tempStr = comments.split("##");
    System.out.println("length=" + tempStr.length);
    try
    {
    	List<Comment> commList = new ArrayList<Comment>();
    	Date curDate = new Date();
      for (int i = 0; i < tempStr.length; ++i)
      {
        String[] tempStrArg = tempStr[i].split("#");
        String tempItemId = tempStrArg[0].substring(tempStrArg[0].indexOf("=") + 1, tempStrArg[0].length());
        String score = tempStrArg[1].substring(tempStrArg[1].indexOf("=") + 1, tempStrArg[1].length());
        String comment = tempStrArg[2].substring(tempStrArg[2].indexOf("=") + 1, tempStrArg[2].length());
        Comment comm = new Comment();
        comm.setCommentId(Tools.generateID());
        comm.setUserId(userId);
        comm.setTargetType(targetType);
        comm.setLessId(lessonId);
        comm.setScore(score);
        comm.setTargetId(targetId);
        comm.setComment(comment);
        comm.setTempItemId(tempItemId);
        comm.setSchId(schId);
        comm.setCommTime(curDate);
        commList.add(comm);
      }
      this.commentService.insert(commList);
      result.setRetcode(1);
      logger.info("评价成功");
      return map;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      logger.error("评价失败!" + e);
      result.setRetmsg("评价失败" + e);

      return map;
    }
  }

  @RequestMapping({"/getComments"})
  @ResponseBody
  public Map<String, Object> getComments(@RequestParam String lessonId, String schId, String targetId,  String targetType, Integer showCount, Integer pageNum, HttpSession session)
  {
    String tid = "tid_get_comments";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    Page page = new Page();
    page.setCurrentPage(pageNum == null ? 1 : pageNum.intValue());
    page.setShowCount(showCount == null ? 10 : showCount.intValue());
    try
    {
      DataDesc datadesc = new DataDesc();
      HashMap data = new HashMap();
      UserView loginUser = (UserView) session.getAttribute("userSession");
      data.put("Comments", this.commentService.selectCommentsListPage(page, loginUser.getUserId(),lessonId, schId, targetId, targetType));
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
      result.setRetmsg("获取评价列表失败" + e);
    }
    return map;
  }
  
  @RequestMapping({"/getCommTempGroup"})
  @ResponseBody
  public Map<String, Object> getCommTempGroup(HttpSession session)
  {
    String tid = "tid_get_comm_temps";
    ResourceDesc rdesc = new ResourceDesc();
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    try
    {
      HashMap data = new HashMap();
      UserView user = (UserView)session.getAttribute("userSession");
      data.put("groups", this.commentService.getTemplateGroupList(user.getUserId()));
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取评价模板列表失败" + e);
    }
    return map;
  }
  
  @RequestMapping({"/getCommTemps"})
  @ResponseBody
  public Map<String, Object> getCommTemps(String roleType, String targetType, @RequestParam String commTempId, @RequestParam String lessId, String schId,HttpSession session)
  {
    String tid = "tid_get_comm_temps";
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
      if (StringUtils.isEmpty(roleType)) {
    	  // STU/TEA/TUT
    	  UserView loginUser = (UserView) session.getAttribute("userSession");
    	  roleType = this.lessonService.getCurUserRole(loginUser.getUserId(), lessId, schId);
      }
      data.put("temps", this.commentService.selectTemplates(roleType, targetType, commTempId));
      map.put("data", data);
      result.setRetcode(1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result.setRetcode(-1);
      result.setRetmsg("获取评价模板列表失败" + e);
    }
    return map;
  }

  @RequestMapping(value={"/importCommTemplates"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public Map<String, Object> importCommTemplates(HttpServletRequest request, HttpSession session,String fileDesc, @RequestParam("commFile") MultipartFile file)
    throws Exception
  {
    logger.info("upload beginning [" + file.getName() + "]");
    HashMap map = new HashMap();
    Result result = new Result();
    map.put("result", result);
    if (!(file.isEmpty()))
    {
      String path = request.getServletContext().getRealPath("/templates/");

      String filename = file.getOriginalFilename();
      Calendar date = Calendar.getInstance();
      SimpleDateFormat datef = new SimpleDateFormat("yyyyMMddHHmmss");
      String tempName = "commTemp" + datef.format(date.getTime()) + Tools.getRandomNum() + filename.substring(filename.lastIndexOf("."));
      File filepath = new File(path, tempName);

      if (!(filepath.getParentFile().exists())) {
        filepath.getParentFile().mkdirs();
      }
      File newFile = new File(path + File.separator + tempName);
      file.transferTo(newFile);
      UserView loginUser = (UserView) session.getAttribute("userSession");
      TCommTemplateGroup group = commentService.importCommItems(loginUser, newFile);
      
      HashMap data = new HashMap();
      data.put("group", group);
      map.put("data", data);
      result.setRetcode(1);
      return map;
    } else {
    	result.setRetcode(-1);
    	result.setRetmsg("导入模板失败");
    }
    return map;
  }
	/**
	 * 下载评价模板
	 */
	@RequestMapping({"/getCommTemp"})
	public ResponseEntity<byte[]> getCommTemp(@RequestParam String tempGroupId, HttpServletRequest request)
			throws Exception {
		String path = request.getServletContext().getRealPath("/templates/");
		String filename = "commentTemplate.xlsx";
		File file = new File(path + File.separator + filename);
		if (file.exists()) {

			List<CommImportItem> items = commentService.selectCommGroupItem(tempGroupId);

			File downloadFile = createDownFiles(request, items, file);
			if (downloadFile == null) {
				return null;
			}

			HttpHeaders headers = new HttpHeaders();

			String downloadFielName = new String(filename.getBytes("UTF-8"), "utf-8");

			headers.setContentDispositionFormData("attachment", downloadFielName);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity(FileUtils.readFileToByteArray(downloadFile), headers, HttpStatus.CREATED);
		} else {
			return null;
		}
	}
	/**
	 * 查看指定的评价模板{"tempGroupId":"660b7f799703439f8764b365059afec1"}
	 */
	@RequestMapping({"/getCommTempById"})
	@ResponseBody
	public Map<String, Object> getCommTempById(@RequestParam String tempGroupId, HttpServletRequest request)
			throws Exception {
	    ResourceDesc rdesc = new ResourceDesc();
	    HashMap map = new HashMap();
	    Result result = new Result();
	    map.put("result", result);
	    try
	    {
	      HashMap data = new HashMap();
	      data.put("temps", this.commentService.selectCommGroupItem(tempGroupId));
	      map.put("data", data);
	      result.setRetcode(1);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      result.setRetcode(-1);
	      result.setRetmsg("查看指定的评价项失败" + e);
	    }
	    return map;
	}

	private File createDownFiles(HttpServletRequest request, List<CommImportItem> items, File file) {
		// 1.make a new template file from file
		String path = request.getServletContext().getRealPath("/templates/");
		Calendar date = Calendar.getInstance();
		SimpleDateFormat datef = new SimpleDateFormat("yyyyMMddHHmmss");
		String tempName = "commTemp" + datef.format(date.getTime()) + Tools.getRandomNum()
				+ file.getName().substring(file.getName().lastIndexOf("."));
		File newFile = new File(path + File.separator + tempName);
//		try {
//			FileUtils.copyFile(file, newFile);
			// 2.insert the items into new template file
			// TODO I don't know why the source file modified,need more research here
			if (ExcelUtil.createCommItemFile(file, newFile, items)) {
				// 3.return new file name
				return newFile;
			}
//		} catch (IOException e) {
//			logger.error("copy file failed" + e.getMessage());
//		}
		return null;
	}
}