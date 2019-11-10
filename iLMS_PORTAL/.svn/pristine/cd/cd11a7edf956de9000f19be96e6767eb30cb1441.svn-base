package com.hotent.sys.persistence.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.org.api.model.IUser;

/**
 * 
 * <pre>
 * 描述：系统操作日志 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 14:37:00
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class Log extends AbstractModel<String> {
	protected String id; /* 主键 */
	protected String module; /* 模块 */
	protected String subModule; /* 子模块 */
	protected String content; /* 内容 */
	protected String operatorId; /* 操作人ID */
	protected String operator; /* 操作类型*/
	protected java.util.Date createTime; /* 创建时间 */
	
	protected String operatorName; /*操作人名字*/
	
	public Log() {
		super();
	}
	
	public Log(String id, String module, String subModule, String content,
			String operatorId, String operator, Date createTime) {
		super();
		this.id = id;
		this.module = module;
		this.subModule = subModule;
		this.content = content;
		this.operatorId = operatorId;
		this.operator = operator;
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 返回 模块
	 * 
	 * @return
	 */
	public String getModule() {
		return this.module;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}

	/**
	 * 返回 子模块
	 * 
	 * @return
	 */
	public String getSubModule() {
		return this.subModule;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 返回 内容
	 * 
	 * @return
	 */
	public String getContent() {
		return this.content;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 返回 操作人ID
	 * 
	 * @return
	 */
	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 返回 操作人
	 * 
	 * @return
	 */
	public String getOperator() {
		return this.operator;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * 返回用户
	 * 
	 * @return
	 */
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}	

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id)
				.append("module", this.module)
				.append("subModule", this.subModule)
				.append("content", this.content)
				.append("operatorId", this.operatorId)
				.append("operator", this.operator)
				.append("createTime", this.createTime).toString();
	}


}