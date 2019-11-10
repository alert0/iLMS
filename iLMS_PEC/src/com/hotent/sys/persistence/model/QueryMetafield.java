package com.hotent.sys.persistence.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.core.model.AbstractModel;
import com.hotent.sys.persistence.enums.FieldControlType;

/**
 * 
 * <pre>
 * 描述：sys_query_metafield 实体对象
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 16:42:01
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public class QueryMetafield extends AbstractModel<String> {

	public static final short TRUE = 1;
	public static final short FALSE = 0;
	/**
	 * 主键
	 */
	protected String id;

	/**
	 * SQL_ID
	 */
	
	protected String sqlId;

	/**
	 * 字段名
	 */
	@XmlAttribute(name = "name")
	protected String name;

	/**
	 * 实际字段名
	 */
	@XmlAttribute(name = "fieldName")
	protected String fieldName;

	/**
	 * 字段备注
	 */
	@XmlAttribute(name = "fieldDesc")
	protected String fieldDesc;

	/**
	 * 是否可见
	 */
	@XmlAttribute(name = "isShow")
	protected Short isShow;

	/**
	 * 是否搜索
	 */
	@XmlAttribute(name = "isSearch")
	protected Short isSearch;

	/**
	 * 控件类型
	 */
	@XmlAttribute(name = "controlType")
	protected String controlType;

	/**
	 * 数据类型
	 */
	@XmlAttribute(name = "dataType")
	protected String dataType;

	/**
	 * 是否衍生列
	 */
	@XmlAttribute(name = "isVirtual")
	protected Short isVirtual;

	/**
	 * 衍生列来自列
	 */
	@XmlAttribute(name = "virtualFrom")
	protected String virtualFrom;

	/**
	 * 来自类型
	 */
	@XmlAttribute(name = "resultFromType")
	protected String resultFromType;

	/**
	 * 衍生列配置
	 */
	@XmlElement(name = "resultFrom")
	protected String resultFrom;

	/**
	 * 报警设定
	 */
	@XmlElement(name = "alarmSetting")
	protected String alarmSetting;

	/**
	 * 日期格式
	 */
	@XmlAttribute(name = "dateFormat")
	protected String dateFormat;

	/**
	 * 连接地址
	 */
	@XmlAttribute(name = "url")
	protected String url;

	/**
	 * 格式化函数
	 */
	@XmlElement(name = "formater")
	protected String formater;

	/**
	 * 控件内容
	 */
	@XmlElement(name = "controlContent")
	protected String controlContent;

	/**
	 * SN_
	 */
	@XmlAttribute(name = "sn")
	protected Short sn;

	/**
	 * WIDTH_
	 */
	@XmlAttribute(name = "width")
	protected Short width;

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

	public void setSqlId(String sqlId) {
		this.sqlId = sqlId;
	}

	/**
	 * 返回 SQL_ID
	 * 
	 * @return
	 */
	public String getSqlId() {
		return this.sqlId;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 字段名
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 返回 实际字段名
	 * 
	 * @return
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	/**
	 * 返回 字段备注
	 * 
	 * @return
	 */
	public String getFieldDesc() {
		return this.fieldDesc;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	/**
	 * 返回 是否可见
	 * 
	 * @return
	 */
	public Short getIsShow() {
		return this.isShow;
	}

	public void setIsSearch(Short isSearch) {
		this.isSearch = isSearch;
	}

	/**
	 * 返回 是否搜索
	 * 
	 * @return
	 */
	public Short getIsSearch() {
		return this.isSearch;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	public String getControlTypeDesc() {
		if (this.controlType.equals("onetext")) {
			return "单行文本框";
		}
		if (this.controlType.equals("select")) {
			return "下拉框";
		}
		if (this.controlType.equals("customdialog")) {
			return "自定义对话框";
		}
		if (this.controlType.equals("date")) {
			return "日期选择器";
		}
		return "";
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * 返回 数据类型
	 * 
	 * @return
	 */
	public String getDataType() {
		return this.dataType;
	}

	public void setIsVirtual(Short isVirtual) {
		this.isVirtual = isVirtual;
	}

	/**
	 * 返回 是否衍生列
	 * 
	 * @return
	 */
	public Short getIsVirtual() {
		return this.isVirtual;
	}

	public void setVirtualFrom(String virtualFrom) {
		this.virtualFrom = virtualFrom;
	}

	/**
	 * 返回 衍生列来自列
	 * 
	 * @return
	 */
	public String getVirtualFrom() {
		return this.virtualFrom;
	}

	public void setResultFromType(String resultFromType) {
		this.resultFromType = resultFromType;
	}

	/**
	 * 返回 来自类型
	 * 
	 * @return
	 */
	public String getResultFromType() {
		return this.resultFromType;
	}

	public void setResultFrom(String resultFrom) {
		this.resultFrom = resultFrom;
	}

	/**
	 * 返回 衍生列配置
	 * 
	 * @return
	 */
	public String getResultFrom() {
		return this.resultFrom;
	}

	public void setAlarmSetting(String alarmSetting) {
		this.alarmSetting = alarmSetting;
	}

	/**
	 * 返回 报警设定
	 * 
	 * @return
	 */
	public String getAlarmSetting() {
		return this.alarmSetting;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 返回 日期格式
	 * 
	 * @return
	 */
	public String getDateFormat() {
		return this.dateFormat;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回 连接地址
	 * 
	 * @return
	 */
	public String getUrl() {
		return this.url;
	}

	public void setFormater(String formater) {
		this.formater = formater;
	}

	/**
	 * 返回 格式化函数
	 * 
	 * @return
	 */
	public String getFormater() {
		return this.formater;
	}

	public void setControlContent(String controlContent) {
		this.controlContent = controlContent;
	}

	/**
	 * 返回 控件内容
	 * 
	 * @return
	 */
	public String getControlContent() {
		return this.controlContent;
	}

	public void setSn(Short sn) {
		this.sn = sn;
	}

	/**
	 * 返回 SN_
	 * 
	 * @return
	 */
	public Short getSn() {
		return this.sn;
	}

	public void setWidth(Short width) {
		this.width = width;
	}

	/**
	 * 返回 WIDTH_
	 * 
	 * @return
	 */
	public Short getWidth() {
		return this.width;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("sqlId", this.sqlId).append("name", this.name).append("fieldName", this.fieldName).append("fieldDesc", this.fieldDesc).append("isShow", this.isShow).append("isSearch", this.isSearch).append("controlType", this.controlType).append("dataType", this.dataType).append("isVirtual", this.isVirtual).append("virtualFrom", this.virtualFrom).append("resultFromType", this.resultFromType).append("resultFrom", this.resultFrom).append("alarmSetting", this.alarmSetting).append("dateFormat", this.dateFormat).append("url", this.url).append("formater", this.formater).append("controlContent", this.controlContent).append("sn", this.sn).append("width", this.width).toString();
	}
}