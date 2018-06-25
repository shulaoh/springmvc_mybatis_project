package com.web.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.web.data.pojo.CommTemplate;
import com.web.data.pojo.Comment;
import com.web.data.pojo.Target;
import com.web.utils.Page;

//@Service("commentService")
public class CommentServiceImpl
  //implements ICommentService
{

//  @Resource
////  private CommentMapper commentMapper;
//
//  public List<Target> selectTargets(String roleType, String targetType, String commTempIds)
//  {
//    HashMap map = new HashMap();
//    map.put("roleType", roleType);
//    map.put("targetType", targetType);
//    commTempIds = commTempIds.replace(",", "','");
//    commTempIds = "'" + commTempIds + "'";
//
//    map.put("commTempIds", commTempIds);
//    return this.commentMapper.selectTargets(map);
//  }
//
//  public int insert(Comment comment)
//  {
//    return this.commentMapper.insert(comment);
//  }
//
//  public List<Comment> selectCommentsListPage(Page page, String lessonId, String schId, String targetId, String targetType)
//  {
//    HashMap map = new HashMap();
//    map.put("page", page);
//    if ((schId != null) && (schId.length() > 0))
//    {
//      map.put("schId", schId);
//    }
//    map.put("lessonId", lessonId);
//    map.put("targetId", targetId);
//    map.put("targetType", targetType);
//    return this.commentMapper.selectCommentsListPage(map);
//  }
//
//  public List<CommTemplate> selectTemplates(String roleType, String targetType)
//  {
//    HashMap map = new HashMap();
//    map.put("roleType", roleType);
//    map.put("targetType", targetType);
//    List templist = this.commentMapper.selectTemplates(map);
//    if ((templist != null) && (templist.size() > 0))
//    {
//      for (int i = 0; i < templist.size(); ++i)
//      {
//        CommTemplate commtempone = (CommTemplate)templist.get(i);
//        HashMap map2 = new HashMap();
//        map2.put("tempId", commtempone.getTempId());
//        List targetlist = this.commentMapper.selectTargetsByTempId(map2);
//        commtempone.setTagets(targetlist);
//      }
//    }
//    return templist;
//  }
}