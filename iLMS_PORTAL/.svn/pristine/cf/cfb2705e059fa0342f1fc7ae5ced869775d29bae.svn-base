package com.hotent.bo.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bo.persistence.dao.BOEntDao;
import com.hotent.bo.persistence.model.BOEnt;

/**
 * 
 * <pre> 
 * 描述：业务对象定义 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class BOEntDaoImpl extends MyBatisDaoImpl<String, BOEnt> implements BOEntDao{

    @Override
    public String getNamespace() {
        return BOEnt.class.getName();
    }

	@Override
	public BOEnt getByName(String name) {
		return this.getUnique("getByName", name);
	}

	@Override
	public Integer getRefCountByName(String name) {
		return (Integer) this.getOne("getRefCountByName", name);
	}
	
}

