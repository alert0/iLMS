package com.hotent.bpmx.core.engine.task.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.JsonElement;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.bpmx.api.cmd.ActionCmd;
import com.hotent.bpmx.api.constant.BpmConstants;
import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.context.ContextThreadUtil;
import com.hotent.bpmx.api.helper.identity.BpmIdentityExtractService;
import com.hotent.bpmx.api.model.delegate.BpmDelegateExecution;
import com.hotent.bpmx.api.model.delegate.BpmDelegateTask;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.Restful;
import com.hotent.bpmx.api.model.process.task.BpmTask;
import com.hotent.bpmx.api.plugin.core.def.BpmExecutionPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmTaskPluginDef;
import com.hotent.bpmx.api.plugin.core.session.BpmExecutionPluginSession;
import com.hotent.bpmx.api.plugin.core.session.BpmTaskPluginSession;
import com.hotent.bpmx.api.service.RestfulService;
import com.hotent.bpmx.core.engine.identity.DefaultBpmIdentityService;
import com.hotent.bpmx.persistence.manager.BpmDefinitionManager;
import com.hotent.bpmx.persistence.manager.BpmProcessInstanceManager;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.webservice.model.BpmIdentityResult;
import com.hotent.org.api.model.IUser;
import com.hotent.org.api.service.IUserService;
import com.hotent.sys.api.call.ICallLogService;
import com.hotent.sys.api.model.SysCallLog;
import com.hotent.sys.util.ContextUtil;

@Service
public class DefaultRestfulService implements RestfulService{
	private static Logger log = LoggerFactory.getLogger(DefaultRestfulService.class);
	
	@Resource
	BpmProcessInstanceManager bpmProcessInstanceManager;
	@Resource
	BpmDefinitionManager bpmDefinitionManager;
	@Resource
	IUserService userServiceImpl;
	@Resource
	DefaultBpmIdentityService bpmIdentityService;
	@Resource
	ICallLogService iCallLogService;
	@Resource
	BpmIdentityExtractService extractService;
	
	@Override
	public Void taskPluginExecute(BpmTaskPluginSession pluginSession,
			BpmTaskPluginDef pluginDef,List<Restful> restfuls) {
		try{
			EventType eventType = pluginSession.getEventType();
			BpmDelegateTask bpmDelegateTask = pluginSession.getBpmDelegateTask();
			for (Restful restful : restfuls) {
				String callTime = restful.getCallTime();
				if(!StringUtils.isEmpty(callTime)&&callTime.contains(eventType.getKey())){
					RestfulParam param = null;
					param = genartor(bpmDelegateTask,eventType);
					param.setTimestamp(System.currentTimeMillis());
					param.setEventType(eventType.getKey());
					String url = restful.getUrl();
					//TODO 暂时存放在reason中
					if(BeanUtils.isNotEmpty(url)){
						SysCallLog callLog = getCallLog(param,restful);
						JsonElement jsonTree = GsonUtil.toJsonTree(param);
						if(restful.getInvokeMode()==1){
							postAsync(url, jsonTree.toString(),restful.getHeader(),callLog);
						}else{
							Boolean isSuccess = true;
							try {
								String post = post(url, jsonTree.toString(),restful.getHeader(),callLog);
								callLog.setResponse(post);
							} catch (Exception e) {
								isSuccess = false;
								callLog.setResponse(ExceptionUtils.getRootCauseMessage(e));
							}
							callLog.setIsSuccess(isSuccess);
							iCallLogService.create(callLog);
						}
					}
				}
			}
		}
		catch(Exception ex){
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
			log.error("[事件中调用Restful接口异常]:" + rootCauseMessage);
		}
		return null;
	}

