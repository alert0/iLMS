package com.hotent.bo.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.persistence.dao.BOEntDao;
import com.hotent.bo.persistence.dao.BoAttributeDao;
import com.hotent.bo.persistence.manager.BoAttributeManager;
import com.hotent.bo.persistence.model.BOEnt;
import com.hotent.bo.persistence.model.BoAttribute;

/**
 * 
 * <pre>
 * 描述：业务实体定义属性 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("boAttributeManager")
public class BoAttributeManagerImpl extends AbstractManagerImpl<String, BoAttribute> implements BoAttributeManager {
	@Resource
	BoAttributeDao boAttributeDao;
	@Resource
	BOEntDao boEntDao;

	@Override
	protected Dao<String, BoAttribute> getDao() {
		return boAttributeDao;
	}

	@Override
	public List<BoAttribute> getByEntId(String entId) {
		BOEnt ent=boEntDao.get(entId);
		List<BoAttribute> list= boAttributeDao.getByEntId(entId);
		for(BoAttribute attribute:list){
			attribute.setBoEnt(ent);
		}
		return list; 
	}

	@Override
	public void removeByEntId(String entId) {
		boAttributeDao.removeByEntId(entId);
	}

}
