package com.hanthink.gps.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.jmx.snmp.Timestamp;

/**
 * 日付処理用の共通関数クラス
 * 
 */
public class DateUtil {
	/** yyyy-MM-ddのフォーマット */
	public static final String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mmのフォーマット */
	public static final String DATE_FORMAT_TIME_R = "yyyy-MM-dd HH:mm";
	/** yyyy-MM-dd HH:mm:ssのフォーマット */
	public static final String DATE_FORMAT_TIME_T = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd_HH:mm:ss */
	public static final String DATE_FORMAT_TIME_TIME = "yyyy-MM-dd_HH:mm:ss";

	public static final String DB_TIME_PATTERN = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYYMMDD_FILE = "yyyyMMdd";
	public static final String DATE_FORMAT_YYYYMMDDHH_FILE = "yyyyMMdd HH";
	/** マップキー 999 年 */
	private static final int INT_YEAR = 999;
	/** 送信日時ピリオドマップ */
	private static Map<Integer, Long> periods = null;

	/**
	 * 入力した日付はストリングに着替えます。
	 * 
	 * @param argDate
	 *            入力した日付
	 * @param argFormat
	 *            入力したフォーマット
	 * @return 着替えるストリング
	 */
	public static String formatDate(Date argDate, String argFormat) {
		if (argDate == null) {
			return "";
		}

		// フォーマット
		SimpleDateFormat sdfFrom = null;
		// 着替えるストリング結果
		String strResult = null;

		try {
			sdfFrom = new SimpleDateFormat(argFormat);
			strResult = sdfFrom.format(argDate).toString();
		} catch (Exception e) {
			strResult = "";
		} finally {
			sdfFrom = null;
		}

		// 結果を返す
		return strResult;
	}

	/**
	 * 入力したストリングのフォーマットはyyyy-MM-ddに着替えます。
	 * 
	 * @param argDateStr
	 *            入力した日付のストリング
	 * @return 着替える日付
	 */
	public static Date formatStringToDate(String argDateStr) {
		return formatStringToDate(argDateStr, null);
	}

	/**
	 * 入力したストリングは入力フォーマットに着替えます。
	 * 
	 * @param argDateStr
	 *            入力したストリング
	 * @param argFormat
	 *            入力したフォーマット
	 * @return 着替える日付
	 */
	public static Date formatStringToDate(String argDateStr, String argFormat) {
		if (argDateStr == null || argDateStr.trim().length() < 1) {
			return null;
		}

		// フォーマット
		SimpleDateFormat sdfFormat = null;
		// 着替える日付
		Date result = null;

		try {
			String strFormat = argFormat;
			if (StringUtil.isNullOrEmpty(strFormat)) {
				strFormat = DATE_FORMAT_YYYYMMDD;
				if ((argDateStr.length() == 19)) {
					strFormat = "yyyy-MM-dd'T'HH:mm:ss";
				} else if (argDateStr.length() > 16) {
					strFormat = DATE_FORMAT_TIME_T;
				} else if (argDateStr.length() > 10) {
					strFormat = DATE_FORMAT_TIME_R;
				}
			}
			sdfFormat = new SimpleDateFormat(strFormat);
			result = sdfFormat.parse(argDateStr);
		} catch (Exception e) {
			result = null;
		} finally {
			sdfFormat = null;
		}

		// 結果を返す
		return result;
	}

