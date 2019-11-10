package com.hotent.sys.api.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:执行任务日志 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-04-16 09:04:28
 */
public class SysJobLog extends AbstractModel<String>{
	
	/**
	 * 主键
	 */
	protected String id; 
	/**
	 * 任务名
	 */
	protected String jobName; 
	/**
	 * 触发器名称
	 */
	protected String trigName; 
	/*开始时间*/
	protected java.util.Date startTime; 
	/*结束时间*/
	protected java.util.Date endTime; 
	/*内容*/
	protected String content; 
	/*状态*/
	protected String state; 
	/*运行时长*/
	protected Long runTime; 
	
	
	public SysJobLog(){}
	
	
	
	
	public SysJobLog(String jobName, String trigName, Date startTime, Date endTime, String content, String state,
			Long runTime) {
		super();
		this.jobName = jobName;
		this.trigName = trigName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
		this.state = state;
		this.runTime = runTime;
	}




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
	public void setJobName(String jobName) 
	{
		this.jobName = jobName;
	}
	/**
	 * 返回 任务名
	 * @return
	 */
	public String getJobName() 
	{
		return this.jobName;
	}
	public void setTrigName(String trigName) 
	{
		this.trigName = trigName;
	}
	/**
	 * 返回 触发器名称
	 * @return
	 */
	public String getTrigName() 
	{
		return this.trigName;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 开始时间
	 * @return
	 */
	public java.util.Date getStartTime() 
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
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 状态
	 * @return
	 */
	public String getState() 
	{
		return this.state;
	}
	public void setRunTime(Long runTime) 
	{
		this.runTime = runTime;
	}
	/**
	 * 返回 运行时长
	 * @return
	 */
	public Long getRunTime() 
	{
		return this.runTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("jobName", this.jobName) 
		.append("trigName", this.trigName) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("content", this.content) 
		.append("state", this.state) 
		.append("runTime", this.runTime) 
		.toString();
	}
}