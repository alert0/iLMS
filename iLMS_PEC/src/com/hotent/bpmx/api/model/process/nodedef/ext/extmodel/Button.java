package com.hotent.bpmx.api.model.process.nodedef.ext.extmodel;

import java.io.Serializable;

/**
 * 流程节点按钮定义。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-5-下午9:10:21
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class Button implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Button(){}
	
	
	
	public Button(String name, String alias) {
		super();
		this.name = name;
		this.alias = alias;
	}
	
	


	public Button(String name, String alias, boolean supportScript) {
		this.name = name;
		this.alias = alias;
		this.supportScript = supportScript;
	}

	public Button(String name, String alias, String beforeScript,
			String afterScript) {
		this.name = name;
		this.alias = alias;
		this.beforeScript = beforeScript;
		this.afterScript = afterScript;
	}
	/**
	 * 按钮名
	 */
	protected String name="";
	/**
	 * 按钮别名
	 */
    protected String alias="";
    
    /**
     * 前置脚本
     */
	protected String beforeScript="";
	/**
	 * 后置脚本
	 */
    protected String afterScript="";
    
    /**
     * 后台java脚本。
     */
    protected String groovyScript="";
    
    protected String urlForm = "";
    
	/**
     * 是否支持脚本
     */
    protected Boolean supportScript=false;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getBeforeScript() {
		return beforeScript;
	}
	public void setBeforeScript(String beforeScript) {
		this.beforeScript = beforeScript;
	}
	
	public String getAfterScript() {
		return afterScript;
	}
	public void setAfterScript(String afterScript) {
		this.afterScript = afterScript;
	}
	
	public Boolean getSupportScript() {
		return supportScript;
	}
	public void setSupportScript(Boolean supportScript) {
		this.supportScript = supportScript;
	}

	public String getGroovyScript() {
		return groovyScript;
	}

	public void setGroovyScript(String groovyScript) {
		this.groovyScript = groovyScript;
	}
	
    public String getUrlForm() {
		return urlForm;
	}

	public void setUrlForm(String urlForm) {
		this.urlForm = urlForm;
	}
	
	
	@Override
	public String toString() {
		return "[name=" + name + ", alias=" + alias + "]";
	}


}
