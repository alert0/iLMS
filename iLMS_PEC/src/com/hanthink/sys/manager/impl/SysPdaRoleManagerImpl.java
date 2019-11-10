package com.hanthink.sys.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysPdaRoleDao;
import com.hanthink.sys.manager.SysPdaRoleManager;
import com.hanthink.sys.model.SysPdaRoleModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("SysPdaRoleManager")
public class SysPdaRoleManagerImpl extends AbstractManagerImpl<String, SysPdaRoleModel> implements SysPdaRoleManager  {
	
	@Resource
	private SysPdaRoleDao dao;

	@Override
	protected Dao<String, SysPdaRoleModel> getDao() {
		return this.dao;
	}

	/**
	 * 分页查询PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:45:04
	 */
	@Override
	public List<SysPdaRoleModel> queryPdaRolePager(SysPdaRoleModel model, Page p) {
		return dao.queryPdaRolePager(model, p);
	}

	/**
	 * 查询PDA角色的菜单信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午1:56:03
	 */
	@Override
	public List<SysPdaRoleModel> queryPdaRoleMenuPager(SysPdaRoleModel model, Page p) {
		return dao.queryPdaRoleMenuPager(model, p);
	}

	/**
	 * 删除PDA角色
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:27:38
	 */
	@Override
	public void deletePdaRole(SysPdaRoleModel model) {
		
		//删除角色信息
		dao.remove(model.getId());
		
		//删除角色权限关系
		dao.removePdaRoleMenuByRoleId(model.getId());
		
		//删除用户角色关系
		dao.removePdaUserRoleByRoleId(model.getId());
		
	}

	/**
	 * 根据角色ID查询该角色还未添加的菜单数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午2:43:11
	 */
	@Override
	public List<SysPdaRoleModel> queryNotAddPdaRoleDataByRoleId(SysPdaRoleModel model, Page p) {
		return dao.queryNotAddPdaRoleDataByRoleId(model, p);
	}

	/**
	 * 添加PDA角色菜单信息
	 * @param roleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:29:11
	 */
	@Override
	public void addPdaRoleMenu(List<SysPdaRoleModel> roleList) {
		dao.addPdaRoleMenu(roleList);
	}

	/**
	 * 删除PDA的角色菜单信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:38:43
	 */
	@Override
	public void deletePdaRoleMenu(List<SysPdaRoleModel> modelList) {
		dao.deletePdaRoleMenu(modelList);
	}

	/**
	 * 根据用户ID查询该用户的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午3:58:43
	 */
	@Override
	public PageList<Map<String, Object>> queryUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return dao.queryUserPdaRoleByUserId(model, p);
	}

	/**
	 * 根据用户ID查询该用户所有未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:12:49
	 */
	@Override
	public PageList<Map<String, Object>> queryAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return dao.queryAddUserPdaRoleByUserId(model, p);
	}

	/**
	 * 根据用户ID查询该用户在当前登录用户有的角色中未添加的的PDA角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:38:22
	 */
	@Override
	public PageList<Map<String, Object>> queryCurUserAddUserPdaRoleByUserId(SysPdaRoleModel model, Page p) {
		return dao.queryCurUserAddUserPdaRoleByUserId(model, p);
	}

	/**
	 * 添加用户的PDA角色信息
	 * @param userPdaRoleList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:44:28
	 */
	@Override
	public void addUserPdaRole(List<SysPdaRoleModel> userPdaRoleList) {
		dao.addUserPdaRole(userPdaRoleList);
	}

	/**
	 * 删除用户的PDA角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2019年1月22日 下午4:47:49
	 */
	@Override
	public void deleteUserPdaRole(List<SysPdaRoleModel> modelList) {
		dao.deleteUserPdaRole(modelList);
	}

}
