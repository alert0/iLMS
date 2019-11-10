package com.hotent.bpmx.persistence.model;


import com.hotent.base.core.model.AbstractModel;


/**
 * 对象功能:流程授权主表明细 Model对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:xucx
 * 创建时间:2014-03-05 10:04:50
 */

public class BpmDefAct extends AbstractModel<String>
{
	
	// id
	protected String id;
	
	// 流程分管权限主表ID
	protected String authorizeId;
	
	// 流程KEY
	protected String defKey;
	
	// 流程名称
	protected String defName;
	
	// 流程权限内容
	protected String rightContent;

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

	public String getDefKey()
	{
		return defKey;
	}

	public void setDefKey(String defKey)
	{
		this.defKey = defKey;
	}

	public String getDefName()
	{
		return defName;
	}

	public void setDefName(String defName)
	{
		this.defName = defName;
	}

	public String getRightContent()
	{
		return rightContent;
	}

	public void setRightContent(String rightContent)
	{
		this.rightContent = rightContent;
	}


}