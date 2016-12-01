package com.practo.urlshortener;

public class Logger {

	org.apache.log4j.Logger logger;

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Logger getInstance(@SuppressWarnings("rawtypes") Class clazz) {
		return new Logger(clazz);
	}

	/**
	 * 
	 * @param clazz
	 */
	private Logger(@SuppressWarnings("rawtypes") Class clazz) {
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}

	/**
	 * 
	 * @param message
	 */
	public void info(Object message) {
		logger.info(message);
	}

	/**
	 * 
	 * @param message
	 * @param t
	 */
	public void error(Object message, Throwable t) {
		logger.error(message, t);
	}

	/***
	 * 
	 * @param message
	 */
	public void error(Object message) {
		logger.error(message);
	}
}
