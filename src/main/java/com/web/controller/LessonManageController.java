package com.web.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import com.web.data.pojo.LessonAdmin;
import com.web.data.pojo.LessonCommon;
import com.web.data.pojo.SysUser;
import com.web.service.ILessonManageService;
import com.web.service.ILessonService;
import com.web.service.INotificationService;
import com.web.service.IPersonalService;
import com.web.service.IScheduleService;
import com.web.service.IUserService;
import com.web.utils.Const;
import com.web.utils.DataDesc;
import com.web.utils.HttpUtil;
import com.web.utils.KeyValue;
import com.web.utils.Page;
import com.web.utils.Result;

@Controller
public class LessonManageController {
	@Resource
	private ILessonManageService lessonManageService;
	@Resource
	private IUserService userService;
	@Resource
	private ILessonService lessonService;

	@Resource
	private IScheduleService scheduleService;
	
	@Resource
	private IPersonalService personalService;

	@Resource
	private INotificationService notificationService;

	@Value("${file.folder}")
    private String fileRelativeAddress;

	private static Logger logger = Logger.getLogger(LessonManageController.class);


	@RequestMapping({"/getLessonFile1"})
	@ResponseBody
	public Map<String, Object> getLessonFile(@RequestParam String lessonId, String schid, @RequestParam Integer showCount, @RequestParam Integer pageNum)
	{
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		int offset = (pageNum - 1) * showCount;
		try
		{
			HashMap data = new HashMap();
			Map<String, Object> params = new HashMap<>();
			params.put("lessonId", lessonId);
			if (schid != null && schid.length() > 0) {
				params.put("scheduleId", schid);
			}
			params.put("offset", offset);
			params.put("size", showCount);
			data.put("lessonFiles", this.lessonManageService.getFilesByLessonId(params));
			data.put("count", lessonManageService.getFilesCountByLessonId(params));
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


	@RequestMapping({ "/getLessons" })
	@ResponseBody
	public Map<String, Object> getLessons(@RequestParam String lessonId, @RequestParam Integer startPage,
			@RequestParam Integer size, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		map.put("filetype", "ppt;pptx;doc;docx;xls;xlsx;pdf");
		try {
			Map<String, Object> data = new HashMap<>();
			SysUser user = (SysUser) session.getAttribute("userSession");
			String userId = user.getUserId();
			if (Const.USER_ROLE_30.equals(user.getAdminFlag()) || Const.USER_ROLE_100.equals(user.getAdminFlag())) {
				userId = null;
			}
			int offset = (startPage - 1) * size;
			List<LessonCommon> list = lessonManageService.getLessonList(lessonId, userId, offset, size);
			int totalCount = lessonManageService.getLessonsCount(lessonId, userId);
			map.put("totalCount", totalCount);
			data.put("lessons", list);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取课程列表失败");
		}
		return map;
	}
/**
 * 
 * @param lessonId
 * @param lessonName
 * @param lessonType
 * @param place
 * @param lessonInfo
 * @param purl
 * @param teacherId
 * @param creatorId
 * @param allCommFlag
 * @param lessPicUrl
 * @param lessCycPicUrl
 * @param inviUserIds
 * @param commTempIds
 * @param adminIds
 * @param inviUserFile 上传学员文件路径
 * @param tmpAdmin 自定义讲师
 * @return
 */
	@RequestMapping( value="/putLesson" ,method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> putLesson(HttpServletRequest request, HttpSession session) {
		
		String paramStr = HttpUtil.getStringFromStream(request);
		JSONObject jobj = JSON.parseObject(paramStr);
		
		String lessonId = jobj.getString("lessonId"); 
		String lessonName = jobj.getString("lessonName");
		String lessonType = jobj.getString("lessonType"); 
		String place=  jobj.getString("place"); 
		String lessonInfo = jobj.getString("lessonInfo"); 
		String purl = jobj.getString("purl");
		String teacherId = jobj.getString("teacherId");
		String creatorId = jobj.getString("creatorId");
		String allCommFlag = jobj.getString("allCommFlag");
		String lessPicUrl = jobj.getString("lessPicUrl");
		String lessCycPicUrl = jobj.getString("lessCycPicUrl");
		String lessonStatus = jobj.getString("lessonStatus");
		String inviUserIds = jobj.getString("inviUserIds");
		String commTempIds = jobj.getString("commTempIds");
		String adminIds = jobj.getString("adminIds");
		String inviUserFile = jobj.getString("inviUserFile");
		
//		String lessonId = request.getParameter("lessonId"); 
//		String lessonName = request.getParameter("lessonName");
//		String lessonType = request.getParameter("lessonType"); 
//		String place=  request.getParameter("place"); 
//		String lessonInfo = request.getParameter("lessonInfo"); 
//		String purl = request.getParameter("purl");
//		String teacherId = request.getParameter("teacherId");
//		String creatorId = request.getParameter("creatorId");
//		String allCommFlag = request.getParameter("allCommFlag");
//		String lessPicUrl = request.getParameter("lessPicUrl");
//		String lessCycPicUrl = request.getParameter("lessCycPicUrl");
//		String lessonStatus = request.getParameter("lessonStatus");
//		String inviUserIds = request.getParameter("inviUserIds");
//		String commTempIds = request.getParameter("commTempIds");
//		String adminIds = request.getParameter("adminIds");
//		String inviUserFile = request.getParameter("inviUserFile");
		
		
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		// ==============================
		// 班主任不能跟新班主任
		// ==============================
		SysUser curUser = (SysUser) session.getAttribute("userSession");
		if (!lessonId.equals("-1")
				&& !Const.USER_ROLE_100.equals(curUser.getAdminFlag())
  			  	&& !Const.USER_ROLE_30.equals(curUser.getAdminFlag())
  			  	&& !Const.USER_ROLE_20.equals(curUser.getAdminFlag())) {
			List<LessonAdmin> admins = this.lessonManageService.getAdminByLessId(lessonId);
			String[] newAdmins = adminIds.split(",");
			if (admins.size() == newAdmins.length) {
				for(LessonAdmin admin : admins) {
					if (!adminIds.contains(admin.getAdminId())) {
						result.setRetcode(-1);
						result.setRetmsg("您不能修改班主任");
					}
				}
			} else {
				result.setRetcode(-1);
				result.setRetmsg("您不能修改班主任");
			}
		}
		// ==============================check end
		if (purl == null)
			purl = "";
		if (lessPicUrl == null)
			lessPicUrl = "DEFAULT_LESS_PIC.PNG";
		if (lessCycPicUrl == null) {
			lessCycPicUrl = "DEFAULT_LESS_CYC_PIC.PNG";
		}
		
		SysUser teacher = userService.getUserById(teacherId);
		String teacherName = null;
		if (teacher != null) {
			teacherName = teacher.getUserName();
		}
		
		LessonCommon less = new LessonCommon();
		less.setLessonId(lessonId);
		less.setLessonName(lessonName);
		less.setLessonType(lessonType);
		int status = 0;
		try {
			status = Integer.valueOf(lessonStatus);
		} catch (NumberFormatException ex) {
			status = 0;
		}
		less.setLessonStatus(status);
		less.setPlace(place);
		less.setLessonInfo(lessonInfo);
		less.setPurl(purl);
		less.setTeacherId(teacherId);
		less.setTeacherName(teacherName);
		less.setCreatorId(creatorId);
		less.setAllCommFlag(allCommFlag);
		less.setLessPicUrl(generateFileAddress(lessPicUrl));
		less.setLessCycPicUrl(generateFileAddress(lessCycPicUrl));
		less.setCommTempIds(commTempIds);
		less.setInstructor(teacher);

		List<SysUser> adminList = new ArrayList<>();
		List<SysUser> studentList = new ArrayList<>();
		if ((inviUserIds != null) && (inviUserIds.trim().length() > 0)) {

			String[] users = inviUserIds.split(",");
			for (int i = 0; i < users.length; ++i) {
				SysUser user = userService.getUserById(users[i]);
				studentList.add(user);
			}
		}
		// import stu file
//		if (!StringUtils.isEmptyOrWhitespaceOnly(inviUserFile)){
//			logger.info("import student file:" + inviUserFile);
//			File userFile = new File(inviUserFile);
//			if (userFile.exists()) {
//				List<String> errs = null;
//				List<SysUser> suts = userService.importUesers(userFile, errs);
//				if (errs.isEmpty()) {
//					studentList.addAll(suts);
//					logger.info("import student success, students:" + suts.size());
//				} else {
//					logger.info("import student file error:");
//					result.setRetcode(-1);
//					StringBuffer buf = new StringBuffer();
//					for(String errInfo : errs) {
//						buf.append(errInfo);
//					}
//					result.setRetmsg("添加课程错误" + buf.toString());
//				}
//			}
//			logger.info("import student file end");
//		}
		if ((adminIds != null) && (adminIds.trim().length() > 0)) {

			String[] users = adminIds.split(",");
			for (int i = 0; i < users.length; ++i) {
				SysUser user = userService.getUserById(users[i]);
				adminList.add(user);
			}
		}

		less.setAdminList(adminList);
		less.setStudentList(studentList);

		try {
			int ret = this.lessonManageService.putLesson(less);
			if (ret > 0) {
				result.setRetcode(1);
				//result.setRetmsg(lessonId);
				return map;
			}

			result.setRetcode(-1);
			result.setRetmsg("添加课程错误");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("添加课程错误" + e);
		}
		return map;
	}

	private String generateFileAddress(String fileName) {
	    String filename = fileName.replaceAll("/", File.separator);
	    filename = filename.replaceAll("\\\\", File.separator);
	    if(fileName.startsWith(fileRelativeAddress)) {
	        return fileName;
        }
	    if (filename.startsWith(File.separator)) {
	        return fileRelativeAddress + fileName;
        } else {
	        return fileRelativeAddress + File.separator + fileName;
        }
    }

	@RequestMapping({ "/joinLesson" })
	@ResponseBody
	public Map<String, Object> joinLesson(@RequestParam String lessonId, HttpSession session) {
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {
			String userId = ((SysUser) session.getAttribute("userSession")).getUserId();
			int ret = this.lessonService.joinLesson(userId, lessonId);
			result.setRetcode(ret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("参与课程失败");
		}
		return map;
	}

	@RequestMapping({ "/updateLessonStatus" })
	@ResponseBody
	public Map<String, Object> updateLessonStatus(@RequestParam String lessonId, @RequestParam int lessonStatus) {
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {

			if (lessonStatus == 1) {
				int count = scheduleService.getScheduleCountByLessonId(lessonId);
				if (count == 0) {
					result.setRetcode(-1);
					result.setRetmsg("发布课程失败：课程需要至少包含一个日程。");
					return map;
				}
			}

			int ret = this.lessonService.updateLessonStatus(lessonStatus, lessonId);
			if (lessonStatus == 0){
				Timestamp t = lessonManageService.getLessonStartDatetime(lessonId);
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
				date = calendar.getTime();
				long l1 = date.getTime();
				long l2 = t.getTime();
				if (l1 > l2) {
					logger.info("通知学员课程被取消");
					notificationService.sendNotificationForChange(lessonId, "cancel");
				}
			}

			result.setRetcode(ret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("修改课程状态失败");
		}
		return map;
	}
	@RequestMapping({ "/deleteLesson" })
	@ResponseBody
	public Map<String, Object> deleteLesson(@RequestParam String lessonId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Result result = new Result();
		map.put("result", result);
		try {
			Map<String, Object> resultMap =this.lessonManageService.deleteLesson(lessonId);
			result.setRetcode(Integer.parseInt((String)resultMap.get("flag")));
			result.setRetmsg(null == resultMap.get("error") ? "" : ((String)resultMap.get("error")));
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("删除课程失败");
		}
		return map;
	}
	@RequestMapping({ "/searchLessons" })
	@ResponseBody
	public Map<String, Object> searchLessons(String searchKey, Integer showCount, Integer pageNum, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		map.put("filetype", "ppt;pptx;doc;docx;xls;xlsx;pdf");
		try {
			Map<String, Object> data = new HashMap<>();
			SysUser user = (SysUser) session.getAttribute("userSession");
			String userId = user.getUserId();
			Page page = new Page();
			page.setCurrentPage(pageNum == null ? 1 : pageNum.intValue());
			page.setShowCount(showCount == null ? 10 : showCount.intValue());
			if (Const.USER_ROLE_100.equals(user.getAdminFlag()) ||
					Const.USER_ROLE_30.equals(user.getAdminFlag())) {
				userId = null;
			}
			List<LessonCommon> lessList = lessonManageService.getLessonList(searchKey, userId, page);
			data.put("lessons", lessList);
			boolean paging = page.caculatePageing();
			DataDesc datadesc = new DataDesc();
			datadesc.setPaging(paging);
			datadesc.setPage(page);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取课程列表失败");
		}
		return map;
	}
	/**
	 * 取到地域列表
	 * @return
	 */
	@RequestMapping({ "/getAreas" })
	@ResponseBody
	public Map<String, Object> getAreas() {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		map.put("data", Const.areaList);
		result.setRetcode(1);
		return map;
	}
	
	@RequestMapping({ "/getIcons" })
	@ResponseBody
	public Map<String, Object> getIcons(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		List<KeyValue> iconList = new ArrayList<KeyValue>();
		String path = request.getServletContext().getContextPath()+ "/image/";
		KeyValue newIcon = null;
		for (KeyValue icon : Const.iconList) {
			newIcon = new KeyValue(icon.getKey(), icon.getValue());
			newIcon.setValue(path + icon.getValue());
			iconList.add(newIcon);
		}
		Result result = new Result();
		map.put("result", result);
		map.put("data", iconList);
		result.setRetcode(1);
		return map;
	}

}
