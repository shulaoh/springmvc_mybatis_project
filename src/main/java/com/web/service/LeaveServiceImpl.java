package com.web.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.data.mapper.LeaveMapper;
import com.web.data.pojo.Leave;
import com.web.utils.Page;

@Service("leaveService")
public class LeaveServiceImpl
  implements ILeaveService
{

  @Resource
  private LeaveMapper leaveMapper;

  public int addLeave(HashMap<String, String> paramMap)
  {
    return this.leaveMapper.addLeave(paramMap);
  }

  public int updateLeave(Leave leave)
  {
	  if (null != leave.getApproveUserId() && leave.getApproveUserId().length() > 0) {
		  leave.setApproveDate(new java.sql.Timestamp(System.currentTimeMillis()));
	  }
    return this.leaveMapper.updateLeave(leave);
  }

  public List<Leave> selectLeaveList(String schId)
  {
    return this.leaveMapper.selectLeaveList(schId);
  }

@Override
public List getMyLeave(String schId, String userId) {
	return this.leaveMapper.getMyLeave(schId, userId);
}
}