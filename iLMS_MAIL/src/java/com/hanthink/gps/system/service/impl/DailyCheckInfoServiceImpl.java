/**
 * 
 */
package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.dao.DailyCheckInfoDao;
import com.hanthink.gps.system.dao.PortalDao;
import com.hanthink.gps.system.service.DailyCheckInfoService;
import com.hanthink.gps.system.vo.CheckDBObjectSpaceVO;
import com.hanthink.gps.system.vo.CheckProcedureTimeVO;
import com.hanthink.gps.system.vo.CheckTableRowNumVO;
import com.hanthink.gps.system.vo.CheckTableSpaceVO;

/**
 * 描述：每日点检邮件发送信息ServiceImpl
 * @author chenyong
 * @date   2016-10-10
 */
public class DailyCheckInfoServiceImpl extends BaseServiceImpl implements DailyCheckInfoService{

	private DailyCheckInfoDao dao;
	
	private PortalDao portalDao;
	
	//统计表空间信息
	@Override
	public List<CheckTableSpaceVO> checkTableSpace(CheckTableSpaceVO vo) {
		return dao.checkTableSpace(vo);
	}

	
	//统计存储过程执行时间
	@Override
	public List<CheckProcedureTimeVO> checkProcedureTime() {
		return dao.checkProcedureTime();
	}


	//统计数据库对象占用空间信息
	@Override
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace(CheckDBObjectSpaceVO userSegmentsVO) {
		return dao.checkDBObjectSpace(userSegmentsVO);
	}


	//统计数据表行数信息
	@Override
	public List<CheckTableRowNumVO> checkTableRowNum(CheckTableRowNumVO checkTableRowNumVO) {
		return dao.checkTableRowNum(checkTableRowNumVO);
	}

    //get  和       set 方法
	public DailyCheckInfoDao getDao() {
		return dao;
	}


	public void setDao(DailyCheckInfoDao dao) {
		this.dao = dao;
	}

	/**
	 * 根据系统参数代码获取系统参数
	 */
	@Override
	public SystemParamVO queryParamByParamCode(SystemParamVO vo) {
		return dao.queryParamByParamCode(vo);
	}


	//获取统计数据库表空间信息_PORTAL
	@Override
	public List<CheckTableSpaceVO> checkTableSpace_PORTAL(
			CheckTableSpaceVO tableSpaceNamePORTAL) {
		return portalDao.checkTableSpace_PORTAL(tableSpaceNamePORTAL);
	}


	//获取存储过程执行时间信息
	@Override
	public List<CheckProcedureTimeVO> checkProcedureTime_PORTAL() {
		return portalDao.checkProcedureTime_PORTAL();
	}


	//获取数据库对象占用空间信息_PORTAL
	@Override
	public List<CheckDBObjectSpaceVO> checkDBObjectSpace_PORTAL(
			CheckDBObjectSpaceVO userSegmentsVO) {
		return portalDao.checkDBObjectSpace_PORTAL(userSegmentsVO);
	}

	//获取数据表记录行数信息
	@Override
	public List<CheckTableRowNumVO> checkTableRowNum_PORTAL(
			CheckTableRowNumVO checkTableRowNumVO) {
		return portalDao.checkTableRowNum_PORTAL(checkTableRowNumVO);
	}


	public PortalDao getPortalDao() {
		return portalDao;
	}


	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}


	
  
	
}
