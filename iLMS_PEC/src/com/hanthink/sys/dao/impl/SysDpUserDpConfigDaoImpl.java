package com.hanthink.sys.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysDpUserDpConfigDao;
import com.hanthink.sys.model.SysDpUserDpConfigModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：用户数据角色管理 DAO实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SysDpUserDpConfigDaoImpl extends MyBatisDaoImpl<String, SysDpUserDpConfigModel> implements SysDpUserDpConfigDao{

    @Override
    public String getNamespace() {
        return SysDpUserDpConfigModel.class.getName();
    }

    /**
     * 根据用户ID查询该用户的数据角色信息
     * @param model
     * @param p
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午9:53:16
     */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return this.getListByKey("queryUserDataRoleByUserId", model, p);
	}

	/**
	 * 根据用户ID查询该用户待添加的数据角色信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:46:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return this.getListByKey("queryAddUserDataRoleByUserId", model, p);
	}

	/**
	 * 添加用户的数据角色信息
	 * @param userDataRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:45:57
	 */
	@Override
	public void addUserDpRole(List<SysDpUserDpConfigModel> userDataRoleList) {
		for(SysDpUserDpConfigModel m : userDataRoleList){
			this.insertByKey("insert_addUserDpRole", m);
		}
	}

	/**
	 * 删除用户的数据角色信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午10:45:46
	 */
	@Override
	public void deleteUserDataRole(List<SysDpUserDpConfigModel> modelList) {
		for(SysDpUserDpConfigModel m : modelList){
			this.deleteByKey("deleteUserDataRole", m);
		}
	}

	/**
	 * 查询系统用户数据信息
	 * @param paramMap
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午10:37:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> querySysUserData(Map<String, Object> paramMap, Page p) {
		return this.getByKey("querySysUserData", paramMap, p);
	}

	/**
	 * 查询当前登录用户可为某个用户添加的数据角色
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2019年1月18日 上午10:41:59
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryCurUserAddUserDataRoleByUserId(SysDpUserDpConfigModel model, Page p) {
		return this.getListByKey("queryCurUserAddUserDataRoleByUserId", model, p);
	}


	
}

