package com.hanthink.gps.pub.dao.impl;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.DevelopeDao;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;

public class DevelopeDaoImpl extends BaseDaoSupport implements DevelopeDao {

	/**
	 * 查询系统模块
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	public PageObject queryModuleForPage(MMenuVO menuVO, int start, int limit) {
		return this.executeQueryForPage("pub.select_queryModuleForPage", menuVO, start, limit);
	}

	/**
	 * 查询菜单、权限
	 * @param privilegeVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 */
	public PageObject queryMenuPrivilegeForPage(PrivilegeVO privilegeVO,
			int start, int limit) {
		return this.executeQueryForPage("pub.select_queryPrivilegesForPage", privilegeVO, start, limit);
	}

	/**
	 * 新增系统模块
	 * @param menuVO
	 * @return MMenuVO
	 */
	public MMenuVO insertSysModule(MMenuVO menuVO) {
		return (MMenuVO) this.executeInsert("pub.insert_insertSysModule", menuVO);
	}

	/**
	 * 修改系统模块
	 * @param menuVO
	 * @return int
	 */
	public int updateSysModule(MMenuVO menuVO) {
		return this.executeUpdate("pub.update_updateSysModule", menuVO);
	}

	/**
	 * 删除系统模块
	 * @param menuVO
	 * @return int
	 */
	public int deleteSysModule(MMenuVO menuVO) {
		return this.executeDelete("pub.delete_deleteSysModule", menuVO);
	}

	/**
	 * 新增系统权限信息 
	 * @param privilegeVO void
	 */
	public void insertSysPrivilege(PrivilegeVO privilegeVO) {
		this.executeInsert("pub.insert_insertSysMenuPrivilege", privilegeVO);
	}

	/**
	 * 修改系统权限信息
	 * @param privilegeVO 
	 * @return int
	 */
	public int updateSysPrivilege(PrivilegeVO privilegeVO) {
		return this.executeUpdate("pub.update_updateMenuPrivilege", privilegeVO);
	}

	/**
	 * 删除系统权限信息
	 * @param privilegeVO
	 * @return int
	 */
	public int deleteSysPrivilege(PrivilegeVO privilegeVO) {
		return this.executeDelete("pub.delete_deleteMenuPrivilege", privilegeVO);
	}

}