	@Override
	public Void executionPluginExecute(BpmExecutionPluginSession pluginSession,
			BpmExecutionPluginDef pluginDef,List<Restful> restfuls) {
		try{
			EventType eventType = pluginSession.getEventType();
			BpmDelegateExecution belegateExecution = pluginSession.getBpmDelegateExecution();
			for (Restful restful : restfuls) {
				String callTime = restful.getCallTime();
				String callNode = restful.getCallNodes();
				if(!StringUtils.isEmpty(callTime)&&callTime.contains(eventType.getKey())
						&&isGlobalNode(belegateExecution,callNode)){
					RestfulParam param = null;
					param = genartor(belegateExecution,eventType);
					param.setTimestamp(System.currentTimeMillis());
					param.setEventType(eventType.getKey());
					String url = restful.getUrl();
					//TODO 暂时存放在reason中
					if(BeanUtils.isNotEmpty(url)){
						SysCallLog callLog = getCallLog(param,restful);
						JsonElement jsonTree = GsonUtil.toJsonTree(param);
						if(restful.getInvokeMode()==1){
							postAsync(url, jsonTree.toString(),restful.getHeader(),callLog);
						}else{
							Boolean isSuccess = true;
							try {
								String post = post(url, jsonTree.toString(),restful.getHeader(),callLog);
								callLog.setResponse(post);
								iCallLogService.create(callLog);
							} catch (Exception e) {
								callLog.setResponse(ExceptionUtils.getRootCauseMessage(e));
								isSuccess = false;
							}
							callLog.setIsSuccess(isSuccess);
							iCallLogService.create(callLog);
						}
					}
				}
			}
		}
		catch(Exception ex){
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
			log.error("[事件中调用Restful接口异常]:" + rootCauseMessage);
		}
		return null;
	}

	@Override
	public Void outTaskPluginExecute(BpmTask task,List<Restful> restfuls,EventType eventType) {
		try{
			for (Restful restful : restfuls) {
				String callTime = restful.getCallTime();
				if(!StringUtils.isEmpty(callTime)&&callTime.contains(eventType.getKey())){
					RestfulParam param = null;
					param = genartor(task,eventType);
					param.setTimestamp(System.currentTimeMillis());
					param.setEventType(eventType.getKey());
					String url = restful.getUrl();
					//TODO 暂时存放在reason中
					if(BeanUtils.isNotEmpty(url)){
						SysCallLog callLog = getCallLog(param,restful);
						JsonElement jsonTree = GsonUtil.toJsonTree(param);
						if(restful.getInvokeMode()==1){
							postAsync(url, jsonTree.toString(),restful.getHeader(),callLog);
						}else{
							Boolean isSuccess = true;
							try {
								String post = post(url, jsonTree.toString(),restful.getHeader(),callLog);
								callLog.setResponse(post);
							} catch (Exception e) {
								isSuccess = false;
								callLog.setResponse(ExceptionUtils.getRootCauseMessage(e));
							}
							callLog.setIsSuccess(isSuccess);
							iCallLogService.create(callLog);
						}
					}
				}
			}
		}
		catch(Exception ex){
			String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
			log.error("[事件中调用Restful接口异常]:" + rootCauseMessage);
		}
		return null;
	}
	

	private SysCallLog getCallLog(RestfulParam param,Restful restful){
		SysCallLog callLog = new SysCallLog();
		callLog.setId(UniqueIdUtil.getSuid());
		callLog.setEventType(param.getEventType());
		callLog.setProcDefKey(param.getFlowKey());
		callLog.setTaskId(param.getTaskId());
		callLog.setProcInstId(param.getInstId());
		callLog.setTaskKey(param.getNodeId());
		callLog.setTaskName(param.getNodeName());
		callLog.setUrl(restful.getUrl());
		callLog.setDesc(restful.getDesc());
		callLog.setInvokeMode(restful.getInvokeMode());
		callLog.setCallTime(new Date());
		callLog.setUserId(ContextUtil.getCurrentUserId());
		return callLog;
	}
	
	/**
	 * 异步请求
	 * @param url
	 * @param params
	 * @return
	 */
	private String postAsync(String url, String params,String headerStr,SysCallLog callLog){
		String postMsg = "";
		try {
			class restfulPostAsync {
			    private String url;
			    private String params;
			    private String header;
			    private String result;
			    private SysCallLog callLog;

			    public restfulPostAsync(String url,String params,String header,SysCallLog callLog) {
			        super();
			        this.url = url;
			        this.params = params;
			        this.header = header;
			        this.callLog = callLog;
			    }
			    public String getResult() {
					return result;
				}

				public void setResult(String result) {
					this.result = result;
				}
				public void toPost() {
			        new Thread(){
			            public void run() {
			            	callLog.setCallTime(new Date());
			            	Boolean isSuccess = true;
			            	try {
								String response = post(url,params,header,callLog);
								callLog.setResponse(response);
							} catch (Exception e) {
								isSuccess = false;
								callLog.setResponse(ExceptionUtils.getRootCauseMessage(e));
							}
			            	callLog.setIsSuccess(isSuccess);
			            	iCallLogService.create(callLog);
			            }
			        }.start();
			    }
			}
			restfulPostAsync restfulPost = new restfulPostAsync(url,params,headerStr,callLog);
			restfulPost.toPost();
			postMsg = restfulPost.getResult();
		}catch(Exception ex){
			callLog.setCallTime(new Date());
			callLog.setIsSuccess(false);
			callLog.setResponse(ExceptionUtils.getRootCauseMessage(ex));
			iCallLogService.create(callLog);
		}
		return postMsg;
	}
	
