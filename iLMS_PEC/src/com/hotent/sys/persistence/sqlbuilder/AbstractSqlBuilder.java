package com.hotent.sys.persistence.sqlbuilder;

/**
 * 描述：TODO
 * 包名：com.hotent.base.db.sql
 * 文件名：DefaultSqlBuilder.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2014-7-16-下午3:33:48
 *  2014广州宏天软件有限公司版权所有
 * 
 */

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.sys.api.sqlbuilder.ISqlBuilder;
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;

/**
 * <pre>
 * 描述：TODO
 * 构建组：x5-base-db
 * 作者：lyj
 * 邮箱:liyj@jee-soft.cn
 * 日期:2014-7-16-下午3:33:48
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public abstract class AbstractSqlBuilder implements ISqlBuilder {
	protected SqlBuilderModel sqlBuilderModel;

	public AbstractSqlBuilder() {
		super();
	}

	@Override
	public void setModel(SqlBuilderModel model) {
		this.sqlBuilderModel = model;
	}

	public String analyzeResultField() {
		StringBuffer sql = new StringBuffer();

		JSONArray resultField = sqlBuilderModel.getResultField();
		if (resultField == null || resultField.isEmpty()) {
			sql.append("* ");
		} else {
			for (int i = 0; i < resultField.size(); i++) {
				JSONObject jo = resultField.getJSONObject(i);
				String field = jo.getString("field");
				String comment = jo.getString("comment");

				String aggFuncOp = jo.getString("AggFuncOp");

				sql.append(aggFuncOp);
				sql.append("(" + field + ") ");// 拼装成eg：SELECT
												// count(id_),(dsalias_)
				if (!StringUtil.isEmpty(comment)) {
					sql.append(" as " + comment + " ");
				}

				if (i < resultField.size() - 1) {// 不是最后一个元素
					sql.append(",");
				}
			}
		}

		return sql.toString();

	}

	/**
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public String analyzeConditionField() {
		StringBuffer sql = new StringBuffer();

		JSONArray conditionField = sqlBuilderModel.getConditionField();

		if (conditionField == null || conditionField.isEmpty()) {
			return sql.toString();
		}

		for (int i = 0; i < conditionField.size(); i++) {
			JSONObject jo = conditionField.getJSONObject(i);
			Object value = jo.get("value");
			if (JsonUtil.getString(jo, "isScript").equals("1")) {// 脚本加上去就行
				sql.append(value);
				continue;
			}
			String field = jo.getString("field");
			String dbType = jo.getString("dbType");

			QueryOP op = QueryOP.getByVal(jo.getString("op"));
			if (op == op.IS_NULL) {
				sql.append(" and " + field + " is null ");
				continue;
			}

			if (op == op.NOTNULL) {
				sql.append(" and " + field + " is not null ");
				continue;
			}

			if (op == QueryOP.IN) {
				String v = "";
				String[] vals = value.toString().split(",");
				for (int j = 0; j < vals.length; j++) {
					v += "'" + vals[j] + "'";
					if (j != vals.length - 1) {
						v += ",";
					}
				}
				sql.append(" and " + field + " " + op.op() + "(" + v + ") ");
				continue;
			}

			if (dbType.equals(Column.COLUMN_TYPE_VARCHAR)) {
				if (op == QueryOP.EQUAL || op == QueryOP.NOT_EQUAL) {
					sql.append(" and " + field + op.op() + "'" + value.toString() + "' ");
				} else if (op == QueryOP.LIKE) {
					sql.append(" and " + field + " like '%" + value.toString() + "%' ");
				} else if (op == QueryOP.RIGHT_LIKE) {
					sql.append(" and " + field + " like '" + value.toString() + "%' ");
				} else if (op == QueryOP.LEFT_LIKE) {
					sql.append(" and " + field + " like '%" + value.toString() + "' ");
				} else if (op == QueryOP.EQUAL_IGNORE_CASE) {
					sql.append(" and upper(" + field + ") " + op.op() + "'" + value.toString().toUpperCase() + "' ");
				}
			} else if (dbType.equals(Column.COLUMN_TYPE_INT) || dbType.equals(Column.COLUMN_TYPE_NUMBER)) {
				if (op == QueryOP.BETWEEN) {
					String start = JsonUtil.getString(((JSONObject) value), "start", "");
					String end = JsonUtil.getString(((JSONObject) value), "end", "");
					if (StringUtil.isNotEmpty(start)) {
						sql.append(" and " + field + " " + ">=" + "'" + start + "' ");
					}
					if (StringUtil.isNotEmpty(end)) {
						sql.append(" and " + field + " " + "<=" + "'" + end + "' ");
					}
				} else {
					sql.append(" and " + field + op.op() + "'" + value.toString() + "' ");
				}
			} else if (dbType.equals(Column.COLUMN_TYPE_DATE)) {
				handleDbTypeEqualDate(sql, field, op, value);
			}
		}

		return sql.toString();

	}

	/**
	 * 
	 * 处理字段类型等于date的时候
	 * 
	 * @param field
	 * @param op
	 * @param value
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public abstract void handleDbTypeEqualDate(StringBuffer sql, String field, QueryOP op, Object value);

	public String analyzeSortField() {
		StringBuffer sql = new StringBuffer();

		JSONArray sortField = sqlBuilderModel.getSortField();

		if (sortField == null || sortField.isEmpty()) {
			return "";
		}

		sql.append(" order by ");
		for (int i = 0; i < sortField.size(); i++) {
			JSONObject jo = sortField.getJSONObject(i);
			String field = jo.getString("field");
			String sortType = jo.getString("sortType");
			sql.append(field + " " + sortType);

			if (i < sortField.size() - 1) {// 不是最后一个元素
				sql.append(",");
			}
		}
		return sql.toString();
	}

	@Override
	public String getSql() {
		StringBuffer sql = new StringBuffer("select ");

		sql.append(analyzeResultField());
		sql.append("from " + sqlBuilderModel.getFromName() + " ");
		sql.append(" where 1=1 ");
		sql.append(analyzeConditionField());
		sql.append(analyzeSortField());

		return sql.toString();
	}

}
