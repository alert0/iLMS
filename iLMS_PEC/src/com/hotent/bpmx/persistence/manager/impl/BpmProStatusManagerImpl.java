package com.hotent.bpmx.persistence.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.db.api.Dao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.persistence.dao.BpmProStatusDao;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProStatus;
import com.hotent.sys.util.ContextUtil;

@Service("bpmProStatusManager")
public class BpmProStatusManagerImpl extends AbstractManagerImpl<String, DefaultBpmProStatus> implements BpmProStatusManager{
	
	@Resource
	BpmProStatusDao bpmProStatusDao;
	@Override
	protected Dao<String, DefaultBpmProStatus> getDao() {
		return bpmProStatusDao;
	}
	


	@Override
	public List<DefaultBpmProStatus> queryHistorys(String procInstId) {
		return bpmProStatusDao.queryHistorys(procInstId);
	}



	@Override
	public void archiveHistory(String procInstId) {
		bpmProStatusDao.archiveHistory(procInstId);
		
	}



	@Override
	public void createOrUpd(String instId, String defId, String nodeId,
			String nodeName, NodeStatus nodeStatus) {
		DefaultBpmProStatus rtn=bpmProStatusDao.getByInstNodeId(instId, nodeId);
		//更新记录
		if(rtn==null){
			String userId=BeanUtils.isNotEmpty(ContextUtil.getCurrentUser())?ContextUtil.getCurrentUser().getUserId():"-1";
			DefaultBpmProStatus proStatus=new DefaultBpmProStatus(instId, defId, nodeId, nodeName, nodeStatus.getKey(),userId );
			
			bpmProStatusDao.create(proStatus);
		}
		else{
			rtn.setStatus(nodeStatus.getKey());
			bpmProStatusDao.update(rtn);
		}
	}



	@Override
	public void delByInstList(List<String> instList) {
		bpmProStatusDao.delByInstList(instList);
	}



	@Override
	public DefaultBpmProStatus getByInstNodeId(String instId, String nodeId) {
		return bpmProStatusDao.getByInstNodeId(instId, nodeId);
	}



	@Override
	public void updStatusByInstList(List<String> list, NodeStatus status) {
		bpmProStatusDao.updStatusByInstList(list, status);
	}
		
	
}
