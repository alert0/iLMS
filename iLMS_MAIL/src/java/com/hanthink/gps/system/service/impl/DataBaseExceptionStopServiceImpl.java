package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.system.dao.DataBaseExceptionStopDao;
import com.hanthink.gps.system.dao.PortalDao;
import com.hanthink.gps.system.service.DataBaseExceptionStopService;
import com.hanthink.gps.system.vo.ProErrorVO;

/**
 * @Title: DataBaseExceptionStopServiceImpl.java
 * @Package: com.hanthink.gps.system.service.impl
 * @Description: 数据库job异常停止,触发器异常提醒
 * @author dtp
 * @date 2019-5-27
 */
public class DataBaseExceptionStopServiceImpl extends BaseServiceImpl 
		implements DataBaseExceptionStopService{
	
	private DataBaseExceptionStopDao dao;
	private PortalDao portalDao;

	/**
	 * 查询数据库JOB异常停止
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryDBJobExceptionStop(ProErrorVO vo) {
		return dao.queryDBJobExceptionStop(vo);
	}
	
	/**
	 * 查询触发器异常
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryDBTriggerExceptionStop(ProErrorVO vo) {
		return dao.queryDBTriggerExceptionStop(vo);
	}
	
	/**
	 * 查询接口异常
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryIFExceptionList(ProErrorVO vo) {
		return dao.queryIFExceptionList(vo);
	}
	/**
	 * 查询数据库JOB异常停止(信息共享平台)
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryDBJobExceptionStopPortal(ProErrorVO vo) {
		return portalDao.queryDBJobExceptionStopPortal(vo);
	}
	
	/**
	 * 查询接口异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryIFExceptionListPortal(ProErrorVO vo) {
		return portalDao.queryIFExceptionListPortal(vo);
	}

	public DataBaseExceptionStopDao getDao() {
		return dao;
	}

	public void setDao(DataBaseExceptionStopDao dao) {
		this.dao = dao;
	}

	public PortalDao getPortalDao() {
		return portalDao;
	}

	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	/**
	 * 查询触发器异常(信息共享平台)
	 * @param vo
	 * @return
	 */
	@Override
	public List<ProErrorVO> queryDBTriggerExceptionStopPortal(ProErrorVO vo) {
		return portalDao.queryDBTriggerExceptionStopPortal(vo);
	}


}
