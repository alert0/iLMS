package com.hanthink.gps.pub.dao.impl;




import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.DataDictDao;
import com.hanthink.gps.pub.vo.DataDictVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SystemParamVO;

public class DataDictDaoImpl extends BaseDaoSupport implements DataDictDao {

	
	/**
	 * 查询
	 * @param menuVO
	 * @return int
	 */
	public PageObject queryDataDictForPage(DataDictVO dDictVO, int start,
			int limit) {
		return this.executeQueryForPage("pub.select_queryDataDict", dDictVO, start, limit);
	}

	
	
	/**
	 * 删除
	 * @param menuVO
	 * @return int
	 */
	public int deleteDataDict(DataDictVO dDictVO) {
		return this.executeDelete("pub.delete_deleteDataDict", dDictVO);
		
	
	}

	/**
	 * 新增
	 * @param menuVO
	 * @return int
	 */
	public DataDictVO insertDataDict(DataDictVO dDictVO) {
		return (DataDictVO) this.executeInsert("pub.insert-insertDataDict",dDictVO);
	}


	/**
	 * 修改
	 */
	public Object updateDataDict(DataDictVO dDictVO) {
		return executeUpdate("pub.update-updateDataDict", dDictVO);
	}


	/**
	 * 查询系统参数
	 */
	public PageObject querySysParam(SystemParamVO sysParamVO, int start,
			int limit) {
		return this.executeQueryForPage("pub.select_querySysParam", sysParamVO, start, limit);
	}


	/**
	 * 修改系统参数值
	 */
	public Object updateSysParam(SystemParamVO sysParamVO) {
		
		return executeUpdate("pub.update_updateSysUpdate",sysParamVO);
		
	}



	public PageObject queryValueForPage(String codeType, int start, int limit) {
		return this.executeQueryForPage("pub.select_ValueForPage", codeType, start,limit);
	}



	public Object addValue(DataDictVO dDictVO) {
		return  this.executeInsert("pub.insert_addValue",dDictVO);
	}



	public void deleteValue(DataDictVO dataDictVO, String[] codeValueArr) {
		// TODO Auto-generated method stub
		
	}



	
}





	

	




