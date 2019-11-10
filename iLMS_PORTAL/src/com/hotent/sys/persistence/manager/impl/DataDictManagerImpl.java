package com.hotent.sys.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.DataDictDao;
import com.hotent.sys.persistence.manager.DataDictManager;
import com.hotent.sys.persistence.model.DataDict;

@Service("dataDictManager")
public class DataDictManagerImpl extends AbstractManagerImpl<String, DataDict> implements DataDictManager{
	@Resource
	DataDictDao dataDictDao;
	@Override
	protected Dao<String, DataDict> getDao() {
		return dataDictDao;
	}
	@Override
	public List<DataDict> getByTypeId(String typeId) {
		return dataDictDao.getByTypeId(typeId);
	}
	@Override
	public DataDict getByDictKey(String typeId, String key) {
		return dataDictDao.getByDictKey(typeId,key);
	}
	
	@Override
	public void removeByIds(String... ids) {
		if(BeanUtils.isNotEmpty(ids)){
			for(String id : ids){
				dataDictDao.remove(id);
				List<DataDict> childs = getChildrenByParentId(id);
				for(DataDict dict : childs){
					dataDictDao.remove(dict.getId());
				}
				
			}
		}
		
		super.removeByIds(ids);
	}
	/**
	 * 获取一级子节点
	 * @param id
	 * @return
	 */
	@Override
	public List<DataDict> getFirstChilsByParentId(String id){
		return dataDictDao.getByParentId(id);
	}
	/**
	 * 获取所有的子节点
	 * @param id
	 * @return
	 */
	@Override
	public List<DataDict> getChildrenByParentId(String id){
		List<DataDict> childs = dataDictDao.getByParentId(id);
		return getChilds(childs);
	}
	/**
	 * 通过子节点查询子节点
	 * @param childs
	 * @return
	 */
	private List<DataDict> getChilds(List<DataDict> childs){
		List<DataDict> dataDict = new ArrayList<DataDict>();
		// 如果孩子不为空 查询孩子的孩子
		if(BeanUtils.isNotEmpty(childs)){
			for(DataDict dict : childs){
				List<DataDict> children =  dataDictDao.getByParentId(dict.getId());  
				//如果孩子的孩子们不为空  则通过孩子们 查询他们的孩子们
				if(BeanUtils.isNotEmpty(children)){
					children = getChilds(children);
					dataDict.addAll(children);
				}
				
			}
			dataDict.addAll(childs);
		}
		return dataDict;
	}
	@Override
	public void delByDictTypeId(String dictTypeId) {
		dataDictDao.delByDictTypeId(dictTypeId);
	}
	
	
	
	
	/**
	 * 更新排序  sn
	 * @param dicId
	 * @param sn
	 */
	@Override
	public void updSn(String dicId, int sn) {
		dataDictDao.updSn(dicId, sn);
	}
	@Override
	public List<DataDict> getInfoByTypeId(String typeId) {
		return dataDictDao.getInfoByTypeId(typeId);
	}
	@Override
	public DataDict getInfoByDictKey(String typeId, String key) {
		return dataDictDao.getInfoByDictKey(typeId, key);
	}

}
