package com.hanthink.sys.manager;

import java.util.List;

import com.hanthink.sys.model.SysDpRoleManageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：数据角色管理 处理接口
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysDpRoleManageManager extends Manager<String, SysDpRoleManageModel>{

	/**
	 * 分页查询数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午1:01:03
	 */
	PageList<SysDpRoleManageModel> queryDpRolePager(SysDpRoleManageModel model, Page p);

	/**
	 * 查询数据角色对应的数据权限基础数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午2:36:46
	 */
	List<SysDpRoleManageModel> queryDpRoleDataPager(SysDpRoleManageModel model, Page p);

	/**
	 * 删除数据权限的角色信息
	 * @param model
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:37:25
	 */
	void deleteDpRole(SysDpRoleManageModel model);

	/**
	 * 根据角色ID查询该角色还未添加的数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:54:15
	 */
	List<SysDpRoleManageModel> queryNotAddDpRoleDataByDataRoleId(SysDpRoleManageModel model, Page p);

	/**
	 * 添加数据角色的权限数据信息
	 * @param dpRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:23:14
	 */
	void addDpRoleData(List<SysDpRoleManageModel> dpRoleList);

	/**
	 * 删除数据角色的权限信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:37:13
	 */
	void deleteDpRoleData(List<SysDpRoleManageModel> modelList);

	
}
