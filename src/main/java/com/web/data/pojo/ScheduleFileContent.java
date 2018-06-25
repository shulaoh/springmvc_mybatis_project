package com.web.data.pojo;

public class ScheduleFileContent {
	
	private String scheduleId;
	
	private String fileId;
	
	private FileContent fileContent;

	public FileContent getFileContent() {
		return fileContent;
	}

	public void setFileContent(FileContent fileContent) {
		this.fileContent = fileContent;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	

}
