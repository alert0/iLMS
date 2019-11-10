package com.hotent.service.api.exception;

/**
 * 服务调用异常
 * @author heyifan
 *
 */
public class InvokeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public InvokeException()
	{
		super();
	}
	
	public InvokeException(String message,Throwable cause)
	{
		super(message, cause);
	}
	
	public InvokeException(String code,String cause)
	{
		super("code:" + code + ",cause:" + cause);
	}
	
	
	

	public InvokeException(Throwable cause)
	{
		super(cause);
	}
}
