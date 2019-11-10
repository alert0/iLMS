package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.model.OrgUser;

/**
 * 
 * <pre> 
 * 描述：用户组织关系 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgUserDao extends Dao<String, OrgUser> {
	
	int updateUserPost(String id,String relId);
	OrgUser getOrgUser(String orgId,String userId,String relId);
	int removeByOrgIdUserId(String orgId,String userId);
	List<OrgUser> getListByOrgIdUserId(String orgId, String userId);
	
	/**
	 * 获取用户的主岗位组织关系
	 * @return
	 */
	OrgUser getOrgUserMaster(String userId);
	
	/**
	 * 设置主部门
	 * @param id
	 * @return
	 */
	boolean setMaster(String id);
	
	/**
	 * 取消主部门。
	 * @param userId
	 * @return
	 */
	boolean cancelUserMasterOrg(String userId);
	
	/**
	 * 根据组织和关系id获取用户列表。
	 * @param queryFilter
	 * @return
	 */
	List getUserByGroup(QueryFilter queryFilter) ;
	/**
	 * 根据用户ids删除该用户的组织
	 * @param ids
	 */
	void removeByUserIds(String[] ids);
	
	/**
	 * 取消orgId部门的主负责人
	 * @param orgId
	 */
	void updateCancleMainCharge(String orgId);
	/**
	 * 根据组织ID获取组织的负责人组织关系
	 * @param orgId  组织ID
	 * @param isMain 是否获取主负责人
	 * @return
	 */
	public List<OrgUser> getChargesByOrgId(String orgId, Boolean isMain);
	/**
	 * 根据组织id删除组织人员关系
	 * 将删除本组织及其子组织下的人员关系
	 * @param orgIds
	 */
	void removeByOrgIds(String ... orgIds);
	
	/**
	 * 根据用户id获取组织人员关系
	 * @param userid
	 * @return
	 */
	List<OrgUser> getByUserId(String userid);
	
	/**
	 * 根据组织id获取组织人员
	 * @param orgId
	 * @return
	 */
	List<OrgUser> getByOrgId(String orgId);
	
	/**
	 * 根据人员id和岗位id获取人员数据
	 * @param userId
	 * @param relId
	 * @return
	 */
	OrgUser getByUserIdAndRelId(String userId,String relId);
	
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
