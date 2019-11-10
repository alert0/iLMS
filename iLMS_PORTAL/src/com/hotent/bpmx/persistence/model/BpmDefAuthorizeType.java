package com.hotent.bpmx.persistence.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:流程授权主表明细 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 09:00:53
 */

public class BpmDefAuthorizeType extends  AbstractModel<String>{
	
	public final static class BPMDEFAUTHORIZE_RIGHT_TYPE{
		
		/**流程授权启动类型*/
		public static final String START="start";
		/**流程授权定义类型*/
		public static final String MANAGEMENT="management";
		/**流程授权任务类型*/
		public static final String TASK="task";
		/**流程授权实例类型*/
		public static final String INSTANCE="instance";
		
	}
	
	
	// id
	protected String id;
	
	//流程授权定义ID
	protected String authorizeId;
	
	//流程授权说明
	protected String authorizeType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getAuthorizeId()
	{
		return authorizeId;
	}

	public void setAuthorizeId(String authorizeId)
	{
		this.authorizeId = authorizeId;
	}

	public String getAuthorizeType()
	{
		return authorizeType;
	}

	public void setAuthorizeType(String authorizeType)
	{
		this.authorizeType = authorizeType;
	}

	
	
}