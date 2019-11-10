package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.SysParamDao;
import com.hanthink.gps.pub.vo.DataDictVO;

public class SysParamDaoImpl extends BaseDaoSupport implements SysParamDao {

	/**
	 * 查询系统参数
	 */
	public String querySysParam(String paramCode) {
		return (String) this.executeQueryForObject("pub.select_queryPubSysParamByCode", paramCode);
	}

	/**
	 * 查询数据字典信息 
	 */
	@SuppressWarnings("unchecked")
	public List<DataDictVO> queryDictMapByCodeType(String codeType) {
		return (List<DataDictVO>) this.executeQueryForList("pub.select_queryDictMapByCodeTypeForList", codeType);
	}

	
}
