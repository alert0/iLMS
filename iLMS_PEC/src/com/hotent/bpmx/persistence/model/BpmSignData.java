package com.hotent.bpmx.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:会签数据 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-03-30 11:34:33
 */
public class BpmSignData extends AbstractModel<String>{
	
	public final static String TYPE_NORMAL = "NORMAL";//正常会签数据
	
	public final static String TYPE_ADDSIGN = "ADDSIGN";//加签会签数据
	
	protected String  id; /*主键*/
	protected String  defId; /*流程定义ID*/
	protected String  instId; /*流程实例ID*/
	//执行ID ,这个字段对应ACT_RU_EXECUTION 的ID_字段。
	protected String  executeId="";
	
	protected String  actInstId; /*ACT流程实例ID*/
	protected String  nodeId; /*节点ID*/
	protected String  taskId; /*流程任务ID*/
	protected String  qualifiedId; /*有资格审批的成员ID*/
	protected String  qualifiedName; /*有资格审批的成员名称*/
	protected java.util.Date  createTime; /*创建时间*/
	protected String  voteResult; /*投票结果(no 未投票 通过 agree,abandon 弃权,oppose 反对)*/
	protected String  voteId; /*投票人*/
	protected String  voter; /*投票人*/
	protected java.util.Date  voteTime; /*投票时间*/
	protected Short index=0;
	//是否活动记录。
	protected int isActive=0;
	
	//数据类型（NORMAL正常会签数据，ADDSIGN加签会签数据）
	protected String type="NORMAL";
	
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
	public void setDefId(String defId) 
	{
		this.defId = defId;
	}
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getDefId() 
	{
		return this.defId;
	}
	public void setInstId(String instId) 
	{
		this.instId = instId;
	}
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public String getInstId() 
	{
		return this.instId;
	}
	public void setActInstId(String actInstId) 
	{
		this.actInstId = actInstId;
	}
	/**
	 * 返回 ACT流程实例ID
	 * @return
	 */
	public String getActInstId() 
	{
		return this.actInstId;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 节点ID
	 * @return
	 */
	public String getNodeId() 
	{
		return this.nodeId;
	}
	public void setTaskId(String taskId) 
	{
		this.taskId = taskId;
	}
	/**
	 * 返回 流程任务ID
	 * @return
	 */
	public String getTaskId() 
	{
		return this.taskId;
	}
	public void setQualifiedId(String qualifiedId) 
	{
		this.qualifiedId = qualifiedId;
	}
	/**
	 * 返回 有资格审批的成员ID
	 * @return
	 */
	public String getQualifiedId() 
	{
		return this.qualifiedId;
	}
	public void setQualifiedName(String qualifiedName) 
	{
		this.qualifiedName = qualifiedName;
	}
	/**
	 * 返回 有资格审批的成员名称
	 * @return
	 */
	public String getQualifiedName() 
	{
		return this.qualifiedName;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setVoteResult(String voteResult) 
	{
		this.voteResult = voteResult;
	}
	/**
	 * 返回 投票结果(no 未投票 通过 agree,abandon 弃权,oppose 反对)
	 * @return
	 */
	public String getVoteResult() 
	{
		return this.voteResult;
	}
	public void setVoteId(String voteId) 
	{
		this.voteId = voteId;
	}
	/**
	 * 返回 投票人
	 * @return
	 */
	public String getVoteId() 
	{
		return this.voteId;
	}
	public void setVoter(String voter) 
	{
		this.voter = voter;
	}
	/**
	 * 返回 投票人
	 * @return
	 */
	public String getVoter() 
	{
		return this.voter;
	}
	public void setVoteTime(java.util.Date voteTime) 
	{
		this.voteTime = voteTime;
	}
	/**
	 * 返回 投票时间
	 * @return
	 */
	public java.util.Date getVoteTime() 
	{
		return this.voteTime;
	}
	
	public Short getIndex() {
		return index;
	}
	public void setIndex(Short index) {
		this.index = index;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExecuteId() {
		return executeId;
	}
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("defId", this.defId) 
		.append("instId", this.instId) 
		.append("actInstId", this.actInstId) 
		.append("nodeId", this.nodeId) 
		.append("taskId", this.taskId) 
		.append("qualifiedId", this.qualifiedId) 
		.append("qualifiedName", this.qualifiedName) 
		.append("createTime", this.createTime) 
		.append("voteResult", this.voteResult) 
		.append("voteId", this.voteId) 
		.append("voter", this.voter) 
		.append("voteTime", this.voteTime) 
		.toString();
	}
}