package com.web.service;

import java.io.File;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.data.bean.CommImportItem;
import com.web.data.bean.CommType;
import com.web.data.bean.CommentList;
import com.web.data.pojo.Comment;
import com.web.data.pojo.SysUser;
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

	TCommTemplateGroup importCommItems(SysUser user, File excelFile);
	
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

	/**
	 * 获取指定目标的历史评价信息（支持课程与日程）
	 * @param page
	 * @param userId
	 * @param lessonId
	 * @param schId
	 * @param targetId
	 * @param targetType
	 * @return
	 */
	List<CommentList> selectCommentsListPage(Page page, String userId, String lessonId, String schId, String targetId,
			String targetType);

}