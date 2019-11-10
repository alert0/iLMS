package com.hotent.org.persistence.dao;
import java.util.List;
import java.util.Map;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.UserUnder;

/**
 * 
 * <pre> 
 * 描述：下属管理 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-25 09:24:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface UserUnderDao extends Dao<String, UserUnder> {
	
	/**
	 * 获取下级用户
	 * @param params
	 * @return
	 */
	List<UserUnder> getUserUnder(QueryFilter queryFilter);
	
	/**
	 * 根据ids删除下属
	 * @param ids
	 */
	void removeByOrgIds(String... ids);
	
	/**
	 * 根据上级id与下级id删除上下级关系
	 * @param map
	 */
	void delByUpIdAndUderId(Map<String,String> map);
}
