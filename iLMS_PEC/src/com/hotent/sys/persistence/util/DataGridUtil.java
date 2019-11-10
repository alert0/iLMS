package com.hotent.sys.persistence.util;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.hotent.base.api.db.IViewOperator;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.db.model.Table;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.table.BaseTableMeta;
import com.hotent.base.db.table.model.DefaultColumn;
import com.hotent.base.db.table.model.DefaultTable;
import com.hotent.base.db.table.util.MetaDataUtil;
import com.hotent.sys.persistence.manager.SysDataGridFieldManager;
import com.hotent.sys.persistence.manager.SysDataGridManager;
import com.hotent.sys.persistence.model.SysDataGrid;
import com.hotent.sys.persistence.model.SysDataGridField;
import com.hotent.sys.persistence.model.SysDataGridFieldInfo;
import com.hotent.sys.util.ContextUtil;

public class DataGridUtil
{

	/**
	 * 获取字段定义列表
	 * 
	 * @param gridName
	 * @return
	 */
	@SuppressWarnings(
	{ "unused", "unchecked" })
	public static List<SysDataGridFieldInfo> getListSysGridConfigByGridName(String gridName)
	{
		ICache<Object> iCache = AppUtil.getCache();

		if (iCache.containKey(gridName))
		{
			return (List<SysDataGridFieldInfo>) iCache.getByKey(gridName);
		}
		List<SysDataGridFieldInfo> listConfigs = getNewListSysGridConfigs(gridName);
		if (!iCache.containKey(gridName) && listConfigs.size() > 0)
			iCache.add(gridName, listConfigs);
		return listConfigs;
	}

	/**
	 * 获取一字段字义
	 * 
	 * @param gridName
	 * @param field
	 * @return
	 */
	public static SysDataGridFieldInfo getSysDatagridFieldInfo(String gridName, String field)
	{
		List<SysDataGridFieldInfo> lConfigs = getListSysGridConfigByGridName(gridName);
		for (SysDataGridFieldInfo info : lConfigs)
		{
			String sfield = info.getField();
			if (sfield.equals(field))
			{
				return info;
			}
		}
		return null;
	}

	/**
	 * 获取一字段定义的数据库字段
	 * 
	 * @param gridName
	 * @param field
	 * @return
	 */
	public static String getDbField(String gridName, String field)
	{
		List<SysDataGridFieldInfo> lConfigs = getListSysGridConfigByGridName(gridName);
		for (SysDataGridFieldInfo sysGridConfig : lConfigs)
		{
			String sfield = sysGridConfig.getField();
			if (sfield.equals(field))
			{
				String dbfield = sysGridConfig.getDbfield();
				return !dbfield.equals("") && dbfield != null ? dbfield : field;
			}

		}
		return field;

	}

	public static Boolean isExistGridName(String gridName)
	{
		ICache<Object> iCache = AppUtil.getCache();
		return iCache.containKey(gridName);
	}

	public static boolean clearDataGridCache(String gridName)
	{
		ICache<Object> iCache = AppUtil.getCache();
		if (iCache.containKey(gridName))
			iCache.delByKey(gridName);
		return true;
	}

	/**
	 * 从数据库中记取全新的表头配置
	 * 
	 * @param gridName
	 * @return
	 */
	private static List<SysDataGridFieldInfo> getNewListSysGridConfigs(String gridName)
	{
		SysDataGridFieldManager sysDatagridFieldManager = AppUtil.getBean(SysDataGridFieldManager.class);
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.setPage(new DefaultPage(1, 10000));
		queryFilter.addFilter("grid_name_", gridName, QueryOP.EQUAL);
		List<SysDataGridFieldInfo> sysGridConfigList = sysDatagridFieldManager.queryListInfo(queryFilter);
		return sysGridConfigList;
	}
 
	

