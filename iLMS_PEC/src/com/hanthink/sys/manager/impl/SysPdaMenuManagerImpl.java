package com.hanthink.sys.manager.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysPdaMenuDao;
import com.hanthink.sys.manager.SysPdaMenuManager;
import com.hanthink.sys.model.SysPdaMenuModel;
import com.hanthink.sys.model.SysPdaUserManagerModel;
import com.hotent.base.api.Page;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：供应商分组 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysPdaMenuManager")
public class SysPdaMenuManagerImpl extends AbstractManagerImpl<String, SysPdaMenuModel> implements SysPdaMenuManager{
	@Resource
	SysPdaMenuDao sysPdaMenuDao;
	@Override
	protected Dao<String, SysPdaMenuModel> getDao() {
		return sysPdaMenuDao;
	}
	
	/**
	 * 左侧查询
	 */
	@Override
	public PageList<SysPdaMenuModel> querySysPdaMenuForPage(SysPdaMenuModel model, DefaultPage p) {
		return sysPdaMenuDao.querySysPdaMenuForPage(model, p);
	}

	/**
	 * 右侧查询
	 */
	@Override
	public PageList<SysPdaMenuModel> getRowClick(String userName, DefaultPage p) {
		return sysPdaMenuDao.getRowClick(userName, p);
	}

	/**
	 * 根据登录名查询菜单权限
	 */
	@Override
	public PageList<SysPdaMenuModel> queryAddPdaMenuByUserName(String userName,String menuDesc, Page p) {
		return sysPdaMenuDao.queryAddPdaMenuByUserName(userName,menuDesc, p);
	}

	/**
	 * 添加用户菜单权限
	 */
	@Override
	public void addPdaMenu(List<SysPdaMenuModel> pdaMenuList) {
		sysPdaMenuDao.addPdaMenu(pdaMenuList);
	}

	/**
	 * 删除用户菜单权限
	 */
	@Override
	public void deletePdaMenu(List<SysPdaMenuModel> modelList) {
		sysPdaMenuDao.deletePdaMenu(modelList);
	}

	

	
}
