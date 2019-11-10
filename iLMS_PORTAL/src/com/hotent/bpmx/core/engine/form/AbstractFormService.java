package com.hotent.bpmx.core.engine.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeForm;
import com.hotent.bpmx.api.model.process.nodedef.ext.CallActivityNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.UserTaskNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.FormExt;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.api.service.BpmFormService;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.natapi.inst.NatProInstanceService;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.manager.BpmInstFormManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.bpmx.persistence.model.BpmInstForm;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormModel;
import com.hotent.form.api.model.FormType;
import com.hotent.form.api.service.FormService;
import com.hotent.form.persistence.model.BpmForm;
import com.hotent.sys.util.SysPropertyUtil;

/**
 * 表单获取抽象类，他有两种情况。
 * <pre>
 * 1.PC表单。
 * 2.mobile表单。
 * </pre>
 * @author ray
 *
 */
public abstract class AbstractFormService implements BpmFormService {
	

	/**
	 * 匹配 "{pk},{用户}这种模式的字符串。
	 */
	private static Pattern regex = Pattern.compile("\\{(.*?)\\}", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	
	@Resource
	protected BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	protected BpmDefinitionService bpmDefinitionService;
	@Resource
	protected BpmBusLinkManager bpmBusLinkManager;
	@Resource
	protected NatProInstanceService natProcessInstanceService;
	@Resource
	protected FormService formService;
	@Resource
	protected BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	protected BpmInstFormManager bpmInstFormManager;
	/**
	 * 获取节点表单
	 * @param bpmNodeDef
	 * @return
	 */
	protected abstract Form getFormByNodeDef(BpmNodeDef bpmNodeDef);
	
	/**
	 * 获取全局表单
	 * @param defExt
	 * @return
	 */
	protected abstract Form getGlobalFormByDefExt(DefaultBpmProcessDefExt defExt);
	
	/**
	 * 获取实例表单。
	 * @param defExt
	 * @return
	 */
	protected abstract Form getInstFormByDefExt(DefaultBpmProcessDefExt defExt);
	
	/**
	 * 获取子流程表单。
	 * @param bpmNodeDef
	 * @param parentDefKey
	 * @return
	 */
	protected abstract Form  getSubForm(BpmNodeDef bpmNodeDef,String parentDefKey);
	
	
	@Override
	public BpmNodeForm getByDefId(String defId){
		BpmNodeForm nodeForm = getFormDefByDefId(defId);
		if (nodeForm == null) 	return null;

		FormModel formModel = getByForm(nodeForm.getForm(), null);
		
		nodeForm.setForm(formModel);
		return nodeForm;
	}

	
	/**
	 * 表单是否为空。
	 * @param form
	 * @return
	 */
	protected boolean isNotEmptyForm(Form form){
		if(form==null) return false;
		return !form.isFormEmpty();
	}
	
	
	
	/**
	 * 在发起流程时获取流程表单。
	 * 
	 * <pre>
	 * 获取逻辑顺序：
	 * 1.取得开启节点的表单。
	 * 2.获取第一个任务节点的表单。
	 * 3.取得全局表单。
	 * </pre>
	 * 
	 * @param defId
	 * @return Form
	 */
	protected BpmNodeForm getFormDefByDefId(String defId){
		BpmNodeForm nodeForm=new BpmNodeForm();
	
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		// 开始节点
		BpmNodeDef bpmNodeDef = bpmProcessDef.getStartEvent();
		//1.获取开始节点的表单
		Form form =getFormByNodeDef(bpmNodeDef);
		if (isNotEmptyForm(form)){
			nodeForm.setBpmNodeDef(bpmNodeDef);
			nodeForm.setForm(form);
			return nodeForm;
		}
		//2.获取第一个节点的表单。
		List<BpmNodeDef> bpmNodeDefs = bpmProcessDef.getStartNodes();
		if (BeanUtils.isNotEmpty(bpmNodeDefs) && bpmNodeDefs.size() == 1){
			BpmNodeDef nodeDef = bpmNodeDefs.get(0);
			if (nodeDef instanceof UserTaskNodeDef){
				//form = nodeDef.getMobileForm();
				form =getFormByNodeDef( nodeDef);
				if(isNotEmptyForm(form)){
					nodeForm.setBpmNodeDef(bpmNodeDef);
					nodeForm.setForm(form);
					return nodeForm;
				}
			}
		}
		//3.获取全局的表单。
		if (form == null){
			DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
			form = getGlobalFormByDefExt(defExt);
			if(isNotEmptyForm(form)){
				nodeForm.setForm(form);
				nodeForm.setBpmNodeDef(bpmNodeDef);
				return nodeForm;
			}
		}
		
		return null;
	}
	
	@Override
	public BpmNodeForm getByDraft(BpmProcessInstance instance){
		String defId = instance.getProcDefId();
		BpmNodeForm nodeForm = getFormDefByDefId(defId);
		
		if (nodeForm == null) 	return null;

		FormModel formModel = getByForm(nodeForm.getForm(), instance);
		nodeForm.setForm(formModel);
		return nodeForm;
	}

	
	/**
	 * 根据表单定义对象获取表单model。
	 * 
	 * @param frm
	 * @return FormModel
	 */
	protected FormModel getByForm(Form frm, BpmProcessInstance instance){
		FormModel formModel = new BpmForm(frm);
		FormCategory formType=formModel.getType();
		if (FormCategory.INNER.equals(formType)){
			String formKey = formModel.getFormValue();
			//当key找不到时会使用FromID找
			FormModel formModelDB =formService.getByFormKey(formKey);
			if(BeanUtils.isNotEmpty(formModelDB)){
				String html=formModelDB.getFormHtml();
				if(html.indexOf("</form>")==-1){
					html="<form name='custForm' >" + html +"</form>";
				}
				
				formModel.setFormHtml(html);
				formModel.setFormKey(formModelDB.getFormKey());
				formModel.setFormId(formModelDB.getFormId());
			}
		}
		else if (FormCategory.FRAME.equals(formType)){
			if (instance != null){
				String url = frm.getFormValue();
				url = getUrl(instance, url);
				formModel.setFormValue(url);
			}
			else{
				String url = frm.getFormValue();
				url=replaceStr(url);
				formModel.setFormValue(url);
			}
		}
		return formModel;
	}
	
	/**
	 * 根据表单定义对象获取表单model。
	 * 
	 * @param frm
	 * @return FormModel
	 */
	protected FormModel handForm(FormModel formModel, BpmProcessInstance instance){
		FormCategory formType=formModel.getType();
		if (FormCategory.INNER.equals(formType)){
			String html=formModel.getFormHtml();
			if(html.indexOf("</form>")==-1){
				html="<form name='custForm' >" + html +"</form>";
			}
			formModel.setFormValue(formModel.getFormKey());
			formModel.setFormHtml(html);
		}
		else if (FormCategory.FRAME.equals(formType)){
			if (instance != null){
				String url = formModel.getFormValue();
				url = getUrl(instance, url);
				formModel.setFormValue(url);
			}
			else{
				String url = formModel.getFormValue();
				url=replaceStr(url);
				formModel.setFormValue(url);
			}
		}
		return formModel;
	}


	
	
	
	
	/**
	 * 获取流程节点的表单定义。
	 * 
	 * <pre>
	 * 只获取本流程定义下的流程节点表单。
	 * </pre>
	 * 
	 */
	@Override
	public Form getFormDefByDefNode(String defId, String nodeId, BpmProcessInstance bpmProcessInstance){
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		Form form = null;
		//检查子流程配置
		String parentInstId = bpmProcessInstance.getParentInstId();
		//没有父流程实例，即不是外部子流程的情况。
		if(StringUtil.isZeroEmpty(parentInstId)&&bpmNodeDef!=null){
			form=getFormByNodeDef(bpmNodeDef);
		}
		return form;
	}

	
	
	@Override
	public FormModel getInstFormByDefId(BpmProcessInstance instance)
	{
		BpmProcessInstance topInstance=bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
		
		String defId=topInstance.getProcDefId();
		FormModel formModel = getInstanceNodeForm(instance, defId, instance.getId());
		//实例中的表单取实例创建时的表单版本
		if(BeanUtils.isNotEmpty(formModel)){
			return formModel;
		}
		// 如果实例表单为空，去拿全局表单（启动时的版本）。
		FormModel frmModel = getInstanceNodeForm(instance, defId, null);
		return frmModel;
	}
	
	
	@Override
	public FormModel getByDefId(String defId, String nodeId, BpmProcessInstance instance){
		
		BpmNodeDef bpmNodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		String useMainForm = "";
		if(BeanUtils.isNotEmpty(bpmNodeDef)){
			useMainForm = BpmUtil.getUseMainForm(bpmNodeDef);
			if(StringUtil.isNotZeroEmpty(instance.getParentInstId())&&bpmNodeDef.getParentBpmNodeDef()==null){
				BpmProcessInstance parentInstance = bpmProcessInstanceManager.get(instance.getParentInstId());
				if(BeanUtils.isNotEmpty(parentInstance)){
					BpmNodeDef parentBpmNodeDef = bpmDefinitionAccessor.getStartEvent(parentInstance.getProcDefId());
					useMainForm = BpmUtil.getUseMainForm(parentBpmNodeDef);
				}
			}
		}
		if(StringUtil.isEmpty(useMainForm)){
			useMainForm = BpmConstants.MAIN_VERSION;
			if(!SysPropertyUtil.getBooleanByAlias("inst.useMainForm", true)){
				useMainForm = BpmConstants.START_VERSION;
			}
		}
		if(useMainForm.equals(BpmConstants.START_VERSION)){
			// 获取流程启动时节点使用的表单
			FormModel formModel = getInstanceNodeForm(instance, defId, nodeId);
			if(BeanUtils.isNotEmpty(formModel)){
				return formModel;
			}
		}
		
		Form frm = null;
		//属于主流程
		if(StringUtil.isZeroEmpty(instance.getParentInstId())){
			// 从节点配置获取。
			frm = getFormDefByDefNode(defId, nodeId, instance);
			if (frm != null){
				FormModel formModel = getByForm(frm, instance);
				return formModel;
			}
			
			// 获取全局表单。
			if (frm == null){
				BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
				DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
				frm=getGlobalFormByDefExt(defExt);
			}
			if (frm != null){
				FormModel formModel = getByForm(frm, instance);
				return formModel;
			}
		}
		//外部流程的情况。
		else{
			//往上查询到最外层流程实例。
			BpmProcessInstance topInstance=bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
			
			//判断是否存在外部子流程的配置。
			String parentDefKey = topInstance.getProcDefKey();
			frm =  getSubForm(bpmNodeDef,parentDefKey);
			
			//通过父类key获取全局表单。
			if(!isNotEmptyForm(frm)){
				BpmNodeForm  nodeFrm = getFormDefByParentFlowKey(topInstance.getProcDefId(),BpmConstants.LOCAL,false);
				if (nodeFrm != null){
					frm = nodeFrm.getForm();
				}
			}
			
			//存在则获取
			if(isNotEmptyForm(frm))  {
				FormModel formModel = getByForm(frm, topInstance);
				return formModel;
			}
			//不存在则获取最外层表单的配置。
			else{
				BpmNodeForm  nodeForm = getFormDefByDefId(topInstance.getProcDefId());
				if (nodeForm == null) return null;
				FormModel formModel = getByForm(nodeForm.getForm(), topInstance);
				return formModel;
			}
		}
		return null;
	}
	
	@Override
	public void handleInstForm(String instId, String defId,Boolean isSubFlow) {
		List<BpmNodeDef> allBpmNodeDefs = bpmDefinitionAccessor.getAllNodeDef(defId);
		ActionCmd cmd = ContextThreadUtil.getActionCmd();
		if(!isSubFlow){
			BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
			DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
			Form frm=defExt.getGlobalForm();
			// 全局表单
			saveBpmInstForm(frm,instId, defId,null);
			
			// 全局手机表单
			frm=defExt.getGlobalMobileForm();
			saveBpmInstForm(frm,instId, defId,null);
			
			// 实例表单
			frm = defExt.getInstForm();
			saveBpmInstForm(frm,instId, defId,instId);
			
			// 实例手机表单
			frm = defExt.getInstMobileForm();
			saveBpmInstForm(frm,instId, defId,instId);
			
		}
	
		
		for (BpmNodeDef bpmNodeDef : allBpmNodeDefs) {
			if( bpmNodeDef instanceof CallActivityNodeDef ){
				BpmDefinition bpmDef = bpmDefinitionService.getBpmDefinitionByDefKey(((CallActivityNodeDef) bpmNodeDef).getFlowKey(), false);
				handleInstForm(instId,bpmDef.getDefId(),true);
				continue;
			}
			if(!isSubFlow){
				saveBpmInstForm(bpmNodeDef.getForm(),instId, defId,bpmNodeDef.getNodeId());
				saveBpmInstForm(bpmNodeDef.getMobileForm(),instId, defId,bpmNodeDef.getNodeId());
			}
			
			if(isSubFlow){
				saveBpmInstForm(bpmNodeDef.getSubForm(String.valueOf(cmd.getVariables().get(BpmConstants.BPM_FLOW_KEY)), FormType.MOBILE),instId, defId,bpmNodeDef.getNodeId());
				saveBpmInstForm(bpmNodeDef.getSubForm(String.valueOf(cmd.getVariables().get(BpmConstants.BPM_FLOW_KEY)), FormType.PC),instId, defId,bpmNodeDef.getNodeId());
			}
		}
	
	}
	
	
	private void saveBpmInstForm(Form form, String instId, String defId,
			String nodeId) {
		if(BeanUtils.isEmpty(form))return;
		BpmInstForm bpmInstForm = new BpmInstForm();
		bpmInstForm.setInstId(instId);
		bpmInstForm.setDefId(defId);
		bpmInstForm.setNodeId( nodeId);
		bpmInstForm.setFormCategory(form.getType().value());
		bpmInstForm.setFormType(form.getFormType());
		if(FormCategory.INNER.equals(form.getType())){
			FormModel formModel = formService.getByFormKey(form.getFormValue());
			if(BeanUtils.isNotEmpty(formModel)){
				bpmInstForm.setFormValue(formModel.getFormId());
			}
		}else{
			bpmInstForm.setFormValue(form.getFormValue());
		}
		if(StringUtil.isNotEmpty(bpmInstForm.getFormValue())){
			bpmInstFormManager.create(bpmInstForm);
		}
		
	}

	private String getUrl(BpmProcessInstance instance, String url){

		Map<String, String> map = new HashMap<String, String>();

		if (ActionCmd.DATA_MODE_PK.equals(instance.getDataMode())){
			map.put("pk", instance.getBizKey());
			map.put("instId", instance.getId());
		} 
		else{
			List<BpmBusLink> list = bpmBusLinkManager.getByInstId(instance.getId());
			for (BpmBusLink link : list){
				map.put(link.getFormIdentify(), link.getBusinesskeyStr());
			}
		}
		url = replaceStr(url, map);

		return url;
	}
	
	/**
	 * 替换字符串。
	 * 
	 * @param str
	 * @param map
	 * @return String
	 */
	private static String replaceStr(String str, Map<String, String> map){
		if (StringUtil.isEmpty(str)) 	return "";

		Matcher regexMatcher = regex.matcher(str);
		while (regexMatcher.find()){
			String key = regexMatcher.group(1);
			String toReplace = regexMatcher.group(0);
			String val = map.get(key);
			if (val == null) val="";
			str = str.replace(toReplace, val);
		}
		return str;

	}
	
	/**
	 * 替换地址。
	 * "{pk} ,{user}"等。
	 * @param str
	 * @return
	 */
	private static String replaceStr(String str){
		if (StringUtil.isEmpty(str)) 	return "";
		
		Matcher regexMatcher = regex.matcher(str);
		while (regexMatcher.find()){
			String toReplace = regexMatcher.group(0);
			str = str.replace(toReplace, "");
		}
		return str;

	}
	
	/**
     *  通过父类key获取全局表单。
     * @param defId
     * @param parentFlowKey
     * @param isMobile
     * @return
     */
    private BpmNodeForm getFormDefByParentFlowKey(String defId,String parentFlowKey,boolean isMobile){
        BpmNodeForm nodeForm=new BpmNodeForm();
        BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
        // 开始节点
        BpmNodeDef bpmNodeDef = bpmProcessDef.getStartEvent();
        DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
        Form flowForm = null;
        if(BeanUtils.isNotEmpty(defExt) && BeanUtils.isNotEmpty(defExt.getAllGlobalForm())){
        	FormType formType=isMobile?FormType.MOBILE:FormType.PC;
        	for (FormExt form : defExt.getAllGlobalForm()) {
    			if(form.getParentFlowKey().equals(parentFlowKey)&&form.getFormType().equalsIgnoreCase(formType.toString())){
    				flowForm = form;
    				break;
    			}
    		}
        }
        if(isNotEmptyForm(flowForm)){
            nodeForm.setForm(flowForm);
            nodeForm.setBpmNodeDef(bpmNodeDef);
            return nodeForm;
        }
        
        return null;
    }
    
}
