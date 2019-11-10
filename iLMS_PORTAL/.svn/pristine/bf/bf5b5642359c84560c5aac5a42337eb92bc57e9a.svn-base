package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.DataDictDao;
import com.hotent.sys.persistence.model.DataDict;


@Repository

public class DataDictDaoImpl extends MyBatisDaoImpl<String, DataDict> implements DataDictDao{

    @Override
    public String getNamespace() {
        return DataDict.class.getName();
    }

	@Override
	public List<DataDict> getByTypeId(String typeId) {
		return this.getByKey("getByTypeId", typeId);
	}

	@Override
	public DataDict getByDictKey(String typeId, String key) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId);
		params.put("key", key);
		return this.getUnique("getByDictKey", params);
	}

	@Override
	public List<DataDict> getByParentId(String parentId) {
		return this.getByKey("getByParentId", parentId); 
	}

	@Override
	public void delByDictTypeId(String dictTypeId) {
		this.deleteByKey("delByDictTypeId", dictTypeId);
		
	}
	
	/**
	 * 更新排序  sn
	 * @param dicId
	 * @param sn
	 */
	@Override
	public void updSn(String dicId, int sn) {
		Map<String, String> params =  new HashMap<String, String>();
		params.put("dicId", dicId);
		params.put("sn", String.valueOf(sn));
		this.updateByKey("updSn", params);
	}

	@Override
	public List<DataDict> getInfoByTypeId(String typeId) {
		return this.getByKey("getInfoByTypeId", typeId);
	}

	@Override
	public DataDict getInfoByDictKey(String typeId, String key) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId);
		params.put("key", key);
		return this.getUnique("getInfoByDictKey", params);
	}
	
}

