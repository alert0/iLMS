package com.hotent.sys.persistence.sqlbuilder;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;

import net.sf.json.JSONObject;

public class SqlServerSqlBuilder extends AbstractSqlBuilder {

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

}
