package com.web.service;

import com.web.data.pojo.Leave;
import java.util.HashMap;
import java.util.List;

public abstract interface ILeaveService
{
  public abstract int addLeave(HashMap<String, String> paramHashMap);

  public abstract int updateLeave(Leave paramLeave);

  public abstract List<Leave> selectLeaveList(String paramString);
}