package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysServiceParamDao;
import com.hotent.sys.persistence.model.SysServiceParam;


@Repository

public class SysServiceParamDaoImpl extends MyBatisDaoImpl<String, SysServiceParam> implements SysServiceParamDao{

    @Override
    public String getNamespace() {
        return SysServiceParam.class.getName();
    }
	/**
	 * 根据外键获取子表明细列表
	 * @param setId
	 * @return
	 */
	public List<SysServiceParam> getByMainId(String setId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("setId", setId);
		return this.getByKey("getSysServiceParamList", params);
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param setId
	 * @return
	 */
	public void delByMainId(String setId) {
		Map<String,Object>params=new HashMap<String, Object>();
		params.put("setId", setId);
		this.deleteByKey("delByMainId", params);
	}
	
	
}

