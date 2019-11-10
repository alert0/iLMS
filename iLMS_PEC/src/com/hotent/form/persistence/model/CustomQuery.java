package com.hotent.form.persistence.model;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:bpm_custom_query entity对象 开发公司:广州宏天软件有限公司 开发人员:liyj_aschs 创建时间:2014-07-01 15:18:22
 */
public class CustomQuery extends AbstractModel<String> {
	private static final long serialVersionUID = 1L;
	protected String id; /* 主键 */
	protected String name; /* 名字 */
	protected String alias; /* 别名 */
	protected String objName; /* 对象名称，如果是表就是表名，视图则视图名 */
	protected Integer needPage=0; /* 是否分页 */
	protected Integer pageSize; /* 分页大小 */
	public static final String DEFAULTTYPE_INPUT_PARAM = "1";
	public static final String DEFAULTTYPE_FIX_VALUE = "2";
	public static final String DEFAULTTYPE_SCRIPT = "3";
	protected String conditionfield; /* 条件字段的json */
	protected String resultfield; /* 返回字段json */
	protected String sortfield; /* 排序字段 */
	protected String dsalias; /* 数据源的别名 */
	protected Short isTable; /* 是否数据库表0:视图,1:数据库表 */
	/** 页面设置 */
	public static final Short SQLBUILDTYPE_PAGESETTING = 0;
	/** DIY SQL */
	public static final Short SQLBUILDTYPE_DIY = 1;
	protected Short sqlBuildType;/* sql语句生成方式：0-页面配置；1-diy自己写 */
	protected String diySql;/* 自定义sql */

	/**
	 * id
	 * 
	 * @return the id
	 * @since 1.0.0
	 */

	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * name
	 * 
	 * @return the name
	 * @since 1.0.0
	 */

	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * alias
	 * 
	 * @return the alias
	 * @since 1.0.0
	 */

	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias
	 *            the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * objName
	 * 
	 * @return the objName
	 * @since 1.0.0
	 */

	public String getObjName() {
		return objName;
	}

	/**
	 * @param objName
	 *            the objName to set
	 */
	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * needPage
	 * 
	 * @return the needPage
	 * @since 1.0.0
	 */

	public Integer getNeedPage() {
		return needPage;
	}

	/**
	 * @param needPage
	 *            the needPage to set
	 */
	public void setNeedPage(Integer needPage) {
		this.needPage = needPage;
	}

	/**
	 * pageSize
	 * 
	 * @return the pageSize
	 * @since 1.0.0
	 */

	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * conditionfield
	 * 
	 * @return the conditionfield
	 * @since 1.0.0
	 */

	public String getConditionfield() {
		return conditionfield;
	}

	/**
	 * @param conditionfield
	 *            the conditionfield to set
	 */
	public void setConditionfield(String conditionfield) {
		this.conditionfield = conditionfield;
	}

	/**
	 * resultfield
	 * 
	 * @return the resultfield
	 * @since 1.0.0
	 */

	public String getResultfield() {
		return resultfield;
	}

	/**
	 * @param resultfield
	 *            the resultfield to set
	 */
	public void setResultfield(String resultfield) {
		this.resultfield = resultfield;
	}

	/**
	 * sortfield
	 * 
	 * @return the sortfield
	 * @since 1.0.0
	 */

	public String getSortfield() {
		return sortfield;
	}

	/**
	 * @param sortfield
	 *            the sortfield to set
	 */
	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}

	/**
	 * dsalias
	 * 
	 * @return the dsalias
	 * @since 1.0.0
	 */

	public String getDsalias() {
		return dsalias;
	}

	/**
	 * @param dsalias
	 *            the dsalias to set
	 */
	public void setDsalias(String dsalias) {
		this.dsalias = dsalias;
	}

	/**
	 * isTable
	 * 
	 * @return the isTable
	 * @since 1.0.0
	 */

	public Short getIsTable() {
		return isTable;
	}

	/**
	 * @param isTable
	 *            the isTable to set
	 */
	public void setIsTable(Short isTable) {
		this.isTable = isTable;
	}

	/**
	 * sqlBuildType
	 * 
	 * @return the sqlBuildType
	 * @since 1.0.0
	 */

	public Short getSqlBuildType() {
		return sqlBuildType;
	}

	/**
	 * @param sqlBuildType
	 *            the sqlBuildType to set
	 */
	public void setSqlBuildType(Short sqlBuildType) {
		this.sqlBuildType = sqlBuildType;
	}

	/**
	 * diySql
	 * 
	 * @return the diySql
	 * @since 1.0.0
	 */

	public String getDiySql() {
		return diySql;
	}

	/**
	 * @param diySql
	 *            the diySql to set
	 */
	public void setDiySql(String diySql) {
		this.diySql = diySql;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BpmCustomQuery [id=" + id + ", name=" + name + ", alias=" + alias + ", objName=" + objName + ", needPage=" + needPage + ", pageSize=" + pageSize + ", conditionfield=" + conditionfield + ", resultfield=" + resultfield + ", sortfield=" + sortfield + ", dsalias=" + dsalias + ", isTable=" + isTable + ", sqlBuildType=" + sqlBuildType + ", diySql=" + diySql + "]";
	}

}