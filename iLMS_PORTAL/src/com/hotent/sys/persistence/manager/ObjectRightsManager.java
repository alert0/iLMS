package com.hotent.sys.persistence.manager;

import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.ObjectRights;
 
/**
 * 
 * <pre> 
 * 描述：对象权限 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:hugh zhuang
 * 邮箱:zxh@jee-soft.cn
 * 日期:2015-04-16 14:49:38
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface ObjectRightsManager extends Manager<String, ObjectRights>{

	/**
	 * 获取对象权限map
	 * @param objId
	 * @param objType
	 * @param beanId 
	 * @return
	 */
	Map<String, String> getRights(String objId, String objType, String beanId);

	void saveRights(String objId, String objType, String[] ownerName,
			String[] ownerType);
	
}
