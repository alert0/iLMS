package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.DataDictVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SystemParamVO;

public interface DataDictService extends BaseService {
	/**
	 * 查询数据字典信息
	 * @param dDictVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */

	PageObject queryDataDictForPage(DataDictVO dDictVO, int start, int limit);
	
	
	/**
	 * 删除数据
	 * @param dDictVO
	 * @return 
	 */

	void deleteDataDict(DataDictVO dDictVO);

	
	
	/**
	 * 修改数据字典 
	 * @param           
	 * @return 
	 */
	
	void updateDateDict(DataDictVO dDictVO);

	/**
	 * 新增数据字典 
	 * @param           
	 * @return 
	 */
	void insertDataDict(DataDictVO dDictVO);


	
	
	/**
	 * 查询系统参数
	 * @param           
	 * @return 
	 */
	PageObject querySysParam(SystemParamVO sysParamVO, int start, int limit);

	

	/**
	 * 修改
	 * 系统参数的值
	 * @param           
	 * @return 
	 */

	void updateSysParam(SystemParamVO sysParamVO);


	/**
	 * 查询数据字典编码
	 * @param codeType
	 * @param start
	 * @param limit
	 * @return
	 * smy 2016-5-12
	 */
	PageObject queryValueForPage(String codeType, int start, int limit);

	/**
	 * 新增数据字典值
	 * @param dictMenuVO
	 * smy 2016-5-12
	 */
	void addValue(DataDictVO dDictVO);


	void deleteValue(DataDictVO dataDictVO, String[] codeValueArr);
    

	


	


 
}
