package com.hanthink.gps.util.logger;

/**
 * 日志输出用共通方法
 *
 */
public class LogUtil {

    /**
     * 输出用logger
     */
    static Logger logger = Logger.getLogger(LogUtil.class.getName());

    /**
     * 输出Debug日志
     * @param message 信息
     */
    public static void debug(String message) {
    	try {
            logger.debug(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }
    
    /**
     * 输出Warn日志
     * @param message 信息
     */
    public static void warn(String message) {
    	try {
            logger.warn(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    /**
     * 输出信息日志
     * @param message 信息
     */
    public static void info(String message) {
    	try {
    		logger.info(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
    	} catch (Exception ex) {
    		logger.error(ex);
    	}
    }

    /**
     * 输出trace日志
     * @param message 信息
     */
    public static void trace(String message) {
    	try {
    		logger.trace(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
    	} catch (Exception ex) {
    		logger.error(ex);
    	}
    }

    /**
     * 输出错误日志
     * @param message 信息
     */
    public static void error(String message) {
    	try {
    		logger.error(getThrowableLineString(new LogThrowableInfo()) + "  - " + message);
    	} catch (Exception ex) {
    		logger.error(ex);
    	}
    }


    /**
     * 输出错误日志
     * @param throwable 异常
     */
    public static void error(Throwable throwable) {
    	logger.error(throwable);
    }
    
    /**
     * 取得异常信息的文本内容
     * @return 异常信息
     */
    public static String getThrowableLineString(LogThrowableInfo t) {
        String[] lines = t.getThrowableStrRep();
        String traceLine = "";

        if (lines != null && lines.length > 3) {
            traceLine = lines[3];
        }

        if (traceLine.startsWith("\tat ")) {
        	traceLine = traceLine.substring(4);
        }

        return traceLine;
    }
}

