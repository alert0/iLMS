package com.hotent.bo.persistence.model;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.bo.api.model.BaseBoDef;

/**
 * BO 定义model
 * 
 * <pre>
 * 描述：BO 定义model
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-01 16:41:22
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BODef extends BaseBoDef {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3361948337932916844L;


	protected String creator = "";
	

	public BODef() {

	}

	public BODef(BaseBoDef baseBoDef) {
		this.setAlias(baseBoDef.getAlias());
		this.setDescription(baseBoDef.getDescription());
		this.setBoEnt(baseBoDef.getBoEnt());
		this.setCategoryId(baseBoDef.getCategoryId());
		this.setStatus(baseBoDef.getStatus());
		this.setDeployed(baseBoDef.getDeployed());
		this.setSupportDb(baseBoDef.getSupportDb());
	}



	public void setSupportDb(Short supportDb) {
		this.supportDb = supportDb;
	}

	/**
	 * 返回 支持数据库
	 * 
	 * @return
	 */
	public Short getSupportDb() {
		return this.supportDb;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 返回 创建人
	 * 
	 * @return
	 */
	public String getCreateBy() {
		return this.createBy;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	

	@Override
	public String toString() {
		return "BODef [ alias()=" + getAlias() + "]";
	}

	
}