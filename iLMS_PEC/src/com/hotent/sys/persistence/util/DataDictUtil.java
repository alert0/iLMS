package com.hotent.sys.persistence.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.persistence.entity.DataDictItemInfo;
import com.hotent.sys.persistence.entity.TextValueEntity;
import com.hotent.sys.persistence.manager.DataDictManager;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.persistence.model.DataDict;

public class DataDictUtil
{
	private static String dataDictey = "dataDictCachekey";

	public static  DataDictItemInfo getDataDictInfo(String typeName)
	{
		Map<String, DataDictItemInfo> map = getCacheDataDicMap();
		if(map.containsKey(typeName))
			return map.get(typeName);
		return null;

	}

	public static List<DataDictItemInfo> getListDataDictInfoList()
	{

		Map<String, DataDictItemInfo> map = getCacheDataDicMap();
		List<DataDictItemInfo> list = new ArrayList<DataDictItemInfo>();
		for (DataDictItemInfo item : map.values())
		{
			list.add(item);
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public static Map<String, DataDictItemInfo> getCacheDataDicMap()
	{
		ICache<Object> iCache = AppUtil.getCache();
		if (iCache.containKey(dataDictey))
		{
			return (Map<String, DataDictItemInfo>) iCache.getByKey(dataDictey);
		}

		Map<String, DataDictItemInfo> newMap = getNewMapDict();
		if (newMap.size() > 0)
		{
			iCache.add(dataDictey, newMap);
		}
		return newMap;
	}

	private static Map<String, DataDictItemInfo> getNewMapDict()
	{

		Map<String, DataDictItemInfo> map = new HashMap<String, DataDictItemInfo>();
		SysTypeManager sysTypeManager = AppUtil.getBean(SysTypeManager.class);
		DataDictManager dataDictManager = AppUtil.getBean(DataDictManager.class);
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.setPage(new DefaultPage(1, 10000));
		queryFilter.addFilter("type_group_key_", "DIC", QueryOP.EQUAL);
		List<SysType> listType = sysTypeManager.query(queryFilter);
		for (SysType sysType : listType)
		{
			String typeName = sysType.getName();

			DataDictItemInfo item = new DataDictItemInfo();
			item.configType = typeName;

			queryFilter = new DefaultQueryFilter();
			queryFilter.setPage(new DefaultPage(1, 10000));
			queryFilter.addFilter("type_id_", sysType.getId(), QueryOP.EQUAL);
			List<DataDict> listDic = dataDictManager.query(queryFilter);
			List<TextValueEntity> listTextValue = new ArrayList<TextValueEntity>();
			for (DataDict dataDict : listDic)
			{
				TextValueEntity entity = new TextValueEntity();
				entity.text = dataDict.getName();
				entity.value = dataDict.getKey();
				
				entity.id=dataDict.getId();
				entity.typeId=dataDict.getTypeId();
				entity.key=dataDict.getKey();
				entity.name=dataDict.getName();
				entity.parentId=dataDict.getParentId();
				listTextValue.add(entity);
			}
			item.data = listTextValue;

			if (map.containsKey(typeName))
				map.remove(typeName);
			map.put(typeName, item);
		}
		return map;

	}

	public static boolean clearDataDicCache()
	{
		ICache<Object> iCache = AppUtil.getCache();
		if (iCache.containKey(dataDictey))
			iCache.delByKey(dataDictey);
		return true;
	}
}
