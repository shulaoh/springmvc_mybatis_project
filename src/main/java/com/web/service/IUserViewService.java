package com.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.web.data.pojo.UserView;
import com.web.utils.Page;

public abstract interface IUserViewService
{
	/**
	 * check是否受邀用户
	 * 
	 * @param paramHashMap(userName,email,phone)
	 * @return 非受邀用户return null
	 */
	UserView getInvitedUser(Map<String, Object> paramHashMap);
	/**
	 * 用户管理一览页面查询用户
	 * @param searchKey 关键字
	 * @param companyId 公司
	 * @param adminFlag 角色
	 * @param paramPage 
	 * @return
	 */
	List<UserView> getUserList(Page paramPage, String searchKey, String companyId, String adminFlag);
	
	UserView getUserByUserId(String paramString);
	
	List<UserView> getUserListByRole(String searchKey, String urole, Page page);
	
	UserView userCheckIn(String loginId);
	
	UserView userCheckInByWeixinID(String webchatId);

	/**
	 * 查询可以作为讲师的用户，指定课程的管理员，创建者，学员除外。
	 * @param lessId
	 * @param searchKey
	 * @param page
	 * @return
	 */
	List<UserView> getTeaList(String lessId, String searchKey, Page page);

	int isTeacher(String userId, Date date);
	

  

}