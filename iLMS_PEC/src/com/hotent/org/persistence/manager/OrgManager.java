package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.Org;

/**
 * 
 * <pre> 
 * 描述：组织架构 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgManager extends Manager<String, Org>{
	/**
	 * 根据Code取定义对象。
	 * @param code
	 * @return
	 */
	Org getByCode(String code);

	/**
	 * 根据用户ID获取组织列表
	 * @param userId
	 * @return
	 */
	List<Org> getOrgListByUserId(String userId);
	
	/**
	 * 根据用户账号获取组织列表
	 * @param account
	 * @return
	 */
	List<Org> getOrgListByAccount(String account);
	
	/**
	 * 获取主组织。
	 * @param userId
	 * @return
	 */
	Org getMainGroup(String userId);
	
	/**
	 * 根据父组织id获取其下子组织
	 * @param parentId
	 * @return
	 */
	List<Org> getByParentId(String parentId);
	
	List<Org> getByOrgName(String orgName);
	
	/**
	 * 根据路径名获取组织
	 * @param pathName
	 * @return
	 */
	List<Org> getByPathName(String pathName);
	
	/**
	 * 根据父级id以及维度id获取组织
	 * @param queryFilter
	 * @return
	 */
	List<Org> getByParentAndDem(QueryFilter queryFilter);
	
	/**
	 * 通过子组织获取父组织
	 * @param demId
	 * @param sonId
	 * @return
	 */
	Org getByDemIdAndSonId(String demId, String sonId);
	
	/**
	 * 查询数据
	 * @param id
	 * @return
	 */
	Org getOrgIdUserId(String id);
	/**
	 * 更新组织，包括其子组织
	 * @param org
	 */
	void updateByOrg(Org org);
	
}
