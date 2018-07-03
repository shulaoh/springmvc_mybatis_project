package com.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Const
{
  public static final String SESSION_USER = "sessionUser";
  public static final String IMAGE_FILE_PATH = "https://lpg.tianmengit.com/cnooc_training/image/";
  public static final String PAGE = "admin/config/PAGE.txt";
  public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";
  public static final String FILEPATHFILE = "uploadFiles/file/";
  public static final String USER_ADMINFLAG_DEFAUL = "0";
  public static final String USER_ADMINFLAG_TEA_DEFAUL = "10";
  public static final String USER_ADMINFLAG_LA2_DEFAUL = "20";
  public static final String USER_USTATUS_DEFAUL = "WAIT";
  public static final String LESSON_OPRTYPE_ALL = "ALL";
  public static final String LESSON_OPRTYPE_PUB = "PUB";
  public static final String LESSON_OPRTYPE_MY = "MY";
  public static final String LESSON_OPRTYPE_INV = "INV";
  public static final String LESSON_OPRTYPE_PAT = "PAT";
  public static final String LESSON_OPRTYPE_WAT = "WAT";
  public static final String LESSON_OPRTYPE_TEA = "TEA";
  public static final String LESSON_STU_STATUS_PUB = "PUB";
  public static final String LESSON_STU_STATUS_WAIT = "WAIT";
  public static final String LESSON_STU_STATUS_INVI = "INVI";
  public static final String LESSON_STU_STATUS_CONF = "CONF";
  public static final String LESSON_ADMINFLAG_YES = "Y";
  public static final String LESSON_ADMINFLAG_NO = "N";
  public static final String PIC_DEFAULT_LESS_PIC = "DEFAULT_LESS_PIC.PNG";
  public static final String PIC_DEFAULT_LESS_CYC_PIC = "DEFAULT_LESS_CYC_PIC.PNG";
  public static final String PIC_DEFAULT_UERR_HEAD_PIC = "DEFAULT_UERR_HEAD_PIC.PNG";
  public static final String Leave_ADMINFLAG_YES = "Y";
  public static final String Leave_ADMINFLAG_NO = "N";
  
  // WAIT：待审核 INFO：信息待补全 REGI：注册用户 DEL：注销用户
  public static final String USER_STATUS_WAIT = "WAIT";
  public static final String USER_STATUS_INFO = "INFO";
  public static final String USER_STATUS_REGT = "REGT";
  public static final String USER_STATUS_DEL = "DEL";
  
  //100：系统管理员 30：一级管理员 20：二级管理员 10：讲师 0：学员 
  public static final String USER_ROLE_100 = "100";
  public static final String USER_ROLE_30 = "30";
  public static final String USER_ROLE_20 = "20";
  public static final String USER_ROLE_10 = "10";
  public static final String USER_ROLE_0 = "0";
  public static final String USER_ROLE_ADMIN = "admin";
  public static final String USER_ROLE_TUT = "tut";
  
  
  public static final String APP_ID="wxc0a422ba9287706a";
  public static final String APP_SECRET="a6c93320cad4374cbf58eeaf5505bed8";
  public static final String DEFULT_PASSWORD = "000000";
  
  // 区域 北京、天津、上海、深圳、湛江、其他
  public static final List<KeyValue> areaList = new ArrayList<KeyValue>();
  static{
	  areaList.add(new KeyValue("010", "北京"));
	  areaList.add(new KeyValue("022", "天津"));
	  areaList.add(new KeyValue("021", "上海"));
	  areaList.add(new KeyValue("0755", "深圳"));
	  areaList.add(new KeyValue("0759", "湛江"));
	  areaList.add(new KeyValue("0", "其他"));
  }
  // 课程默认缩略图
  public static final List<KeyValue> iconList = new ArrayList<KeyValue>();
  static{
	  iconList.add(new KeyValue("1", "PIC_DEFAULT_LESS_HUIYI.PNG"));
	  iconList.add(new KeyValue("2", "PIC_DEFAULT_LESS_PEIXUN.PNG"));
	  iconList.add(new KeyValue("3", "PIC_DEFAULT_LESS_GONGKAI.PNG"));
  }
  
  // 导出评价模板设定
  public static final Map<String, Integer> excelSettings = new HashMap<String, Integer>();
  static{
	  excelSettings.put("STULES", 3);// role-target:excel row index
	  excelSettings.put("STUSCH", 13);
	  excelSettings.put("STUTUT", 23);
	  
	  excelSettings.put("TUTLES", 33);// role-target:excel row index
	  excelSettings.put("TUTSCH", 43);
	  excelSettings.put("TUTSTU", 53);
	  
	  excelSettings.put("TEALES", 63);// role-target:excel row index
	  excelSettings.put("TEASCH", 73);
	  excelSettings.put("TEASTU", 83);
  }
}