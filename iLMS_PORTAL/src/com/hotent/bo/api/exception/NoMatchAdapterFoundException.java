package com.hotent.bo.api.exception;

/**
 * 未找到匹配的适配器异常
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bo-core
 * 作者：heyifan
 * 邮箱:heyf@jee-soft.cn
 * 日期:2014-1-21-下午3:05:53
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@SuppressWarnings("serial")
public class NoMatchAdapterFoundException extends BOBaseException{
	public NoMatchAdapterFoundException(){
	}
	
	public NoMatchAdapterFoundException(String message){
		super(message);
	}
	
	public NoMatchAdapterFoundException(String message, Throwable cause){
		super(message,cause);
	}
	
	public NoMatchAdapterFoundException(Throwable cause){
		super(cause);
	}
}
