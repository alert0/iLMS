package com.hotent.portal.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;

/**
 * 
 * <pre> 
 * 对象功能:布局管理 Dao类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:14
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysIndexLayoutManageDao extends Dao<String, SysIndexLayoutManage> {
	/**
	 * 根据当前组织获取当前布局
	 * @param currentGroupId
	 * @param layoutType 
	 * @return
	 */
	SysIndexLayoutManage getByOrgId(String currentGroupId);
	
	/**
	 * 获取
	 * @param params
	 * @return
	 */
	List<SysIndexLayoutManage> getByUserIdFilter(Map<String,Object> params);
	
	/**
	 * 找自己所属子组织的没权限但设置默认布局
	 * @param orgIds
	 * @param isDef
	 * @return
	 */
	List<SysIndexLayoutManage> getManageLayout(String orgIds, Short isDef);
	
	/**
	 * 根据组织id设置默认布局
	 * @param orgId
	 */
	public void updateIsDef(String orgId);
	
	/**
	 * 判断布局名称是否重复
	 * @param name
	 * @return
	 */
	Boolean isExistName(String name);
	/**
	 * 通过组织id和布局类型获取实体
	 * @param orgId
	 * @param layoutType
	 * @return
	 */
	SysIndexLayoutManage getByOrgIdAndLayoutType(String orgId, Short layoutType);
	/**
	 * 取消当前组织当前布局类型的所有默认布局
	 * @param orgId
	 * @param layoutType
	 */
	void cancelOrgIsDef(String orgId, Short layoutType);
	/**
	 * 查该布局是不是组织的布局
	 * @param id
	 * @param typePc
	 * @param orgIndexLayoutId
	 * @return
	 */
	SysIndexLayoutManage getByOrgIdAndLayoutTypeAndLayoutId(String orgId, short layoutType, String layoutId);
	/**
	 * 获取pc布局
	 * @param id
	 * @param type
	 * @return
	 */
	SysIndexLayoutManage getByIdAndType(String id, Short type);
}
