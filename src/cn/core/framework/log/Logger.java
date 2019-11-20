package cn.core.framework.log;

import java.io.Serializable;

/**
 * <strong>Create on : 2016年11月2日 下午1:03:50 </strong> <br>
 * <strong>Description : Logger</strong> <br>
 * @version <strong> v 1.0.0 </strong> <br>
 * @author <strong></strong><br>
 */
public class Logger implements Serializable {

	/** Create on : Paddy Zhang 2017年2月4日 上午10:21:39  <br>
	 * Description :  <br>
	 */
	private static final long serialVersionUID = 1L;
	private static org.apache.log4j.Logger logger;

	private Logger(org.apache.log4j.Logger log4jLogger) {
		logger = log4jLogger;
	}

	public static Logger getLogger(Class<?> classObject) {
		return new Logger(org.apache.log4j.Logger.getLogger(classObject));
	}

	public static Logger getLogger(String loggerName) {
		return new Logger(org.apache.log4j.Logger.getLogger(loggerName));
	}

	public void debug(Object object) {
		logger.debug(object);
	}

	public void debug(Object object, Throwable e) {
		logger.debug(object, e);
	}

	public void info(Object object) {
		logger.info(object);
	}

	public void info(Object object, Throwable e) {
		logger.info(object, e);
	}

	public void warn(Object object) {
		logger.warn(object);
	}

	public void warn(Object object, Throwable e) {
		logger.warn(object, e);
	}

	public void error(Object object) {
		logger.error(object);
	}

	public void error(Object object, Throwable e) {
		logger.error(object, e);
	}

	public void fatal(Object object) {
		logger.fatal(object);
	}

	public String getName() {
		return logger.getName();
	}

	public org.apache.log4j.Logger getLog4jLogger() {
		return logger;
	}

	public boolean equals(Logger newLogger) {
		return logger.equals(newLogger.getLog4jLogger());
	}
}