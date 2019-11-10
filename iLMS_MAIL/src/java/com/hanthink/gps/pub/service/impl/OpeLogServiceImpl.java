package com.hanthink.gps.pub.service.impl;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.hanthink.gps.pub.dao.OpeLogDao;
import com.hanthink.gps.pub.service.OpeLogService;
import com.hanthink.gps.pub.vo.OpeLogVO;
import com.hanthink.gps.pub.vo.OpeTableVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.util.DateUtil;

/**
 * 
 * 广汽乘用车系统模块-操作日志
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 04/09 0.01  anMin    新建
 * 
 */

public class OpeLogServiceImpl extends BaseServiceImpl implements OpeLogService {
	
	private OpeLogDao opeLogDao;

	public OpeLogDao getOpeLogDao() {
		return opeLogDao;
	}

	public void setOpeLogDao(OpeLogDao opeLogDao) {
		this.opeLogDao = opeLogDao;
	}

	/**
	 * 
	 *操作日志 记录
	 *@return null
	 *@param 表名,主键字段名 ,主键值,操作类型【I：插入;D:删除；M：修改】，触发源,ip,用户名
	 */
	public void sysOpeLog(String tableName,String idColumn, String pkKey, String trxType,
			String fromName,String localIp,String userid) {
		
		//根据表名查询所有列名
		OpeTableVO opeTbaleColumnVo= new OpeTableVO();
		opeTbaleColumnVo.setOpetable(tableName);
		List<OpeTableVO> OpeColumnList= opeLogDao.queryTableColumn(opeTbaleColumnVo);

		//根据主键查询被操作的行的值，转化成list
		HashMap<String, Object> map = new HashMap<String, Object>();  
		 map.put("ID", pkKey);  
		 map.put("col1", "*"); 
		 map.put("tablePrefix", tableName);
		 map.put("col2", idColumn);  

		List opeObjRowList = opeLogDao.queryOpeObjRowList(map);
		Map resultMap= (Map) opeObjRowList.get(0);
		
		//处理日期
		for(int i= 0 ;i<OpeColumnList.size();i++){
			if((OpeColumnList.get(i).getDataType().equals("DATE"))){
				
				java.util.Date sqlDate = (java.util.Date) resultMap.get(OpeColumnList.get(i).getColumnName());
				if(null != sqlDate){
					Date argDate= new java.util.Date(sqlDate.getTime());
					String ageDateM = DateUtil.formatDate(argDate,"yyyy-MM-dd HH:mm:ss");
					resultMap.put(OpeColumnList.get(i).getColumnName(), ageDateM);
				}
				
			}else if((OpeColumnList.get(i).getDataType().equals("TIMESTAMP(6)"))){
				
				java.sql.Timestamp sqlDate = getOracleTimestamp(resultMap.get(OpeColumnList.get(i).getColumnName()));
				if(null != sqlDate){
					Date argDate= new java.util.Date(sqlDate.getTime());
					String ageDateM = DateUtil.formatDate(argDate,"yyyy-MM-dd HH:mm:ss");
					resultMap.put(OpeColumnList.get(i).getColumnName(), ageDateM);
				}
				
			}
		}
		
		//插入记录表
		OpeLogVO opeLogVO = new OpeLogVO();
		opeLogVO.setTableName(tableName);
		opeLogVO.setOldValue(resultMap.toString());
		opeLogVO.setFromName(fromName);
		opeLogVO.setLoclIp(localIp);
		opeLogVO.setTrxType(trxType);
		opeLogVO.setIdColumn(idColumn);
		opeLogVO.setRecordKey(pkKey);
		opeLogVO.setUserName(userid);

		opeLogDao.inserOpeLog(opeLogVO);
	}
	
	//转化oracle.sql.timestamp
	private Timestamp getOracleTimestamp(Object value) {
		  try {
		    Class clz = value.getClass();
		    Method m = clz.getMethod("timestampValue", null);
		    return (Timestamp) m.invoke(value, null);
		  } catch (Exception e) {
		    return null;
		  }
		}

	/**
	 * 
	 *查询操作日志 
	 *@return null
	 *@param
	 */
	public PageObject queryOpeObjForPage(OpeLogVO opeLogVO, int start,
			int limit) {
		
		return opeLogDao.queryOpeObjForPage(opeLogVO,start,limit);
	}
	
	/**
	 * 
	 *事件日志 记录
	 *@return null
	 *@param 业务/表名、触发方式、触发类型、操作内容
	 */
	public void sysEventOpeLog(String trxName,String fromName,String trxType,String oldValue,
			String localIp,String userid) {
		OpeLogVO opeLogVO = new OpeLogVO();
		opeLogVO.setTableName(trxName);
		opeLogVO.setFromName(fromName);
		opeLogVO.setTrxType(trxType);
		opeLogVO.setOldValue(oldValue);
		
		opeLogVO.setLoclIp(localIp);
		opeLogVO.setUserName(userid);
		
		opeLogDao.insertEventOpeLog(opeLogVO);






		
	}
	

}
