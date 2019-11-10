package com.hanthink.gps.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * ストリング処理用の共通関数クラス
 * 
 * 
 */
public class StringUtil {

	/**
	 * ＮＵＬＬはブランクストリングに切り替えます。
	 * 
	 * @param sIn
	 *            指定したストリング
	 * @return 切り替えたストリング
	 */
	public static String nvl(String sIn) {
		return (sIn == null) ? "" : sIn;
	}

	/** 从左边开始添加空格 */
	public static final int DIRECT_LEFT = 0;
	/** 从右边边开始添加空格 */
	public static final int DIRECT_RIGHT = 1;
	/** 数值类最大值检查出错 */
	public static final int MAX_VALUE_CHECK_ERROR = 1;
	/** 数值类精度检查出错 */
	public static final int PRECISION_CHECK_ERROR = 2;
	/** 非数值类文字列 */
	public static final int NOT_NUM_VALUE = 3;
	/** 数值类检查OK */
	public static final int NUM_CHECK_OK = 0;

	public static boolean isNullOrEmpty(CharSequence argCharSeq) {

		if ((argCharSeq == null) || (argCharSeq.toString().trim().length() < 1)) {
			return true;
		}

		return false;
	}

	public static boolean isNullOrEmpty(String str) {
		if ((str == null) || (str.trim().length() < 1)) {
			return true;
		}
		return false;
	}

	public static boolean equalsString(String argStr1, String argStr2) {

		if ((argStr1 == null) && (argStr2 == null)) {
			return true;
		}
		if ((argStr1 == null) || (argStr2 == null)) {
			return false;
		}
		return argStr1.equals(argStr2);
	}

	public static String getFirstLowerCase(String argString) {

		if (isNullOrEmpty(argString)) {
			return "";
		}
		if (argString.length() < 2) {
			return argString.toLowerCase();
		}

		char ch = argString.charAt(0);
		ch = Character.toLowerCase(ch);

		return ch + argString.substring(1);
	}

	public static String getFirstUpperCase(String argString) {

		if (isNullOrEmpty(argString)) {
			return "";
		}
		if (argString.length() < 2) {
			return argString.toUpperCase();
		}

		char ch = argString.charAt(0);
		ch = Character.toUpperCase(ch);

		return ch + argString.substring(1);
	}

	public static void appendLine(StringBuffer argSbf) {
		argSbf.append(System.getProperty("line.separator"));
	}

	/**
	 * 入力したストリングの中、特別な文字は入力した文字に取り替えられます。
	 * 
	 * @param src
	 *            入力したストリング
	 * @param argParams
	 *            入力した文字
	 * @return 切り替えたストリング
	 */
	public static String formatMsg(String src, Object... argParams) {
		return String.format(src, argParams);
	}

	public static int getCount(String src, String strChar) {
		int result = 0;

		int beginIndex = 0;
		int endIndex = src.indexOf(strChar, beginIndex);

		while (endIndex >= 0) {
			result++;
			beginIndex = endIndex + strChar.length();
			endIndex = src.indexOf(strChar, beginIndex);
		}

		return result;
	}

	/**
	 * 入力したストリングの中、特別な文字は入力した文字に取り替えられます。
	 * 
	 * @param src
	 *            入力したストリング
	 * @param sFnd
	 *            特別な文字
	 * @param sRep
	 *            入力した文字
	 * @return 切り替えたストリング
	 */
	public static String replaceStr(String src, String sFnd, String sRep) {
		if (src == null || "".equals(nvl(sFnd))) {
			return src;
		}

		String sTemp = "";
		int endIndex = 0;
		int beginIndex = 0;
		do {
			endIndex = src.indexOf(sFnd, beginIndex);
			if (endIndex >= 0) { // mean found it.
				sTemp = sTemp + src.substring(beginIndex, endIndex) + nvl(sRep);
				beginIndex = endIndex + sFnd.length();
			} else if (beginIndex >= 0) {
				sTemp = sTemp + src.substring(beginIndex);
				break;
			}
		} while (endIndex >= 0);

		return sTemp;
	}

	public static String getNextString(String argStr, int argNumLen) {
		String strResult = argStr;
		String strTempNum = "0";
		if (argStr.length() > argNumLen) {
			int index = argStr.length() - argNumLen;
			strResult = argStr.substring(0, index);

			strTempNum = argStr.substring(index);
		}
		StringBuilder sb = new StringBuilder("0000000000");
		long lngNo = Long.parseLong(strTempNum);
		sb.append(++lngNo);

		strResult += sb.toString().substring(sb.length() - argNumLen);
		return strResult;
	}

