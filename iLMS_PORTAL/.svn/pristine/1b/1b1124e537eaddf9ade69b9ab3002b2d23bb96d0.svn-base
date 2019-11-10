package com.hotent.org.persistence.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.SysUserParamsDao;
import com.hotent.org.persistence.model.SysUserParams;

/**
 * 
 * <pre> 
 * 描述：用户参数 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-11-01 17:11:33
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class SysUserParamsDaoImpl extends MyBatisDaoImpl<String, SysUserParams> implements SysUserParamsDao{

    @Override
    public String getNamespace() {
        return SysUserParams.class.getName();
    }

	@Override
	public List<SysUserParams> getByUserId(String id) {
		return this.sqlSessionTemplate.selectList(getNamespace()+".getByUserId", id);
	}

	@Override
	public SysUserParams getByUserIdAndAlias(String userId, String alias) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("alias", alias);
		return this.sqlSessionTemplate.selectOne(getNamespace()+".getByUserIdAndAlias", map);
	}

	@Override
	public void removeByUserIds(String ... ids) {
		for (String userId : ids) {
			this.deleteByKey("removeByUserId", userId);
		}
	}

	@Override
	public void removeByAlias(String alias) {
		this.deleteByKey("removeByAlias",alias);
	}
	
}

