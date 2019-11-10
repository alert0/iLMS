package com.hotent.bpmx.listener;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.TaskFinishCmd;
import com.hotent.bpmx.api.constant.AopType;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.event.DoNextEvent;
import com.hotent.bpmx.api.event.DoNextModel;
import com.hotent.bpmx.api.exception.WorkFlowException;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;



/**
 * 负责表单数据保存。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-9-4-下午5:52:30
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class DoNextEventListener  implements ApplicationListener<DoNextEvent>,Ordered {
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;  
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor; 
	@Resource
	BpmInstService bpmInstService;
	
	

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public void onApplicationEvent(DoNextEvent ev) {
		DoNextModel model= (DoNextModel) ev.getSource();
		
		//设置业务主键。
		setBuinessKey(model);
		
		if(AopType.PREVIOUS.equals(model.getAopType())){
			before(model);
		}
		else{
			after(model);
		}
	}
	
	/**
	 * 将主键放到上下文中。
	 * <pre>
	 *  适用情况，iframe的情况。
	 *  1.直接启动流程是businessKey为空。
	 *  2.在保存草稿的时候从实例中获取主键，放到cmd中。
	 *  	在编写处理器时，可以根据这个值判断到底增加数据还是更新数据。
	 * </pre>
	 * @param model
	 */
	private void setBuinessKey(DoNextModel model) {
		ActionCmd cmd = model.getTaskFinishCmd();
		
		if (!(cmd instanceof TaskFinishCmd)) return;
			
		DefaultBpmProcessInstance inst = (DefaultBpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);
		
		if (!ActionCmd.DATA_MODE_PK.equals(inst.getDataMode())) return;
	
		String pkInst=inst.getBizKey();
		if(StringUtil.isNotEmpty(pkInst)){
			cmd.setBusinessKey(pkInst);
			cmd.setSysCode(inst.getSysCode());
		}
	}
	
	private void before(DoNextModel model){
		//检查流程实例是否禁用
		checkFlowIsValid(model);
		saveBusLink(model);
		executeHandler(model,true);
	}
	
	//检查流程实例是否禁用
	private void checkFlowIsValid(DoNextModel model) {
		BpmTask bpmTask = (BpmTask) model.getTaskFinishCmd().getTransitVars(BpmConstants.BPM_TASK);
		boolean isForbindden = bpmInstService.isSuspendByInstId(bpmTask.getProcInstId());
		if(isForbindden){
			throw new WorkFlowException("流程已经被禁止，请联系管理员！");
		}
	}
	
	private void saveBusLink(DoNextModel model){
		DefaultTaskFinishCmd cmd=(DefaultTaskFinishCmd) model.getTaskFinishCmd();
		ActionCmd actionCmd=(ActionCmd)cmd;
		BpmProcessInstance instance= (BpmProcessInstance) actionCmd.getTransitVars(BpmConstants.PROCESS_INST);
		//数据模式
		String dataMode=instance.getDataMode();
		
		if(ActionCmd.DATA_MODE_PAIR.equals(dataMode)){
			BusDataUtil.handExt(cmd);
		}
		//bo数据的处理
		else if (ActionCmd.DATA_MODE_BO.equals(dataMode)){
			BusDataUtil.handSaveBoData(instance, actionCmd);
		}
		else if (ActionCmd.DATA_MODE_PK.equals(dataMode)){
			
		}
	}
	
	
	private void after(DoNextModel model){
		executeHandler(model,false);
	}
	
	
	
	private NodeProperties getNodeProperties(DoNextModel model){
		ActionCmd cmd= (ActionCmd) model.getTaskFinishCmd();
		
		BpmTask bpmTask= (BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);
		
		BpmProcessInstance instance= (BpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);
		
		String nodeId=bpmTask.getNodeId();
		
		String defId=instance.getProcDefId();
		
		BpmNodeDef bpmNodeDef=bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		
		NodeProperties nodeProperties=null;
		
		if(StringUtil.isNotZeroEmpty(instance.getParentInstId())){
			BpmProcessInstance parentInst=bpmProcessInstanceManager.get(instance.getParentInstId());
			String defKey=parentInst.getProcDefKey();
			nodeProperties=bpmNodeDef.getPropertiesByParentDefKey(defKey);
		}
		
		if(nodeProperties==null){
			nodeProperties=bpmNodeDef.getLocalProperties();
		}
		
		return nodeProperties;
	}
	
	
	private void executeHandler(DoNextModel model,boolean isBefore){
		TaskFinishCmd cmd= model.getTaskFinishCmd();
		
		ActionCmd actionCmd=(ActionCmd)cmd;
		
		//获取发起节点获取不到则获取第一个节点。
		NodeProperties properties= getNodeProperties(model);
		
		BusDataUtil.executeHandler(properties, actionCmd, isBefore);
		
	}
}
