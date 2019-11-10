package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysObjLogDao;
import com.hotent.sys.persistence.manager.SysObjLogManager;
import com.hotent.sys.persistence.model.SysObjLog;

/**
 * 
 * <pre> 
 * 描述：常用日志 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2016-09-27 10:33:03
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysObjLogManager")
public class SysObjLogManagerImpl extends AbstractManagerImpl<String, SysObjLog> implements SysObjLogManager{
	@Resource
	SysObjLogDao sysObjLogDao;
	@Override
	protected Dao<String, SysObjLog> getDao() {
		return sysObjLogDao;
	}
}
