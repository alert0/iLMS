package com.hotent.org.persistence.manager;

import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;
import com.hotent.org.persistence.model.OrgUser;

/**
 * 
 * <pre> 
 * 描述：用户组织关系 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgUserManager extends Manager<String, OrgUser>{
	
	int updateUserPost(String id, String relId);
	
	/**
	 * 根据组织用户和关系id查找关联关系。
	 * @param orgId
	 * @param userId
	 * @param relId
	 * @return
	 */
	OrgUser getOrgUser(String orgId,String userId,String relId);
    
	/**
	 * 根据用户和组织ID获取关联关系。
	 * @param orgId
	 * @param userId
	 * @return
	 */
	List<OrgUser> getListByOrgIdUserId(String orgId, String userId);
	
	/**
	 * 获取用户的主岗位组织关系
	 * @return
	 */
	OrgUser getOrgUserMaster(String userId);
	
    int removeByOrgIdUserId(String orgId,String userId);
    
    /**
     * 设置主组织关系。
     * @param id		主键
     * @return
     */
	void setMaster(String id);
	
	/**
	 * 设置部门负责人，同一个用户只能是某个组织的主负责人或负责人
	 * 不能既是主负责人又是负责人
	 * @param userId 用户id
	 * * @param isCharge false为取消操作，true为设置操作
	 * @param orgId 组织id
	 */
	void setCharge(String userId,Boolean isCharge, String orgId);
	
	/**
	 * 根据queryfilter查询部门或岗位下的人员。
	 * @param queryFilter
	 * @return
	 */
	public List getUserByGroup(QueryFilter queryFilter) ;
	/**
	 * 根据组织ID获取组织的负责人组织关系
	 * @param orgId  组织ID
	 * @param isMain 是否获取主负责人(只有一个)
	 * @return
	 */
	public List<OrgUser> getChargesByOrgId(String orgId,Boolean isMain);

	/**
	 * 获取用户
	 * @param queryFilter
	 * @return
	 */
	List getUserAndGroup(DefaultQueryFilter queryFilter);
	
	/**
	 * 手机端查询用户
	 * @param queryFilter
	 * @return
	 */
	List getSerachUser(DefaultQueryFilter queryFilter);
}
