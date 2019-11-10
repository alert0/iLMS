package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:URL拦器规则脚本 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-10-11 14:57:01
 */
public class SysUrlRules extends AbstractModel<String>{
	protected String id; /*主键*/
	protected String script; /*groovy脚本*/
	protected Short enable; /*是否启用；0：禁用；1：启用*/
	protected String sysUrlId; /*URL地址拦截管理ID*/
	protected String descp; /*描述*/
	protected Short sort; /*排序*/
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
	public void setScript(String script) 
	{
		this.script = script;
	}
	/**
	 * 返回 groovy脚本
	 * @return
	 */
	public String getScript() 
	{
		return this.script;
	}
	public void setEnable(Short enable) 
	{
		this.enable = enable;
	}
	/**
	 * 返回 是否启用；0：禁用；1：启用
	 * @return
	 */
	public Short getEnable() 
	{
		return this.enable;
	}
	public void setSysUrlId(String sysUrlId) 
	{
		this.sysUrlId = sysUrlId;
	}
	/**
	 * 返回 URL地址拦截管理ID
	 * @return
	 */
	public String getSysUrlId() 
	{
		return this.sysUrlId;
	}
	public void setDescp(String descp) 
	{
		this.descp = descp;
	}
	/**
	 * 返回 描述
	 * @return
	 */
	public String getDescp() 
	{
		return this.descp;
	}
	public void setSort(Short sort) 
	{
		this.sort = sort;
	}
	/**
	 * 返回 排序
	 * @return
	 */
	public Short getSort() 
	{
		return this.sort;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("script", this.script) 
		.append("enable", this.enable) 
		.append("sysUrlId", this.sysUrlId) 
		.append("descp", this.descp) 
		.append("sort", this.sort) 
		.toString();
	}
}