	/**
	 * 第いくつの週間を得ます。
	 * 
	 * @param date
	 *            入力した日付
	 * @return String 週
	 */
	public static String getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String dayOfWeek = "";
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		switch (weekday) {
		case 1:
			dayOfWeek = "日曜日";
			break;
		case 2:
			dayOfWeek = "月曜日";
			break;
		case 3:
			dayOfWeek = "火曜日";
			break;
		case 4:
			dayOfWeek = "水曜日";
			break;
		case 5:
			dayOfWeek = "木曜日";
			break;
		case 6:
			dayOfWeek = "金曜日";
			break;
		case 7:
			dayOfWeek = "土曜日";
			break;
		}
		return dayOfWeek;
	}

	/**
	 * 日時を比較する
	 * 
	 * @param argDate1
	 *            日時1
	 * @param argDate2
	 *            日時2
	 * @return boolean
	 */
	// public static int compare(Date argDate1, Date argDate2) {
	// return compare(argDate1, argDate2, DATE_FORMAT_1_TIME_T);
	// }

	/**
	 * 日時を比較する
	 * 
	 * @param argDate1
	 *            日時1
	 * @param argDate2
	 *            日時2
	 * @param argFormat
	 *            フォーマット
	 * @return boolean
	 */
	public static int compare(Date argDate1, Date argDate2, String argFormat) {
		if (argDate1 == null && argDate2 == null) {
			return 0;
		}
		if (argDate1 == null) {
			return -1;
		}
		if (argDate2 == null) {
			return 1;
		}

		String strDate1 = formatDate(argDate1, argFormat);
		String strDate2 = formatDate(argDate2, argFormat);

		return strDate1.compareTo(strDate2);
	}

	/**
	 * 月のピリオドを取得する。
	 * 
	 * @see java.util.Calendar#JANUARY
	 * @see java.util.Calendar#FEBRUARY
	 * @see java.util.Calendar#MARCH
	 * @see java.util.Calendar#APRIL
	 * @see java.util.Calendar#MAY
	 * @see java.util.Calendar#JUNE
	 * @see java.util.Calendar#JULY
	 * @see java.util.Calendar#AUGUST
	 * @see java.util.Calendar#SEPTEMBER
	 * @see java.util.Calendar#OCTOBER
	 * @see java.util.Calendar#NOVEMBER
	 * @see java.util.Calendar#DECEMBER
	 * @see java.util.Calendar#UNDECIMBER
	 * @param argMonth
	 *            月
	 * @return ピリオド
	 */
	public static long getMonthPeriod(int argMonth) {
		// 送信日時ピリオドを初期化する
		initPeriod();
		return periods.get(argMonth);
		// return 1000 * 60;
	}

	/**
	 * 送信日時ピリオドを初期化する。
	 */
	private static void initPeriod() {
		Calendar cal = Calendar.getInstance();
		if (periods != null
				&& periods.get(INT_YEAR).equals(cal.get(Calendar.YEAR))) {
			return;
		}
		periods = new HashMap<Integer, Long>();
		// now year
		periods.put(INT_YEAR, Long.valueOf(cal.get(Calendar.YEAR)));

		Calendar calNext = Calendar.getInstance();

		// JANUARY
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		// FEBRUARY
		calNext.set(Calendar.MONTH, Calendar.FEBRUARY);
		long lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: JANUARY
		periods.put(Calendar.JANUARY, lngDay);

		// MARCH
		cal.set(Calendar.MONTH, Calendar.MARCH);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: FEBRUARY
		periods.put(Calendar.FEBRUARY, lngDay);

		// APRIL
		calNext.set(Calendar.MONTH, Calendar.APRIL);
		lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: MARCH
		periods.put(Calendar.MARCH, lngDay);

		// MAY
		cal.set(Calendar.MONTH, Calendar.MAY);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: APRIL
		periods.put(Calendar.APRIL, lngDay);

		// JUNE
		calNext.set(Calendar.MONTH, Calendar.JUNE);
		lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: MAY
		periods.put(Calendar.MAY, lngDay);

		// JULY
		cal.set(Calendar.MONTH, Calendar.JULY);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: JUNE
		periods.put(Calendar.JUNE, lngDay);

		// AUGUST
		calNext.set(Calendar.MONTH, Calendar.AUGUST);
		lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: JULY
		periods.put(Calendar.JULY, lngDay);

		// SEPTEMBER
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: AUGUST
		periods.put(Calendar.AUGUST, lngDay);

		// OCTOBER
		calNext.set(Calendar.MONTH, Calendar.OCTOBER);
		lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: SEPTEMBER
		periods.put(Calendar.SEPTEMBER, lngDay);

		// NOVEMBER
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: OCTOBER
		periods.put(Calendar.OCTOBER, lngDay);

		// DECEMBER
		calNext.set(Calendar.MONTH, Calendar.DECEMBER);
		lngDay = calNext.getTimeInMillis() - cal.getTimeInMillis();
		// MONTH: NOVEMBER
		periods.put(Calendar.NOVEMBER, lngDay);

		// next year JANUARY
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.add(Calendar.YEAR, 1);
		lngDay = cal.getTimeInMillis() - calNext.getTimeInMillis();
		// MONTH: NOVEMBER
		periods.put(Calendar.NOVEMBER, lngDay);
	}

	public static String formatDate(Timestamp argDate, String argFormat) {
		if (argDate == null) {
			return "";
		}

		// フォーマット
		SimpleDateFormat sdfFrom = null;
		// 着替えるストリング結果
		String strResult = null;

		try {
			sdfFrom = new SimpleDateFormat(argFormat);
			strResult = sdfFrom.format(argDate).toString();
		} catch (Exception e) {
			strResult = "";
		} finally {
			sdfFrom = null;
		}

		// 結果を返す
		return strResult;
	}

}
