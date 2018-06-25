package com.web.service;

import com.web.data.mapper.LeaveMapper;
import com.web.data.pojo.Leave;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

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
    return this.leaveMapper.updateLeave(leave);
  }

  public List<Leave> selectLeaveList(String schId)
  {
    return this.leaveMapper.selectLeaveList(schId);
  }
}