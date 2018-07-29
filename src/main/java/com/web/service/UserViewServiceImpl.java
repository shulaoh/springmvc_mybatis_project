package com.web.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.data.mapper.UserViewMapper;
import com.web.data.pojo.UserView;
import com.web.utils.Const;
import com.web.utils.Page;
@Service("userViewService")
public class UserViewServiceImpl implements IUserViewService {

	@Resource
	private UserViewMapper userViewMapper;
	
	@Override
	public UserView getInvitedUser(Map<String, Object> paramHashMap) {
		return userViewMapper.getInvitedUser(paramHashMap);
	}

	@Override
	public List<UserView> getUserList(Page paramPage, String searchKey, String companyId, String adminFlag) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", paramPage);
		map.put("searchKey", searchKey);
		map.put("companyId", companyId);
		map.put("adminFlag", adminFlag);
		List<UserView> list = this.userViewMapper.selectUserListPage(map);
		if (list != null && list.size() > 0) {
			for (UserView uv : list) {
				if (uv.getCompanyId() != null && uv.getCompanyId().equals("000")) {
					uv.setCompanyName(uv.getRemark());
				}
			}
		}
		return list;
	}

	@Override
	public UserView getUserByUserId(String paramString) {
		UserView uv = userViewMapper.getUserByUserId(paramString);
		if (uv != null && uv.getCompanyId() != null && uv.getCompanyId().equals("000")) {
			uv.setCompanyName(uv.getRemark());
		}
		return uv;
	}

	@Override
	public List<UserView> getUserListByRole(String searchKey, String role, Page page) {
		if (role != null && Const.USER_ROLE_ADMIN.equalsIgnoreCase(role)) {
			return userViewMapper.getUserByRoleListPage(searchKey, new String[]{Const.USER_ROLE_20, Const.USER_ROLE_30, Const.USER_ROLE_0,
			Const.USER_ROLE_10, Const.USER_ROLE_100},page);
		} if (role != null && Const.USER_ROLE_TUT.equalsIgnoreCase(role)) {
			return userViewMapper.getUserByRoleListPage(searchKey, new String[]{Const.USER_ROLE_20, Const.USER_ROLE_10,Const.USER_ROLE_0},page);
		} else if (role != null && Const.USER_ROLE_0.equalsIgnoreCase(role)) {
			// 查所有的学员：改为除系统管理员以外的所有人
			return userViewMapper.getUserByRoleListPage(searchKey, new String[]{Const.USER_ROLE_30, Const.USER_ROLE_20, Const.USER_ROLE_10,Const.USER_ROLE_0},page);
		} else {
			return userViewMapper.getUserByRoleListPage(searchKey, new String[]{role}, page);
		}
	}

	@Override
	public List<UserView> getTeaList(String lessId, String searchKey, Page page) {
		return userViewMapper.getTeaListPage(lessId, searchKey, new String[]{Const.USER_ROLE_20, Const.USER_ROLE_10,Const.USER_ROLE_0},page);
	}
	
	@Override
	public UserView userCheckIn(String loginId) {
		return userViewMapper.userCheckIn(loginId);
	}

	@Override
	public UserView userCheckInByWeixinID(String webchatId) {
		return userViewMapper.userCheckInByWeixinID(webchatId);
	}

	@Override
	public int isTeacher(String userId, Date rangeDate) {
		
		return this.userViewMapper.isTeacher(userId, rangeDate);
	}
}
