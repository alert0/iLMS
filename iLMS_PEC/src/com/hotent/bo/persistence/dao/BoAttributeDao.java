package com.hotent.bo.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bo.persistence.model.BoAttribute;

/**
 * 
 * <pre> 
 * 描述：业务实体定义属性 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BoAttributeDao extends Dao<String, BoAttribute> {
	
	/**
	 * 根据实体获取属性列表。
	 * @param entId
	 * @return
	 */
	List<BoAttribute> getByEntId(String entId);

	void removeByEntId(String entId);
}
