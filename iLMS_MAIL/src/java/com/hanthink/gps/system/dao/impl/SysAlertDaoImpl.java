/**
 * 
 */
package com.hanthink.gps.system.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.system.dao.SysAlertDao;
import com.hanthink.gps.system.vo.SysAlertVO;

/**
 * 描述：系统警讯信息提醒DAOImpl
 * @author chenyong
 * @date   2016-10-18
 * 
 */
public class SysAlertDaoImpl extends BaseDaoSupport implements SysAlertDao{

	/**
	 * 查询A级警讯信息
	 * @return  PECGpsMesIfErrorVO A级警讯信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysAlertVO> queryPECGpsMesIfErrorA() {
		
		return (List<SysAlertVO>) this.executeQueryForList("system.select_queryPECGpsMesIfErrorA");
	}

	/**
	 * 查询非A级警讯信息
	 * @return  PECGpsMesIfErrorVO 非A级警讯信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysAlertVO> queryPECGpsMesIfErrorNotA() {
		
		String param = "a.alert_level<>'A'";
		return (List<SysAlertVO>) this.executeQueryForList("system.select_queryPECGpsMesIfErrorNotA", param);
	}

	
	/**
	 * 修改非A级别的数据为已处理
	 */
	@Override
	public void updateNotAIsHandle() {
		
		this.executeUpdate("system.update_updateNotAIsHandle");
	}

}
