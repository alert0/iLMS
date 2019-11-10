package com.hanthink.sys.manager.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysDpRoleManageDao;
import com.hanthink.sys.manager.SysDpRoleManageManager;
import com.hanthink.sys.model.SysDpRoleManageModel;
import com.hotent.base.api.Page;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：数据权限角色管理实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysDpRoleManageManager")
public class SysDpRoleManageManagerImpl extends AbstractManagerImpl<String, SysDpRoleManageModel> implements SysDpRoleManageManager{
	@Resource
	SysDpRoleManageDao sysDpRoleManageDao;
	@Override
	protected Dao<String, SysDpRoleManageModel> getDao() {
		return sysDpRoleManageDao;
	}
	
	/**
	 * 分页查询数据角色数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午1:02:28
	 */
	@Override
	public PageList<SysDpRoleManageModel> queryDpRolePager(SysDpRoleManageModel model, Page p) {
		return sysDpRoleManageDao.queryDpRolePager(model, p);
	}

	/**
	 * 查询数据角色对应的数据权限基础数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午2:37:01
	 */
	@Override
	public List<SysDpRoleManageModel> queryDpRoleDataPager(SysDpRoleManageModel model, Page p) {
		return sysDpRoleManageDao.queryDpRoleDataPager(model, p);
	}

	/**
	 * 删除数据权限的角色信息
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:37:51
	 */
	@Override
	public void deleteDpRole(SysDpRoleManageModel model) {
		
		//删除角色信息
		sysDpRoleManageDao.remove(model.getId());
		
		//删除该角色所对应的权限基础数据关系
		sysDpRoleManageDao.deleteDpRoleDataByDataRoleId(model.getId());
		
		//删除角色对应的用户角色关系
		sysDpRoleManageDao.deleteDpUserRoleByDataRoleId(model.getId());
		
	}

	/**
	 * 根据角色ID查询该角色还未添加的数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:54:30
	 */
	@Override
	public List<SysDpRoleManageModel> queryNotAddDpRoleDataByDataRoleId(SysDpRoleManageModel model, Page p) {
		return sysDpRoleManageDao.queryNotAddDpRoleDataByDataRoleId(model, p);
	}

	/**
	 * 添加数据角色的权限数据信息
	 * @param dpRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:23:32
	 */
	@Override
	public void addDpRoleData(List<SysDpRoleManageModel> dpRoleList) {
		sysDpRoleManageDao.addDpRoleData(dpRoleList);
	}

	/**
	 * 删除数据角色的权限信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:37:29
	 */
	@Override
	public void deleteDpRoleData(List<SysDpRoleManageModel> modelList) {
		sysDpRoleManageDao.deleteDpRoleData(modelList);
	}
	
	

	
}
