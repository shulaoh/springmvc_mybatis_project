package com.web.data.mapper;

import com.web.data.pojo.Leave;
import com.web.utils.Page;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public abstract interface LeaveMapper {
	public abstract int addLeave(HashMap<String, String> paramHashMap);

	public abstract int updateLeave(Leave paramLeave);

	public abstract List<Leave> selectLeaveList(String paramString);

	public abstract List getMyLeave(@Param("schId") String schId, @Param("userId") String userId);
}