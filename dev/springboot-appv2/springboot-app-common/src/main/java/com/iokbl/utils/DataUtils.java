package com.iokbl.utils;

import com.iokbl.config.Constants;
import com.iokbl.model.TUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 一般工具类
 * 
 * @author terence
 *
 */
public class DataUtils {

	private static final Logger logger = LoggerFactory.getLogger(DataUtils.class);

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	public static synchronized String formatDate(Date date) {
		return formatter.format(date);
	}

	public static synchronized Date parseDate(String date_str) {
		try {
			return formatter.parse(date_str);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
	public static final String encodeHex(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; ++i) {
			if ((bytes[i] & 0xFF) < 16) {
				buf.append("0");
			}
			buf.append(Long.toString(bytes[i] & 0xFF, 16));
		}
		return buf.toString();
	}

	public static Date getTokenExpireTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 120);

		return calendar.getTime();
	}

	public static int getTimeValidRange(String time_valid_range) {
		int time_valid_range_sec = 0;
		if (StringUtils.isEmpty(time_valid_range)) {
			return 0;
		}
		String val = "0";
		char unit = time_valid_range.charAt(time_valid_range.length() - 1);
		if ("yMdhms".indexOf(unit) != -1) {
			val = time_valid_range.substring(0, time_valid_range.length() - 1);
			int val_int = 0;
			try {
				val_int = Integer.parseInt(val);
			} catch (Exception e) {
				logger.warn("OtsUtils.getTimeValidRange() Integer.parseInt("
						+ val
						+ ") fail,please check the value of time_valid_range in ots.properties.");
				return 0;
			}
			time_valid_range_sec = getTimeValidRangeSec(val_int, unit);
		} else {
			try {
				time_valid_range_sec = Integer.parseInt(time_valid_range);
			} catch (Exception e) {
				logger.warn("OtsUtils.getTimeValidRange() Integer.parseInt("
						+ time_valid_range
						+ ") fail,please check the value of time_valid_range in ots.properties.");
				time_valid_range_sec = 0;
			}
		}
		return time_valid_range_sec;
	}

	private static int getTimeValidRangeSec(int val_int, char unit) {
		int time_valid_range_sec = 0;
		if ('y' == unit)
			time_valid_range_sec = val_int * 365 * 24 * 60 * 60;
		else if ('M' == unit)
			time_valid_range_sec = val_int * 30 * 24 * 60 * 60;
		else if ('d' == unit)
			time_valid_range_sec = val_int * 24 * 60 * 60;
		else if ('h' == unit)
			time_valid_range_sec = val_int * 60 * 60;
		else if ('m' == unit)
			time_valid_range_sec = val_int * 60;
		else if ('s' == unit)
			time_valid_range_sec = val_int;
		else {
			time_valid_range_sec = val_int;
		}
		return time_valid_range_sec;
	}

	/**
	 * 获取主机名
	 * 
	 * @return
	 */
	// public static String getHostName() {
	//
	// try {
	// return InetAddress.getLocalHost().getHostName();
	// } catch (UnknownHostException e) {
	// logger.error("linux unknown host!");
	// }
	//
	// return "default_host";
	// }

	/**
	 * 判断系统
	 *
	 * @return String
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 获取本机ip地址，并自动区分Windows还是linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			} else {
				// 如果是Linux操作系统
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("get ip error.", e);
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		logger.debug("localhost IP:" + sIP);

		return sIP;
	}

	/**
	 * 数据类型转换：String 转换成 Double，保留小数几位
	 * 
	 * @param dataStr
	 * @param dNo
	 * 
	 * @result Double
	 */
	public static Double stringToDouble(String dataStr, Integer dNo) {
		// 构造以字符串内容为值的BigDecimal类型的变量bd
		BigDecimal bd = new BigDecimal(dataStr);
		// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
		Double douV = bd.setScale(dNo, BigDecimal.ROUND_HALF_UP).doubleValue();

		return douV;
	}

	/**
	 * 数据类型转换：String 转换成 BigDecimal，保留小数几位
	 * 
	 * @param dataStr
	 * @param dNo
	 * 
	 * @result BigDecimal
	 */
	public static BigDecimal stringToBigDecimal(String dataStr, Integer dNo) {
		// 构造以字符串内容为值的BigDecimal类型的变量bd
		BigDecimal bd = new BigDecimal(dataStr);
		// 设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)
		bd = bd.setScale(dNo, BigDecimal.ROUND_HALF_UP);

		return bd;
	}

	/**
	 * 时间计算：加小时
	 * 
	 * @param dateV
	 * @param hours
	 * 
	 * @result Double
	 */
	public static Date dateAddHour(Date dateV, Integer hours) {
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		hour += hours;
		rightNow.set(Calendar.HOUR_OF_DAY, hour);
		Date date = rightNow.getTime();

		return date;
	}

	/**
	 * 补"0"
	 * 
	 * @param size
	 *            长度
	 * @param val
	 * 
	 * @return String
	 */
	public static String addZero(int size, String val) {
		String result = val;
		if (val.length() >= size) {
			return val;
		} else {
			for (int i = 0; i < size - val.length(); i++) {
				result = "0" + result;
			}

			return result;
		}
	}

	/**
	 * 补"0"
	 * 
	 * @param size
	 *            长度
	 * @param val
	 * 
	 * @return String
	 */
	public static String addZero(int size, int val) {
		String s = String.valueOf(val);
		String result = s;
		if (s.length() >= size) {
			return s;
		} else {
			for (int i = 0; i < size - s.length(); i++) {
				result = "0" + result;
			}

			return result;
		}
	}

	/**
	 * 字符串转换成Util日期,格式自定义：如yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static synchronized Date stringToDate(String dateStr,
			String formatStr) {
		Date date = null;
		if (StringUtils.isNotBlank(formatStr)) {
			DateFormat dd = new SimpleDateFormat(formatStr);
			try {
				date = dd.parse(dateStr);
			} catch (ParseException e) {

			}
		}
		return date;
	}

	/**
	 * 字符串转换成SQL日期,格式自定义：如yyyy-MM-dd
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date stringToDate(String dateStr) {
		return java.sql.Date.valueOf(dateStr);
	}

	/**
	 * XMLGregorianCalendar类型和Date类型之间的相互转换
	 * 
	 * @param cal
	 * @return
	 */
	public static Date convertToDate(XMLGregorianCalendar cal) {
		GregorianCalendar ca = cal.toGregorianCalendar();
		return ca.getTime();
	}

	/**
	 * 字符串转换成Timestamp,格式自定义：如yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String dateStr, String formatStr) {
		Date a = stringToDate(dateStr, formatStr);
		Timestamp ts = new Timestamp(a.getTime());
		return ts;
	}

	/**
	 * 时间格式转换,String(毫秒数) 转换成 Timestamp,如：1421205613550
	 * 
	 */
	public static synchronized Timestamp stringToTimestamp2(String dateStr,
			String formatStr) {

		SimpleDateFormat df = new SimpleDateFormat(formatStr);
		Date dt = new Date(Long.parseLong(dateStr));
		Timestamp ts = null;
		String sd = df.format(dt);
		ts = Timestamp.valueOf(sd);

		return ts;
	}

	/**
	 * 日期转换成字符串，如：yyyy-MM-dd HH:mm:ss
	 */
	public static synchronized String dateToStr(Date dateV, String formatStr) {
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String dateStr = sdf.format(dateV);
		return dateStr;
	}

	public static synchronized String formatSimpleDate(Date date) {
		return dateToStr(date, "yyyy-MM-dd");
	}

	/**
	 * 判断两个日期是否是同一天
	 * 
	 * @param date1
	 *            date1
	 * @param date2
	 *            date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2
						.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}

	/**
	 * Double类型转换成BigDecimal
	 * 
	 * @param val
	 * 
	 * @return BigDecimal
	 */

	public static BigDecimal doubleToBigDecimal(Double val) {
		return new BigDecimal(Double.toString(val));
	}

	/***
	 * 
	 * @param date
	 * @param dateFormat
	 *            : e.g:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String formatDateByPattern(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	/***
	 * convert Date to cron ,eg. "0 06 10 15 1 ? 2014"
	 * 
	 * @param date
	 *            : 时间点
	 * @return
	 */
	public static String getCron(Date date) {
		String dateFormat = "ss mm HH dd MM ? yyyy";
		return formatDateByPattern(date, dateFormat);
	}

	public static String getId(String prefix,long value){
		return prefix + (1000000000000000l + value);
	}

	public static String getSessionUser_name(HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserInfo planUserInfo = (TUserInfo)session.getAttribute(Constants.USER_SESSION_KEY);
		if(planUserInfo != null){
			return planUserInfo.getUser_name();
		}else{
			return null;
		}
	}

	public static String getSessionUser_id(HttpServletRequest request) {
		HttpSession session = request.getSession();
		TUserInfo planUserInfo = (TUserInfo)session.getAttribute(Constants.USER_SESSION_KEY);
		if(planUserInfo != null){
			return planUserInfo.getUser_id();
		}else{
			return null;
		}
	}

}
