package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;

public interface DevelopeService extends BaseService {

	/**
	 * 查询模块、菜单
	 * @param menuVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	PageObject queryModuleMenuForPage(MMenuVO menuVO, int start, int limit);

	/**
	 * 查询菜单、权限
	 * @param privilegeVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	PageObject queryMenuPrivilegeForPage(PrivilegeVO privilegeVO, int start, int limit);

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
	 * 删除系统一级模块
	 * @param menuVO
	 * @return int
	 */
	int deleteFirstModule(MMenuVO menuVO);

	/**
	 * 删除系统二级模块
	 * @param menuVO
	 * @return int
	 */
	int deleteSecondModule(MMenuVO menuVO);

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
	 * 删除三级菜单
	 * @param privilegeVO
	 * @return int
	 */
	int deleteThirdMenu(PrivilegeVO privilegeVO);

	/**
	 * 删除界面权限
	 * @param pagePrivilegeVO
	 * @return int
	 */
	int deletePagePrivilege(PrivilegeVO pagePrivilegeVO);

}
