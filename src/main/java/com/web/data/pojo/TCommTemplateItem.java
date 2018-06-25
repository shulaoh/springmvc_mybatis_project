package com.web.data.pojo;

public class TCommTemplateItem {
    private String tempItemId;

    private String tempItemName;

    private String tempItemType;

    private Integer tempItemValue;

    private String tempItemText;

    private Integer tempItemEnable;

    public String getTempItemId() {
        return tempItemId;
    }

    public void setTempItemId(String tempItemId) {
        this.tempItemId = tempItemId == null ? null : tempItemId.trim();
    }

    public String getTempItemName() {
        return tempItemName;
    }

    public void setTempItemName(String tempItemName) {
        this.tempItemName = tempItemName == null ? null : tempItemName.trim();
    }

    public String getTempItemType() {
        return tempItemType;
    }

    public void setTempItemType(String tempItemType) {
        this.tempItemType = tempItemType == null ? null : tempItemType.trim();
    }

    public Integer getTempItemValue() {
        return tempItemValue;
    }

    public void setTempItemValue(Integer tempItemValue) {
        this.tempItemValue = tempItemValue;
    }

    public String getTempItemText() {
        return tempItemText;
    }

    public void setTempItemText(String tempItemText) {
        this.tempItemText = tempItemText == null ? null : tempItemText.trim();
    }

    public Integer getTempItemEnable() {
        return tempItemEnable;
    }

    public void setTempItemEnable(Integer tempItemEnable) {
        this.tempItemEnable = tempItemEnable;
    }
}