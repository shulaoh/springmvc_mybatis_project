package com.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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
import com.web.utils.Tools;

@Controller
public class SignController {
	private static Logger logger = Logger.getLogger(SignController.class);

	@Resource
	private ISignService signService;
	
	@Value( "${server.address}" )
	private String serverAddress;
	
	@Value( "${file.folder}" )
	private String fileFloder;

	@RequestMapping({ "/getSchSignInfo" })
	@ResponseBody
	public Map<String, Object> getSchSignInfo(@RequestParam String lessonId, @RequestParam String scheduleId, HttpSession session) {
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		String userId = ((SysUser) session.getAttribute("userSession")).getUserId();

		try {
			SignInfo signInfo = signService.getSignByScheduleIdForCurrentUser(lessonId, scheduleId, userId);
			if (signInfo == null) {
				result.setRetcode(1);
				map.put("data", signInfo);
				result.setRetmsg("本人没有签到信息");
			} else {
				if (null != signInfo.getSignPicUrl() && signInfo.getSignPicUrl().length() > 0) {
					signInfo.setSignPicUrl(
							"cnooc_training/image/" + signInfo.getSignPicUrl().replaceAll("\"", ""));
				}
				logger.info("获取用户(userId=" + userId + ")签到信息成功，课程ID：" + lessonId + "日程ID：" + scheduleId);

				result.setRetcode(1);
				map.put("data", signInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			logger.error("获取用户(userId=" + userId + ")签到信息失败，课程ID：" + lessonId + "日程ID：" + scheduleId + "失败原因：" + e);
			result.setRetmsg("获取签到信息错误" + e);
		}
		return map;
	}

	@RequestMapping({ "/lessonStuSign" })
	@ResponseBody
	public Map<String, Object> lessonStuSign(HttpServletRequest request,String lessonId, String schId, String signPicUrl, HttpSession session) {
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
		// 将客户签名图片顺时针旋转270度
		String signPicFileName = request.getServletContext().getRealPath("/image/") + signPicUrl.replaceAll("\"", "");
		
		sign.setSignPicUrl(Tools.rorate(signPicFileName));
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

    //{"lessId":"6cd55affb4d242c49d399814bfca2177","schId":"09764e7c65164b56a764179f71054361","showCount":"10","pageNum":"1"}
	@RequestMapping({ "/getSignList" })
	@ResponseBody
	public Map<String, Object> getSignList(HttpServletRequest request, @RequestParam String lessId,@RequestParam String schId, Integer showCount, Integer pageNum) {
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum == null ? 1 : pageNum.intValue());
		page.setShowCount(showCount == null ? 10 : showCount.intValue());
		try {
			List<SignInfo> data = this.signService.getSignList(lessId, schId, page);

			if (data != null && data.size() >= 0) {
				SignInfo signInfo = null;
				for (int i = 0; i < data.size(); ++i) {
					signInfo = data.get(i);
					if (null != signInfo.getSignPicUrl() && signInfo.getSignPicUrl().length() > 0) {
						signInfo.setSignPicUrl(
								"cnooc_training/image/" + signInfo.getSignPicUrl().replaceAll("\"", ""));
					}
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
			result.setRetcode(-1);
			logger.error("课程(lessId=" + lessId + ") 日程(schId=" + schId + ")签到信息获取失败，失败原因：" + e.getMessage());
			result.setRetmsg("签到信息获取失败:" + e);
		}
		return map;
	}
	@RequestMapping({ "/exportLessSignInfo" })
	@ResponseBody
	public ResponseEntity<byte[]> exportLessSignInfo(HttpServletRequest request, @RequestParam String lessId) {
			List<SignInfo> data = this.signService.getSignListByLessId(lessId);
			String imagePath = request.getServletContext().getRealPath("/image/");
			for (SignInfo sInfo : data) {
				
				if (StringUtils.isNotEmpty(sInfo.getSignPicUrl())) {
//					sInfo.setSignPicUrl(this.serverAddress + this.fileFloder + "/" + sInfo.getSignPicUrl().replaceAll("\"", ""));
					sInfo.setSignPicUrl(imagePath +sInfo.getSignPicUrl().replaceAll("\"", ""));
//					data.get(i).setSignPicUrl(
//							imagePath + data.get(i).getSignPicUrl().replaceAll("\"", ""));
					
				}
			}
	        String path = request.getServletContext().getRealPath("/tmp/");
	        String fileName = "signInfo_" + Tools.generateID() + ".xls";
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