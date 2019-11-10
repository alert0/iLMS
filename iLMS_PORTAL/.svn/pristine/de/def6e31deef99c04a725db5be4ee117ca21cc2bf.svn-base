package com.hotent.bpmx.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：流程的测试用例设置 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2018-01-15 16:39:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmTestCase extends AbstractModel<String>{
	
	private static final long serialVersionUID = 1L;

	/**
	* 编号
	*/
	protected String id; 
	
	/**
	* 测试用例名称
	*/
	protected String name; 
	
	/**
	* 表单数据
	*/
	protected String boFormData; 
	
	/**
	* 流程变量
	*/
	protected String flowVars; 
	
	/**
	* 发起人
	*/
	protected String startor; 
	
	/**
	* 审批动作， 默认动作时 agree
	*/
	protected String actionType; 
	
	/**
	* 断点测试设置
	*/
	protected String bpmDebugger; 
	
	/**
	* 流程定义key
	*/
	protected String defKey; 
	
	/**
	 * 发起人账号
	 */
	protected String startorAccount;
	
	/**
	 * 发起人名称
	 */
	protected String startorFullName;
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 编号
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 测试用例名称
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setBoFormData(String boFormData) {
		this.boFormData = boFormData;
	}
	
	/**
	 * 返回 表单数据
	 * @return
	 */
	public String getBoFormData() {
		return this.boFormData;
	}
	
	public void setFlowVars(String flowVars) {
		this.flowVars = flowVars;
	}
	
	/**
	 * 返回 流程变量
	 * @return
	 */
	public String getFlowVars() {
		return this.flowVars;
	}
	
	public void setStartor(String startor) {
		this.startor = startor;
	}
	
	/**
	 * 返回 发起人
	 * @return
	 */
	public String getStartor() {
		return this.startor;
	}
	
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	/**
	 * 返回 审批动作， 默认动作时 agree
	 * @return
	 */
	public String getActionType() {
		return this.actionType;
	}
	
	public void setBpmDebugger(String bpmDebugger) {
		this.bpmDebugger = bpmDebugger;
	}
	
	/**
	 * 返回 断点测试设置
	 * @return
	 */
	public String getBpmDebugger() {
		return this.bpmDebugger;
	}
	
	public String getDefKey() {
		return defKey;
	}

	public void setDefKey(String defKey) {
		this.defKey = defKey;
	}
	
	public String getStartorAccount() {
		return startorAccount;
	}

	public void setStartorAccount(String startorAccount) {
		this.startorAccount = startorAccount;
	}

	public String getStartorFullName() {
		return startorFullName;
	}

	public void setStartorFullName(String startorFullName) {
		this.startorFullName = startorFullName;
	}
	
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("boFormData", this.boFormData) 
		.append("flowVars", this.flowVars) 
		.append("startor", this.startor) 
		.append("actionType", this.actionType) 
		.append("bpmDebugger", this.bpmDebugger) 
		.append("defKey", this.defKey) 
		.toString();
	}
}