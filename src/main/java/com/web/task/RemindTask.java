package com.web.task;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.web.data.bean.SchRemainInfo;
import com.web.data.mapper.ScheduleMapper;

@Component
public class RemindTask {

	/**@Resource
	private ScheduleMapper scheduleMapper;
	
	@Scheduled(cron="0 * 13 * * ?")
	public void scheduleRemind() {
		// 1. get all of schedule that will be start in next 4 hours
		String startTime= "";
		String endTime = "";
		List<SchRemainInfo> remainInfos = scheduleMapper.getRemainInfo(startTime, endTime);
		// 2. loop all student and teacher
		
		// 3. send message
	}**/
}
