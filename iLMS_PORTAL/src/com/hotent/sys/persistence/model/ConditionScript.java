package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_script entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:helh
 * 创建时间:2014-05-08 15:19:20
 */
public class ConditionScript extends AbstractModel<String> implements Cloneable{

	private static final long serialVersionUID = 1L;
	protected String  id;			/*主键*/
	protected String  className;	/*脚本所在类的类名*/
	protected String  classInsName;	/*类实例名*/
	protected String  methodName;	/*方法名*/
	protected String  methodDesc;	/*方法描述*/
	protected String  returnType;	/*返回值类型*/
	/**
	 *  参数信息
	 * [
	 * 	{
	 * 		"paraName":"arg0",
	 * 		"paraType":"org.activiti.engine.impl.persistence.entity.TaskEntity",
	 * 		"paraDesc":"任务实体",
	 * 		"paraCt":"18"
	 *  }
	 * ]
	 */
	protected String  argument;		
	protected Integer enable=1;		/*是否有效 ，0：否 ， 1：是*/
	protected Integer type;		/*脚本类型，条件脚本 1，人员脚本 2*/
	 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassInsName() {
		return classInsName;
	}
	public void setClassInsName(String classInsName) {
		this.classInsName = classInsName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodDesc() {
		return methodDesc;
	}
	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("className", this.className) 
		.append("classInsName", this.classInsName) 
		.append("methodName", this.methodName) 
		.append("methodDesc", this.methodDesc) 
		.append("returnType", this.returnType) 
		.append("argument", this.argument) 
		.append("enable", this.enable) 
		.toString();
	}
}
