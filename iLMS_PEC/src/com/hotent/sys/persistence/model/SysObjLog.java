package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;


 /**
 * 
 * <pre> 
 * 描述：常用日志 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-09-27 10:33:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysObjLog extends AbstractModel<String>{
	
	/**
	* ID_
	*/
	protected String id; 
	
	/**
	* OPERATOR_ID_
	*/
	protected String operatorId; 
	
	/**
	* OPERATOR_
	*/
	protected String operator; 
	
	/**
	* CREATE_TIME_
	*/
	protected java.util.Date createTime; 
	
	/**
	* NAME_
	*/
	protected String name; 
	
	/**
	* CONTENT_
	*/
	protected String content; 
	
	/**
	* OBJ_TYPE_
	*/
	protected String objType; 
	
	/**
	* PARAM_
	*/
	protected String param; 
	
	public SysObjLog() {

	}
	
	
	public SysObjLog(String name, String content,String objType) {
		super();
		
		IUser sysUser =ContextUtil.getCurrentUser();
		this.setOperator(sysUser.getFullname());
		this.setOperatorId(sysUser.getUserId());
		
		this.name = name;
		this.content = content;
		this.objType = objType;
	}
	
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
	
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	/**
	 * 返回 OPERATOR_ID_
	 * @return
	 */
	public String getOperatorId() {
		return this.operatorId;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	

	/**
	 * 返回 OPERATOR_
	 * @return
	 */
	public String getOperator() {
		return this.operator;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 返回 CREATE_TIME_
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 NAME_
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 返回 CONTENT_
	 * @return
	 */
	public String getContent() {
		return this.content;
	}
	
	public void setObjType(String objType) {
		this.objType = objType;
	}
	
	/**
	 * 返回 OBJ_TYPE_
	 * @return
	 */
	public String getObjType() {
		return this.objType;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	/**
	 * 返回 PARAM_
	 * @return
	 */
	public String getParam() {
		return this.param;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("operatorId", this.operatorId) 
		.append("operator", this.operator) 
		.append("createTime", this.createTime) 
		.append("name", this.name) 
		.append("content", this.content) 
		.append("objType", this.objType) 
		.append("param", this.param) 
		.toString();
	}
}