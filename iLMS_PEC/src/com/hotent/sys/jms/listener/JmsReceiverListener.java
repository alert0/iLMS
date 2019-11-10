package com.hotent.sys.jms.listener;

import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ExceptionUtil;
import com.hotent.base.core.util.FastJsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.constant.OpinionStatus;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.core.util.BpmUtil;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.manager.BpmTestCaseManager;
import com.hotent.bpmx.persistence.manager.impl.BpmCheckOpinionManagerImpl;
import com.hotent.bpmx.persistence.model.BpmTestCase;
import com.hotent.bpmx.persistence.model.DefaultBpmCheckOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.util.PublishAutoTestEventUtil;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.restful.params.DoEndParamObject;
import com.hotent.restful.params.DoNextParamObject;
import com.hotent.sys.api.jms.model.AutoTestModel;
import com.hotent.sys.jms.model.JmsReceiverEvent;
import com.hotent.sys.persistence.manager.SysObjLogManager;
import com.hotent.sys.persistence.model.SysObjLog;
import com.hotent.sys.util.SysObjTypeConstants;

/**
 * 处理出JmsVo对象之外的消息。
 * <pre> 
 * 构建组：x5-sys-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-5-10-上午7:41:29
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class JmsReceiverListener implements
		ApplicationListener<JmsReceiverEvent> {

	@Override
	public void onApplicationEvent(JmsReceiverEvent ev) {
		
		Object obj=ev.getSource();
		//处理消息
		
		// 流程仿真
		autoTest(obj);
	}
	
	// 流程仿真测试
	private void autoTest(Object obj){
		if(!(obj instanceof AutoTestModel)) return;
		
		AutoTestModel model = (AutoTestModel) obj;
		
		if(StringUtil.isEmpty(model.getProcInstId())) return;
		
		BpmProcessInstanceManager bean = AppUtil.getBean(BpmProcessInstanceManager.class);
		BpmTestCaseManager testCase = AppUtil.getBean(BpmTestCaseManager.class);
		BoDataService boDataService = AppUtil.getBean(BoDataService.class);
		DefaultBpmProcessInstance instance = bean.get(model.getProcInstId());
		if(BeanUtils.isEmpty(instance)) return;
		String flowKey = instance.getProcDefKey();
		String nodeId = model.getNodeId();
		instance = (DefaultBpmProcessInstance) bean.getTopBpmProcessInstance(instance);
		
		String sysCode = instance.getSysCode();
		if(StringUtil.isEmpty(sysCode) || !sysCode.startsWith(SysObjTypeConstants.BPMX_AUTO_TEST) ) return;
		
		BpmTestCase bpmTestCase = testCase.get(sysCode.replace(SysObjTypeConstants.BPMX_AUTO_TEST, ""));
		
		// 获取当前的审批动作 {nodeId:actionName}==>> {"userTask1":"agree"}
		String actionName = "agree";
		String destination = "";
		int count = 1;
		String actionType = bpmTestCase.getActionType();
		if(StringUtil.isNotEmpty(actionType) && JSONObject.parseObject(actionType).containsKey(flowKey) ){
			JSONArray jsonArray = JSONObject.parseObject(actionType).getJSONArray(flowKey);
			JSONObject parse = FastJsonUtil.arrayToObject(jsonArray, "nodeId");
			if(parse.containsKey(model.getNodeId())){
				parse = parse.getJSONObject(model.getNodeId());
				actionName = FastJsonUtil.getString(parse, "actionName",actionName);
				count =  FastJsonUtil.getInt(parse, "count",count);
			}
		}
		
		List<BoData> boDatas = boDataService.getDataByInst(instance);
	   try {
		   
			IProcessService processService = AppUtil.getBean(IProcessService.class);
		    if("endProcess".equals(actionName)){
		    	DoEndParamObject doEndParamObject = new DoEndParamObject();
		    	doEndParamObject.setAccount(model.getRandomAccount());
		    	doEndParamObject.setEndReason("流程仿真测试--人工结束流程");
		    	doEndParamObject.setTaskId(model.getTaskId());
		    	processService.doEndProcess(doEndParamObject);
		    	return;
			}
		    
		    // bpmDebugger
		    String debugger = bpmTestCase.getBpmDebugger();
		    if(StringUtil.isNotEmpty(debugger) && !model.getSkipDebugger() ){
		    	JSONObject debuggerJo = JSONObject.parseObject(debugger);
		    	if(debuggerJo.containsKey(flowKey)){
		    		JSONArray jsonArray = debuggerJo.getJSONArray(flowKey);
		    		if(jsonArray.contains(nodeId)){
		    			throw new RuntimeException("设置了断点, 流程审批到该节点停止了测试用例往下执行,需要继续运行的请在流程实例中点击继续运行。 ");
		    		}
		    	}
		    }
		    
		    if(OpinionStatus.BACK_TO_START.getKey().equals(actionName) || OpinionStatus.REJECT.getKey().equals(actionName)  ){
		    	
		    	if( OpinionStatus.REJECT.getKey().equals(actionName)){
		    		destination = BpmUtil.getRejectPreDestination(model.getTaskId());
		    		if(StringUtil.isEmpty(destination)){
		    			throw new RuntimeException("在审批节点[ "+model.getNodeName()+" ] 不支持驳回到上一步的设置，请修改测试用例。 ");
		    		}
		    	}
		    	
		    	// 判断驳回次数
		    	DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		    	queryFilter.addFilter("proc_inst_id_", model.getProcInstId(), QueryOP.EQUAL);
		    	queryFilter.addFilter("task_key_", model.getNodeId(), QueryOP.EQUAL);
		    	queryFilter.addFilter("status_", OpinionStatus.BACK_TO_START.getKey().equals(actionName)?"backToStart":"reject", QueryOP.EQUAL);
		    	BpmCheckOpinionManagerImpl bpmCheckOpinionManagerImpl = (BpmCheckOpinionManagerImpl) AppUtil.getBean("bpmCheckOpinionManager");
		    	
		    	List<DefaultBpmCheckOpinion> query = bpmCheckOpinionManagerImpl.query(queryFilter);
		    	if(BeanUtils.isNotEmpty(query) && query.size()>=count){
		    		actionName = "agree";
		    	}
		    	
		    }
		   
		    //自动下一任务
			// BO数据前置处理
			JSONObject data =BoDataUtil.hanlerData(instance,model.getNodeId(), boDatas);
			
			DoNextParamObject doNextParamObject = new DoNextParamObject();
			doNextParamObject.setAccount(model.getRandomAccount());
			doNextParamObject.setActionName(actionName);
			doNextParamObject.setData(Base64.getBase64(data.toJSONString()));
			doNextParamObject.setTaskId(model.getTaskId());
			doNextParamObject.setDestination(destination);
			doNextParamObject.setOpinion("流程仿真测试");
			BasicResult doNext = processService.doNext(doNextParamObject);
			
			if(doNext.getResult()){
				PublishAutoTestEventUtil.publishAutoTestEvent(instance.getId());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			SysObjLogManager service = AppUtil.getBean(SysObjLogManager.class);
			SysObjLog sysObjLog = service.get(instance.getId());
			if(BeanUtils.isEmpty(sysObjLog)){
				sysObjLog = new SysObjLog();
				sysObjLog.setId(instance.getId());
				sysObjLog.setContent("在审批任务【"+model.getNodeName()+"】出现异常" + ExceptionUtil.getExceptionMessage(e));
				sysObjLog.setName("流程[ "+instance.getSubject()+" ]未正常结束： ");
				sysObjLog.setObjType(SysObjTypeConstants.BPMX_AUTO_TEST);
				sysObjLog.setParam("");
				service.create(sysObjLog);
			}else{
				sysObjLog.setContent("在审批任务【"+model.getNodeName()+"】出现异常" + ExceptionUtil.getExceptionMessage(e));
				service.update(sysObjLog);
			}
			
			
		}
		
		
		
	}
	
}
