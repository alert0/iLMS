package com.hotent.bpmx.core.engine.form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.NodeType;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.manager.BpmCheckOpinionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;

@Service
public class BoDataServiceImpl implements BoDataService {
	
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BoInstanceFactory boInstanceFactory;
	@Resource
	BpmBusLinkManager bpmBusLinkManager;
	@Resource
	BpmCheckOpinionManager bpmCheckOpinionManager;

	
	@Override
	public List<BoData> getDataByInst(BpmProcessInstance instance){
		//获取最外层的流程实例数据。
		instance=bpmProcessInstanceManager.getTopBpmProcessInstance(instance);
		
		DefaultBpmProcessDefExt defExt=BpmDefUtil.getProcessExt(instance);
		List<ProcBoDef> boList= defExt.getBoDefList();

		if (BeanUtils.isEmpty(boList)) return Collections.emptyList();
		//根据实例ID获取关联数据。
		Map<String, BpmBusLink> keyValueMap =bpmBusLinkManager.getMapByInstId(instance.getId());

		String saveMode =  defExt.isBoSaveToDb() ? BodefConstants.SAVE_MODE_DB : BodefConstants.SAVE_MODE_BOOBJECT;
		
		BoDataHandler handler= boInstanceFactory.getBySaveType(saveMode);

		List<BoData> dataObjects = new ArrayList<BoData>();

		for (String key : keyValueMap.keySet()){
			BpmBusLink link = keyValueMap.get(key);
			//本来应该是根据boent的主键类型来判断拿str还是key的，但为了方便其实都拿一次也行
			String id=StringUtil.isNotEmpty(link.getBusinesskeyStr())?link.getBusinesskeyStr():link.getBusinesskey().toString();
			BoData boData= handler.getById(id, link.getBoDefCode());
			dataObjects.add(boData);
		}
		return dataObjects;
	}
	
	/**
	 * 1.根据流程定义ID获取流程定义定义的BO列表。
	 * 2.根据bocode 获取 bodata数据。
	 */
	@Override
	public List<BoData> getDataByDefId(String defId){
		BpmProcessDef<BpmProcessDefExt> bpmProcessDef = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt = (DefaultBpmProcessDefExt) bpmProcessDef.getProcessDefExt();
		List<ProcBoDef> boList = defExt.getBoDefList();
		if (BeanUtils.isEmpty(boList)) return null;
		 
		String saveMode = defExt.isBoSaveToDb() ? BodefConstants.SAVE_MODE_DB : BodefConstants.SAVE_MODE_BOOBJECT;
		 
		BoDataHandler handler= boInstanceFactory.getBySaveType(saveMode);

		List<BoData> dataObjects = new ArrayList<BoData>();
		for (ProcBoDef procBoDef : boList){
			String boKey = procBoDef.getKey();
			BoData boData= handler.getByBoDefCode(boKey);
			dataObjects.add(boData);
		}
		return dataObjects;
	}
	
	@Override
	public List<BoData> getDataByBoKeys(List<String> boKeyList){
		List<BoData> dataObjects = new ArrayList<BoData>();
		BoDataHandler handler= boInstanceFactory.getBySaveType(BodefConstants.SAVE_MODE_DB);//?

		for (String key : boKeyList){
			BoData boData= handler.getByBoDefCode(key);
			dataObjects.add(boData);
		}
		return dataObjects;
	}
	
	
	/**
	 * 表单意见转换。
	 * {
		"caiwuOpinion":[{auditor:"",opinion:"",createTime:"",auditorName:"",status:""},{auditor:"",opinion:"",auditorName:"",userName:"",status:""}],
		"juzhangyOpinion":[{auditor:"",opinion:"",createTime:"",auditorName:"",status:""},{auditor:"",opinion:"",auditorName:"",userName:"",status:""}]
		}
	 */
	@Override
	public JSONObject getFormOpinionJson(String proInstId) {
		JSONObject json=new JSONObject();
		
		List<DefaultBpmCheckOpinion> opinionList = bpmCheckOpinionManager.getFormOpinionByInstId(proInstId);
		//节点和类型的映射
		Map<String,Boolean> nodeTypeMap=convertNodeDef( proInstId);
		
		Map<String,String> identityMap=new HashMap<String, String>();
	
		for(DefaultBpmCheckOpinion opinion:opinionList){
			String formIdentity=opinion.getFormName();
			JSONObject opinionJson= getJsonByOpinion(opinion);
			identityMap.put(formIdentity, opinion.getTaskKey());
			
			if(json.containsKey(formIdentity)){
				JSONArray ary=json.getJSONArray(formIdentity);
				ary.add(opinionJson);
				
			}
			else{
				JSONArray ary=new JSONArray();
				ary.add(opinionJson);
				json.put(formIdentity, ary);
			}
		}
		Set<String> keySet= json.keySet();
		
		JSONObject rtnJson=new JSONObject();
		
		for(Iterator<String> it= keySet.iterator();it.hasNext();){
			String key=it.next();
			JSONArray ary= json.getJSONArray(key);
			String nodeId=identityMap.get(key);
			boolean isSignTask = false;
			if(nodeTypeMap.containsKey(nodeId)){
				isSignTask=nodeTypeMap.get(nodeId);
			}
			if(isSignTask){
				rtnJson.put(key, ary);
			}
			else{
				JSONArray tmpAry=new JSONArray();
				tmpAry.add(ary.get(ary.size()-1));
				rtnJson.put(key, tmpAry);
			}
		}
		
		
		return rtnJson;
	}
	
	/**
	 * 返回节点和节点类型的map。
	 * 类型：true : 会签 ,false :任务节点
	 * @param proInstId
	 * @return
	 */
	private Map<String,Boolean> convertNodeDef(String proInstId){
		BpmProcessInstance instance= bpmProcessInstanceManager.get(proInstId);
		List<BpmNodeDef> nodeList=bpmDefinitionAccessor.getAllNodeDef(instance.getProcDefId());
		Map<String,Boolean> map=new HashMap<String, Boolean>();
		for(BpmNodeDef def:nodeList){
			map.put(def.getNodeId(),NodeType.SIGNTASK.equals( def.getType()));
		}
		return map;
		
	}
	
	private JSONObject getJsonByOpinion(DefaultBpmCheckOpinion opinion){
		JSONObject json=new JSONObject();
		json.put("nodeId", opinion.getTaskKey());
		json.put("opinion", opinion.getOpinion());
		json.put("createTime", opinion.getCompleteTime());
		json.put("status", opinion.getStatus());
		json.put("auditorName", opinion.getAuditorName());
		json.put("auditor", opinion.getAuditor());
		return json;
	}

	

}
