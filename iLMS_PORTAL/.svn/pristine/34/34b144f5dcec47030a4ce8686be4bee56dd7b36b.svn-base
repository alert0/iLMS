package com.hotent.bpmx.persistence.model.nodehandler;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.constant.ScriptType;
import com.hotent.bpmx.api.model.process.def.FieldInitSetting;
import com.hotent.bpmx.api.model.process.def.FormInitItem;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.nodedef.ext.BaseBpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.Button;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.context.PluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.core.defxml.entity.ext.ButtonDef;
import com.hotent.bpmx.core.defxml.entity.ext.Buttons;
import com.hotent.bpmx.core.defxml.entity.ext.ExtPlugins;
import com.hotent.bpmx.core.defxml.entity.ext.FieldSetting;
import com.hotent.bpmx.core.defxml.entity.ext.Form;
import com.hotent.bpmx.core.defxml.entity.ext.FormInitSetting;
import com.hotent.bpmx.core.defxml.entity.ext.InitItem;
import com.hotent.bpmx.core.defxml.entity.ext.InitItem.PrevSetting;
import com.hotent.bpmx.core.defxml.entity.ext.InitItem.SaveSetting;
import com.hotent.bpmx.core.defxml.entity.ext.MobileForm;
import com.hotent.bpmx.core.defxml.entity.ext.PropItem;
import com.hotent.bpmx.core.defxml.entity.ext.Propers;
import com.hotent.bpmx.core.defxml.entity.ext.Script;
import com.hotent.bpmx.core.defxml.entity.ext.Scripts;
import com.hotent.bpmx.core.defxml.entity.ext.SubProcessForm;
import com.hotent.bpmx.persistence.util.BpmDefAccessorUtil;
import com.hotent.bpmx.plugin.core.context.AbstractBpmPluginContext;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.model.FormType;
import com.hotent.form.persistence.model.DefaultForm;

public class PluginContextUtil {
	
	

	/**
	 * 处理节点插件。
	 * @param baseNodeDef
	 * @param baseNode 
	 * void
	 */
	public static void handBaseNode(BaseBpmNodeDef baseNodeDef,Object baseNode){

		//处理插件
		handPlugin( baseNodeDef, baseNode);
		//处理表单
		handForm(baseNodeDef, baseNode);
		//处理脚本
		handScript(baseNodeDef, baseNode);
		//表单初始化功能。
		handFormInit(baseNodeDef, baseNode);
		//节点属性。
		handNodeProperties(baseNodeDef, baseNode);
		//处理节点按钮
		handButton(baseNodeDef, baseNode);
		
	}
	
	
	/**
	 * 处理按钮。
	 * @param userNodeDef
	 * @param userTask 
	 * void
	 */
	private static void handButton(BaseBpmNodeDef userNodeDef,Object baseNode){
		List<Button> btnList=new ArrayList<Button>();
		
		Buttons buttons=  BpmDefAccessorUtil.getButtons(baseNode);
		
		if(buttons==null) return;
		
		List<ButtonDef> list= buttons.getButton();
		
		if(BeanUtils.isEmpty(list)) return;
		
		for(ButtonDef button:list){
			Button btn=new Button(button.getName(),button.getAlias(),button.getBeforeScript(),button.getAfterScript());
			btn.setGroovyScript(button.getGroovyScript());
			btn.setUrlForm(button.getUrlForm());
			btnList.add(btn);
		}
		
		userNodeDef.setButtons(btnList);
		
	}
	
	/**
	 * 处理节点属性。  
	 * @param userNodeDef
	 * @param baseNode 
	 * void
	 */
	private static void handNodeProperties(BaseBpmNodeDef userNodeDef,Object baseNode){
		Propers nodeProperties=BpmDefAccessorUtil.getNodeProperties(baseNode);
		
		if(nodeProperties==null) return ;
		
		List<PropItem> items= nodeProperties.getItem();
		
		if(BeanUtils.isEmpty(items)) return;
		
		for(PropItem item :items){
			NodeProperties properties=new NodeProperties();
			
			properties.setJumpType(item.getJumpType());
			properties.setBackMode(item.getBackMode());
			properties.setBackNode(item.getBackNode());
			properties.setBackUserMode(item.getBackUserMode());
			properties.setPostHandler(item.getPostHandler());
			properties.setPrevHandler(item.getPrevHandler());
			properties.setNodeId(userNodeDef.getNodeId());
			
			properties.setAllowExecutorEmpty(item.isAllowExecutorEmpty());
			properties.setSkipExecutorEmpty(item.isSkipExecutorEmpty());
			properties.setNotifyType(item.getNotifyType());
			properties.setDateType(item.getDateType());
			properties.setDueTime(item.getDueTime());
			properties.setPopWin(item.isPopWin());
			
			properties.setParentDefKey(item.getParentDefKey());
			
			userNodeDef.addNodeProperties(properties);
			
		}
	}
	
	private static void handFormInit(BaseBpmNodeDef baseNodeDef,Object baseNode){
		NodeType type=baseNodeDef.getType();
		if(!type.equals(NodeType.START) &&
				!type.equals(NodeType.USERTASK) &&
				!type.equals(NodeType.SIGNTASK)) return ;
		FormInitSetting formInitSetting=BpmDefAccessorUtil.getFormInitSetting(baseNode);
		if(formInitSetting==null) return ;
		List<InitItem> initItems= formInitSetting.getInitItem();
		if(BeanUtils.isEmpty(initItems)) return;
		
		//baseNodeDef.addFormInitItem(initItem);
		for(InitItem item:initItems){
			FormInitItem initItem=new FormInitItem();
			initItem.setNodeId(baseNodeDef.getNodeId());
			initItem.setParentDefKey(item.getParentDefKey());
			
			PrevSetting prev= item.getPrevSetting();
			if(prev!=null){
				List<FieldSetting> fieldInitSettings= prev.getFieldSetting();
				List<FieldInitSetting> showSettings=convertFieldSetting(fieldInitSettings);
				initItem.setShowFieldsSetting(showSettings);
			}
			SaveSetting save=item.getSaveSetting();
			if(save!=null){
				List<FieldSetting> saveInitSettings= save.getFieldSetting();
				List<FieldInitSetting> saveSettings=convertFieldSetting(saveInitSettings);
				initItem.setSaveFieldsSetting(saveSettings);
			}
			
			baseNodeDef.addFormInitItem(initItem);
		}
		
	}
	
