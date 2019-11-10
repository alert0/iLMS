package com.hotent.portal.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.portal.persistence.model.SysIndexTools;
import com.hotent.portal.persistence.model.SysLayoutTools;

/**
 * 
 * <pre> 
 * 描述：布局工具设置 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 20:25:54
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface SysLayoutToolsManager extends Manager<String, SysLayoutTools>{
	
	public SysLayoutTools getByLayoutID(String layoutId, String toolsType);
	
	/**
	 * 不同类型查出不同工具
	 * @param l
	 * @param tools 
	 * @return
	 */
	public List<SysIndexTools> queryTools(String layoutId, String tools);
}
