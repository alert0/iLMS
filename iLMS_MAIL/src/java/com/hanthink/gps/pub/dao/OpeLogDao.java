package com.hanthink.gps.pub.dao;

import java.util.HashMap;
import java.util.List;

import com.hanthink.gps.pub.vo.OpeLogVO;
import com.hanthink.gps.pub.vo.OpeTableVO;
import com.hanthink.gps.pub.vo.PageObject;

public interface OpeLogDao {

	public List<OpeTableVO> queryTableColumn(OpeTableVO opeTableVO);


	public void inserOpeLog(OpeLogVO opeLogVO);

	public List<Object> queryOpeObjRowList(HashMap<String, Object> map);


	/**
	 * 
	 *查询操作日志 
	 * @param limit 
	 * @param start 
	 *@return null
	 *@param
	 */
	public PageObject queryOpeObjForPage(OpeLogVO opeLogVO, int start, int limit);

	/**
	 * 
	 *事件日志 记录
	 *@return null
	 *@param 业务/表名、触发方式、触发类型、操作内容
	 */
	public void insertEventOpeLog(OpeLogVO opeLogVO);
	
	
	
}
