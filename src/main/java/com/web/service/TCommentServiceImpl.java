package com.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.web.data.bean.CommImportGroup;
import com.web.data.bean.CommImportItem;
import com.web.data.bean.CommType;
import com.web.data.bean.CommentList;
import com.web.data.mapper.CommentMapper;
import com.web.data.mapper.TCommTemplateGroupMapper;
import com.web.data.mapper.TCommTemplateItemMapper;
import com.web.data.mapper.TCommTemplateRelateMapper;
import com.web.data.pojo.Comment;
import com.web.data.pojo.TCommTemplateGroup;
import com.web.data.pojo.TCommTemplateItem;
import com.web.data.pojo.TCommTemplateRelate;
import com.web.utils.ExcelUtil;
import com.web.utils.Page;
import com.web.utils.Tools;

@Service("commentService")
public class TCommentServiceImpl implements ICommentService {

	@Resource
	private TCommTemplateItemMapper commentItemMapper;

	@Resource
	private CommentMapper commentMapper;
	
	@Resource
	TCommTemplateGroupMapper groupMapper;
	
	@Resource
	TCommTemplateRelateMapper relateMapper;

	@Override
	public List<CommType> selectTargets(String roleType, String targetType, String commTempId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roleType", roleType);
		map.put("targetType", targetType);
		map.put("commTempId", commTempId);
		return this.commentItemMapper.selectCommTypes(map);
	}

	@Override
	public int insert(Comment comment) {
		return this.commentMapper.insert(comment);
	}

	@Override
	public List<CommentList> selectCommentsListPage(Page page, String lessonId, String schId, String targetId,
			String targetType) {
		HashMap map = new HashMap();
		map.put("page", page);
		if ((schId != null) && (schId.length() > 0)) {
			map.put("schId", schId);
		}
		map.put("lessonId", lessonId);
		map.put("targetId", targetId);
		map.put("targetType", targetType);
		return this.commentMapper.selectCommentsListPage(map);
	}

	@Override
	public List<TCommTemplateItem> selectTemplates(String roleType, String targetType, String commTempId) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("roleType", roleType);
		map.put("targetType", targetType);
		map.put("commTempId", commTempId);
		return this.commentItemMapper.selectCommTemps(map);
	}

	@Override
	public int insert(List<Comment> records) {
		
		return this.commentMapper.insertBatch(records);
	}

	@Override
	public TCommTemplateGroup importCommItems(File excelFile) {
		List<CommImportGroup> groups = new ArrayList<CommImportGroup>();
		boolean result = ExcelUtil.importCommItem(excelFile, groups);
		if (result) {
			
			List<TCommTemplateGroup> groupList = new ArrayList<TCommTemplateGroup>();
			List<TCommTemplateItem> itemList = new ArrayList<TCommTemplateItem>();
			List<TCommTemplateRelate> relateList = new ArrayList<TCommTemplateRelate>();
			TCommTemplateGroup tGroup = null;
			TCommTemplateItem tItem = null;
			TCommTemplateRelate tRelate = null;
			
			for (CommImportGroup group : groups) {
				
				tGroup = new TCommTemplateGroup();
				tGroup.setTempGroupId(Tools.generateID());
				tGroup.setTempGroupName(group.getGroupName());
				
				for(CommImportItem item : group.getItems()) {
					
					tItem = new TCommTemplateItem();
					tItem.setTempItemId(Tools.generateID());
					tItem.setTempItemName(item.getItemName());
					tItem.setTempItemType(item.getItemType());
					
					tRelate = new TCommTemplateRelate();
					tRelate.setTempRelateId(Tools.generateID());
					tRelate.setRoleType(item.getRoleType());
					tRelate.setTargetType(item.getTargetType());
					tRelate.setTempGroupId(tGroup.getTempGroupId());
					tRelate.setTempItemId(tItem.getTempItemId());
					
					itemList.add(tItem);
					relateList.add(tRelate);
				}
				
				groupList.add(tGroup);
			}
			groupMapper.insertBatch(groupList);
			commentItemMapper.insertBatch(itemList);
			relateMapper.insertBatch(relateList);
			// 这里暂时支持单个评价模板
			return tGroup;
		}
		return null;
	}

	public List<TCommTemplateGroup> getTemplateGroupList(String userId) {
		return this.groupMapper.selectAll(userId);
	}

	@Override
	public List<CommImportItem> selectCommGroupItem(String tempGroupId) {
		return groupMapper.selectCommGroupItem(tempGroupId);
	}
	
}