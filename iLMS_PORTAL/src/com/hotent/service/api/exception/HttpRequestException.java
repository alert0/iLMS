package com.hotent.service.api.exception;

/**
 * 服务请求异常
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public class HttpRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public HttpRequestException()
	{
		super();
	}
	
	public HttpRequestException(String message,Throwable cause)
	{
		super(message, cause);
	}
	
	public HttpRequestException(String message)
	{
		super(message);
	}

	public HttpRequestException(Throwable cause)
	{
		super(cause);
	}
}
