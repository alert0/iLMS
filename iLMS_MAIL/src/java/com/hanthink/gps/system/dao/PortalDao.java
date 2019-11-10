package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;
import com.hanthink.gps.system.vo.ProErrorVO;

/**
 * @Title: DailyCheckInfoPortalDao.java
 * @Package: com.hanthink.gps.system.dao
 * @Description: 连接门户数据库
 * @author dtp
 * @date 2019-3-9
 */
public interface PortalDao {

	/**
	 * 获取统计数据库表空间信息
	 * @param tableSpaceNamePORTAL
	 * @return
	 */
	List<CheckTableSpaceVO> checkTableSpace_PORTAL(
			CheckTableSpaceVO tableSpaceNamePORTAL);

	/**
	 * 获取存储过程执行时间信息
	 * @return
	 */
	List<CheckProcedureTimeVO> checkProcedureTime_PORTAL();

	/**
	 * 获取数据库对象占用空间信息_PORTAL
	 * @param userSegmentsVO 
	 * @return
	 */
	List<CheckDBObjectSpaceVO> checkDBObjectSpace_PORTAL(CheckDBObjectSpaceVO userSegmentsVO);

	/**
	 * 获取数据表记录行数信息
	 * @param checkTableRowNumVO
	 * @return
	 */
	List<CheckTableRowNumVO> checkTableRowNum_PORTAL(
			CheckTableRowNumVO checkTableRowNumVO);

	List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo_portal();

	/**
	 * 查询数据库JOB异常停止(信息共享平台)
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryDBJobExceptionStopPortal(ProErrorVO vo);

	/**
	 * 查询接口异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryIFExceptionListPortal(ProErrorVO vo);

	/**
	 * 查询触发器异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	List<ProErrorVO> queryDBTriggerExceptionStopPortal(ProErrorVO vo);

}
