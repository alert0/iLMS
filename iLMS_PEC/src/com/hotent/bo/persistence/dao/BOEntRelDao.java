package com.hotent.bo.persistence.dao;
import java.util.List;

import com.hotent.base.db.api.Dao;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BOEntRel;

/**
 * 
 * <pre> 
 * 描述：BO 应用定义 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface BOEntRelDao extends Dao<String, BOEntRel> {
	
	/**
	 * 根据BO定义ID获取BO实体列表。
	 * @param defId
	 * @return
	 */
	List<BOEntRel> getByDefId(String defId);
	
	void removeByDefId(String defId);

	List<BOEntRel> getByEntId(String entId);
}
