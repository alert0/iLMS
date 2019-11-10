package com.hotent.sys.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：sys_query_sqldef 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:28:47
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class QuerySqldef extends AbstractModel<String> {

	/**
	 * ID_
	 */
	protected String id;

	/**
	 * ALIAS_
	 */
	@XmlAttribute(name = "alias")
	protected String alias;

	/**
	 * NAME_
	 */
	@XmlAttribute(name = "name")
	protected String name;

	/**
	 * DS_NAME_
	 */
	@XmlAttribute(name = "dsName")
	protected String dsName;

	/**
	 * SQL_
	 */
	@XmlElement(name = "sql")
	protected String sql;

	/**
	 * CATEGORY_ID_
	 */
	@XmlAttribute(name = "categoryId")
	protected String categoryId;

	/**
	 * SUPPORT_TAB_
	 */
	@XmlAttribute(name = "supportTab")
	protected Short supportTab;

	/**
	 * BUTTON_DEF_
	 */
	@XmlElement(name = "buttonDef")
	protected String buttonDef;

	// 以下字段跟数据库无关
	private List<QueryMetafield> metafields = new ArrayList<QueryMetafield>();
	private List<QueryView> views = new ArrayList<QueryView>();

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 ID_
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 ALIAS_
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 NAME_
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	/**
	 * 返回 DS_NAME_
	 * 
	 * @return
	 */
	public String getDsName() {
		return this.dsName;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 返回 SQL_
	 * 
	 * @return
	 */
	public String getSql() {
		return this.sql;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * 返回 CATEGORY_ID_
	 * 
	 * @return
	 */
	public String getCategoryId() {
		return this.categoryId;
	}

	public void setSupportTab(Short supportTab) {
		this.supportTab = supportTab;
	}

	/**
	 * 返回 SUPPORT_TAB_
	 * 
	 * @return
	 */
	public Short getSupportTab() {
		return this.supportTab;
	}

	public void setButtonDef(String buttonDef) {
		this.buttonDef = buttonDef;
	}

	/**
	 * 返回 BUTTON_DEF_
	 * 
	 * @return
	 */
	public String getButtonDef() {
		return this.buttonDef;
	}
	
	public List<QueryMetafield> getMetafields() {
		return metafields;
	}

	public void setMetafields(List<QueryMetafield> metafields) {
		this.metafields = metafields;
	}

	public List<QueryView> getViews() {
		return views;
	}

	public void setViews(List<QueryView> views) {
		this.views = views;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("alias", this.alias).append("name", this.name).append("dsName", this.dsName).append("sql", this.sql).append("categoryId", this.categoryId).append("supportTab", this.supportTab).append("buttonDef", this.buttonDef).toString();
	}
}