package com.hotent.bpmx.api.service;

import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormModel;

/**
 * 流程表单获取接口。
 * <pre>
 *  主要的功能是:
 *  1.启动流程的表单。
 *  2.实例的表单。
 *  3.审批任务的表单。
 * </pre>
 * @author csx
 *
 */
public interface BpmFormService   {
	
	/**
	 * 获取流程节点的表单定义。
	 * <pre>
	 * 1.判断流程实例是否有父实例。即判断当前流程是否子流程。
	 * 2.如果是则取流程定义父表单的设置。
	 * 3.否则取当前的配置。
	 * </pre>
	 * @param defId
	 * @param nodeId
	 * @return 
	 * Form
	 */
	Form getFormDefByDefNode(String defId,String nodeId,BpmProcessInstance instance);
	
	
	/**
	 * 获取某个流程的发起表单。
	 * <pre>
	 * 	1.获取开始节点的表单。
	 *  2.获取第一个节点的表单。
	 *  3.获取全局表单。
	 * </pre>
	 * @param defId
	 * @return FormModel
	 */
	BpmNodeForm getByDefId(String defId);
	
	
	/**
	 * 根据草稿获取表单。
	 * <pre>
	 * 	1.获取发起节点的表单。
	 * 	2.获取表单将pk进行替换。
	 * </pre>
	 * @param instance
	 * @return  BpmNodeForm
	 */
	BpmNodeForm getByDraft(BpmProcessInstance instance);
	
	
	/**
	 * 审批流程获取审批表单。
	 * <pre>
	 * 	1.获取运行时表单。
	 * 	2.获取不到则获取当前节点配置的表单。
	 * 	3.获取不到则获取全局表单。
	 * </pre>
	 * @param defId
	 * @param nodeId
	 * @param instance
	 * @return FormModel
	 */
	FormModel getByDefId(String defId,String nodeId,BpmProcessInstance instance);
	
	/**
	 * 获取流程实例审批的表单
	 * @param instId
	 * @param defId
	 * @param nodeId
	 * @return
	 */
	FormModel getInstanceNodeForm(BpmProcessInstance instance, String defId, String nodeId);
	
	
	/**
	 * 流程启动时， 记录流程实例节点表单
	 * @param instId
	 * @param defId
	 */
	void handleInstForm(String instId,String defId,Boolean isSubFlow);
	
	
	/**
	 * 获取流程业务表单。 
	 * <pre>
	 * 1.在运行时获取业务表单。
	 * 2.获取流程定义的业务表单。
	 * </pre>
	 * @param	instance流程实例
	 * @return  FormModel
	 */
	FormModel getInstFormByDefId(BpmProcessInstance instance);
		
}
