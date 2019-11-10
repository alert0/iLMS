package com.hotent.org.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.api.context.ICurrentContext;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.manager.OrgUserManager;
import com.hotent.org.persistence.model.OrgUser;

/**
 * 
 * <pre> 
 * 描述：用户组织关系 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:27:31
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("orgUserManager")
public class OrgUserManagerImpl extends AbstractManagerImpl<String, OrgUser> implements OrgUserManager{
	@Resource
	OrgUserDao orgUserDao;
	@Override
	protected Dao<String, OrgUser> getDao() {
		return orgUserDao;
	}
	
	@Resource
	ICache iCache;
	
	
	public int updateUserPost(String id, String relId) {
	  return orgUserDao.updateUserPost(id, relId);
	}
	public OrgUser getOrgUser(String orgId, String userId, String relId) {
		return orgUserDao.getOrgUser(orgId, userId, relId);
	}
	public List<OrgUser> getListByOrgIdUserId(String orgId, String userId) {
		return orgUserDao.getListByOrgIdUserId(orgId, userId);
	}
	
	public  int removeByOrgIdUserId(String orgId,String userId){
		return orgUserDao.removeByOrgIdUserId(orgId, userId);
	}
	
	public void setMaster(String id){
		OrgUser orgUser=this.get(id);
		if(orgUser.getIsMaster()==0){
			orgUserDao.cancelUserMasterOrg(orgUser.getUserId());
			orgUserDao.setMaster(id);
		}
		else{
			orgUser.setIsMaster(0);
			orgUserDao.cancelUserMasterOrg(orgUser.getUserId());
		}
		
		//删除缓存。
		String userKey=ICurrentContext.CURRENT_ORG + orgUser.getUserId();
		iCache.delByKey(userKey);
		
	}
	
	
	public OrgUser getOrgUserMaster(String userId) {
		return orgUserDao.getOrgUserMaster(userId);
	}
	
	@Override
	public List getUserByGroup(QueryFilter queryFilter) {
		return orgUserDao.getUserByGroup(queryFilter);
	}
	
	/**
	 * 0  不是部门负责人  1 部门负责人  2 部门主负责人
	 */
	@Override
	public void setCharge(String userId,Boolean isCharge, String orgId) {
		List<OrgUser> orgUserList=this.getListByOrgIdUserId(orgId,userId);
		//判断是否为设置为主负责人，若是则先取消之前的主负责人
		if(BeanUtils.isNotEmpty(orgUserList)){
			if(orgUserList.get(0).getIsCharge() !=2  && isCharge){
				orgUserDao.updateCancleMainCharge(orgId);
			}
		}
		for(int i=0;i<orgUserList.size();i++){
			
			if(orgUserList.get(i).getIsCharge()==2){
				orgUserList.get(i).setIsCharge(1);
			}else if(orgUserList.get(i).getIsCharge()==1){
				orgUserList.get(i).setIsCharge(0);
			}else if(orgUserList.get(i).getIsCharge()==0){
				orgUserList.get(i).setIsCharge(1);
			}
			
			// 设置为主负责人
			if(orgUserList.get(i).getIsCharge() !=2  && isCharge){
				orgUserList.get(i).setIsCharge(2);
			}
			orgUserDao.update(orgUserList.get(i));
			//删除缓存。
			String userKey=ICurrentContext.CURRENT_ORG + orgUserList.get(i).getUserId();
			iCache.delByKey(userKey);
		}
	}
	@Override
	public List<OrgUser> getChargesByOrgId(String orgId, Boolean isMain) {
		return orgUserDao.getChargesByOrgId(orgId, isMain);
	}
	@Override
	public List getUserAndGroup(DefaultQueryFilter queryFilter) {
		return orgUserDao.getUserAndGroup(queryFilter);
	}
	@Override
	public List getSerachUser(DefaultQueryFilter queryFilter) {
		return orgUserDao.getSerachUser(queryFilter);
	}

}
