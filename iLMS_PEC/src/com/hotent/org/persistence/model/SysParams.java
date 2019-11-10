package com.hotent.org.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：组织参数 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-10-31 14:29:12
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysParams extends AbstractModel<String>{
	
	private static final long serialVersionUID = 1L;

	/**
	* 编号
	*/
	protected String id; 
	
	/**
	* 参数名
	*/
	protected String name; 
	
	/**
	* 参数key
	*/
	protected String alias; 
	
	/**
	* 参数类型
	*/
	protected String type; 
	
	/**
	* 数据来源
	*/
	protected String ctlType; 
	
	/**
	* 数据
	*/
	protected String json; 
	
	
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
	 * 返回 参数名
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	/**
	 * 返回 参数key
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 参数类型
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	public void setCtlType(String ctlType) {
		this.ctlType = ctlType;
	}
	
	/**
	 * 返回 数据来源
	 * @return
	 */
	public String getCtlType() {
		return this.ctlType;
	}
	
	public void setJson(String json) {
		this.json = json;
	}
	
	/**
	 * 返回 数据
	 * @return
	 */
	public String getJson() {
		return this.json;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("alias", this.alias) 
		.append("type", this.type) 
		.append("ctlType", this.ctlType) 
		.append("json", this.json) 
		.toString();
	}
}