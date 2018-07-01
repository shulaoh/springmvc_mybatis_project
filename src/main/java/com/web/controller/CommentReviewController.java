package com.web.controller;


import com.web.service.ICommentReviewService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class CommentReviewController {

    @Resource
    private ICommentReviewService commentReviewService;

    private static Logger logger = Logger.getLogger(CommentReviewController.class);


    @RequestMapping({"/getCommentReview"})
    @ResponseBody
    public Map<String, Object> getCommentReview(@RequestParam String lessonId, String scheduleId) {
        Map<String, Object> map = commentReviewService.getCommentReview(lessonId, scheduleId);
        return map;
    }
}
