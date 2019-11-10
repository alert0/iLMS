package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.SysOrgParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-04 11:39:44
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysOrgParamsDao extends Dao<String, SysOrgParams> {

	List<SysOrgParams> getByOrgId(String id);

	SysOrgParams getByOrgIdAndAlias(String orgId, String alias);

	void removeByOrgIds(String ... ids);

	void removeByAlias(String alias);
}
