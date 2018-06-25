package com.web.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.web.data.pojo.*;
import org.springframework.stereotype.Service;

import com.web.data.mapper.LessonAdminMapper;
import com.web.data.mapper.LessonManageMapper;
import com.web.data.mapper.LessonStudentMapper;
import com.web.data.mapper.SysUserMapper;
import com.web.utils.Page;
import com.web.utils.Tools;

@Service("lessonManageService")
public class LessonManageServiceImpl implements ILessonManageService {

    @Resource
    private LessonManageMapper lessonManageMapper;

    @Resource
    private LessonAdminMapper lessonAdminMapper;

    @Resource
    private LessonStudentMapper lessonStudentMapper;
    
	@Resource
	private SysUserMapper sysUserMapper;

    @Override
    public List<LessonCommon> getLessonListByUserId(String userId, int offset, int size) {
        List<LessonCommon> list = lessonManageMapper.getLessonListByUserId(userId, offset, size);

        if (list != null && list.size() > 0) {
            for (LessonCommon lesson : list) {
                String lessonId = lesson.getLessonId();
                Timestamp startTs = lessonManageMapper.getStartDateByLessonId(lessonId);
                Timestamp endTs = lessonManageMapper.getEndDatetimeByLessonId(lessonId);
                lesson.setStartDateTime(startTs);
                lesson.setEndDateTime(endTs);
            }
        }

        return list;
    }

    @Override
    public LessonCommon getLessonByLessonId(String lessonId) {
        LessonCommon lesson = lessonManageMapper.getLessonByLessonId(lessonId);
        if (lesson != null) {
            Timestamp startTs = lessonManageMapper.getStartDateByLessonId(lessonId);
            Timestamp endTs = lessonManageMapper.getEndDatetimeByLessonId(lessonId);
            lesson.setStartDateTime(startTs);
            lesson.setEndDateTime(endTs);
        }
        return lesson;
    }

    @Override
    public List<LessonCommon> getLessonList(String lessonId, String userId, int offset, int size) {
        if (lessonId.equals("-1")) {
            return getLessonListByUserId(userId, offset, size);
        } else {
            LessonCommon lesson = getLessonByLessonId(lessonId);
            List<LessonCommon> list = new ArrayList<>();
            list.add(lesson);
            return list;
        }
    }

    @Override
    public Map<String, Object> deleteLesson(String lessonId) {
        Map<String, Object> map = new HashMap<>();
        Timestamp ts = lessonManageMapper.getStartDateByLessonId(lessonId);
        if (null != ts) {
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(ts);
        	cal.add(Calendar.DATE, 1);
        	int date = cal.get(Calendar.DATE);
        	cal.setTime(new Date());
        	int now = cal.get(Calendar.DATE);
        	if (date > now) {
        		//String error = "A lesson can only be deleted at least on the day before it is started.";
        		String error = "删除课程失败：不能删除当天开始或者正在进行的课程";
        		map.put("flag", "-1");
        		map.put("error", error);
        		return map;
        	}
        }
        lessonManageMapper.deleteLesson(lessonId);
        map.put("flag", "1");
        return map;

    }

    @Override
    public List<Schedule> getSchedule(String lessonId, String scheduleId) {
        if (scheduleId.equals("-1")) {
        	List<Schedule> schList = lessonManageMapper.getScheduleByLessonId(lessonId);
        	for (Schedule sch : schList) {
        		sch.setTeachers(sysUserMapper.selectBySchId(sch.getSchId()));
        	}
            return schList;
        } else {
            List<Schedule> list = new ArrayList<>();
            
            Schedule sch = lessonManageMapper.getScheduleByScheduleId(scheduleId);
    		
    		sch.setTeachers(this.sysUserMapper.selectBySchId(sch.getSchId()));
            list.add(sch);
            return list;
        }
    }

    @Override
    public int putLesson(LessonCommon lesson) {
        String lessonId = lesson.getLessonId();
        if (lessonId.equals("-1")) {
            lessonId = Tools.generateID();
            lesson.setLessonId(lessonId);
            lessonManageMapper.insertLesson(lesson);

        } else {
            lessonManageMapper.updateLesson(lesson);

        }
        lessonId = lesson.getLessonId();
        List<SysUser> list = lesson.getAdminList();
        if (list != null && list.size() > 0) {
            List<String> l2 = new ArrayList<>();
            for (SysUser user : list) {
                LessonAdmin admin = new LessonAdmin();
                admin.setLessonId(lessonId);
                admin.setAdminId(user.getUserId());
                lessonAdminMapper.insertLessonAdmin(admin);
                l2.add(user.getUserId());
            }
            lessonAdminMapper.deleteLessonAdminNotInList(lessonId, l2);
        }

        List<SysUser> studentList = lesson.getStudentList();
        if (studentList != null && studentList.size() > 0) {
            List<String> l2 = new ArrayList<>();
            for (SysUser user : studentList) {
                LessonStudent student = new LessonStudent();
                student.setLessonId(lessonId);
                String userId = user.getUserId();
                student.setUserId(userId);
                student.setStatus("INVI");
                lessonStudentMapper.insertLessonStudent(student);
                l2.add(user.getUserId());
            }
            lessonStudentMapper.deleteLessonStudentNotInList(lessonId, l2);
        }
        return 1;
    }

    @Override
    public int getLessonsCount(String lessonId, String userId) {
        if (lessonId.equals("-1")) {
            return lessonManageMapper.getLessonsCount(userId);
        } else {
            LessonCommon lesson = lessonManageMapper.getLessonByLessonId(lessonId);
            return (lesson == null) ? 0 : 1;
        }
    }

	@Override
	public List<LessonCommon> getLessonList(String searchKey, String userId, Page page) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("searchKey", searchKey);
		paramMap.put("userId", userId);
		List<LessonCommon> lessList =  this.lessonManageMapper.searchByRoleListPage(userId, searchKey, page);
		for (LessonCommon lesson : lessList) {
			Timestamp startTs = lessonManageMapper.getStartDateByLessonId(lesson.getLessonId());
			Timestamp endTs = lessonManageMapper.getEndDatetimeByLessonId(lesson.getLessonId());
			lesson.setStartDateTime(startTs);
			lesson.setEndDateTime(endTs);
		}
		return lessList;
	}

    @Override
    public Timestamp getLessonStartDatetime(String lessonId) {
        return lessonManageMapper.getStartDateByLessonId(lessonId);
    }

    @Override
    public int getLessonStatus(String lessonId) {
        return lessonManageMapper.getLessonStatus(lessonId);
    }

    @Override
    public List<FileContent> getFilesByLessonId(Map<String, Object> map) {
        return lessonManageMapper.getFilesByLessonId(map);
    }

    @Override
    public int getFilesCountByLessonId(Map<String, Object> map) {
        return lessonManageMapper.getFilesCountByLessonId(map);
    }
}
