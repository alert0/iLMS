package com.hotent.sys.persistence.model;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.AppUtil;
 
import com.hotent.sys.util.ContextUtil;

 
 /**
 * 
 * <pre> 
 * 描述：sys_grid_config 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-05-04 09:09:04
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class SysDataGridField extends AbstractModel<String>{
	protected String id; /*id_*/
	protected String gridid; /*表id*/
	protected String gridName; /*grid视图名*/
	protected String physicsTable; /*physics_table*/
	protected String field; /*字段*/
	protected String dbfield;
	protected String title; /*字段标题*/
	protected Boolean hidden=false; /*是否隐藏*/
	protected Integer orderNum=100; /*排序*/
	protected Integer width=120; /*宽度*/
	protected String align="center"; /*对齐*/
	protected Boolean sortable=false; /*是否可排序*/
	protected Boolean resizable=true; /*是否可拖动列宽*/
	protected String formatter; /*格式化*/
	protected String styler; /*样式格式化*/
	protected String editor; /*编辑器*/
	protected String editorType; /*编辑器类别*/
	protected Integer rowspan=1; /*跨行*/
	protected Integer colspan=1; /*跨列*/
	protected Boolean search=false; /*是否搜索*/
	protected String searchParamter; /*搜索框参数*/
	protected String searchCombobox; /*search_combobox*/
	protected String datatype; /*数据类型*/
	protected Boolean required=false; /*required*/
	protected Boolean frozen=false; /*是否为冻结列*/
	protected Boolean edit=false; /*是否可编辑*/
	protected Integer mergeIndexed=1; /*merge_indexed_*/
	protected Boolean ispk=false; /*是否为主键列*/
	protected Boolean isunique=false; /*是否为唯一值（多列变成组合）*/
	protected Boolean isphysicsField=true; /*是否为物理表字段*/
	protected Boolean isHiddenInput=false; /*是否为隐藏控件字段*/
	protected String creator="默认"; /*creator_*/
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	protected java.util.Date createdTime=new Date(); /*created_time_*/
	
 
	public void setIsHiddenInput(Boolean isHiddenInput) 
	{
		this.isHiddenInput = isHiddenInput;
	}
	public Boolean getIsHiddenInput()
	{
		return this.isHiddenInput;
	}
	public void setDbfield(String dbfield) 
	{
		this.dbfield = dbfield;
	}
	public  String getDbfield()
	{
		return this.dbfield;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 id_
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
 
	public void setGridId(String gridid) {
		this.gridid = gridid.toString();
	}
 
	public String getGridId() {
		return gridid.toString();
	}	
	public void setGridName(String gridName) 
	{
		this.gridName = gridName;
	}
	/**
	 * 返回 grid视图名
	 * @return
	 */
	public String getGridName() 
	{
		return this.gridName;
	}
	public void setPhysicsTable(String physicsTable) 
	{
		this.physicsTable = physicsTable;
	}
	/**
	 * 返回 physics_table
	 * @return
	 */
	public String getPhysicsTable() 
	{
		return this.physicsTable;
	}
	public void setField(String field) 
	{
		this.field = field;
	}
	/**
	 * 返回 字段
	 * @return
	 */
	public String getField() 
	{
		return this.field;
	}
	public void setTitle(String title) 
	{
		this.title = title;
	}
	/**
	 * 返回 字段标题
	 * @return
	 */
	public String getTitle() 
	{
		return this.title;
	}
	public void setHidden(Boolean hidden) 
	{
		this.hidden = hidden;
	}
	/**
	 * 返回 是否隐藏
	 * @return
	 */
	public Boolean getHidden() 
	{
		return this.hidden;
	}
	public void setOrderNum(Integer orderNum) 
	{
		this.orderNum = orderNum;
	}
	/**
	 * 返回 排序
	 * @return
	 */
	public Integer getOrderNum() 
	{
		return this.orderNum;
	}
	public void setWidth(Integer width) 
	{
		this.width = width;
	}
	/**
	 * 返回 宽度
	 * @return
	 */
	public Integer getWidth() 
	{
		return this.width;
	}
	public void setAlign(String align) 
	{
		this.align = align;
	}
	/**
	 * 返回 对齐
	 * @return
	 */
	public String getAlign() 
	{
		return this.align;
	}
	public void setSortable(Boolean sortable) 
	{
		this.sortable = sortable;
	}
	/**
	 * 返回 是否可排序
	 * @return
	 */
	public Boolean getSortable() 
	{
		return this.sortable;
	}
	public void setResizable(Boolean resizable) 
	{
		this.resizable = resizable;
	}
	/**
	 * 返回 是否可拖动列宽
	 * @return
	 */
	public Boolean getResizable() 
	{
		return this.resizable;
	}
	public void setFormatter(String formatter) 
	{
		this.formatter = formatter;
	}
	/**
	 * 返回 格式化
	 * @return
	 */
	public String getFormatter() 
	{
		return this.formatter;
	}
	public void setStyler(String styler) 
	{
		this.styler = styler;
	}
	/**
	 * 返回 样式格式化
	 * @return
	 */
	public String getStyler() 
	{
		return this.styler;
	}
	public void setEditor(String editor) 
	{
		this.editor = editor;
	}
	/**
	 * 返回 编辑器
	 * @return
	 */
	public String getEditor() 
	{
		return this.editor;
	}
	public void setEditorType(String editorType) 
	{
		this.editorType = editorType;
	}
	/**
	 * 返回 编辑器
	 * @return
	 */
	public String getEditorType() 
	{
		return this.editorType;
	}
	public void setRowspan(Integer rowspan) 
	{
		this.rowspan = rowspan;
	}
	/**
	 * 返回 跨行
	 * @return
	 */
	public Integer getRowspan() 
	{
		return this.rowspan;
	}
	public void setColspan(Integer colspan) 
	{
		this.colspan = colspan;
	}
	/**
	 * 返回 跨列
	 * @return
	 */
	public Integer getColspan() 
	{
		return this.colspan;
	}
	public void setSearch(Boolean search) 
	{
		this.search = search;
	}
	/**
	 * 返回 是否搜索
	 * @return
	 */
	public Boolean getSearch() 
	{
		return this.search;
	}
	public void setSearchParamter(String searchParamter) 
	{
		this.searchParamter = searchParamter;
	}
	/**
	 * 返回 搜索框参数
	 * @return
	 */
	public String getSearchParamter() 
	{
		return this.searchParamter;
	}
	public void setSearchCombobox(String searchCombobox) 
	{
		this.searchCombobox = searchCombobox;
	}
	/**
	 * 返回 search_combobox
	 * @return
	 */
	public String getSearchCombobox() 
	{
		return this.searchCombobox;
	}
	public void setDatatype(String datatype) 
	{
		this.datatype = datatype;
	}
	/**
	 * 返回 数据类型
	 * @return
	 */
	public String getDatatype() 
	{
		return this.datatype;
	}
	public void setRequired(Boolean required) 
	{
		this.required = required;
	}
	/**
	 * 返回 required
	 * @return
	 */
	public Boolean getRequired() 
	{
		return this.required;
	}
	public void setFrozen(Boolean frozen) 
	{
		this.frozen = frozen;
	}
	/**
	 * 返回 是否为冻结列
	 * @return
	 */
	public Boolean getFrozen() 
	{
		return this.frozen;
	}
	public void setEdit(Boolean edit) 
	{
		this.edit = edit;
	}
	/**
	 * 返回 是否可编辑
	 * @return
	 */
	public Boolean getEdit() 
	{
		return this.edit;
	}
	public void setMergeIndexed(Integer mergeIndexed) 
	{
		this.mergeIndexed = mergeIndexed;
	}
	/**
	 * 返回 merge_indexed_
	 * @return
	 */
	public Integer getMergeIndexed() 
	{
		return this.mergeIndexed;
	}
	public void setIspk(Boolean ispk) 
	{
		this.ispk = ispk;
	}
	/**
	 * 返回 是否为主键列
	 * @return
	 */
	public Boolean getIspk() 
	{
		return this.ispk;
	}
	public void setIsunique(Boolean isunique) 
	{
		this.isunique = isunique;
	}
	/**
	 * 返回 是否为唯一值（多列变成组合）
	 * @return
	 */
	public Boolean getIsunique() 
	{
		return this.isunique;
	}
	public void setIsphysicsField(Boolean isphysicsField) 
	{
		this.isphysicsField = isphysicsField;
	}
	/**
	 * 返回 是否为物理表字段
	 * @return
	 */
	public Boolean getIsphysicsField() 
	{
		return this.isphysicsField;
	}
	public void setCreator(String creator) 
	{
		this.creator = creator;
	}
	/**
	 * 返回 creator_
	 * @return
	 */
	public String getCreator() 
	{
		return this.creator;
	}
	public void setCreatedTime(java.util.Date createdTime) 
	{
		this.createdTime = createdTime;
	}
	/**
	 * 返回 created_time_
	 * @return
	 */
	public java.util.Date getCreatedTime() 
	{
		return this.createdTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("gridName", this.gridName) 
		.append("physicsTable", this.physicsTable) 
		.append("field", this.field) 
		.append("title", this.title) 
		.append("hidden", this.hidden) 
		.append("orderNum", this.orderNum) 
		.append("width", this.width) 
		.append("align", this.align) 
		.append("sortable", this.sortable) 
		.append("resizable", this.resizable) 
		.append("formatter", this.formatter) 
		.append("styler", this.styler) 
		.append("editor", this.editor) 
		.append("rowspan", this.rowspan) 
		.append("colspan", this.colspan) 
		.append("search", this.search) 
		.append("searchParamter", this.searchParamter) 
		.append("searchCombobox", this.searchCombobox) 
		.append("datatype", this.datatype) 
		.append("required", this.required) 
		.append("frozen", this.frozen) 
		.append("edit", this.edit) 
		.append("mergeIndexed", this.mergeIndexed) 
		.append("ispk", this.ispk) 
		.append("isunique", this.isunique) 
		.append("isphysicsField", this.isphysicsField) 
		.append("creator", this.creator) 
		.append("createdTime", this.createdTime) 
		.toString();
	}
}