package com.hotent.bpmx.api.exception;

/**
 * 
 * 用于业务代码和流程交互是抛出业务异常。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-11-下午2:23:03
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -3211477670808757149L;

	public BusinessException(String msg){
		
		super(msg);
	}
	
	public BusinessException(String msg, Throwable throwable)
	{
		super(msg,throwable);
	}
}
