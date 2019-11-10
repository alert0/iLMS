package com.hotent.mini.ext.handler;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.service.BpmInstService;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.mini.ext.script.ScriptImpl;
import com.hotent.oa.persistence.dao.DemoUserDao;
import com.hotent.oa.persistence.model.DemoUser;
import com.hotent.sys.util.ContextUtil;

@Service
public class DemoHandler {
	
	@Resource
	DemoUserDao demoUserDao;
	
	@Resource
	BpmInstService bpmInstService;
	
	@Resource
	ScriptImpl scriptImpl;
	/**
	 * 处理数据。
	 * @param processCmd
	 */
	public void handerDemoUser(ActionCmd processCmd){
		//获取流程实例ID
 		String instId=processCmd.getInstId();
		//表单数据
		String data=processCmd.getBusData();
	 
		//判断CMD类型 启动时用的是DefaultProcessInstCmd 其它情况都是用 DefaultTaskFinishCmd
//		boolean isFinishCmd= scriptImpl.isDefaultTaskFinishCmd(processCmd);
		//ContextThreadUtil
//		String	actionName;
//		String taskId;
//		if(isFinishCmd){
//			DefaultTaskFinishCmd cmd=(DefaultTaskFinishCmd)processCmd;
//			taskId=cmd.getTaskId();
//			actionName=cmd.getActionName();
//		}
//		else {
//			Set<BpmTask> set=ContextThreadUtil.getByInstId(instId);
//			List<BpmTask> list = new ArrayList<BpmTask>(set); 
//			taskId=list.get(0).getTaskId();
//			actionName=processCmd.getTransitVars("actionName").toString();
//			
//			
//			//processCmd.getBpmProcessInstance();
//		}
//		
//	    
//	    if(!actionName.equals("saveDraft")&&!actionName.equals("startFlow"))
//	    {
//	       BpmProcessInstance inst =bpmInstService.getProcessInstance(instId);
//	       String bk=inst.getBizKey();
//	    }
	    
//		DemoUser demoUser=JSONObject.parseObject(data, DemoUser.class);
//		if()
//		if(StringUtil.isEmpty(demoUser.getId())){
//			String id=UniqueIdUtil.getSuid();
//			//启动时放后置处理器时调用
//			if(processCmd.getTransitVars().containsKey("bpmProcessInstance")){
//			   DefaultBpmProcessInstance inst= (DefaultBpmProcessInstance)processCmd.getTransitVars("bpmProcessInstance");
//			   inst.setBizKey(id);
//			}
//			//必须调用
//			
//			processCmd.setBusinessKey(id);
//			demoUser.setId(id);
//			demoUserDao.create(demoUser);
//		}
//		else{
//			demoUserDao.update(demoUser);
//		}
		
		if(processCmd.getActionName().equals("backToStart"))return;
		if(processCmd.getActionName().equals("reject"))return;
		if(StringUtil.isEmpty(data))return;
		DemoUser demoUser=JSONObject.parseObject(data, DemoUser.class);
		if(StringUtil.isEmpty(processCmd.getBusinessKey())){
			String id=UniqueIdUtil.getSuid();
			//必须调用
			processCmd.setBusinessKey(id);
			demoUser.setId(id);
			demoUserDao.create(demoUser);
			//启动时放后置处理器时调用
			if(processCmd.getTransitVars().containsKey(BpmConstants.PROCESS_INST)){
			   DefaultBpmProcessInstance inst= (DefaultBpmProcessInstance)processCmd.getTransitVars(BpmConstants.PROCESS_INST);
			   inst.setBizKey(id);
			}
		}
		else{
			demoUserDao.update(demoUser);
		}
		
		
	}
	
//	public void after(ActionCmd processCmd){
//		//获取流程实例ID
// 		String instId=processCmd.getInstId();
//		DefaultProcessInstCmd cmd=(DefaultProcessInstCmd)processCmd;
// 
//	}
	
	public void setBpmIdentity(ActionCmd processCmd){
		List<BpmIdentity> identity = new ArrayList<BpmIdentity>();
		BpmIdentity b = (BpmIdentity) processCmd.getVariables().get("oldExecutor");
		identity.add(b);
		processCmd.addTransitVars(BpmConstants.BPM_NEXT_NODE_USERS, identity);
	}
	
	/**
	 * 设置前置处理器或后置处理器， 用于在保存前改变表单的数据
	 * @param processCmd
	 */
	public void changeBoData(ActionCmd processCmd){
		//获取流程实例ID
 		String instId=processCmd.getInstId();
		//表单数据
		String data=processCmd.getBusData();
		
		JSONObject jsonObject = JSONObject.parseObject(data);
		jsonObject.getJSONObject("ywdxy").put("zdy", instId);
//		jsonObject.put("zdy", instId);
		processCmd.setBusData(jsonObject.toString());

	}

}
