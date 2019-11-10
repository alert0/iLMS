package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;

/**
 * 
 * <pre> 
 * 描述：组织关系定义 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-29 18:00:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgReldefManager extends Manager<String, OrgReldef>{
	/**
	 * 根据职务编码获取职务定义
	 * @param code
	 * @return
	 */
	public OrgReldef getByCode(String code);
	
	/**
	 * 根据职务名称获取职务
	 * @param name
	 * @return
	 */
	List<OrgReldef> getByName(String name);
	
	/**
	 * 通过用户ID获取其拥有的职务
	 * @param userId
	 * @return
	 */
	List<OrgReldef> getListByUserId(String userId);
	
	/**
	 * 通过用户账号获取其拥有的职务
	 * @param account
	 * @return
	 */
	List<OrgReldef>  getListByAccount(String account);
}
