package com.web.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.web.controller.SignController;
import com.web.data.mapper.CompanyMapper;
import com.web.data.mapper.PersonalInfoMapper;
import com.web.data.mapper.SysUserMapper;
import com.web.data.mapper.UserViewMapper;
import com.web.data.pojo.Company;
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

	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Resource
	private SysUserMapper sysUserMapper;

	@Resource
	private PersonalInfoMapper personalInfoMapper;
	
	@Resource
	private UserViewMapper userViewMapper;
	
	@Resource
	private CompanyMapper companyMapper;

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
				setCompanyId(insertUsers);
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

	private void setCompanyId(List<SysUser> insertUsers) {
		List<Company> companyList = companyMapper.selectAll();
		for (SysUser user : insertUsers) {
			for(Company company : companyList) {
				if (!StringUtils.isEmptyOrWhitespaceOnly(user.getCompanyName())
						&& company.getName().equals(user.getCompanyName().trim())) {
					user.setCompanyId(company.getId());
					break;
				}
			}
			if (StringUtils.isEmptyOrWhitespaceOnly(user.getCompanyId())) {
				user.setRemark(user.getCompanyName());
			}
		}
	}

	private List<PersonalInfo> createPInfoList(List<SysUser> insertUsers) {
		List<PersonalInfo> pList = new ArrayList<PersonalInfo>();
		PersonalInfo pInfo = null;
		for (SysUser user : insertUsers) {
			pInfo = new PersonalInfo();
			BeanUtils.copyProperties(user, pInfo);
			pInfo.setCompanyId(user.getCompanyId());
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
				SysUser user = null;
				for (int i = 0; i < users.size();i++) {
					user = users.get(i);
					for (UserView dbUser: oldUserByPhone) {
						if (dbUser.getPhone().equals(user.getPhone())) {
							if (dbUser.getUserName().equals(user.getUserName())) {
								user.setUserId(dbUser.getUserId());
							} else {
								// 电话号码已经被别人占用
								users.remove(i--);
								errs.add("导入学员的姓名/手机号码与系统保存信息不匹配,姓名:" + user.getUserName() + ",手机号:" + user.getPhone());
							}
						}
					}
				}
			}
			if (!emails.isEmpty()) {
				oldUserByEmail = userViewMapper.getUsersByEmail(emails);
				SysUser user = null;
				for (int i = 0; i < users.size();i++) {
					user = users.get(i);
					if (StringUtils.isNullOrEmpty(user.getUserId())) {
						for (UserView dbUser: oldUserByEmail) {
							if (dbUser.getEmail().equals(user.getEmail())) {
								if (dbUser.getUserName().equals(user.getUserName())) {
									user.setUserId(dbUser.getUserId());
								} else {
									// 邮件地址已经被别人占用
									users.remove(i--);
									errs.add("导入学员的姓名/邮件地址与系统保存信息不匹配,姓名:" + user.getUserName() + ",邮件地址:" + user.getEmail());
								}
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
		if("000".equals(user.getCompanyId())) {
			user.setRemark(newUser.getCompanyName());
		}
		if (invitedUser != null && !invitedUser.getuStatus().equals(Const.USER_STATUS_DEL)) {
			// 受邀用户
			user.setUserId(invitedUser.getUserId());
			user.setUstatus(Const.USER_STATUS_REGI);
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
		user.setUstatus(newUser.getuStatus());
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