package com.web.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.web.data.bean.SignInfo;
import com.web.data.pojo.*;
import org.springframework.stereotype.Service;

import com.web.data.bean.ScheduleInfo;
import com.web.data.mapper.FileContentMapper;
import com.web.data.mapper.LessonManageMapper;
import com.web.data.mapper.LessonMapper;
import com.web.data.mapper.SchTeaMapper;
import com.web.data.mapper.ScheduleFileContentMapper;
import com.web.data.mapper.ScheduleMapper;
import com.web.data.mapper.SignMapper;
import com.web.data.mapper.SysUserMapper;
import com.web.data.mapper.UserViewMapper;
import com.web.utils.Page;
import com.web.utils.Result;
import com.web.utils.Tools;

@Service("scheduleService")
public class ScheduleServiceImpl implements IScheduleService {

	@Resource
	private ScheduleMapper scheduleMapper;

	@Resource
	private FileContentMapper fileContentMapper;

	@Resource
	private ScheduleFileContentMapper scheduleFileContentMapper;

	@Resource
	private LessonMapper lessonMapper;

	@Resource
	private SignMapper signMapper;
	
	@Resource
	private LessonManageMapper lessonManageMapper;
	
	@Resource
	private SchTeaMapper schTeaMapper;
	
	@Resource
	private SysUserMapper sysUserMapper;

	public List<Schedule> selectSchedulesListPage(Page page, String lessonId, String userId) {
		HashMap map = new HashMap();
		map.put("page", page);
		map.put("lessonId", lessonId);
		List list = this.scheduleMapper.selectSchedulesListPage(map);
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Schedule temp = (Schedule) localIterator.next();

			map.put("userId", userId);
			map.put("lessonId", temp.getLessonId());
			map.put("targetType", "SCH");
			map.put("targetId", temp.getSchId());
			int commentFlag = this.lessonMapper.getcommentFlag(map);
			if (commentFlag > 0) {
				temp.setHaveCommFlag("Y");
			} else {
				temp.setHaveCommFlag("N");
			}
			map.put("schId", temp.getSchId());
			int hasSign = this.signMapper.getSignFlag(map);
			if (hasSign > 0) {
				temp.setHaveSign("Y");
			} else {
				temp.setHaveSign("N");
			}
			//List<SignInfo> signList = signMapper.selectSignListPage(lessonId, temp.getSchId(), new Page());
			//temp.setSignInfo(signList);
			HashMap mapLeave = new HashMap();
			mapLeave.put("userId", userId);
			mapLeave.put("schId", temp.getSchId());
			int leaveCount = this.lessonMapper.countHaveLeave(mapLeave);
			if (leaveCount > 0) {
				temp.setHaveLeave("Y");
			} else {
				temp.setHaveLeave("N");
			}
			
			// default set sign time delay 15 mins
			if ("Y".equals(temp.getHaveSign())) {
				String signEtime = temp.getSignETime();
				Date eDate = Tools.str2Date(signEtime + ":00");
				Calendar ca = Calendar.getInstance();
				ca.setTime(eDate); 
				ca.add(Calendar.MINUTE, 15);
				eDate = ca.getTime();
				temp.setSignETime(Tools.date2Str(eDate));
			}
			if (null != temp.getTutorId()) {
				List<SysUser> teachers = sysUserMapper.getUserByUserIds(temp.getTutorId().split(","));
				temp.setTeachers(teachers);
			}
		}

