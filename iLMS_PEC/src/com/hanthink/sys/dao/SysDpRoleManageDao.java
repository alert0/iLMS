package com.hanthink.sys.dao;
import java.util.List;

import com.hanthink.sys.model.SysDpRoleManageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：数据权限角色管理DAO
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysDpRoleManageDao extends Dao<String, SysDpRoleManageModel> {

	/**
	 * 分页查询数据角色数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午1:06:17
	 */
	PageList<SysDpRoleManageModel> queryDpRolePager(SysDpRoleManageModel model, Page p);

	/**
	 * 查询数据角色对应的数据权限基础数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午2:37:59
	 */
	List<SysDpRoleManageModel> queryDpRoleDataPager(SysDpRoleManageModel model, Page p);

	/**
	 * 根据数据角色ID删除该角色所对应的权限基础数据关系
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:39:31
	 */
	void deleteDpRoleDataByDataRoleId(String dataRoleId);

	/**
	 * 根据角色ID查询该角色还未添加的数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:55:22
	 */
	List<SysDpRoleManageModel> queryNotAddDpRoleDataByDataRoleId(SysDpRoleManageModel model, Page p);

	/**
	 * 添加数据角色的权限数据信息
	 * @param dpRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:24:18
	 */
	void addDpRoleData(List<SysDpRoleManageModel> dpRoleList);

	/**
	 * 删除数据角色的权限信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:38:19
	 */
	void deleteDpRoleData(List<SysDpRoleManageModel> modelList);

	/**
	 * 根据角色ID删除该角色所对应的用户角色关系信息
	 * @param dataRoleId
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午11:46:05
	 */
	void deleteDpUserRoleByDataRoleId(String dataRoleId);
}
