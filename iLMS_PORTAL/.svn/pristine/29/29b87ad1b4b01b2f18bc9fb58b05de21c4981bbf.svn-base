package com.hanthink.sys.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.sys.model.SysDpUserDpConfigModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：用户数据角色管理 DAO接口
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysDpUserDpConfigDao extends Dao<String, SysDpUserDpConfigModel> {

	/**
	 * 根据用户ID查询该用户的数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午9:52:02
	 */
	PageList<Map<String, Object>> queryUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p);

	/**
	 * 根据用户ID查询该用户待添加的数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:44:46
	 */
	PageList<Map<String, Object>> queryAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p);

	/**
	 * 添加用户的数据角色信息
	 * @param userDataRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:45:09
	 */
	void addUserDpRole(List<SysDpUserDpConfigModel> userDataRoleList);

	/**
	 * 删除用户的数据角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:45:32
	 */
	void deleteUserDataRole(List<SysDpUserDpConfigModel> modelList);

	/**
	 * 查询系统用户数据信息
	 * @param paramMap
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午10:37:16
	 */
	PageList<Map<String, Object>> querySysUserData(Map<String, Object> paramMap, Page p);

	/**
	 * 查询当前登录用户可为某个用户添加的数据角色
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午10:41:28
	 */
	PageList<Map<String, Object>> queryCurUserAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p);
	
	
}
