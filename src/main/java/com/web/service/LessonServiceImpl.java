package com.web.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import com.web.data.mapper.LessonManageMapper;
import com.web.data.pojo.SysUser;
import com.web.utils.Tools;
import org.springframework.stereotype.Service;

import com.web.data.mapper.LessonMapper;
import com.web.data.pojo.Lession;
import com.web.utils.Page;

@Service("lessonService")
public class LessonServiceImpl implements ILessonService {

	@Resource
	private LessonMapper lessonMapper;

	@Resource
	private LessonManageMapper lessonManageMapper;

	public List<Lession> getLessons(Page page, String userId, String oprType, String lessonType,
			String lessonNameKeyword) {
		List list;
		HashMap map = new HashMap();
		map.put("page", page);
		map.put("userId", userId);
		if ((lessonNameKeyword != null) && (lessonNameKeyword.length() > 0)) {
			map.put("lessonNameKeyword", "%" + lessonNameKeyword + "%");
		}
		if (!(lessonType.equalsIgnoreCase("ALL"))) {
			map.put("lessonType", lessonType);
		}
		if (oprType.equalsIgnoreCase("ALL")) {
			list = this.lessonMapper.selectAllLessonByUserId(map);
		} else if (oprType.equalsIgnoreCase("MY")) {
			list = this.lessonMapper.selectMyLessonAsStudent(map);
		} else if (oprType.equalsIgnoreCase("TEA")) {
			//list = this.lessonMapper.selectLessionsByTEATypeListPage(map);
			list = lessonMapper.selectLessonByAdminId(map);
		} else {
			map.put("oprType", oprType);
			list = this.lessonMapper.selectLessionsByStuTypeListPage(map);
		}
		for (Iterator localIterator = list.iterator(); localIterator.hasNext();) {
			Lession temp = (Lession) localIterator.next();

			Timestamp startTs = lessonManageMapper.getStartDateByLessonId(temp.getLessonId());
			Timestamp endTs = lessonManageMapper.getEndDatetimeByLessonId(temp.getLessonId());
			temp.setStime(Tools.date2Str(startTs, "yyyy-MM-dd HH:mm"));
			temp.setEtime(Tools.date2Str(endTs, "yyyy-MM-dd HH:mm"));
			List<SysUser> adminList = lessonManageMapper.getAdminListByLessonId(temp.getLessonId());
			temp.setAdminList(adminList);
			map.put("lessonId", temp.getLessonId());
			map.put("targetType", "LES");
			map.put("targetId", temp.getLessonId());
			int commentFlag = this.lessonMapper.getcommentFlag(map);
			if (commentFlag > 0) {
				temp.setCommentFlag("Y");

			} else {
				temp.setCommentFlag("N");
			}

		}

		return list;
	}

	public int joinLesson(String userId, String lessonId) {
		HashMap map = new HashMap();
		map.put("userId", userId);
		map.put("lessonId", lessonId);
		String couJoinStatus = this.lessonMapper.getLessonStuStatus(map);
		if (couJoinStatus == null) {
			this.lessonMapper.insertLessonStuStatus(map);
			return 2;
		}
		if (couJoinStatus.equalsIgnoreCase("INVI")) {
			this.lessonMapper.updateLessonStuStatus(map);
			return 1;
		}
		if (couJoinStatus.equalsIgnoreCase("WAIT")) {
			return 2;
		}
		return 1;
	}

	public Lession selectLessionById(String lessonId, String lessonStatus) {
		HashMap map = new HashMap();
		map.put("lessonId", lessonId);
		map.put("lessonStatus", lessonStatus);
		return this.lessonMapper.selectLessionById(map);
	}

	public int updateLessonStatus(int lessonStatus, String lessonId) {
		Lession less = new Lession();
		less.setLessonId(lessonId);
		less.setLessonStatus(lessonStatus);
		less.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return this.lessonMapper.updateLessonStatus(less);
	}

	public List<Lession> selectLessionsByUserIdListPage(Page page, String userId) {
		HashMap map = new HashMap();
		map.put("page", page);
		if ((userId != null) && (userId.length() > 0)) {
			map.put("userId", userId);
		}
		return this.lessonMapper.selectLessionsByUserIdListPage(map);
	}

	public int insertLess(Lession less) {
		less.setCreateTime(new Timestamp(System.currentTimeMillis()));
		less.setUpdateTime(less.getCreateTime());
		return this.lessonMapper.insertLess(less);
	}

	public int insertLessStu(String lessId, String userId) {
		HashMap map = new HashMap();
		map.put("lessId", lessId);
		map.put("userId", userId);
		return this.lessonMapper.insertLessStu(map);
	}

	public int updateByPrimaryKeySelective(Lession less) {
		less.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return this.lessonMapper.updateByPrimaryKeySelective(less);
	}

	@Override
	public String getCurUserRole(String userId, String lessonId, String schId) {
		return this.lessonMapper.getCurUserRole(userId, lessonId, schId);
	}
}