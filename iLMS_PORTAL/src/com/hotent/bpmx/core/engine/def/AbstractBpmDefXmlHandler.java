package com.hotent.bpmx.core.engine.def;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefXmlHandler;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.persistence.dao.BpmDefDataDao;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.model.BpmDefData;

/**
 * 保存流程定义处理器。
 * @author ray
 *
 * @param <T>
 */
public abstract class AbstractBpmDefXmlHandler<T> implements BpmDefXmlHandler<T>{
	
	@Resource
	protected BpmDefinitionManager bpmDefinitionManager; 
	
	@Resource
	protected BpmDefDataDao bpmDefDataDao;
	@Resource
	protected BpmDefinitionAccessor bpmDefinitionAccessor;

	
	protected void updateXml(String defId,String bpmnXml){
		BpmDefData bpmData=bpmDefDataDao.get(defId);
		bpmData.setBpmnXml(bpmnXml);
		bpmDefinitionManager.updBpmData(defId,bpmData);
	}
	
	/**
	 * 抽象方法，由实际的类去实现，获取具体的XML。
	 * @param defId
	 * @param nodeId
	 * @param xml
	 * @return
	 */
	protected abstract String getXml(String defId, String nodeId, T xml) ;


	@Override
	public void saveNodeXml(String defId, String nodeId, T setting) {
		
		String xml=getXml(defId, nodeId, setting);
		updateXml(defId,xml);
	}
	
	
	protected List<BpmNodeDef> getNodeList(String defId){
		List<BpmNodeDef> list=new ArrayList<BpmNodeDef>();
		List<BpmNodeDef> bpmNodeDefList= bpmDefinitionAccessor.getAllNodeDef(defId);
		
		for (BpmNodeDef bpmNodeDef : bpmNodeDefList) {
			if( NodeType.START.equals(bpmNodeDef.getType())||
					 NodeType.USERTASK.equals(bpmNodeDef.getType()) ||
					 NodeType.SIGNTASK.equals(bpmNodeDef.getType() ))
				list.add(bpmNodeDef);
		
		}
		return list;
	}
}
