package com.hotent.bpmx.listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.db.ITableOperator;
import com.hotent.base.api.db.model.Column;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.table.impl.mysql.MySQLTableOperator;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.exception.BOBaseException;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;
import com.hotent.bo.api.instance.DataTransform;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.DataType;
import com.hotent.bpmx.api.exception.HandlerException;
import com.hotent.bpmx.api.model.process.def.NodeProperties;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.nodedef.ext.extmodel.ProcBoDef;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.api.service.DataObjectHandler;
import com.hotent.bpmx.core.engine.def.BpmDefUtil;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.core.util.HandlerUtil;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;

public class BusDataUtil {
	
	
	/**
	 * 处理bo数据到业务中间关联表。
	 * 
	 * @param model
	 * @param boResult
	 *            void
	 */
	public static void handlerBusinessLink(BpmProcessInstance instance,List<BoResult> boResults,String saveMode) {
		BpmBusLinkManager bpmBusLinkManager =AppUtil.getBean(BpmBusLinkManager.class);
		
		
		for(BoResult result:boResults){
			
			String action=result.getAction();
			if(BoResult.ACTION_TYPE.ADD.equals(action)){
				
				BpmBusLink busLink = BpmUtil.buildBusLink(instance,result,saveMode);
				//创建分区。
				createPartition(busLink.getFormIdentify());
				
				bpmBusLinkManager.create(busLink);
			}
			
			else if(BoResult.ACTION_TYPE.DELETE.equals(action)) {
				BaseBoEnt boEnt=result.getBoEnt();
				boolean isNumber=boEnt.isPkNumber();
				
				bpmBusLinkManager.delByBusinesKey(result.getPk(), result.getBoEnt().getName(), isNumber);
			}
			//更新暂不做处理。
			else if(BoResult.ACTION_TYPE.UPDATE.equals(action)){
				// 用已有的表单数据启动流程
				BpmBusLink bbl = bpmBusLinkManager.getByBusinesKey(result.getPk(), result.getBoEnt().isPkNumber());
				if(BeanUtils.isEmpty(bbl)){
					BpmBusLink busLink = BpmUtil.buildBusLink(instance,result,saveMode);
					//创建分区。
					createPartition(busLink.getFormIdentify());
					bpmBusLinkManager.create(busLink);
				}
				
			}
		}
	}
	
	/**
	 * 是否支持分区。
	 */
	private static int  supportPart=-1;
	
	private static Set<String> partions= Collections.synchronizedSet(new HashSet<String>());
	
	private static final String tableName="BPM_BUS_LINK";
	
	/**
	 * 创建分区。
	 * @param partName
	 */
	private static void createPartition(String partName){
		if(StringUtil.isEmpty(partName)) return;
		
		ITableOperator tableOperator=(ITableOperator)AppUtil.getBean("tableOperator");
		// TODO MySQL动态创建分区的语句有误，在MySQL中不能对字符串类型的字段创建 LIST分区
		if(tableOperator instanceof MySQLTableOperator){
			return;
		}
		if(supportPart==-1){
			//表是否支持分区。
			boolean isSupport=tableOperator.supportPartition(tableName);
			supportPart=isSupport?1:0;
		}
		
		if(supportPart==0) return;
		//是否存在指定的分区。
		if(partions.contains(partName)) return;
		
		boolean isPartExist=tableOperator.isExsitPartition(tableName, partName);
		
		//添加缓存partions
		partions.add(partName);
		
		if(isPartExist) return;
		
		tableOperator.createPartition(tableName, partName);
	}


	
	/**
	 * 处理多个业务主键的业务中间表数据问题。
	 * @param cmd
	 */
	public static void handExt(ActionCmd cmd){
		Map<String,String> pairs= cmd.getDataPair();
		if(BeanUtils.isNotEmpty(pairs)) return ;
		//添加中间表。
		addBusLink(cmd);
	}
	
	
	//构建BPM_BUS_LINK;
	private static void addBusLink(ActionCmd cmd){
		BpmProcessInstanceManager bpmProcessInstanceManager=AppUtil.getBean(BpmProcessInstanceManager.class);
		
		BpmBusLinkManager bpmBusLinkManager=AppUtil.getBean(BpmBusLinkManager.class);
		
		String instId=cmd.getInstId();
		
		Map<String,String> pairs=cmd.getDataPair();
	
		DataType dataType= cmd.getPkDataType();
		
		BpmProcessInstance instance= bpmProcessInstanceManager.get(instId);
		
		boolean isNumber=!DataType.STRING.equals(dataType);
		
		for (Map.Entry<String, String> entry : pairs.entrySet()) {
			String key=entry.getKey();
			String val=entry.getValue();
			
			BpmBusLink bpmBusLink = bpmBusLinkManager.getByBusinesKey(val, key, isNumber);
			if(bpmBusLink!=null) continue;
			
			BpmBusLink busLink=BpmUtil.buildBusLink(instance);
			busLink.setIsMain(1);
			busLink.setFormIdentify(key);
			
			if(!isNumber){
				busLink.setBusinesskeyStr(val);
			}
			else{
				busLink.setBusinesskey(Long.parseLong( val));
			}
			//添加分区。
			createPartition(key);
			
			bpmBusLinkManager.create(busLink);
		}
	}
	
	/**
	 * 支持处理器。
	 * @param properties
	 * @param actionCmd
	 * @param isBefore
	 */
	public static void executeHandler(NodeProperties properties,ActionCmd actionCmd,boolean isBefore ){
		if(properties==null) return;
	

		String handler=isBefore?properties.getPrevHandler():properties.getPostHandler();
		if(StringUtil.isEmpty(handler)) return;
		
		try{
			HandlerUtil.invokeHandler(actionCmd,handler );
		}
		catch(Exception ex){
			throw new HandlerException(ex.getMessage(), ex.getCause());
		}
	}
	
