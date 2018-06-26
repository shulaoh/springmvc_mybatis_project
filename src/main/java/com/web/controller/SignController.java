package com.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.web.data.bean.SignInfo;
import com.web.data.pojo.Sign;
import com.web.data.pojo.SysUser;
import com.web.service.ISignService;
import com.web.utils.Const;
import com.web.utils.DataDesc;
import com.web.utils.ExcelUtil;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;

@Controller
public class SignController {
	private static Logger logger = Logger.getLogger(SignController.class);

	@Resource
	private ISignService signService;

	@RequestMapping({ "/lessonStuSign" })
	@ResponseBody
	public Map<String, Object> lessonStuSign(String lessonId, String schId, String signPicUrl, HttpSession session) {
		String tid = "tid_lesson_stu_sign";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Sign sign = new Sign();
		String userId = ((SysUser) session.getAttribute("userSession")).getUserId();
		sign.setUserId(userId);
		sign.setLessonId(lessonId);
		if (schId == null)
			schId = "";
		sign.setSchId(schId);
		sign.setSignPicUrl(signPicUrl);
		try {
			int ret = this.signService.insert(sign);
			if (ret > 0) {
				result.setRetcode(1);
				logger.info("用户(userId=" + userId + ")签到成功，课程ID：" + lessonId + "日程ID：" + schId);
				return map;
			}

			result.setRetcode(-1);
			result.setRetmsg("签到错误");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			logger.error("用户(userId=" + userId + ")签到失败，课程ID：" + lessonId + "日程ID：" + schId + "失败原因：" + e);
			result.setRetmsg("签到错误" + e);
		}
		return map;
	}

	@RequestMapping({ "/getSignListBySchId" })
	@ResponseBody
	public Map<String, Object> getSignListBySchId(HttpServletRequest request,@RequestParam String schId, Integer showCount, Integer pageNum) {
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum == null ? 1 : pageNum.intValue());
		page.setShowCount(showCount == null ? 10 : showCount.intValue());
		String imagePath = request.getServletContext().getRealPath("/image/");
		try {
			List<SignInfo> data = this.signService.getSignListBySchId(schId, page);

			if (data != null && data.size() >= 0) {
				for (int i = 0; i < data.size(); ++i) {
					data.get(i).setSignPicUrl(
							imagePath + data.get(i).getSignPicUrl().replaceAll("\"", ""));
				}
				boolean paging = page.caculatePageing();
				DataDesc datadesc = new DataDesc();
				datadesc.setPaging(paging);
				datadesc.setPage(page);
				map.put("datadesc", datadesc);
				result.setRetcode(1);
				map.put("data", data);
				return map;
			}

			result.setRetcode(-1);
			result.setRetmsg("未获取到签到信息");
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			logger.error("日程(schId=" + schId + ")签到信息获取失败，失败原因：" + e);
			result.setRetmsg("签到错误" + e);
		}
		return map;
	}
	@RequestMapping({ "/exportLessSignInfo" })
	@ResponseBody
	public ResponseEntity<byte[]> exportLessSignInfo(HttpServletRequest request, @RequestParam String lessId) {
			List<SignInfo> data = this.signService.getSignListByLessId(lessId);
			String imagePath = request.getServletContext().getRealPath("/image/");
			/*for (int i = 0; i < data.size(); ++i) {
				if (StringUtils.isNotEmpty(data.get(i).getSignPicUrl())) {
					data.get(i).setSignPicUrl(
							imagePath + data.get(i).getSignPicUrl().replaceAll("\"", ""));
				}
			}*/
	        String path = request.getServletContext().getRealPath("/tmp/");
	        String fileName = "signInfo_" + lessId + ".xls";
	        File file = new File(path + File.separator + fileName);
	        if (ExcelUtil.exportSignToExcel(data, file)) {
	        	HttpHeaders headers = new HttpHeaders();
	        	
	        	headers.setContentDispositionFormData("attachment", fileName);
	        	
	        	headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        	try {
	        		return new ResponseEntity(FileUtils.readFileToByteArray(file),
	        				headers, HttpStatus.CREATED);
	        	} catch (IOException e) {
	        		logger.info("导出失败：lessId=" + lessId + e.getMessage());
	        	}
	        }
	        return null;
	}

}