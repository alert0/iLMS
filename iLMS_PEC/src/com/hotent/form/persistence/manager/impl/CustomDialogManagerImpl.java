package com.hotent.form.persistence.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.DateFormatUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.form.persistence.dao.CustomDialogDao;
import com.hotent.form.persistence.manager.CustomDialogManager;
import com.hotent.form.persistence.model.CustomDialog;
import com.hotent.sys.api.sqlbuilder.ISqlBuilder;
import com.hotent.sys.api.sqlbuilder.ISqlBuilderService;
import com.hotent.sys.api.sqlbuilder.SqlBuilderModel;

@Service("customDialogManager")
public class CustomDialogManagerImpl extends AbstractManagerImpl<String, CustomDialog> implements CustomDialogManager {
	@Resource
	CustomDialogDao customDialogDao;
	@Resource
	ISqlBuilderService sqlBuilderService;
	@Resource
	CommonDao commonDao;
	@Resource
	GroovyScriptEngine groovyScriptEngine;

	@Override
	protected Dao<String, CustomDialog> getDao() {
		return customDialogDao;
	}

	@Override
	public CustomDialog getByAlias(String alias) {
		return customDialogDao.getByAlias(alias);
	}

	@Override
	public List getListData(CustomDialog customDialog, Map<String, Object> param, String dbType) {
		String sql ="";
		if(customDialog.getSqlBuildType()==1){
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("map", param);
			params.putAll(param);
			sql=groovyScriptEngine.executeString(customDialog.getDiySql(), params);
		}
		else{
			
			SqlBuilderModel model = constructSqlBuilderModel(customDialog, param, dbType);
			ISqlBuilder sqlBuilder = sqlBuilderService.getSqlBuilder(model);
			sql = sqlBuilder.getSql();
		}

		List list = null;
		if (customDialog.getNeedPage()) {
			int pageNo = Integer.parseInt(MapUtil.getIgnoreCase(param, "page", "1").toString());
			int pageSize = Integer.parseInt(MapUtil.getIgnoreCase(param, "pageSize", customDialog.getPageSize()).toString());
			list = commonDao.query(sql, new DefaultPage(pageNo, pageSize));
		} else {
			list = commonDao.query(sql);
		}
		
		//大小写兼容处理
		for(int i=0;i<list.size();i++){
			Map<String, Object> m =(Map<String, Object>) list.get(i);
			Map<String, Object> tm = new HashMap<String, Object>();
			for(String k : m.keySet()){
				try {
					if(BeanUtils.isNotEmpty(m.get(k))&&"java.sql.Timestamp".equals(m.get(k).getClass().getCanonicalName())){
						Date date = (Date) m.get(k);
						if(date.getYear()==70&&date.getMonth()==0&&date.getDate()==1){
							tm.put(k.toUpperCase(),DateFormatUtil.format(date, "HH:mm:ss"));
						}else if(date.getMinutes()==0&&date.getHours()==0&&date.getSeconds()==0){
							tm.put(k.toUpperCase(),DateFormatUtil.formatDate(date));
						}else{
							tm.put(k.toUpperCase(),DateFormatUtil.formaDatetTime((Date) m.get(k)) );
						}
					}else{
						tm.put(k.toUpperCase(), m.get(k));
					}
				} catch (Exception e) {
					tm.put(k.toUpperCase(), m.get(k));
				}
			}
			list.set(i, tm);
		}
		return list;
	}

	@Override
	public List geTreetData(CustomDialog customDialog, Map<String, Object> param, String dbType) {
		String sql = getTreeSql(customDialog, param, dbType);

		List pageList = commonDao.query(sql);

		// 处理是否是节点的问题
		handleIsParent(pageList);

		return pageList;
	}
	
	// 遍历参数中传过来的 可选条件，并返回可选条件的 值和condition
	private Map<String,Object> getFromParam(Map<String, Object> param , String fieldName){
		try {
			Pattern regex = Pattern.compile("Q\\^(.*)\\^(.*)");
			String name = "";
			for (String k : param.keySet()) {
				Matcher regexMatcher = regex.matcher(k);
				if (regexMatcher.matches()) {
					name = regexMatcher.group(1);
					if(fieldName.equals(name)){
						Object object = param.get(k);
						if(BeanUtils.isEmpty(object)){
							return null;
						}
						Map<String, Object> returnMap = MapUtil.buildMap("value", object);
						returnMap.put("condition", regexMatcher.group(2));
						return returnMap;
					}
				}
			}
		} catch (PatternSyntaxException ex) {
			return null;
		}
		return null;
	}
 
