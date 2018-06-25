package com.web.service;

import java.util.List;

import com.web.data.bean.SignInfo;
import com.web.data.pojo.Sign;
import com.web.utils.Page;

public abstract interface ISignService
{
  public abstract int insert(Sign paramSign);

  List<SignInfo> getSignListBySchId(String schId, Page page);
  
  List<SignInfo> getSignListByLessId(String lessId);
  
}