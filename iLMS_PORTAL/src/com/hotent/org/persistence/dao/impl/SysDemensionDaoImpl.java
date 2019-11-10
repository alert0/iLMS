package com.hotent.org.persistence.dao.impl;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.SysDemensionDao;
import com.hotent.org.persistence.model.SysDemension;

/**
 * 
 * <pre> 
 * 描述：维度管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-19 15:30:09
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysDemensionDaoImpl extends MyBatisDaoImpl<String, SysDemension> implements SysDemensionDao{

    @Override
    public String getNamespace() {
        return SysDemension.class.getName();
    }

	@Override
	public SysDemension getByCode(String code) {
		return this.getUnique("getByCode", code);
	}

	@Override
	public SysDemension getDefaultDemension() {
		return this.getUnique("getDefaultDemension",null);
	}

	@Override
	public void setNotDefaultDemension() {
		this.updateByKey("setNotDefaultDemension");
	}
	
}

