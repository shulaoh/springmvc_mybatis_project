package com.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.web.data.mapper.PersonalInfoMapper;
import com.web.data.mapper.SysUserMapper;
import com.web.data.mapper.UserViewMapper;
import com.web.data.pojo.PersonalInfo;
import com.web.data.pojo.SysConfig;
import com.web.data.pojo.SysUser;
import com.web.data.pojo.UserView;
import com.web.utils.Const;
import com.web.utils.ExcelUtil;
import com.web.utils.Page;
import com.web.utils.Tools;

import sun.print.PSStreamPrintService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private PersonalInfoMapper personalInfoMapper;
	
	@Resource
	private UserViewMapper userViewMapper;

	public SysUser getUserById(String userId) {
		return this.sysUserMapper.selectByPrimaryKey(userId);
	}

	public SysUser userCheckIn(String loginId) {
		return this.sysUserMapper.userCheckIn(loginId);
	}

	public int updatePassword(SysUser user) {
		return this.sysUserMapper.updatePassword(user);
	}

	public int updateByPrimaryKeySelective(SysUser user) {
		return this.sysUserMapper.updateByPrimaryKeySelective(user);
	}

	public int insert(SysUser user) {
		return this.sysUserMapper.insert(user);
	}

	public List<SysConfig> getSysConfig() {
		return this.sysUserMapper.getSysConfig();
	}

	public List<SysUser> getUserList(Page page, String searchKey) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("searchKey", searchKey);
		return this.sysUserMapper.selectUserListPage(map);
	}

	public int updateUserStatus(String userId, String ustatus) {
		HashMap map = new HashMap();
		map.put("userId", userId);
		map.put("ustatus", ustatus);
		return this.sysUserMapper.updateUserStatus(map);
	}

	public SysUser userCheckInByWeixinID(String wechatId) {
		return this.sysUserMapper.userCheckInByWeixinID(wechatId);
	}

	public int getWechatIdFlag(String wechatId) {
		return this.sysUserMapper.getWechatIdFlag(wechatId);
	}

	@Override
	public List<SysUser> importUesers(File userFile, List<String> errs) {
		List<SysUser> users = new ArrayList<SysUser>();

		if (ExcelUtil.importUsers(userFile, users, errs) && errs.isEmpty()) {

			if (reduceUsers(users, errs)) {
				List<SysUser> insertUsers = needInsert(users);
				if (!insertUsers.isEmpty()) {
					// insert users
					if (sysUserMapper.insertBatch(insertUsers) > 0) {
						personalInfoMapper.insertBatch(createPInfoList(insertUsers));
					}
				}
			}
		}
		return users;
	}

	private List<PersonalInfo> createPInfoList(List<SysUser> insertUsers) {
		List<PersonalInfo> pList = new ArrayList<PersonalInfo>();
		PersonalInfo pInfo = null;
		for (SysUser user : insertUsers) {
			pInfo = new PersonalInfo();
			BeanUtils.copyProperties(user, pInfo);
			pInfo.setPersonId(Tools.generateID());
			pList.add(pInfo);
		}
		return pList;
	}

	private List<SysUser> needInsert(List<SysUser> users) {
		List<SysUser> insertUsers = new ArrayList<SysUser>();
		for (SysUser user: users) {
			if (StringUtils.isEmptyOrWhitespaceOnly(user.getUserId())) {
				user.setUserId(Tools.generateID());
				insertUsers.add(user);
			}
		}
		return insertUsers;
	}

	private boolean reduceUsers(List<SysUser> users, List<String> errs) {
		// 1. users内部check
		Map<String, String> phoneMap = new HashMap<String, String>();
		Map<String, String> emailMap = new HashMap<String, String>();
		List<String> phones = new ArrayList<String>();
		List<String> emails = new ArrayList<String>();
		// check phone 不能重复
		// check email 不能重复
		for (SysUser user : users) {
			if (!StringUtils.isNullOrEmpty(user.getPhone())) {
				if (phoneMap.containsKey(user.getPhone())) {
					errs.add("电话号码重复：" + user.getPhone());
				} else {
					phoneMap.put(user.getPhone(), "");
					phones.add(user.getPhone());
				}
			}
			if (!StringUtils.isNullOrEmpty(user.getEmail())) {
				if (emailMap.containsKey(user.getEmail())) {
					errs.add("邮件地址重复：" + user.getEmail());
				} else {
					emailMap.put(user.getEmail(), "");
					emails.add(user.getEmail());
				}
			}
		}
		// 2. 数据库check
		if (errs.isEmpty()) {
			List<UserView> oldUserByPhone = null;
			List<UserView> oldUserByEmail = null;
			if (!phones.isEmpty()) {
				oldUserByPhone = userViewMapper.getUsersByPhone(phones);
				for (SysUser user: users) {
					for (UserView dbUser: oldUserByPhone) {
						if (dbUser.getPhone().equals(user.getPhone())) {
							user.setUserId(dbUser.getUserId());
						}
					}
				}
			}
			if (!emails.isEmpty()) {
				oldUserByEmail = userViewMapper.getUsersByEmail(emails);
				for (SysUser user: users) {
					if (StringUtils.isNullOrEmpty(user.getUserId())) {
						for (UserView dbUser: oldUserByEmail) {
							if (dbUser.getEmail().equals(user.getEmail())) {
								user.setUserId(dbUser.getUserId());
							}
						}
					}
				}
			}
//			// check phone 不能重复
//			if (!phones.isEmpty() && sysUserMapper.checkPhone(phones) > 0) {
//				errs.add("电话号码与数据库中的重复，请与管理员联系");
//			} else if (!emails.isEmpty() && sysUserMapper.checkEmail(emails) > 0) {
//				// check email 不能重复
//				errs.add("Email与数据库中的重复，请与管理员联系");
//			}

		}
		return errs.isEmpty();
	}

	@Override
	public int userRegist(UserView newUser) {
		// check 是否受邀用户
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		paramHashMap.put("userName", newUser.getUserName());
		paramHashMap.put("email", newUser.getEmail());
		paramHashMap.put("phone", newUser.getPhone());
		UserView invitedUser = userViewMapper.getInvitedUser(paramHashMap);

		PersonalInfo pInfo = new PersonalInfo();
		BeanUtils.copyProperties(newUser,pInfo);
		SysUser user = new SysUser();
		BeanUtils.copyProperties(newUser,user);
		if (invitedUser != null && !invitedUser.getuStatus().equals(Const.USER_STATUS_DEL)) {
			// 受邀用户
			user.setUserId(invitedUser.getUserId());
			user.setUstatus(Const.USER_STATUS_REGT);
			if (sysUserMapper.updateByPrimaryKeySelective(user) > 0) {
				pInfo.setUserId(invitedUser.getUserId());
				pInfo.setPersonId(invitedUser.getPersonId());
				return personalInfoMapper.updateByPrimaryKeySelective(pInfo);
			}
		} else {
			if (checkPhoneAndEmail(newUser.getEmail(), newUser.getPhone()) == 0 ) {
				setNewUserInfo(user);
				if (sysUserMapper.insert(user) > 0) {
					pInfo.setPersonId(Tools.generateID());
					pInfo.setUserId(user.getUserId());
					return personalInfoMapper.insert(pInfo);
				}
			} else {
				return -2;
			}
		}
		return 0;
	}

	private int checkPhoneAndEmail(String email, String phone) {
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		paramHashMap.put("email", email);
		paramHashMap.put("phone", phone);

		return this.userViewMapper.userCheck(paramHashMap);
	}

	private void setNewUserInfo(SysUser sysUser) {
		sysUser.setUserId(Tools.generateID());
		if ((sysUser.getAdminFlag() == null) || (sysUser.getAdminFlag().trim().length() <= 0)) {
			sysUser.setAdminFlag("0");
		}
		if ((sysUser.getUstatus() != null) && (sysUser.getUstatus().trim().length() > 0)) {
			sysUser.setUstatus(sysUser.getUstatus());
		} else {
			sysUser.setUstatus("WAIT");
		}
		if ((sysUser.getUserPicUrl() == null) || (sysUser.getUserPicUrl().trim().length() == 0)) {
			sysUser.setUserPicUrl("https://lpg.tianmengit.com/cnooc_training/image/DEFAULT_UERR_HEAD_PIC.PNG");
		} else {
			sysUser.setUserPicUrl("https://lpg.tianmengit.com/cnooc_training/image/" + sysUser.getUserPicUrl());
		}
		if (StringUtils.isEmptyOrWhitespaceOnly(sysUser.getPassword())) {
			sysUser.setPassword(Const.DEFULT_PASSWORD);
		}
	}

	@Override
	public int addUser(UserView newUser) {
		HashMap<String, Object> paramHashMap = new HashMap<String, Object>();
		paramHashMap.put("email", newUser.getEmail());
		paramHashMap.put("phone", newUser.getPhone());

		// check
		if (userViewMapper.userCheck(paramHashMap) > 0) {
			return -2;
		} else {
			PersonalInfo pInfo = new PersonalInfo();
			BeanUtils.copyProperties(newUser,pInfo);
			SysUser user = new SysUser();
			BeanUtils.copyProperties(newUser, user);
			setNewUserInfo(user);
			if (sysUserMapper.insert(user) > 0) {
				newUser.setUserId(user.getUserId()); //controller need this
				pInfo.setPersonId(Tools.generateID());
				pInfo.setUserId(user.getUserId());
				return personalInfoMapper.insert(pInfo);
			}
		}
		return 0;
	}

	@Override
	public int updateUser(UserView newUser) {
		PersonalInfo pInfo = new PersonalInfo();
		BeanUtils.copyProperties(newUser, pInfo);
		SysUser user = new SysUser();
		BeanUtils.copyProperties(newUser, user);
		if (sysUserMapper.updateByPrimaryKeySelective(user) > 0) {
			pInfo.setUserId(newUser.getUserId());
			if (!StringUtils.isEmptyOrWhitespaceOnly(pInfo.getPersonId())) {
				return personalInfoMapper.updateByPrimaryKeySelective(pInfo);
			} else {
				return personalInfoMapper.updateByUserIdSelective(pInfo);
			}
		}
		return 0;
	}
}