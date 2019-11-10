package com.hotent.sys.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.sqlbuilder.ISqlBuilder;
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;
import com.hotent.sys.api.var.IContextVar;
import com.hotent.sys.persistence.dao.QueryViewDao;
import com.hotent.sys.persistence.manager.QueryMetafieldManager;
import com.hotent.sys.persistence.manager.QuerySqldefManager;
import com.hotent.sys.persistence.manager.QueryViewManager;
import com.hotent.sys.persistence.manager.SysDataSourceManager;
import com.hotent.sys.persistence.model.QuerySqldef;
import com.hotent.sys.persistence.model.QueryView;
import com.hotent.sys.persistence.sqlbuilder.service.SqlBuilderService;
import com.hotent.sys.persistence.util.FilterJsonStructUtil;

/**
 * 
 * <pre>
 * 描述：sys_query_view 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-13 17:26:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("queryViewManager")
public class QueryViewManagerImpl extends AbstractManagerImpl<String, QueryView> implements QueryViewManager {
	@Resource
	QueryViewDao queryViewDao;
	@Resource
	QuerySqldefManager querySqldefManager;
	@Resource
	QueryMetafieldManager queryMetafieldManager;
	@Resource
	CommonDao commonDao;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	@Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	SqlBuilderService sqlBuilderService;
	@Resource
	SysDataSourceManager sysDataSourceManager;

	@Override
	protected Dao<String, QueryView> getDao() {
		return queryViewDao;
	}

	@Override
	public List<QueryView> getBySqlAlias(String sqlAlias) {
		return queryViewDao.getBySqlAlias(sqlAlias);
	}

	@Override
	public void removeBySqlAlias(String sqlAlias) {
		queryViewDao.removeBySqlAlias(sqlAlias);
	}

	@Override
	public QueryView getBySqlAliasAndAlias(String sqlAlias, String alias) {
		return queryViewDao.getBySqlAliasAndAlias(sqlAlias, alias);
	}

	public String getShowSql(QueryView queryView, Map<String, Object> queryParams) {
		// 过滤条件是SQL替代，直接返回
		if (queryView.getFilterType() == (short) 2) {
			return executeScript(queryView.getFilter(), queryParams);
		}

		QuerySqldef querySqldef = querySqldefManager.getByAlias(queryView.getSqlAlias());
		JSONArray conJA = JSONArray.fromObject(queryView.getConditions());

		// 开始拼装SQL
		StringBuffer sql = new StringBuffer();
		sql.append(querySqldef.getSql().replace(";", "") + " where 1=1 ");// 处理完select语句

		/** <------------开始处理条件字段-------------> **/

		// sqlbuilder用到的条件配置
		JSONArray conditionField = new JSONArray();
		// 剩下的SQL追加和条件脚本 将其拼装到conditionField
		handleFilter(queryView, conditionField, queryParams);
		// 处理页面传过来的数据 也是把数据拼装到conditionField
		handleCondition(queryParams, conJA, conditionField);

		String dbType = sysDataSourceManager.getDbType(querySqldef.getDsName());
		SqlBuilderModel model = new SqlBuilderModel();
		model.setDbType(dbType);
		model.setConditionField(conditionField);
		ISqlBuilder sqlBuilder = sqlBuilderService.getSqlBuilder(model);
		String csql = sqlBuilder.analyzeConditionField();
		sql.append(csql);// 利用sqlbuilder拼装条件字段

		/** <------------处理条件字段结束-------------> **/

		String sortField = MapUtil.getIgnoreCase(queryParams, "sortField", "").toString();
		String orderSeq = MapUtil.getIgnoreCase(queryParams, "orderSeq", "").toString();
		if (StringUtil.isNotEmpty(sortField)) {// 利用jqGrid传过来的数据直接拼接order字段。。么的，他传的参数就是直接拼装的。。
			if (StringUtil.isNotEmpty(orderSeq)) {
				sql.append(" order by " + sortField + " " + orderSeq);
			} else {
				sql.append(" order by " + sortField.substring(0, sortField.length() - 1));
			}
		}

		return sql.toString();
	}

	/**
	 * 字符串的常量
	 * 
	 * @param script
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	private String executeScript(String script, Map<String, Object> param) {
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("param", param);
		vars.putAll(param);
		String str = groovyScriptEngine.executeString(replaceVar(script), vars);
		return str;
	}

	private String replaceVar(String str) {
		List<IContextVar> comVarList = (List<IContextVar>) AppUtil.getBean("queryViewComVarList");
		for (IContextVar c : comVarList) {
			str = str.replace("[" + c.getAlias() + "]", c.getValue());
		}
		return "return \"" + str + "\" ;";
	}

	@Override
	public void handleShowData(QueryView queryView, List list) {
		JSONArray showJA = JSONArray.fromObject(queryView.getShows());
		Map<String, Map<String, Object>> cacheMap = new HashMap<String, Map<String, Object>>();// 在sql中，做缓存
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			Map<String, Object> tmp = new HashMap<String, Object>();// 最终的字段
			for (int j = 0; j < showJA.size(); j++) {
				JSONObject jo = showJA.getJSONObject(j);
				String fieldName = jo.getString("fieldName");
				if (JsonUtil.getString(jo, "hidden", "0").equals("0")) {// 不隐藏就添加
					tmp.put(fieldName, MapUtil.getIgnoreCase(map, fieldName, "").toString());
				} else {
					continue;
				}

				if (JsonUtil.getString(jo, "isVirtual", "0").equals("0")) {// 是虚拟列
					continue;
				}

				String con = MapUtil.getIgnoreCase(map, jo.getString("virtualFrom")) + "";// 来源的值
				String str = jo.getString("resultFrom").replace("#CON#", con);
				Object val = getValFromCache(cacheMap, fieldName, con);// 先从缓存取
				if (BeanUtils.isEmpty(val)) {// 取不到值才开始计算
					if (jo.getString("resultFromType").equals("script")) {
						val = groovyScriptEngine.executeString(str, new HashMap<String, Object>());
					} else if (jo.getString("resultFromType").equals("sql")) {
						try {
							Map<String, Object> m = jdbcTemplate.queryForMap(str);
							for (String k : m.keySet()) {// 肯定只有一个值的
								val = m.get(k);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
							val = "";
						}
					}
					putValToCache(cacheMap, fieldName, con, val);// 增加缓存
				}
				tmp.put(fieldName, val.toString());
			}
			list.set(i, tmp);
		}

	}

	/**
	 * <pre>
	 * 在虚拟列处理工程中，从缓存里取值
	 * 缓存map的格式如下：eg:
	 * sex:{0:男,1:女},isChinese:{0:否,1:是}
	 * </pre>
	 * 
	 * @param cacheMap
	 * @param fieldName
	 *            :需要缓存的虚拟列 列名
	 * @param key
	 *            :来源列的值
	 * @return Object 对应的值
	 * @exception
	 * @since 1.0.0
	 */
	private Object getValFromCache(Map<String, Map<String, Object>> cacheMap, String fieldName, String key) {
		Map<String, Object> map = cacheMap.get(fieldName);
		if (BeanUtils.isEmpty(map)) {
			return null;
		}
		return map.get(key);
	}

	/**
	 * <pre>
	 * 在虚拟列处理工程中，从缓存里取值
	 * 缓存map的格式如下：eg:
	 * sex:{0:男,1:女},isChinese:{0:否,1:是}
	 * </pre>
	 * 
	 * @param cacheMap
	 * @param fieldName
	 *            :需要缓存的虚拟列 列名
	 * @param key
	 *            :来源列的值
	 * @param val
	 *            ：对应的值 void
	 * @exception
	 * @since 1.0.0
	 */
	private void putValToCache(Map<String, Map<String, Object>> cacheMap, String fieldName, String key, Object val) {
		Map<String, Object> map = cacheMap.get(fieldName);
		if (BeanUtils.isEmpty(map)) {
			map = new HashMap<String, Object>();
			cacheMap.put(fieldName, map);
		}
		map.put(key, val);
	}

	/**
	 * 拼装过滤条件的sqlbuilder对应的conditionField
	 * 
	 * @param queryView
	 * @param conditionField
	 * @param fieldJO
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void handleFilter(QueryView queryView, JSONArray conditionField, Map<String, Object> param) {
		String sql = "";
		if (queryView.getFilterType() == (short) 3) {// 追加SQL
			sql = executeScript(queryView.getFilter(), param);
		} else if (queryView.getFilterType() == (short) 1) {// 条件脚本
			QuerySqldef querySqldef = querySqldefManager.getByAlias(queryView.getSqlAlias());
			String dbType = sysDataSourceManager.getDbType(querySqldef.getDsName());
			sql = FilterJsonStructUtil.getSql(queryView.getFilter(), dbType);
		}
		if (StringUtil.isEmpty(sql)) {
			return;
		}
		JSONObject jo = new JSONObject();
		jo.put("isScript", "1");
		jo.put("value", " and " + sql);
		conditionField.add(jo);
	}

	/**
	 * 把页面传来的参数也拼装到sqlbuilder用到的条件配置
	 * 
	 * @param queryParams
	 * @param conJO
	 * @param conditionField
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	private void handleCondition(Map<String, Object> queryParams, JSONArray conJA, JSONArray conditionField) {
		// 处理条件字段 页面传过来的数据
		for (Object obj : conJA) {
			JSONObject con = (JSONObject) obj;
			String fieldName = con.getString("fieldName");
			String op = con.getString("operate");
			Object val;
			if (op.equals(QueryOP.BETWEEN.value())) {// between特殊处理
				JSONObject jo = new JSONObject();
				jo.put("start", queryParams.get("begin" + fieldName));
				if (con.getString("dataType").equals("date")) {// 日期类型的end要自增1天
					String end = queryParams.get("end"+ fieldName)+"";
					try {
						String dateFormat = con.getString("dateFormat");
						if(StringUtil.isEmpty(dateFormat)){
							dateFormat = "yyyy-MM-dd";
						}
						end = TimeUtil.getNextDays(TimeUtil.convertString(end, dateFormat), 1).toLocaleString();
					} catch (Exception e) {
					}
					jo.put("end", end);
				} else {
					jo.put("end", queryParams.get("end" + fieldName));
				}
				val = jo;
			} else {
				val = queryParams.get(fieldName);
			}

			if (BeanUtils.isEmpty(val)) {
				continue;
			}
			JSONObject jo = new JSONObject();
			jo.put("field", fieldName);
			jo.put("op", op);
			jo.put("dbType", con.getString("dataType"));
			jo.put("value", val);
			conditionField.add(jo);
		}
	}

	/**
	 * 删除页面请求的Q^开头
	 * 
	 * @param queryParams
	 * @return Map<String,Object>
	 * @exception
	 * @since 1.0.0
	 */
	private Map<String, Object> handleParam(Map<String, Object> queryParams) {
		Map<String, Object> param = new HashMap<String, Object>();
		for (String key : queryParams.keySet()) {// 只保留页面上包含Q^开头的参数
			if (!key.startsWith("Q^"))
				continue;
			param.put(key.replace("Q^", ""), queryParams.get(key));
		}
		queryParams.putAll(param);
		return param;
	}
}
