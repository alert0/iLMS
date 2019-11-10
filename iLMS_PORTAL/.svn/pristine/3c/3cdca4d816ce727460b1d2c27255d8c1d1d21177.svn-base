package com.hotent.bpmx.core.engine.def;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmBoDef;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.model.process.def.BpmFormInit;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.def.FormInitItem;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.BaseBpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.FormExt;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.persistence.dao.BpmDefinitionDao;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormType;
import com.hp.hpl.sparta.Element;
import com.jamesmurty.utils.XMLBuilder;

public class BpmDefUtil {

	/**
	 * 获取流程的bo定义。 返回json格式如下：
	 * 
	 * [{"required":false,"key":"order","name":"订单"},{"required":false,"key":
	 * "orderItem","name":"订单项"}]
	 * 
	 * @param bpmProcessDefExt
	 * @return
	 */
	public static JSONArray getProcBoDef(BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt) {
		DefaultBpmProcessDefExt defaultBpmProcessDefExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		List<ProcBoDef> procBoDefList = defaultBpmProcessDefExt.getBoDefList();
		JSONArray jsonAry = (JSONArray) JSONArray.toJSON(procBoDefList);
		return jsonAry;
	}

	/**
	 * 返回bo数据定义。
	 * 
	 * @param bpmProcessDefExt
	 * @return
	 */
	public static BpmBoDef getBpmBoDef(BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt) {
		DefaultBpmProcessDefExt defaultBpmProcessDefExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();

		List<ProcBoDef> procBoDefList = defaultBpmProcessDefExt.getBoDefList();
		String saveMode = defaultBpmProcessDefExt.isBoSaveToDb() ? "database" : "boObject";
		BpmBoDef boDef = new BpmBoDef();

		boDef.setBoDefs(procBoDefList);
		boDef.setBoSaveMode(saveMode);

		return boDef;
	}
	
	/**
	 * 根据流程定义key和节点ID获取外部流程的bo定义。
	 * @param defKey
	 * @param nodeId
	 * @return
	 */
	public static BpmBoDef getBpmBoDef(String defKey){
		BpmDefinitionAccessor definitionAccessor =AppUtil.getBean(BpmDefinitionAccessor.class);
		
		BpmDefinitionDao definitionDao=AppUtil.getBean(BpmDefinitionDao.class);
		BpmDefinition def= definitionDao.getMainByDefKey(defKey);
		
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = definitionAccessor.getBpmProcessDef(def.getDefId());
		BpmBoDef boDef = BpmDefUtil.getBpmBoDef(bpmProcessDefExt);
		return boDef;
	}
	
	/**
	 * 根据流程实例获取顶级BO定义列表。
	 * <pre>
	 * 	判断实例是否为子流程实例，如果是 则往上查询，直到找到主实例，
	 * 取主实例的流程定义的元数据。
	 * </pre>
	 * @param instance
	 * @return
	 */
	public static DefaultBpmProcessDefExt  getProcessExt(BpmProcessInstance instance){
		BpmDefinitionAccessor definitionAccessor =AppUtil.getBean(BpmDefinitionAccessor.class);
		BpmProcessInstanceManager bpmProcessInstanceManager =AppUtil.getBean(BpmProcessInstanceManager.class);
		BpmProcessInstance topInstance= bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
		
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = definitionAccessor.getBpmProcessDef(topInstance.getProcDefId());
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
		return defExt;
	}

	

	/**
	 * 返回开始，任务和会签节点列表。
	 * 
	 * @param bpmProcessDefExt
	 * @return
	 */
	public static List<BpmNodeDef> getNodeDefs(BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt) {
		List<BpmNodeDef> nodeDefs = bpmProcessDefExt.getBpmnNodeDefs();
		List<BpmNodeDef> list = new ArrayList<BpmNodeDef>();

		for (BpmNodeDef bpmNodeDef : nodeDefs) {
			NodeType nodeType = bpmNodeDef.getType();
			if (nodeType == NodeType.START || nodeType == NodeType.USERTASK || nodeType == NodeType.SIGNTASK) {
				list.add(bpmNodeDef);
			}
		}
		return list;
	}

	/**
	 * bo格式。
	 * [{"required":false,"key":"order","name":"订单"},{"required":false,"key"
	 * :"orderItem","name":"订单项"}]
	 * 
	 * @param json
	 * @return
	 */
	public static List<ProcBoDef> parseBoDef(String json) {
		List<ProcBoDef> procBoDefList = JSONArray.parseArray(json, ProcBoDef.class);
		return procBoDefList;
	}

