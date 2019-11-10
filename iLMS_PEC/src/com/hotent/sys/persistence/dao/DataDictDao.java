package com.hotent.sys.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.DataDict;


public interface DataDictDao extends Dao<String, DataDict> {

	/**
	 * 通过字典类别查询所有的字典项
	 * @param typeId 
	 * @return
	 */
	List<DataDict> getByTypeId(String typeId);
	/**
	 * 通过字典类别查询所有的字典项--MM_PUB_DATA_DICT表
	 * @param typeId 
	 * @return
	 */
	List<DataDict> getInfoByTypeId(String typeId);
	/**
	 * 通过字典key 类型获取字典项
	 * @param typeId
	 * @param key
	 * @return
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
	 * 根据parentId 获取下一级节点
	 * @param id
	 * @return
	 */
	List<DataDict> getByParentId(String parentId);
	/**
	 * 根据类型删除所有字典
	 * @param dictTypeId
	 */
	void delByDictTypeId(String dictTypeId);
	/**
	 * 更新排序  sn
	 * @param dicId
	 * @param sn
	 */
	void updSn(String dicId, int sn);
}
