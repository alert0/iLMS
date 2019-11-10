package com.hotent.sys.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:sys_script entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:helh
 * 创建时间:2014-05-08 14:50:24
 */
public class Script extends AbstractModel<String> implements Cloneable{

	private static final long serialVersionUID = 1L;
	protected String id;		/*主键*/
	protected String name;		/*名称*/
	protected String script;	/*脚本*/
	protected String category;	/*脚本分类*/
	protected String memo;		/*备注*/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("script", this.script) 
		.append("category", this.category) 
		.append("memo", this.memo) 
		.toString();
	}
}
