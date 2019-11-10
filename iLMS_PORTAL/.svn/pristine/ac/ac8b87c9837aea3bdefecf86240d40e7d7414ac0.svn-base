package com.hotent.org.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.OrgAuthDao;
import com.hotent.org.persistence.model.OrgAuth;
import com.hotent.org.persistence.dao.OrgDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.OrgAuthManager;

/**
 * 
 * <pre> 
 * 描述：分级组织管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-20 14:30:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("orgAuthManager")
public class OrgAuthManagerImpl extends AbstractManagerImpl<String, OrgAuth> implements OrgAuthManager{
	@Resource
	OrgAuthDao orgAuthDao;
	@Resource
	OrgManager orgManager;
	
	@Override
	protected Dao<String, OrgAuth> getDao() {
		return orgAuthDao;
	}
	@Override
	public List<OrgAuth> getAllOrgAuth(QueryFilter queryFilter) {
		return orgAuthDao.getAllOrgAuth(queryFilter);
	}
	@Override
	public OrgAuth getByOrgIdAndUserId(Map<String, String> map) {
		return orgAuthDao.getByOrgIdAndUserId(map);
	}
	@Override
	public List<OrgAuth> getLayoutOrgAuth(String userId) {
		return orgAuthDao.getLayoutOrgAuth(userId);
	}
	@Override
	public List<OrgAuth> getByUserId(String userId) {
		List<OrgAuth> groupAuthList = orgAuthDao.getByUserId(userId);
		List<OrgAuth> authList = new ArrayList<OrgAuth>();
		//若分级管理中，两者对应的组织是父子关系，且父子分配的管理权限一致，那么子组织不返回前台
		for(OrgAuth auth : groupAuthList){
			boolean isChild = false;
			for(OrgAuth groupAuth : groupAuthList){
				//判断是父子关系
				boolean flag1 = (auth.getId()!= groupAuth.getId()) && auth.getDemId().equals(groupAuth.getDemId()) && auth.getOrgPath().startsWith(groupAuth.getOrgPath());
				//判断父子权限是否一致
				boolean flag2 = false;
				if (groupAuth.getOrgPerms().equals(auth.getOrgPerms())
						&& groupAuth.getUserPerms().equals(auth.getUserPerms())
						&& groupAuth.getOrgauthPerms().equals(auth.getOrgauthPerms())
						&& groupAuth.getPosPerms().equals(auth.getPosPerms())){
					flag2 = true;
				}
				if(flag1 && flag2)
					isChild = true;
			}
			if(!isChild)authList.add(auth); 
		}
		
		return authList;
	}
}
