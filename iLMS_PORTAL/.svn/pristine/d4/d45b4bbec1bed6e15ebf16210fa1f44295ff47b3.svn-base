package com.hotent.portal.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.db.api.Dao;
import com.hotent.portal.persistence.model.SysIndexColumn;

/**
 * 
 * <pre> 
 * 对象功能:首页栏目 Dao类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-31 09:41:14
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysIndexColumnDao extends Dao<String, SysIndexColumn> {

	Boolean isExistAlias(String alias, String id);
	
	/**
	 * 通过别名获取栏目
	 * 
	 * @param alias
	 * @return
	 */
	public SysIndexColumn getByColumnAlias(String alias);
 
	/**
	 * 获取当前用户有权限的栏目
	 * @param params
	 * @return
	 */
	public List<SysIndexColumn> getByUserIdFilter(Map<String, Object> params);
	
	public Integer getCounts();
	
}