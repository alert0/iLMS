package com.hotent.bpmx.listener;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.CallSubProcessStartEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;

/**
 * 外部子流程进入时触发的事件。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-17-下午1:59:22
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service(value="callSubProcessStartEventListener")
public class CallSubProcessStartEventListener implements  ApplicationListener<CallSubProcessStartEvent>,Ordered{

	@Resource
	BpmProStatusManager bpmProStatusManager;

	@Override
	public void onApplicationEvent(CallSubProcessStartEvent ev) {
		
		BpmDelegateExecution execution=(BpmDelegateExecution) ev.getSource();
		
		String nodeId=execution.getNodeId();
		String bpmnDefId=execution.getBpmnDefId();
		String nodeName=execution.getNodeName();
		
		//传递流程变量
		Map<String,Object> variables= execution.getVariables();
		
		Integer completeInstance=(Integer)variables.get(BpmConstants.NUMBER_OF_COMPLETED_INSTANCES);
		
		//将流程变量通过这个方式
		removeVars(variables);
		ContextThreadUtil.cleanCommuVars();
		ContextThreadUtil.setCommuVars(variables);
		
		String instId=(String)execution.getVariable(BpmConstants.PROCESS_INST_ID);

		//首次调用。
		if(completeInstance==null){
			bpmProStatusManager.createOrUpd(instId, bpmnDefId, nodeId, nodeName, NodeStatus.PENDING);
		}
	}
	
	/**
	 * 移除不必要的变量。
	 * @param variables 
	 * void
	 */
	private void removeVars(Map<String,Object> variables){
		variables.remove(BpmConstants.NUMBER_OF_LOOPCOUNTER);
		variables.remove(BpmConstants.NUMBER_OF_ACTIVE_INSTANCES);
		variables.remove(BpmConstants.NUMBER_OF_COMPLETED_INSTANCES);
		variables.remove(BpmConstants.NUMBER_OF_INSTANCES);
		
	}
	
	@Override
	public int getOrder() {
		return 1;
	}

}
