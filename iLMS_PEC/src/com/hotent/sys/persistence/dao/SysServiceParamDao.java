package com.hotent.sys.persistence.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.sys.persistence.model.SysServiceParam;
 


public interface SysServiceParamDao extends Dao<String, SysServiceParam> {
	/**
	 * 根据外键获取子表明细列表
	 * @param setId
	 * @return
	 */
	public List<SysServiceParam> getByMainId(String setId);
	
	/**
	 * 根据外键删除子表记录
	 * @param setId
	 * @return
	 */
	public void delByMainId(String setId);
	
}
