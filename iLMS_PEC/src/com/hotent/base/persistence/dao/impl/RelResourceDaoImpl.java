package com.hotent.base.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.persistence.dao.RelResourceDao;
import com.hotent.base.persistence.model.RelResource;

/**
 * 
 * <pre> 
 * 描述：关联资源 DAO实现类
 * 构建组：x5-bpmx-platform
 * 作者:Li
 * 邮箱:liyang@jee-soft.cn
 * 日期:2016-08-11 14:47:16
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Repository
public class RelResourceDaoImpl extends MyBatisDaoImpl<String, RelResource> implements RelResourceDao{

    @Override
    public String getNamespace() {
        return RelResource.class.getName();
    }

	@Override
	public List<RelResource> getByResourceId(String resId) {
		return this.getByKey("getByResourceId", resId);
	}

	@Override
	public void removeByResId(String resId) {
		deleteByKey("removeByResId", resId);
	}
	
	
	
	
}

