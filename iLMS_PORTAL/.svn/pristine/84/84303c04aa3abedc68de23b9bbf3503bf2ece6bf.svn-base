package com.hotent.form.persistence.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * 对象功能:用户任务表单字段信息表 entity对象
 * 开发公司:广州宏天软件有限公司
 * 开发人员:何一帆
 * 创建时间:2014-06-18 11:57:34
 */
public class BpmFormField extends AbstractModel<String>{
	
	
	public static final String CTRL_TYPE_ONETEXT= "1";   //单行文本
	public static final String CTRL_TYPE_MANYTEXT= "2";  //多行文本
	public static final String CTRL_TYPE_ONESELECT= "3";  //下拉框
	public static final String CTRL_TYPE_MANYSELECT= "10";  //下拉框多选
	public static final String CTRL_TYPE_CHECKBOX= "4";  //复选框
	public static final String CTRL_TYPE_RADIO= "5";  //单选按钮
	public static final String CTRL_TYPE_DATE= "6";  //日期控件
	public static final String CTRL_TYPE_SELECTORS= "7";  //选择器
	public static final String CTRL_TYPE_OFFICE= "8";  //office控件
	public static final String CTRL_TYPE_FILEUPLOAD= "9";  //文件上传
	
	private static final long serialVersionUID = 1L;
	protected String id; /*主键*/
	protected String name; /*字段名称*/
	protected String desc; /*字段描述*/
	protected String type; /*字段的类型*/
	protected String groupId = "0";/*分组ID*/
	
	protected String formId; /*表单ID*/
	protected String boDefId;/*bo定义ID*/
	protected String entId;/*表ID*/
	
	protected String boAttrId; /*BO属性ID*/
	protected String calculation; /*运算表达式*/
	protected String ctrlType; /*控件类型*/
	protected String validRule; /*验证规则*/
	protected String option; /*选项*/
	protected Integer sn; /*排序*/
	
	/**
	 * 映射对应的实体名称。
	 */
	protected String entName="";
	
	

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
	public void setFormId(String formId) 
	{
		this.formId = formId;
	}
	/**
	 * 返回 表单ID
	 * @return
	 */
	public String getFormId() 
	{
		return this.formId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 字段名称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	/**
	 * 返回 字段描述
	 * @return
	 */
	public String getDesc() 
	{
		return this.desc;
	}


	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 数据的字段类型。
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public String getBoDefId() {
		return boDefId;
	}
	public void setBoDefId(String boDefId) {
		this.boDefId = boDefId;
	}
	public String getEntId() {
		return entId;
	}
	public void setEntId(String entId) {
		this.entId = entId;
	}
	public void setBoAttrId(String boAttrId) 
	{
		this.boAttrId = boAttrId;
	}
	/**
	 * 返回 BO属性ID
	 * @return
	 */
	public String getBoAttrId() 
	{
		return this.boAttrId;
	}
	public void setCalculation(String calculation) 
	{
		this.calculation = calculation;
	}
	/**
	 * 返回 运算表达式
	 * @return
	 */
	public String getCalculation() 
	{
		return this.calculation;
	}
	public void setCtrlType(String ctrlType) 
	{
		this.ctrlType = ctrlType;
	}
	/**
	 * 返回 控件类型
	 * @return
	 */
	public String getCtrlType() 
	{
		return this.ctrlType;
	}
	public void setValidRule(String validRule) 
	{
		this.validRule = validRule;
	}
	/**
	 * 返回 验证规则
	 * @return
	 */
	public String getValidRule() 
	{
		if(StringUtil.isEmpty(validRule)){
			return "{}";
		}
		return this.validRule;
	}
	public void setOption(String option) 
	{
		this.option = option;
	}
	/**
	 * 返回 选项
	 * @return
	 */
	public String getOption() 
	{
		return this.option;
	}
	public void setSn(Integer sn) 
	{
		this.sn = sn;
	}
	/**
	 * 返回 排序
	 * @return
	 */
	public Integer getSn() 
	{
		return this.sn;
	}
	

	public String getEntName() {
		return entName;
	}
	public void setEntName(String entName) {
		this.entName = entName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("formId", this.formId) 
		.append("name", this.name) 
		.append("desc", this.desc) 
		.append("type", this.type) 
		.append("boAttrId", this.boAttrId) 
		.append("calculation", this.calculation) 
		.append("ctrlType", this.ctrlType) 
		.append("validRule", this.validRule) 
		.append("option", this.option) 
		.append("sn", this.sn) 
		.toString();
	}
}