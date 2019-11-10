package com.hotent.portal.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysIndexColumnDao;
import com.hotent.portal.persistence.model.SysIndexColumn;

/**
 * <pre>
 * 对象功能:首页栏目 Dao类
 * 开发公司:广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysIndexColumnDaoImpl extends MyBatisDaoImpl<String, SysIndexColumn> implements SysIndexColumnDao{
	@Override
	public String getNamespace() {
		return SysIndexColumn.class.getName();
	}

	/**
	 * 通过别名获取栏目
	 * 
	 * @param columnAlias
	 * @return
	 */
	public SysIndexColumn getByColumnAlias(String alias) {
		return (SysIndexColumn) getOne("getByColumnAlias", alias);
	}

	/**
	 * 获取当前用户有权限的栏目
	 * @param params
	 * @return
	 */
	@Override
	public List<SysIndexColumn> getByUserIdFilter(Map<String, Object> params) {
		return this.getByKey("getByUserIdFilter",params);
	}

	public Integer getCounts() {
		return (Integer)this.getOne("getCounts", null);
	}

	@Override
	public Boolean isExistAlias(String alias, String id) {
		Map<String,Object> map=new HashMap<String,Object>();
    	map.put("alias", alias);
    	map.put("id", id);
    	Integer count=(Integer)this.getOne("isExistAlias", map);
    	return count>0;
	}


}