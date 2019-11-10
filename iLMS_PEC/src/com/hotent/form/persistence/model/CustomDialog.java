package com.hotent.form.persistence.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 对象功能:bpm_custom_dialog entity对象 开发公司:广州宏天软件有限公司 开发人员:liyj_aschs 创建时间:2014-07-24 14:23:11
 */
public class CustomDialog extends AbstractModel<String> {
	private static final long serialVersionUID = 1L;
	protected String id; /* 主键 */
	protected String name; /* 名字 */
	protected String alias; /* 别名 */
	protected Short style; /* 显示样式：0-列表，1-树形 */
	protected String objName; /* 对象名称，如果是表就是表名，视图则视图名 */
	protected Boolean needPage; /* 是否分页 */
	protected Integer pageSize; /* 分页大小 */
	protected String displayfield; /* 显示字段 */
	protected String conditionfield; /* 条件字段的json */
	protected String resultfield; /* 返回字段json */
	protected String sortfield; /* 排序字段 */
	protected String dsalias; /* 数据源的别名 */
	protected Short isTable; /* 是否数据库表0:视图,1:数据库表 */
	protected String diySql; /* diy的sql语句 */
	protected Short sqlBuildType; /* sql生成的方式：0-页面设置；1-diy */
	protected Integer width; /* 弹出框的宽度 */
	protected Integer height; /* 弹出框的高度 */
	protected Integer selectNum; /* 选择的人数 -1:多选 */
	protected Boolean system; /* 是否系统内部的 */
	protected Short parentCheck;//树多选时父级联动 1-true,0-false
	protected Short childrenCheck;//树多选时子级联动 1-true,0-false
	
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 主键
	 * 
	 * @return
	 */
	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 名字
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * 返回 别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setStyle(Short style) {
		this.style = style;
	}

	/**
	 * 返回 显示样式：0-列表，1-树形
	 * 
	 * @return
	 */
	public Short getStyle() {
		return this.style;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	/**
	 * 返回 对象名称，如果是表就是表名，视图则视图名
	 * 
	 * @return
	 */
	public String getObjName() {
		return this.objName;
	}

	/**
	 * needPage
	 * @return  the needPage
	 * @since   1.0.0
	 */
	
	public Boolean getNeedPage() {
		return needPage;
	}

	/**
	 * @param needPage the needPage to set
	 */
	public void setNeedPage(Boolean needPage) {
		this.needPage = needPage;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回 分页大小
	 * 
	 * @return
	 */
	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setDisplayfield(String displayfield) {
		this.displayfield = displayfield;
	}

	/**
	 * 返回 显示字段
	 * 
	 * @return
	 */
	public String getDisplayfield() {
		return this.displayfield;
	}

	public void setConditionfield(String conditionfield) {
		this.conditionfield = conditionfield;
	}

	/**
	 * 返回 条件字段的json
	 * 
	 * @return
	 */
	public String getConditionfield() {
		return this.conditionfield;
	}

	public void setResultfield(String resultfield) {
		this.resultfield = resultfield;
	}

	/**
	 * 返回 返回字段json
	 * 
	 * @return
	 */
	public String getResultfield() {
		return this.resultfield;
	}

	public void setSortfield(String sortfield) {
		this.sortfield = sortfield;
	}

	/**
	 * 返回 排序字段
	 * 
	 * @return
	 */
	public String getSortfield() {
		return this.sortfield;
	}

	public void setDsalias(String dsalias) {
		this.dsalias = dsalias;
	}

	/**
	 * 返回 数据源的别名
	 * 
	 * @return
	 */
	public String getDsalias() {
		return this.dsalias;
	}

	public void setIsTable(Short isTable) {
		this.isTable = isTable;
	}

	/**
	 * 返回 是否数据库表0:视图,1:数据库表
	 * 
	 * @return
	 */
	public Short getIsTable() {
		return this.isTable;
	}

	public void setDiySql(String diySql) {
		this.diySql = diySql;
	}

	/**
	 * 返回 diy的sql语句
	 * 
	 * @return
	 */
	public String getDiySql() {
		return this.diySql;
	}

	public void setSqlBuildType(Short sqlBuildType) {
		this.sqlBuildType = sqlBuildType;
	}

	/**
	 * 返回 sql生成的方式：0-页面设置；1-diy
	 * 
	 * @return
	 */
	public Short getSqlBuildType() {
		return this.sqlBuildType;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * 返回 弹出框的宽度
	 * 
	 * @return
	 */
	public Integer getWidth() {
		return this.width;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	/**
	 * 返回 弹出框的高度
	 * 
	 * @return
	 */
	public Integer getHeight() {
		return this.height;
	}

	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}

	/**
	 * 返回 选择的人数 -1:多选
	 * 
	 * @return
	 */
	public Integer getSelectNum() {
		return this.selectNum;
	}

	/**
	 * system
	 * @return  the system
	 * @since   1.0.0
	 */
	
	public Boolean getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(Boolean system) {
		this.system = system;
	}
	
	public Short getParentCheck() {
		return parentCheck;
	}

	public void setParentCheck(Short parentCheck) {
		this.parentCheck = parentCheck;
	}

	public Short getChildrenCheck() {
		return childrenCheck;
	}

	public void setChildrenCheck(Short childrenCheck) {
		this.childrenCheck = childrenCheck;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("alias", this.alias).append("style", this.style).append("objName", this.objName).append("needPage", this.needPage).append("pageSize", this.pageSize).append("displayfield", this.displayfield).append("conditionfield", this.conditionfield).append("resultfield", this.resultfield).append("sortfield", this.sortfield).append("dsalias", this.dsalias).append("isTable", this.isTable).append("diySql", this.diySql).append("sqlBuildType", this.sqlBuildType).append("width", this.width).append("height", this.height).append("selectNum", this.selectNum).toString();
	}
}