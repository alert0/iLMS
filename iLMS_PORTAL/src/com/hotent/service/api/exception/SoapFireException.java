package com.hotent.service.api.exception;

/**
 * webservice服务调用异常
 * @author heyifan
 *
 */
public class SoapFireException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SoapFireException() {
		super();
	}

	public SoapFireException(String message, Throwable cause) {
		super(message, cause);
	}

	public SoapFireException(String message) {
		super(message);
	}

	public SoapFireException(Throwable cause) {
		super(cause);
	}

}
