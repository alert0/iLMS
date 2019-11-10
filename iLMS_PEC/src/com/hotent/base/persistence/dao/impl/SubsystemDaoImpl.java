package com.hotent.base.persistence.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.persistence.dao.SubsystemDao;
import com.hotent.base.persistence.model.Subsystem;

/**
 * 
 * <pre> 
 * 描述：子系统定义 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SubsystemDaoImpl extends MyBatisDaoImpl<String, Subsystem> implements SubsystemDao{

    @Override
    public String getNamespace() {
        return Subsystem.class.getName();
    }

	@Override
	public boolean isExist(Subsystem subsystem) {
		int rtn = (Integer) this.getOne("isExist", subsystem);
		return rtn>0;
	}

	@Override
	public List<Subsystem> getList() {
		return this.getByKey("getList", null);
	}

	@Override
	public void updNoDefault() {
		this.updateByKey("updNoDefault", null);
	}

	@Override
	public List<Subsystem> getSystemByUser(String userId) {
		return this.getByKey("getSystemByUser", userId);
	}
    
	
}

