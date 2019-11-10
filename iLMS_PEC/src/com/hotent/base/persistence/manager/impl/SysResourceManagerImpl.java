package com.hotent.base.persistence.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.cache.ICache;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.base.persistence.dao.RelResourceDao;
import com.hotent.base.persistence.dao.SysResourceDao;
import com.hotent.base.persistence.manager.CacheUtil;
import com.hotent.base.persistence.manager.SysResourceManager;
import com.hotent.base.persistence.model.RelResource;
import com.hotent.base.persistence.model.SysResource;
import com.hotent.org.api.model.IUser;

/**
 * 
 * <pre> 
 * 描述：子系统资源 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysResourceManager")
public class SysResourceManagerImpl extends AbstractManagerImpl<String, SysResource> implements SysResourceManager{
	@Resource
	SysResourceDao sysResourceDao;
	
	@Resource
	RelResourceDao relResourceDao;
	
	@Resource
	ICache iCache;

	
	@Override
	protected Dao<String, SysResource> getDao() {
		return sysResourceDao;
	}
	
	@Override
	public List<SysResource> getBySystemId(String id) {
		List<SysResource> list= sysResourceDao.getBySystemId(id);
		
		return list;
	}

	@Override
	public SysResource getByResId(String id) {
		SysResource sysResource = this.get(id);
		sysResource.setRelResources(relResourceDao.getByResourceId(id));
		return sysResource;
	}
	
	@Override
	public void create(SysResource sysResource) {
		String resId=UniqueIdUtil.getSuid();
		sysResource.setId(resId);
		//先删除
		relResourceDao.removeByResId(resId);
		//在添加
		List<RelResource> relResources = sysResource.getRelResources();
		for (RelResource relResource : relResources) {
			relResource.setId(UniqueIdUtil.getSuid());
			relResource.setResId(resId);
			relResourceDao.create(relResource);
		}
		super.create(sysResource);
	}
	
	@Override
	public void update(SysResource sysResource) {
		String resId=sysResource.getId();
		//先删除
		relResourceDao.removeByResId(resId);
		//在添加
		List<RelResource> relResources = sysResource.getRelResources();
		for (RelResource relResource : relResources) {
			relResource.setId(UniqueIdUtil.getSuid());
			relResource.setResId(resId);
			relResourceDao.create(relResource);
		}
		super.update(sysResource);
	}

	@Override
	public List<SysResource> getBySystemAndRole(String systemId, String roleId) {
		return sysResourceDao.getBySystemAndRole(systemId, roleId);
	}

	@Override
	public boolean isExist(SysResource resource) {
		boolean rtn = sysResourceDao.isExist(resource);
		return rtn;
	}

	@Override
	public void removeByResId(String resId) {
		List<SysResource> list= getRecursionById(resId);
		for(SysResource resource:list){
			this.remove(resource.getId());
		}
	}
	

	@Override
	public void remove(String entityId) {
		relResourceDao.removeByResId(entityId);
		super.remove(entityId);
	}

	@Override
	public List<SysResource> getRecursionById(String resId) {
		List<SysResource> list=new ArrayList<SysResource>();
		
		SysResource resource=this.get(resId);
		list.add(resource);
		
		List<SysResource> tmpList =sysResourceDao.getByParentId(resId);
		if(BeanUtils.isEmpty(tmpList)) return list;
		
		for(SysResource sysResource:tmpList){
			recursion(sysResource,list);
		}
		return list;
	}
	
	private void recursion(SysResource sysResource,List<SysResource> list){
		list.add(sysResource);
		List<SysResource> tmpList =sysResourceDao.getByParentId(sysResource.getId());
		if(BeanUtils.isEmpty(tmpList)) return;
		
		for(SysResource resource:tmpList){
			recursion(resource,list);
		}
	}

	@Override
	public List<SysResource> getBySystemAndUser(String systemId, String userId) {
		List<SysResource> list=sysResourceDao.getBySystemAndUser(systemId, userId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasAlias(String systemId, IUser user, String alias) {
		Set<String> userSet = null;
		String userAliasCacheKey = CacheUtil.RESOURCE_USER_RES + systemId + user.getUserId();
		if(iCache.containKey(userAliasCacheKey)){
			userSet = (Set<String>) iCache.getByKey(userAliasCacheKey);
		}else{
			userSet = new HashSet<String>();
			List<SysResource> list = null;
			if(user.isAdmin()){
				list = getBySystemId(systemId);
			}else{
				list = getBySystemAndUser(systemId, user.getUserId());
			}
			for(SysResource r : list){
				userSet.add(r.getAlias());
			}
			iCache.add(userAliasCacheKey, userSet);
		}
		return null == userSet ? false : userSet.contains(alias);
	}
	
	@Override
	public String getReqActionResType(String systemId, String actionPath) {
		String sysResTypeCacheKey = CacheUtil.RESOURCE_REQ_RES_TYPE + systemId;
		Map<String, SysResource> reqResMap = (Map<String, SysResource>) iCache.getByKey(sysResTypeCacheKey);
		if(null != reqResMap){
			SysResource resource = reqResMap.get(actionPath);
			return null == resource ? null : resource.getSysResType();
		}
		return null;
	}

	/**
	 * 判断用户是否有请求操作权限
	 * @param systemId
	 * @param user
	 * @param actionPath
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年10月31日 下午12:06:41
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean hasReqAction(String systemId, IUser user, String actionPath) {
		Set<String> userSet = null;
		String userAliasCacheKey = CacheUtil.RESOURCE_REQ_USER_RES + systemId + user.getUserId();
		
		Set<String> sysSet = null;
		String sysAliasCacheKey = CacheUtil.RESOURCE_REQ_RES + systemId;
		
		Map<String, SysResource> reqResMap = null;
		String sysResTypeCacheKey = CacheUtil.RESOURCE_REQ_RES_TYPE + systemId;
		
		List<SysResource> allList = null;
		
		if(iCache.containKey(userAliasCacheKey)){
			userSet = (Set<String>) iCache.getByKey(userAliasCacheKey);
		}else{
			userSet = new HashSet<String>();
			List<SysResource> list = null;
			if(user.isAdmin()){
				if(null == allList){
					allList = getBySystemId(systemId);
				}
				list = allList;
			}else{
				list = getBySystemAndUser(systemId, user.getUserId());
			}
			for(SysResource r : list){
				if( StringUtil.isNotEmpty(r.getDefaultUrl()) && 
						(SysResource.SYS_RES_TYPE_REQAJAX.equals(r.getSysResType()) 
								|| SysResource.SYS_RES_TYPE_REQFILE.equals(r.getSysResType())) ){
					userSet.add(r.getDefaultUrl());
				}
			}
			iCache.add(userAliasCacheKey, userSet);
		}
		
		if(iCache.containKey(sysAliasCacheKey)){
			sysSet = (Set<String>) iCache.getByKey(sysAliasCacheKey);
		}else{
			sysSet = new HashSet<String>();
			if(null == allList){
				allList = getBySystemId(systemId);
			}
			for(SysResource r : allList){
				if(StringUtil.isNotEmpty(r.getDefaultUrl()) 
						&& (SysResource.SYS_RES_TYPE_REQAJAX.equals(r.getSysResType()) 
								|| SysResource.SYS_RES_TYPE_REQFILE.equals(r.getSysResType())) ){
					sysSet.add(r.getDefaultUrl());
				}
			}
			iCache.add(sysAliasCacheKey, sysSet);
		}
		
		if(iCache.containKey(sysResTypeCacheKey)){
			reqResMap = (Map<String, SysResource>) iCache.getByKey(sysResTypeCacheKey);
		}else{
			reqResMap = new HashMap<String, SysResource>();
			if(null == allList){
				allList = getBySystemId(systemId);
			}
			for(SysResource r : allList){
				if(StringUtil.isNotEmpty(r.getDefaultUrl()) 
						&& (SysResource.SYS_RES_TYPE_REQAJAX.equals(r.getSysResType()) 
								|| SysResource.SYS_RES_TYPE_REQFILE.equals(r.getSysResType()) ) ){
					reqResMap.put(r.getDefaultUrl(), r);
				}
			}
			iCache.add(sysResTypeCacheKey, reqResMap);
		}
		
		if(null != sysSet && sysSet.contains(actionPath)){
			return null == userSet ? false : userSet.contains(actionPath);
		}
		
		return true;
	}
	
	
}
