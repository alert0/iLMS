package com.hotent.sys.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:IP地址管理 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zyp
 * 创建时间:2014-07-24 10:11:20
 */
public class SysAcceptIp extends AbstractModel<String>{
	protected String acceptId; /*主键*/
	protected String title; /*标题*/
	protected String startIp; /*开始IP*/
	protected String endIp; /*结束IP*/
	protected String remark; /*备注*/
	@Override
	public void setId(String acceptId) {
		this.acceptId = acceptId.toString();
	}
	@Override
	public String getId() {
		return acceptId.toString();
	}	
	public void setAcceptId(String acceptId) 
	{
		this.acceptId = acceptId;
	}
	/**
	 * 返回 主键
	 * @return
	 */
	public String getAcceptId() 
	{
		return this.acceptId;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 标题
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
	}
	public void setStartIp(String startIp) 
	{
		this.startIp = startIp;
	}
	/**
	 * 返回 开始IP
	 * @return
	 */
	public String getStartIp() 
	{
		return this.startIp;
	}
	public void setEndIp(String endIp) 
	{
		this.endIp = endIp;
	}
	/**
	 * 返回 结束IP
	 * @return
	 */
	public String getEndIp() 
	{
		return this.endIp;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("acceptId", this.acceptId) 
		.append("title", this.title) 
		.append("startIp", this.startIp) 
		.append("endIp", this.endIp) 
		.append("remark", this.remark) 
		.toString();
	}
}