	/**
	 * 通过流程实例ID更新BO数据
	 * @param instanceId 流程实例ID
	 * @param boDatas bo实例
	 */
	public static void updateBoData(String instanceId, BoData...boDatas){
		BpmBusLinkManager bpmBusLinkManager = AppUtil.getBean(BpmBusLinkManager.class);
		BoInstanceFactory boInstanceFactory=AppUtil.getBean(BoInstanceFactory.class);
		
		for (BoData boData : boDatas) {
			BaseBoDef boDef = boData.getBoDef();
			BaseBoEnt boEnt = boData.getBoEnt();
			String pkKey = boEnt.getPkKey();
			String pkType = boEnt.getPkType();
			Object pkValue = boData.getByKey(pkKey);
			if(BeanUtils.isEmpty(pkValue)) throw new BOBaseException("要更新的bo没有主键数据");
			String pk = pkValue.toString();
			
			BpmBusLink businesKey = bpmBusLinkManager.getByBusinesKey(pk, Column.COLUMN_TYPE_NUMBER.equals(pkType));
			String saveMode = businesKey.getSaveMode();
			BoDataHandler handler= boInstanceFactory.getBySaveType(saveMode);
			handler.save(pk, boDef.getId(), boData);
		}
	}
	
	/**
	 * 处理bo数据。
	 * @param instance
	 * @param cmd
	 */
	public static void handSaveBoData(BpmProcessInstance instance,  ActionCmd cmd)  {
		
		String boJson=cmd.getBusData();
		if(StringUtil.isEmpty(boJson)) {
			BoDataService boDataService = AppUtil.getBean(BoDataService.class);
			List<BoData> boDatas = boDataService.getDataByInst(instance);
			Map<String,BoData> boMap=new HashMap<String, BoData>();
			for(BoData data:boDatas){
				String code=data.getBoDef().getAlias();
				boMap.put(code, data);
			}
			// 将BO放入cmd上下文中。
			cmd.getTransitVars().put(BpmConstants.BO_INST, boMap);
			return;
		}
		
		DataTransform dataTransform=AppUtil.getBean(DataTransform.class);
		BpmBusLinkManager  bpmBusLinkManager=AppUtil.getBean(BpmBusLinkManager.class);
		BoInstanceFactory boInstanceFactory=AppUtil.getBean(BoInstanceFactory.class);
		DataObjectHandler dataObjectHandler=AppUtil.getBean(DataObjectHandler.class);
		BoDefService boDefService=AppUtil.getBean(BoDefService.class);
		
		DefaultBpmProcessDefExt bpmProcessDefExt = BpmDefUtil.getProcessExt(instance);
		
		
		JSONObject jsonObj = JSONObject.parseObject(boJson);
		// 验证BO数据。
		BoDataUtil.validBo(bpmProcessDefExt, jsonObj);
		// BO Map数据。
		Map<String, JSONObject> jsonMap = BoDataUtil.getMap(jsonObj);

		List<ProcBoDef> list = bpmProcessDefExt.getBoDefList();

		String saveType = bpmProcessDefExt.isBoSaveToDb() ? BodefConstants.SAVE_MODE_DB : BodefConstants.SAVE_MODE_BOOBJECT;
		
		BoDataHandler handler= boInstanceFactory.getBySaveType(saveType);
		
		String instId=instance.getId();

		Map<String, BoData> boMap = new HashMap<String, BoData>();
		
		List<BoData> boDatas=new ArrayList<BoData>();
		
		Map<String,BpmBusLink> linkMap=bpmBusLinkManager.getMapByInstId(instId);
		
		for (ProcBoDef boDef : list) {
			String key = boDef.getKey();
			if(!jsonMap.containsKey(key)) continue;
			JSONObject json = jsonMap.get(key);
			
			BaseBoDef def= boDefService.getByName(key);
			BaseBoEnt boEnt=def.getBoEnt();
			
			//BO数据。
			BoData curData= dataTransform.parse(json.toJSONString());
			curData.setBoDef(def);
			curData.setBoEnt(boEnt);
			
			boDatas.add(curData);
		}
		//处理数据。
		BpmTask bpmTask=(BpmTask) cmd.getTransitVars(BpmConstants.BPM_TASK);
		if(BeanUtils.isNotEmpty(bpmTask)){
			dataObjectHandler.handSaveData(instance,bpmTask.getNodeId(), boDatas);
		}else{
			dataObjectHandler.handSaveData(instance, boDatas);
		}

		for (BoData boData : boDatas) {
			BaseBoEnt boEnt =boData.getBoEnt();
			BaseBoDef def= boData.getBoDef();
			String id="";
			//新增
			if(BeanUtils.isNotEmpty(linkMap)){
				BpmBusLink busLink=linkMap.get(def.getAlias());
				//取得主键。
				if(busLink!= null)
				id=boEnt.isPkNumber()? busLink.getBusinesskey().toString():  busLink.getBusinesskeyStr();
			}
			
			List<BoResult> boResults= handler.save(id, def.getId(), boData);
			//处理业务中间表数据信息。
			BusDataUtil.handlerBusinessLink(instance, boResults,saveType);
			
			boMap.put(def.getAlias(), boData);
			
		}
		// 将BO放入cmd上下文中。
		cmd.getTransitVars().put(BpmConstants.BO_INST, boMap);
	}
}
