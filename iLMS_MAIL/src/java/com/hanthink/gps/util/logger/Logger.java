package com.hanthink.gps.util.logger;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.apache.log4j.Level;

import com.hanthink.gps.util.exception.SystemException;

public class Logger {

	/**
	 * TraceログONフラグ
	 */
	private static boolean traceEnabled = true;

	/**
	 * クラス名称 　
	 */
	private static final String FQCN = Logger.class.getName();

	/**
	 * log4jのログ出力用クラス 　
	 */
	private org.apache.log4j.Logger m_logger = null;

	/**
	 * コンストラクタ
	 * 
	 * @param logger
	 *            log4jのログ出力用クラス
	 */
	protected Logger(org.apache.log4j.Logger logger) {
		this.m_logger = logger;
	}

	/**
	 * ログ出力用クラスを取得する。
	 * 
	 * @param name
	 *            ログ名称
	 * @return log4jのログ
	 */
	public static Logger getLogger(String name) {
		return new Logger(org.apache.log4j.Logger.getLogger(name));
	}

	/**
	 * デバッグログを出力する。
	 * 
	 * @param message
	 *            出力メッセージ
	 */
	public final void debug(final Object message) {
		if (m_logger.isDebugEnabled()) {
			if (message instanceof Throwable) {
				Throwable e = (Throwable) message;
				m_logger.log(FQCN, Level.DEBUG, e.getMessage(), e);
			} else {
				m_logger.log(FQCN, Level.DEBUG, message, null);
			}
		}
	}

	/**
	 * 情報ログを出力する。
	 * 
	 * @param message
	 *            出力メッセージ
	 */
	public final void info(final Object message) {
		if (m_logger.isInfoEnabled()) {
			m_logger.log(FQCN, Level.INFO, message, null);
		}
	}
	
	/**
	 * 情報ログを出力する。
	 * 
	 * @param message
	 *            出力メッセージ
	 */
	public final void warn(final Object message) {
		if (m_logger.isInfoEnabled()) {
			m_logger.log(FQCN, Level.WARN, message, null);
		}
	}

	/**
	 * トレースログを出力する。
	 * 
	 * @param message
	 *            出力メッセージ
	 */
	public final void trace(final Object message) {
		if (m_logger.isDebugEnabled()) {
			if (traceEnabled) {
				// m_logger.log(FQCN, Level.TRACE, message, null);
				m_logger.log(FQCN, CustomizeLevel.TRACE, message, null);
			}
		}
	}

	/**
	 * トレースログを出力する。
	 * 
	 * @param message
	 *            出力メッセージ
	 * @param throwable
	 *            エラー情報
	 */
	public final void trace(final Object message, final Throwable throwable) {
		if (m_logger.isDebugEnabled()) {
			if (traceEnabled) {
				// m_logger.log(FQCN, Level.TRACE, message, throwable);
				m_logger.log(FQCN, CustomizeLevel.TRACE, message, throwable);
			}
		}
	}

	/**
	 * TraceログONフラグを取得する。
	 * 
	 * @return TraceログONフラグ
	 */
	public static boolean isTraceEnabled() {
		return traceEnabled;
	}

	/**
	 * TraceログONフラグを設定する。
	 * 
	 * @param newOpen
	 *            TraceログONフラグ
	 */
	public static void setTraceEnabled(boolean newOpen) {
		traceEnabled = newOpen;
	}

	/**
	 * 致命的なログを出力する。
	 * 
	 * @param message
	 *            信息
	 */
	public final void error(final Object message) {
		m_logger.log(FQCN, Level.ERROR, message, null);
	}

	/**
	 * 输出错误日志
	 * 
	 * @param message
	 *            信息
	 */
	public final void error(final Throwable message) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter osw = null;
		osw = new OutputStreamWriter(baos);
		PrintWriter pw = new PrintWriter(osw);
		message.printStackTrace(pw);
		pw.close();
		m_logger.log(FQCN, Level.ERROR, baos.toString(), null);
	}

	/**
	 * 输出致命错误日志
	 * 
	 * @param syserr
	 *            信息
	 * @param actionName
	 *            方法名称
	 */
	public final void fatal(final SystemException syserr, String actionName) {
		String msg = syserr.getErrCode() + " " + actionName + " "
				+ syserr.getErrMsg();
		m_logger.log(FQCN, Level.ERROR, msg, syserr);
		m_logger.log(FQCN, Level.FATAL, msg, null);
	}
}
