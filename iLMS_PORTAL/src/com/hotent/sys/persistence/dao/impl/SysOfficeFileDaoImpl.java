package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysOfficeFileDao;
import com.hotent.sys.persistence.model.SysOfficeFile;


/**
 * 
 * <pre>
 * 描述：在线文档和WEB签章业务表 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:hugh
 * 邮箱:zxh@jee-soft.cn
 * 日期:2014-10-31 09:28:06
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysOfficeFileDaoImpl extends MyBatisDaoImpl<String, SysOfficeFile>
		implements SysOfficeFileDao {

	@Override
	public String getNamespace() {
		return SysOfficeFile.class.getName();
	}

}
