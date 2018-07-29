package com.web.controller;


import com.web.data.bean.SignInfo;
import com.web.service.ICommentReviewService;
import com.web.utils.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommentReviewController {

    @Resource
    private ICommentReviewService commentReviewService;

    private static Logger logger = Logger.getLogger(CommentReviewController.class);


    @RequestMapping({"/getCommentReview"})
    @ResponseBody
    public Map<String, Object> getCommentReview(@RequestParam String lessonId, String scheduleId, String reviewType) {
    	ResourceDesc rdesc = new ResourceDesc();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Result result = new Result();
		map.put("result", result);
		try {
			DataDesc datadesc = new DataDesc();
			Map<String, Object> data = null;
			data = commentReviewService.getCommentReview(lessonId, scheduleId, reviewType);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			logger.info("获取评价信息失败" + e.getMessage());
			result.setRetcode(-1);
			result.setRetmsg("获取评价信息失败" + e);
		}
		return map;
    }

	@RequestMapping({ "/exportCommentReview" })
	@ResponseBody
	public ResponseEntity<byte[]> exportCommentReview(HttpServletRequest request, @RequestParam String lessonId,
													   String scheduleId, String reviewType) {

		Map<String, Object> data = this.commentReviewService.getCommentReview(lessonId, scheduleId, reviewType);

		String path = request.getServletContext().getRealPath("/tmp/");
		String fileName = "comment_review_" + Tools.generateID() + ".xls";
		File file = new File(path + File.separator + fileName);
		if (ExcelUtil.exportReviewToExcel(data, file)) {
			HttpHeaders headers = new HttpHeaders();

			headers.setContentDispositionFormData("attachment", fileName);

			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			try {
				return new ResponseEntity(FileUtils.readFileToByteArray(file),
						headers, HttpStatus.CREATED);
			} catch (IOException e) {
				logger.info("导出失败：lessonId=" + lessonId + "scheduleId = " + scheduleId + "reviewType = " + reviewType + ". " + e.getMessage());
			}
		}
		return null;
	}
}
