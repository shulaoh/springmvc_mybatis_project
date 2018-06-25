package com.web.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.web.data.pojo.PersonalInfo;
import com.web.data.pojo.SysConfig;
import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;
import com.web.utils.Page;

public abstract interface IUserService
{
	
	int userRegist(UserView newUser);
	
	int addUser(UserView newUser);
	
	int updateUser(UserView newUser);
	
  public abstract SysUser getUserById(String paramString);

  public abstract SysUser userCheckIn(String paramString);

  public abstract SysUser userCheckInByWeixinID(String paramString);

  public abstract int updatePassword(SysUser paramSysUser);

  public abstract int updateByPrimaryKeySelective(SysUser paramSysUser);

  public abstract int updateUserStatus(String paramString1, String paramString2);

  public abstract int insert(SysUser paramSysUser);

  public abstract List<SysConfig> getSysConfig();

  public abstract List<SysUser> getUserList(Page paramPage, String searchKey);

  public abstract int getWechatIdFlag(String paramString);

  List<SysUser> importUesers(File userFile, List<String> errs);


}