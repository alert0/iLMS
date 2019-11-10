package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
 
import com.hotent.org.persistence.model.Org;

/**
 * 
 * <pre> 
 * 描述：组织架构 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-28 15:13:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgDao extends Dao<String, Org> {
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
	 * 根据父组织id获取其下子组织（包含父组织）
	 * @param parentId
	 * @return
	 */
	List<Org> getByParentId(String parentId);
	
	/**
	 * 根据组织名称获取组织列表
	 * @param orgName
	 * @return
	 */
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
	 * 根据子级查询父级
	 * @param demId
	 * @param sonId
	 * @return
	 */
	Org getByDemIdAndSonId(String demId, String sonId);

	Org getOrgIdUserId(String id);
}
