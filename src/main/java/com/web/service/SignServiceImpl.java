package com.web.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.data.bean.SignInfo;
import com.web.data.mapper.SignMapper;
import com.web.data.pojo.Sign;
import com.web.utils.Page;

@Service("signService")
public class SignServiceImpl implements ISignService {

	@Resource
	private SignMapper signMapper;

	public int insert(Sign sign) {
		return this.signMapper.insert(sign);
	}

	public List<SignInfo> getSignList(String lessId, String schId, Page page) {
		return this.signMapper.selectSignListPage(lessId, schId, page);
	}
	public List<SignInfo> getSignListBySchId(String schId, Page page) {
		// return this.signMapper.selectSchSignListPage(schId, page);
		return null;
	}
	@Override
	public List<SignInfo> getSignListByLessId(String lessId) {
		return this.signMapper.selectLessSignList(lessId);
	}

	@Override
	public SignInfo getSignByScheduleIdForCurrentUser(String lessonId, String scheduleId, String userId) {
		return signMapper.getSignByScheduleIdForCurrentUser(lessonId, scheduleId, userId);
	}
}