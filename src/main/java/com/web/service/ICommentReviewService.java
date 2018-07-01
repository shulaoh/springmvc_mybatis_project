package com.web.service;

import com.web.data.pojo.CommentReview;

import java.util.List;
import java.util.Map;

public interface ICommentReviewService {

    Map<String, Object> getCommentReview(String lessonId, String scheduleId);
}
