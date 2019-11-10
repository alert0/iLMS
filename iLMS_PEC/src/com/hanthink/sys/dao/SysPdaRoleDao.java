package com.hanthink.sys.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.sys.model.SysPdaRoleModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

public interface SysPdaRoleDao extends Dao<String, SysPdaRoleModel>  {

	/**
	 * 分页查询PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:45:31
	 */
	List<SysPdaRoleModel> queryPdaRolePager(SysPdaRoleModel model, Page p);

	/**
	 * 查询PDA角色的菜单信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:56:34
	 */
	List<SysPdaRoleModel> queryPdaRoleMenuPager(SysPdaRoleModel model, Page p);

	/**
	 * 根据角色ID删除角色菜单权限ID
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:31:08
	 */
	void removePdaRoleMenuByRoleId(String id);

	/**
	 * 根据角色ID删除用户角色关系信息
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:31:39
	 */
	void removePdaUserRoleByRoleId(String id);

	/**
	 * 根据角色ID查询该角色还未添加的菜单数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:43:42
	 */
	List<SysPdaRoleModel> queryNotAddPdaRoleDataByRoleId(SysPdaRoleModel model, Page p);

	/**
	 * 添加PDA角色菜单信息
	 * @param roleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:29:39
	 */
	void addPdaRoleMenu(List<SysPdaRoleModel> roleList);

	/**
	 * 删除PDA的角色菜单信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:41:19
	 */
	void deletePdaRoleMenu(List<SysPdaRoleModel> modelList);

	/**
	 * 根据用户ID查询该用户的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:59:11
	 */
	PageList<Map<String, Object>> queryUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 根据用户ID查询该用户所有未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:13:28
	 */
	PageList<Map<String, Object>> queryAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 根据用户ID查询该用户在当前登录用户有的角色中未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:38:51
	 */
	PageList<Map<String, Object>> queryCurUserAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p);

	/**
	 * 添加用户的PDA角色信息
	 * @param userPdaRoleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:44:58
	 */
	void addUserPdaRole(List<SysPdaRoleModel> userPdaRoleList);

	/**
	 * 删除用户的PDA角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:48:11
	 */
	void deleteUserPdaRole(List<SysPdaRoleModel> modelList);

}
