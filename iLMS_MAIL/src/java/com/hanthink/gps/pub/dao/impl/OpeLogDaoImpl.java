package com.hanthink.gps.pub.dao.impl;

import java.util.HashMap;
import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.OpeLogDao;
import com.hanthink.gps.util.sequence.SeqManager;
import com.hanthink.gps.util.sequence.SeqType;
import com.hanthink.gps.pub.vo.OpeLogVO;
import com.hanthink.gps.pub.vo.OpeTableVO;
import com.hanthink.gps.pub.vo.PageObject;

public class OpeLogDaoImpl extends BaseDaoSupport implements OpeLogDao {


	/**
	 * 
	 *操作日志 记录
	 *@return null
	 *@param 表名,主键字段名 ,主键值,操作类型【I：插入;D:删除；M：修改】，触发源,ip,用户名
	 */
	public void inserOpeLog(OpeLogVO opeLogVO) {
		opeLogVO.setId(Integer.parseInt(SeqManager.getSeq(SeqType.SEQ_MM_PUB_OPE_LOG_ID)));
		System.out.println("序列:"+Integer.parseInt(SeqManager.getSeq(SeqType.SEQ_MM_PUB_OPE_LOG_ID)));
		executeInsert("pub.insert_OpeLog", opeLogVO);
	}

	
	/**
	 * 
	 *被操作表的字段
	 *@return null
	 *@param 表名
	 */
	public List<OpeTableVO> queryTableColumn(OpeTableVO opeTableVO) {
		return (List<OpeTableVO>) executeQueryForList("pub.select_OpeLogTableColumn",opeTableVO);	
	}
	
	/**
	 * 
	 *被操作表的记录
	 *@return null
	 *@param 表名以及主键值
	 */
	public List queryOpeObjRowList(HashMap<String, Object> map) {
		return (List<Object>) executeQueryForList("pub.select_OpeLogValue",map);	
		}



	/**
	 * 
	 *查询操作日志 
	 *@return null
	 *@param
	 */
	public PageObject queryOpeObjForPage(OpeLogVO opeLogVO,int start,int limit) {
	return this.executeQueryForPage("pub.select_queryOpeLog_forPage", opeLogVO, start, limit);
	}

	/**
	 * 
	 *事件日志 记录
	 *@return null
	 *@param 业务/表名、触发方式、触发类型【E】、操作内容
	 */
	public void insertEventOpeLog(OpeLogVO opeLogVO) {
		
		opeLogVO.setId(Integer.parseInt(SeqManager.getSeq(SeqType.SEQ_MM_PUB_OPE_LOG_ID)));
		System.out.println("序列:"+Integer.parseInt(SeqManager.getSeq(SeqType.SEQ_MM_PUB_OPE_LOG_ID)));
		executeInsert("pub.insert_EventLog", opeLogVO);		
	}







	

}
