package com.hotent.org.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.OrgDao;
import com.hotent.org.persistence.dao.SysDemensionDao;
import com.hotent.org.persistence.model.Org;
import com.hotent.org.persistence.model.SysDemension;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.manager.SysDemensionManager;

/**
 * 
 * <pre> 
 * 描述：维度管理 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2017-07-19 15:30:10
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysDemensionManager")
public class SysDemensionManagerImpl extends AbstractManagerImpl<String, SysDemension> implements SysDemensionManager{
	@Resource
	SysDemensionDao sysDemensionDao;
	@Resource
	OrgManager orgManager;
	@Override
	protected Dao<String, SysDemension> getDao() {
		return sysDemensionDao;
	}
	
	@Override
	public void removeByIds(String... ids) {
		for (String demid : ids) {
			this.remove(demid);
			String[] orgIds = findOrgIdByDemid(demid);
			if(BeanUtils.isNotEmpty(orgIds)){
				orgManager.removeByIds(orgIds);
			}
		}
	}
	
	private String[] findOrgIdByDemid(String demid){
		List<String> strs = new ArrayList<String>();  
		QueryFilter queryFilter= new DefaultQueryFilter();
		queryFilter.addFilter("dem_id_", demid, QueryOP.EQUAL);
		List<Org> list = orgManager.query(queryFilter);
		for (Org org : list) {
			strs.add(org.getId());
		}
		if(BeanUtils.isNotEmpty(strs)){
			return strs.toArray(new String[strs.size()]);
		}
		return null;
	}

	@Override
	public SysDemension getByCode(String code) {
		return sysDemensionDao.getByCode(code);
	}

	@Override
	public SysDemension getDefaultDemension() {
		return sysDemensionDao.getDefaultDemension();
	}

	@Override
	public void setDefaultDemension(String id) {
		if(StringUtil.isNotEmpty(id)){
			SysDemension demension = sysDemensionDao.get(id);
			if(BeanUtils.isEmpty(demension)){
				demension = sysDemensionDao.getByCode(id);
			}
			if(BeanUtils.isNotEmpty(demension)){
				sysDemensionDao.setNotDefaultDemension();
				demension.setIsDefault(1);
				sysDemensionDao.update(demension);
			}
		}
	}
}
