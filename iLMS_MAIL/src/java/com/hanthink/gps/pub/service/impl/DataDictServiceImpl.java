package com.hanthink.gps.pub.service.impl;



import com.hanthink.gps.pub.dao.DataDictDao;
import com.hanthink.gps.pub.service.DataDictService;
import com.hanthink.gps.pub.vo.DataDictVO;

import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SystemParamVO;


public class DataDictServiceImpl extends BaseServiceImpl implements DataDictService {
    
	private DataDictDao dataDictDao;
	
	//查询
	public PageObject queryDataDictForPage(DataDictVO dDictVO, int start,
			int limit) {
		return dataDictDao.queryDataDictForPage(dDictVO, start, limit);
	}
	
	
	
	public DataDictDao getDataDictDao() {
		return dataDictDao;
	}
	
	public void setDataDictDao(DataDictDao dataDictDao) {
		this.dataDictDao = dataDictDao;
	}

	
	/**
	 * 删除
	 * @param dDictVO
	 * @return 
	 */

	public void deleteDataDict(DataDictVO dDictVO) {
		dataDictDao.deleteDataDict(dDictVO);
		
	}


	/**
	 * 新增
	 * @param dDictVO
	 * @return 
	 */

	public void insertDataDict(DataDictVO dDictVO) {
		dataDictDao.insertDataDict(dDictVO);
		
	}


	/**
	 * 修改
	 * @param dDictVO
	 * @return 
	 */

	public void updateDateDict(DataDictVO dDictVO) {
		dataDictDao.updateDataDict(dDictVO);
		
	}




	/**
	 * 查询系统参数
	 * @param 
	 * @return 
	 * @return 
	 */
	public PageObject querySysParam(SystemParamVO sysParamVO, int start,
			int limit) {
		return dataDictDao.querySysParam(sysParamVO, start, limit);
	}



	/**
	 * 查询系统参数
	 * @param dDictVO
	 * @return 
	 * @return 
	 */
	public void updateSysParam(SystemParamVO sysParamVO) {
		dataDictDao.updateSysParam(sysParamVO);
		
	}



	public PageObject queryValueForPage(String codeType, int start, int limit) {
		return dataDictDao.queryValueForPage(codeType, start, limit);
	}



	public void addValue(DataDictVO dDictVO) {
		dataDictDao.addValue(dDictVO);
		
	}



	public void deleteValue(DataDictVO dataDictVO, String[] codeValueArr) {
		dataDictDao.deleteValue(dataDictVO,codeValueArr);	
	}
	




}

	

	





	
		
	



	



	




	


