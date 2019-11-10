package com.hotent.bpmx.listener;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.MultiInstanceType;
import com.hotent.bpmx.api.constant.NodeStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.CallSubProcessEndEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.persistence.manager.BpmProStatusManager;

/**
 *  <pre> 
 * 子流程结束时将变量传递出来。
 * 1.传递的变量名称为： callActivityVars_ +"节点名称";
 * 2.如果流程为串行流程那么删除 相关的用户变量。
 *
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-18-下午6:31:29
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service(value="callSubProcessEndEventListener")
public class CallSubProcessEndEventListener implements  ApplicationListener<CallSubProcessEndEvent>,Ordered{

	@Resource
	BpmProStatusManager bpmProStatusManager;
	
	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public void onApplicationEvent(CallSubProcessEndEvent endEvent) {
		BpmDelegateExecution execution=(BpmDelegateExecution) endEvent.getSource();
		Integer instCount=(Integer) execution.getVariable(BpmConstants.NUMBER_OF_INSTANCES);
		Integer completeInstCount=(Integer) execution.getVariable(BpmConstants.NUMBER_OF_COMPLETED_INSTANCES);
		//单实例的情况。
		if(instCount==null){
			setVars(execution);
			
			updNodeStatus(execution);
		}
		//多实例的情况
		else if(instCount.equals(completeInstCount)){
			MultiInstanceType mulType=execution.multiInstanceType();
			
			if(MultiInstanceType.SEQUENTIAL.equals(mulType)){
				String varName=BpmConstants.SIGN_USERIDS + execution.getNodeId();
				execution.removeVariable(varName);
			}
			setVars(execution);
			
			updNodeStatus(execution);
		}
		
		
	}
	
	/**
	 * 将流程变量从内部子流程传递出来。
	 * @param execution 
	 * void
	 */
	private void setVars(BpmDelegateExecution execution){
		Map<String, Object> vars=ContextThreadUtil.getCommuVars();
		String varName=BpmConstants.CALL_ACTIVITI_VARS + execution.getNodeId();
		execution.setVariable(varName, vars);
	}

	/**
	 * 更新节点状态为完成。
	 * @param execution 
	 * void
	 */
	private void updNodeStatus(BpmDelegateExecution execution){
		String instId=(String) execution.getVariable(BpmConstants.PROCESS_INST_ID);
		String bpmnDefId=execution.getBpmnDefId();
		String nodeId=execution.getNodeId();
		String nodeName=execution.getNodeName();
		
		bpmProStatusManager.createOrUpd(instId, bpmnDefId, nodeId, nodeName, NodeStatus.COMPLETE);
	}
	

}
