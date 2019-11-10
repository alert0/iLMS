package com.hotent.bpmx.api.helper.identity;

import java.util.List;
import java.util.Map;

import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.org.api.model.IUser;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bpmx-api
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-3-上午10:07:17
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface UserQueryPluginHelper {
	/**
	 * 
	 * 根据插件定义和流程变量查询bpmIdentity集合
	 * @param bpmPluginContexts
	 * @param variables
	 * @return 
	 * List&lt;BpmIdentity>
	 */
	public List<BpmIdentity> query(List<BpmPluginContext> bpmPluginContexts,Map<String, Object> variables);
	
	/**
	 * 
	 * 根据插件定义和流程变量查询用户集合
	 * @param bpmPluginContexts
	 * @param variables
	 * @return 
	 * List&lt;User>
	 */
	public List<IUser> queryUsers(List<BpmPluginContext> bpmPluginContexts,Map<String, Object> variables);
	
	/**
	 * 通过条件json 预览用户
	 * @param conditionJson
	 * @return
	 */
	public List<IUser> queryUsersByConditions(String conditionJson,Map map);
	
	
}
