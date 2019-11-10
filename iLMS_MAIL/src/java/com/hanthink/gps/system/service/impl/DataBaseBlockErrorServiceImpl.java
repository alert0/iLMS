/**
 * 
 */
package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.system.dao.DataBaseBlockErrorDao;
import com.hanthink.gps.system.dao.PortalDao;
import com.hanthink.gps.system.service.DataBaseBlockErrorService;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;

/**
 * 描述：数据库阻塞异常邮件提醒ServiceImpl
 * @author chenyong
 * @date   2016-10-09
 */
public class DataBaseBlockErrorServiceImpl extends BaseServiceImpl implements DataBaseBlockErrorService{

	private DataBaseBlockErrorDao dao;
	
	private PortalDao portalDao;

	
	@Override
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo() {
		return dao.queryDataBaseBlockErrorInfo();
	}
	
	@Override
	public List<DataBaseBlockErrorVO> queryDataBaseBlockErrorInfo_PORTAL() {
		return portalDao.queryDataBaseBlockErrorInfo_portal();
	}
	
	
	//get 和  set方法
	public DataBaseBlockErrorDao getDao() {
		return dao;
	}

	public void setDao(DataBaseBlockErrorDao dao) {
		this.dao = dao;
	}

	public PortalDao getPortalDao() {
		return portalDao;
	}


	public void setPortalDao(PortalDao portalDao) {
		this.portalDao = portalDao;
	}

	
}
