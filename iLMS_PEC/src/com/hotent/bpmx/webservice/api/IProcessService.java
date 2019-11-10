package com.hotent.bpmx.webservice.api;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.api.model.process.task.BpmTaskOpinion;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.bpmx.webservice.model.BpmIdentityResult;
import com.hotent.bpmx.webservice.model.BpmTaskResult;
import com.hotent.bpmx.webservice.model.StartResult;
import com.hotent.restful.params.BpmImageParamObject;
import com.hotent.restful.params.DefOtherParam;
import com.hotent.restful.params.DoEndParamObject;
import com.hotent.restful.params.DoNextParamObject;
import com.hotent.restful.params.StartFlowParamObject;
import com.hotent.restful.params.CommonResult;
import com.hotent.restful.vo.BpmCheckOpinionVo;

/**
 * 流程控制接口
 * 
 * @author Administrator
 *
 */
public interface IProcessService
{

	/**
	 * 客户端启动流程
	 * @param startFlowParamObject
	 * @return
	 * @throws Exception
	 */
	StartResult start(StartFlowParamObject startFlowParamObject) throws Exception;

	/**
	 * 客户端提交数据,执行流程往下跳转
	 * @param doNextParamObject
	 * @return
	 * @throws Exception
	 */
	BasicResult doNext(DoNextParamObject doNextParamObject) throws Exception;


	/**
	 * 人工终止流程
	 * 参数格式：{account:"",taskId:"",endReason:"",messageType:""}
	 * account:用户账号 必填
	 * taskId:任务ID 必填
	 * endReason:终止原因 必填
	 * messageType:消息通知类型 非必填，默认邮件
	 * @param flowParamObject
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> doEndProcess(DoEndParamObject doEndParamObject) throws Exception;
	

	
	/**
	 * 按流程实例ID或任务实例ID取得某个流程的审批历史
	 * @param procInstId 实例id
	 * @param taskId 任务id
	 * @return
	 * @throws Exception
	 */
	List<BpmTaskOpinion> getHistoryOpinion(String procInstId,String taskId) throws Exception;
	
	/**
	 * 按Activiti实例Id取得对应流程的审批历史，act_ru_task表的PROC_INST_ID_值
	 * @param actTaskId
	 * @return
	 * @throws Exception
	 */
	List<BpmCheckOpinionVo> getProcessOpinionByActInstId(String actTaskId) throws Exception;

	/**
	 * 根据任务ID获取可驳回的节点
	 * @param taskId
	 * @param rejectType
	 * @return
	 * @throws Exception
	 */
	List<JSONObject> getEnableRejectNode(String taskId,String rejectType) throws Exception;

	/**
	 * 根据任务ID或流程实例ID获取BusinessKey（流程表单为URL表单的情况）
	 * @param procInstanceId 实例id
	 * @param taskId 任务id
	 * @return
	 * @throws NullPointerException
	 */
	CommonResult<String> getBusinessKey(String procInstanceId,String taskId) throws NullPointerException;

	
	/**
	 * 根据BussinessKey获取流程实例ID
	 * @param businessKey
	 * @return
	 * @throws NullPointerException
	 */
	CommonResult<String> getProcInstId(String businessKey) throws NullPointerException;
	
	/**
	 * 通过表单保存草稿
	 * @param flowParamObject
	 * @return
	 * @throws NullPointerException
	 */
	CommonResult<String> saveDraft(StartFlowParamObject startFlowParamObject) throws Exception;
	 
	/**
	 * 设置流程的其他参数
	 * @param defOtherParam
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setDefOtherParam(DefOtherParam defOtherParam) throws Exception;
	
	/**
	 * 根据任务id获取预先设置的审批用语列表
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	List<String> getApprovalItems(String taskId) throws Exception;
	
	/**
	 * 根据任务id获取其处理人
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	List<BpmIdentityResult> getNodeUsers(String taskId) throws Exception;
	
	/**
	 * 根据流程taskId获取对应的流程运行对象
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	BpmProcessInstance getProcessRunByTaskId(String taskId) throws Exception;
	
	/**
	 * 根据instId和nodeId获取节点的状态
	 * @param runId
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	String getStatusByRunidNodeId(String instId,String nodeId) throws Exception;
	
	/**
	 * 根据任务ID获取流程任务实例
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	BpmTaskResult getTaskByTaskId(String taskId) throws Exception;
	
	/**
	 * 根据taskid获取taskName
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	String getTaskNameByTaskId(String taskId) throws Exception;
	
	/**
	 * 通过businessKey获取运行实例
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	DefaultBpmProcessInstance getInstancetByBusinessKey(String businessKey) throws Exception;
	/**
	 * 
	 * @param businessKey
	 * @param sysCode
	 * @return
	 * @throws Exception
	 */
	DefaultBpmProcessInstance getInstancetByBizKeySysCode(String businessKey,String sysCode) throws Exception;
	
	/**
	 * 根据实例id获取实例对象
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	BpmProcessInstance getInstanceByInstId(String instId) throws Exception;
	
	/**
	 * 根据xml获取实例列表
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	JSONObject getInstanceListByXml(String xml) throws Exception;
	
	/**
	 * 取得某个运行流程实例对应的任务列表
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	JSONObject getTasksByInstId(String instId) throws Exception;
	
	/**
	 * 根据任务id获取任务的后续节点
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	JSONArray getTaskOutNodes(String taskId) throws Exception;
	
	/**
	 * 根据任务id获取在线表单地址
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	String getDetailUrl(String taskId) throws Exception;
	
	/**
	 * 根据任务taskId,设置流程变量（全局）
	 * @param taskId
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setTaskVar(String taskId,Map<String,Object> variables) throws Exception;
	
	/**
	 * 设置任务节点本地变量
	 * @param taskId
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setTaskVarLocal(String taskId,Map<String,Object> variables) throws Exception;
	/**
	 * 通过流程实例id取起流程实例
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> forbiddenInstance(String instId) throws Exception;
	/**
	 * 通过流程实例id取消挂起流程实例
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> unForbiddenInstance(String instId) throws Exception;
	/**
	 * 根据父流程实例ID和节点定义ID查子流程实例
	 * @param parentInstId
	 * @param superNodeId
	 * @return
	 * @throws Exception
	 */
	List<BpmProcessInstance> getBpmProcessByParentIdAndSuperNodeId(String parentInstId,String superNodeId)throws Exception;
	/**
	 * 通过父流程实例ID和实例的状态获取实例列表
	 * @param parentInstId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	List<DefaultBpmProcessInstance> getInstancesByParentId(String parentInstId,String status) throws Exception;
	/**
	 * 通过父流程定义ID和实例的状态获取实例列表
	 * @param defId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	List<DefaultBpmProcessInstance> getInstancesByDefId(String defId,String status) throws Exception;
	/**
	 * 根据流程实例ID查询顶级的流程实例。
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	BpmProcessInstance getTopBpmProcessInstance(String instId) throws Exception;
	
	/**
	 * 根据流程定义id或流程实例id或任务id或BPMN实例ID获取流程图。
	 * @param bpmImageParamObject
	 * @return
	 * @throws Exception
	 */
	String getBpmImage(BpmImageParamObject bpmImageParamObject) throws Exception;
	
}
