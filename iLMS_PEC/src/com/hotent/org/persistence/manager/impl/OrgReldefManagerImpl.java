package com.hotent.org.persistence.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.org.persistence.dao.OrgRelDao;
import com.hotent.org.persistence.dao.OrgReldefDao;
import com.hotent.org.persistence.model.OrgRel;
import com.hotent.org.persistence.model.OrgReldef;
import com.hotent.org.persistence.manager.OrgReldefManager;

/**
 * 
 * <pre> 
 * 描述：组织关系定义 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-06-29 18:00:43
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("orgReldefManager")
public class OrgReldefManagerImpl extends AbstractManagerImpl<String, OrgReldef> implements OrgReldefManager{
	@Resource
	OrgReldefDao orgReldefDao;
	@Resource
	OrgRelDao orgRelDao;
	
	@Override
	protected Dao<String, OrgReldef> getDao() {
		return orgReldefDao;
	}
	public OrgReldef getByCode(String code) {
    	return  orgReldefDao.getByCode(code);
	}
	@Override
	public List<OrgReldef> getByName(String name) {
		return orgReldefDao.getByName(name);
	}
	@Override
	public void removeByIds(String... ids){
		for (String id : ids) {
			List<OrgRel> relList = orgRelDao.getByReldefId(id);
			OrgReldef reldef = orgReldefDao.get(id);
			if(BeanUtils.isNotEmpty(relList)){
				ThreadMsgUtil.addMsg(reldef.getName()+"已与岗位关联，不能删除！");
				continue;
			}
		}
		if(StringUtil.isEmpty(ThreadMsgUtil.getMessage(false))){
			super.removeByIds(ids);
		}
	}
	@Override
	public List<OrgReldef> getListByUserId(String userId) {
		List<OrgReldef> orgRelDefs = new ArrayList<OrgReldef>();
		List<OrgRel> orgRels = orgRelDao.getListByUserId(userId);
		if(BeanUtils.isEmpty(orgRels)) return orgRelDefs;
		for (OrgRel orgRel : orgRels) {
			OrgReldef orgReldef = this.get(orgRel.getRelDefId());
			orgRelDefs.add(orgReldef);
		}
		return orgRelDefs;
	}
	
	@Override
	public List<OrgReldef> getListByAccount(String account) {
		List<OrgReldef> orgRelDefs = new ArrayList<OrgReldef>();
		List<OrgRel> orgRels = orgRelDao.getListByAccount(account);
		if(BeanUtils.isEmpty(orgRels)) return orgRelDefs;
		for (OrgRel orgRel : orgRels) {
			OrgReldef orgReldef = this.get(orgRel.getRelDefId());
			orgRelDefs.add(orgReldef);
		}
		return orgRelDefs;
	}
}
