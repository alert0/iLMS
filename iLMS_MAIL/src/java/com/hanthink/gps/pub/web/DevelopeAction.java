package com.hanthink.gps.pub.web;

import com.hanthink.gps.pub.service.DevelopeService;
import com.hanthink.gps.pub.vo.MMenuVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.PrivilegeVO;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * 开发人员管理action 用于配置菜单、权限等
 * @author zuosl
 *
 */
public class DevelopeAction extends BaseActionSupport {
	private static final long serialVersionUID = 100516221123664L;
	
	private DevelopeService service;
	
	private String parentId; //父模块代码
	
	private MMenuVO pageMenuVO; //界面模块信息
	private PrivilegeVO pagePrivilegeVO; //界面菜单权限信息
	
	/**
	 * 查询一级模块
	 */
	public void queryFirstModuleForPage(){
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("1");
		PageObject o = service.queryModuleMenuForPage(menuVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询二级模块
	 *  void
	 */
	public void querySecondModuleForPage(){
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("2");
		menuVO.setParentId(parentId);
		PageObject o = service.queryModuleMenuForPage(menuVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询三级菜单权限
	 *  void
	 */
	public void queryMenuPrivilegeForPage(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
		privilegeVO.setParentCode(parentId);
		PageObject o = service.queryMenuPrivilegeForPage(privilegeVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 查询界面按钮数据权限
	 *  void
	 */
	public void queryPagePrivilegesForPage(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_PAGE);
		privilegeVO.setParentCode(parentId);
		PageObject o = service.queryMenuPrivilegeForPage(privilegeVO, start, limit);
		writeJson(o);
	}
	
	/**
	 * 新增一级模块
	 *  void
	 */
	public void addFirstModule(){
		
		//检查模块代码是否重复
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("1");
		menuVO.setMenuId(pageMenuVO.getMenuId());
		PageObject o = service.queryModuleMenuForPage(menuVO, 0, 1);
		if(0 < o.getTotalCount()){
			addError("模块代码【"+menuVO.getMenuId()+"】已存在");
		}
		if (isInvalid()){
			return;
		}
		
		//新增
		menuVO.setMenuName(pageMenuVO.getMenuName());
		menuVO.setSort(pageMenuVO.getSort());
		menuVO.setParentId(pageMenuVO.getParentId());
		service.insertSysModule(menuVO);
		write(Constants.MSG_SUCCESS);
		
	}
	
	/**
	 * 修改一级模块
	 *  void
	 */
	public void updateFirstModule(){
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("1");
		menuVO.setMenuId(pageMenuVO.getMenuId());
		menuVO.setMenuName(pageMenuVO.getMenuName());
		menuVO.setSort(pageMenuVO.getSort());
		menuVO.setParentId(pageMenuVO.getParentId());
		service.updateSysModule(menuVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除一级模块
	 *  void
	 */
	public void deleteFirstModule(){
		
		//检查是否有子模块，若有则不允许删除
		MMenuVO menuVO1 = new MMenuVO();
		menuVO1.setMenuLevel("2");
		menuVO1.setParentId(pageMenuVO.getMenuId());
		PageObject o = service.queryModuleMenuForPage(menuVO1, 0, 5);
		if(0 < o.getTotalCount()){
			addError("该模块有子模块存在，不允许删除");
		}
		if (isInvalid()){
			return;
		}
		
		//删除模块
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuId(pageMenuVO.getMenuId());
		service.deleteFirstModule(menuVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 新增二级模块
	 *  void
	 */
	public void addSecondModule(){
		//检查模块代码是否重复
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("2");
		menuVO.setMenuId(pageMenuVO.getMenuId());
		PageObject o = service.queryModuleMenuForPage(menuVO, start, limit);
		if(0 < o.getTotalCount()){
			addError("模块代码【"+menuVO.getMenuId()+"】已存在");
		}
		if (isInvalid()){
			return;
		}
		
		//新增
		menuVO.setMenuName(pageMenuVO.getMenuName());
		menuVO.setSort(pageMenuVO.getSort());
		menuVO.setParentId(parentId);
		service.insertSysModule(menuVO);
		write(Constants.MSG_SUCCESS);
	}

	/**
	 * 修改二级模块
	 *  void
	 */
	public void updateSecondModule(){
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuLevel("2");
		menuVO.setMenuId(pageMenuVO.getMenuId());
		menuVO.setMenuName(pageMenuVO.getMenuName());
		menuVO.setSort(pageMenuVO.getSort());
		service.updateSysModule(menuVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除二级模块
	 *  void
	 */
	public void deleteSecondModule(){
		
		//检查是否有子菜单，若有则不允许删除
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
		privilegeVO.setParentCode(pageMenuVO.getMenuId());
		PageObject o = service.queryMenuPrivilegeForPage(privilegeVO, 0, 5);
		if(0 < o.getTotalCount()){
			addError("该模块有子菜单不允许删除");
		}
		if (isInvalid()){
			return;
		}
		
		//删除二级模块
		MMenuVO menuVO = new MMenuVO();
		menuVO.setMenuId(pageMenuVO.getMenuId());
		service.deleteSecondModule(menuVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 新增三级菜单
	 *  void
	 */
	public void addThirdMenu(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeCode(pagePrivilegeVO.getPrivilegeCode());
		
		//检查权限代码是否重复
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
		PageObject o = service.queryMenuPrivilegeForPage(privilegeVO, 0, 1);
		if(0 < o.getTotalCount()){
			addError("权限代码【"+privilegeVO.getPrivilegeCode()+"】已存在");
		}
		if (isInvalid()){
			return;
		}
		
		//新增
		privilegeVO.setParentCode(parentId);
		privilegeVO.setPrivilegeDesc(pagePrivilegeVO.getPrivilegeDesc());
		privilegeVO.setMenuName(pagePrivilegeVO.getMenuName());
		privilegeVO.setMenuUrl(pagePrivilegeVO.getMenuUrl());
		privilegeVO.setSort(pagePrivilegeVO.getSort());
		service.insertSysPrivilege(privilegeVO);
		
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 修改三级菜单
	 *  void
	 */
	public void updateThirdMenu(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_MENU);
		privilegeVO.setPrivilegeCode(pagePrivilegeVO.getPrivilegeCode());
		privilegeVO.setMenuName(pagePrivilegeVO.getMenuName());
		privilegeVO.setMenuUrl(pagePrivilegeVO.getMenuUrl());
		privilegeVO.setPrivilegeDesc(pagePrivilegeVO.getPrivilegeDesc());
		privilegeVO.setSort(pagePrivilegeVO.getSort());
		service.updateSysPrivilege(privilegeVO);
		
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除三级菜单
	 *  void
	 */
	public void deleteThirdMenu(){
		service.deleteThirdMenu(pagePrivilegeVO);
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 新增界面权限
	 *  void
	 */
	public void addPagePrivilege(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeCode(pagePrivilegeVO.getPrivilegeCode());
		
		//检查权限代码是否重复
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_PAGE);
		PageObject o = service.queryMenuPrivilegeForPage(privilegeVO, 0, 1);
		if(0 < o.getTotalCount()){
			addError("权限代码【"+privilegeVO.getPrivilegeCode()+"】已存在");
		}
		if (isInvalid()){
			return;
		}
		
		//新增
		privilegeVO.setParentCode(parentId);
		privilegeVO.setPrivilegeDesc(pagePrivilegeVO.getPrivilegeDesc());
		privilegeVO.setSort(pagePrivilegeVO.getSort());
		service.insertSysPrivilege(privilegeVO);
		
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 修改界面权限
	 *  void
	 */
	public void updatePagePrivilege(){
		PrivilegeVO privilegeVO = new PrivilegeVO();
		privilegeVO.setPrivilegeType(Constants.PRIVILEGE_TYPE_PAGE);
		privilegeVO.setPrivilegeCode(pagePrivilegeVO.getPrivilegeCode());
		privilegeVO.setPrivilegeDesc(pagePrivilegeVO.getPrivilegeDesc());
		privilegeVO.setSort(pagePrivilegeVO.getSort());
		service.updateSysPrivilege(privilegeVO);
		
		write(Constants.MSG_SUCCESS);
	}
	
	/**
	 * 删除界面权限
	 *  void
	 */
	public void deletePagePrivilege(){
		service.deletePagePrivilege(pagePrivilegeVO);
		write(Constants.MSG_SUCCESS);
	}
	
	
	public DevelopeService getService() {
		return service;
	}

	public void setService(DevelopeService service) {
		this.service = service;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public MMenuVO getPageMenuVO() {
		return pageMenuVO;
	}
	public void setPageMenuVO(MMenuVO pageMenuVO) {
		this.pageMenuVO = pageMenuVO;
	}

	public PrivilegeVO getPagePrivilegeVO() {
		return pagePrivilegeVO;
	}
	public void setPagePrivilegeVO(PrivilegeVO pagePrivilegeVO) {
		this.pagePrivilegeVO = pagePrivilegeVO;
	}
	
	

}
