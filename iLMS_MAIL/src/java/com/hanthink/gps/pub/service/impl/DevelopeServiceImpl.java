package com.hanthink.gps.pub.service.impl;

import com.hanthink.gps.pub.dao.DevelopeDao;
import com.hanthink.gps.pub.service.DevelopeService;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;
import com.hanthink.gps.util.Constants;

public class DevelopeServiceImpl extends BaseServiceImpl implements DevelopeService {
	
	private DevelopeDao developeDao;

	/**
	 * 查询模块、菜单
	 * @param menuVO
	 * @param start
	 * @param limit
	 * @return PageObject
	 * @author zuosl  2016-3-15 下午03:48:36
	 */
	public PageObject queryModuleMenuForPage(MMenuVO menuVO, int start,
			int limit) {
		return developeDao.queryModuleForPage(menuVO, start, limit);
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
		return developeDao.queryMenuPrivilegeForPage(privilegeVO, start, limit);
	}
	
	/**
	 * 新增系统模块
	 * @param menuVO
	 * @return MMenuVO
	 */
	public MMenuVO insertSysModule(MMenuVO menuVO) {
		return developeDao.insertSysModule(menuVO);
	}
	
	/**
	 * 修改系统模块
	 * @param menuVO
	 * @return int
	 */
	public int updateSysModule(MMenuVO menuVO) {
		return developeDao.updateSysModule(menuVO);
	}
	
	/**
	 * 删除系统一级模块
	 * @param menuVO
	 * @return int
	 */
	public int deleteFirstModule(MMenuVO menuVO) {
		
		//删除一级模块
		MMenuVO mVO1 = new MMenuVO();
		mVO1.setMenuId(menuVO.getMenuId());
		mVO1.setMenuLevel("1");
		developeDao.deleteSysModule(mVO1);
		
		//删除相关二级模块
//		MMenuVO mVO2 = new MMenuVO();
//		mVO2.setParentId(menuVO.getMenuId());
//		mVO2.setMenuLevel("2");
//		developeDao.deleteSysModule(mVO2);
		
		return 0;
	}
	
	/**
	 * 删除系统二级模块
	 * @param menuVO
	 * @return int
	 */
	public int deleteSecondModule(MMenuVO menuVO) {
		MMenuVO mVO2 = new MMenuVO();
		mVO2.setMenuId(menuVO.getMenuId());
		mVO2.setMenuLevel("2");
		return developeDao.deleteSysModule(mVO2);
		
		//删除相关三级菜单
//		PrivilegeVO priVO1 = new PrivilegeVO();
//		priVO1.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
//		priVO1.setParentCode(menuVO.getMenuId());
//		int count2 = developeDao.deleteSysPrivilege(priVO1);
		
//		return count1 + count2;
	}
	
	/**
	 * 新增系统权限信息 
	 * @param privilegeVO void
	 */
	public void insertSysPrivilege(PrivilegeVO privilegeVO) {
		developeDao.insertSysPrivilege(privilegeVO);
	}
	
	/**
	 * 修改系统权限信息
	 * @param privilegeVO 
	 * @return int
	 */
	public int updateSysPrivilege(PrivilegeVO privilegeVO) {
		return developeDao.updateSysPrivilege(privilegeVO);
	}
	
	/**
	 * 删除三级菜单
	 * @param privilegeVO
	 * @return int
	 */
	public int deleteThirdMenu(PrivilegeVO privilegeVO) {
		//删除菜单
		PrivilegeVO priVO1 = new PrivilegeVO();
		priVO1.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
		priVO1.setPrivilegeCode(privilegeVO.getPrivilegeCode());
		int count1 = developeDao.deleteSysPrivilege(priVO1);
		
		//删除菜单相关权限
		PrivilegeVO priVO2 = new PrivilegeVO();
		priVO2.setPrivilegeType(Constants.PRIVILEGE_TYPE_PAGE);
		priVO2.setParentCode(privilegeVO.getPrivilegeCode());
		int count2 = developeDao.deleteSysPrivilege(priVO2);
		
		return count1 + count2;
	}
	
	/**
	 * 删除界面权限
	 * @param pagePrivilegeVO
	 * @return int
	 */
	public int deletePagePrivilege(PrivilegeVO privilegeVO) {
		PrivilegeVO priVO = new PrivilegeVO();
		priVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_PAGE);
		priVO.setPrivilegeCode(privilegeVO.getPrivilegeCode());
		System.out.println("-***************************");
		System.out.println(priVO.getPrivilegeType());
		System.out.println(priVO.getPrivilegeCode());
		return developeDao.deleteSysPrivilege(priVO);
	}
	
	
	public DevelopeDao getDevelopeDao() {
		return developeDao;
	}

	public void setDevelopeDao(DevelopeDao developeDao) {
		this.developeDao = developeDao;
	}

	
}
