package com.hotent.bo.instance.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.exception.BOBaseException;
import com.hotent.bo.api.instance.BoSubDataHandler;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.bo.api.model.BoResult.ACTION_TYPE;
import com.hotent.bo.api.model.SqlModel;
import com.hotent.bo.persistence.dao.BODataRelDao;
import com.hotent.bo.persistence.model.BODataRel;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BoAttribute;

@Service
public class BoDbHandlerImpl extends AbstractBoDataHandler {

	@Resource
	JdbcTemplate jdbcTemplate;
	@Resource
	BODataRelDao boDataRelDao;
	@Resource
	BoSubDataHandler boSubDataHandler;
	@Resource
	CommonDao<Map<String,Object>> commonDao;

	@Override
	public List<BoResult> save(String id, String defId, BoData curData) {
		BOEnt boEnt = (BOEnt) curData.getBoEnt();
		Map<String, Object> row = curData.getData();
		String pk = boEnt.getPkKey().toLowerCase();
		
		if(StringUtil.isNotEmpty(id)){
			row.put(pk, id);
		}
		// 添加结果。
		List<BoResult> resultList = new ArrayList<BoResult>();
		try {
			// 数据中包含主表表示更新
			if (row.containsKey(pk)) {
				update(curData, resultList);
			} else {
				add(curData, resultList, "0");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BOBaseException(ex.getMessage());
		}
		// 设置bodefCode.
		setBoCodeCode(resultList, curData.getBoDef().getAlias());

		return resultList;
	}

	@Override
	public BoData getById(Object pk, String bodefCode) {
		BODef boDef = boDefManager.getByDefName(bodefCode);

		BoData boData = new BoData();
		boData.setBoDef(boDef);
		BOEnt boEnt = (BOEnt) boDef.getBoEnt();
		boData.setBoEnt(boEnt);

		Map<String, Object> row = getById(boEnt, pk);
		boData.setData(row);

		// 子表处理
		List<BaseBoEnt> childEntList = boEnt.getChildEntList();

		for (BaseBoEnt childEnt : childEntList) {
			List<Map<String, Object>> list = getByFk((BOEnt) childEnt, pk);
			String key = childEnt.getName();

			List<BoData> listData = new ArrayList<BoData>();

			for (Map<String, Object> rowMap : list) {
				BoData childData = new BoData();

				Map<String, Object> rtnMap = convertDbToData((BOEnt) childEnt, rowMap);

				childData.setData(rtnMap);
				listData.add(childData);
			}
			boData.addInitDataMap(key, childEnt.getInitData());
			boData.setSubList(key, listData);
		}

		return boData;
	}

	/**
	 * 将从数据库读取的数据到实例数据。
	 * 
	 * @param boEnt
	 * @param map
	 * @return
	 */
	private Map<String, Object> convertDbToData(BOEnt boEnt, Map<String, Object> map) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		for (Entry<String, Object> ent : map.entrySet()) {
			String field = ent.getKey().toLowerCase();
			BaseAttribute attribute = boEnt.getAttrByField(field);
			// 处理日期。
			Object val = handValue(attribute, ent.getValue());
			rtnMap.put(attribute.getName(), val);
		}
		return rtnMap;
	}

	/**
	 * 数据根据bo属性处理。
	 * 
	 * @param attr
	 * @param val
	 * @return
	 */
	private Object handValue(BaseAttribute attr, Object val) {
		if (BeanUtils.isEmpty(val))
			return val;
		if (Column.COLUMN_TYPE_DATE.equals(attr.getDataType())) {
			String format = attr.getFormat();
			return TimeUtil.getDateTimeString((Date) val, format);
		}
		return val;
	}

	/**
	 * 获取一行数据。
	 * 
	 * @param boEnt
	 * @param instId
	 * @return
	 */
	private Map<String, Object> getById(BOEnt boEnt, Object pk) {
		String sql = "select * from " + boEnt.getTableName() + " where " + boEnt.getPkKey() + "=?";

		Map<String, Object> map = null;
		if (boEnt.isExternal()) {//外部表数据
			try {
				map = DataSourceUtil.getJdbcTempByDsAlias(boEnt.getDsName()).queryForMap(sql, new Object[] { pk });
			} catch (Exception e) {
				throw new RuntimeException("操作外部表：" + boEnt.getDsName() + " 中的 " + boEnt.getDesc() + " 出错："+e.getMessage(), e);
			}
		}else{
			map = jdbcTemplate.queryForMap(sql, new Object[] { pk });
		}

		Map<String, Object> rtnMap = convertDbToData(boEnt, map);

		return rtnMap;
	}

