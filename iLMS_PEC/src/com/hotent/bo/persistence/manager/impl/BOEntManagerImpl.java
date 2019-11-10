package com.hotent.bo.persistence.manager.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import com.hotent.base.api.db.ITableOperator;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.db.model.Table;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.api.IDynamicDatasource;
import com.hotent.base.db.datasource.DataSourceUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.table.BaseTableMeta;
import com.hotent.base.db.table.util.MetaDataUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.api.bodef.IBoEntHandler;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.persistence.dao.BOEntDao;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.manager.BOEntManager;
import com.hotent.bo.persistence.manager.BOEntRelManager;
import com.hotent.bo.persistence.manager.BoAttributeManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BOEntRel;
import com.hotent.bo.persistence.model.BoAttribute;

/**
 * 
 * <pre>
 * 描述：业务对象定义 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bOEntManager")
public class BOEntManagerImpl extends AbstractManagerImpl<String, BOEnt> implements BOEntManager {
	@Resource
	BOEntDao bOEntDao;
	@Resource
	ITableOperator tableOperator;
	@Resource
	BoAttributeManager boAttributeManager;

	@Resource
	BOEntRelManager boEntRelManager;
	@Resource
	BODefManager boDefManager;
	@Resource
	IDynamicDatasource dynamicDatasource;

	@Override
	protected Dao<String, BOEnt> getDao() {
		return bOEntDao;
	}

	@Override
	public void createTable(BOEnt boEnt) throws SQLException {

		BaseAttribute colPk = new BaseAttribute();
		colPk.setName(BaseBoEnt.PK_NAME);
		colPk.setFieldName(BaseBoEnt.PK_NAME);
		colPk.setIsPk(true);
		colPk.setCharLen(40);
		colPk.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colPk.setComment("主键");

		BaseAttribute colFk = new BaseAttribute();
		colFk.setName(BaseBoEnt.FK_NAME);
		colFk.setFieldName(BaseBoEnt.FK_NAME);
		colFk.setIsPk(false);
		colFk.setCharLen(40);
		colFk.setColumnType(Column.COLUMN_TYPE_VARCHAR);
		colFk.setComment("外键");

		List<BoAttribute> colList = boEnt.getColumnList();
		for (BoAttribute col : colList) {
			col.setFieldName(col.getFieldName());
		}

		boEnt.addAttrFirst((BaseAttribute) colFk);
		boEnt.addAttrFirst((BaseAttribute) colPk);

		tableOperator.createTable((Table) boEnt);

		boEnt.setIsCreateTable((short) 1);// 更新生成表的状态
		update(boEnt);
	}

	@Override
	public void save(String json) throws Exception {
		// 开始处理boEnt对象
		BOEnt bOEnt = GsonUtil.toBean(json, BOEnt.class);
		BOEnt dbBoEnt =null;//数据库的旧对象，更新时才有值
		BOEnt oldBoEnt = null;//更新时保存更新前的实体，方便创建表失败后还原
        List<BoAttribute> oldAttrList = new ArrayList<BoAttribute>();//更新前实体属性，方便创建表失败后还原
		if (StringUtil.isEmpty(bOEnt.getId())) {
			if(bOEntDao.getByName(bOEnt.getName())!=null){
				throw new Exception("别名:"+bOEnt.getName()+" 已存在");
			}
			
			bOEnt.setId(UniqueIdUtil.getSuid());
			this.create(bOEnt);
		} else {
			BOEnt temp=bOEntDao.getByName(bOEnt.getName());//数据库用这个名字的对象
			if(temp!=null&&!temp.getId().equals(bOEnt.getId())){
				throw new Exception("别名:"+bOEnt.getName()+" 已被使用，更新失败");
			}
			
			dbBoEnt = getById(bOEnt.getId());
			oldBoEnt = this.get(bOEnt.getId());
			oldAttrList = boAttributeManager.getByEntId(bOEnt.getId());
			this.update(bOEnt);
			boAttributeManager.removeByEntId(bOEnt.getId());// 先删除旧的属性列表
		}

		// 开始处理字段attr对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		JSONArray attrListJA = jsonObject.getJSONArray("attributeList");
		List<BoAttribute> newAttrList = new ArrayList<BoAttribute>();// 新字段
		for (int i = 0; i < attrListJA.size(); i++) {
			BoAttribute boAttribute = GsonUtil.toBean(attrListJA.getJSONObject(i), BoAttribute.class);
			boAttribute.setBoEnt(bOEnt);
			if (StringUtil.isEmpty(boAttribute.getId())) {// 新字段
				newAttrList.add(boAttribute);
				boAttribute.setId(UniqueIdUtil.getSuid());
				boAttribute.setEntId(bOEnt.getId());
				boAttribute.setBoEnt(bOEnt);
			}
			boAttribute.setTableName(bOEnt.getTableName());
			boAttributeManager.create(boAttribute);
		}

		// 开始处理物理表
		if (!bOEnt.isCreatedTable() || bOEnt.isExternal()) {// 没生成表或外部表就不处理
			return;
		}
		if (getCanEditByName(bOEnt.getName()) == 0) {// 处于任意修改的状态
			if(dbBoEnt!=null){
				tableOperator.dropTable(dbBoEnt.getTableName());// 删除旧表
			}
			bOEnt = getById(bOEnt.getId());
			try {
                createTable(bOEnt);// 新增表
            } catch (Exception e) {
                if(BeanUtils.isNotEmpty(oldBoEnt)){
                    boAttributeManager.removeByEntId(oldBoEnt.getId());// 先删除旧的属性列表
                    for (BoAttribute boAttribute : oldAttrList) {//还原之前的属性
                         boAttributeManager.create(boAttribute);
                    }
                    this.update(oldBoEnt);//还原之前的实体
                    createTable(oldBoEnt);// 还原之前的表结构
                    throw new Exception("生成表失败："+ExceptionUtils.getRootCause(e).getMessage());
                }
            }
		} else {// 已生成表，又不是任意修改，那么就应该在表结构插入新字段
			for (BoAttribute attr : newAttrList) {
				tableOperator.addColumn(bOEnt.getTableName(), attr);
			}
		}
	}

	@Override
	public void remove(String entityId) {
		BOEnt boEnt = get(entityId);

		// 验证实例是否已绑定定义
		String str = "";
		for (BOEntRel rel : boEntRelManager.getByEntId(entityId)) {
			BODef boDef = boDefManager.get(rel.getBoDefid());
			if (StringUtil.isNotEmpty(str)) {
				str += ",";
			}
			str += boDef.getDescription();
		}
		if (StringUtil.isNotEmpty(str)) {
			throw new RuntimeException("实体“" + boEnt.getDesc() + "”已绑定业务对象定义“" + str+"”，不能被删除！");
		}

		// 先处理字段attr
		boAttributeManager.removeByEntId(entityId);
		super.remove(entityId);

		boAttributeManager.removeByEntId(entityId);// 删除对应字段信息
		if (boEnt.isCreatedTable()&&!boEnt.isExternal()) {// 已生成物理表
			try {
				tableOperator.dropTable(boEnt.getTableName());
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public BOEnt getById(String entId) {
		BOEnt boEnt = get(entId);
		List list = boAttributeManager.getByEntId(entId);
		boEnt.setBoAttrList(list);
		return boEnt;
	}

	@Override
	public BOEnt getByName(String name) {
		BOEnt boEnt = bOEntDao.getByName(name);
		if(BeanUtils.isNotEmpty(boEnt)){
			List list = boAttributeManager.getByEntId(boEnt.getId());
			boEnt.setBoAttrList(list);
		}
		return boEnt;
	}

	@Override
	public int getCanEditByName(String name) {
		// 获取bo;
		Integer rtn = bOEntDao.getRefCountByName(name);
		if (rtn > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public void reloadByEntId(String entId) {
		try{
			BOEnt boEnt=bOEntDao.get(entId);
			String dsAlias=boEnt.getDsName();
			String tableName=boEnt.getTableName();
			//根据entId 获取属性列表
			List<BoAttribute> attrList = boAttributeManager.getByEntId(entId);
			//读取数据
			String dbType= dynamicDatasource.getDbTypeByAlias(dsAlias);
			DbContextHolder.setDataSource(dsAlias, dbType);// 转换这次进程的数据源
			
			BaseTableMeta baseTableMeta = MetaDataUtil.getBaseTableMetaAfterSetDT(dbType);// 获取表操作元
			// 在manager层切换不了数据源，暂时这样写
			baseTableMeta.setJdbcTemplate(DataSourceUtil.getJdbcTempByDsAlias(dsAlias));
			Table	table = baseTableMeta.getTableByName(tableName);
			
			//获取删除的属性列
			List<BoAttribute> removeList= getRemoveList(attrList,table.getColumnList());
			//获取添加的属性列
			List<BoAttribute> addList= getAddList(attrList,table.getColumnList(),boEnt);
			
			List<BoAttribute> updateList = getUpdateList(attrList,table.getColumnList(),boEnt);
			
			//修改boattr列表
			handleAttr(removeList,addList,updateList);
			//修改bpm_form_def ,bpm_form_field
			IBoEntHandler handler=AppUtil.getBean(IBoEntHandler.class);
			if(handler!=null){
				handler.handlerEntChange(boEnt, removeList, addList);
			}
		}catch(Exception e){
			
		}
	}
	
	void handleAttr(List<BoAttribute> removeList,List<BoAttribute> addList,List<BoAttribute> updateList){
		for(BoAttribute attr:removeList){
			boAttributeManager.remove(attr.getId());
		}
		for(BoAttribute attr:addList){
			boAttributeManager.create(attr);
		}
		for(BoAttribute attr:updateList){
			boAttributeManager.update(attr);
		}
		
	}
	
	private List<BoAttribute> getRemoveList(List<BoAttribute>  attrList,List<Column> columnList){
		Set<String> attrSet=new HashSet<String>();
		for(Column col:columnList){
			attrSet.add(col.getFieldName());
		}
		List<BoAttribute> list=new ArrayList<BoAttribute>();
		for(BoAttribute attr:attrList){
			if(!attrSet.contains( attr.getFieldName())){
				list.add(attr);
			}
		}
		return list;
	}
	
	private List<BoAttribute> getAddList(List<BoAttribute>  attrList,List<Column> columnList,BOEnt boEnt){
		Map<String,BoAttribute> attrMap=new HashMap<String,BoAttribute>();
		for(BoAttribute attr:attrList){
			attrMap .put(attr.getFieldName(), attr);
		}
		
		List<BoAttribute> list=new ArrayList<BoAttribute>();
		for(Column column:columnList){
			
			String fieldName=column.getFieldName();
			if(fieldName.equalsIgnoreCase(boEnt.getPk())) continue;
			
			if(StringUtil.isNotEmpty(boEnt.getFk()) 
					&& 	 fieldName.equalsIgnoreCase(boEnt.getFk())) continue;
			
			if(attrMap.containsKey(column.getFieldName())) continue;
			BoAttribute attr=getByColumn(column);
			
			attr.setBoEnt(boEnt);
			
			attr.setEntId(boEnt.getId());
			list.add(attr);
		}
		return list;
	}
	
	private List<BoAttribute> getUpdateList(List<BoAttribute>  attrList,List<Column> columnList,BOEnt boEnt){
		Set<String> attrSet=new HashSet<String>();
		Map<String,String> attrMap = new HashMap<String, String>();
		for(BoAttribute attr:attrList){
			attrSet.add(attr.getFieldName());
			attrMap.put(attr.getFieldName(), attr.getId());
		}
		List<BoAttribute> list=new ArrayList<BoAttribute>();
		for(Column column:columnList){
			if(attrSet.contains(column.getFieldName())){
				BoAttribute attrTmp = getByColumn(column,attrMap.get(column.getFieldName()));
				attrTmp.setBoEnt(boEnt);
				attrTmp.setEntId(boEnt.getId());
				list.add(attrTmp);
			}
		}
		return list;
	}
	
	private BoAttribute getByColumn(Column column){
		return getByColumn(column,null);
	}
	
	private BoAttribute getByColumn(Column column,String id){
		BoAttribute attr = new BoAttribute();
		attr.setId( id==null? UniqueIdUtil.getSuid():id);
		attr.setName(column.getFieldName());
		attr.setFieldName(column.getFieldName());
		attr.setIsPk(false);
		attr.setIsRequired(column.getIsNull()?0:1);
		String columnType=column.getColumnType();
		if(columnType.equals(Column.COLUMN_TYPE_VARCHAR)){
			attr.setAttrLength(column.getCharLen());
		}
		else if(columnType.equals(Column.COLUMN_TYPE_NUMBER)){
			attr.setAttrLength(column.getIntLen());
			attr.setDecimalLen(column.getDecimalLen());
		}
		
		attr.setFormat("");
		updateDateFormat(column.getFcolumnType(),attr);
		
		attr.setDataType(columnType);
		attr.setDesc(column.getComment());
		return attr;
	
	}

	private void updateDateFormat(String fcolumnType, BoAttribute attr) {
		if("datetime".equals(fcolumnType) || "timestamp".equals(fcolumnType) ){
			attr.setFormat("yyyy-MM-dd HH:mm:ss");
		}
		if("time".equals(fcolumnType)){
			attr.setFormat("HH:mm:ss");
		}
		if("date".equals(fcolumnType)){
			attr.setFormat("yyyy-MM-dd");
		}
	}
	
	

}
