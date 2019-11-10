package com.hotent.form.persistence.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * 对象功能:表单元数据
 * 开发公司:广州宏天软件有限公司
 * 开发人员:苗继方
 * 创建时间:2016-03-17
 */
public class BpmFormDef extends AbstractModel<String>{

	private static final long serialVersionUID = 1L;
	protected String id; /*表单定义ID*/
	@XmlAttribute(name = "key")
	protected String key; /*key*/
	
	@XmlAttribute(name = "name")
	protected String name; /*表单名字*/
	
	@XmlAttribute(name = "type")
	protected String type;/*类型*/
	
	@XmlAttribute(name = "typeId")
	protected String typeId;/*类型Id*/
	
	@XmlAttribute(name = "desc")
	protected String desc; /*表单名字*/
	
	@XmlTransient
	protected List<BpmFormField> bpmFormFieldList = new ArrayList<BpmFormField>(); /*用户任务表单字段信息表列表*/
	
	@XmlElement(name = "expand")
	protected String expand;
	
	
	/**意见配置
	 * [{"name": "jzyj","desc": "局长意见"},{"name": "cwyj","desc": "财务意见" }]
	 */
	@XmlElement(name = "opinionConf")
	protected String opinionConf;
	
	@XmlElement(name = "ganged")/*表单联动数组*/
	protected String ganged;
	
	
	public String getGanged() {
		return ganged;
	}

	public void setGanged(String ganged) {
		this.ganged = ganged;
	}

	public String getExpand() {
		return expand;
	}

	public void setExpand(String expand) {
		this.expand = expand;
	}


	public BpmFormDef(){
		
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeId() {
		return typeId;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public List<BpmFormField> getBpmFormFieldList() {
		return bpmFormFieldList;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setBpmFormFieldList(List<BpmFormField> bpmFormFieldList) {
		this.bpmFormFieldList = bpmFormFieldList;
	}


	public String getId() {
		return id;
	}

	public String getOpinionConf() {
		return opinionConf;
	}

	public void setOpinionConf(String opinionConf) {
		this.opinionConf = opinionConf;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	@XmlTransient
	public JSONArray getFieldList() {
		JSONObject json = JSONObject.parseObject(this.expand);
		return json.getJSONArray("fields");
	}
	
}