	public static String getNextStringWithNoPrefex(String argStr, int argNumLen) {
		String strResult = argStr;
		String strTempNum = argStr.substring(argStr.length() - argNumLen);
		StringBuilder sb = new StringBuilder("0000000000");
		long lngNo = Long.parseLong(strTempNum);
		sb.append(++lngNo);
		strResult = sb.toString().substring(sb.length() - argNumLen);
		return strResult;
	}

	public static String replaceSqlParams(String argSql, Object[] argParams) {
		StringBuilder sb = new StringBuilder();

		final String REPLACE_CHAR = "?";
		int fromIndex = 0;
		int pos = -1;
		int index = 0;
		while ((pos = argSql.indexOf(REPLACE_CHAR, fromIndex)) >= 0) {
			sb.append(argSql.substring(fromIndex, pos));
			String strParam = getParamValue(argParams, index++);
			sb.append(strParam);
			fromIndex = pos + REPLACE_CHAR.length();
		}
		sb.append(argSql.substring(fromIndex));

		return sb.toString().replaceAll("(=)(\\s*+NULL\\b)", "IS$2");
	}

	/**
	 * マップから、パラメータの値を取得する。
	 * 
	 * @param params
	 *            パラメータ
	 * @param index
	 *            パラメータのインデックス
	 * @return パラメータの値
	 */
	private static String getParamValue(Object[] params, int index) {
		Object obj = params[index];

		String result = "";
		if (obj == null) {
			result = "NULL";
		} else if (obj instanceof java.lang.String) {
			result = "'" + obj + "'";
		} else if (obj instanceof Date) {
			result = "TO_DATE('"
					+ DateUtil.formatDate((Date) obj,
							DateUtil.DATE_FORMAT_TIME_T)
					+ "', 'yyyy/mm/dd HH24:MI:SS')";
		} else {
			result = "'" + obj + "'";
		}

		return result;
	}

	/**
	 * 字符串比较
	 * 
	 * @param argStr1
	 *            字符串1
	 * @param argStr2
	 *            字符串2
	 * @return boolean
	 */
	public static int compare(String argStr1, String argStr2) {
		if (argStr1 == null && argStr2 == null) {
			return 0;
		}
		if (argStr1 == null) {
			return -1;
		}
		if (argStr2 == null) {
			return 1;
		}

		return argStr1.compareTo(argStr2);
	}

	/**
	 * 给指定的str添加到指定的长度(全角算2,一律在字符串右边添加空格),超过长度的截取
	 * 
	 * @param strIn
	 *            要添加的字符串
	 * @param len
	 *            添加到的长度
	 * @param direct
	 *            从左边添加还是右边添加]
	 * 
	 * @return 添加到的指定长度
	 */
	public static String paddingSpaceForChar(String strIn, int len, int direct) {
		if (strIn == null) {
			return null;
		}
		int strInLen = getStrLength(strIn);
		if (strInLen == len) {
			return strIn;

		} else if (strInLen > len) {
			return chopStr(strIn, len);

		} else {
			StringBuffer sb = new StringBuffer((strIn == null ? "" : strIn));
			for (int i = 0; i < (len - strInLen); i++) {
				sb.append(" ");
			}
			return sb.toString();
		}
	}

	/**
	 * 给指定的str添加到指定的长度(全角算2,一律在字符串右边添加"0"),超过长度的截取
	 * 
	 * @param strIn
	 *            要添加的字符串
	 * @param len
	 *            添加到的长度
	 * @param direct
	 *            从左边添加还是右边添加
	 * 
	 * @return 添加到的指定长度
	 */
	public static String paddingZoreForChar(String strIn, int len, int direct) {
		if (strIn == null) {
			return null;
		}
		int strInLen = getStrLength(strIn);
		if (strInLen == len) {
			return strIn;

		} else if (strInLen > len) {
			return chopStr(strIn, len);

		} else {
			StringBuffer sb = new StringBuffer((strIn == null ? "" : strIn));
			for (int i = 0; i < (len - strInLen); i++) {
				sb.append("0");
			}
			return sb.toString();
		}
	}

