package com.hotent.org.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.org.persistence.dao.UserUnderDao;
import com.hotent.org.persistence.model.UserUnder;

/**
 * 
 * <pre> 
 * 描述：下属管理 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-25 09:24:29
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class UserUnderDaoImpl extends MyBatisDaoImpl<String, UserUnder> implements UserUnderDao{

    @Override
    public String getNamespace() {
        return UserUnder.class.getName();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserUnder> getUserUnder(QueryFilter queryFilter) {
		return this.getByQueryFilter("getUserUnder", queryFilter);
	}

	@Override
	public void removeByOrgIds(String... ids) {
		for(String id : ids){
			this.deleteByKey("delByOrgId", id);
		}
	}

	@Override
	public void delByUpIdAndUderId(Map<String, String> map) {
		this.getByKey("delByUpIdAndUderId", map);
	}
	
}

