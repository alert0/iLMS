package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.UserDao;
 
import com.hotent.org.persistence.model.User;

/**
 * 
 * <pre> 
 * 描述：用户表 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:50
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class UserDaoImpl extends MyBatisDaoImpl<String, User> implements UserDao{

    @Override
    public String getNamespace() {
        return User.class.getName();
    }
 
	public User getByAccount(String account) {
		return this.getUnique("getByAccount", account);
	}
 
	/**
	 * 组织用户（对组织用户关系表只取一条）
	 */
	public List<User> queryOrgUser(QueryFilter queryFilter) {
		return this.getByQueryFilter("queryOrgUser", queryFilter);
	}
	
	/**
	 * 组织用户岗位（对组织用户关系表只取多条）
	 */
	public List queryOrgUserRel(QueryFilter queryFilter) {
		return this.getByQueryFilter("queryOrgUserRel", queryFilter);
	}

	public List<User> getUserListByOrgId(String orgId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("orgId", orgId);
		return this.getList("queryOrgUser", params);
 
	}
	/**
	 * 组织用户岗位
	 * @param orgId
	 * @return
	 */
	public List<User> getOrgUserRelByOrgId(String orgId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("orgId", orgId);
		return this.getList("queryOrgUserRel", params);
 
	}

	public List<User> getListByRelCode(String relCode) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("relCode", relCode);
		return this.getList("getUserListByRel", params);
	}

	public List<User> getListByRelId(String relId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("relId", relId);
		return this.getList("getUserListByRel", params);
	}

	public List<User> getUserListByRoleId(String roleId) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("roleId", roleId);
		return this.getList("getUserListByRole", params);
	}

	public List<User> getUserListByRoleCode(String roleCode) {
		Map<String, Object> params=new  HashMap<String,Object>();
		params.put("roleCode", roleCode);
		return this.getList("getUserListByRole", params);
	}

	@Override
	public boolean isUserExist(User user) {
		Integer rtn= (Integer) this.getOne("isUserExist", user);
		
		return rtn>0;
	}
	
	@Override
	public List<User> getByUserEmail(String email) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("email",email);
		return this.getByKey("getByUserEmail", params);
	}

	@Override
	public List<User> getUpUsersByUserId(String underUserId) {
		return this.getByKey("getUpUsersByUserId", underUserId);
	}

	@Override
	public User getUpUserByUserIdAndOrgId(Map<String, String> map) {
		return this.getUnique("getUpUserByUserIdAndOrgId", map);
	}

	@Override
	public List<User> getUnderUsersByUserId(String upUserId) {
		return this.getByKey("getUnderUsersByUserId", upUserId);
	}

	@Override
	public List<User> getUnderUserByUserIdAndOrgId(Map<String, String> map) {
		return this.getByKey("getUnderUserByUserIdAndOrgId", map);
	}

	@Override
	public User getLoginUser(String account) {
		return this.getUnique("getLoginUser", account);
	}
		@Override
	public List<User> getUserByIds(String[] ids) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userIds", ids);
		return this.getByKey("getByUserIds", params);
	}
	
	
}

