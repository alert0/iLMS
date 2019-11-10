package com.hotent.org.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.SysParamsDao;
import com.hotent.org.persistence.model.SysParams;

/**
 * 
 * <pre> 
 * 描述：组织参数 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-10-31 14:29:12
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysParamsDaoImpl extends MyBatisDaoImpl<String, SysParams> implements SysParamsDao{

    @Override
    public String getNamespace() {
        return SysParams.class.getName();
    }

	@Override
	public SysParams getByAlias(String alias) {
		return this.getUnique("getByAlias", alias);
	}

	@Override
	public List<SysParams> getByType(String type) {
		return this.getByKey("getByType", type);
	}	
}