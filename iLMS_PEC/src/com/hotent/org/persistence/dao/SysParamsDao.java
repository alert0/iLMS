package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.SysParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-10-31 14:29:12
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysParamsDao extends Dao<String, SysParams> {

	SysParams getByAlias(String alias);

	List<SysParams> getByType(String type);
}
