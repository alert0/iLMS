package com.hotent.sys.api.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：sys_call_log 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:zhangxw
 * 邮箱:zhangxw@jee-soft.cn
 * 日期:2017-10-26 11:40:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysCallLog extends AbstractModel<String>{
	
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	*  流程定义ID
	*/
	protected String procDefId; 
	
	/**
	* 流程定义Key
	*/
	protected String procDefKey; 
	
	/**
	* 任务ID
	*/
	protected String taskId; 
	
	/**
	* 父流程实例ID
	*/
	protected String supInstId; 
	
	/**
	* 流程实例ID
	*/
	protected String procInstId; 
	
	/**
	* 任务定义Key
	*/
	protected String taskKey; 
	
	/**
	* 任务名称
	*/
	protected String taskName; 
	
	/**
	* 调用人员ID
	*/
	protected String userId; 
	
	/**
	* 事件类型
	*/
	protected String eventType; 
	
	/**
	* 调用地址
	*/
	protected String url; 
	
	/**
	* 接口描述
	*/
	protected String desc; 
	
	/**
	* 接口调用模式:0:同步，1:异步
	*/
	protected Integer invokeMode; 
	
	/**
	* 是否调用成功
	*/
	protected Boolean isSuccess; 
	
	/**
	* 调用结果
	*/
	protected String response; 
	
	/**
	* 调用时间
	*/
	protected java.util.Date callTime; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	
	/**
	 * 返回  流程定义ID
	 * @return
	 */
	public String getProcDefId() {
		return this.procDefId;
	}
	
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	
	/**
	 * 返回 流程定义Key
	 * @return
	 */
	public String getProcDefKey() {
		return this.procDefKey;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * 返回 任务ID
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setSupInstId(String supInstId) {
		this.supInstId = supInstId;
	}
	
	/**
	 * 返回 父流程实例ID
	 * @return
	 */
	public String getSupInstId() {
		return this.supInstId;
	}
	
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public String getProcInstId() {
		return this.procInstId;
	}
	
	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}
	
	/**
	 * 返回 任务定义Key
	 * @return
	 */
	public String getTaskKey() {
		return this.taskKey;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	/**
	 * 返回 任务名称
	 * @return
	 */
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 调用人员ID
	 * @return
	 */
	public String getUserId() {
		return this.userId;
	}
	
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	/**
	 * 返回 事件类型
	 * @return
	 */
	public String getEventType() {
		return this.eventType;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * 返回 调用地址
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * 返回 接口描述
	 * @return
	 */
	public String getDesc() {
		return this.desc;
	}
	
	public void setInvokeMode(Integer invokeMode) {
		this.invokeMode = invokeMode;
	}
	
	/**
	 * 返回 接口调用模式:0:同步，1:异步
	 * @return
	 */
	public Integer getInvokeMode() {
		return this.invokeMode;
	}
	
	
	
	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	/**
	 * 返回 调用结果
	 * @return
	 */
	public String getResponse() {
		return this.response;
	}
	
	public void setCallTime(java.util.Date callTime) {
		this.callTime = callTime;
	}
	
	/**
	 * 返回 调用时间
	 * @return
	 */
	public java.util.Date getCallTime() {
		return this.callTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("procDefId", this.procDefId) 
		.append("procDefKey", this.procDefKey) 
		.append("taskId", this.taskId) 
		.append("supInstId", this.supInstId) 
		.append("procInstId", this.procInstId) 
		.append("taskKey", this.taskKey) 
		.append("taskName", this.taskName) 
		.append("userId", this.userId) 
		.append("eventType", this.eventType) 
		.append("url", this.url) 
		.append("desc", this.desc) 
		.append("invokeMode", this.invokeMode) 
		.append("isSuccess", this.isSuccess) 
		.append("response", this.response) 
		.append("callTime", this.callTime) 
		.toString();
	}
}