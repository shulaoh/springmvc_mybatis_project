package com.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.data.pojo.LessonStudent;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;
import com.web.service.IPersonalService;
import com.web.service.IUserService;
import com.web.service.IUserViewService;
import com.web.utils.Const;
import com.web.utils.DataDesc;
import com.web.utils.Page;
import com.web.utils.ResourceDesc;
import com.web.utils.Result;
import com.web.utils.Tools;
import com.web.utils.WeChatUtil;

@Controller
public class UserController {

	@Resource
	private IUserService userService;

	@Resource
	private IPersonalService personalService;

	@Resource
	private IUserViewService userViewService;

	private static Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping({ "/userRegist" })
	@ResponseBody
	public Map<String, Object> userRegist(UserView sysUser, HttpSession session) {
		String tid = "tid_user_regist";

		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		// check input(no username, no webchatid or (no phone and no email))
		if (StringUtils.isEmptyOrWhitespaceOnly(sysUser.getUserName())
				|| (StringUtils.isEmptyOrWhitespaceOnly(sysUser.getEmail())
						&& StringUtils.isEmptyOrWhitespaceOnly(sysUser.getPhone()))
				|| StringUtils.isEmptyOrWhitespaceOnly(sysUser.getWechatId())) {
			result.setRetcode(-1);
			result.setRetmsg("用户信息不全");
			return map;
		}
		int registResult = -1;
		// set default status
		sysUser.setuStatus(Const.USER_STATUS_WAIT);
		if ((registResult = userService.userRegist(sysUser)) > 0) {
			session.setAttribute("userSession", sysUser);
			result.setRetcode(1);
			result.setRetmsg(session.getId());
		} else if (registResult == -2){
			result.setRetcode(-1);
			result.setRetmsg("用户电话或邮件重复");
		} else {
			result.setRetcode(-1);
			result.setRetmsg("注册用户失败");
		}

		return map;
	}

	@RequestMapping({ "/addUser" })
	@ResponseBody
	public Map<String, Object> addUser(UserView sysUser, HttpSession session) {
		String tid = "tid_user_regist";

		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		// check input(no username, no webchatid or (no phone and no email))
		if (StringUtils.isEmptyOrWhitespaceOnly(sysUser.getUserName())
				|| (StringUtils.isEmptyOrWhitespaceOnly(sysUser.getEmail())
						&& StringUtils.isEmptyOrWhitespaceOnly(sysUser.getPhone()))) {
			result.setRetcode(-1);
			result.setRetmsg("用户信息不全");
			return map;
		}
		int retCode = 0;
		sysUser.setuStatus(Const.USER_STATUS_INFO);
		if ((retCode = userService.addUser(sysUser)) > 0) {
			result.setRetcode(1);
			result.setRetmsg(sysUser.getUserId());
		} else {
			result.setRetcode(-1);
			if (retCode == -2) {
				result.setRetmsg("有相同姓名，邮箱，电话的用户存在，请与管理员联系");
			} else {
				result.setRetmsg("添加失败，请与管理员联系");
			}
		}
		return map;
	}

	@RequestMapping({ "/updateMyUserInfo" })
	@ResponseBody
	public Map<String, Object> updateMyUserInfo(UserView sysUser, HttpSession session) {
		String tid = "tid_update _my_user_info";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		UserView loginUser = (UserView) session.getAttribute("userSession");
		sysUser.setUserId(loginUser.getUserId());
		sysUser.setPersonId(loginUser.getPersonId());
		if ((sysUser.getUserPicUrl() == null) || (sysUser.getUserPicUrl().trim().length() == 0)) {
			sysUser.setUserPicUrl("https://lpg.tianmengit.com/cnooc_training/image/DEFAULT_UERR_HEAD_PIC.PNG");
		} else if (sysUser.getUserPicUrl().indexOf("http") < 0) {
			sysUser.setUserPicUrl(
					"https://lpg.tianmengit.com/cnooc_training/image/" + sysUser.getUserPicUrl().replaceAll("\"", ""));
		}
		if (this.userService.updateUser(sysUser) > 0) {
			result.setRetcode(1);
		} else {
			result.setRetcode(-1);
			result.setRetmsg("修改个人信息失败");
		}
		return map;
	}

