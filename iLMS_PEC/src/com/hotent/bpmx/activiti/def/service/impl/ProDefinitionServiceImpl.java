package com.hotent.bpmx.activiti.def.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import com.hotent.bpmx.activiti.cache.ActivitiDefCache;
import com.hotent.bpmx.activiti.def.dao.ProDefinitionDao;
import com.hotent.bpmx.activiti.def.impl.EclipseDefTransform;
import com.hotent.bpmx.activiti.def.impl.FlashDefTransform;
import com.hotent.bpmx.activiti.def.impl.WebDefTransform;
import com.hotent.bpmx.api.constant.DesignerType;
import com.hotent.bpmx.natapi.def.DefTransform;
import com.hotent.bpmx.natapi.def.NatProDefinitionService;

/**
 * <pre>
 * 描述：流程定义服务类
 * 构建组：x5-bpmx-activiti
 * 作者：csx
 * 邮箱:chensx@jee-soft.cn
 * 日期:2013-11-22-上午9:37:45
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class ProDefinitionServiceImpl implements NatProDefinitionService {
	
	@Resource
	RepositoryService repositoryService;
	@Resource
	ProDefinitionDao proDefinitionDao;
	
	public String deploy(String tenantId,String name, String defXml)
			throws UnsupportedEncodingException {
		InputStream stream = new ByteArrayInputStream(defXml.getBytes("utf-8"));
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		deploymentBuilder.tenantId(tenantId);
		deploymentBuilder.name(name);
		deploymentBuilder.addInputStream("bpmn20.xml", stream);
		Deployment deploy = deploymentBuilder.deploy();

		return deploy.getId();
	}
	/*
	 * (non-Javadoc)
	 * @see com.hotent.bpmx.natapi.proc.NatProDefinitionService#getDefXmlByDeployId(java.lang.String)
	 */
	public String getDefXmlByDeployId(String deployId) {
		return proDefinitionDao.getDefXmlByDeployId(deployId); 
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hotent.bpmx.natapi.proc.NatProDefinitionService#writeDefXml(java.lang.String, java.lang.String)
	 */
	public void writeDefXml(String deployId, String defXml) {
		proDefinitionDao.writeDefXml(deployId, defXml);
		String actDefId=getProcessDefinitionIdByDeployId(deployId);
		ActivitiDefCache.clearByDefId(actDefId);
	}
	
	
	
	public String getProcessDefinitionIdByDeployId(String deployId) {
		ProcessDefinition proDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
		if(proDefinition==null) return null;
		return proDefinition.getId();
	}
	
	@Override
	public void clearCacheByBpmnDefId(String bpmnDefId) {
		ActivitiDefCache.clearByDefId(bpmnDefId);
	}
	
	
	
	@Override
	public DefTransform getDefTransform(DesignerType designerType) {
		DefTransform trans=null;
		switch (designerType) {
			case WEB:
				trans=new WebDefTransform();
				break;
			case ECLIPSE:
				trans=new EclipseDefTransform();
				break;
			case FLASH:
				trans=new FlashDefTransform();
				break;
		}
		return trans;
	}
	
}
