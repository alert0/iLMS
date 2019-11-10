/**
 * 描述：TODO
 * 包名：com.hotent.base.db.sql.impl
 * 文件名：MySqlSqlBuilder.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午4:33:40
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
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;

/**
 * <pre>
 * 描述：TODO
 * 构建组：x5-base-db
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午4:33:40
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class MySqlSqlBuilder extends AbstractSqlBuilder {
	public MySqlSqlBuilder() {
		super();
	}

	@Override
	public void handleDbTypeEqualDate(StringBuffer sql, String field, QueryOP op, Object value) {
		if (op == QueryOP.BETWEEN) {
			String start = JsonUtil.getString(((JSONObject) value), "start", "");
			String end = JsonUtil.getString(((JSONObject) value), "end", "");
			if (StringUtil.isNotEmpty(start)) {
				sql.append(" and " + field + " " + ">=" + " '" + start + "'");
			}
			if (StringUtil.isNotEmpty(end)) {
				sql.append(" and " + field + " " + "<=" + " '" + end + "'");
			}
		} else {
			sql.append(" and " + field + " " + op.op() + " '" + value.toString() + "'");
		}
	}

	public static void main(String[] args) {
		// String fromName = "tbtest";
		// JSONArray resultField =
		// JSONArray.fromObject("[{\"field\":\"birthday\",\"comment\":\"生日\",\"AggFuncOp\":\"\"},{\"field\":\"name\",\"comment\":\"名称\",\"AggFuncOp\":\"\"}]");
		JSONArray conditionField = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("field", "birthday");
		jo.put("op", QueryOP.BETWEEN);
		jo.put("dbType", Column.COLUMN_TYPE_DATE);
		JSONObject value = new JSONObject();
		value.put("start", "2000-1-1");
		value.put("end", "2016-1-1");
		jo.put("value", value);
		conditionField.add(jo);
		// JSONArray sortField = new JSONArray();
		// SqlBuilder builder = new MySqlSqlBuilder(fromName, resultField,
		// conditionField, sortField);
		// System.out.println(builder.getSql());
	}
}
