package com.hotent.bpmx.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.hotent.base.core.cache.ICache;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.BpmDefinitionDelEvent;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.natapi.def.NatProDefinitionService;

@Service
public class BpmDefinitionListener implements ApplicationListener<BpmDefinitionDelEvent>{

	@Resource
	ICache iCache;
	
	@Resource
	NatProDefinitionService natProDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	
	@Override
	public void onApplicationEvent(BpmDefinitionDelEvent endEvent) {
		BpmDefinition bpmDef=(BpmDefinition) endEvent.getSource();
		delFromCache(bpmDef);
	}
	
	/**
	 * 清除缓存。
	 * @param defId 
	 * void
	 */
	private void delFromCache(BpmDefinition def){
		String bpmnDefId=def.getBpmnDefId();
		String defId=def.getDefId();
		
		String bpmnKey=BpmDefinition.BPM_BPMN_ID + bpmnDefId;
		String key=BpmDefinition.BPM_DEFINITION + defId;
		String procKey = BpmConstants.PROCDEF_PREFIX + defId;
		
		iCache.delByKey(key);
		iCache.delByKey(bpmnKey);
		iCache.delByKey(procKey);

		natProDefinitionService.clearCacheByBpmnDefId(bpmnDefId);
	
		bpmDefinitionAccessor.clean(defId);
		
	}
	
	

}
