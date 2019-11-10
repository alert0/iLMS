package com.hotent.bpmx.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmInstFormDao;
import com.hotent.bpmx.persistence.model.BpmInstForm;
import com.hotent.bpmx.persistence.manager.BpmInstFormManager;

/**
 * 
 * <pre> 
 * 描述：bpm_inst_form 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:liygui
 * 邮箱:liygui@jee-soft.cn
 * 日期:2017-07-04 15:19:05
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("bpmInstFormManager")
public class BpmInstFormManagerImpl extends AbstractManagerImpl<String, BpmInstForm> implements BpmInstFormManager{
	@Resource
	BpmInstFormDao bpmInstFormDao;
	@Override
	protected Dao<String, BpmInstForm> getDao() {
		return bpmInstFormDao;
	}
	
	@Override
	public BpmInstForm getNodeForm(String instId, String defId, String nodeId,
			String type) {
		return bpmInstFormDao.getNodeForm(instId, defId, nodeId, type);
	}
	@Override
	public BpmInstForm getGlobalForm(String instId,String type) {
		return bpmInstFormDao.getGlobalForm(instId, type);
	}
	
	@Override
	public BpmInstForm getInstForm(String instId, String type) {
		return bpmInstFormDao.getInstForm(instId, type);
	}

	@Override
	public void removeDataByDefId(String defId) {
		bpmInstFormDao.removeDataByDefId(defId);
	}

	@Override
	public void removeDataByInstId(String instId) {
		bpmInstFormDao.removeDataByInstId(instId);
	}

	
}
