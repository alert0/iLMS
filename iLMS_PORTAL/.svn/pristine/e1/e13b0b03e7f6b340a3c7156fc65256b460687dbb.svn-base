package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.bpmx.api.model.process.def.BpmBoDef;

/**
 * 对象功能:流程跟业务定义之间的关系
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyg
 * 创建时间:2014-08-13 11:16:12
 */
@SuppressWarnings("serial")
public class BpmProBo extends AbstractModel<String>{
	
	protected String  id; /*主键*/
	protected String  processId; /*流程定义ID*/
	protected String  processKey; /*流程定义KEY*/
	protected String  boCode; /*业务对象 标识code*/
	protected String  boName; /*业务对象 名称*/
	protected String  creatorId; /*创建者ID*/
	protected Date  createTime; /*创建时间*/
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getProcessId()
	{
		return processId;
	}
	public void setProcessId(String processId)
	{
		this.processId = processId;
	}
	public String getProcessKey()
	{
		return processKey;
	}
	public void setProcessKey(String processKey)
	{
		this.processKey = processKey;
	}
	public String getBoCode()
	{
		return boCode;
	}
	public void setBoCode(String boCode)
	{
		this.boCode = boCode;
	}
	public String getBoName()
	{
		return boName;
	}
	public void setBoName(String boName)
	{
		this.boName = boName;
	}
	public String getCreatorId()
	{
		return creatorId;
	}
	public void setCreatorId(String creatorId)
	{
		this.creatorId = creatorId;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof BpmProBo)){//地址比较
			return this==obj;
		}
		BpmProBo bpb = (BpmProBo) obj;
		if(bpb.getId().equals(this.id)){
			return true;
		}
		if(bpb.getBoCode().equals(this.boCode)&&(bpb.getProcessId().equals(this.processId)||bpb.getProcessKey().equals(this.processKey))){
			return true;
		}
		return false;
	}

}