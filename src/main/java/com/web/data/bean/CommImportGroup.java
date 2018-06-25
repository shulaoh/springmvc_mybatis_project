package com.web.data.bean;

import java.util.ArrayList;
import java.util.List;

public class CommImportGroup {

	private String groupName;
	
	private List<CommImportItem> items;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<CommImportItem> getItems() {
		return items;
	}

	public void setItems(List<CommImportItem> items) {
		this.items = items;
	}
	
	public void addItem(CommImportItem item) {
		if (items == null) {
			items = new ArrayList<CommImportItem>();
		}
		items.add(item);
	}
	
	public int getItemSize() {
		if (items == null) {
			return 0;
		}
		return items.size();
	}
	
}
