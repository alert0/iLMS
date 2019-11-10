package com.hotent.org.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.OrgReldefDao;
import com.hotent.org.persistence.model.OrgReldef;

/**
 * 
 * <pre> 
 * 描述：组织关系定义 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-29 18:00:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class OrgReldefDaoImpl extends MyBatisDaoImpl<String, OrgReldef> implements OrgReldefDao{

    @Override
    public String getNamespace() {
        return OrgReldef.class.getName();
    }
    public OrgReldef getByCode(String code) {
    	return this.getUnique("getByCode", code);
	}
	@Override
	public List<OrgReldef> getByName(String name) {
		return this.getByKey("getByName", name);
	}
    
}

