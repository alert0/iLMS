package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.SysPropertiesDao;
import com.hotent.sys.persistence.model.SysProperties;

/**
 * 
 * <pre> 
 * 描述：SYS_PROPERTIES DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-07-28 09:19:53
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysPropertiesDaoImpl extends MyBatisDaoImpl<String, SysProperties> implements SysPropertiesDao{

    @Override
    public String getNamespace() {
        return SysProperties.class.getName();
    }

	@Override
	public List<String> getGroups() {
		List<String> list=this.getList("getGroups", null);
		return list;
	}

	@Override
	public boolean isExist(SysProperties sysProperties) {
		int rtn=(Integer) this.getOne("isExist", sysProperties);
		return rtn>0;
	}
	
}

