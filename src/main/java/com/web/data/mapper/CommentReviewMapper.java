package com.web.data.mapper;

import com.web.data.pojo.CommentReview;

import java.util.List;
import java.util.Map;

public interface CommentReviewMapper {
    List<CommentReview> getCommentReview(Map<String, Object> map);
}
