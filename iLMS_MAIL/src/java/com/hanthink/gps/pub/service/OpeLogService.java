package com.hanthink.gps.pub.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.OpeLogVO;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 
 * 广汽乘用车系统模块-操作日志
 * 
 * 日期        版本    作者         说 明
 * 
 * 2016 04/09 0.01  anMin    新建
 * 
 */

public interface OpeLogService extends BaseService{

	

	/**
	 * 
	 *操作日志 记录
	 *@return null
	 *@param 表名,主键字段名 ,主键值,操作类型【I：插入;D:删除；M：修改】，触发源,ip,用户名
	 */
	
	public void sysOpeLog(String tableName,String idColumn,String pkKey,String trxType,String fromName,String localIp,String userid);
	
	
	/**
	 * 
	 *查询操作日志 
	 *@return null
	 *@param
	 */
	public PageObject queryOpeObjForPage(OpeLogVO opeLogVO, int start,
			int limit);	
	
	/**
	 * 
	 *事件日志 记录
	 *@return null
	 *@param 业务/表名、触发方式、触发类型【E】、操作内容
	 */
	public void sysEventOpeLog(String fromName,String trxName,String trxType,String oldValue,
			String localIp,String userid);
}
