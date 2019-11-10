package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:代理指定流程 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-04-30 15:11:06
 */
public class BpmAgentDef extends AbstractModel<String>{
	protected String  id; /*主键*/
	protected String  settingId; /*设定ID*/
	protected String  flowKey; /*流程定义KEY*/
	protected String flowName="";
	protected String  nodeId; /*节点定义ID(为空的情况,如果指定ID,那么代理只在这些ID的任务生效)*/
	protected String  nodeName; /*节点名称*/
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
	public void setSettingId(String settingId) 
	{
		this.settingId = settingId;
	}
	/**
	 * 返回 设定ID
	 * @return
	 */
	public String getSettingId() 
	{
		return this.settingId;
	}
	public void setFlowKey(String flowKey) 
	{
		this.flowKey = flowKey;
	}
	/**
	 * 返回 流程定义KEY
	 * @return
	 */
	public String getFlowKey() 
	{
		return this.flowKey;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public void setNodeId(String nodeId) 
	{
		this.nodeId = nodeId;
	}
	/**
	 * 返回 节点定义ID(为空的情况,如果指定ID,那么代理只在这些ID的任务生效)
	 * @return
	 */
	public String getNodeId() 
	{
		return this.nodeId;
	}
	public void setNodeName(String nodeName) 
	{
		this.nodeName = nodeName;
	}
	/**
	 * 返回 节点名称
	 * @return
	 */
	public String getNodeName() 
	{
		return this.nodeName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("settingId", this.settingId) 
		.append("flowKey", this.flowKey) 
		.append("nodeId", this.nodeId) 
		.append("nodeName", this.nodeName) 
		.toString();
	}
}