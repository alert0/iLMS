package com.hotent.service.api.exception;

/**
 * WSDL文档解析异常
 * @author heyifan
 *
 */
public class WSDLParseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WSDLParseException() {
		super();
	}

	public WSDLParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public WSDLParseException(String message) {
		super(message);
	}

	public WSDLParseException(Throwable cause) {
		super(cause);
	}

}
