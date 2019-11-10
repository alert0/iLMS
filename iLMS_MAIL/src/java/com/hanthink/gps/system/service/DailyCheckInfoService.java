/**
 * 
 */
package com.hanthink.gps.system.service;

import java.util.List;

import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;

/**
 * 描述：每日点检信息Service
 * @author chenyong
 * @date   2016-10-10
 */
public interface DailyCheckInfoService extends BaseService{
  
	//统计表空间信息
	public List<CheckTableSpaceVO> checkTableSpace(CheckTableSpaceVO vo);
	
	//统计存储过程执行时间
	public List<CheckProcedureTimeVO> checkProcedureTime();
	
	//统计数据库表占用空间信息
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace(CheckDBObjectSpaceVO userSegmentsVO);
	
	//统计数据表行数信息
	public List<CheckTableRowNumVO> checkTableRowNum(CheckTableRowNumVO checkTableRowNumVO);
	
	//根据系统参数代码获取系统参数
	public SystemParamVO queryParamByParamCode(SystemParamVO vo);

	//获取统计数据库表空间信息_PORTAL
	public List<CheckTableSpaceVO> checkTableSpace_PORTAL(
			CheckTableSpaceVO tableSpaceNamePORTAL);

	//获取存储过程执行时间信息
	public List<CheckProcedureTimeVO> checkProcedureTime_PORTAL();

	//获取数据库对象占用空间信息_PORTAL
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace_PORTAL(
			CheckDBObjectSpaceVO userSegmentsVO);

	//获取数据表记录行数信息
	public List<CheckTableRowNumVO> checkTableRowNum_PORTAL(
			CheckTableRowNumVO checkTableRowNumVO);
}