	/**
	 * 根据gridName获取表结构
	 * 
	 * @param gridName
	 * @return
	 * @throws Exception
	 */
	public static Table getDataGridTableStructure(String gridName) throws Exception
	{
		SysDataGridManager sysDatagridManager = AppUtil.getBean(SysDataGridManager.class);
		SysDataGrid sysDataGrid = sysDatagridManager.getByGridName(gridName);
		String dataSourceType = sysDataGrid.getDataSourceType();
		// 表名，视图名，SQL语句
		String dataSource = sysDataGrid.getDataSource();

		String dbType = PropertyUtil.getJdbcType();// 默认数据源类型，mysql,orcale,sqlserver
		DbContextHolder.setDataSource(dataSource, dbType);// 转换这次进程的数据源

		Table table = null;
		// 表
		if (dataSourceType.equals("table"))
		{
			BaseTableMeta baseTableMeta = MetaDataUtil.getBaseTableMetaAfterSetDT(dbType);// 获取表操作元
			table = baseTableMeta.getTableByName(dataSource.trim());
		} else if (dataSourceType.equals("viewtable"))
		{
			IViewOperator iViewOperator = MetaDataUtil.getIViewOperatorAfterSetDT(dbType);
			table = iViewOperator.getModelByViewName(dataSource.trim());
		} else if (dataSourceType.equals("sql"))
		{

			JdbcTemplate jdbcTemplate = AppUtil.getBean(JdbcTemplate.class);
			String sql = sysDataGrid.getDataSource();
			if (StringUtil.isEmpty(sql))
				return null;
			table = new DefaultTable();
			table.setTableName("defaultTable");
			SqlRowSet srs = jdbcTemplate.queryForRowSet(sql);// 得到sql的结果集
			SqlRowSetMetaData srsmd = srs.getMetaData();
			for (int i = 1; i < srsmd.getColumnCount() + 1; i++)
			{
				Column column = new DefaultColumn();
				column.setFieldName(srsmd.getColumnName(i));
				column.setComment(srsmd.getColumnName(i));
				column.setColumnType(srsmd.getColumnTypeName(i));
				table.addColumn(column);
			}

		}
		return table;
	}

	/**
	 * 根据gridName增量创建表头配置
	 * @param gridName
	 * @return
	 * @throws Exception
	 */
	public static boolean createDataGridColumn(String gridName) throws Exception
	{
		SysDataGridManager sysDatagridManager = AppUtil.getBean(SysDataGridManager.class);
		SysDataGridFieldManager sysDatagridFieldManager = AppUtil.getBean(SysDataGridFieldManager.class);
		List<SysDataGridFieldInfo> listOle = getNewListSysGridConfigs(gridName);

		Table table = DataGridUtil.getDataGridTableStructure(gridName);
		if(table==null)return false;
		SysDataGrid sysDatagrid = sysDatagridManager.getByGridName(gridName);
		String gridId = sysDatagrid.getGridid();
		List<Column> list = table.getColumnList();
		for (Column column : list)
		{
			boolean isExist = false;
			String name = !sysDatagrid.getDataSourceType().equals("sql") ? StringUtil.convertDbFieldToField(column.getFieldName()) : column.getFieldName();
			String title = StringUtil.isEmpty(column.getComment()) ? name : column.getComment();
			for (SysDataGridFieldInfo oleFiled : listOle)
			{
				if (name.equals(oleFiled.getField()))
				{
					isExist = true;
					break;
				}
			}
			if (isExist)
				continue;
			SysDataGridField field = new SysDataGridField();
			field.setTitle(column.getComment());
			field.setField(name);
			field.setAlign("center");
			field.setCreator("admin");
			 field.setCreator(ContextUtil.getCurrentUser().getAccount());
			//field.setDatatype(column.getColumnType().toLowerCase());
			field.setDbfield(column.getFieldName());
			field.setEdit(false);
			field.setGridId(gridId);
			field.setHidden(false);
			field.setId(UniqueIdUtil.getSuid());
			field.setOrderNum(1000);
			field.setResizable(true);
			field.setIspk(column.getIsPk());
			field.setWidth(120);
			sysDatagridFieldManager.create(field);

		}
		return true;
	}
	

	
}
