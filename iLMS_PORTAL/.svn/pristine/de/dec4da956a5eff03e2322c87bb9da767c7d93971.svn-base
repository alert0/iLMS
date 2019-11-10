package com.hotent.system.worktime.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:加班情况 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-01-07 16:06:45
 */
public class OverTime extends AbstractModel<String>{
	private static final long serialVersionUID = 1L;
	protected String  id;        /*主键*/
	protected String  subject;   /*主题*/
	protected String  userId;    /*用户ID*/
	protected Date    startTime; /*开始时间*/
	protected Date    endTime;   /*结束时间*/
	protected Short   workType=1;  /*类型*/	protected String  memo;      /*备注*/
	protected String  userName;  /*用户名称*/
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 主题
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 用户ID
	 * @return
	 */
	public String getUserId() 
	{
		return this.userId;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 开始时间
	 * @return
	 */
	public Date getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 结束时间
	 * @return
	 */
	public Date getEndTime() 
	{
		return this.endTime;
	}
	public void setWorkType(Short workType) 
	{
		this.workType = workType;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public Short getWorkType() 
	{
		return this.workType;
	}
	public void setMemo(String memo) 
	{
		this.memo = memo;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getMemo() 
	{
		return this.memo;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("subject", this.subject) 
		.append("userId", this.userId) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("workType", this.workType) 
		.append("memo", this.memo) 
		.toString();
	}
}