	@RequestMapping({ "/updateMyPassword" })
	@ResponseBody
	public Map<String, Object> updateMyPassword(String oldPassword, String newPassword, HttpSession session) {
		String tid = "tid_update_my_password";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		SysUser sysUser = new SysUser();
		if (oldPassword.endsWith(((SysUser) session.getAttribute("userSession")).getPassword())) {
			sysUser.setPassword(newPassword);
			sysUser.setUserId(((SysUser) session.getAttribute("userSession")).getUserId());
			if (this.userService.updatePassword(sysUser) > 0) {
				((SysUser) session.getAttribute("userSession")).setPassword(newPassword);
				result.setRetcode(1);
				result.setRetmsg("修改密码成功");
			} else {
				result.setRetcode(-1);
				result.setRetmsg("修改密码失败");
			}
		}
		return map;
	}
	@RequestMapping({ "/updatePassword" })
	@ResponseBody
	public Map<String, Object> updatePassword(String userId, String oldPassword, String newPassword) {
		String tid = "tid_update_my_password";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		SysUser oldUser = this.userService.getUserById(userId);
		if (oldPassword.endsWith(oldUser.getPassword())) {
			oldUser.setPassword(newPassword);
			if (this.userService.updatePassword(oldUser) > 0) {
				result.setRetcode(1);
				result.setRetmsg("修改密码成功");
			} else {
				result.setRetcode(-1);
				result.setRetmsg("修改密码失败");
			}
		}
		return map;
	}
	@RequestMapping({ "/getMyUserInfo" })
	@ResponseBody
	public Map<String, Object> getMyUserInfo(HttpSession session) {
		String tid = "tid_get_my_user_info";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		result.setRetcode(1);
		map.put("result", result);
		map.put("datadesc", new DataDesc());
		HashMap data = new HashMap();
		UserView user = (UserView) session.getAttribute("userSession");
		UserView newUser = this.userViewService.getUserByUserId(user.getUserId());
		newUser.setRole(user.getRole());
		session.setAttribute("userSession", newUser);
		if (needToRegiste(newUser)) {
			result.setRetcode(2000);// 受邀用户注册
		}
		data.put("userInfo", newUser);
		map.put("data", data);
		return map;
	}
	/**
	 * 用户管理一览页面查询用户
	 * @param searchKey 关键字
	 * @param companyId 公司
	 * @param adminFlag 角色
	 * @param showCount 每页显示数量
	 * @param pageNum	页码
	 * @param session
	 * @return
	 */
	@RequestMapping({ "/getUserList" })
	@ResponseBody
	public Map<String, Object> getUserList(@RequestParam String searchKey, String companyId, String adminFlag,Integer showCount, Integer pageNum,
			HttpSession session) {
		String tid = "tid_get_userList";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum == null ? 1 : pageNum.intValue());
		page.setShowCount(showCount == null ? 10 : showCount.intValue());
		try {
			DataDesc datadesc = new DataDesc();
			HashMap data = new HashMap();
			data.put("users", this.userViewService.getUserList(page, searchKey,companyId,adminFlag));
			boolean paging = page.caculatePageing();
			datadesc.setPaging(paging);
			datadesc.setPage(page);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取用户列表失败" + e);
		}
		return map;
	}

	@RequestMapping({ "/getUserById" })
	@ResponseBody
	public Map<String, Object> getUserById(@RequestParam String userId) {
		String tid = "tid_get_userList";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {
			DataDesc datadesc = new DataDesc();
			HashMap data = new HashMap();
			data.put("user", userViewService.getUserByUserId(userId));
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			logger.info("获取用户失败" + e.getMessage());
			result.setRetcode(-1);
			result.setRetmsg("获取用户失败" + e);
		}
		return map;
	}

	@RequestMapping({ "/getUserListByRole" })
	@ResponseBody
	public Map<String, Object> getUserListByRole(String searchKey, @RequestParam String urole, @RequestParam Integer showCount,
			@RequestParam Integer pageNum, HttpSession session) {
		String tid = "tid_get_userList_by_role";
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum.intValue());
		page.setShowCount(showCount.intValue());
		try {
			DataDesc datadesc = new DataDesc();
			HashMap data = new HashMap();
			data.put("users", userViewService.getUserListByRole(searchKey, urole, page));
			boolean paging = page.caculatePageing();
			datadesc.setPaging(paging);
			datadesc.setPage(page);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取用户列表失败" + e);
		}
		return map;
	}
	
	@RequestMapping({ "/getCompanyList" })
	@ResponseBody
	public Map<String, Object> getCompanyList() {
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {
			HashMap data = new HashMap();
			data.put("companyList", this.personalService.getAllCompany());
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取公司列表失败" + e);
		}
		return map;
	}
	
	@RequestMapping({ "/updateUserStatus" })
	@ResponseBody
	public Map<String, Object> updateUserStatus(@RequestParam String ustatus, @RequestParam String userId) {
		String tid = "tid_update_User_Status";
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		try {
			int ret = this.userService.updateUserStatus(userId, ustatus);
			result.setRetcode(ret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("修改用户状态失败");
		}
		return map;
	}

	@RequestMapping({ "/updateUserInfo" })
	@ResponseBody
	public Map<String, Object> updateUserInfo(UserView sysUser, HttpSession session) {

		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		String errMsg = "";
		// check current user exsit
		SysUser oldUser = this.userService.getUserById(sysUser.getUserId());
		// TODO just update not null fields so don't need to check request
		// fields
		if (oldUser != null) {
			if (oldUser.getPassword().equals(sysUser.getPassword())) {
				if (userService.updateUser(sysUser) > 0) {
					result.setRetcode(1);
					return map;
				} else {
					errMsg = "更新失败，请与管理员联系";
				}
			} else {
				errMsg = "请确认用户密码";
			}
		} else {
			errMsg = "请确认用户是否还存在";
		}
		logger.info(errMsg);
		result.setRetcode(-1);
		result.setRetmsg(errMsg);

		return map;
	}

	@RequestMapping(value = { "/importUsers" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public HashMap<String, Object> upload(HttpServletRequest request, String fileDesc,
			@RequestParam("userFile") MultipartFile file, String lessonId) throws Exception {
		logger.info("upload beginning [" + file.getName() + "]");
		List<String> errs = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		Result result = new Result();
		map.put("result", result);
		if (!file.isEmpty()) {
			String path = request.getServletContext().getRealPath("/image/");

			String filename = file.getOriginalFilename();
			Calendar date = Calendar.getInstance();
			SimpleDateFormat datef = new SimpleDateFormat("yyyyMMddHHmmss");
			String tempName = datef.format(date.getTime()) + Tools.getRandomNum()
					+ filename.substring(filename.lastIndexOf("."));
			File filepath = new File(path, tempName);

			if (!(filepath.getParentFile().exists())) {
				filepath.getParentFile().mkdirs();
			}
			File newFile = new File(path + File.separator + tempName);
			file.transferTo(newFile);
			// test
			errs = new ArrayList<String>();
			List<SysUser> users = this.userService.importUesers(newFile, errs);

			result.setRetcode(1);
			result.setRetmsg(filepath.getAbsolutePath());
			map.put("users", users);

		} else {
			result.setRetcode(-1);
			result.setRetmsg("文件无内容");
		}
		return map;
	}

	@RequestMapping({ "/getTeaList" })
	@ResponseBody
	public Map<String, Object> getTeaList(String searchKey, @RequestParam String lessId, @RequestParam Integer showCount,
			@RequestParam Integer pageNum, HttpSession session) {
		ResourceDesc rdesc = new ResourceDesc();
		HashMap map = new HashMap();
		Result result = new Result();
		map.put("result", result);
		Page page = new Page();
		page.setCurrentPage(pageNum.intValue());
		page.setShowCount(showCount.intValue());
		try {
			DataDesc datadesc = new DataDesc();
			HashMap data = new HashMap();
			data.put("users", userViewService.getTeaList(lessId, searchKey, page));
			boolean paging = page.caculatePageing();
			datadesc.setPaging(paging);
			datadesc.setPage(page);
			map.put("datadesc", datadesc);
			map.put("data", data);
			result.setRetcode(1);
		} catch (Exception e) {
			e.printStackTrace();
			result.setRetcode(-1);
			result.setRetmsg("获取讲师列表失败" + e);
		}
		return map;
	}
	
	@RequestMapping({"/downloadUserTemplate"})
    public ResponseEntity<byte[]> downloadUserTemplate(HttpServletRequest request)
            throws Exception {
        String path = request.getServletContext().getRealPath("/templates/");
        File file = new File(path + File.separator + "userTemplate.xlsx");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentDispositionFormData("attachment", "userTemplate.xlsx");

        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
	
	private boolean needToRegiste(UserView user) {
		if (StringUtils.isNullOrEmpty(user.getUserName()) || StringUtils.isNullOrEmpty(user.getPhone())
				|| StringUtils.isNullOrEmpty(user.getEmail()) || StringUtils.isNullOrEmpty(user.getDept())) {
			return true;
		}
		return false;
	}

}