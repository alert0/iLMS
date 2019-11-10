package com.hotent.portal.persistence.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.portal.persistence.dao.SysLayoutToolsDao;
import com.hotent.portal.persistence.model.SysLayoutTools;

/**
 * 
 * <pre> 
 * 描述：布局工具设置 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 20:25:54
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysLayoutToolsDaoImpl extends MyBatisDaoImpl<String, SysLayoutTools> implements SysLayoutToolsDao{

    @Override
    public String getNamespace() {
        return SysLayoutTools.class.getName();
    }

	@Override
	public SysLayoutTools getByLayoutID(String layoutId, String toolsType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("layoutId", layoutId);
		params.put("toolsType", toolsType);
		return getUnique("getByLayoutIDAndType", params);
	}
	
}

