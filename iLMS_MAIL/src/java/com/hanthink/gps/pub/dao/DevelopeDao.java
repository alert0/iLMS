package com.hanthink.gps.pub.dao;

import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;

public interface DevelopeDao {

	/**
	 * 查询系统模块
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	PageObject queryModuleForPage(MMenuVO menuVO, int start, int limit);

	/**
	 * 查询菜单、权限
	 * @param privilegeVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	PageObject queryMenuPrivilegeForPage(PrivilegeVO privilegeVO, int start,
			int limit);

	/**
	 * 新增系统模块
	 * @param menuVO
	 * @return MMenuVO
	 */
	MMenuVO insertSysModule(MMenuVO menuVO);

	/**
	 * 修改系统模块
	 * @param menuVO
	 * @return int
	 */
	int updateSysModule(MMenuVO menuVO);

	/**
	 * 删除系统模块
	 * @param menuVO
	 * @return int
	 */
	int deleteSysModule(MMenuVO menuVO);

	/**
	 * 新增系统权限信息 
	 * @param privilegeVO void
	 */
	void insertSysPrivilege(PrivilegeVO privilegeVO);

	/**
	 * 修改系统权限信息
	 * @param privilegeVO 
	 * @return int
	 */
	int updateSysPrivilege(PrivilegeVO privilegeVO);

	/**
	 * 删除系统权限信息
	 * @param privilegeVO
	 * @return int
	 */
	int deleteSysPrivilege(PrivilegeVO privilegeVO);

}
