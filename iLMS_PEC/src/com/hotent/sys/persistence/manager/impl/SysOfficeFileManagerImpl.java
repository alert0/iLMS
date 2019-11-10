package com.hotent.sys.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.sys.persistence.dao.SysOfficeFileDao;
import com.hotent.sys.persistence.manager.SysOfficeFileManager;
import com.hotent.sys.persistence.model.SysOfficeFile;

/**
 * 
 * <pre>
 * 描述：在线文档和WEB签章业务表 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-10-31 09:28:06
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysOfficeFileManager")
public class SysOfficeFileManagerImpl extends
		AbstractManagerImpl<String, SysOfficeFile> implements
		SysOfficeFileManager {
	@Resource
	SysOfficeFileDao sysOfficeFileDao;

	@Override
	protected Dao<String, SysOfficeFile> getDao() {
		return sysOfficeFileDao;
	}
}
