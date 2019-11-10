package com.hotent.bo.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bo.persistence.dao.BOEntRelDao;
import com.hotent.bo.persistence.manager.BOEntRelManager;
import com.hotent.bo.persistence.model.BOEntRel;

/**
 * 
 * <pre> 
 * 描述：BO 应用定义 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-04-05 09:58:28
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bOEntRelManager")
public class BOEntRelManagerImpl extends AbstractManagerImpl<String, BOEntRel> implements BOEntRelManager{
	@Resource
	BOEntRelDao bOEntRelDao;
	@Override
	protected Dao<String, BOEntRel> getDao() {
		return bOEntRelDao;
	}
	@Override
	public List<BOEntRel> getByDefId(String defId) {
		return bOEntRelDao.getByDefId(defId);
	}
	@Override
	public void removeByDefId(String defId) {
		bOEntRelDao.removeByDefId(defId);
	}
	@Override
	public List<BOEntRel> getByEntId(String entId) {
		return bOEntRelDao.getByEntId(entId);
	}
	@Override
	public BOEntRel getByDefIdAndEntId(String defId,String entId) {
		QueryFilter queryFilter = new DefaultQueryFilter();
		queryFilter.addFilter("bo_defid_", defId, QueryOP.EQUAL);
		queryFilter.addFilter("ref_ent_id_", entId, QueryOP.EQUAL);
		List<BOEntRel> list = bOEntRelDao.query(queryFilter);
		if(list==null||list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
}
