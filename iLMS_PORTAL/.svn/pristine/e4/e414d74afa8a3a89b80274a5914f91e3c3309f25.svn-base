package com.hotent.org.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.OrgRelDao;
import com.hotent.org.persistence.dao.OrgUserDao;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgUser;
import com.hotent.org.persistence.manager.OrgRelManager;

/**
 * 
 * <pre> 
 * 描述：组织关联关系 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-30 10:26:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("orgRelManager")
public class OrgRelManagerImpl extends AbstractManagerImpl<String, OrgRel> implements OrgRelManager{
	@Resource
	OrgRelDao orgRelDao;
	@Resource
	OrgUserDao orgUserDao;
	
	@Override
	protected Dao<String, OrgRel> getDao() {
		return orgRelDao;
	}
	public   OrgRel getByCode(String code) {
		return this.orgRelDao.getByCode(code);
	}
	public List<OrgRel> getListByOrgId(String orgId) {
		return this.orgRelDao.getListByOrgId(orgId);
	}
	public List<OrgRel> queryInfoList(QueryFilter queryFilter) {
		return this.orgRelDao.queryInfoList(queryFilter);
	}
	public OrgRel getByOrgIdRelDefId(String orgId, String relDefId) {
		return this.orgRelDao.getByOrgIdRelDefId(orgId, relDefId);
	}
	
	public List<OrgRel> getListByUserId(String userId) {
		return this.orgRelDao.getListByUserId(userId);
	}
	public List<OrgRel> getListByAccount(String account) {
		return this.orgRelDao.getListByAccount(account);
	}
	
	@Override
	 public void removeByIds(String ...ids){
		for(String id : ids){
			OrgRel rel = orgRelDao.get(id);
			List<OrgUser> orgUserList = orgUserDao.getByOrgId(rel.getOrgId());
			if(BeanUtils.isNotEmpty(orgUserList)){
				removeOrgUser(rel,orgUserList);
			}else{
				orgRelDao.remove(rel.getId());
			}
		}
	}
	
	/**
	 * 判断组织人员关系是否可删除，并作删除和更新操作
	 * @param rel
	 * @param orgUserList
	 */
	public void removeOrgUser(OrgRel rel ,List<OrgUser> orgUserList){
		for(OrgUser user : orgUserList){
			List<OrgUser> list = orgUserDao.getListByOrgIdUserId(user.getOrgId(), user.getUserId());
			if(list.size() == 1 && rel.getId().equals(list.get(0).getRelId())){//组织下该人只有一条数据，且刚好是与该岗位关联的情况
				OrgUser orgUser = orgUserDao.getByUserIdAndRelId(user.getUserId(), rel.getId());
				orgUser.setRelId(null);//置空岗位字段数据
				orgUserDao.update(orgUser);
			}else if(list.size() > 1){//有多条数据，那直接将该岗位对应的人员数据删掉即可
				OrgUser orgUser = orgUserDao.getByUserIdAndRelId(user.getUserId(), rel.getId());
				if(BeanUtils.isNotEmpty(orgUser)){
					orgUserDao.remove(orgUser.getId());
				}
			}
			orgRelDao.remove(rel.getId());
		}
	}
	@Override
	public List<OrgRel> getByRelDefId(String relDefId) {
		return orgRelDao.getByReldefId(relDefId);
	}
}
