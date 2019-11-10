package com.hanthink.sys.manager.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.sys.dao.SysDpBaseDataDao;
import com.hanthink.sys.manager.SysDpBaseDataManager;
import com.hanthink.sys.model.SysDpBaseDataModel;
import com.hotent.base.api.Page;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre> 
 * 描述：数据权限基础数据 处理实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("SysDpBaseDataManager")
public class SysDpBaseDataManagerImpl extends AbstractManagerImpl<String, SysDpBaseDataModel> implements SysDpBaseDataManager{
	@Resource
	SysDpBaseDataDao sysDpBaseDataDao;
	@Override
	protected Dao<String, SysDpBaseDataModel> getDao() {
		return sysDpBaseDataDao;
	}
	
	/**
	 * 查询数据权限基础数据的分类信息数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:31:21
	 */
	@Override
	public PageList<SysDpBaseDataModel> queryDpBaseDataType(SysDpBaseDataModel model, Page p) {
		return sysDpBaseDataDao.queryDpBaseDataType(model, p);
	}
	
	/**
	 * 根据分类编码查询数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:57:21
	 */
	@Override
	public PageList<SysDpBaseDataModel> queryDpBaseDataByType(SysDpBaseDataModel model, Page p) {
		return sysDpBaseDataDao.queryDpBaseDataByType(model, p);
	}

	/**
	 * 根据业务主键判断数据是否存在
	 * @param id
	 * @param typeCode
	 * @param valueCode
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午4:52:18
	 */
	@Override
	public boolean isKeyExist(String id, String typeCode, String valueCode) {
		return sysDpBaseDataDao.isKeyExist(id, typeCode, valueCode);
	}

	/**
	 * 删除数据权限基础数据
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午5:25:38
	 */
	@Override
	public void deleteSysDpBaseData(String id) {
		
		//删除数据权限基础数据
		sysDpBaseDataDao.remove(id);
		
		//删除跟该基础数据权限相关的数据角色与基础数据关系数据
		sysDpBaseDataDao.deleteDpRoleData(id);
		
	}
	
	
	

	
}
