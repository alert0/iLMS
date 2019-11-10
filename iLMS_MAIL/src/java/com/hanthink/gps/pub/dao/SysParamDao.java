package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.DataDictVO;

public interface SysParamDao {

	/**
	 * 查询系统参数
	 * @param paramCode
	 * @return
	 */
	String querySysParam(String paramCode);

	/**
	 * 查询数据字典信息
	 * @param codeType
	 * @return
	 */
	List<DataDictVO> queryDictMapByCodeType(String codeType);

}
