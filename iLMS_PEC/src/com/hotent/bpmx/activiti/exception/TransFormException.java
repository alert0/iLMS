package com.hotent.bpmx.activiti.exception;

/**
 * 流程定义文件转换时出错时抛出的异常。
 * <pre> 
 * 描述：TODO
 * 构建组：x5-base-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-2-8-下午3:55:09
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class TransFormException extends RuntimeException {

	public TransFormException(String msg){
		
		super(msg);
	}
	
	public TransFormException(String msg, Throwable throwable)
	{
		super(msg,throwable);
	}
}
