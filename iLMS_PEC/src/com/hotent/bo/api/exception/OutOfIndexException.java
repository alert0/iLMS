package com.hotent.bo.api.exception;

/**
 * 超出索引范围异常
 * <pre> 
 * 描述：超出数组索引上限的异常
 * 构建组：ProcessClient
 * 作者：heyifan
 * 邮箱:heyf@jee-soft.cn
 * 日期:2014-1-16-下午1:46:11
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@SuppressWarnings("serial")
public class OutOfIndexException extends BOBaseException{
	public OutOfIndexException(){
	}
	
	public OutOfIndexException(String message){
		super(message);
	}
	
	public OutOfIndexException(String message, Throwable cause){
		super(message,cause);
	}
	
	public OutOfIndexException(Throwable cause){
		super(cause);
	}

}
