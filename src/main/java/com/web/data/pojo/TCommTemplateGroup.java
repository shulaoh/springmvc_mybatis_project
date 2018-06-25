package com.web.data.pojo;

public class TCommTemplateGroup {
    private String tempGroupId;

    private String tempGroupName;

    private String tempGroupDecs;
    
    private String tempGroupOwerId;

    private Integer tempGroupEnable;

    public String getTempGroupId() {
        return tempGroupId;
    }

    public String getTempGroupOwerId() {
		return tempGroupOwerId;
	}

	public void setTempGroupOwerId(String tempGroupOwerId) {
		this.tempGroupOwerId = tempGroupOwerId;
	}

	public void setTempGroupId(String tempGroupId) {
        this.tempGroupId = tempGroupId == null ? null : tempGroupId.trim();
    }

    public String getTempGroupName() {
        return tempGroupName;
    }

    public void setTempGroupName(String tempGroupName) {
        this.tempGroupName = tempGroupName == null ? null : tempGroupName.trim();
    }

    public String getTempGroupDecs() {
        return tempGroupDecs;
    }

    public void setTempGroupDecs(String tempGroupDecs) {
        this.tempGroupDecs = tempGroupDecs == null ? null : tempGroupDecs.trim();
    }

    public Integer getTempGroupEnable() {
        return tempGroupEnable;
    }

    public void setTempGroupEnable(Integer tempGroupEnable) {
        this.tempGroupEnable = tempGroupEnable;
    }
}