	/**
	 * 根据外键获取列表数据。
	 * 
	 * @param boEnt
	 * @param instId
	 * @return
	 */
	private List<Map<String, Object>> getByFk(BOEnt boEnt, Object pk) {
		List<Map<String, Object>> list = boSubDataHandler.getSubDataByFk(boEnt, pk);
		return list;
	}

	/**
	 * 处理添加数据的情况。
	 * 
	 * @param curData
	 * @param resultList
	 * @param parentId
	 * @throws ParseException
	 */
	private void add(BoData curData, List<BoResult> resultList, String parentId) throws ParseException {
		BOEnt boEnt = (BOEnt) curData.getBoEnt();

		Map<String, Object> map = convertDbMap(curData);
		// 添加主表
		BoResult boResult = insert(boEnt, map, parentId);
		resultList.add(boResult);

		Map<String, BaseBoEnt> entMap = boEnt.getChildMap();
		if (BeanUtils.isEmpty(entMap)) return;
		// 子表添加
		for (Entry<String, List<BoData>> ent : curData.getSubMap().entrySet()) {
			String key = ent.getKey();
			String tableName = key.replaceFirst("sub_", "");
			BOEnt childEnt = (BOEnt) entMap.get(tableName.toLowerCase());
			List<BoData> subDataList = ent.getValue();
			for (BoData chidData : subDataList) {
				// 设置子数据的实体元数据。
				chidData.setBoEnt(childEnt);
				add(chidData, resultList, boResult.getPk());
			}
		}
	}

	/**
	 * 更新数据只管两层结构。
	 * 
	 * <pre>
	 * 	1.更新主表。
	 *  2.更新子表。
	 *  	1.添加
	 *  	2.删除
	 *  	3.更新
	 * </pre>
	 * 
	 * @param curData
	 * @param resultList
	 * @throws ParseException
	 */
	private void update(BoData curData, List<BoResult> resultList) throws ParseException {
		BOEnt boEnt = (BOEnt) curData.getBoEnt();

		Map<String, Object> map = convertDbMap(curData);
		// 更新主表
		BoResult boResult = update(boEnt, map);
		resultList.add(boResult);

		String pk = (String) map.get(boEnt.getPkKey());

		Map<String, BaseBoEnt> entMap = boEnt.getChildMap();

		if (BeanUtils.isEmpty(entMap)) return;
		//删除子表数据中不存在的数据
		if(BeanUtils.isEmpty(curData.getSubMap())){
			for (Entry<String, BaseBoEnt> entry : entMap.entrySet()) {  
				BOEnt childEnt = (BOEnt) entMap.get(entry.getKey());
			   if(BeanUtils.isNotEmpty(childEnt)){
				   	// 获取原来的数据。
					Set<String> oldIdSet = getOldIds(childEnt, pk);
					for(String subPk : oldIdSet){
						BoResult result = delete(childEnt, subPk);
						resultList.add(result);
					}
			   }
			  
			}  
		}else{
			// 子表数据更新。
			for (Entry<String, List<BoData>> ent : curData.getSubMap().entrySet()) {
				String key = ent.getKey();
				String tableName = key.replaceFirst("sub_", "");
				BOEnt childEnt = (BOEnt) entMap.get(tableName.toLowerCase());
				// 获取原来的数据。
				Set<String> oldIdSet = getOldIds(childEnt, pk);
				//存在的记录。
				Set<String> updSet=new HashSet<String>();
				//
				List<BoData> subDataList = ent.getValue();
				for (BoData chidData : subDataList) {
					chidData.setBoEnt(childEnt);
					String childPkField = childEnt.getPkKey();
					Map<String, Object> childRow = convertDbMap(chidData);
					// 表示数据存在。
					if (chidData.containKey(childPkField)) {
						String childPk = chidData.getString(childPkField);
						updSet.add(childPk);
						// 包含
						if (oldIdSet.contains(childPk)) {
							BoResult result = update(childEnt, childRow);
							if (result != null) {
								resultList.add(result);
							}
						}
					}
					// 数据不存在则添加
					else {
						BoResult result = insert(childEnt, childRow, pk);
						resultList.add(result);
					}
				}
				//原来的集合不包含提交的记录就要删除。
				for(String subPk : oldIdSet){
					if(!updSet.contains(subPk)){
						BoResult result = delete(childEnt, subPk);
						resultList.add(result);
					}
				}
			}
		}
		// 设置bocode
		setBoCodeCode(resultList, curData.getBoDef().getAlias());
	}