		return list;
	}

	public int updateByPrimaryKeySelective(Schedule sche) {
		sche.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return this.scheduleMapper.updateByPrimaryKeySelective(sche);
	}

	public int insert(Schedule sch) {
		sch.setCreateTime(new Timestamp(System.currentTimeMillis()));
		sch.setUpdateTime(sch.getCreateTime());
		return this.scheduleMapper.insert(sch);
	}

	public Schedule getSingleScheduleByScheduleId(String scheduleId) {
		Schedule sch = this.scheduleMapper.getSingleScheduleByScheduleId(scheduleId);
		
		sch.setTeachers(this.sysUserMapper.selectBySchId(sch.getSchId()));
		
		return sch;
	}

	private long[] getStartEndDateTimeBySchedule(String startDateString, String minutes) {
		long[] date = new long[2];

		Date startDate = Tools.str2DateNoSec(startDateString);
		long start = startDate.getTime();
		date[0] = start;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MINUTE, Integer.valueOf(minutes));
		Date endDate = calendar.getTime();
		long end = endDate.getTime();

		date[1] = end;
		return date;
	}

	public Map<String, Object> putSchedule(Schedule schedule) {
		Map<String, Object> map = new HashMap<>();
		Result result = new Result();
		map.put("result", result);

		String lessonId = schedule.getLessonId();
		if (lessonId == null) {
			result.setRetcode(-1);
			result.setRetmsg("错误：更新日程时课程Id不能为空");
			return map;
		}


		// 查出现有的讲师日程安排
		List<String> teaIds = new ArrayList<String>();
		for (SysUser user: schedule.getTeachers()) {
			teaIds.add(user.getUserId());
		}
		List<ScheduleInfo> curSchInfoList = scheduleMapper.getScheduleByTeaIds(teaIds);
		long[] now = getStartEndDateTimeBySchedule(schedule.getSsTime(), schedule.getSchLastMNum());
		for (ScheduleInfo sch : curSchInfoList) {
			long[] existing = getStartEndDateTimeBySchedule(sch.getSsTime(), sch.getSchLastMNum());
			for (String teaId : teaIds) {
				if ((!(sch.getSchId().equals(schedule.getSchId())))
						&& (sch.getUserId().equals(teaId))
						&&((now[0] >= existing[0] && now[0] <= existing[1]) || (now[1] >= existing[0] && now[1] <= existing[1]))
						) {
					result.setRetmsg("错误：该日程任课教师" + "与现有日程：" + sch.getSchName() + "在上课时间存在冲突");
					result.setRetcode(-1);
					return map;
				}
			}
		}
//		HashMap<String, Object> scheduleMap = new HashMap<>();
//		scheduleMap.put("lessonId", lessonId);
//		List<Schedule> list = scheduleMapper.getSchedulesListByLessonId(scheduleMap);
//		
//		long[] now = getStartEndDateTimeBySchedule(schedule);
//		String teacherId = schedule.getTutorId();
//		String location = schedule.getSchPlace();
//		for (Schedule s : list) {
//			long[] existing = getStartEndDateTimeBySchedule(s);
//			String t = schedule.getTutorId();
//			String loc = schedule.getSchPlace();
//			if (((now[0] >= existing[0] && now[0] <= existing[1]) || (now[1] >= existing[0] && now[1] <= existing[1]))
//					&& (teacherId.equals(t) || location.equals(loc))) {
//				String name = s.getSchName();
//				if (teacherId.equals(t)) {
//					//String teacher = schedule.getTutor().getName();
//					result.setRetmsg("错误：该日程任课教师" + "与现有日程：" + name + "在上课时间存在冲突");
//				}
//				if (location.equals(loc)) {
//					result.setRetmsg("错误：该日程上课地点：" + location + "与现有日程：" + name + "在上课时间存在冲突");
//				}
//				result.setRetcode(-1);
//				return map;
//			}
//		}
		try {
			String scheduleId = schedule.getSchId();
			if (scheduleId.equals("-1")) {
				scheduleId = Tools.generateID();
				schedule.setSchId(scheduleId);
				schedule.setCreateTime(new Timestamp(System.currentTimeMillis()));
				schedule.setUpdateTime(schedule.getCreateTime());
				scheduleMapper.insert(schedule);
			} else {
				schedule.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				scheduleMapper.updateByPrimaryKeySelective(schedule);
			}

			List<FileContent> fileList = schedule.getFileContentList();
			scheduleFileContentMapper.deleteAllFileContentsByScheduleId(scheduleId);
			if (fileList != null && fileList.size() > 0) {
				// 删除该日程下关联的全部附件

				for (FileContent fc : fileList) {
					// 1. 插入附件表
					if (fc.getFileId().equals("-1")) {
						fc.setSchId(scheduleId);
						fc.setFileId(Tools.generateID());
					}

					fileContentMapper.insert(fc);

					ScheduleFileContent sfc = new ScheduleFileContent();
					sfc.setFileId(fc.getFileId());
					sfc.setScheduleId(scheduleId);
					sfc.setFileContent(fc);
					scheduleFileContentMapper.insertScheduleFileContent(sfc);

				}
			}

			List<SchTeaLink> stLinkList = new ArrayList<SchTeaLink>();
			SchTeaLink schTeaLink = null;
			for (SysUser user: schedule.getTeachers()) {
				schTeaLink = new SchTeaLink();
				schTeaLink.setSchId(schedule.getSchId());
				schTeaLink.setUserId(user.getUserId());
				stLinkList.add(schTeaLink);
			}
			// 先删除再增加
			schTeaMapper.deleteBySchId(schedule.getSchId());
			schTeaMapper.insertBatch(stLinkList);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("错误：更新日程失败");
			return map;
		}
		result.setRetcode(1);
		result.setRetmsg(schedule.getSchId());
		return map;
	}
	
	public int deleteSchedule(String scheduleId) {
		try {
			List<FileContent> files = scheduleMapper.getFileContentListByScheduleId(scheduleId);
			if (files != null && files.size() > 0) {
				for (FileContent fc : files) {
					String fileId = fc.getFileId();
					fileContentMapper.deleteByFileId(fileId);
				}
			}
			scheduleFileContentMapper.deleteAllFileContentsByScheduleId(scheduleId);
			scheduleMapper.deleteScheduleById(scheduleId);
		} catch (Exception ex) {
			return -1;
		}
		return 1;
	}
	
	public boolean hasSchModRole(String userId, String lessId) {
		return this.scheduleMapper.hasSchModRole(userId, lessId) > 0 ? true : false;
	}

	@Override
	public String getLessonIdByScheduleId(String scheduleId) {
		return scheduleMapper.getLessonIdByScheduleId(scheduleId);
	}

	@Override
	public int getScheduleCountByLessonId(String lessonId) {
		return scheduleMapper.getScheduleCountByLessonId(lessonId);
	}
}