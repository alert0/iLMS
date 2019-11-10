package com.hanthink.sys.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.sys.dao.SysDpBaseDataDao;
import com.hanthink.sys.model.SysDpBaseDataModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：数据权限基础数据 DAO实现类
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Repository
public class SysDpBaseDataDaoImpl extends MyBatisDaoImpl<String, SysDpBaseDataModel> implements SysDpBaseDataDao{

    @Override
    public String getNamespace() {
        return SysDpBaseDataModel.class.getName();
    }

    /**
     * 查询数据权限基础数据的分类信息数据
     * @param model
     * @param p
     * @return
     * @author ZUOSL	
     * @DATE	2018年12月25日 下午3:32:28
     */
	@Override
	public PageList<SysDpBaseDataModel> queryDpBaseDataType(SysDpBaseDataModel model, Page p) {
		return (PageList<SysDpBaseDataModel>) this.getByKey("queryDpBaseDataType", model, p);
	}

	/**
	 * 根据分类编码查询数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:32:14
	 */
	@Override
	public PageList<SysDpBaseDataModel> queryDpBaseDataByType(SysDpBaseDataModel model, Page p) {
		return (PageList<SysDpBaseDataModel>) this.getByKey("queryDpBaseDataByType", model, p);
	}

	/**
	 * 根据业务主键判断数据是否存在
	 * @param id
	 * @param typeCode
	 * @param valueCode
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午4:54:45
	 */
	@Override
	public boolean isKeyExist(String id, String typeCode, String valueCode) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("id", id);
		params.put("typeCode", typeCode);
		params.put("valueCode", valueCode);
		Integer sn=(Integer)getOne("isKeyExist", params);
		return sn>0?true:false;
	}

	/**
	 * 删除基础数据权限相关的数据角色与基础数据关系数据
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午5:49:40
	 */
	@Override
	public void deleteDpRoleData(String id) {
		this.deleteByKey("deleteDpRoleData", id);
	}

	
	
    
	
}

