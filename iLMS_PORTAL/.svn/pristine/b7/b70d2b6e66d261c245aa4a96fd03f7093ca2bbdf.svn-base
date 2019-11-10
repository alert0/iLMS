package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.persistence.dao.SysTypeDao;


@Repository
public class SysTypeDaoImpl extends MyBatisDaoImpl<String, SysType> implements SysTypeDao{

    @Override
    public String getNamespace() {
        return SysType.class.getName();
    }

	@Override
	public List<SysType> getByParentId(Long parentId) {
		 return getByKey("getByParentId", buildMap("parentId", parentId));
	}

	/**
     * 判断typeKey是否已经存在
     */
	@Override
	public boolean isKeyExist(String id, String typeGroupKey, String typeKey) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("typeGroupKey", typeGroupKey);
		params.put("typeKey", typeKey);
		Integer sn=(Integer)getOne("isKeyExist", params);
		return sn>0?true:false;
	}

	@Override
	public List<SysType> getByGroupKey(String groupKey, String currUserId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("groupKey", groupKey);
		params.put("currUserId", currUserId);
		 return getByKey("getByGroupKey", params);
	}
	/**
	 * 不包含本身
	 */
	@Override
	public List<SysType> getByPath(String path) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("path", path);
		return this.getByKey("getByPath", params);
	}

	@Override
	public List<SysType> getPrivByPartId(String parentId, String userId) {
		Map<String, String> params =  new HashMap<String, String>();
		params.put("parentId", parentId);
		params.put("userId", userId);
		return this.getByKey("getPrivByPartId", params);
	}

	/**
	 * 更新排序  sn
	 * @param typeId
	 * @param sn
	 */
	@Override
	public void updSn(String typeId, int sn) {
		Map<String, String> params =  new HashMap<String, String>();
		params.put("typeId", typeId);
		params.put("sn", String.valueOf(sn));
		this.updateByKey("updSn", params);
	}

	@Override
	public List<SysType> getTypesByParentId(String groupKey, String parentId) {
		Map<String, String> params =  new HashMap<String, String>();
		params.put("parentId", parentId);
		params.put("groupKey",groupKey);
		return this.getByKey("getTypesByParentId", params);
	}

	@Override
	public SysType getByTypeKeyAndGroupKey(String groupKey, String typeKey) {
		Map<String, String> params =  new HashMap<String, String>();
		params.put("typeKey", typeKey);
		params.put("groupKey",groupKey);
		return this.getUnique("getByTypeKeyAndGroupKey", params);
	}

	@Override
	public SysType getByTypeKey(String typeKey) {
		return this.getUnique("getByKey", typeKey);
	}

	/**
	 * 获取数据字典MM_PUB_DATA_DICT填充下拉框
	 */
	@Override
	public List queryPubDataDictByCodeType(String typeKey) {
		Map<String, String> map =  new HashMap<String, String>();
		map.put("typeKey", typeKey);
		return this.getList("queryPubDataDictByCodeType", map);
	}

	/**
	 * 分页查询分类组数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午6:17:31
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public PageList<Map<String, Object>> querySysTypeGroup(Map<String, Object> paramMap, Page page) {
		return this.getByKey("querySysTypeGroup", paramMap, page);
	}

	/**
	 * 分页查询系统分类数据信息
	 * @param paramMap
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月24日 下午6:17:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> querySysTypeByGroupKey(Map<String, Object> paramMap, Page page) {
		return this.getByKey("querySysTypeByGroupKey", paramMap, page);
	}
	
}

