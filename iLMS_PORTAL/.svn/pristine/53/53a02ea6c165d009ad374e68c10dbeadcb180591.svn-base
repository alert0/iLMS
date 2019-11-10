package com.hotent.bo.api.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.hotent.base.core.model.AbstractModel;

/**
 * 基础bo定义。
 * @author ray
 */
public class BaseBoDef extends AbstractModel<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4123138805823634808L;

	/**
	 * 实体关系。
	 * @author ray
	 *
	 */
	public final static class BOENT_RELATION {
		
		/**
		 * 主实体。
		 */
		public final static String MAIN = "main";
		/**
		 * 一对一。
		 */
		public final static String ONE_TO_ONE = "onetoone";
		/**
		 * 一对多。
		 */
		public final static String ONE_TO_MANY = "onetomany";
		/**
		 * 多对多。
		 */
		public final static String MANY_TO_MANY = "manytomany";
	}
	
	private String id="";
	
	/**
	 * bo定义名称。
	 */
	@XmlAttribute(name = "alias")
	private String alias="";
	/**
	 * bo 描述。
	 */
	@XmlElement(name = "description")
	private String description="";
	
	@XmlElement(name = "ent")
	private BaseBoEnt boEnt=new BaseBoEnt();
	
	/**
	 * 支持数据库
	 */
	@XmlAttribute(name = "supportDb")
	protected Short supportDb = 0;
	
	/**
	 * 分类ID
	 */
	@XmlAttribute(name = "categoryId")
	protected String categoryId = "";
	
	
	/**
	 * 是否禁用。 normal 正常,forbidden 禁止
	 */
	@XmlAttribute(name = "status")
	protected String status = "normal";
	
	/**
	 * 是否发布（发布后不能修改）
	 */
	@XmlAttribute(name = "deployed")
	protected Short deployed = 0;
	
	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BaseBoEnt getBoEnt() {
		return boEnt;
	}

	public void setBoEnt(BaseBoEnt boEnt) {
		this.boEnt = boEnt;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public Short getSupportDb() {
		return supportDb;
	}

	public void setSupportDb(Short supportDb) {
		this.supportDb = supportDb;
	}

	/**
	 * 返回 是否禁用
	 * 
	 * @return
	 */
	public String getStatus() {
		return this.status;
	}
	
	public Short getDeployed() {
		return deployed;
	}

	public void setDeployed(Short deployed) {
		this.deployed = deployed;
	}

	@Override
	public String toString() {
		return "BaseBoDef [alias=" + alias + ", description=" + description + ",categoryId=" + categoryId + "]";
	}
}
