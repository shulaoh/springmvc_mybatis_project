package com.web.controller;

import com.web.data.pojo.FileContent;
import com.web.data.pojo.Lession;
import com.web.data.pojo.Schedule;
import com.web.data.pojo.SysUser;
import com.web.service.ILessonManageService;
import com.web.service.ILessonService;
import com.web.service.INotificationService;
import com.web.service.IScheduleService;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.Tools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class ScheduleController {

	@Resource
	private IScheduleService scheduleService;

	@Resource
	private ILessonService lessService;

	@Resource
	private ILessonManageService lessonManageService;

	@Resource
    private INotificationService notificationService;

	private static Logger logger = Logger.getLogger(ScheduleController.class);

	@RequestMapping({ "/getSchedule" })
	@ResponseBody
	public Map<String, Object> getSchedule(@RequestParam String lessonId, @RequestParam Integer showCount,
			@RequestParam Integer pageNum, HttpSession session) {
		String tid = "tid_get_schedule";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum.intValue());
		page.setShowCount(showCount.intValue());
		try {
			DataDesc datadesc = new DataDesc();
			HashMap data = new HashMap();
			data.put("schedules", this.scheduleService.selectSchedulesListPage(page, lessonId,
					((SysUser) session.getAttribute("userSession")).getUserId()));
			boolean paging = page.caculatePageing();
			datadesc.setPaging(paging);
			datadesc.setPage(page);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取日程列表失败");
		}
		return map;
	}

	@RequestMapping({ "/getSingleScheduleByScheduleId" })
	@ResponseBody
	public Map<String, Object> getScheduleBy(@RequestParam String scheduleId) {
		String tid = "get_single_schedule_by_ scheduleId";
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {
			HashMap data = new HashMap();
			data.put("schedule", this.scheduleService.getSingleScheduleByScheduleId(scheduleId));
			map.put("datadesc", new DataDesc());
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取日程信息失败");
		}
		return map;
	}

	@RequestMapping({ "/updateSchedule" })
	@ResponseBody
	public Map<String, Object> updateSchedule(Schedule sche, HttpSession session) {
		String tid = "tid_update_schedule";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		if (this.scheduleService.updateByPrimaryKeySelective(sche) > 0) {
			result.setRetcode(1);
		} else {
			result.setRetcode(-1);
			result.setRetmsg("修改日程信息失败");
		}
		if (sche.getSsTime() == null)
			return map;

		Lession less = this.lessService.selectLessionById(sche.getLessonId(), null);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long lessSTime = 0L;
		long lessETime = 0L;
		try {
			long schETime;
			long schSTime = format.parse(sche.getSsTime()).getTime();
			if (sche.getSchLastMNum() == null)
				schETime = format.parse(sche.getSsTime()).getTime();
			else
				schETime = format.parse(sche.getSsTime()).getTime()
						+ Integer.valueOf(sche.getSchLastMNum()).intValue() * 60 * 1000;

			if ((less.getStime() != null) && (less.getStime().length() > 0)) {
				lessSTime = format.parse(less.getStime()).getTime();
				if (schSTime < lessSTime)
					less.setStime(format.format(Long.valueOf(schSTime)));
				else
					less.setStime(less.getStime());
			} else {
				less.setStime(format.format(Long.valueOf(schSTime)));
			}
			if ((less.getEtime() != null) && (less.getEtime().length() > 0)) {
				lessETime = format.parse(less.getEtime()).getTime();
				if (schETime > lessETime)
					less.setEtime(format.format(Long.valueOf(schETime)));
				else
					less.setEtime(less.getEtime());
			} else {
				less.setEtime(format.format(Long.valueOf(schETime)));
			}

			this.lessService.updateByPrimaryKeySelective(less);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping({ "/addSch" })
	@ResponseBody
	public Map<String, Object> addSch(@RequestParam String lessonId, @RequestParam String schName,
			@RequestParam String schPlace, @RequestParam String schIntro, @RequestParam String ssTime,
			@RequestParam String schLastMNum, @RequestParam String signFlag, @RequestParam String signSTime,
			@RequestParam String signETime, @RequestParam String commentFlag, @RequestParam String allCommFlag,
			@RequestParam String tutorId, String commTempIds, HttpSession session) {
		String tid = "tid_add_sch";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		if (schName == null)
			schName = "";
		Schedule sch = new Schedule();
		sch.setLessonId(lessonId);
		sch.setSchId(Tools.generateID());
		sch.setSchName(schName);
		sch.setSchPlace(schPlace);
		sch.setSchIntro(schIntro);
		sch.setSsTime(ssTime);
		sch.setSchLastMNum(schLastMNum);
		sch.setSignFlag(signFlag);
		sch.setSignSTime(signSTime);
		sch.setSignETime(signETime);
		sch.setCommentFlag(commentFlag);
		sch.setAllCommFlag(allCommFlag);
		sch.setTutorId(tutorId);
		sch.setCommTempIds(commTempIds);
		try {
			int ret = this.scheduleService.insert(sch);

			Lession less = this.lessService.selectLessionById(lessonId, null);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			long lessSTime = 0L;
			long lessETime = 0L;
			long schSTime = format.parse(ssTime).getTime();
			long schETime = format.parse(ssTime).getTime() + Integer.valueOf(schLastMNum).intValue() * 15 * 60 * 1000;
			if (less.getStime() != null) {
				lessSTime = format.parse(less.getStime()).getTime();
				if (lessSTime > 10000L)
					if (schSTime < lessSTime)
						less.setStime(format.format(Long.valueOf(schSTime)));
					else
						less.setStime(less.getStime());

				else
					less.setStime(format.format(Long.valueOf(schSTime)));
			} else {
				less.setStime(format.format(Long.valueOf(schSTime)));
			}
			if (less.getEtime() != null) {
				lessETime = format.parse(less.getEtime()).getTime();
				if (schETime > lessETime)
					less.setEtime(format.format(Long.valueOf(schETime)));
				else
					less.setEtime(less.getEtime());
			} else {
				less.setEtime(format.format(Long.valueOf(schETime)));
			}

			this.lessService.updateByPrimaryKeySelective(less);

			if (ret > 0) {
				result.setRetcode(1);
				return map;
			}

			result.setRetcode(-1);
			result.setRetmsg("添加日程错误");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("添加日程错误" + e);
		}
		return map;
	}

	@RequestMapping({ "/deleteSchedule" })
	@ResponseBody
	public Map<String, Object> deleteSchedule(@RequestParam String scheduleId) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);

		try {
			Map<String, Object> data = new HashMap<>();
			int ret = this.scheduleService.deleteSchedule(scheduleId);
			String lessonId = scheduleService.getLessonIdByScheduleId(scheduleId);
            notificationService.sendNotificationForChange(lessonId, "update");
			if (ret > 0) {
			    result.setRetcode(1);
			} else {
				result.setRetcode(-1);
				result.setRetmsg("删除日程列表失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("删除日程列表失败");
		}
		return map;
	}
	
	@RequestMapping({ "/getSchedules" })
	@ResponseBody
	public Map<String, Object> getSchedules(@RequestParam String lessonId, @RequestParam String scheduleId) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);

		try {
			Map<String, Object> data = new HashMap<>();
			data.put("schedules", this.lessonManageService.getSchedule(lessonId, scheduleId));
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取日程列表失败");
		}
		return map;
	}
	
	@RequestMapping({ "/putSchedule" })
	@ResponseBody
	public Map<String, Object> putSchedule(@RequestParam String lessonId, @RequestParam String schId, @RequestParam String schName,
			@RequestParam String schPlace, @RequestParam String schIntro, @RequestParam String ssTime,
			@RequestParam String schLastMNum, @RequestParam String signFlag, @RequestParam String signSTime,
			@RequestParam String signETime, @RequestParam String commentFlag, @RequestParam String allCommFlag,
			@RequestParam String tutorId, String commTempIds, String attachments) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);
		if (schName == null)
			schName = "";
		Schedule sch = new Schedule();
		sch.setLessonId(lessonId);
		sch.setSchId(schId);
		sch.setSchName(schName);
		sch.setSchPlace(schPlace);
		sch.setSchIntro(schIntro);
		sch.setSsTime(ssTime);
		sch.setSchLastMNum(schLastMNum);
		sch.setSignFlag(signFlag);
		sch.setSignSTime(signSTime);
		sch.setSignETime(signETime);
		sch.setCommentFlag(commentFlag);
		sch.setAllCommFlag(allCommFlag);
		sch.setTutorId(tutorId);
		sch.setCommTempIds(commTempIds);
		List<FileContent> fileList = new ArrayList<>();
		if ((attachments != null) && (attachments.trim().length() > 0)){
            String[] file = attachments.split(";");
            for (int i = 0; i < file.length; ++i) {
            	String[] filenames = file[i].split(",");
            	FileContent fc = new FileContent();
            	String filename = filenames[0];
            	String attach = filenames[1];
            	fc.setSchId(schId);
            	fc.setFileId("-1");
            	fc.setFileName(filename);
            	fc.setFileType(filename.substring(filename.lastIndexOf(".") + 1));
            	fc.setFileURL(attach);
            	fileList.add(fc);
            }
        }
		sch.setFileContentList(fileList);
		
		List<SysUser> teachers = new ArrayList<>();
		if ((tutorId != null) && (tutorId.trim().length() > 0)){
            String[] teas = tutorId.split(",");
            SysUser tea = null;
            for (int i = 0; i < teas.length; ++i) {
            	tea = new SysUser();
            	tea.setUserId(teas[i]);
            	teachers.add(tea);
            }
        }
		sch.setTeachers(teachers);
		
		try {
			Map<String, Object> rtn = this.scheduleService.putSchedule(sch);
			int lessonStatus = lessonManageService.getLessonStatus(lessonId);
			if (lessonStatus == 1) {
                notificationService.sendNotificationForChange(lessonId, "update");
            }

			result = (Result)rtn.get("result");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("错误：添加日程错误");
		}
		map.put("result", result);
		return map;
	}
	
	@RequestMapping({ "/hasSchModRole" })
	@ResponseBody
	public Map<String, Object> hasSchModRole(@RequestParam String lessonId, @RequestParam String userId) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);

		try {
			Map<String, Object> data = new HashMap<>();
			data.put("hasRole", this.scheduleService.hasSchModRole(userId, lessonId));
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取日程修改权限失败");
		}
		return map;
	}
	
//	private SysUser createTemAdmin(String tmpAdmin) {
//		SysUser newUser = new UserView();
//		newUser.setUserId(Tools.generateID());
//		newUser.setUserName("自定义讲师");
//		newUser.setDept("自定义讲师");
//		newUser.setAdminFlag(Const.USER_ROLE_20);
//		newUser.setUstatus(Const.USER_STATUS_INFO);
//		if (Tools.checkEmail(tmpAdmin)) {
//			newUser.setPhone(tmpAdmin);
//		} else if (Tools.checkMobileNumber(tmpAdmin)) {
//			newUser.setEmail(tmpAdmin);
//		} else {
//			newUser.setRemark(tmpAdmin);
//		}
//		userService.insert(newUser);
//		PersonalInfo pInfo = new PersonalInfo();
//		BeanUtils.copyProperties(newUser, pInfo);
//		pInfo.setPersonId(Tools.generateID());
//		personalService.insert(pInfo);
//		return newUser;
//	}
}