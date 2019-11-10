package com.hotent.bpmx.persistence.model;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:act_ru_execution entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-04-03 21:44:13
 */
public class ActExecution extends AbstractModel<String> implements Cloneable{
	protected String  id; /*ID_*/
	protected Integer  rev; /*REV_*/
	protected String  procInstId; /*PROC_INST_ID_*/
	protected String  businessKey; /*BUSINESS_KEY_*/
	protected String  parentId; /*PARENT_ID_*/
	protected String  procDefId; /*PROC_DEF_ID_*/
	protected String  superExec; /*SUPER_EXEC_*/
	protected String  actId; /*ACT_ID_*/
	protected Short  isActive; /*IS_ACTIVE_*/
	protected Short  isConcurrent; /*IS_CONCURRENT_*/
	protected Short  isScope; /*IS_SCOPE_*/
	protected Short  isEventScope; /*IS_EVENT_SCOPE_*/
	protected Integer  suspensionState; /*SUSPENSION_STATE_*/
	protected Integer  cachedEntState; /*CACHED_ENT_STATE_*/
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 ID_
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setRev(Integer rev) 
	{
		this.rev = rev;
	}
	/**
	 * 返回 REV_
	 * @return
	 */
	public Integer getRev() 
	{
		return this.rev;
	}
	public void setProcInstId(String procInstId) 
	{
		this.procInstId = procInstId;
	}
	/**
	 * 返回 PROC_INST_ID_
	 * @return
	 */
	public String getProcInstId() 
	{
		return this.procInstId;
	}
	public void setBusinessKey(String businessKey) 
	{
		this.businessKey = businessKey;
	}
	/**
	 * 返回 BUSINESS_KEY_
	 * @return
	 */
	public String getBusinessKey() 
	{
		return this.businessKey;
	}
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	/**
	 * 返回 PARENT_ID_
	 * @return
	 */
	public String getParentId() 
	{
		return this.parentId;
	}
	public void setProcDefId(String procDefId) 
	{
		this.procDefId = procDefId;
	}
	/**
	 * 返回 PROC_DEF_ID_
	 * @return
	 */
	public String getProcDefId() 
	{
		return this.procDefId;
	}
	public void setSuperExec(String superExec) 
	{
		this.superExec = superExec;
	}
	/**
	 * 返回 SUPER_EXEC_
	 * @return
	 */
	public String getSuperExec() 
	{
		return this.superExec;
	}
	public void setActId(String actId) 
	{
		this.actId = actId;
	}
	/**
	 * 返回 ACT_ID_
	 * @return
	 */
	public String getActId() 
	{
		return this.actId;
	}
	public void setIsActive(Short isActive) 
	{
		this.isActive = isActive;
	}
	/**
	 * 返回 IS_ACTIVE_
	 * @return
	 */
	public Short getIsActive() 
	{
		return this.isActive;
	}
	public void setIsConcurrent(Short isConcurrent) 
	{
		this.isConcurrent = isConcurrent;
	}
	/**
	 * 返回 IS_CONCURRENT_
	 * @return
	 */
	public Short getIsConcurrent() 
	{
		return this.isConcurrent;
	}
	public void setIsScope(Short isScope) 
	{
		this.isScope = isScope;
	}
	/**
	 * 返回 IS_SCOPE_
	 * @return
	 */
	public Short getIsScope() 
	{
		return this.isScope;
	}
	public void setIsEventScope(Short isEventScope) 
	{
		this.isEventScope = isEventScope;
	}
	/**
	 * 返回 IS_EVENT_SCOPE_
	 * @return
	 */
	public Short getIsEventScope() 
	{
		return this.isEventScope;
	}
	public void setSuspensionState(Integer suspensionState) 
	{
		this.suspensionState = suspensionState;
	}
	/**
	 * 返回 SUSPENSION_STATE_
	 * @return
	 */
	public Integer getSuspensionState() 
	{
		return this.suspensionState;
	}
	public void setCachedEntState(Integer cachedEntState) 
	{
		this.cachedEntState = cachedEntState;
	}
	/**
	 * 返回 CACHED_ENT_STATE_
	 * @return
	 */
	public Integer getCachedEntState() 
	{
		return this.cachedEntState;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("rev", this.rev) 
		.append("procInstId", this.procInstId) 
		.append("businessKey", this.businessKey) 
		.append("parentId", this.parentId) 
		.append("procDefId", this.procDefId) 
		.append("superExec", this.superExec) 
		.append("actId", this.actId) 
		.append("isActive", this.isActive) 
		.append("isConcurrent", this.isConcurrent) 
		.append("isScope", this.isScope) 
		.append("isEventScope", this.isEventScope) 
		.append("suspensionState", this.suspensionState) 
		.append("cachedEntState", this.cachedEntState) 
		.toString();
	}
	
	
	@Override
	public Object clone(){
		ActExecution o = null;  
        try {  
            o = (ActExecution) super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
	    return o;  
	}
}