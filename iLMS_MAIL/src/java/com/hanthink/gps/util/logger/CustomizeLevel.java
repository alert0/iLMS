package com.hanthink.gps.util.logger;

import org.apache.log4j.Level;

public class CustomizeLevel extends Level {

	private static final long serialVersionUID = -365981253573246476L;

	/**
	 * trace的整数值
	 */
	public static final int TRACE_INT = Integer.MAX_VALUE;

	/**
	 * trace的字符串值 　　
	 */
	private static final String TRACE_STR = "TRACE";

	/**
	 * トレースレバー配置
	 */
	public static final CustomizeLevel TRACE = new CustomizeLevel(TRACE_INT,
			TRACE_STR, 7);

	/**
	 * コンストラクタ
	 * 
	 * @param level
	 *            レバー
	 * @param strLevel
	 *            ストリングレバー
	 * @param syslogEquiv
	 *            システムログ当量
	 */
	protected CustomizeLevel(int level, String strLevel, int syslogEquiv) {
		super(level, strLevel, syslogEquiv);
	}

	/**
	 * ストリングレバーをレバーオブジェクトにチェンジする。
	 * 
	 * @param sArg
	 *            ストリングレバー
	 * @return レバーオブジェクト
	 */
	public static Level toLevel(String sArg) {
		return (Level) toLevel(sArg, CustomizeLevel.TRACE);
	}

	/**
	 * ストリングレバーをレバーにチェンジする。
	 * 
	 * @param sArg
	 *            ストリングレバー
	 * @param defaultValue
	 *            デフォールトレバー
	 * @return レバーオブジェクト
	 */
	public static Level toLevel(String sArg, Level defaultValue) {
		if (sArg == null) {
			return defaultValue;
		}

		String stringVal = sArg.toUpperCase();

		if (stringVal.equals(TRACE_STR)) {
			return CustomizeLevel.TRACE;
		}

		return Level.toLevel(sArg, (Level) defaultValue);
	}

	/**
	 * 整数型レバーをレバーオブジェクトにチェンジする。
	 * 
	 * @param i
	 *            整数型レバー
	 * @return レバーオブジェクト
	 */
	public static Level toLevel(int i) {

		switch (i) {
		case TRACE_INT:
			return CustomizeLevel.TRACE;
		default:
			break;
		}

		return Level.toLevel(i);
	}
}
