package com.hanthink.sys.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.base.manager.AbstractManager;
import com.hanthink.sys.model.SysPdaRoleModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SysPdaRoleManager extends AbstractManager<String, SysPdaRoleModel>{

	/**
	 * 分页查询PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:44:53
	 */
	List<SysPdaRoleModel> queryPdaRolePager(SysPdaRoleModel model, Page p);

	/**
	 * 查询PDA角色的菜单信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:55:43
	 */
	List<SysPdaRoleModel> queryPdaRoleMenuPager(SysPdaRoleModel model, Page p);

	/**
	 * 删除PDA角色
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:27:13
	 */
	void deletePdaRole(SysPdaRoleModel model);

	/**
	 * 根据角色ID查询该角色还未添加的菜单数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:42:53
	 */
	List<SysPdaRoleModel> queryNotAddPdaRoleDataByRoleId(SysPdaRoleModel model, Page p);

	/**
	 * 添加PDA角色菜单信息
	 * @param roleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:28:33
	 */
	void addPdaRoleMenu(List<SysPdaRoleModel> roleList);

	/**
	 * 删除PDA的角色菜单信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:38:24
	 */
	void deletePdaRoleMenu(List<SysPdaRoleModel> modelList);

	/**
	 * 根据用户ID查询该用户的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:58:13
	 */
	PageList<Map<String, Object>> queryUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 根据用户ID查询该用户所有未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:11:57
	 */
	PageList<Map<String, Object>> queryAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 根据用户ID查询该用户在当前登录用户有的角色中未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:37:29
	 */
	PageList<Map<String, Object>> queryCurUserAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 添加用户的PDA角色信息
	 * @param userDataRoleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:43:55
	 */
	void addUserPdaRole(List<SysPdaRoleModel> userDataRoleList);

	/**
	 * 删除用户的PDA角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:47:32
	 */
	void deleteUserPdaRole(List<SysPdaRoleModel> modelList);

}
