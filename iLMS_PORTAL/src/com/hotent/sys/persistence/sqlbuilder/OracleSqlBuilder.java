/**
z * 描述：TODO
 * 包名：com.hotent.base.db.sql.impl
 * 文件名：OracleSqlBuilder.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午5:05:36
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.persistence.sqlbuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;

/**
 * <pre>
 * 描述：TODO
 * 构建组：x5-base-db
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午5:05:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class OracleSqlBuilder extends AbstractSqlBuilder {

	public OracleSqlBuilder() {
		super();
	}

	@Override
	// TODO 日期格式已写死- -
	public void handleDbTypeEqualDate(StringBuffer sql, String field, QueryOP op, Object value) {
		if (op == QueryOP.BETWEEN) {
			String start = JsonUtil.getString(((JSONObject) value), "start", "");
			String end = JsonUtil.getString(((JSONObject) value), "end", "");
			if (StringUtil.isNotEmpty(start)) {
				sql.append(" and " + field + " " + ">=" + " to_date('" + start + "','yyyy-mm-dd hh24:mi:ss')");
			}
			if (StringUtil.isNotEmpty(end)) {
				sql.append(" and " + field + " " + "<=" + " to_date('" + end + "','yyyy-mm-dd hh24:mi:ss')");
			}
		} else {
			sql.append(" and " + field + " " + op.op() + " to_date('" + value.toString() + "','yyyy-mm-dd hh24:mi:ss')");
		}
	}

	public static void main(String[] args) {
		// String fromName = "W_WPDD";
		// JSONArray resultField = new JSONArray();
		JSONArray conditionField = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("field", "F_DDSJ");
		jo.put("op", QueryOP.BETWEEN);
		jo.put("dbType", Column.COLUMN_TYPE_DATE);
		JSONObject value = new JSONObject();
		value.put("start", "2000-1-1");
		value.put("end", "2016-1-1");
		jo.put("value", value);
		conditionField.add(jo);
		// JSONArray sortField = new JSONArray();
		// SqlBuilder builder = new OracleSqlBuilder(fromName, resultField,
		// conditionField, sortField);
		// System.out.println(builder.getSql());
	}

}
