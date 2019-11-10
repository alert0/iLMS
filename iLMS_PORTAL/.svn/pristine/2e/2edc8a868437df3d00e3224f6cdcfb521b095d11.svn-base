package com.hotent.sys.persistence.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;

/**
 * 
 * <pre>
 * 描述：sys_query_view 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:26:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class QueryView extends AbstractModel<String> {

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * sql别名
	 */
	@XmlAttribute(name = "sqlAlias")
	protected String sqlAlias;

	/**
	 * 视图名称
	 */
	@XmlAttribute(name = "name")
	protected String name;

	/**
	 * 视图别名
	 */
	@XmlAttribute(name = "alias")
	protected String alias;

	/**
	 * 显示字段JSON数据
	 */
	@XmlElement(name = "shows")
	protected String shows;

	/**
	 * 查询条件定义
	 */
	@XmlElement(name = "conditions")
	protected String conditions;

	/**
	 * 过滤器类型
	 */
	@XmlAttribute(name = "filterType")
	protected Short filterType;

	/**
	 * 过滤器
	 */
	@XmlElement(name = "filter")
	protected String filter;

	/**
	 * 按纽定义
	 */
	@XmlElement(name = "buttons")
	protected String buttons;

	/**
	 * 是否初始化查询
	 */
	@XmlAttribute(name = "initQuery")
	protected Short initQuery;

	/**
	 * 模版定义
	 */
	@XmlElement(name = "template")
	protected String template;

	/**
	 * 是否支持分组
	 */
	@XmlAttribute(name = "supportGroup")
	protected Short supportGroup;

	/**
	 * 分组设定
	 */
	@XmlElement(name = "groupSetting")
	protected String groupSetting;

	/**
	 * 分页大小
	 */
	@XmlAttribute(name = "pageSize")
	protected Short pageSize;

	/**
	 * 显示行号
	 */
	@XmlAttribute(name = "showRowsNum")
	protected Short showRowsNum;

	/**
	 * 排序
	 */
	@XmlAttribute(name = "sn")
	protected Short sn;

	/**
	 * 是否分页
	 */
	@XmlAttribute(name = "needPage")
	protected Short needPage;

	/**
	 * 摸版别名
	 */
	@XmlAttribute(name = "templateAlias")
	protected String templateAlias;

	// 以下字段跟数据库无关
	private Short rebuildTemp;

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

	public void setSqlAlias(String sqlAlias) {
		this.sqlAlias = sqlAlias;
	}

	/**
	 * 返回 sql别名
	 * 
	 * @return
	 */
	public String getSqlAlias() {
		return this.sqlAlias;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 视图名称
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
	 * 返回 视图别名
	 * 
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	/**
	 * 返回 查询条件定义
	 * 
	 * @return
	 */
	public String getConditions() {
		return this.conditions;
	}

	public String getShows() {
		return shows;
	}

	public void setShows(String shows) {
		this.shows = shows;
	}

	public void setFilterType(Short filterType) {
		this.filterType = filterType;
	}

	/**
	 * 返回 过滤器类型
	 * 
	 * @return
	 */
	public Short getFilterType() {
		return this.filterType;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * 返回 过滤器
	 * 
	 * @return
	 */
	public String getFilter() {
		return this.filter;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	/**
	 * 返回 按纽定义
	 * 
	 * @return
	 */
	public String getButtons() {
		return this.buttons;
	}

	public void setInitQuery(Short initQuery) {
		this.initQuery = initQuery;
	}

	/**
	 * 返回 是否初始化查询
	 * 
	 * @return
	 */
	public Short getInitQuery() {
		return this.initQuery;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 返回 模版定义
	 * 
	 * @return
	 */
	public String getTemplate() {
		return this.template;
	}

	public void setSupportGroup(Short supportGroup) {
		this.supportGroup = supportGroup;
	}

	/**
	 * 返回 是否支持分组
	 * 
	 * @return
	 */
	public Short getSupportGroup() {
		return this.supportGroup;
	}

	public void setGroupSetting(String groupSetting) {
		this.groupSetting = groupSetting;
	}

	/**
	 * 返回 分组设定
	 * 
	 * @return
	 */
	public String getGroupSetting() {
		return this.groupSetting;
	}

	public void setPageSize(Short pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 返回 分页大小
	 * 
	 * @return
	 */
	public Short getPageSize() {
		return this.pageSize;
	}

	public void setShowRowsNum(Short showRowsNum) {
		this.showRowsNum = showRowsNum;
	}

	/**
	 * 返回 显示行号
	 * 
	 * @return
	 */
	public Short getShowRowsNum() {
		return this.showRowsNum;
	}

	public void setSn(Short sn) {
		this.sn = sn;
	}

	/**
	 * 返回 排序
	 * 
	 * @return
	 */
	public Short getSn() {
		return this.sn;
	}

	public void setNeedPage(Short needPage) {
		this.needPage = needPage;
	}

	/**
	 * 返回 是否分页
	 * 
	 * @return
	 */
	public Short getNeedPage() {
		return this.needPage;
	}

	public void setTemplateAlias(String templateAlias) {
		this.templateAlias = templateAlias;
	}

	/**
	 * 返回 摸版别名
	 * 
	 * @return
	 */
	public String getTemplateAlias() {
		return this.templateAlias;
	}
	
	
	public Short getRebuildTemp() {
		return rebuildTemp;
	}

	public void setRebuildTemp(Short rebuildTemp) {
		this.rebuildTemp = rebuildTemp;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("sqlAlias", this.sqlAlias).append("name", this.name).append("alias", this.alias).append("conditions", this.conditions).append("filterType", this.filterType).append("filter", this.filter).append("buttons", this.buttons).append("initQuery", this.initQuery).append("template", this.template).append("supportGroup", this.supportGroup).append("groupSetting", this.groupSetting).append("pageSize", this.pageSize).append("showRowsNum", this.showRowsNum).append("sn", this.sn).append("needPage", this.needPage).append("templateAlias", this.templateAlias).toString();
	}
}