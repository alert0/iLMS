/**
 * 描述：TODO
 * 包名：com.hotent.platform.bpm.model
 * 文件名：SqlBuilderModel.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午5:53:46
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.api.sqlbuilder;

import net.sf.json.JSONArray;

/**
 * <pre>
 * 描述：
 * 返回字段的JsonArray，每个JSONObject:{"field":"字段名字","comment":"列名","AggFuncOp":"合计函数(sum,max,min..可以为空)"}
 * 条件字段的JsonArray，每个JSONObject:{"field":"字段名字","op":"QueryOP.op","dbType":"字段数据类型,eg:Column.COLUMN_TYPE_VARCHAR","value":"Object（因为between之类的会有两个值,多值用JSONObject装载）","isScript":"1/0"}
 * 排序字段的JsonArray，每个JSONObject:{"field":"字段名字","sortType":"排序方式（asc,desc）"}
 * 构建组：x5-bpmx-platform
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午5:53:46
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class SqlBuilderModel {
	/**
	 * 数据库类型
	 */
	private String dbType = "";

	// String fromName, JSONArray resultField, JSONArray conditionField,
	// JSONArray sortField

	// 数据来源的名字，可以是table名字或者view的名字
	private String fromName;
	// 返回字段的JsonArray，每个JSONObject:{"field":"字段名字","comment":"列名","AggFuncOp":"合计函数(sum,max,min..可以为空)"}
	private JSONArray resultField;
	// 条件字段的JsonArray，每个JSONObject:{"field":"字段名字","op":"QueryOP，运算符","dbType":"字段数据类型,eg:Column.COLUMN_TYPE_VARCHAR","value":"Object（因为between之类的会有两个值,多值用JSONObject装载）","isScript":"1/0"}
	private JSONArray conditionField;
	// 排序字段的JsonArray，每个JSONObject:{"field":"字段名字","sortType":"排序方式（asc,desc）"}
	private JSONArray sortField;

	/**
	 * dbType
	 * 
	 * @return the dbType
	 * @since 1.0.0
	 */

	public String getDbType() {
		return dbType;
	}

	/**
	 * @param dbType
	 *            the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * fromName
	 * 
	 * @return the fromName
	 * @since 1.0.0
	 */

	public String getFromName() {
		return fromName;
	}

	/**
	 * @param fromName
	 *            the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * resultField
	 * 
	 * @return the resultField
	 * @since 1.0.0
	 */
	public JSONArray getResultField() {
		return resultField;
	}

	/**
	 * @param resultField
	 *            the resultField to set
	 */
	public void setResultField(JSONArray resultField) {
		this.resultField = resultField;
	}

	/**
	 * conditionField
	 * 
	 * @return the conditionField
	 * @since 1.0.0
	 */

	public JSONArray getConditionField() {
		return conditionField;
	}

	/**
	 * @param conditionField
	 *            the conditionField to set
	 */
	public void setConditionField(JSONArray conditionField) {
		this.conditionField = conditionField;
	}

	/**
	 * sortField
	 * 
	 * @return the sortField
	 * @since 1.0.0
	 */

	public JSONArray getSortField() {
		return sortField;
	}

	/**
	 * @param sortField
	 *            the sortField to set
	 */
	public void setSortField(JSONArray sortField) {
		this.sortField = sortField;
	}
}
