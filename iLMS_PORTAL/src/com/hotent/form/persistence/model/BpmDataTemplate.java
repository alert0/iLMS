package com.hotent.form.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;


 /**
 * 
 * <pre> 
 * 描述：业务数据模板 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class BpmDataTemplate extends AbstractModel<String>{
	
	private static final long serialVersionUID = 1L;

	/** 参数标识(当前路径)*/
	public static final  String PARAMS_KEY_CTX=  "__ctx";

	public static final String PARAMS_KEY_ALIAS = "alias";

	/** 新增*/
	public static final  String MANAGE_TYPE_ADD="add";
	/** 编辑 */
	public static final  String MANAGE_TYPE_EDIT="edit";
	/** 删除*/
	public static final  String MANAGE_TYPE_DEL="del";
	/** 明细 */
	public static final  String MANAGE_TYPE_DETAIL="detail";
	/** 导出 */
	public static final  String MANAGE_TYPE_EXPORT="export";
	
	/** 启动流程 */
	public static final String MANAGE_TYPE_START_FLOW = "startFlow";

	/** boAlias */
	public static final String PARAMS_KEY_BOALIAS = "boAlias";
	
	/** boAlias */
	public static final String PARAMS_KEY_FORM_KEY = "formKey";

	/** 流程定义id */
	public static final String PARAMS_KEY_DEF_ID = "defId";
	
	/** filterKey */
	public static final String PARAMS_KEY_FILTER_KEY = "filterKey";


	/**
	* 主键
	*/
	protected String id; 
	
	/**
	* 业务对象定义id
	*/
	protected String boDefId; 
	
	/**
	 * 业务对象定义别名
	 */
	protected String boDefAlias;
	
	/**
	* 自定义表单key
	*/
	protected String formKey; 
	
	/**
	* 名称
	*/
	protected String name; 
	
	/**
	* 别名
	*/
	protected String alias; 
	
	/**
	* 样式
	*/
	protected Short style; 
	
	/**
	* 是否需要分页
	*/
	protected Short needPage; 
	
	/**
	* 分页大小
	*/
	protected Short pageSize; 
	
	/**
	* 数据模板别名
	*/
	protected String templateAlias; 
	
	/**
	* 数据模板代码
	*/
	protected String templateHtml; 
	
	/**
	* 显示字段
	*/
	protected String displayField; 
	
	/**
	* 排序字段
	*/
	protected String sortField; 
	
	/**
	* 条件字段
	*/
	protected String conditionField; 
	
	/**
	* 管理字段
	*/
	protected String manageField; 
	
	/**
	* 过滤条件
	*/
	protected String filterField; 
	
	/**
	* 变量字段
	*/
	protected String varField; 
	
	/**
	* 过滤类型（1.建立条件,2.脚本条件）
	*/
	protected Short filterType; 
	
	/**
	* 数据来源
	*/
	protected Short source; 
	
	/**
	* 流程定义ID
	*/
	protected String defId; 
	
	/**
	* 是否查询
	*/
	protected Short isQuery; 
	
	/**
	* 是否过滤
	*/
	protected Short isFilter; 
	
	/**
	* 导出字段
	*/
	protected String exportField; 
	
	/**
	* 打印字段
	*/
	protected String printField; 
	
	/**
	 * 绑定的流程名称
	 */
	protected String subject; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 主键
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setBoDefId(String boDefId) {
		this.boDefId = boDefId;
	}
	
	/**
	 * 返回 自定义表ID
	 * @return
	 */
	public String getBoDefId() {
		return this.boDefId;
	}
	
	public String getBoDefAlias() {
		return boDefAlias;
	}

	public void setBoDefAlias(String boDefAlias) {
		this.boDefAlias = boDefAlias;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	
	/**
	 * 返回 自定义表单key
	 * @return
	 */
	public String getFormKey() {
		return this.formKey;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名称
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
	 * @return
	 */
	public String getAlias() {
		return this.alias;
	}
	
	public void setStyle(Short style) {
		this.style = style;
	}
	
	/**
	 * 返回 样式
	 * @return
	 */
	public Short getStyle() {
		return this.style;
	}
	
	public void setNeedPage(Short needPage) {
		this.needPage = needPage;
	}
	
	/**
	 * 返回 是否需要分页
	 * @return
	 */
	public Short getNeedPage() {
		return this.needPage;
	}
	
	public void setPageSize(Short pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 返回 分页大小
	 * @return
	 */
	public Short getPageSize() {
		return this.pageSize;
	}
	
	public void setTemplateAlias(String templateAlias) {
		this.templateAlias = templateAlias;
	}
	
	/**
	 * 返回 数据模板别名
	 * @return
	 */
	public String getTemplateAlias() {
		return this.templateAlias;
	}
	
	public void setTemplateHtml(String templateHtml) {
		this.templateHtml = templateHtml;
	}
	
	/**
	 * 返回 数据模板代码
	 * @return
	 */
	public String getTemplateHtml() {
		return this.templateHtml;
	}
	
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}
	
	/**
	 * 返回 显示字段
	 * @return
	 */
	public String getDisplayField() {
		return this.displayField;
	}
	
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	/**
	 * 返回 排序字段
	 * @return
	 */
	public String getSortField() {
		return this.sortField;
	}
	
	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}
	
	/**
	 * 返回 条件字段
	 * @return
	 */
	public String getConditionField() {
		return this.conditionField;
	}
	
	public void setManageField(String manageField) {
		this.manageField = manageField;
	}
	
	/**
	 * 返回 管理字段
	 * @return
	 */
	public String getManageField() {
		return this.manageField;
	}
	
	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}
	
	/**
	 * 返回 过滤条件
	 * @return
	 */
	public String getFilterField() {
		return this.filterField;
	}
	
	public void setVarField(String varField) {
		this.varField = varField;
	}
	
	/**
	 * 返回 变量字段
	 * @return
	 */
	public String getVarField() {
		return this.varField;
	}
	
	public void setFilterType(Short filterType) {
		this.filterType = filterType;
	}
	
	/**
	 * 返回 过滤类型（1.建立条件,2.脚本条件）
	 * @return
	 */
	public Short getFilterType() {
		return this.filterType;
	}
	
	public void setSource(Short source) {
		this.source = source;
	}
	
	/**
	 * 返回 数据来源
	 * @return
	 */
	public Short getSource() {
		return this.source;
	}
	
	public void setDefId(String defId) {
		this.defId = defId;
	}
	
	/**
	 * 返回 流程定义ID
	 * @return
	 */
	public String getDefId() {
		return this.defId;
	}
	
	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}
	
	/**
	 * 返回 是否查询
	 * @return
	 */
	public Short getIsQuery() {
		return this.isQuery;
	}
	
	public void setIsFilter(Short isFilter) {
		this.isFilter = isFilter;
	}
	
	/**
	 * 返回 是否过滤
	 * @return
	 */
	public Short getIsFilter() {
		return this.isFilter;
	}
	
	public void setExportField(String exportField) {
		this.exportField = exportField;
	}
	
	/**
	 * 返回 导出字段
	 * @return
	 */
	public String getExportField() {
		return this.exportField;
	}
	
	public void setPrintField(String printField) {
		this.printField = printField;
	}
	
	/**
	 * 返回 打印字段
	 * @return
	 */
	public String getPrintField() {
		return this.printField;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("boDefId", this.boDefId) 
		.append("formKey", this.formKey) 
		.append("name", this.name) 
		.append("alias", this.alias) 
		.append("style", this.style) 
		.append("needPage", this.needPage) 
		.append("pageSize", this.pageSize) 
		.append("templateAlias", this.templateAlias) 
		.append("templateHtml", this.templateHtml) 
		.append("displayField", this.displayField) 
		.append("sortField", this.sortField) 
		.append("conditionField", this.conditionField) 
		.append("manageField", this.manageField) 
		.append("filterField", this.filterField) 
		.append("varField", this.varField) 
		.append("filterType", this.filterType) 
		.append("source", this.source) 
		.append("defId", this.defId) 
		.append("isQuery", this.isQuery) 
		.append("isFilter", this.isFilter) 
		.append("exportField", this.exportField) 
		.append("printField", this.printField) 
		.toString();
	}

}