	/**
	 * 获取流程所有的初始化数据。
	 * 
	 * @param bpmProcessDefExt
	 * @return
	 */
	public static BpmFormInit getBpmFormInit(BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt, String parentDefKey) {
		List<BpmNodeDef> nodeDefs = bpmProcessDefExt.getBpmnNodeDefs();

		BpmFormInit formInit = new BpmFormInit();

		for (BpmNodeDef bpmNodeDef : nodeDefs) {
			NodeType nodeType = bpmNodeDef.getType();
			if (nodeType == NodeType.START || nodeType == NodeType.USERTASK || nodeType == NodeType.SIGNTASK) {
				if (StringUtil.isNotEmpty(parentDefKey)) {
					formInit.setParentDefKey(parentDefKey);
				}
				addFormInitItem(bpmNodeDef, formInit);
			}
		}

		return formInit;

	}
	
	/**
	 * 添加到BpmFormInit 项。
	 * @param def
	 * @param formInit
	 */
	private static void addFormInitItem(BpmNodeDef def, BpmFormInit formInit) {
		BaseBpmNodeDef nodeDef = (BaseBpmNodeDef) def;
		List<FormInitItem> items = nodeDef.getFormInitItems();
		for(FormInitItem item :items){
			if(item.getParentDefKey().equals(formInit.getParentDefKey())){
				formInit.addFormInitItem(item);
			}
		}
	}

	
	/**
	 * 获取流程表单XML.
	 * 
	 * @param form
	 *            表单
	 * @return
	 */
	public static String getFormXml(Form form) {
		if(form==null) return "";
		String pre=form.getFormType().equals(FormType.PC.value()) ?"ext:form" :"ext:mobileForm";
		return getXmlBuilder(form,pre);
	}

	/**
	 * 获取流程实例表单XML.
	 * 
	 * @param form
	 *            表单
	 * @return
	 */
	public static String getInstFormXml(Form form,boolean isPc) {
		if(form==null) return "";
		String pre=isPc?"ext:instForm" :"ext:instMobileForm";
		return getXmlBuilder(form, pre);
	}
	
	
	/**
	 * 获取表单的XML.
	 * 
	 * @param form
	 *            表单
	 * @param xmlName
	 *            xml名前缀
	 * @return
	 */
	private static String getXmlBuilder(Form form, String xmlName) {
		String xml = "";
		if (BeanUtils.isEmpty(form) || BeanUtils.isEmpty(form.getType()))  return  "";
		if(StringUtil.isEmpty(form.getFormValue())) return "";
		try {
			XMLBuilder ruleBuilder = XMLBuilder
					.create(xmlName)
					.a("xmlns:ext", BpmConstants.BPM_XMLNS)
					.a("name", form.getName())
					.a("type",BeanUtils.isEmpty(form.getType()) ? "" : form.getType().value())
					.a("formValue", form.getFormValue());
			
			if (StringUtil.isNotEmpty(form.getParentFlowKey()))
				ruleBuilder.a("parentFlowKey", form.getParentFlowKey());

			// 如果属于 FormExt 加入扩展
			if (form instanceof FormExt) {
				FormExt formExt = (FormExt) form;
				String prevHandler = formExt.getPrevHandler();
				if (StringUtil.isNotEmpty(prevHandler))
					ruleBuilder.a("prevHandler", prevHandler);
				String postHandler = formExt.getPostHandler();
				if (StringUtil.isNotEmpty(postHandler))
					ruleBuilder.a("postHandler", postHandler);
			}
			xml = ruleBuilder.asString();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * 获取流程表单XML.
	 * 
	 * @param form
	 *            表单
	 * @return
	 * @throws FactoryConfigurationError 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	public static String getRestfulXml(List<Restful> restfuls){
		String xml = "";
		if(BeanUtils.isNotEmpty(restfuls)){
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("<ext:globalRestFuls xmlns:ext=\"" + BpmConstants.BPM_XMLNS + "\" >");
				for (Restful restful : restfuls) {
					try {
						XMLBuilder xmlBuilder = XMLBuilder.create("ext:globalRestFul",BpmConstants.BPM_XMLNS).e("ext:url").t(restful.getUrl()).up().e("ext:desc").t(restful.getDesc()).up().e("ext:header").t(restful.getHeader()).up()
								.e("ext:invokeMode").t(String.valueOf(restful.getInvokeMode())).up().e("ext:callTime").t(restful.getCallTime()).up().a("params", restful.getParams()).a("outPutScript", restful.getOutPutScript())
								.a("parentDefKey", restful.getParentDefKey());
						sb.append(xmlBuilder.asString() + "\n");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				sb.append("</ext:globalRestFuls>");
				xml = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xml;
	}
	
}
