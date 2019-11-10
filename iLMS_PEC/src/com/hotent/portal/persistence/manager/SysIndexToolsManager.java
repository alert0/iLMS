package com.hotent.portal.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.SysIndexTools;

/**
 * 
 * <pre> 
 * 描述：首页工具 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 12:45:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysIndexToolsManager extends Manager<String, SysIndexTools>{

	
	/**
	 * 根据ids获取列表
	 * @param toolsIds
	 * @return
	 */
	public List<SysIndexTools> getToolsByIds(List<String> toolsIds);
	
	public Object getModelByHandler(String handler, Object[] args,
			Class<?>[] parameterTypes) throws Exception;
	
	public Object[] getDataParam(String dataParam, Map<String, Object> params);
	
	public Class<?>[] getParameterTypes(String dataParam,
			Map<String, Object> params);
	
}


