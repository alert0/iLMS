package com.hanthink.sys.manager.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysDpUserDpConfigDao;
import com.hanthink.sys.manager.SysDpUserDpConfigManager;
import com.hanthink.sys.model.SysDpUserDpConfigModel;
import com.hotent.base.api.Page;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：用户数据角色 处理实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysDpUserDpConfigManager")
public class SysDpUserDpConfigManagerImpl extends AbstractManagerImpl<String, SysDpUserDpConfigModel> implements SysDpUserDpConfigManager{
	@Resource
	SysDpUserDpConfigDao sysDpUserDpConfigDao;
	
	@Override
	protected Dao<String, SysDpUserDpConfigModel> getDao() {
		return sysDpUserDpConfigDao;
	}

	/**
	 * 根据用户ID查询该用户的数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午9:51:23
	 */
	@Override
	public PageList<Map<String, Object>> queryUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return sysDpUserDpConfigDao.queryUserDataRoleByUserId(model, p);
	}

	/**
	 * 根据用户ID查询该用户待添加的数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:43:09
	 */
	@Override
	public PageList<Map<String, Object>> queryAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return sysDpUserDpConfigDao.queryAddUserDataRoleByUserId(model, p);
	}

	/**
	 * 添加用户的数据角色信息
	 * @param userDataRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:43:22
	 */
	@Override
	public void addUserDpRole(List<SysDpUserDpConfigModel> userDataRoleList) {
		sysDpUserDpConfigDao.addUserDpRole(userDataRoleList);
	}

	/**
	 * 删除用户的数据角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:43:32
	 */
	@Override
	public void deleteUserDataRole(List<SysDpUserDpConfigModel> modelList) {
		sysDpUserDpConfigDao.deleteUserDataRole(modelList);
	}

	/**
	 * 查询系统用户数据信息
	 * @param paramMap
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午10:36:45
	 */
	@Override
	public PageList<Map<String, Object>> querySysUserData(Map<String, Object> paramMap, Page p) {
		return sysDpUserDpConfigDao.querySysUserData(paramMap, p);
	}

	/**
	 * 查询当前登录用户可为某个用户添加的数据角色
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午10:40:59
	 */
	@Override
	public PageList<Map<String, Object>> queryCurUserAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return sysDpUserDpConfigDao.queryCurUserAddUserDataRoleByUserId(model, p);
	}

	
	

	
}
