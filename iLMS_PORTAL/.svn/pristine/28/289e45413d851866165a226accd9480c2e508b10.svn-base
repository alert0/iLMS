package com.hotent.form.persistence.manager.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.api.Page;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.datatrans.ITypeConvert;
import com.hotent.base.core.datatrans.ResultTransform;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.CustomQueryDao;
import com.hotent.form.persistence.manager.CustomQueryManager;
import com.hotent.form.persistence.model.CustomQuery;
import com.hotent.form.util.CustomUtil;
import com.hotent.sys.api.sqlbuilder.ISqlBuilderService;
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;

@Service("customQueryManager")
public class CustomQueryManagerImpl extends AbstractManagerImpl<String, CustomQuery> implements CustomQueryManager {
	@Resource
	CustomQueryDao customQueryDao;
	@SuppressWarnings("rawtypes")
	@Resource
	CommonDao commonDao;
	@Resource
	ISqlBuilderService sqlBuilderService;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	protected Dao<String, CustomQuery> getDao() {
		return customQueryDao;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList getData(CustomQuery customQuery, String queryData, String dsType, int pageNo, int pageSize) {
		String sql = getSql(customQuery, queryData, dsType);
		PageList pageList = null;
		List list = null;
		if(customQuery.getNeedPage()==1){
			pageNo=pageNo<=0?1:pageNo;
			pageSize=pageSize<=0?10:pageSize;
			Page page = new DefaultPage(pageNo, pageSize);
			pageList = commonDao.query(sql, page);
		}
		else{
			list = commonDao.query(sql);
			pageList = new PageList(list);
		}
		

		// 格式化
		ResultTransform.transform(pageList, new ITypeConvert() {
			@Override
			public Object processValue(Object obj) {
				Map<String, Object> map = (Map<String, Object>) obj;
				for (String key : map.keySet()) {
					if (!Date.class.isAssignableFrom(map.get(key).getClass()) && !java.util.Date.class.isAssignableFrom(map.get(key).getClass()))
						continue;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						map.put(key, sdf.format(map.get(key)));
					} catch (Exception e) {
					}
				}

				return map;
			}
		});
		return pageList;
	}

	/**
	 * 把customQuery根据一些参数翻译成SqlBuilderModel
	 * 
	 * @param customQuery
	 * @param queryData
	 *            ：页面传来的参数：JSONARRAY格式
	 * @param dsType
	 *            ：数据源的类型
	 * @return SqlBuilderModel
	 * @exception
	 * @since 1.0.0
	 */
	private SqlBuilderModel buildSqlBuilderModel(CustomQuery customQuery, String queryData, String dsType) {
		SqlBuilderModel sqlBuilderModel = new SqlBuilderModel();
		
		

		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(queryData)) {
			JSONArray jArray = JSONArray.fromObject(queryData);
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject jSONObject = jArray.getJSONObject(i);
				if (StringUtil.isNotEmpty(jSONObject.getString("value"))) {
					params.put(jSONObject.getString("key"), jSONObject.getString("value"));
				}
			}
		}

		// setting
		sqlBuilderModel.setDbType(dsType);
		sqlBuilderModel.setFromName(customQuery.getObjName());
		sqlBuilderModel.setResultField(JSONArray.fromObject(customQuery.getResultfield()));// 返回字段

		JSONArray conditionField = new JSONArray();// 条件字段
		JSONArray confilJA = JSONArray.fromObject(customQuery.getConditionfield());
		for (int i = 0; i < confilJA.size(); i++) {
			JSONObject jObject = confilJA.getJSONObject(i);

			String field = jObject.getString("field");
			String defaultType = jObject.getString("defaultType");
			String defaultValue = jObject.getString("defaultValue");
			String dbType = jObject.getString("dbType");
			String condition = jObject.getString("condition");
			Object value = null;

			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("field", field);
			jsonObject.accumulate("op", condition);
			jsonObject.accumulate("dbType", dbType);

			value = CustomUtil.buildValue(field, defaultType, defaultValue, params);
			// 为空就不加入构建条件
			if (BeanUtils.isEmpty(value)) continue;
			// 是空字符也不构建	
			if ((value instanceof String) && (StringUtil.isEmpty(value.toString()) || value.toString().equals("|"))) {
				continue;
			}

			if (condition.equals(QueryOP.BETWEEN.toString())) {// between的值特殊处理
				value = CustomUtil.handleDateBetweenValue(value);
			}

			jsonObject.accumulate("value", value);

			conditionField.add(jsonObject);
		}
		sqlBuilderModel.setConditionField(conditionField);

		sqlBuilderModel.setSortField(JSONArray.fromObject(customQuery.getSortfield()));// 排序字段

		return sqlBuilderModel;
	}

	private String getSql(CustomQuery customQuery, String queryData, String dsType) {
		String sql="";
		if(customQuery.getSqlBuildType()==1){
			Map<String,Object> params=new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(queryData)){
				com.alibaba.fastjson.JSONArray json=com.alibaba.fastjson.JSONObject.parseArray(queryData);
				Map<String,Object> tmp=new HashMap<String, Object>();
				for(Object obj :json){
					com.alibaba.fastjson.JSONObject jsonObj=(com.alibaba.fastjson.JSONObject)obj;
					tmp.put(jsonObj.getString("key"), jsonObj.get("value"));
				}
			
				params.put("map", tmp);
				params.putAll(tmp);
			}
			sql=groovyScriptEngine.executeString(customQuery.getDiySql(), params);
		}
		else{
			SqlBuilderModel model = buildSqlBuilderModel(customQuery, queryData, dsType);
			sql= sqlBuilderService.getSql(model);
		}
		return sql;
	}

	@Override
	public CustomQuery getByAlias(String alias) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("alias_", alias, QueryOP.EQUAL);
		List<CustomQuery> customQueries = query(queryFilter);
		if (customQueries == null || customQueries.isEmpty())
			return null;
		return customQueries.get(0);
	}
}
