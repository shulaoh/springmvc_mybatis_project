package com.web.service;

import java.io.File;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.data.bean.CommImportItem;
import com.web.data.bean.CommType;
import com.web.data.bean.CommentList;
import com.web.data.pojo.Comment;
import com.web.data.pojo.TCommTemplateGroup;
import com.web.data.pojo.TCommTemplateItem;
import com.web.utils.Page;

public abstract interface ICommentService {

	/**
	 * 后台WEB获取当前添加/修改的的课程和日程的评价可关联的评价模板列表
	 * @param roleType
	 * @param targetType
	 * @param commTempId
	 * @return
	 */
	List<TCommTemplateItem> selectTemplates(String roleType, String targetType, String commTempId);

	/**
	 * 获取指定目标的历史评价信息（支持课程与日程）
	 * @param paramPage
	 * @param paramString1
	 * @param paramString2
	 * @param paramString3
	 * @param paramString4
	 * @return
	 */
	List<CommentList> selectCommentsListPage(Page paramPage, String paramString1, String paramString2, String paramString3,
			String paramString4);

	int insert(Comment paramComment);
	
	int insert(List<Comment> paramComment);

	/**
	 * 指定评价对象的评价类型信息
	 * @param roleType
	 * @param targetType
	 * @param commTempId
	 * @return
	 */
	List<CommType> selectTargets(String roleType, String targetType, String commTempId);

	TCommTemplateGroup importCommItems(File excelFile);
	
	/**
	 * 查询所有的评价模板，供创建课程使用
	 * 查询公共的和自己创建的模板
	 * @return
	 */
	List<TCommTemplateGroup> getTemplateGroupList(String userId);
	
	/**
	 * 查询指定模板id的评价条目明细
	 * @param tempGroupId
	 * @return
	 */
	List<CommImportItem> selectCommGroupItem(String tempGroupId);
}