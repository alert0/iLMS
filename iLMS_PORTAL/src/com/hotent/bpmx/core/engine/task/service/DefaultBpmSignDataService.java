package com.hotent.bpmx.core.engine.task.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.service.BpmSignDataService;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.persistence.dao.BpmSignDataDao;
import com.hotent.bpmx.persistence.model.BpmSignData;

/**
 * 读取会签审批过的人员。
 * @author ray
 *
 */
@Service
public class DefaultBpmSignDataService implements BpmSignDataService {
	
	@Resource
	BpmSignDataDao bpmSignDataDao;

	@Override
	public List<BpmIdentity> getHistoryAuditor(String executeId, String nodeId) {
		List<BpmIdentity> identityList=new ArrayList<BpmIdentity>();
		List<BpmSignData> list=bpmSignDataDao.getVoteByExecuteNode(executeId, nodeId, 0);
		for(BpmSignData data:list){
			if("no".equals(data.getVoteResult())) continue;
			BpmIdentity identity=  DefaultBpmIdentity.getIdentityByUserId(data.getVoteId(), data.getVoter());
			identityList.add(identity);
		}
		return identityList;
	}

}
