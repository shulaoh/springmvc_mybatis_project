package com.web.data.mapper;

import com.web.data.pojo.FileContent;
import org.apache.ibatis.annotations.Param;

import com.web.data.pojo.ScheduleFileContent;

import java.util.List;

public interface ScheduleFileContentMapper {
	
	void insertScheduleFileContent(ScheduleFileContent sfc);
    void deleteScheduleFileContentByKey(@Param("scheduleId") String scheduleId, @Param("fileId") String fileId);
    void deleteAllFileContentsByScheduleId(@Param("scheduleId") String scheduleId);
    List<ScheduleFileContent> getFileContentBySheduleId(@Param("scheduleId") String scheduleId);

}