	/**
	 * 转换表单字段初始化列表。 
	 * @param list
	 * @return 
	 * List&lt;FieldInitSetting>
	 */
	private static List<FieldInitSetting> convertFieldSetting(List<FieldSetting> list){
		List<FieldInitSetting> rtnlist=new ArrayList<FieldInitSetting>();
		
		for(FieldSetting fieldSetting:list){
			FieldInitSetting initSetting=new FieldInitSetting();
			initSetting.setDescription(fieldSetting.getDescription());
			initSetting.setSetting(fieldSetting.getSetting());
			
			rtnlist.add(initSetting);
		}
		
		return rtnlist;
	}
	
	/**
	 * 处理节点脚本。
	 * @param baseNodeDef
	 * @param baseNode 
	 * void
	 */
	private static void handScript(BaseBpmNodeDef baseNodeDef,Object baseNode){
		Scripts scripts=BpmDefAccessorUtil.getScripts(baseNode);
		if(scripts==null ) return;
		
		List<Script> list= scripts.getScript();
		for(Script script:list){
			ScriptType scriptType=ScriptType.fromKey(script.getScriptType().value());
			baseNodeDef.addScript(scriptType  , script.getContent());
		}
	}
	
	/**
	 * 处理子流程表单。
	 * @param baseNodeDef
	 * @param baseNode 
	 * void
	 */
	public static void handSubForm(BaseBpmNodeDef baseNodeDef,Object baseNode){
		SubProcessForm subForm=BpmDefAccessorUtil.getSubForm(baseNode);
		if(subForm==null) return;
		
		List<Form> list= subForm.getFormOrMobileForm();
		if(BeanUtils.isEmpty(list)) return ;
		
		List<com.hotent.form.api.model.Form> formList =new ArrayList<com.hotent.form.api.model.Form>();
		
		for(Form form:list){
			com.hotent.form.api.model.Form frm = new DefaultForm();
			frm.setName(form.getName());
			frm.setFormValue(form.getFormValue());
			com.hotent.bpmx.core.defxml.entity.ext.FormCategory formCategory = form.getType();
			if(formCategory != null){
				frm.setType(FormCategory.fromValue( form.getType().value() ));
			}
			frm.setParentFlowKey(form.getParentFlowKey());
			
			if(form instanceof MobileForm){
				frm.setFormType(FormType.MOBILE.value());
			}
			
			formList.add(frm);
		}
		
		baseNodeDef.setSubFormList(formList);
	}
	
	
	/**
	 * 处理表单定义。
	 * @param baseNodeDef
	 * @param baseNode 
	 * void
	 */
	private static void handForm(BaseBpmNodeDef baseNodeDef,Object baseNode){
		List<Form> formList=BpmDefAccessorUtil.getForm(baseNode);
		
		if(BeanUtils.isEmpty(formList)) return;
		
		List<com.hotent.form.api.model.Form> list=new ArrayList<com.hotent.form.api.model.Form>();
		
		for(Form form:formList){
			com.hotent.form.api.model.Form frm = new DefaultForm(); 
			frm.setName(form.getName());
			frm.setFormValue(form.getFormValue());
			FormCategory formCategory = null;
			if(form.getType() != null){
				formCategory = FormCategory.fromValue( form.getType().value() );
				frm.setType(formCategory);
			}
			if(form instanceof MobileForm) {
				frm.setFormType(FormType.MOBILE.value());
			}
			list.add(frm);
		}
		baseNodeDef.setForm(list); 
	}
	
	

	/**
	 * 处理节点插件。
	 * @param baseNodeDef
	 * @param baseNode 
	 * void
	 */
	private static void handPlugin(BaseBpmNodeDef baseNodeDef,Object baseNode){
		List<BpmPluginContext> list=new ArrayList<BpmPluginContext>();
		
		ExtPlugins extPlugins=BpmDefAccessorUtil.getNodeExtPlugins(baseNode);
		
		if(extPlugins==null) return ;
		
		List<Object> pluginList= extPlugins.getAny();
		for(Object obj:pluginList){
			if(!(obj instanceof Element)) continue;
				
			Element el=(Element)obj;
			//获取插件上下文定义。
			String pluginContextBeanId = el.getLocalName() + PluginContext.PLUGINCONTEXT; 
			Object objs = AppUtil.getBean(pluginContextBeanId);
			PluginContext pluginContext=(PluginContext)AppUtil.getBean(pluginContextBeanId);
			if(pluginContext==null) continue;
		
			//解析插件的XML数据。
			BpmPluginDef bpmPluginDef = pluginContext.parse(el);
			((AbstractBpmPluginContext)pluginContext).setBpmPluginDef(bpmPluginDef);
			
			// 防止从内存中获取到相同的对象
			BpmPluginContext cloneBean = (BpmPluginContext) BeanUtils.cloneBean(pluginContext);
			
			list.add(cloneBean);
			
			
		}
		baseNodeDef.setBpmPluginContexts(list);
		
	}
	
	
	
}
