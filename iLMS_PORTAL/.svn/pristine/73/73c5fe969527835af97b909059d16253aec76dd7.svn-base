package com.hotent.base.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.base.persistence.dao.SubsystemDao;
import com.hotent.base.persistence.manager.SubsystemManager;
import com.hotent.base.persistence.model.Subsystem;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：子系统定义 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("subsystemManager")
public class SubsystemManagerImpl extends AbstractManagerImpl<String, Subsystem> implements SubsystemManager{
	@Resource
	SubsystemDao subsystemDao;
	
	@Override
	protected Dao<String, Subsystem> getDao() {
		return subsystemDao;
	}
	
	@Override
	public boolean isExist(Subsystem subsystem) {
		return subsystemDao.isExist(subsystem);
	}

	@Override
	public List<Subsystem> getList() {
		return subsystemDao.getList();
	}

	@Override
	public Subsystem getDefaultSystem(String userId) {
		List<Subsystem> list=subsystemDao.getSystemByUser(userId);
		if(BeanUtils.isEmpty(list)) return null;
		
		for(Subsystem system:list){
			if(1==system.getIsDefault()) return system;
		}
		return list.get(0);
	}

	@Override
	public void setDefaultSystem(String systemId) {
		Subsystem subSystem=subsystemDao.get(systemId);
		if(subSystem.getIsDefault()==1){
			subSystem.setIsDefault(0);
		}
		else{
			subsystemDao.updNoDefault();
			subSystem.setIsDefault(1);
		}
		subsystemDao.update(subSystem);
	}


	@Override
	public List<Subsystem> getCuurentUserSystem() {
		IUser user = ContextUtil.getCurrentUser();
		if(BeanUtils.isEmpty(user)) return null;
		if(user.isAdmin()){
			return subsystemDao.getList();
		}
		return subsystemDao.getSystemByUser(user.getUserId());
	}
}
