package com.hotent.bpmx.persistence.model;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.bpmx.api.model.process.inst.BpmProCpto;

/**
 * 对象功能:流程实例抄送 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-05-03 11:46:20
 */
public class CopyTo extends AbstractModel<String> implements BpmProCpto{
	protected String  id; /*主键*/
	protected String  instId; /*流程实例ID*/
	protected String  bpmnInstId; /*ACT实例ID*/
	protected String  nodeId; /*节点ID*/

	protected java.util.Date  createTime; /*抄送时间*/
	protected String  opinion; /*意见*/
	protected String  subject; /*流程实例标题*/
	
	protected String  type; /*抄送类型(copyto抄送,trans转发)*/
	protected String  startorId; /*流程发起人*/
	protected String  startor; /*流程发起人*/
	
	protected String  typeId; /*流程分类*/
	
	/*是否已读*/
	protected Integer  isRead=0; 
	
	protected String  bId; /*接收表ID*/
	
	//转发接收人
	protected String recever;
	
	protected List<BpmCptoReceiver> receivers=new ArrayList<BpmCptoReceiver>();
	
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
	
	public void setBId(String bId) 
	{
		this.bId = bId;
	}
 
	public String getBId() 
	{
		return this.bId;
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
	public void setBpmnInstId(String bpmnInstId) 
	{
		this.bpmnInstId = bpmnInstId;
	}
	/**
	 * 返回 ACT实例ID
	 * @return
	 */
	public String getBpmnInstId() 
	{
		return this.bpmnInstId;
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
	
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 抄送时间
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setOpinion(String opinion) 
	{
		this.opinion = opinion;
	}
	/**
	 * 返回 意见
	 * @return
	 */
	public String getOpinion() 
	{
		return this.opinion;
	}
	public void setSubject(String subject) 
	{
		this.subject = subject;
	}
	/**
	 * 返回 流程实例标题
	 * @return
	 */
	public String getSubject() 
	{
		return this.subject;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 抄送类型(copyto抄送,trans转发)
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setStartorId(String startorId) 
	{
		this.startorId = startorId;
	}
	/**
	 * 返回 流程发起人
	 * @return
	 */
	public String getStartorId() 
	{
		return this.startorId;
	}
	public String getStartor() {
		return startor;
	}
	public void setStartor(String startor) {
		this.startor = startor;
	}
	public void setTypeId(String typeId) 
	{
		this.typeId = typeId;
	}
	/**
	 * 返回 流程分类
	 * @return
	 */
	public String getTypeId() 
	{
		return this.typeId;
	}
	
	
	public String getRecever() {
		return recever;
	}
	public void setRecever(String recever) {
		this.recever = recever;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public List<BpmCptoReceiver> getReceivers() {
		return receivers;
	}
	public void setReceivers(List<BpmCptoReceiver> receivers) {
		this.receivers = receivers;
	}
	
	public void addReceiver(BpmCptoReceiver receiver){
		this.receivers.add(receiver);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("instId", this.instId) 
		.append("bpmnInstId", this.bpmnInstId) 
		.append("nodeId", this.nodeId) 
	
		.append("createTime", this.createTime) 
		.append("opinion", this.opinion) 
		.append("subject", this.subject) 
	
		.append("type", this.type) 
		.append("startorId", this.startorId) 
		.append("typeId", this.typeId)
		.append("recever",this.recever)
		.toString();
	}
}