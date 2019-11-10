package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.DataDictVO;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SystemParamVO;

public interface DataDictDao {

	PageObject queryDataDictForPage(DataDictVO dDictVO, int start, int limit);
	
	//删除
	int deleteDataDict(DataDictVO dDictVO);
	
	
	
	
    //新增
	
	DataDictVO insertDataDict(DataDictVO dDictVO);


	

    //修改
	

	Object updateDataDict(DataDictVO dDictVO);

	
	
	
	//查询系统参数

	PageObject querySysParam(SystemParamVO sysParamVO, int start, int limit);
	
	
	//修改系统参数值

	Object updateSysParam(SystemParamVO sysParamVO);

	PageObject queryValueForPage(String codeType, int start, int limit);

	Object addValue(DataDictVO dDictVO);

	void deleteValue(DataDictVO dataDictVO, String[] codeValueArr);

}
