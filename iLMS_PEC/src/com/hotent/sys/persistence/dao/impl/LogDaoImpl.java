package com.hotent.sys.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.LogDao;
import com.hotent.sys.persistence.model.Log;

/**
 * 
 * <pre>
 * 描述：系统操作日志 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 14:37:00
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class LogDaoImpl extends MyBatisDaoImpl<String, Log> implements LogDao {

	@Override
	public String getNamespace() {
		return Log.class.getName();
	}

}
