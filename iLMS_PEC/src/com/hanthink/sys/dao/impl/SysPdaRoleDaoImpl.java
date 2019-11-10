package com.hanthink.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysPdaRoleDao;
import com.hanthink.sys.model.SysPdaRoleModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;

@Repository
public class SysPdaRoleDaoImpl extends MyBatisDaoImpl<String, SysPdaRoleModel> implements SysPdaRoleDao {

	@Override
	public String getNamespace() {
		return SysPdaRoleModel.class.getName();
	}

	/**
	 * 分页查询PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:45:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPdaRoleModel> queryPdaRolePager(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryPdaRole", model, p);
	}

	/**
	 * 查询PDA角色的菜单信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:56:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPdaRoleModel> queryPdaRoleMenuPager(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryPdaRoleMenu", model, p);
	}

	/**
	 * 根据角色ID删除角色菜单权限ID
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:32:04
	 */
	@Override
	public void removePdaRoleMenuByRoleId(String id) {
		this.deleteByKey("removePdaRoleMenuByRoleId", id);
	}

	/**
	 * 根据角色ID删除用户角色信息
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:32:10
	 */
	@Override
	public void removePdaUserRoleByRoleId(String id) {
		this.deleteByKey("removePdaUserRoleByRoleId", id);
	}

	/**
	 * 根据角色ID查询该角色还未添加的菜单数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:44:15
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysPdaRoleModel> queryNotAddPdaRoleDataByRoleId(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryNotAddPdaRoleDataByRoleId", model, p);
	}

	/**
	 * 添加PDA角色菜单信息
	 * @param roleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:29:54
	 */
	@Override
	public void addPdaRoleMenu(List<SysPdaRoleModel> roleList) {
		for(SysPdaRoleModel model : roleList){
			this.insertByKey("insert_addPdaRoleMenu", model);
		}
	}

	/**
	 * 删除PDA的角色菜单信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:41:35
	 */
	@Override
	public void deletePdaRoleMenu(List<SysPdaRoleModel> modelList) {
		for(SysPdaRoleModel model : modelList){
			this.deleteByKey("deletePdaRoleMenu", model);
		}
	}

	/**
	 * 根据用户ID查询该用户的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:00:43
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryUserPdaRoleByUserId", model, p);
	}

	/**
	 * 根据用户ID查询该用户所有未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:13:46
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryAddUserPdaRoleByUserId", model, p);
	}

	/**
	 * 根据用户ID查询该用户在当前登录用户有的角色中未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:39:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryCurUserAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return this.getListByKey("queryCurUserAddUserPdaRoleByUserId", model, p);
	}

	/**
	 * 添加用户的PDA角色信息
	 * @param userPdaRoleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:45:12
	 */
	@Override
	public void addUserPdaRole(List<SysPdaRoleModel> userPdaRoleList) {
		for(SysPdaRoleModel model : userPdaRoleList){
			this.insertByKey("insert_addUserPdaRole", model);
		}
	}

	/**
	 * 删除用户的PDA角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:48:27
	 */
	@Override
	public void deleteUserPdaRole(List<SysPdaRoleModel> modelList) {
		for(SysPdaRoleModel model : modelList){
			this.deleteByKey("deleteUserPdaRole", model);
		}
	}

}
