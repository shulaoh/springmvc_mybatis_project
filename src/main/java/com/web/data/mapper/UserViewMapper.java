package com.web.data.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;
import com.web.utils.Page;

public interface UserViewMapper {
	/**
	 * check是否受邀用户
	 * 
	 * @param paramHashMap
	 * @return 非受邀用户return null
	 */
	UserView getInvitedUser(Map<String, Object> paramHashMap);

	List<UserView> selectUserListPage(HashMap<String, Object> paramHashMap);

	UserView getUserByUserId(String paramString);

	List<UserView> getUserByRoleListPage(@Param("searchKey")String searchKey, @Param("adminFlag")String adminFlag,@Param("adminFlag2")String adminFlag2, @Param("page") Page page);

	UserView userCheckIn(String paramString);

	UserView userCheckInByWeixinID(String paramString);

	int userCheck(HashMap<String, Object> paramHashMap);

	List<UserView> getUsersByPhone(List<String> phones);

	List<UserView> getUsersByEmail(List<String> emails);
}