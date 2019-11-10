package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：堆栈关系表 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-06-18 17:38:35
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmExeStackRelation extends AbstractModel<String>{
	protected String relationId; /*关系ID*/
	protected String procInstId; /*流程实例ID*/
	protected String fromStackId; /*来自堆栈ID*/
	protected String toStackId; /*到达堆栈ID*/
	protected String fromNodeId; /*来自节点*/
	protected String toNodeId; /*到达节点*/
	protected Short relationState=1; /*关系状态：1正常，0回收作废*/
	protected String fromNodeType; /*来自的节点类型*/
	protected String toNodeType; /*到达的节点类型*/
	protected java.util.Date createdTime=new Date(); /*创建时间*/
	
	
	protected boolean isMarked=false; /*是否遍历过，用于驳回时寻找历史时标记*/
	
	public void setIsMarked(boolean isMarked) {
		this.isMarked =isMarked;
	}
	public boolean getIsMarked() {
		return isMarked;
	}
	
	@Override
	public void setId(String relationId) {
		this.relationId = relationId.toString();
	}
	@Override
	public String getId() {
		return relationId.toString();
	}	
	public void setRelationId(String relationId) 
	{
		this.relationId = relationId;
	}
	/**
	 * 返回 关系ID
	 * @return
	 */
	public String getRelationId() 
	{
		return this.relationId;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}
	/**
	 * 返回 流程实例ID
	 * @return
	 */
	public String getProcInstId() 
	{
		return this.procInstId;
	}
	public void setFromStackId(String fromStackId) 
	{
		this.fromStackId = fromStackId;
	}
	/**
	 * 返回 来自堆栈ID
	 * @return
	 */
	public String getFromStackId() 
	{
		return this.fromStackId;
	}
	public void setToStackId(String toStackId) 
	{
		this.toStackId = toStackId;
	}
	/**
	 * 返回 到达堆栈ID
	 * @return
	 */
	public String getToStackId() 
	{
		return this.toStackId;
	}
	public void setFromNodeId(String fromNodeId) 
	{
		this.fromNodeId = fromNodeId;
	}
	/**
	 * 返回 来自节点
	 * @return
	 */
	public String getFromNodeId() 
	{
		return this.fromNodeId;
	}
	public void setToNodeId(String toNodeId) 
	{
		this.toNodeId = toNodeId;
	}
	/**
	 * 返回 到达节点
	 * @return
	 */
	public String getToNodeId() 
	{
		return this.toNodeId;
	}
	public void setRelationState(Short relationState) 
	{
		this.relationState = relationState;
	}
	/**
	 * 返回 关系状态：1正常，0回收作废
	 * @return
	 */
	public Short getRelationState() 
	{
		return this.relationState;
	}
	public void setFromNodeType(String fromNodeType) 
	{
		this.fromNodeType = fromNodeType;
	}
	/**
	 * 返回 来自的节点类型
	 * @return
	 */
	public String getFromNodeType() 
	{
		return this.fromNodeType;
	}
	public void setToNodeType(String toNodeType) 
	{
		this.toNodeType = toNodeType;
	}
	/**
	 * 返回 到达的节点类型
	 * @return
	 */
	public String getToNodeType() 
	{
		return this.toNodeType;
	}
	public void setCreatedTime(java.util.Date createdTime) 
	{
		this.createdTime = createdTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreatedTime() 
	{
		return this.createdTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("relationId", this.relationId) 
		.append("procInstId", this.procInstId) 
		.append("fromStackId", this.fromStackId) 
		.append("toStackId", this.toStackId) 
		.append("fromNodeId", this.fromNodeId) 
		.append("toNodeId", this.toNodeId) 
		.append("relationState", this.relationState) 
		.append("fromNodeType", this.fromNodeType) 
		.append("toNodeType", this.toNodeType) 
		.append("createdTime", this.createdTime) 
		.toString();
	}
}