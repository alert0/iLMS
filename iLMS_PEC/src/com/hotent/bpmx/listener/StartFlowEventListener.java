package com.hotent.bpmx.listener;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.cmd.BaseActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.constant.ProcessInstanceStatus;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.event.StartFlowEvent;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.core.engine.task.cmd.DefaultTaskFinishCmd;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.util.BpmCheckOpinionUtil;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 监听并处理流程发起事件。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-3-26-上午9:14:47
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service(value="startFlowEventListener")
public class StartFlowEventListener implements ApplicationListener<StartFlowEvent>,Ordered {

	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;
	
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager  ;
	@Resource
	BpmDefinitionManager  bpmDefinitionManager;
	
	@Override
	public void onApplicationEvent(StartFlowEvent ev) {
		BpmDelegateExecution execution=(BpmDelegateExecution)ev.getSource();
		String currentProcInstId=(String)execution.getVariable(BpmConstants.PROCESS_INST_ID);
		//流程通讯变量，这个在外部子流程CallSubProcessStartListener中进行传入。
		Map<String, Object> commuVars=ContextThreadUtil.getCommuVars();
		ActionCmd cmd= ContextThreadUtil.getActionCmd();
		//一般的流程
		if(commuVars.isEmpty()){						
			//创建发起流程的审批意见
			createOpinion(execution, currentProcInstId, null);
		}
		//子流程的情况。
		else{
			String parentProcInstId=(String)commuVars.get(BpmConstants.PROCESS_INST_ID);
			//获取父的流程实例ID
			BpmProcessInstance parentInstance=(BpmProcessInstance) cmd.getTransitVars(BpmConstants.PROCESS_INST);	
			
			BpmProcessInstance topInstance=(BpmProcessInstance)bpmProcessInstanceManager.getTopBpmProcessInstance(parentInstance);
			
			//产生BPMPROCESSINSTANCE,
			DefaultBpmProcessInstance instance= createInstance(execution,commuVars,parentInstance);
			//子流程转换cmd。
			converCmd(parentProcInstId,instance);
			
			commuVars.put(BpmConstants.PROCESS_PARENT_INST_ID, parentProcInstId);
			commuVars.put(BpmConstants.PROCESS_INST_ID, instance.getId());
			commuVars.put(BpmConstants.PROCESS_DEF_ID, instance.getProcDefId());
			//传递流程变量。
			commuVars.put(BpmConstants.BPM_FLOW_KEY, topInstance.getProcDefKey());
			

			//设置流程变量
			execution.setVariables(commuVars);
			//添加流程意见
			createOpinion(execution, instance.getId(), parentProcInstId);	
			
		}
		
	}
	
	
	
	private void converCmd(String parentProcInstId,BpmProcessInstance instance){
		
	
		BaseActionCmd baseCmd=(BaseActionCmd)ContextThreadUtil.getActionCmd();
		
		DefaultTaskFinishCmd cmd=new DefaultTaskFinishCmd();
		cmd.setInstId(instance.getId());
		
		cmd.setActionName(OpinionStatus.AGREE.getKey());
		
		cmd.setBpmIdentities(baseCmd.getBpmIdentities());
		cmd.putTransitVars(baseCmd.getTransitVars());
		cmd.addTransitVars(BpmConstants.PROCESS_INST, instance);
		
		ContextThreadUtil.setActionCmd(cmd);
	}
	
	
	
	private DefaultBpmProcessInstance createInstance(BpmDelegateExecution execution ,Map<String, Object> commuVars,BpmProcessInstance parentInstance){
		String businessKey=(String) commuVars.get(BpmConstants.BUSINESS_KEY);
		String parentProcInstId=(String)commuVars.get(BpmConstants.PROCESS_INST_ID);
		String subject=(String) commuVars.get(BpmConstants.SUBJECT);
		
		String bpmnDefId=execution.getBpmnDefId();
		
		String  defId=  bpmDefinitionManager.getDefIdByBpmnDefId(bpmnDefId);
		DefaultBpmDefinition bpmDefinition =bpmDefinitionManager.getById(defId);
		
		DefaultBpmProcessInstance instance=new DefaultBpmProcessInstance();
		instance.setId(UniqueIdUtil.getSuid());
		instance.setParentInstId(parentProcInstId);
		instance.setProcDefId(bpmDefinition.getDefId());
		instance.setProcDefKey(bpmDefinition.getDefKey());
		instance.setBpmnDefId(bpmDefinition.getBpmnDefId());
		instance.setProcDefName(bpmDefinition.getName());
		//数据处理模式
		instance.setDataMode(parentInstance.getDataMode());
		
		if(execution.getSupperExecution()!=null){
		   String superNodeId= execution.getSupperExecution().getNodeId();
		   instance.setSuperNodeId(superNodeId);
		}
		instance.setBpmnInstId(execution.getBpmnInstId());
		instance.setBizKey(businessKey);
		if(BeanUtils.isNotEmpty(commuVars.get(BpmConstants.SYS_CODE))){
			instance.setSysCode((String)commuVars.get(BpmConstants.SYS_CODE));
		}
		if(BpmDefinition.TEST_STATUS.RUN.equals(bpmDefinition.getTestStatus())){
			instance.setIsFormmal(BpmProcessInstance.FORMAL_YES);
		}
		
		instance.setSubject(subject);
		int supportMobile = bpmDefinition.getSupportMobile();
		//是否支持移动端：如果父流程实例存在，则取父流程
		if(BeanUtils.isNotEmpty(parentInstance)){
			DefaultBpmProcessInstance pInstance = bpmProcessInstanceManager.get(parentInstance.getId());
			if(BeanUtils.isNotEmpty(pInstance)){
				supportMobile = pInstance.getSupportMobile();
			}
		}
		instance.setSupportMobile(supportMobile);
		IUser currentUser=ContextUtil.getCurrentUser();
		//设置创建用户ID
		instance.setCreateBy(currentUser.getUserId());
		instance.setCreator(currentUser.getFullname());
		instance.setCreateTime(new Date());
		
		instance.setStatus(ProcessInstanceStatus.STATUS_RUNNING.getKey());
		
		bpmProcessInstanceManager.create(instance);
		
		return instance;
		
	}
	
	/**
	 * 在流程发起时记录在流程意见表中记录提交人信息。
	 * @param execution				
	 * @param procInstId
	 * @param parentProcInstId 
	 * void
	 */
	private void createOpinion(BpmDelegateExecution execution,String procInstId,String parentProcInstId){
		DefaultBpmCheckOpinion bpmCheckOpinion = BpmCheckOpinionUtil.buildBpmCheckOpinion(execution, procInstId,false);
		bpmCheckOpinion.setStatus(OpinionStatus.START.getKey());
		bpmCheckOpinion.setOpinion("发起流程");
		bpmCheckOpinion.setSupInstId(parentProcInstId);
		bpmCheckOpinionManager.create(bpmCheckOpinion);
	}
	
	
	

	@Override
	public int getOrder() {
		return 1;
	}
	
	
}
