package com.web.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.coobird.thumbnailator.Thumbnails;

public class Tools {
	public static int getRandomNum() {
		Random r = new Random();
		return (r.nextInt(900000) + 100000);
	}

	public static boolean notEmpty(String s) {
		return ((s != null) && (!("".equals(s))) && (!("null".equals(s))));
	}

	public static boolean isEmpty(String s) {
		return ((s == null) || ("".equals(s)) || ("null".equals(s)));
	}

	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str))
			return null;

		return str.split(splitRegex);
	}

	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static Date str2Date(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();

			return new Date();

		}
	}

	public static Date str2DateNoSec(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();

			return new Date();

		}
	}

	public static String formatTimestampNoSec(Timestamp timestamp) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String str = df.format(timestamp);
		return str;
	}

	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return "";
	}

	public static String getTimes(String StrDate) {
		String resultTimes = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date now = new Date();
			Date date = df.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / 86400000L;
			long hour = times / 3600000L - day * 24L;
			long min = times / 60000L - day * 24L * 60L - hour * 60L;
			long sec = times / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;

			StringBuffer sb = new StringBuffer();

			if (hour > 0L)
				sb.append(hour + "小时前");
			else if (min > 0L)
				sb.append(min + "分钟前");
			else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}

	public static void writeFile(String fileP, String content) {
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";
		filePath = filePath.trim() + fileP.trim().substring(6).trim();
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filePath));
			pw.print(content);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static String readTxtFile(String fileP) {
		String filePath;
		try {
			filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../";
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();

			String encoding = "utf-8";
			File file = new File(filePath);
			if ((file.isFile()) && (file.exists())) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				if ((lineTxt = bufferedReader.readLine()) != null)
					return lineTxt;

				read.close();
			} else {
				System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}

	public static String generateID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static void main(String[] args) {
		Tools.rorate("D:/BJHR/source/cnooc_training/WebRoot/image/tmp_ae7e20b9c1081be189bf1cea8e824d0c10e2ba6d328b613d_180628160501.png");
	}

	/**
	 * 将客户签名图片顺时针旋转270度
	 * 
	 * @param signPicFileName
	 * @return 客户签名图片
	 */
	public static String rorate(String signPicFileName) {
		int nameEnd = signPicFileName.lastIndexOf(".png");
		int nameStr = signPicFileName.lastIndexOf("/");
		String fileName = signPicFileName.substring(nameStr + 1, nameEnd);
		String newFileName = fileName + "_270.png";
		String newFilePath = signPicFileName.substring(0, nameStr + 1) + newFileName;
		try {
			Thumbnails.of(new File(signPicFileName)).scale(1f).rotate(270).toFile(newFilePath);
			new File(signPicFileName).delete();
		} catch (IOException e) {
			e.printStackTrace();
			newFileName = signPicFileName.substring(nameStr + 1);
		}
		return newFileName;
	}
}