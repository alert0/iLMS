package com.hotent.portal.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.impl.AbstractManagerImpl;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.bpmx.persistence.model.BpmDefUser;
import com.hotent.portal.persistence.dao.SysIndexToolsDao;
import com.hotent.portal.persistence.dao.SysLayoutToolsDao;
import com.hotent.portal.persistence.manager.SysLayoutToolsManager;
import com.hotent.portal.persistence.model.SysIndexTools;
import com.hotent.portal.persistence.model.SysLayoutTools;

/**
 * 
 * <pre> 
 * 描述：布局工具设置 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 20:25:54
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysLayoutToolsManager")
public class SysLayoutToolsManagerImpl extends AbstractManagerImpl<String, SysLayoutTools> implements SysLayoutToolsManager{
	@Resource
	SysLayoutToolsDao sysLayoutToolsDao;
	@Resource
	SysIndexToolsDao sysIndexToolsDao;
//	@Resource
//	BpmDefUserManager bpmdefUserManager;
	@Override
	protected Dao<String, SysLayoutTools> getDao() {
		return sysLayoutToolsDao;
	}
	
	@Override
	public SysLayoutTools getByLayoutID(String layoutId, String toolsType) {
		return sysLayoutToolsDao.getByLayoutID(layoutId, toolsType);
	}

	@Override
	public List<SysIndexTools> queryTools(String layoutId, String tools) {
		List<SysIndexTools> sysIndexToolsList = new ArrayList<SysIndexTools>();
//		List<String> authorizeIdsByUserMap = bpmdefUserManager.getAuthorizeIdsByUserMap(SysIndexTools.INDEX_TOOLS);
		SysLayoutTools sysLayoutTools = sysLayoutToolsDao.getByLayoutID(layoutId, tools);
//		if(sysLayoutTools != null){
//		String[] toolsArray = sysLayoutTools.getToolsIds().split(",");
//			for(String toolId : toolsArray){
//				if(authorizeIdsByUserMap.contains(toolId)){
//					SysIndexTools sysIndexTools = sysIndexToolsDao.get(toolId);
//					sysIndexToolsList.add(sysIndexTools);
//				}
//			}
//		}
		return sysIndexToolsList;
	}
}
