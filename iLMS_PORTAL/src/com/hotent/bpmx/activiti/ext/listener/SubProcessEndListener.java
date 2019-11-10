package com.hotent.bpmx.activiti.ext.listener;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.MultiInstanceDef;
import com.hotent.bpmx.api.service.BpmDefinitionService;

/**
 *子流程退出时执行的监听。 
 * @author ray
 *
 */
public class SubProcessEndListener extends AbstractExecutionListener {
	
	@Resource
	BpmDefinitionService bpmDefinitionService;


	/**
	 * serialVersionUID
	 * @since 1.0.0
	 */
	private static final long serialVersionUID = -968956129657422689L;

	@Override
	public EventType getBeforeTriggerEventType() {
		return null;
	}

	@Override
	public EventType getAfterTriggerEventType() {
		return null;
	}

	@Override
	public void beforePluginExecute(BpmDelegateExecution bpmDelegateExecution) {
	}

	@Override
	public void triggerExecute(BpmDelegateExecution bpmDelegateExecution) {

	}

	@Override
	public void afterPluginExecute(BpmDelegateExecution bpmDelegateExecution) {
		
		String bpmnDefId=bpmDelegateExecution.getBpmnDefId();
		String nodeId=bpmDelegateExecution.getNodeId();
		BpmNodeDef nodeDef=bpmDefinitionService.getBpmNodeDef(bpmnDefId, nodeId);
		
		if(!(nodeDef instanceof MultiInstanceDef)) return;
		MultiInstanceDef multiNodeDef=(MultiInstanceDef)nodeDef;
		
		if(multiNodeDef.supportMuliInstance() && !multiNodeDef.isParallel()){
			//串行子流程删除流程变量。
			Integer nrOfInstances=(Integer)bpmDelegateExecution.getVariable( BpmConstants.NUMBER_OF_INSTANCES);
			Integer nrOfCompletedInstances=(Integer)bpmDelegateExecution.getVariable(BpmConstants.NUMBER_OF_COMPLETED_INSTANCES);
			//没有完成。
			if(BeanUtils.isNotEmpty(nrOfInstances) &&!nrOfInstances.equals(nrOfCompletedInstances)) return;
			
			String varName= BpmConstants.SIGN_USERIDS+ bpmDelegateExecution.getNodeId();
			bpmDelegateExecution.removeVariable(varName);
		}
		
	}

	@Override
	protected ScriptType getScriptType() {
		return ScriptType.END;
	}



}
