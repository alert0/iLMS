package com.hanthink.sys.dao;
import com.hanthink.sys.model.SysDpBaseDataModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：数据权限基础数据DAO接口
 * 作者:linzhuo
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface SysDpBaseDataDao extends Dao<String, SysDpBaseDataModel> {
	

	/**
	 * 查询数据权限基础数据的分类信息数据
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:31:07
	 */
	PageList<SysDpBaseDataModel> queryDpBaseDataType(SysDpBaseDataModel model, Page p);

	/**
	 * 根据分类编码查询数据权限基础数据信息
	 * @param model
	 * @param p
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午3:31:47
	 */
	PageList<SysDpBaseDataModel> queryDpBaseDataByType(SysDpBaseDataModel model, Page p);

	/**
	 * 根据业务主键判断数据是否存在
	 * @param id
	 * @param typeCode
	 * @param valueCode
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午4:54:33
	 */
	boolean isKeyExist(String id, String typeCode, String valueCode);

	/**
	 * 删除基础数据权限相关的数据角色与基础数据关系数据
	 * @param id
	 * @author ZUOSL	
	 * @DATE	2018年12月25日 下午5:49:14
	 */
	void deleteDpRoleData(String id);
	
	
}
