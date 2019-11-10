package com.hotent.portal.persistence.manager.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.org.persistence.dao.OrgDao;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.portal.persistence.dao.SysIndexLayoutManageDao;
import com.hotent.portal.persistence.dao.SysIndexMyLayoutDao;
import com.hotent.portal.persistence.dao.UserSettingDao;
import com.hotent.portal.persistence.manager.UserSettingManager;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
import com.hotent.portal.persistence.model.UserSetting;
import com.hotent.sys.api.system.PropertyService;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：用户配置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-08-01 17:18:55
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("userSettingManager")
public class UserSettingManagerImpl extends AbstractManagerImpl<Long, UserSetting> implements UserSettingManager{
	@Resource
	UserSettingDao userSettingDao;
	@Resource
	SysIndexMyLayoutDao sysIndexMyLayoutDao;
	@Resource
	SysIndexLayoutManageDao sysIndexLayoutManageDao;
	@Resource
	OrgDao orgDao;
	@Resource
	OrgManager orgManger;
//	@Resource
//	BpmDefUserManager bpmdefUserManager;
	@Resource
	PropertyService propertyService;
	@Override
	protected Dao<Long, UserSetting> getDao() {
		return userSettingDao;
	}
	@Override
	public UserSetting getUserSettingByUserId(String userId) {
		return userSettingDao.getUserSettingByUserId(userId);
	}
	
	@Override
	public UserSetting getDefaultUserSettingByUserId(String currentUserId) {
		UserSetting userSetting = getUserSettingByUserId(currentUserId);
//		List<String> authorizeIdsByUserMap = bpmdefUserManager.getAuthorizeIdsByUserMap(SysIndexLayoutManage.INDEX_MANAGE);
		UserSetting initializedUserSetting = getUserSettingByUserId("0");
		String uiStyle = propertyService.getByAlias("UI_STYLE", "default");
		List<String> styleLayoutIds = Arrays.asList(initializedUserSetting.getLayoutId().split(","));
		if(userSetting == null){
			userSetting = new UserSetting();
			// 初始化的用户设置用户id为0
			userSetting.setSkinName(initializedUserSetting.getSkinName());
			userSetting.setUserId(currentUserId);
			// 如果没有设置，就取系统属性中的默认值
			userSetting.setIndexName(uiStyle);
		} 
//		else if (!styleLayoutIds.contains(userSetting.getLayoutId())) {
//			// 检查userSetting布局ID有没有使用权限
//			boolean checkAuthority = checkAuthority(userSetting,currentUserId, authorizeIdsByUserMap);
//			if(checkAuthority){
//				return userSetting;
//			}
//		}
		else{
			return userSetting;
		}
		// 获取当前用户的首页布局，如果个人首页布局为空，继续获取上级的首页布局，直至都为空，使用初始化用户设置的首页布局
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutDao.getByUserId(currentUserId);
		if(sysIndexMyLayout == null){
			// 当前组织ID
//			Org org = (Org) ContextUtil.getCurrentGroup();
			String layoutId =  null;
//			if(org != null){
//				if(StringUtil.isNotEmpty(org.getPath())){
//					String[] orgArray = org.getPath().split("\\.");
//					for (int i = orgArray.length-1; i >= 0; i--) {
//						layoutId = getLayoutByOrg(orgArray[i], authorizeIdsByUserMap);
//						if(layoutId != null){
//							break;
//						}
//					}
//				}
//			}
			if(layoutId == null){
				layoutId = styleLayoutIds.get(0);
				String skinName = "blue";
				if(("portal").equals(uiStyle)){
					layoutId = styleLayoutIds.get(1);
					skinName = "default";
				}else if(("other").equals(uiStyle)){
					layoutId = styleLayoutIds.get(2);
					skinName = "green";
				}
				userSetting.setLayoutId(layoutId);
				userSetting.setSkinName(skinName);
			}else{
				userSetting.setLayoutId(layoutId);
			}
			
		}else{
			userSetting.setLayoutId(sysIndexMyLayout.getId());
		}
		
		return userSetting;
	}
	
	/**
	 * 获取当前用户的在组织树中有权限的默认布局
	 * @param orgId
	 * @param authorizeIdsByUserMap
	 * @return
	 */
	private String getLayoutByOrg(String orgId, List<String> authorizeIdsByUserMap) {
		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageDao.getByOrgIdAndLayoutType(orgId, SysIndexLayoutManage.TYPE_PC);
		if (sysIndexLayoutManage != null) {
			if (authorizeIdsByUserMap.contains(sysIndexLayoutManage.getId().toString())) {
				return sysIndexLayoutManage.getId();
			}
		}
		return null;
	}
	
	/**
	 * 检查userSetting布局ID有没有使用权限
	 */
	private boolean checkAuthority(UserSetting userSetting,String currentUserId, List<String> authorizeIdsByUserMap){
		String layoutId = userSetting.getLayoutId();
		// 1.检查是不是我的首页布局
		SysIndexMyLayout sysIndexMyLayout = sysIndexMyLayoutDao.get(layoutId);
		if(sysIndexMyLayout != null){
			return true;
		}
		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageDao.get(layoutId);
		if(sysIndexLayoutManage != null && BeanUtils.isNotEmpty(sysIndexLayoutManage.getOrgId())){
			// 2.检查是不是授权的布局
			if(authorizeIdsByUserMap != null){
				for (String authorizeId : authorizeIdsByUserMap) {
					if(authorizeId.equals(layoutId)){
						return true;
					}
				}
			}
		}
		return false;
	}
}
