package com.web.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import com.web.data.bean.CommImportGroup;
import com.web.data.bean.CommImportItem;
import com.web.data.bean.SignInfo;
import com.web.data.pojo.SysUser;

public class ExcelUtil {

	public static boolean importCommItem(File excelFile, List<CommImportGroup> groups) {
		boolean result = false;
		Workbook workBook = null;
		try {
			if (excelFile.isFile() && excelFile.exists()) {
				workBook = createWorkBook(excelFile);
				if (workBook != null) {
					// 2.循环模板sheet（第一个sheet为列子忽略掉，循环其他的）
					Sheet tempSheet = null;
					for (int i = 1; i < workBook.getNumberOfSheets(); i++) {
						tempSheet = workBook.getSheetAt(i);
						// 3.校验模板行大于3，读取模板sheet的第4行，要求1到5的cell里有数据，否则忽略当前sheet
						if (tempSheet.getLastRowNum() > 3) {
							// 4.读取第2行第二列，生成CommImportGroup对象
							CommImportGroup group = new CommImportGroup();
							group.setGroupName(tempSheet.getRow(1).getCell(1).toString());

							// 5.从第4行开始读取每行数据封装成CommImportItem对象并存入CommImportGroup对象
							getTempItems(tempSheet, group);

							if (group.getItemSize() > 0) {
								groups.add(group);
							}
						}
					}
				} else {
					result = false;
				}
			}
			result = true;
		} catch (FileNotFoundException e) {
			result = false;
		} catch (IOException e) {
			result = false;
		} catch (InvalidFormatException e) {
			result = false;
		} finally {
			closeWorkBook(workBook);
		}
		// 6.返回CommImportGroup对象的list
		return result;
	}
	
