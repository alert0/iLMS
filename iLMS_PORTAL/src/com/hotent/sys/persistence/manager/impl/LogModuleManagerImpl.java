package com.hotent.sys.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.api.event.LogModuleCons;
import com.hotent.sys.persistence.dao.LogModuleDao;
import com.hotent.sys.persistence.manager.LogModuleManager;
import com.hotent.sys.persistence.model.LogModule;

/**
 * 
 * <pre>
 * 描述：日志模块管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("logModuleManager")
public class LogModuleManagerImpl extends
		AbstractManagerImpl<String, LogModule> implements LogModuleManager {
	@Resource
	LogModuleDao logModuleDao;

	@Override
	protected Dao<String, LogModule> getDao() {
		return logModuleDao;
	}

	@Override
	public Boolean isEnabled(String module) {
		if (StringUtil.isEmpty(module))
			return false;
		Short val = logModuleDao.getEnabledByName(module);
		if (val == null) {
			return false;
		} else {
			return val == 0 ? false : true;
		}
	}

	@Override
	public List<String> getNames() {
		List<String> list = logModuleDao.getNames();
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public void innsert(LogModuleCons[] cons, List<String> bdNames) {
		for (LogModuleCons lmCon : cons) {
			if (!bdNames.contains(lmCon.value())) { // 如果不存在数据库中，则插入
				LogModule logModule = this.getInitLogModule(lmCon);
				this.create(logModule);
			}
		}
	}

	@Override
	public void innsertAll(LogModuleCons[] cons) {
		for (LogModuleCons lmCon : cons) {
			LogModule logModule = this.getInitLogModule(lmCon);
			this.create(logModule);
		}
	}

	/**
	 * 根据常量获取初始化对象，默认是禁用的
	 * 
	 * @param lmCon
	 * @return
	 */
	private LogModule getInitLogModule(LogModuleCons lmCon) {
		LogModule logModule = new LogModule();
		logModule.setId(UniqueIdUtil.getSuid());
		logModule.setName(lmCon.value());
		logModule.setAlias(lmCon.toString());
		logModule.setEnabled(LogModule.DISABLED);
		return logModule;
	}

	@Override
	public void setEnable(String[] ids, Short flag) {
		logModuleDao.setEnable(ids,flag);
	}

}
