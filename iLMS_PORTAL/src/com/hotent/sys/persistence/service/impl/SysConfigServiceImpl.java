/**
 * 描述：TODO
 * 包名：com.hotent.platform.system.service.impl
 * 文件名：SysConfigServiceImpl.java
 * 作者：win-mailto:chensx@jee-soft.cn
 * 日期2014-3-27-上午10:50:50
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.sys.persistence.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.org.api.constant.GroupTypeConstant;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.api.service.IUserGroupService;
import com.hotent.sys.api.model.IConditionScript;
import com.hotent.sys.util.ContextUtil;

 

/**
 * <pre> 
 * 描述：配置参数服务实现类
 * 构建组：x5-bpmx-platform
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-3-27-上午10:50:50
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class SysConfigServiceImpl implements IConditionScript{
	
	@Resource
	IUserGroupService userGroupService;

	public boolean isSuccess(String config, String name){
		return true;
	}
	
	public boolean isFailure(int count){
		return (count>6);
	}
	
	/**
	 * 判断用户是否具有roleCode角色
	 * @param roleCode
	 * @return
	 */
	public boolean isInRole(String roleCode){
		List<IGroup> list =  userGroupService.getGroupsByGroupTypeUserId(GroupTypeConstant.ROLE.key(), ContextUtil.getCurrentUserId());
		for (IGroup group : list) {
			if(group.getGroupCode().equals(roleCode)){
				return true;
			}
		}
		return false;
	}
	
	public Set<String> getExecutor(String arg0, Long arg1){
		return null;
	}
	public Set<String> getHandlerUser(String nodeId, String instanceId){
		return null;
	}
}