	private Request setHeaders(Request request,String headerStr){
		if(StringUtil.isNotEmpty(headerStr)){
			try {
				headerStr = com.hotent.base.core.encrypt.Base64.getFromBase64(headerStr);
				JSONObject obj = JSONObject.fromObject(headerStr);
				 Iterator it = obj.keys();
				 while(it.hasNext()){
					 String key = (String)it.next();
					 request.setHeader(key, obj.getString(key));
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return request;
	}
	
	/**
	 * 同步请求
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String post(String url, String params,String headerStr,SysCallLog callLog) throws ClientProtocolException, IOException{
		Request request = Request.Post(url);
		request = setHeaders(request,headerStr);
		String response = request.bodyString(params, ContentType.APPLICATION_JSON)
			   .execute().returnContent().toString();
		return response;
	}
	
	/**
	 * 判断全局事件中的节点事件是否执行
	 * @param nodeId  当前节点
	 * @param eventType 当前事件类型
	 * @param callNodes restful事件触发节点
	 * @return
	 */
	private boolean isGlobalNode(BpmDelegateExecution execution,String callNodes){
		String nodeId = execution.getVariable("restful_nodeId")!=null?(String) execution.getVariable("restful_nodeId"):"";
		if(StringUtil.isNotEmpty(nodeId)){
			if(StringUtil.isEmpty(callNodes)||callNodes.contains(nodeId)){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	
	private RestfulParam genartor(BpmDelegateExecution execution,EventType eventType){
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		RestfulParam param = new RestfulParam();
		String instId=(String)execution.getVariable(BpmConstants.PROCESS_INST_ID);
		param.setActionName(taskCmd.getActionName());
		param.setNodeId(execution.getNodeId());
		param.setNodeName(execution.getNodeName());
		if(EventType.TASK_COMPLETE_EVENT.equals(eventType)||EventType.TASK_CREATE_EVENT.equals(eventType)){
			BpmDelegateTask delegateTask = (BpmDelegateTask) taskCmd.getVariables().get("restful_task");
			if(BeanUtils.isNotEmpty(delegateTask)){
				param.setNodeId(delegateTask.getTaskDefinitionKey());
				param.setNodeName(delegateTask.getName());
				param.setTaskId(delegateTask.getId());
				param.setCandidate(getCandidates(delegateTask,eventType));
				if(EventType.TASK_COMPLETE_EVENT.equals(eventType)){
					param.setAssignee(getBpmIdentityResult(ContextUtil.getCurrentUserId()));
				}
			}
		}
		DefaultBpmProcessInstance defaultBpmProcessInstance = bpmProcessInstanceManager.get(instId);
		if(BeanUtils.isEmpty(defaultBpmProcessInstance)){
			defaultBpmProcessInstance = (DefaultBpmProcessInstance) taskCmd.getTransitVars(BpmConstants.PROCESS_INST);
			if(BeanUtils.isEmpty(defaultBpmProcessInstance)){
				return param;
			}
		}
		param.setInstId(instId);
		param.setProcDefId(defaultBpmProcessInstance.getProcDefId());
		param.setFlowKey(defaultBpmProcessInstance.getProcDefKey());
		param.setBusinesskey(defaultBpmProcessInstance.getBizKey());
		param.setSysCode(defaultBpmProcessInstance.getSysCode());
		param.setCreateTime(defaultBpmProcessInstance.getCreateTime());
		param.setProcDefName(defaultBpmProcessInstance.getProcDefName());
		param.setCreator(getBpmIdentityResult(defaultBpmProcessInstance.getCreateBy()));
		return param;
	}
	
	private RestfulParam genartor(BpmTask task,EventType eventType){
		RestfulParam param = new RestfulParam();
		String instId = task.getProcInstId();
		param.setNodeId(task.getNodeId());
		param.setNodeName(task.getName());
		param.setTaskId(task.getId());
		param.setActionName(task.getStatus());
		if(EventType.TASK_COMPLETE_EVENT.equals(eventType)){
			param.setAssignee(getBpmIdentityResult(task.getAssigneeId()));
		}
		List<BpmIdentityResult> candidates = new ArrayList<BpmIdentityResult>();
		candidates.add(getBpmIdentityResult(task.getAssigneeId()));
		param.setCandidate(candidates);
		completeInstInfo(instId, param);
		return param;
	}
	
	private RestfulParam genartor(BpmDelegateTask delegateTask,EventType eventType){
		ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
		RestfulParam param = new RestfulParam();
		String instId = (String) delegateTask.getVariable(BpmConstants.PROCESS_INST_ID);
		param.setNodeId(delegateTask.getTaskDefinitionKey());
		param.setNodeName(delegateTask.getName());
		param.setTaskId(delegateTask.getId());
		param.setActionName(taskCmd.getActionName());
		if(EventType.TASK_COMPLETE_EVENT.equals(eventType)){
			param.setAssignee(getBpmIdentityResult(ContextUtil.getCurrentUserId()));
		}
		param.setCandidate(getCandidates(delegateTask,eventType));
		completeInstInfo(instId, param);
		return param;
	}
	
	private void completeInstInfo(String instId, RestfulParam param){
		DefaultBpmProcessInstance defaultBpmProcessInstance = bpmProcessInstanceManager.get(instId);
		if(BeanUtils.isEmpty(defaultBpmProcessInstance)){
			ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
			defaultBpmProcessInstance = (DefaultBpmProcessInstance) taskCmd.getTransitVars(BpmConstants.PROCESS_INST);
			if(BeanUtils.isEmpty(defaultBpmProcessInstance)){
				return;
			}
		}
		param.setInstId(instId);
		param.setProcDefId(defaultBpmProcessInstance.getProcDefId());
		param.setFlowKey(defaultBpmProcessInstance.getProcDefKey());
		param.setBusinesskey(defaultBpmProcessInstance.getBizKey());
		param.setSysCode(defaultBpmProcessInstance.getSysCode());
		param.setCreateTime(defaultBpmProcessInstance.getCreateTime());
		param.setProcDefName(defaultBpmProcessInstance.getProcDefName());
		param.setCreator(getBpmIdentityResult(defaultBpmProcessInstance.getCreateBy()));
	}
	
	
	
	private BpmIdentityResult getBpmIdentityResult(String userId){
		BpmIdentityResult bpmIdentity = null;
		try {
			if(StringUtil.isNotEmpty(userId)){
				IUser user = userServiceImpl.getUserById(userId);
				if(BeanUtils.isNotEmpty(user)){
					bpmIdentity = new BpmIdentityResult(user);
				}
			}
		} catch (Exception e) {}
		return bpmIdentity;
	}
	
	private List<BpmIdentityResult> getCandidates(BpmDelegateTask delegateTask,EventType eventType){
		List<BpmIdentityResult> list = new ArrayList<BpmIdentityResult>();
		try {
			if(EventType.TASK_COMPLETE_EVENT.equals(eventType)){
				List<BpmIdentity> bpmIdentitys = bpmIdentityService.queryByTask(delegateTask.getId());
				if(BeanUtils.isNotEmpty(bpmIdentitys)){
					for (BpmIdentity bpmIdentity : bpmIdentitys) {
						if(!BpmIdentity.TYPE_USER.equals(bpmIdentity.getType())){
							List<IUser> userList= userServiceImpl.getUserListByGroup(bpmIdentity.getGroupType(), bpmIdentity.getId());
							for (IUser iUser : userList) {
								list.add(new BpmIdentityResult(iUser));
							}
						}else{
							BpmIdentityResult identityResult = getBpmIdentityResult(bpmIdentity.getId());
							if(BeanUtils.isNotEmpty(identityResult)){
								list.add(identityResult);
							}
						}
					}
				}
			}else{
				String nodeId = delegateTask.getTaskDefinitionKey();
				ActionCmd taskCmd = ContextThreadUtil.getActionCmd();
				List<BpmIdentity> identityList = new ArrayList<BpmIdentity>();
				// 从流程变量中获取是否设置了人员，如果设置则从流程变量中获取。
				Map<String,List< BpmIdentity>> nodeUsers=(Map<String,List< BpmIdentity>>) taskCmd.getTransitVars(BpmConstants.BPM_NODE_USERS);
				// 正常跳转指定的执行人
				if(taskCmd.getTransitVars(BpmConstants.BPM_NEXT_NODE_USERS)!=null){
					identityList = (List<BpmIdentity>) taskCmd.getTransitVars(BpmConstants.BPM_NEXT_NODE_USERS);
				}
				// 已有指定执行人
				if(nodeUsers!=null && nodeUsers.containsKey(nodeId)){
					identityList=nodeUsers.get(nodeId);
				}
				// 先从任务的Excutors中获取，如果获取不到再从CMD中获取。
				if (BeanUtils.isEmpty(identityList))
				{
					identityList = delegateTask.getExecutors();
				}
				// 如果在上下文指定了人员，则取上下文的人员。 
				if (BeanUtils.isEmpty(identityList))
				{
					Map<String, List<BpmIdentity>> identityMap = taskCmd.getBpmIdentities();
					identityList = identityMap.get(nodeId);
				}
				//将bpmIdentity抽取为用户
				if(BeanUtils.isNotEmpty(identityList)){
					identityList = extractService.extractBpmIdentity(identityList);
				}
				for (BpmIdentity bpmIdentity : identityList) {
					list.add(getBpmIdentityResult(bpmIdentity.getId()));
				}
			}
			
		} catch (Exception e) {}
		return list;
	}
	
	class RestfulParam{
		private long timestamp;							/*时间戳*/
		private String procDefId;						/*流程定义ID*/
		private String flowKey;							/*流程定义KEY*/
		private String instId;							/*流程实例ID*/
		private String taskId;							/*任务ID*/
		private String nodeId;							/*节点ID*/
		private String nodeName;						/*节点名称*/
		private String eventType;						/*事件类型*/
		private String businesskey;						/*业务主键*/
		private String sysCode;						    /*业务系统编码*/
		private String procDefName;						/*流程名称*/
		private BpmIdentityResult creator;				/*实例发起人*/
		private BpmIdentityResult assignee;				/*任务执行人*/
		private List<BpmIdentityResult> candidate;		/*任务候选人*/
		private String actionName;                      /*节点处理类型*/
		private String nodeType;                        /*节点类型*/
		private Date createTime;
		private Date completeTime;
		
		public String getFlowKey() {
			return flowKey;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public void setFlowKey(String flowKey) {
			this.flowKey = flowKey;
		}
		public String getEventType() {
			return eventType;
		}
		public void setEventType(String eventType) {
			this.eventType = eventType;
		}
		public String getProcDefId() {
			return procDefId;
		}
		public void setProcDefId(String procDefId) {
			this.procDefId = procDefId;
		}
		public String getInstId() {
			return instId;
		}
		public void setInstId(String instId) {
			this.instId = instId;
		}
		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getNodeId() {
			return nodeId;
		}
		public void setNodeId(String nodeId) {
			this.nodeId = nodeId;
		}
		public String getNodeName() {
			return nodeName;
		}
		public void setNodeName(String nodeName) {
			this.nodeName = nodeName;
		}
		public String getBusinesskey() {
			return businesskey;
		}
		public void setBusinesskey(String businesskey) {
			this.businesskey = businesskey;
		}
		public String getSysCode() {
			return sysCode;
		}
		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}
		public String getProcDefName() {
			return procDefName;
		}
		public void setProcDefName(String procDefName) {
			this.procDefName = procDefName;
		}
		public BpmIdentityResult getCreator() {
			return creator;
		}
		public void setCreator(BpmIdentityResult creator) {
			this.creator = creator;
		}
		public BpmIdentityResult getAssignee() {
			return assignee;
		}
		public void setAssignee(BpmIdentityResult assignee) {
			this.assignee = assignee;
		}
		public List<BpmIdentityResult> getCandidate() {
			return candidate;
		}
		public void setCandidate(List<BpmIdentityResult> candidate) {
			this.candidate = candidate;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getCompleteTime() {
			return completeTime;
		}
		public void setCompleteTime(Date completeTime) {
			this.completeTime = completeTime;
		}
		public String getActionName() {
			return actionName;
		}
		public void setActionName(String actionName) {
			this.actionName = actionName;
		}
		public String getNodeType() {
			return nodeType;
		}
		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}
	}
}
