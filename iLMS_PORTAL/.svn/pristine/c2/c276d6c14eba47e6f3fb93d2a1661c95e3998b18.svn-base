package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：任务催办 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:miaojf
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-07-28 16:52:35
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmTaskReminder extends AbstractModel<String>{
	public final static String TASK_DUE_ACTION_NO_ACTION = "no-action"; // 无动作
	public final static String TASK_DUE_ACTION_AUTO_NEXT = "auto-next"; // 自动下一个任务
	public final static String TASK_DUE_ACTION_END_PROCESS = "end-process"; // 结束任务
	public final static String TASK_DUE_ACTION_CALL_METHOD = "call-method"; // 调用方法
	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 催办任务ID
	*/
	protected String taskId; 
	
	/**
	* 催办名称
	*/
	protected String name; 
	
	/**
	* 相对时间
	*/
	protected java.util.Date relDate; 
	
	/**
	* 到期执行动作
	*/
	protected String dueAction; 
	
	/**
	* 调用指定方法
	*/
	protected String dueScript; 
	
	/**
	* 到期日期
	*/
	protected java.util.Date dueDate; 
	
	/**
	* 期间是否发送催办
	*/
	protected Integer isSendMsg; 
	
	/**
	* 发送催办消息开始时间
	*/
	protected java.util.Date msgSendDate; 
	
	/**
	* 发送消息间隔
	*/
	protected Integer msgInterval; 
	
	/**
	* 发送次数
	*/
	protected Integer msgCount; 
	
	/**
	* 消息类型  inner,msg,email 等
	*/
	protected String msgType; 
	
	/**
	* 富文本内容
	*/
	protected String htmlMsg = ""; 
	
	/**
	* 普通文本内容
	*/
	protected String plainMsg = ""; 
	
	/**
	* 预警配置（预警名称，triggerDate，change2level）
	*/
	protected String warningset; 
	
	/**
	* 触发时间(每次触发后更新触发时间)
	*/
	protected java.util.Date triggerDate; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	/**
	 * 返回 催办任务ID
	 * @return
	 */
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 催办名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setRelDate(java.util.Date relDate) {
		this.relDate = relDate;
	}
	
	/**
	 * 返回 相对时间
	 * @return
	 */
	public java.util.Date getRelDate() {
		return this.relDate;
	}
	
	public void setDueAction(String dueAction) {
		this.dueAction = dueAction;
	}
	
	/**
	 * 返回 到期执行动作
	 * @return
	 */
	public String getDueAction() {
		return this.dueAction;
	}
	
	public void setDueScript(String dueScript) {
		this.dueScript = dueScript;
	}
	
	/**
	 * 返回 调用指定方法
	 * @return
	 */
	public String getDueScript() {
		return this.dueScript;
	}
	
	public void setDueDate(java.util.Date dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
	 * 返回 到期日期
	 * @return
	 */
	public java.util.Date getDueDate() {
		return this.dueDate;
	}
	
	public void setIsSendMsg(Integer isSendMsg) {
		this.isSendMsg = isSendMsg;
	}
	
	/**
	 * 返回 期间是否发送催办
	 * @return
	 */
	public Integer getIsSendMsg() {
		return this.isSendMsg;
	}
	
	public void setMsgSendDate(java.util.Date msgSendDate) {
		this.msgSendDate = msgSendDate;
	}
	
	/**
	 * 返回 发送催办消息开始时间
	 * @return
	 */
	public java.util.Date getMsgSendDate() {
		return this.msgSendDate;
	}
	
	public void setMsgInterval(Integer msgInterval) {
		this.msgInterval = msgInterval;
	}
	
	/**
	 * 返回 发送消息间隔
	 * @return
	 */
	public Integer getMsgInterval() {
		return this.msgInterval;
	}
	
	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
	
	/**
	 * 返回 发送次数
	 * @return
	 */
	public Integer getMsgCount() {
		return this.msgCount;
	}
	
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	/**
	 * 返回 消息类型  inner,msg,email 等
	 * @return
	 */
	public String getMsgType() {
		return this.msgType;
	}
	
	public void setHtmlMsg(String htmlMsg) {
		this.htmlMsg = htmlMsg;
	}
	
	/**
	 * 返回 富文本内容
	 * @return
	 */
	public String getHtmlMsg() {
		return this.htmlMsg;
	}
	
	public void setPlainMsg(String plainMsg) {
		this.plainMsg = plainMsg;
	}
	
	/**
	 * 返回 普通文本内容
	 * @return
	 */
	public String getPlainMsg() {
		return this.plainMsg;
	}
	
	public void setWarningset(String warningset) {
		this.warningset = warningset;
	}
	
	/**
	 * 返回 预警配置（预警名称，triggerDate，change2level）
	 * @return
	 */
	public String getWarningset() {
		return this.warningset;
	}
	
	public void setTriggerDate(java.util.Date triggerDate) {
		this.triggerDate = triggerDate;
	}
	
	/**
	 * 返回 触发时间(每次触发后更新触发时间)
	 * @return
	 */
	public java.util.Date getTriggerDate() {
		return this.triggerDate;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("taskId", this.taskId) 
		.append("name", this.name) 
		.append("relDate", this.relDate) 
		.append("dueAction", this.dueAction) 
		.append("dueScript", this.dueScript) 
		.append("dueDate", this.dueDate) 
		.append("isSendMsg", this.isSendMsg) 
		.append("msgSendDate", this.msgSendDate) 
		.append("msgInterval", this.msgInterval) 
		.append("msgCount", this.msgCount) 
		.append("msgType", this.msgType) 
		.append("htmlMsg", this.htmlMsg) 
		.append("plainMsg", this.plainMsg) 
		.append("warningset", this.warningset) 
		.append("triggerDate", this.triggerDate) 
		.toString();
	}
}