	private SqlBuilderModel constructSqlBuilderModel(CustomDialog customDialog, Map<String, Object> param, String dbType) {
		// 拼装条件字段
		JSONArray conditionField = new JSONArray();
		JSONArray conJA = JSONArray.fromObject(customDialog.getConditionfield());
		for (int i = 0; i < conJA.size(); i++) {
			JSONObject jo = conJA.getJSONObject(i);
			String fieldName = jo.getString("field");
			String defaultType = jo.getString("defaultType");
			String defaultValue = jo.getString("defaultValue");
			String fdbType = jo.getString("dbType");
			String condition = jo.getString("condition");

			Object value = null;
			if (defaultType.equals("1")) {// 页面输入参数传入
				value =MapUtil.getIgnoreCase(param, "Q^" + fieldName);
			} else if (defaultType.equals("2")) {// 固定值
				value = defaultValue;
			} else if (defaultType.equals("3")) {// 脚本
				value = groovyScriptEngine.executeObject(defaultValue, param);
			} else if(defaultType.equals("7")) {//可选条件
				Map<String, Object> fromParam = getFromParam(param, fieldName);
				if(BeanUtils.isEmpty(fromParam)) continue;
				condition = MapUtil.getString(fromParam, "condition");
				value = fromParam.get("value");
			}
			else{// 动态传入
				value = MapUtil.getIgnoreCase(param,fieldName);
			}

			if (BeanUtils.isEmpty(value))
				continue;

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("field", fieldName);
			jsonObject.put("op", condition);
			jsonObject.put("dbType", fdbType);
			jsonObject.put("value", value);
			conditionField.add(jsonObject);
		}

		// 拼装排序排序
		JSONArray sortField = JSONArray.fromObject(customDialog.getSortfield());
		JSONObject sortFieldJO = JsonUtil.arrayToObject(sortField, "field");
		String sortFieldStr = MapUtil.getIgnoreCase(param, "sortField", "").toString();
		String orderSeq = MapUtil.getIgnoreCase(param, "orderSeq", "").toString();
		if (StringUtil.isNotEmpty(sortFieldStr) && StringUtil.isNotEmpty(orderSeq)) {
			JSONObject jo = new JSONObject();
			jo.put("field", sortFieldStr);
			jo.put("sortType", orderSeq);
			sortFieldJO.put(sortFieldStr, jo);
		}

		SqlBuilderModel model = new SqlBuilderModel();
		model.setFromName(customDialog.getObjName());
		model.setDbType(dbType);
		model.setConditionField(conditionField);
		model.setSortField(JsonUtil.objectToArray(sortFieldJO));
		
		return model;
	}

	private void handleIsParent(List list) {
		for (Object obj : list) {
			Map<String, Object> map = (Map<String, Object>) obj;
			for (String key : map.keySet()) {
				if (!key.equals("isParent") && key.trim().toUpperCase().equals("ISPARENT")) {
					Object isParent = map.get(key);
					if (isParent != null) {
						if ("true".equals(isParent.toString())) {
							map.put("isParent", true);
						} else {
							map.put("isParent", false);
						}
						map.remove(key);
						break;
					}
				}
			}

		}
	}

	private String getTreeSql(CustomDialog customDialog, Map<String, Object> param, String dbType) {
		String sql = "";

		SqlBuilderModel model = constructSqlBuilderModel(customDialog, param, dbType);

		JSONObject djo = JSONObject.fromObject(customDialog.getDisplayfield());

		// 处理pid的值问题，其实就是把pid添加到sqlbuildermodel的条件构建中
		JSONObject pidJson = new JSONObject();
		String pid = getPid(customDialog, param);
		if (StringUtil.isNotEmpty(pid)) {
			pidJson.put("field", djo.get("pid"));
			pidJson.put("op", QueryOP.EQUAL.value());
			pidJson.put("dbType", Column.COLUMN_TYPE_VARCHAR);
			pidJson.put("value", pid);

		} else {
			pidJson.put("field", djo.get("pid"));
			pidJson.put("dbType", Column.COLUMN_TYPE_VARCHAR);
			pidJson.put("op", QueryOP.IS_NULL);
		}
		model.getConditionField().add(pidJson);

		sql = sqlBuilderService.getSql(model);

		sql = sql.replace(customDialog.getObjName(), customDialog.getObjName() + " o ");

		String isParentSql = ", ( case (select count(*)  from " + customDialog.getObjName() + " p where p." + djo.get("pid") + "=o." + djo.get("id") + " and p." + djo.get("id") + "!=p." + djo.get("pid") + ") when 0 then 'false' else 'true' end )isParent ";
		String[] strs = sql.split("from");

		if (strs[0].contains("*")) {
			strs[0] = strs[0].replace("*", "");
		} else {
			strs[0] += ",";
		}

		String str0 = strs[0] + djo.get("id") + " \""+  djo.get("id")+"\""  + "," + djo.get("pid") + " \""+  djo.get("pid")+"\""   + "," + djo.get("displayName")+" \""+  djo.get("displayName")+"\"";
		str0 = str0 + isParentSql;
		sql = str0 + " from " + strs[1];

		return sql;
	}

	private String getPid(CustomDialog customDialog, Map<String, Object> param) {

		JSONObject jo = JSONObject.fromObject(customDialog.getDisplayfield());
		String id = jo.getString("id");
		String isScript = JsonUtil.getString(jo, "isScript", "false");
		String pvalue = JsonUtil.getString(jo, "pvalue", "");

		String pidVal = MapUtil.getIgnoreCase(param, id, "").toString();
		if (StringUtil.isEmpty(pidVal)) {
			if (isScript != null && isScript.equals("true")) {// 是脚本，开始解释这段脚本
				pidVal = groovyScriptEngine.executeString(pvalue, null).toString();
			} else {
				pidVal = pvalue;
			}
		}
		return pidVal;
	}
}
