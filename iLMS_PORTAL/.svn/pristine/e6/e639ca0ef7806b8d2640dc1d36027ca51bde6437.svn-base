package com.hanthink.sys.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysDpRoleManageDao;
import com.hanthink.sys.model.SysDpRoleManageModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：数据角色管理 DAO实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SysDpRoleManageDaoImpl extends MyBatisDaoImpl<String, SysDpRoleManageModel> implements SysDpRoleManageDao{

    @Override
    public String getNamespace() {
        return SysDpRoleManageModel.class.getName();
    }

    /**
     * 分页查询数据角色数据信息
     * @param model
     * @param p
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月26日 下午1:06:45
     */
	@Override
	public PageList<SysDpRoleManageModel> queryDpRolePager(SysDpRoleManageModel model, Page p) {
		return (PageList<SysDpRoleManageModel>) this.getByKey("queryDpRole", model, p);
	}

	/**
	 * 查询数据角色对应的数据权限基础数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午2:38:11
	 */
	@Override
	public List<SysDpRoleManageModel> queryDpRoleDataPager(SysDpRoleManageModel model, Page p) {
		return (PageList<SysDpRoleManageModel>) this.getByKey("queryDpRoleBaseData", model, p);
	}

	/**
	 * 根据数据角色ID删除该角色所对应的权限数据关系
	 * @param dataRoleId
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:40:15
	 */
	@Override
	public void deleteDpRoleDataByDataRoleId(String dataRoleId) {
		this.deleteByKey("deleteDpRoleDataByDataRoleId", dataRoleId);
	}

	/**
	 * 根据角色ID查询该角色还未添加的数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午3:55:39
	 */
	@Override
	public List<SysDpRoleManageModel> queryNotAddDpRoleDataByDataRoleId(SysDpRoleManageModel model, Page p) {
		return (PageList<SysDpRoleManageModel>) this.getByKey("queryNotAddDpRoleDataByDataRoleId", model, p);
	}

	/**
	 * 添加数据角色的权限数据信息
	 * @param dpRoleList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:24:43
	 */
	@Override
	public void addDpRoleData(List<SysDpRoleManageModel> dpRoleList) {
		for(SysDpRoleManageModel model : dpRoleList){
			this.insertByKey("insert_addDpRoleData", model);
		}
	}

	/**
	 * 删除数据角色的权限信息
	 * @param modelList
	 * @author ZUOSL	
	 * @DATE	2018年12月26日 下午4:38:46
	 */
	@Override
	public void deleteDpRoleData(List<SysDpRoleManageModel> modelList) {
		for(SysDpRoleManageModel model : modelList){
			this.deleteByKey("deleteDpRoleData", model);
		}
	}

	/**
	 * 根据角色ID删除该角色所对应的用户角色关系信息
	 * @param dataRoleId
	 * @author ZUOSL	
	 * @DATE	2018年12月27日 下午11:46:49
	 */
	@Override
	public void deleteDpUserRoleByDataRoleId(String dataRoleId) {
		this.deleteByKey("deleteDpUserRoleByDataRoleId", dataRoleId);
	}

	
}

