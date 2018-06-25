package com.web.controller;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.service.*;
import com.web.utils.*;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StringUtils;
import com.web.data.pojo.LessonCommon;
import com.web.data.pojo.PersonalInfo;
import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;

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
			data.put("lessonFiles", this.lessonManageService.getFilesByLessonId(map));
			data.put("count", lessonManageService.getFilesCountByLessonId(map));
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
	@RequestMapping({ "/putLesson" })
	@ResponseBody
	public Map<String, Object> putLesson(@RequestParam String lessonId, @RequestParam String lessonName,
			@RequestParam String lessonType, @RequestParam String place, String lessonInfo, String purl,
			@RequestParam String teacherId, @RequestParam String creatorId, @RequestParam String allCommFlag,
			@RequestParam String lessPicUrl, @RequestParam String lessCycPicUrl, String inviUserIds, String commTempIds,
			String adminIds,String inviUserFile) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
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

		less.setPlace(place);
		less.setLessonInfo(lessonInfo);
		less.setPurl(purl);
		less.setTeacherId(teacherId);
		less.setTeacherName(teacherName);
		less.setCreatorId(creatorId);
		less.setAllCommFlag(allCommFlag);
		less.setLessPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessPicUrl);
		less.setLessCycPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + lessCycPicUrl);
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
		if (!StringUtils.isEmptyOrWhitespaceOnly(inviUserFile)){
			File userFile = new File(inviUserFile);
			if (userFile.exists()) {
				List<String> errs = null;
				List<SysUser> suts = userService.importUesers(userFile, errs);
				if (errs.isEmpty()) {
					studentList.addAll(suts);
				} else {
					result.setRetcode(-1);
					StringBuffer buf = new StringBuffer();
					for(String errInfo : errs) {
						buf.append(errInfo);
					}
					result.setRetmsg("添加课程错误" + buf.toString());
				}
			}
		}
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
	public Map<String, Object> getIcons() {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		map.put("data", Const.iconList);
		result.setRetcode(1);
		return map;
	}

}
