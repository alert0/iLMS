package com.hotent.bo.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.alibaba.fastjson.annotation.JSONField;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.db.model.Table;
import com.hotent.base.core.model.AbstractModel;
import com.hotent.base.core.util.string.StringUtil;

/**
 * bo 实体类定义。
 * 
 * @author ray
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ent")
public class BaseBoEnt extends AbstractModel<String> implements Table {

	/**
	 * 主键字段名称
	 */
	public static String PK_NAME = "ID_";

	/**
	 * 外键字段名称
	 */
	public static String FK_NAME = "REF_ID_";

	/**
	 * 表前缀。
	 */
	public static String TABLE_PREFIX = "W_";

	/**
	 * 列前缀。
	 */
	public static String FIELD_PREFIX = "F_";

	/**
	 * 
	 */
	private static final long serialVersionUID = -6250373131806511988L;

	/**
	 * 主键。
	 */
	@XmlTransient
	private String id = "";

	/**
	 * 实体名称。 唯一
	 */
	@XmlAttribute(name = "name")
	private String name = "";

	/**
	 * 描述。
	 */
	@XmlAttribute(name = "description")
	private String desc = "";
	
	/**
	 * 包ID
	 */
	@XmlAttribute(name = "packageId")
	protected String packageId;
	
	


	/**
	 * 实体关系，这个在构建BODEF时使用。
	 */
	@XmlAttribute(name = "type")
	protected String type = BaseBoDef.BOENT_RELATION.MAIN;

	/**
	 * 数据源名称
	 */
	protected String dsName;
	/**
	 * 表名
	 */
	@XmlAttribute(name = "tableName")
	protected String tableName;
	
	protected String tableNameNoPrefix;
	
	/**
	 * 是否外部表
	 */
	@XmlAttribute(name = "isExternal")
	protected Short isExternal=0;
	/**
	 * pk_
	 * 
	 */
	@XmlAttribute(name = "pk")
	protected String pk;
	/**
	 * fk_
	 */
	@XmlAttribute(name = "fk")
	protected String fk;
	/**
	 * 主键类型，主要用于外部表的时候。
	 */
	@XmlAttribute(name = "pkType")
	protected String pkType;

	/**
	 * 属性描述。
	 */
	@XmlElement(name = "attr")
	private List<BaseAttribute> boAttrList = new ArrayList<BaseAttribute>();

	/**
	 * 子实体列表。
	 */
	@XmlElementWrapper(name = "ents")
	@XmlElement(name = "ent", type = BaseBoEnt.class)
	private List<BaseBoEnt> childEntList = new ArrayList<BaseBoEnt>();

	/**
	 * 列列表。
	 */
	@XmlTransient
	private List<Column> columnList = new ArrayList<Column>();

	/**
	 * 子实体map。
	 */
	@XmlTransient
	private Map<String, BaseBoEnt> childMapList = new HashMap<String, BaseBoEnt>();

	/**
	 * 对象实体map。
	 */
	@XmlTransient
	@JSONField(serialize=false)
	private Map<String, BaseAttribute> attributeMap = new HashMap<String, BaseAttribute>();
	
	/**
	 * db属性节点。
	 */
	@XmlTransient
	private Map<String, BaseAttribute> attrFieldMap = new HashMap<String, BaseAttribute>();
	
	

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * 名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String description) {
		this.desc = description;
	}
	
	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	/**
	 * 获取当前实体对对于父实体的关系。
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置实体关系。
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	@JSONField(serialize=true,name="attrs")
	public List<BaseAttribute> getBoAttrList() {
		return boAttrList;
	}

	public void setBoAttrList(List<BaseAttribute> boAttrList) {

		this.boAttrList = boAttrList;
		for (BaseAttribute attribute : boAttrList) {
			// 设置boent。
			attribute.setBoEnt(this);
			this.attributeMap.put(attribute.name.toLowerCase(), attribute);
			
			this.attrFieldMap.put(attribute.getFieldName().toLowerCase(), attribute);
			// 添加列。
			this.columnList.add(attribute);
			
		}
	}
	
	/**
	 * 获取初始数据。
	 * @return
	 */
	@JSONField(serialize=false)
	public Map<String,Object> getInitData(){
		Map<String,Object> map=new HashMap<String, Object>();
		for (BaseAttribute attr : boAttrList) {
			String val=StringUtil.isEmpty(attr.getDefaultValue())?"":attr.getDefaultValue();
			map.put(attr.getName(), val);
		}
		return map;
	}

	@JSONField(serialize=true,name="childEnts")
	public List<BaseBoEnt> getChildEntList() {
		return childEntList;
	}

	public void setChildEntList(List<BaseBoEnt> childEntList) {
		this.childEntList = childEntList;
		for (BaseBoEnt ent : childEntList) {
			childMapList.put(ent.getName().toLowerCase(), ent);
		}
	}

	/**
	 * 添加实体类。
	 * 
	 * @param ent
	 */
	public void addEnt(BaseBoEnt ent) {
		this.childEntList.add(ent);
		childMapList.put(ent.getName().toLowerCase(), ent);
	}

	@JSONField(serialize=false)
	public Map<String, BaseBoEnt> getChildMap() {
		return childMapList;
	}

	/**
	 * 根据键获取BaseAttribute。
	 * 
	 * @param key
	 * @return
	 */
	public BaseAttribute getAttribute(String key) {
		return this.attributeMap.get(key.toLowerCase());
	}
	
	/**
	 * 根据字段名获取属性。
	 * @param fieldName
	 * @return
	 */
	public BaseAttribute getAttrByField(String fieldName){
		//主键
		if(fieldName.equalsIgnoreCase(this.getPkKey())){
			BaseAttribute attribute=new BaseAttribute(this.getPkKey(), "", "");
			attribute.setBoEnt(this);
			attribute.setFieldName(fieldName);
			return attribute;
		}
		//外键
		if(fieldName.equalsIgnoreCase(this.getFk())){
			BaseAttribute attribute=new BaseAttribute(this.getFk(), "", "");
			attribute.setBoEnt(this);
			attribute.setFieldName(fieldName);
			return attribute;
		}
		return this.attrFieldMap.get(fieldName.toLowerCase());
	}

	/**
	 * 添加属性。
	 * 
	 * @param attribute
	 */
	public void addAttr(BaseAttribute attribute) {
		attribute.setBoEnt(this);
		this.boAttrList.add(attribute);
		this.attributeMap.put(attribute.name.toLowerCase(), attribute);
		this.attrFieldMap.put(attribute.getFieldName().toLowerCase(), attribute);
	}

	public void addAttrFirst(BaseAttribute attribute) {
		attribute.setBoEnt(this);
		this.boAttrList.add(0, attribute);
		this.attributeMap.put(attribute.name.toLowerCase(), attribute);
		
		this.attrFieldMap.put(attribute.getFieldName().toLowerCase(), attribute);
		
	}

	@Override
	public String getComment() {
		return this.desc;
	}

	@Override
	@JSONField(serialize=false)
	public List getColumnList() {
		return this.boAttrList;
	}

	@Override
	@JSONField(serialize=false)
	public List<Column> getPrimayKey() {
		List<Column> list = new ArrayList<Column>();
		for (Column col : columnList) {
			if (col.getIsPk()) {
				list.add(col);
			}
		}
		return list;
	}

	@Override
	public void setComment(String comment) {
		this.desc = comment;
	}

	@Override
	public void setColumnList(List columns) {
		this.boAttrList = columns;
	}

	@Override
	public void addColumn(Column column) {
		this.boAttrList.add((BaseAttribute) column);
	}

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	/**
	 * 返回 数据源名称
	 * 
	 * @return
	 */
	public String getDsName() {
		return this.dsName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回 表名
	 * 
	 * @return
	 */
	public String getTableName() {
		if(isExternal()){
			return this.tableName;
		}
		return TABLE_PREFIX+this.name;
	}
	
	public void setTableNameNoPrefix(String tableNameNoPrefix) {
		this.tableNameNoPrefix = tableNameNoPrefix;
	}
	
	public String getTableNameNoPrefix() {
		return this.tableNameNoPrefix;
	}

	public void setIsExternal(Short isExternal) {
		this.isExternal = isExternal;
	}

	/**
	 * 返回 是否外部表
	 * 
	 * @return
	 */
	public Short getIsExternal() {
		return this.isExternal;
	}

	/**
	 * 是否为外部表。
	 * 
	 * @return
	 */
	public boolean isExternal() {
		if (this.isExternal == null)
			return false;
		return this.isExternal == 1;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	/**
	 * 返回 pk_
	 * 
	 * @return
	 */
	public String getPk() {
		return this.pk;
	}

	public void setFk(String fk) {
		this.fk = fk;
	}

	public String getPkKey() {
		if (isExternal()) {
			return this.pk.toLowerCase();
		}
		return BaseBoEnt.PK_NAME.toLowerCase();
	}

	/**
	 * 返回 fk_
	 * 
	 * @return
	 */
	public String getFk() {
		if (isExternal()) {
			if(StringUtil.isEmpty(fk)){
				return "";
			}
			return this.fk.toLowerCase();
		}
		return BaseBoEnt.FK_NAME.toLowerCase();
	}

	public void setPkType(String pkType) {
		this.pkType = pkType;
	}

	/**
	 * 返回 主键类型，主要用于外部表的时候
	 * <pre>
	 * number
	 * varchar
	 * </pre>
	 * @return
	 */
	public String getPkType() {
		return this.pkType;
	}
	
	/**
	 * 主键类型是否为数字。
	 * @return
	 */
	public boolean isPkNumber(){
		//自定义表主键为字符串类型。
		if(this.isExternal()){
			return Column.COLUMN_TYPE_NUMBER.equalsIgnoreCase(this.pkType);
		}
		return false;
	}

	@JSONField(serialize=false)
	public Map<String, BaseAttribute> getAttrFieldMap() {
		return attrFieldMap;
	}

	public void setAttrFieldMap(Map<String, BaseAttribute> attrFieldMap) {
		this.attrFieldMap = attrFieldMap;
	}

	@Override
	public String toString() {
		return "BaseBoEnt [name=" + name + ", desc=t" + desc + ", packageId=" + packageId + ", type=" + type + "]";
	}
}
