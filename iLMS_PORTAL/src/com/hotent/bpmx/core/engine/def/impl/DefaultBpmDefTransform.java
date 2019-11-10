package com.hotent.bpmx.core.engine.def.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.JAXBUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BoDefXml;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.FormExt;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.BpmDefTransform;
import com.hotent.bpmx.core.model.def.BpmDefXml;
import com.hotent.bpmx.core.model.def.BpmDefXmlList;
import com.hotent.bpmx.natapi.def.NatProDefinitionService;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.form.api.model.Form;
import com.hotent.form.api.model.FormCategory;
import com.hotent.form.api.service.FormService;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.form.persistence.model.BpmFormRight;
import com.hotent.form.persistence.model.BpmFormRightXml;
import com.hotent.org.api.model.IUser;
import com.hotent.sys.util.ContextUtil;

/**
 * 流程定义导入导出的实现。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-20-上午11:55:32
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class DefaultBpmDefTransform implements BpmDefTransform {
	
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	
	@Resource
	NatProDefinitionService natProDefinitionService;
	
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmProBoManager bpmProBoManager;
	@Resource
	BoDefService boDefService;
	@Resource
	FormService formService;
	@Resource 
	BpmFormRightManager bpmFormRightManager;
	
	@Override
	public Map<String,String> exportDef(List<String> defList) {
		Map<String,String> map =  new HashMap<String, String>();
		BpmDefXmlList list=new BpmDefXmlList();
		BpmFormRightXml formRightList = new BpmFormRightXml();
		Set<String> formKeys = new HashSet<String>();
		Set<String> bocodes = new HashSet<String>();
 		for(String defId:defList){
			//处理表单
			handelFormBo(defId,formKeys,bocodes);
			
			BpmDefXml defXml= getByDefId(defId);
			list.addBpmDefXml(defXml);
			
			formRightList.addBpmFormRight(getFormRightsByDefId(defId));
		}
		try {
			String xml=JAXBUtil.marshall(list, BpmDefXmlList.class);
			String boXml =getBoXml(bocodes);
			String formXml =formService.getFormExportXml(formKeys);
			String formRightXml = JAXBUtil.marshall(formRightList, BpmFormRightXml.class);
			
			map.put("bpmdefs.flow.xml",xml);
			map.put("bo.xml",boXml);
			map.put("form.xml", formXml);
			map.put("formrights.xml", formRightXml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导出失败！"+e.getMessage(),e);
		}
		return map;
	}
	
	// 导出表单权限
	private List<BpmFormRight> getFormRightsByDefId(String defId) {
		DefaultBpmDefinition definition= bpmDefinitionManager.getById(defId);
		List<BpmFormRight> list = bpmFormRightManager.getByFlowKey(definition.getDefKey());
		return list;
	}
	//导出bo 的xml
	private String getBoXml(Set<String> bocodes) throws Exception {
		BoDefXml bodefXml =  new BoDefXml();
		for (String boId : bocodes) {
			BaseBoDef bodef  = boDefService.getByName(boId);
			bodefXml.addBodef(bodef);
		}
		return JAXBUtil.marshall(bodefXml, BoDefXml.class);
		
	}
	//处理表单和BO
	private void handelFormBo(String defId,Set<String> form, Set<String> bocode) {
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		
		// 取出formKey 为导出做准备
		Form globalForm = defExt.getGlobalForm();
		if(globalForm!= null && FormCategory.INNER==globalForm.getType()&& StringUtil.isNotEmpty(globalForm.getFormValue()))
			form.add(globalForm.getFormValue());
		
		FormExt globalMobileForm = defExt.getGlobalMobileForm();
		if(globalMobileForm!= null && FormCategory.INNER==globalMobileForm.getType()&& StringUtil.isNotEmpty(globalMobileForm.getFormValue()))
			form.add(globalMobileForm.getFormValue());
		
		
		List<BpmNodeDef> nodeList = bpmDefinitionAccessor.getSignUserNode(defId);
		for(BpmNodeDef bpmNodeDef:nodeList){
			Form nodeForm = bpmNodeDef.getForm();
			if(nodeForm != null && FormCategory.INNER==nodeForm.getType() && StringUtil.isNotEmpty(nodeForm.getFormValue()))
				form.add(nodeForm.getFormValue());
			

			Form mobileNodeForm = bpmNodeDef.getMobileForm();
			if(mobileNodeForm != null && FormCategory.INNER==mobileNodeForm.getType() && StringUtil.isNotEmpty(mobileNodeForm.getFormValue()))
				form.add(mobileNodeForm.getFormValue());
			
		}
		//取出所有Bo
		List<ProcBoDef> boDefList = defExt.getBoDefList();
		for (ProcBoDef procBoDef : boDefList) {
			bocode.add(procBoDef.getKey());
		}
	}

	@Override
	public void importDef(String unZipFilePath)  {
		try {
			String flowXml = FileUtil.readFile( unZipFilePath + "/bpmdefs.flow.xml");
			String formXmlStr = FileUtil.readFile(unZipFilePath + "/form.xml");
			String boXmlStr = FileUtil.readFile(unZipFilePath +  "/bo.xml");
			String formRightsXml = FileUtil.readFile(unZipFilePath +  "/formrights.xml");
			
			// 流程xml 导入处理
			BpmDefXmlList defList=(BpmDefXmlList) JAXBUtil.unmarshall(flowXml, BpmDefXmlList.class);
			List<BpmDefXml> list= defList.getBpmList();
			for(BpmDefXml defXml:list){
				importDef(defXml);
			}
			if(StringUtil.isNotEmpty(boXmlStr)){
				// bo 导入处理
				BoDefXml boXml = (BoDefXml) JAXBUtil.unmarshall(boXmlStr, BoDefXml.class);
				List<BaseBoDef> boDefs =boXml.getDefList();
				boDefService.importBoDef(boDefs);
				if(StringUtil.isNotEmpty(formXmlStr)){
					// 表单xml导入处理
					formService.importForm(formXmlStr);
				}
				if(StringUtil.isNotEmpty(formRightsXml)){
					// 导入表单权限
					bpmFormRightManager.importFormRights(formRightsXml);
				}
				
			}
			
			
			
		} catch (Exception e) {
			throw new RuntimeException("XML转换为POJO类型错误"+e.getMessage(),e);
		}
	}

	/**
	 * 根据流程定义ID获取BpmDefXml。
	 * @param defId
	 * @return 
	 * BpmDefXml
	 */
	private BpmDefXml getByDefId(String defId){
		DefaultBpmDefinition definition= bpmDefinitionManager.getById(defId);

		BpmDefXml defXml=new BpmDefXml();
		//流程定义
		defXml.setBpmDefinition(definition);
		
		return defXml;
	}
	
	/**
	 * 导入某个流程。
	 * @param defXml 
	 * void
	 */
	private void importDef(BpmDefXml defXml){
		importDefinition(defXml);
	}
	

	/**
	 * 导入流程定义。
	 * @param defXml
	 * @return  DefaultBpmDefinition
	 */
	private DefaultBpmDefinition importDefinition(BpmDefXml defXml){
		String defId=UniqueIdUtil.getSuid();
		DefaultBpmDefinition def= defXml.getBpmDefinition();
		def.setDefId(defId);
		//发布流程
		String bpmnXml=def.getBpmnXml();
		
		String deployId="";
		try {
			deployId = natProDefinitionService.deploy("",def.getName(),bpmnXml);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String  bpmnDefId=natProDefinitionService.getProcessDefinitionIdByDeployId(deployId);
		
		def.setIsMain("Y");
		def.setBpmnDefId(bpmnDefId);
		def.setBpmnDeployId(deployId);
		Integer version= bpmDefinitionManager.getMaxVersion(def.getDefKey());
		def.setVersion(version);
		
		IUser user=ContextUtil.getCurrentUser();
		if(user!=null){
			def.setCreateBy(user.getUserId());
		}
		
		def.setCreateTime(new Date());
		
		bpmDefinitionManager.create(def);
		
		bpmDefinitionManager.updMainVersion(defId);
		
		return def;
	}
	

}
