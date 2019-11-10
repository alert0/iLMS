package com.hotent.base.persistence.manager.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.base.persistence.dao.ResRoleDao;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.base.persistence.manager.ResRoleManager;
import com.hotent.base.persistence.model.ResRole;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：角色资源分配 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("resRoleManager")
public class ResRoleManagerImpl extends AbstractManagerImpl<String, ResRole> implements ResRoleManager{
	@Resource
	ResRoleDao resRoleDao;
	@Resource
	ICache iCache;
	
	
	
	@Override
	protected Dao<String, ResRole> getDao() {
		return resRoleDao;
	}
	@Override
	public List<ResRole> getAllByRoleId(String roleId) {

		return resRoleDao.getAllByRoleId(roleId);
	}
	

	@Override
	public void assignResByRoleSys(String resIds, String systemId, String roleId) {
		resRoleDao.removeByRoleAndSystem(roleId,systemId);
		
		String[] aryRes=resIds.split(",");
		for(String resId :aryRes){
			if("0".equals(resId)) continue;
			ResRole resRole = new ResRole();
			resRole.setId(UniqueIdUtil.getSuid());
			resRole.setRoleId(roleId);
			resRole.setSystemId(systemId);
			resRole.setResId(resId);
			resRoleDao.create(resRole);
		}
		
	}
	@Override
	public Map<String, Set<String>> getResRoleBySystem(String systemId) {
		String resStr=CacheUtil. RESOURCE_RES + systemId;
		if(iCache.containKey(resStr)){
			return (Map<String, Set<String>>) iCache.getByKey(resStr);
		}
		
		List<ResRole> list= resRoleDao.getResRoleBySystemId(systemId);
		Map<String, Set<String>> map=new HashMap<String, Set<String>>();
		
		for(ResRole res:list){
			String resAlias=res.getResAlias();
			if(map.containsKey(resAlias)){
				Set<String> set=map.get(resAlias);
				set.add(res.getRoleAlias());
			}
			else{
				Set<String> set=new HashSet<String>();
				set.add(res.getRoleAlias());
				map.put(resAlias, set);
			}
		}
		iCache.add(resStr, map);
		return map;
	}
	
	@Override
	public Map<String, Set<String>> getUrlRoleBySystem(String systemId) {
		String urlStr=CacheUtil.RESOURCE_URL + systemId;
		if(iCache.containKey(urlStr)){
			return (Map<String, Set<String>>) iCache.getByKey(urlStr);
		}
		
		List<ResRole> list= resRoleDao.getResRoleBySystemId(systemId);
		List<ResRole> urlList= resRoleDao.getUrlRoleBySystemId(systemId);
		
		urlList.addAll(list);
		
		Map<String, Set<String>> map=new HashMap<String, Set<String>>();
		
		for(ResRole res:list){
			String url=res.getUrl();
			if(StringUtil.isEmpty(url)) continue;
			if(map.containsKey(url)){
				Set<String> set=map.get(url);
				if(StringUtil.isNotEmpty(res.getRoleAlias())){
					set.add(res.getRoleAlias());
				}
			}
			else{
				Set<String> set=new HashSet<String>();
				if(StringUtil.isNotEmpty(res.getRoleAlias())){
					set.add(res.getRoleAlias());
				}
				map.put(url, set);
			}
		}
		//添加到缓存
		iCache.add(urlStr, map);
		return map;
	}
	
	
	@Override
	public boolean hasRight(String systemId, String alias) {
		Map<String, Set<String>> urlRoleMap= getUrlRoleBySystem( systemId);
		Set<String> resRoles=urlRoleMap.get(alias);
		if(BeanUtils.isEmpty(resRoles)) return false;
		
		UserDetails user=(UserDetails) ContextUtil.getCurrentUser();
		
		Collection<? extends GrantedAuthority>  roles=user.getAuthorities();
		Set<String> roleSet=new HashSet<String>();
		for(GrantedAuthority tmp:roles){
			roleSet.add(tmp.getAuthority());
		}
		for(Iterator<String> it=roleSet.iterator();it.hasNext();){
			String role=it.next();
			if(resRoles.contains(role)) return true;
		}
		
		return false;
	}
	@Override
	public void removeByRoleAndSystem(String roleId, String systemId) {
		resRoleDao.removeByRoleAndSystem(roleId, systemId);
	}
	
	
	
	
	
	
}
