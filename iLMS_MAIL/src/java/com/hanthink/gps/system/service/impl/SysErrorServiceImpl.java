package com.hanthink.gps.system.service.impl;

import java.util.List;

import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.dao.DailyCheckInfoDao;
import com.hanthink.gps.system.dao.SysErrorDao;
import com.hanthink.gps.system.service.SysErrorService;
import com.hanthink.gps.system.vo.ProErrorVO;

public class SysErrorServiceImpl extends BaseServiceImpl implements SysErrorService {
	
	private SysErrorDao dao;
	private DailyCheckInfoDao dailyCheckInfoDao;

	public SysErrorDao getDao() {
		return dao;
	}
	public void setDao(SysErrorDao dao) {
		this.dao = dao;
	}
	@Override
	public List<ProErrorVO> queryProErrorInfoList() {
		return dao.queryProErrorInfoList();
	}
	
	/**
	 * 查询系统参数
	 * @param vo
	 * @return
	 */
	@Override
	public SystemParamVO queryParamByParamCode(SystemParamVO vo) {
		return dailyCheckInfoDao.queryParamByParamCode(vo);
	}
	public DailyCheckInfoDao getDailyCheckInfoDao() {
		return dailyCheckInfoDao;
	}
	public void setDailyCheckInfoDao(DailyCheckInfoDao dailyCheckInfoDao) {
		this.dailyCheckInfoDao = dailyCheckInfoDao;
	}
	
}
