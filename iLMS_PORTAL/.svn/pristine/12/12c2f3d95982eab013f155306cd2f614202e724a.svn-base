package com.hotent.sys.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.DataDict;

public interface DataDictManager extends Manager<String, DataDict>{
	/**
	 * 通过数据字典类别查询所有的数据字典项
	 * @param typeId
	 */
	List<DataDict> getByTypeId(String typeId);
	
	/**
	 * 通过数据字典类别查询所有的数据字典项--MM_PUB_DATA_DICT表
	 * @param typeId
	 */
	List<DataDict> getInfoByTypeId(String typeId);
	/**
	 * 通过 类型，和字典key 获取字典项
	 */
	DataDict getByDictKey(String typeId, String key);
	/**
	 * 通过字典key 类型获取字典项--MM_PUB_DATA_DICT表
	 * @param typeId
	 * @param key
	 * @return
	 */
	DataDict getInfoByDictKey(String typeId, String key);
	/**
	 * 通过父节点获取子节点（包含二级子节点）
	 * @param parentId
	 * @return
	 */
	List<DataDict> getChildrenByParentId(String parentId);

	void delByDictTypeId(String id);
	

	/**
	 * 更新排序  sn
	 * @param dicId
	 * @param sn
	 */
	void updSn(String dicId, int sn);
	
	/**
	 * 通过父节点ID获取一级子节点
	 */
	List<DataDict> getFirstChilsByParentId(String parentId);
}
