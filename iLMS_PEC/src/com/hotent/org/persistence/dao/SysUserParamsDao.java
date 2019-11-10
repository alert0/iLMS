package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.SysUserParams;

/**
 * 
 * <pre> 
 * 描述：用户参数 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-01 17:11:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysUserParamsDao extends Dao<String, SysUserParams> {

	List<SysUserParams> getByUserId(String id);

	SysUserParams getByUserIdAndAlias(String userId, String alias);

	void removeByUserIds(String ... ids);

	void removeByAlias(String alias);
}
