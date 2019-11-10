package com.hotent.sys.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.LogModuleDao;
import com.hotent.sys.persistence.model.LogModule;

/**
 * 
 * <pre>
 * 描述：日志模块管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ouxb
 * 邮箱:ouxb@jee-soft.cn
 * 日期:2014-10-29 16:57:57
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class LogModuleDaoImpl extends MyBatisDaoImpl<String, LogModule>
		implements LogModuleDao {

	@Override
	public String getNamespace() {
		return LogModule.class.getName();
	}

	@Override
	public Short getEnabledByName(String name) {
		return (Short)super.getOne("getEnabledByModule", name);
	}

	@Override
	public List<String> getNames() {
		return this.sqlSessionTemplate.selectList(this.getNamespace() + ".getNames");
	}

	@Override
	public void setEnable(String[] ids, Short flag) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ids", ids);
		params.put("flag", flag);
		super.updateByKey("setEnable", params);
	}


}
