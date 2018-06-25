package com.web.data.bean;

public class CommImportItem {

	private String roleType;
	
	private String targetType;
	
	private String itemName;
	
	private String itemType;

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.itemName + "-" + this.itemType + "-" + this.roleType + "-" + this.targetType;
	}
	
	
}