	public static boolean createCommItemFile(File sourceFile, File destFile, List<CommImportItem> items) {
		boolean result = false;
		Workbook workBook = null;
		FileOutputStream outputStream = null;
		FileInputStream fis = null;
		try {
			if (sourceFile.isFile() && sourceFile.exists()) {
				if (sourceFile.getName().endsWith(".xls")) {
					fis = new FileInputStream(sourceFile);
					workBook = new HSSFWorkbook(fis);
				} else if (sourceFile.getName().endsWith(".xlsx")) {
					workBook = new XSSFWorkbook(sourceFile);
				}
				if (workBook != null) {
					// 2.模板sheet2（第一个sheet为列子忽略掉）
					Sheet tempSheet = null;
						tempSheet = workBook.getSheetAt(1);
						String key = null;
						int index = 0;
					for (CommImportItem item: items) {
						if (key == null || !key.equals(item.getRoleType() + item.getTargetType())) {
							if (Const.excelSettings.get(item.getRoleType() + item.getTargetType()) != null) {
								index = Const.excelSettings.get(item.getRoleType() + item.getTargetType());
								key = item.getRoleType() + item.getTargetType();
							} else {
								continue;
							}
						} else {
							index++;
						}
						tempSheet.getRow(index).getCell(5).setCellValue(item.getItemName());
					}
					// 输出
					outputStream = new FileOutputStream(destFile);
					workBook.write(outputStream);
					}
				} else {
					result = false;
				}
			result = true;
		} catch (FileNotFoundException e) {
			result = false;
		} catch (IOException e) {
			result = false;
		} catch (InvalidFormatException e) {
			result = false;
		} finally {
			
			try {
				if (fis != null) fis.close();
				if (outputStream != null)outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static void closeWorkBook(Workbook workBook) {
		if (workBook != null)
			try {
				workBook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * 
	 * @param excelFile
	 * @param users
	 * @return
	 */
	public static boolean importUsers(File excelFile, List<SysUser> users, List<String> errs) {
		boolean result = false;
		try {
			if (excelFile.isFile() && excelFile.exists()) {
				// 1.check file is excel file
				Workbook workBook = createWorkBook(excelFile);
				if (workBook != null) {
					// 2.循环模板sheet（第一个sheet为列子忽略掉，循环其他的）
					Sheet tempSheet = null;
					for (int i = 1; i < workBook.getNumberOfSheets(); i++) {
						tempSheet = workBook.getSheetAt(i);
						// 3.校验模板行大于2
						if (tempSheet.getLastRowNum() > 2) {

							// 4.从第3行开始读取每行数据封装成SysUser对象
							int lastRowIndex = tempSheet.getLastRowNum();
							Row row = null;
							int lastCellIndex;
							SysUser imUser = null;
							boolean isCurValid = false;
							for (int rIndex = 2; rIndex <= lastRowIndex; rIndex++) {
								row = tempSheet.getRow(rIndex);
								if (row == null) {
									break;
								}
								lastCellIndex = row.getLastCellNum();
								if (lastCellIndex >= 7) {
									isCurValid = true;
									imUser = new SysUser();
									imUser.setUserName(row.getCell(1).getStringCellValue());
									// 性别row.getCell(2).getStringCellValue()
									// 出生年月row.getCell(3).getStringCellValue()
									imUser.setPhone(row.getCell(4).toString());
									imUser.setEmail(row.getCell(5).getStringCellValue());
									//单位名称
									imUser.setCompanyName(row.getCell(6).getStringCellValue());
									imUser.setDept(row.getCell(7).getStringCellValue());
									// 用户名不能为空
									if (StringUtils.isEmpty(imUser.getUserName())) {
										// 出现用户名为空的字段时停止加载数据，认为后面已经没有数据了
										break;
									}
									// if
									// (StringUtils.isEmpty(imUser.getPhone())
									// &&
									// StringUtils.isEmpty(imUser.getEmail())) {
									// // 手机号码或邮件二者不能同时为空
									// errs.add("No." + (rIndex - 1) +
									// "手机号码或邮件二者不能同时为空");
									// isCurValid = false;
									// }
									if (StringUtils.isEmpty(imUser.getPhone())) {
										// 手机号码不能为空
										errs.add("No." + (rIndex - 1) + "手机号码不能为空");
										isCurValid = false;
									}
									if (!StringUtils.isEmpty(imUser.getPhone())
											&& !Tools.checkMobileNumber(imUser.getPhone())) {
										// 手机号码格式
										errs.add("No." + (rIndex - 1) + "手机号码格式有误");
										isCurValid = false;
									}
									if (!StringUtils.isEmpty(imUser.getEmail())
											&& !Tools.checkEmail(imUser.getEmail())) {
										// 邮件格式
										errs.add("No." + (rIndex - 1) + "邮件格式有误");
										isCurValid = false;
									}
									if (StringUtils.isEmpty(imUser.getCompanyName())) {
										// 工作部门
										errs.add("No." + (rIndex - 1) + "工作单位不能为空");
										isCurValid = false;
									}
									if (StringUtils.isEmpty(imUser.getDept())) {
										// 工作部门
										errs.add("No." + (rIndex - 1) + "工作部门不能为空");
										isCurValid = false;
									}
									if (isCurValid) {
										// 封装数据
										// imUser.setUserId(Tools.generateID());
										imUser.setPassword(Const.DEFULT_PASSWORD);
										// adminFlag =
										// StringUtils.isEmpty(row.getCell(5).toString())
										// ? adminFlag :
										// row.getCell(5).toString();
										// adminFlag =
										// Float.valueOf(adminFlag).intValue() +
										// "";
										imUser.setAdminFlag(Const.USER_ADMINFLAG_DEFAUL);
										// 用户状态 (WAIT：待审核 INFO：信息待补全 REGI：注册用户
										// DEL：注销用户)
										imUser.setUstatus(Const.USER_STATUS_INFO);
										users.add(imUser);
									}

								}
							}
						}
					}
					result = true;
				} else {
					errs.add("文件读取失败");
				}
			}
		} catch (FileNotFoundException e) {
			errs.add("文件读取失败" + e.getMessage());
		} catch (IOException e) {
			errs.add("文件读取失败" + e.getMessage());
		} catch (InvalidFormatException e) {
			errs.add("文件读取失败" + e.getMessage());
		}
		return result;
	}

	private static Workbook createWorkBook(File excelFile)
			throws FileNotFoundException, IOException, InvalidFormatException {
		FileInputStream fis;
		if (excelFile.getName().endsWith(".xls")) {
			fis = new FileInputStream(excelFile);
			return new HSSFWorkbook(fis);
		} else if (excelFile.getName().endsWith(".xlsx")) {
			return new XSSFWorkbook(excelFile);
		}
		
		return null;
	}
	

	private static void getTempItems(Sheet tempSheet, CommImportGroup group) {
		int lastRowIndex = tempSheet.getLastRowNum();
		CommImportItem item = null;
		Row row = null;
		int lastCellIndex = 4;
		String lastRoleType = "";
		String lastTargetType = "";
		for (int rIndex = 3; rIndex <= lastRowIndex; rIndex++) {
			row = tempSheet.getRow(rIndex);
			if (row == null) {
				break;
			}
			lastCellIndex = row.getLastCellNum();
			if (lastCellIndex >= 7 && isValid(row)) {
				// 读取模板sheet的第4行，要求1到7的cell里有数据，否则忽略当前sheet
				item = new CommImportItem();
				if (!StringUtils.isEmpty(row.getCell(2).getStringCellValue())) {
					lastRoleType = row.getCell(2).getStringCellValue().trim();
				}
				if (!StringUtils.isEmpty(row.getCell(4).getStringCellValue())) {
					lastTargetType = row.getCell(4).getStringCellValue().trim();
				}
				item.setRoleType(lastRoleType);
				item.setTargetType(lastTargetType);
				item.setItemName(row.getCell(5).toString());
				item.setItemType(row.getCell(7).getStringCellValue());
				group.addItem(item);
			}
		}
	}

	private static boolean isValid(Row row) {
//		int dataCount = 0;
//		for (int j = 1; j <= 7; j++) {
//			if (!StringUtils.isEmpty(row.getCell(j).toString())) {
//				dataCount++;
//			}
//		}
		return !StringUtils.isEmpty(row.getCell(5).toString());
	}

	public static boolean exportSignToExcel(List<SignInfo> signInfos, File toFile) {
		// 创建一个表格
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = null;
		// 需要导出的人员信息
		if (signInfos != null && signInfos.size() > 0) {
			// 一定要放在循环外,只能声明一次
			HSSFPatriarch patriarch = null;
			SignInfo signInfo;
			String schName = "";
			int rowCount = 0;
			for (int i = 0; i < signInfos.size(); i++) {
				signInfo = signInfos.get(i);
				if (sheet == null || !signInfo.getSchName().equals(schName)) {
					schName = signInfo.getSchName();
					sheet = wb.createSheet(schName + Tools.date2Str(signInfo.getSchStime(), "yyyyMMddHHmm"));
					createHeader(sheet, signInfo);
					patriarch = sheet.createDrawingPatriarch();
					rowCount = 0;
					// sheet.setColumnWidth(6, 100000);
				}
				// No 姓名 手机号 邮箱 公司 部门 签到时间 签到图片
				createRowData(sheet, signInfo, rowCount);
				try {
					addSinPicToRow(wb, rowCount + 2, patriarch, signInfo.getSignPicUrl());
				} catch (IOException e) {
					addSinMisPicToRow(sheet, rowCount + 2);
				}
				rowCount++;
			}

		}
		// 输出
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(toFile);
			wb.write(outputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {

			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	private static void addSinMisPicToRow(HSSFSheet sheet, int i) {
		HSSFRow dataRow;
		HSSFCell dataCell0;
		dataRow = sheet.getRow(i);
		dataCell0 = dataRow.createCell(7);
		dataCell0.setCellValue("文件读取失败");

	}

	private static void addSinPicToRow(HSSFWorkbook wb, int row, HSSFPatriarch patriarch, String signPicUrl)
			throws IOException {
		if (!StringUtils.isEmpty(signPicUrl)) {
			BufferedImage bufferImg = null;
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			bufferImg = ImageIO.read(new File(signPicUrl));
			ImageIO.write(bufferImg, "jpg", byteArrayOut);
			// 关于HSSFClientAnchor(dx1,dy1,dx2,dy2,col1,row1,col2,row2)的参数，有必要在这里说明一下：
			// dx1：起始单元格的x偏移量，
			// dy1：起始单元格的y偏移量，
			// dx2：终止单元格的x偏移量，
			// dy2：终止单元格的y偏移量，
			// col1：起始单元格列序号，从0开始计算；
			// row1：起始单元格行序号，从0开始计算，
			// col2：终止单元格列序号，从0开始计算；
			// row2：终止单元格行序号，从0开始计算，
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 250, (short) 7, row, (short) 7, row);
			patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		}
	}

	private static void createRowData(HSSFSheet sheet, SignInfo signInfo, int rowNo) {
		HSSFRow dataRow;
		HSSFCell dataCell0;
		dataRow = sheet.createRow(rowNo + 2);
		dataRow.setHeightInPoints(40);
		dataCell0 = dataRow.createCell(0);
		dataCell0.setCellValue("" + (rowNo + 1));
		dataCell0 = dataRow.createCell(1);
		dataCell0.setCellValue(signInfo.getUserName());
		dataCell0 = dataRow.createCell(2);
		dataCell0.setCellValue(signInfo.getPhone());
		dataCell0 = dataRow.createCell(3);
		dataCell0.setCellValue(signInfo.getEmail());
		dataCell0 = dataRow.createCell(4);
		dataCell0.setCellValue(signInfo.getCompanyName());
		dataCell0 = dataRow.createCell(5);
		dataCell0.setCellValue(signInfo.getDept());
		dataCell0 = dataRow.createCell(6);
		dataCell0.setCellValue(Tools.date2Str(signInfo.getSignTime()));
	}

	private static void createHeader(HSSFSheet sheet, SignInfo signInfo) {
		HSSFRow dataRow;
		HSSFCell dataCell0;
		// No 姓名 手机号 邮箱 公司 部门 签到时间 签到图片
		String[] headers = new String[] { "No", "姓名", "手机号", "邮箱", "公司", "部门", "签到时间", "签到图片" };
		// 课程名-日程名
		dataRow = sheet.createRow(0);
		// 创建合并单元格对象
		CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, headers.length - 1);
		sheet.addMergedRegion(rangeAddress);

		dataCell0 = dataRow.createCell(0);
		dataCell0.setCellValue(signInfo.getLessName() + "-" + signInfo.getSchName());

		dataRow = sheet.createRow(1);
		for (int i = 0; i < headers.length; i++) {
			dataCell0 = dataRow.createCell(i);
			dataCell0.setCellValue(headers[i]);
		}

	}

	private static byte[] toByteArray(String filename) throws IOException {

		File f = new File(filename);
		if (!f.exists()) {
			throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	public static void main(String[] args) throws Exception {
		String a = null;
//		System.out.println(StringUtils.isEmptyOrWhitespaceOnly(a));
		//========================
//		List<CommImportItem> items = new ArrayList<CommImportItem>();
//		CommImportItem item1 = new CommImportItem();
//		item1.setRoleType("STU");
//		item1.setTargetType("LES");
//		item1.setItemName("哈哈01");
//		items.add(item1);
//		item1 = new CommImportItem();
//		item1.setRoleType("STU");
//		item1.setTargetType("LES");
//		item1.setItemName("哈哈02");
//		items.add(item1);
//		item1 = new CommImportItem();
//		item1.setRoleType("STU");
//		item1.setTargetType("LES");
//		item1.setItemName("哈哈03");
//		items.add(item1);
//		item1 = new CommImportItem();
//		item1.setRoleType("STU");
//		item1.setTargetType("SCH");
//		item1.setItemName("哈哈SCH01");
//		items.add(item1);
//		items.add(item1);
//		item1 = new CommImportItem();
//		item1.setRoleType("STU");
//		item1.setTargetType("SCH");
//		item1.setItemName("哈哈SCH02");
//		items.add(item1);
//		items.add(item1);
//		item1 = new CommImportItem();
//		item1.setRoleType("TEA");
//		item1.setTargetType("STU");
//		item1.setItemName("哈哈TEASTU01");
//		items.add(item1);
//		String tempName = "d:\\BJHR\\commTemp_commentTemplate02.xlsx";
//		File newFile = new File(tempName);
//		File file = new File("d:\\BJHR\\commentTemplate02.xlsx");
//		try {
//			FileUtils.copyFile(file, newFile);
//			// 2.insert the items into new template file
//			if (ExcelUtil.createCommItemFile(file, newFile,items)) {
//				// 3.return new file name
//			}
//		} catch (IOException e) {
//		}
		// =======================
//		List<SignInfo> signInfos = new ArrayList<SignInfo>();
//		SignInfo s1 = new SignInfo();
//		s1.setSchId("8b111ab71de64ec2b56b554cc1dfb4b6");
//		s1.setLessName("党章专项讲座");
//		s1.setSchName("5月3号上午");
//		s1.setSchStime(new Date());
//		s1.setPlace("第一会议室");
//		s1.setUserName("李宏伟");
//		s1.setLessName("lessName");
//		s1.setSignTime(new Date());
//		s1.setSignPicUrl("20180505011146848014.png");
//		signInfos.add(s1);
//		s1 = new SignInfo();
//		s1.setUserName("name02");
//		s1.setLessName("lessName");
//		s1.setSchName("schName02");
//		s1.setSignTime(new Date());
//		s1.setSignPicUrl("D:\\BJHR\\source\\cnooc_training\\WebRoot\\image\\20180513145212687521.jpg");
//		signInfos.add(s1);
//		s1 = new SignInfo();
//		s1.setUserName("name03");
//		s1.setLessName("lessName");
//		s1.setSchName("schName02");
//		s1.setSignTime(new Date());
//		s1.setSignPicUrl("D:\\BJHR\\source\\cnooc_training\\WebRoot\\image\\20180513145212687521.jpg");
//		signInfos.add(s1);
//		exportSignToExcel(signInfos, new File("D:\\BJHR\\tets.xls"));
		// =======================
//		List<CommImportGroup> groups = new ArrayList<CommImportGroup>();
//		importCommItem(new File("d:\\BJHR\\commentTemplate02.xlsx"), groups);
//		for (CommImportGroup group : groups) {
//			System.out.println(group.getGroupName());
//			for (CommImportItem item : group.getItems()) {
//				System.out.println(item.getItemName() + "-f-" + item.getRoleType() + "-t-" + item.getTargetType());
//			}
//		}
		//==========================
		// List<SysUser> users = new ArrayList<SysUser>();
		// List<String> errs = new ArrayList<String>();
		// importUsers(new File("d:\\BJHR\\userTemplate.xlsx"), users, errs);
		// for (SysUser user : users) {
		// System.out.println(user.getAdminFlag());
		// }
		// for (String err : errs) {
		// System.out.println(err);
		// }
	}

}
