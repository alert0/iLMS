package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_script entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:helh
 * 创建时间:2014-05-08 15:43:24
 */
public class AliasScript extends AbstractModel<String> implements Cloneable {
	
	
	private static final long serialVersionUID = 1L;
	protected String  id;			/*ID*/
	protected String  aliasName;	/*脚本的别名*/
	protected String  aliasDesc;	/*脚本的描叙*/
	protected String  className;	/*调用类的路径*/
	protected String  classInsName;	/*调用类的对象名*/
	protected String  methodName;	/*调用的方法名*/
	protected String  methodDesc;	/*调用的方法的描叙*/
	protected String  scriptContent;/*自定义脚本内容*/
	protected String  returnType;	/*方法返回类型*/
	protected Integer type;			/*脚本类型（自定义：1  系统默认：0）*/
	protected String  argument;		/*方法相关设置*/
	protected Integer enable;		/*是否禁用（禁用：1   启用：0）*/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getAliasDesc() {
		return aliasDesc;
	}
	public void setAliasDesc(String aliasDesc) {
		this.aliasDesc = aliasDesc;
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
	public String getScriptContent() {
		return scriptContent;
	}
	public void setScriptContent(String scriptContent) {
		this.scriptContent = scriptContent;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("aliasName", this.aliasName) 
		.append("aliasDesc", this.aliasDesc) 
		.append("className", this.className) 
		.append("classInsName", this.classInsName) 
		.append("methodName", this.methodName) 
		.append("methodDesc", this.methodDesc) 
		.append("scriptContent", this.scriptContent) 
		.append("returnType", this.returnType)
		.append("type", this.type) 
		.append("argument", this.argument) 
		.append("enable", this.enable) 
		.toString();
	}
}
