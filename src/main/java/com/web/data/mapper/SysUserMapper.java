package com.web.data.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.data.pojo.SysConfig;
import com.web.data.pojo.SysUser;

public abstract interface SysUserMapper
{
  public abstract int deleteByPrimaryKey(String paramString);

  public abstract int insert(SysUser paramSysUser);

  public abstract SysUser selectByPrimaryKey(String paramString);

  public abstract SysUser userCheckIn(String paramString);

  public abstract SysUser userCheckInByWeixinID(String paramString);

  public abstract int updatePassword(SysUser paramSysUser);

  public abstract int updateByPrimaryKeySelective(SysUser paramSysUser);

  public abstract int updateUserStatus(HashMap<String, Object> paramHashMap);

  public abstract List<SysConfig> getSysConfig();

  public abstract List<SysUser> selectUserListPage(HashMap<String, Object> paramHashMap);

  public abstract int getWechatIdFlag(String paramString);
  
  int insertBatch(List<SysUser> records);
  
  int checkPhone(List<String> records);
  
  int checkEmail(List<String> records);
  
  List<SysUser> selectBySchId(String schId);
}