package com.hotent.portal.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;

/**
 * 
 * <pre> 
 * 对象功能:布局管理  Service类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:15
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysIndexLayoutManageManager extends Manager<String, SysIndexLayoutManage>{
	public String getDefaultDesignHtml();
	/**
	 * 保存布局管理
	 * @param sysIndexLayoutManage
	 * @param type
	 */
	void save(SysIndexLayoutManage sysIndexLayoutManage, int type);
	
	//public List<SysIndexLayoutManage> getList(QueryFilter filter);
	
	public SysIndexLayoutManage getLayoutList(String userId,List<SysIndexColumn>  columnList, Short type);
	
	/**
	 * 找自己拥有权限的管理布局
	 * @return
	 */
	public String getMyHasRightsLayout();
	
	/**
	 * 找自己所属子组织没权限但设置默认布局
	 * @return
	 */
	public String getHasRightsLayout();
	
	/**
	 * 系统管理员的默认布局
	 * @return
	 */
	public String getManagerLayout();
	/**
	 * 
	 * @param layoutId
	 * @return
	 */
	public String obtainIndexManageData(String layoutId);
	/**
	 * 通过组织Id获取默认布局
	 * @param orgId
	 * @param layoutType 
	 * @return
	 */
	public SysIndexLayoutManage getByOrgId(String orgId);
	/**
	 * 判断布局名称是否重复
	 * @param name
	 * @return
	 */
	public Boolean isExistName(String name);
	/**
	 * 通过组织和布局类型获取实体
	 * @param orgId
	 * @param layoutType
	 * @return
	 */
	public SysIndexLayoutManage getByOrgIdAndLayoutType(String orgId, Short layoutType);
	/**
	 * 取消所有默认当前布局类型
	 * @param orgId
	 * @param layoutType
	 */
	public void cancelOrgIsDef(String orgId, Short layoutType);
	/**
	 * 获取手机的首页布局
	 * @param userId 
	 * @return
	 */
	public String obtainIndexManageMobileData(String layoutId);
	/**
	 * 获取pc端布局
	 * @param valueOf
	 * @param typePc
	 * @return
	 */
	public SysIndexLayoutManage getByIdAndType(String id, Short type);
	/**
	 * 获取手机布局id
	 * @param userId
	 */
	void getMobileLayoutId(String userId);
}