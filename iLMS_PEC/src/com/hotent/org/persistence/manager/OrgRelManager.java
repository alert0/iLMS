package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.OrgRel;

/**
 * 
 * <pre> 
 * 描述：组织关联关系 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgRelManager extends Manager<String, OrgRel>{
	OrgRel getByCode(String code);
	
	/**
	 * 根据组织ID获取岗位列表
	 * @param orgId
	 * @return
	 */
	List<OrgRel> getListByOrgId(String orgId);
	
	
	List<OrgRel> queryInfoList(QueryFilter queryFilter);
	
	/**
	 * 根据组织ID和职务ID获取岗位定义
	 * @param orgId
	 * @param relDefId
	 * @return
	 */
	OrgRel getByOrgIdRelDefId(String orgId,String relDefId);
	
	/**
	 * 根据职务ID获取岗位列表
	 * @param relDefId 职务ID
	 * @return
	 */
	List<OrgRel> getByRelDefId(String relDefId);
	
	/**
	 * 根据用户ID获取对应的岗位列表
	 * @param userId
	 * @return
	 */
	List<OrgRel>  getListByUserId(String userId);
	
	/**
	 * 根据用户账号获取对应的岗位列表
	 * @param account
	 * @return
	 */
	List<OrgRel>  getListByAccount(String account);
}
