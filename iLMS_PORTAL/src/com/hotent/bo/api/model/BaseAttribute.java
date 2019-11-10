package com.hotent.bo.api.model;



import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringPool;
import com.hotent.base.core.util.string.StringUtil;

/**
 * BO定义属性对象接口API 开发公司:广州宏天软件有限公司 开发人员:何一帆 创建时间:2014-01-21 10:38:10
 */
public class BaseAttribute extends AbstractModel<String> implements Column {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8651746735202676879L;

	public final static class BOATTRIBUTE_IS_REQUIRED {
		/**
		 * 必填
		 */
		public final static int REQUIRED_YES = 1;
		/**
		 * 非必填
		 */
		public final static int REQUIRED_NO = 0;

	}

	public BaseAttribute() {

	}

	public BaseAttribute(String name, String desc, String dataType) {
		this.name = name;
		this.desc = desc;
		this.dataType = dataType;
	}

	@XmlTransient
	protected String id = "";

	/* 属性名称 */
	@XmlAttribute(name = "name")
	protected String name;
	/* 属性描述 */
	@XmlAttribute(name = "description")
	protected String desc;
	/*
	 * 数据类型。string=字符串；number=数值；date=日期（长日期，通过显示格式来限制）；
	 */
	@XmlAttribute(name = "dataType")
	protected String dataType;
	
	/**
	 * 列实际类型
	 */
	protected String fcolumnType;
	/*
	 * 基本默认值
	 */
	@XmlAttribute(name = "defaultValue")
	protected String defaultValue="";
	/*
	 * 基本类型显示格式
	 */
	@XmlAttribute(name = "format")
	protected String format="";
	/*
	 * 是否必填
	 */
	@XmlAttribute(name = "isRequired")
	protected int isRequired = BOATTRIBUTE_IS_REQUIRED.REQUIRED_NO;
	/*
	 * 属性长度
	 */
	@XmlAttribute(name = "attrLength")
	protected int attrLength = 0;
	/*
	 * 浮点长度
	 */
	@XmlAttribute(name = "decimalLen")
	protected int decimalLen = 0;

	private boolean isPk = false;

	private boolean isNull = false;

	private String tableName = "";
	
	/*
	 * 排序
	 */
	@XmlAttribute(name = "sn")
	protected int sn = 0;

	/**
	 * 数据库字段名称，外部表专用
	 */
	@XmlAttribute(name = "fieldName")
	protected String fieldName="";
	/**
	 * bo实体对象。
	 */
	
	private BaseBoEnt boEnt = null;

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 属性名称
	 * 
	 * @return
	 */
	@Override
	public String getFieldName() {
		if(this.boEnt.isExternal()){
			return this.fieldName;
		}
		if(this.name.equalsIgnoreCase(BaseBoEnt.FK_NAME) || this.name.equalsIgnoreCase(BaseBoEnt.PK_NAME) ){
			return this.name;
		}
		return BaseBoEnt.FIELD_PREFIX + this.name;
		
	}
	
	@Override
	public void setFieldName(String tmp) {
		this.fieldName = tmp;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 返回 属性描述
	 * 
	 * @return
	 */
	public String getDesc() {
		return this.desc;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 返回 数据类型。varchar=字符串；number=数值；date=日期（长日期，通过显示格式来限制）；
	 * 
	 * @return
	 */
	public String getDataType() {
		return this.dataType;
	}
	
	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 返回 基本默认值
	 * 
	 * @return
	 */
	@Override
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 返回 基本类型显示格式
	 * 
	 * @return
	 */
	public String getFormat() {
		if(StringUtil.isEmpty(format) && "date".equals(this.dataType)){
			return StringPool.DATE_FORMAT_DATE;
		}
		return this.format;
	}
	
	@Override
	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * 返回 是否必填
	 * 
	 * @return
	 */
	@Override
	public int getIsRequired() {
		return this.isRequired;
	}

	/**
	 * 返回属性长度
	 * 
	 * @return
	 */
	public int getAttrLength() {
		return attrLength;
	}

	public void setAttrLength(int attrLength) {
		this.attrLength = attrLength;
	}

	/**
	 * 返回 浮点长度
	 * 
	 * @return
	 */
	@Override
	public int getDecimalLen() {
		return decimalLen;
	}
	
	@Override
	public void setDecimalLen(int decimalLen) {
		this.decimalLen = decimalLen;
	}

	@Override
	public String getComment() {
		return this.desc;
	}

	@Override
	public boolean getIsPk() {
		return this.isPk;
	}

	@Override
	public boolean getIsNull() {
		return this.isNull;
	}

	@Override
	public String getColumnType() {

		return this.dataType;
	}

	@Override
	public int getCharLen() {

		return attrLength;
	}

	@Override
	public int getIntLen() {

		return attrLength;
	}

	@Override
	public void setColumnType(String columnType) {
		this.dataType = columnType;

	}

	@Override
	public void setComment(String comment) {
		this.desc = comment;

	}

	@Override
	public void setIsNull(boolean isNull) {
		this.isNull = isNull;

	}

	@Override
	public void setIsPk(boolean isPk) {
		this.isPk = isPk;

	}

	@Override
	public void setCharLen(int charLen) {
		this.attrLength = charLen;

	}

	@Override
	public void setIntLen(int intLen) {
		this.attrLength = intLen;

	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@JSONField(serialize=false)
	public BaseBoEnt getBoEnt() {
		return boEnt;
	}

	public void setBoEnt(BaseBoEnt boEnt) {
		this.boEnt = boEnt;
	}
	
	@Override
	public String toString() {
		return "BaseAttribute [name=" + name + ", desc=" + desc + "]";
	}

	@Override
	public String getFcolumnType() {
		return this.fcolumnType;
	}

	@Override
	public void setFcolumnType(String fcolumnType) {
		this.fcolumnType = fcolumnType;
	}
	

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

}