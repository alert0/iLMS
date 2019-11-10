package com.hotent.org.persistence.dao;
import java.util.List;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.User;

/**
 * 
 * <pre> 
 * 描述：组织关联关系 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
public interface OrgRelDao extends Dao<String, OrgRel> {
	OrgRel getByCode(String code);
	
	List<OrgRel> getListByOrgId(String orgId);
	
	List<OrgRel> queryInfoList(QueryFilter queryFilter);
	
	OrgRel getByOrgIdRelDefId(String orgId,String relDefId);
	
	List<OrgRel>  getListByUserId(String userId);
	
	List<OrgRel>  getListByAccount(String account);
	
	/**
	 * 根据组织id删除组织岗位
	 * 包含删除其自组织下的所有岗位
	 * @param ids
	 */
	void removeByOrgIds(String ... ids);
	
	/**
	 * 根据职务id获取岗位列表
	 * @param reldefId
	 * @return
	 */
	List<OrgRel> getByReldefId(String reldefId);
}
