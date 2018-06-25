package com.web.service;

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
	
	List<UserView> getUserList(Page paramPage, String searchKey);
	
	UserView getUserByUserId(String paramString);
	
	List<UserView> getUserListByRole(String searchKey, String urole, Page page);
	
	UserView userCheckIn(String loginId);
	
	UserView userCheckInByWeixinID(String webchatId);
	

  

}