	/**
	 * 获取子表数据ID
	 * 
	 * @param childEnt
	 * @param pk
	 * @return
	 */
	private Set<String> getOldIds(BOEnt childEnt, String pk) {
		Set<String> set = new HashSet<String>();
		String pkField = childEnt.getPkKey().toLowerCase();
		List<Map<String, Object>> oldList = boSubDataHandler.getSubDataByFk(childEnt, pk);

		for (Map<String, Object> row : oldList) {
			for (Map.Entry<String, Object> entry : row.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(pkField)) {
					set.add(entry.getValue().toString());
					break;
				}
			}
		}
		return set;
	}

	/**
	 * 将数据进行转换。
	 * 
	 * <pre>
	 * 1.将map数据转成DB字段。
	 * 2.转换数据的类型。
	 * </pre>
	 * 
	 * @param boEnt
	 * @param curData
	 * @return
	 * @throws ParseException
	 */
	private Map<String, Object> convertDbMap(BoData curData) throws ParseException {
		BOEnt boEnt = (BOEnt) curData.getBoEnt();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		List<BaseAttribute> list = boEnt.getBoAttrList();
		for (BaseAttribute attr : list) {
			BoAttribute boAttr = (BoAttribute) attr;
			String fieldName = boAttr.getFieldName();
			String name = boAttr.getName();
			if (!curData.containKey(name)) continue;
			
			Object obj = curData.getValByKey(name);
			rtnMap.put(fieldName, obj);
		}
		
		String pk=boEnt.getPkKey();
		String fk=boEnt.getFk();
		
		
		if (curData.containKey(pk)) {
			rtnMap.put(pk, curData.getString(pk));
		}

		if (StringUtil.isNotEmpty(fk) && curData.containKey(fk)) {
			rtnMap.put(fk, curData.getString(fk));
		}
		
		
		
		return rtnMap;

	}

	/**
	 * 插入数据。
	 * 
	 * @param baseBoEnt
	 * @param row
	 * @param parentId
	 * @return
	 */
	private BoResult insert(BOEnt baseBoEnt, Map<String, Object> row, String parentId) {
		String tableName = baseBoEnt.getTableName();
		String pkField = baseBoEnt.getPkKey();
		String id = UniqueIdUtil.getSuid();

		StringBuffer fieldNames = new StringBuffer();
		StringBuffer params = new StringBuffer();
		final List<Object> values = new ArrayList<Object>();

		fieldNames.append(pkField).append(",");
		params.append("?,");
		values.add(id);

		// 多对多时关联放到第三张表中。
		if (!baseBoEnt.getType().equals(BaseBoDef.BOENT_RELATION.MANY_TO_MANY) && StringUtil.isNotEmpty(baseBoEnt.getFk())) {
			fieldNames.append(baseBoEnt.getFk()).append(",");
			params.append("?,");
			values.add(parentId);
		}

		for (Map.Entry<String, Object> entry : row.entrySet()) {
			fieldNames.append(entry.getKey()).append(",");
			params.append("?,");
			values.add(entry.getValue());
		}
		StringBuffer sql = new StringBuffer();

		sql.append(" INSERT INTO ");
		sql.append(tableName);
		sql.append("(");
		sql.append(fieldNames.substring(0, fieldNames.length() - 1));
		sql.append(")");
		sql.append(" VALUES (");
		sql.append(params.substring(0, params.length() - 1));
		sql.append(")");

		SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());

		// 执行插入动作。
		executeSql(sqlModel, baseBoEnt);

		// 多对多的情况处理中间表。
		if (baseBoEnt.getType().equals(BaseBoDef.BOENT_RELATION.MANY_TO_MANY)) {
			String relPk = UniqueIdUtil.getSuid();
			BODataRel entRel = new BODataRel(relPk, parentId, id, baseBoEnt.getName());
			boDataRelDao.create(entRel);
		}

		/**
		 * 结果返回。
		 */
		BoResult result = new BoResult();
		result.setParentId(parentId);
		result.setAction(ACTION_TYPE.ADD);
		result.setBoEnt(baseBoEnt);
		result.setPk(id);

		return result;

	}

	/**
	 * 
	 * 执行sql语句。
	 * 
	 * @param model
	 * @param boEnt
	 *            :为null时默认是在本地数据与执行该sql void
	 * @exception
	 * @since 1.0.0
	 */
	private void executeSql(SqlModel model, BOEnt boEnt) {
		String sql = model.getSql();
		if (StringUtil.isEmpty(sql)) return;
		Object[] obs = model.getValues();
		if (boEnt == null || !boEnt.isExternal()) {
			jdbcTemplate.update(sql, obs);
		} else {
			try {
				JdbcTemplate template= DataSourceUtil.getJdbcTempByDsAlias(boEnt.getDsName());
				template.update(sql, obs);
			} catch (Exception e) {
				throw new RuntimeException("操作外部表：" + boEnt.getDsName() + " 中的 " + boEnt.getDesc() + " 出错："+e.getMessage(),e);
			}
		}

	}

	/**
	 * 更新一行数据。
	 * 
	 * @param boEnt
	 * @param row
	 * @return
	 */
	private BoResult update(BOEnt boEnt, Map<String, Object> row) {

		String tableName = boEnt.getTableName();
		String pkField = boEnt.getPkKey();

		final List<Object> values = new ArrayList<Object>();

		String pkValue = row.get(pkField).toString();

		StringBuffer set = new StringBuffer();

		for (Map.Entry<String, Object> entry : row.entrySet()) {
			// 主键忽略
			if (pkField.equals(entry.getKey()))
				continue;

			set.append(entry.getKey()).append("=?,");
			values.add(entry.getValue());
		}
		if (values.size() == 0)
			return null;
		// sql
		StringBuffer sql = new StringBuffer();

		sql.append(" update ");
		sql.append(tableName);
		sql.append(" set ");
		sql.append(set.substring(0, set.length() - 1));
		sql.append(" where ");
		sql.append(pkField);
		sql.append("=?");
		values.add(pkValue);
		SqlModel sqlModel = new SqlModel(sql.toString(), values.toArray());

		executeSql(sqlModel, boEnt);

		BoResult result = new BoResult();
		result.setAction(ACTION_TYPE.UPDATE);
		result.setBoEnt(boEnt);
		result.setPk(pkValue);
		
		String fkField = boEnt.getFk();
		if(row.containsKey(fkField)){
			String fkValue = row.get(fkField).toString();
			if(StringUtils.isNotEmpty(fkValue)&&!fkValue.equals("0")){
				result.setParentId(fkValue);
			}
		}

		return result;
	}

	/**
	 * 删除数据。
	 * 
	 * @param boEnt
	 * @param row
	 * @return
	 */
	private BoResult delete(BOEnt boEnt, String pk) {
		String sql = "delete  from " + boEnt.getTableName() + "  where " + boEnt.getPkKey() + " =? ";

		SqlModel sqlModel = new SqlModel(sql, new Object[] { pk });

		executeSql(sqlModel, boEnt);
		// 多对多的情况删除关联数据。
		if (boEnt.getType().equals(BaseBoDef.BOENT_RELATION.MANY_TO_MANY)) {
			sql = "delete from xbo_data_rel where SUB_BO_NAME='" + boEnt.getName() + "' and FK_=?";
			sqlModel = new SqlModel(sql, new Object[] { pk });
			executeSql(sqlModel, null);
		}

		// 删除表数据
		BoResult result = new BoResult();
		result.setAction(ACTION_TYPE.DELETE); //实体表中的数据被删除，则删除对应的bpm_bus_link里的数据
		result.setBoEnt(boEnt);
		result.setPk(pk);

		return result;
	}

	/**
	 * 根据主键递归获取数据。
	 */
	@Override
	public BoData getResById(Object id, String bodefCode) {
		BODef boDef = boDefManager.getByDefName(bodefCode);

		BoData boData = new BoData();
		BOEnt boEnt = (BOEnt) boDef.getBoEnt();

		Map<String, Object> row = getById(boEnt, id);
		boData.setData(row);

		getCascadeById(id, boEnt, boData);

		return boData;
	}

	/**
	 * 递归调用。
	 * 
	 * @param id
	 * @param boEnt
	 * @param boData
	 */
	private void getCascadeById(Object id, BOEnt boEnt, BoData boData) {
		// 子表处理
		List<BaseBoEnt> childEntList = boEnt.getChildEntList();

		if (BeanUtils.isEmpty(childEntList))
			return;

		/**
		 * 子表处理。
		 */
		for (BaseBoEnt childEnt : childEntList) {
			List<Map<String, Object>> list = getByFk((BOEnt) childEnt, id);
			String key = childEnt.getName();

			List<BoData> listData = new ArrayList<BoData>();

			for (Map<String, Object> rowMap : list) {
				BoData childData = new BoData();
				Map<String, Object> rtnMap = convertDbToData((BOEnt) childEnt, rowMap);
				childData.setData(rtnMap);
				listData.add(childData);

				String childId = (String) rowMap.get(childEnt.getPkKey());
				// 递归
				getCascadeById(childId, (BOEnt) childEnt, childData);
			}
			boData.setSubList(key, listData);
		}

	}

	@Override
	public String saveType() {
		return BodefConstants.SAVE_MODE_DB;
	}

	@Override
	public void removeBoData(String boCode, String[] aryIds) {
		BODef boDef = boDefManager.getByDefName(boCode);
		BOEnt boEnt = (BOEnt) boDef.getBoEnt();
		List<BaseBoEnt> childEntList = boEnt.getChildEntList();

		for (String id : aryIds) {
			delete(boEnt, id);
			
			for (BaseBoEnt child : childEntList) {
				String sql = "delete  from " + child.getTableName() + "  where " + child.getFk() + " =? ";
				SqlModel sqlModel = new SqlModel(sql, new Object[] { id });
				
				executeSql(sqlModel, boEnt);
			}
		}
	}

	@Override
	public List<Map<String, Object>> getList(String boCode, Map<String, Object> param) {
		BODef boDef = boDefManager.getByDefName(boCode);
		BOEnt boEnt = (BOEnt)boDef.getBoEnt();
		StringBuffer sql = new StringBuffer("select * from "+boEnt.getTableName());
		
		List<Object> p = new ArrayList<Object>();
		if(BeanUtils.isNotEmpty(param)){
			sql.append(" where 1=1 ");
			for (String fieldName : param.keySet()) {
				String filedName = fieldName.toLowerCase();
				// 如果param Name为某个字段。则拼接where 条件
				if(boEnt.getAttrFieldMap().containsKey(filedName)){
					sql.append(" and "+filedName+"=? ");
					p.add(param.get(fieldName));
				}
			}
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), p.toArray());
		
		// 数据转换
		List<Map<String, Object>> returnData = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> rowMap : list) {
			Map<String, Object> rtnMap = convertDbToData(boEnt, rowMap);
			returnData.add(rtnMap);
		}
		
		return returnData;
	}
	
	/**
	 * 查询数据  
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageList<Map<String, Object>> getList(String boCode,
			QueryFilter queryFilter) {
		Map<String, Object> params = queryFilter.getParams();
		
		BODef boDef = boDefManager.getByDefName(boCode);
		BOEnt boEnt = (BOEnt)boDef.getBoEnt();
		StringBuffer sb = new StringBuffer(" select * from " + boEnt.getTableName());
		
		// 数据转换
    	PageList<Map<String, Object>> returnData = new PageList<Map<String,Object>>();
    	// 处理分页
		PageList<Map<String, Object>> list = commonDao.queryForListPage(sb.toString(), queryFilter, params);
    	returnData.setPageResult(list.getPageResult());
		for (Map<String, Object> rowMap : list) {
			Map<String, Object> rtnMap = convertDbToData(boEnt, rowMap);
			returnData.add(rtnMap);
		}
		return returnData;
	}

}
