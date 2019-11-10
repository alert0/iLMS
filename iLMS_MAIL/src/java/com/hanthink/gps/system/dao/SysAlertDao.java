/**
 * 
 */
package com.hanthink.gps.system.dao;

import java.util.List;

import com.hanthink.gps.system.vo.SysAlertVO;

/**
 * 描述：系统警讯信息提醒DAO
 * @author chenyong
 * @date   2016-10-18
 * 
 */
public interface SysAlertDao {
    
	/**
	 * 查询A级警讯信息
	 * @return  PECGpsMesIfErrorVO A级警讯信息
	 */
	public List<SysAlertVO> queryPECGpsMesIfErrorA();
	
	/**
	 * 查询非A级警讯信息
	 * @return PECGpsMesIfErrorVO 非A级警讯信息
	 */
	public List<SysAlertVO> queryPECGpsMesIfErrorNotA();
	
	/**
	 * 修改非A级别的数据为已处理
	 */
	public void updateNotAIsHandle();
}