	/**
	 * 判定是不是全角
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isHalf(char c) {
		if (!(('\uFF61' <= c) && (c <= '\uFF9F'))
				&& !(('\u0020' <= c) && (c <= '\u007E'))) {
			return false;
		} else
			return true;
	}

	/**
	 * 获取字符串长度(全角算2)
	 * 
	 * @param s
	 * @return
	 */
	public static int getStrLength(String s) {
		if (s == null) {
			return 0;
		}

		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isHalf(s.charAt(i))) {
				len += 1;
			} else {
				len += 2;
			}
		}
		return len;
	}

	/**
	 * 获取字符串长度(全角算3)
	 * 
	 * @param s
	 * @return
	 */
	public static int getStrDBLength(String s) {
		if (s == null) {
			return 0;
		}

		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isHalf(s.charAt(i))) {
				len += 1;
			} else {
				len += 3;
			}
		}
		return len;
	}

	/**
	 * 判断指定的String长度是不是超过指定的长度(非半角ascii长度为3)
	 * 
	 * @param s
	 *            被测试字符串
	 * @param length
	 *            指定最大长度
	 * @return false : 在最大长度内 <br>
	 *         true: 超出指定最大长度
	 */
	public static boolean strLenInValid(String s, int length) {

		int actLen = getStrDBLength(s);
		if (length >= actLen) {
			return false;
		}
		return true;
	}

	/**
	 * 判断数值类控件数字是否合法
	 * 
	 * @param s
	 *            数字类文字列
	 * @param maxValue
	 *            最大值
	 * @param precision
	 *            精度(小数点位数)
	 * @return 检查结果(因为涉及的可能有至少两种情况<br>
	 *         0：检查OK 1：超过最大值 , 2：精度检查出错,3:非数值类型文字列)
	 */
	public static int maxValueValid(String s, BigDecimal maxValue, int precision) {
		if (s == null || "".equals(s)) {
			return NOT_NUM_VALUE;
		}
		try {
			BigDecimal value = new BigDecimal(s);
			if (value.compareTo(maxValue) <= 0) {
				if (BigDecimal.ZERO.compareTo(value) != 0
						&& value.scale() > precision) {
					return PRECISION_CHECK_ERROR;
				}
				return NUM_CHECK_OK;
			} else {
				return MAX_VALUE_CHECK_ERROR;
			}
		} catch (NumberFormatException e) {
			return NOT_NUM_VALUE;
		}
	}

	/**
	 * 截取字符串
	 * 
	 * @param s
	 * @param byteLen
	 * @return
	 */
	public static String chopStr(String s, int byteLen) {
		if (byteLen < 0) {
			return "";
		}
		if (s == null) {
			return null;
		}

		int len = 0;
		for (int i = 0; i < s.length(); i++) {
			if (isHalf(s.charAt(i))) {
				len += 1;
			} else {
				len += 2;
			}
			if (len > byteLen) {
				return s.substring(0, i);
			}
		}
		return s;
	}

	/**
	 * 转跳页面时候的url参数解析函数
	 * 
	 * @param queryString
	 * @param enc
	 * @return
	 */
	public static Map<String, String[]> getParamsMap(String queryString,
			String enc) {
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();
		if (queryString != null && queryString.length() > 0) {
			int ampersandIndex, lastAmpersandIndex = 0;
			String subStr, param, value;
			String[] paramPair, values, newValues;
			do {
				ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				if (ampersandIndex > 0) {
					subStr = queryString.substring(lastAmpersandIndex,
							ampersandIndex - 1);
					lastAmpersandIndex = ampersandIndex;
				} else {
					subStr = queryString.substring(lastAmpersandIndex);
				}
				paramPair = subStr.split("=");
				param = paramPair[0];
				value = paramPair.length == 1 ? "" : paramPair[1];
				try {
					value = URLDecoder.decode(value, enc);
				} catch (UnsupportedEncodingException ignored) {
				}
				if (paramsMap.containsKey(param)) {
					values = paramsMap.get(param);
					int len = values.length;
					newValues = new String[len + 1];
					System.arraycopy(values, 0, newValues, 0, len);
					newValues[len] = value;
				} else {
					newValues = new String[] { value };
				}
				paramsMap.put(param, newValues);
			} while (ampersandIndex > 0);
		}
		return paramsMap;
	}

	public static boolean isBlank(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 生成随机数密码(数字和字母组合)
	 * 
	 * @param pwdLeng
	 *            密码长度
	 * 
	 * @return 生成的随机密码
	 */
	public static String genRandomNum(int pwdLeng) {
		if (pwdLeng < 6) {
			pwdLeng = 6;
		}
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwdLeng) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(str.length));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 将数字转化为字符串，并格式化为指定的长度，不够位数的前面补指定字符 Input: 1, 5, '0' Output: "00001"
	 * 
	 * @return java.lang.String
	 * @param value
	 *            - 要转换的数字
	 * @param number
	 *            - 要补齐的位数
	 * @param c
	 *            char - 补充的字符
	 */
	public static String leftPad(String strIn, int len, char c) {
		if (strIn == null) {
			return null;
		}
		int strInLen = getStrLength(strIn);
		if (strInLen == len) {
			return strIn;
		} else if (strInLen > len) {
			return chopStr(strIn, len);
		} else {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < (len - strInLen); i++) {
				sb.append(c);
			}
			sb.append(strIn);
			return sb.toString();
		}
	}

	/**
	 * 获取指定日期的所在自然周(或月)开始时间和结束时间 默认以周日为每个星期的开始日 <br>
	 * eg: 时间是2010-08-05, 则返回所在的星期的开始和结束时间(2010-08-01至2010-08-07)或是月份的开始和结束时间
	 * 
	 * @param date
	 *            指定的日期
	 * @param timeFlg
	 *            <b>true:自然周</b>
	 *            <b>false:自然月</b>
	 * @param firstDayOfWeekToMonday 是否使用周一作为一周的开始(默认周日为一周的开始)
	 * 
	 * @return array[0] 所在开始日, array[1] 所在结束日
	 */
	public static String[] getWeekOrMonthOfTheDay(Calendar cal,
			boolean timeFlg, boolean firstDayOfWeekToMonday) {
		// 日期输出格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String[] timePeriod = new String[2];
		if(timeFlg) {
		// 设定一周的开始时间是周日 默认
		int firstDayOfWeek = Calendar.SUNDAY;
		// 设定一周的结束时间默认周六
		int endDayOfWeek = Calendar.SATURDAY;
		
		// 指定周一为默认一周开始时间
		if (firstDayOfWeekToMonday) {
			firstDayOfWeek = Calendar.MONDAY;
			endDayOfWeek = Calendar.SUNDAY;
		}
		cal.setFirstDayOfWeek(firstDayOfWeek);
		// 当前时间是第一周且月份是1月份
		if (cal.get(Calendar.WEEK_OF_YEAR) == 1 && cal.get(Calendar.MONTH) == 0) {
			cal.set(Calendar.DAY_OF_YEAR, 1);
			Date start = cal.getTime();
			timePeriod[0] = format.format(start);
			cal.set(Calendar.DAY_OF_WEEK, endDayOfWeek);
			Date end = cal.getTime();
			timePeriod[1] = format.format(end);
		} else
		// 当前时间是最后一周
		if ((cal.get(Calendar.WEEK_OF_YEAR) == 1 && cal.get(Calendar.MONTH) == 11) || cal.get(Calendar.WEEK_OF_YEAR) == cal.getMaximum(Calendar.WEEK_OF_YEAR)) {
			cal.set(Calendar.DAY_OF_YEAR, cal
					.getActualMaximum(Calendar.DAY_OF_YEAR));
			Date end = cal.getTime();
			timePeriod[1] = format.format(end);
			cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
			Date start = cal.getTime();
			timePeriod[0] = format.format(start);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
			
			// 设定是计算第多少周开始的时间
			cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
			// 获取当前时间的所在字然周的开始时间
			Date startDate = cal.getTime();
			timePeriod[0] = format.format(startDate);
			cal.set(Calendar.DAY_OF_WEEK, endDayOfWeek);
			// 获取结束日期(周)
			Date endDate = cal.getTime();
			timePeriod[1] = format.format(endDate);
		}
		} else {
			// 好像默认的就是1号为一个月的开始时间
			cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date startDate = cal.getTime();
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date endDate = cal.getTime();
			timePeriod[0] = format.format(startDate);
			timePeriod[1] = format.format(endDate);
		}
		return timePeriod;
	}

	/**
	 * 获取系统日期所在自然周(或月)开始时间和结束时间 默认以周日为每个星期的开始日 <br>
	 * eg: 时间是2010-08-05, 则返回所在的星期的开始和结束时间(2010-08-01至2010-08-07)或是月份的开始和结束时间
	 * 
	 * @param timeFlg
	 *            <b>true:自然周</b>
	 *            <b>false:自然月</b>
	 * @param firstDayOfWeekToMonday 是否使用周一作为一周的开始(默认周日为一周的开始)
	 * 
	 * @return array[0] 所在开始日, array[1] 所在结束日
	 */
	public static String[] getWeekOrMonth(boolean timeFlg,
			boolean firstDayOfWeekToMonday,Date dateParam) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateParam);
		return getWeekOrMonthOfTheDay(cal, timeFlg, firstDayOfWeekToMonday);
	}
	/**
	* @param startDateStr 开始时间
	* @param endDateStr 结束时间
	*
	* @return 开始时间和结束时间所包含时间的List
	**/
	public static List<String> getDayArray(String startDateStr,String endDateStr){
		List<String> list = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		try {
			beginDate = format.parse(startDateStr);
		Date endDate = format.parse(endDateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		while(cal.getTime().compareTo(endDate) <= 0) {
			list.add(format.format(cal.getTime()));
			cal.add(Calendar.DATE, 1);
		}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 获取随即序列
	 * 
	 * @return
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString().replaceAll("_", "");
	}
	
	/**
     * 转换成string
     * @author anmin
     * @create_date 2015 下午04:36:34
     * @param value
     * @param defalutValue
     * @return
     */
	 public static String getStr(Object value) {
	        if (null == value) {
	            return null;
	        }
	        return String.valueOf(value);
	    }
}
