package com.hanthink.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysPdaMenuDao;
import com.hanthink.sys.model.SysPdaMenuModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：PDA菜单权限分配 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SysPdaMenuDaoImpl extends MyBatisDaoImpl<String, SysPdaMenuModel> implements SysPdaMenuDao{

    @Override
    public String getNamespace() {
        return SysPdaMenuModel.class.getName();
    }

    /**
     * 左侧查询
     */
	@Override
	public PageList<SysPdaMenuModel> querySysPdaMenuForPage(SysPdaMenuModel model, DefaultPage p) {
		 return (PageList<SysPdaMenuModel>) this.getByKey("querySysPdaMenuForPage", model, p);
	}

	/**
	 * 右侧查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<SysPdaMenuModel> getRowClick(String userName, DefaultPage p) {
		Map<String,Object> map=new HashMap();
		map.put("userName", userName);
		return (PageList<SysPdaMenuModel>) this.getByKey("getRowClick", map,p);
	}

	/**
	 * 根据登录名查询待添加用户菜单权限信息
	 */
	@Override
	public PageList<SysPdaMenuModel> queryAddPdaMenuByUserName(String userName,String menuDesc, Page p) {
		Map<String,Object> map=new HashMap();
		map.put("userName", userName);
		map.put("menuDesc", menuDesc);
		return (PageList<SysPdaMenuModel>) this.getByKey("queryAddPdaMenuByUserName", map, p);
	}

	/**
	 * 添加菜单权限
	 */
	@Override
	public void addPdaMenu(List<SysPdaMenuModel> pdaMenuList) {
		for(SysPdaMenuModel m : pdaMenuList){
			this.insertByKey("addPdaMenu", m);
		}
	}

	/**
	 * 删除菜单权限
	 */
	@Override
	public void deletePdaMenu(List<SysPdaMenuModel> modelList) {
		for(SysPdaMenuModel m : modelList){
			this.deleteByKey("deletePdaMenu", m);
		}
	}

  

	
	
}

