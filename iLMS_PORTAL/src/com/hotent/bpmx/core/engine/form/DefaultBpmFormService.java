package com.hotent.bpmx.core.engine.form;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.persistence.model.BpmInstForm;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.model.BpmForm;

@Service
public class DefaultBpmFormService extends AbstractFormService 
{
		

	@Override
	protected Form getFormByNodeDef(BpmNodeDef bpmNodeDef) {
		return bpmNodeDef.getForm();
	}

	@Override
	protected Form getGlobalFormByDefExt(DefaultBpmProcessDefExt ext) {
		return ext.getGlobalForm();
	}


	@Override
	protected Form getSubForm(BpmNodeDef bpmNodeDef, String parentDefKey) {
		return bpmNodeDef.getSubForm(parentDefKey, FormType.PC);
	}


	@Override
	protected Form getInstFormByDefExt(DefaultBpmProcessDefExt defExt) {
		return defExt.getInstForm();
	}

	@Override
	public FormModel getInstanceNodeForm(BpmProcessInstance instance, String defId,
			String nodeId) {
		//子流程表单
		boolean isSub = false;
		//实例表单
		boolean isInstForm = false;
		if(instance.getId().equals(nodeId)){
			isInstForm = true;
		}
		if(StringUtil.isNotZeroEmpty(instance.getParentInstId())){
			instance = bpmProcessInstanceManager.get(instance.getParentInstId());
			isSub = true;
		}
		
		// 获取实例的节点表单
		BpmInstForm bpmInstForm = bpmInstFormManager.getNodeForm(instance.getId(), defId, nodeId,FormType.PC.value());
		
		//1、判断子流程是否有清空节点表单配置，如果有，则按以下规则找表单：(先查找通过父流程给本节点配置的表单，若本节点没有配置表单则)查找父流程开始节点配置的表单，
		//若开始节点配置了表单，则使用开始节点的表单，若开始节点没有配置表单则查找父流程第一个节点配置的表单，若第一个节点配置了表单，则使用第一个节点配置的表单，
		//若第一个节点没有配置表单，则使用父流程的全局表单。
		//2、如果是获取流程实例表单，且为子流程，则都获取最外层主流程的流程实例表单
		if(isSub){
			if(isInstForm){
				//获取子流程流程实例表单，实际是取主流程实例表单
				bpmInstForm = bpmInstFormManager.getNodeForm(instance.getId(), defId, instance.getId(),FormType.PC.value());
			}else{
				BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
				Form form = bpmNodeDef.getSubForm(instance.getProcDefKey(), FormType.PC);
				if(BeanUtils.isEmpty(form)){
					bpmInstForm = null;
					List<BpmNodeDef> nodeDefs = bpmDefinitionAccessor.getAllNodeDef(instance.getProcDefId());
					//获取主流程开始节点定义表单
					for (BpmNodeDef nodeDef : nodeDefs) {
						if(nodeDef.getType().equals(NodeType.START)&&BeanUtils.isNotEmpty(nodeDef.getSubFormList())){
							bpmInstForm = bpmInstFormManager.getNodeForm(instance.getId(), instance.getProcDefId(), nodeDef.getNodeId(),FormType.PC.value());
							break;
						}
					}
					//获取主流程第一个节点定义表单
					if(BeanUtils.isEmpty(bpmInstForm)){
						List<BpmNodeDef> startNodes = bpmDefinitionAccessor.getStartNodes(instance.getProcDefId());
						if(BeanUtils.isNotEmpty(startNodes)){
							bpmInstForm = bpmInstFormManager.getNodeForm(instance.getId(), instance.getProcDefId(), startNodes.get(0).getNodeId(),FormType.PC.value());
						}
					}
				}
			}
		}
		
		// 全局表单
		if(BeanUtils.isEmpty(bpmInstForm)){
			bpmInstForm = bpmInstFormManager.getGlobalForm(instance.getId(),FormType.PC.value());
		}
		
		if(BeanUtils.isEmpty(bpmInstForm)){
			return null;
		}
		
		String formCategory = bpmInstForm.getFormCategory();
		FormModel formModel = new BpmForm();
		if(FormCategory.INNER.equals(FormCategory.fromValue(formCategory))){
			formModel = formService.getByFormId(bpmInstForm.getFormValue());
			if(BeanUtils.isEmpty(formModel)){
				return null;
			}
			formModel.setType(FormCategory.INNER);
		}else{
			formModel.setType(FormCategory.FRAME);
			// bpmInstForm.getFormValue();
			formModel.setFormValue(bpmInstForm.getFormValue());
		}
		formModel = handForm(formModel, instance);
		
		return formModel;
	}

	
}
