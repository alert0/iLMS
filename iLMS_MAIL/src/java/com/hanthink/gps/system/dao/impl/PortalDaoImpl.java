package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.PortalDao;
import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;
import com.hanthink.gps.system.vo.ProErrorVO;

public class PortalDaoImpl  extends BaseDaoSupport implements PortalDao{

	/**
	 * 获取统计数据库表空间信息
	 * @param tableSpaceNamePORTAL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckTableSpaceVO> checkTableSpace_PORTAL(
			CheckTableSpaceVO tableSpaceNamePORTAL) {
		return (List<CheckTableSpaceVO>) this.executeQueryForList("portal.checkTableSpace", tableSpaceNamePORTAL);
	}

	/**
	 * 获取存储过程执行时间信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckProcedureTimeVO> checkProcedureTime_PORTAL() {
		return (List<CheckProcedureTimeVO>) this.executeQueryForList("portal.checkProcedureTime");
	}

	/**
	 * 获取数据库对象占用空间信息_PORTAL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace_PORTAL(CheckDBObjectSpaceVO userSegmentsVO) {
		return  (List<CheckDBObjectSpaceVO>) this.executeQueryForList("portal.checkDBObjectSpace", userSegmentsVO);
	}

	/**
	 * 获取数据表记录行数信息
	 * @param checkTableRowNumVO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CheckTableRowNumVO> checkTableRowNum_PORTAL(
			CheckTableRowNumVO checkTableRowNumVO) {
		return (List<CheckTableRowNumVO>) this.executeQueryForList("portal.checkTableRowNum", checkTableRowNumVO);
	}

	/**
	 * 数据库死锁
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo_portal() {
		DataBaseBlockErrorVO  vo=new DataBaseBlockErrorVO();
		 vo.setVsession("v$session");
		 vo.setVlock("v$locked_object");
		 vo.setSqltext("v$sqltext");
		return (List<DataBaseBlockErrorVO>) this.executeQueryForList("portal.queryDataBaseBlockErrorInfo",vo);
	}

	/**
	 * 查询数据库JOB异常停止(信息共享平台)
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryDBJobExceptionStopPortal(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("portal.queryDBJobExceptionStopPortal", vo);
	}

	/**
	 * 查询接口异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryIFExceptionListPortal(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("portal.queryIFExceptionListPortal", vo);
	}

	/**
	 * 查询触发器异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProErrorVO> queryDBTriggerExceptionStopPortal(ProErrorVO vo) {
		return (List<ProErrorVO>) this.executeQueryForList("portal.queryDBTriggerExceptionStopPortal", vo);
	}

}
