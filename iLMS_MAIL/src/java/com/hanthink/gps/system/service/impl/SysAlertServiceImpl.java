package com.hanthink.gps.system.service.impl;


import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.system.dao.SysAlertDao;
import com.hanthink.gps.system.service.SysAlertService;
import com.hanthink.gps.system.vo.SysAlertVO;

/**
 * 描述：系统警讯信息提醒ServiceImpl
 * @author chenyong 
 * @date   2016-10-18
 */
public class SysAlertServiceImpl extends BaseServiceImpl implements SysAlertService{

	private SysAlertDao dao;
	
	/**
	 * 获得A级异常信息
	 * return PECGpsMesIfErrorVO  A级异常信息
	 */
	@Override
	public List<SysAlertVO> queryPECGpsMesIfErrorA() {
		
		return dao.queryPECGpsMesIfErrorA();
	}
	
	/**
	 * 获取非A级别异常信息
	 * return PECGpsMesIfErrorVO  非A级别异常信息
	 */
	@Override
	public List<SysAlertVO> queryPECGpsMesIfErrorNotA() {
		
		return dao.queryPECGpsMesIfErrorNotA();
	}
	
	/**
	 * 修改非A级别的数据为已处理
	 */
	@Override
	public void updateNotAIsHandle() {
		
		dao.updateNotAIsHandle();
	}
	
	/**
	 * get 和 set方法
	 * @return
	 */
	public SysAlertDao getDao() {
		return dao;
	}
	public void setDao(SysAlertDao dao) {
		this.dao = dao;
	}
}
