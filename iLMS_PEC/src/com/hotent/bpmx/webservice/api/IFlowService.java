package com.hotent.bpmx.webservice.api;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmDefinition;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.restful.params.AssignParamObject;
import com.hotent.restful.params.BaseQueryFilter;
import com.hotent.restful.params.BpmAgentsettingParam;
import com.hotent.restful.params.CommunicateParamObject;
import com.hotent.restful.params.ModifyExecutorsParamObject;
import com.hotent.restful.params.RevokeParamObject;
import com.hotent.restful.params.TaskOrInstanQueryFilter;
import com.hotent.restful.params.TaskTransParamObject;
import com.hotent.restful.params.CommonResult;

/**
 * 流程的相关接口
 * 
 */
public interface IFlowService {
	
	/**
	 *  获取用户待办事宜
	 * @param taskQueryFilter
	 * @return
	 */
	PageList<DefaultBpmTask> getTodoList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	/**
	 * 获取用户的已办事宜
	 * @param taskOrInstanQueryFilter
	 * @param status 流程状态
	 * @return
	 * @throws Exception
	 */
	PageList<Map<String,Object>> getDoneList(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String status) throws Exception;
	
	/**
	 * 获取用户的办结事宜。
	 * 
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmProcessInstance> getMyCompletedList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	/**
	 * 我的请求
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmProcessInstance> getMyRequestList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	/**
	 *  获取用户可发起的流程
	 * @param taskOrInstanQueryFilter
	 * @param typeId 流程分类id
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmDefinition> getMyFlowList(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String typeId) throws Exception;
	

	/**
	 * 获取用户的草稿列表
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmProcessInstance> getMyDraftList(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	
 
	/**
	 * 根据流程定义ID或流程定义KEY获取流程变量
	 * 
	 * @param json {defId:"流程定义ID",defKey:"流程定义key"}
	 * @return
	 */
	List<BpmVariableDef> getWorkflowVar(String json) throws Exception;
	
	/**
	 * 获取用户的抄送转发事宜
	 * @param taskOrInstanQueryFilter
	 * @param type 类型：copyto(抄送) trans(转发)
	 * @return
	 * @throws Exception
	 */
	PageList<CopyTo> getReceiverCopyTo(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String type) throws Exception ;
	
	/**
	 * 获取用户转办代理事宜
	 * @param taskOrInstanQueryFilter
	 * @param type 类型：agent(代理) turn(转办)
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmTaskTurn> getDelegate(TaskOrInstanQueryFilter taskOrInstanQueryFilter,String type) throws Exception;
	
	/**
	 * 我的流转任务
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	PageList<BpmTaskTransRecord> getMyTrans(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	/**
	 * 根据id删除草稿
	 * @param id
	 * @return
	 */
	CommonResult<String> removeDraftById(String runId) throws Exception;
	/**
	 * 根据流程定义ID获取流程定义实体
	 * @param defId
	 * @return
	 */
	DefaultBpmDefinition getBpmDefinitionByDefId(String defId) throws Exception;
	
	/**
	 * 任务转办
	 * @param assignParamObject
	 * @return
	 * @throws Exception
	 */
	BasicResult taskAssign(AssignParamObject assignParamObject) throws Exception;
	
	/**
	 * 任务沟通
	 * @param communicateParamObject
	 * @return
	 * @throws Exception
	 */
	BasicResult communicate(CommunicateParamObject communicateParamObject) throws Exception;
	
	/**
	 * 流程任务加签，增加会签人员
	 * @param assignParamObject
	 * @return
	 * @throws Exception
	 */
	BasicResult taskSignUsers(AssignParamObject assignParamObject) throws Exception;
	
	/**
	 * 根据实例id撤回流程（撤销）
	 * @param instanceId
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> revokeInstance(RevokeParamObject revokeParamObject) throws Exception;
	
	/**
	 * 根据流程定义key获取流程的所有节点信息
	 * @param defKey
	 * @return
	 * @throws Exception
	 */
	JSONArray getNodesByDefKey(String defKey) throws Exception ;
	
	/**
	 * 根据任务id获取下一环节处理人
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	Map<String, List<BpmIdentity>> getNextTaskUsers(String taskId) throws Exception;
	
	/**
	 * 修改任务执行人
	 * @param modifyExecutorsParamObject
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setTaskExecutors(ModifyExecutorsParamObject modifyExecutorsParamObject) throws Exception;
	
	/**
	 * 设置代理
	 * @param conditions
	 * @param agentDefList
	 * @param agentSetting
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> setAgent(BpmAgentsettingParam agentSetting) throws Exception;
	
	/**
	 * 判断用户是否有添加会签权限
	 * @param json
	 * @return
	 * @throws Exception
	 */
	Boolean isAllowAddSign(Map<String,Object> json) throws Exception;
	
	/**
	 * 保存流转信息（增加流转）
	 * @param taskTransParamObject
	 * @return
	 * @throws Exception
	 */
	CommonResult<String> taskToTrans(TaskTransParamObject taskTransParamObject) throws Exception;
	
	/**
	 * 获取处理任务的 在线表单地址
	 * @param taskId
	 * @param formType
	 * @return
	 */
	String getUrlFormByTaskId(String taskId, String formType);
	
	/**
	 * 获取实例表单  
	 *  如果nodeId 不为空，先获取节点配置的实例表单
	 *  否则获取全局的实例表单地址
	 * @param proInstId
	 * @param nodeId
	 * @param formType  pc/mobile
	 * @return
	 */
	String getInstUrlForm(String proInstId, String nodeId, String formType);
	
	/**
	 * 我的请求（包括人工终止和结束状态的实例）
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmProcessInstance> getMyRequestListAll(TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception;
	
	/**
	 * @param baseQueryFilter
	 * 查询流程定义列表
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmDefinition> getBpmDefList(BaseQueryFilter baseQueryFilter) throws Exception;
	
	/**
	 * @param baseQueryFilter
	 * 查询流程实例列表
	 * @return
	 * @throws Exception
	 */
	PageList<DefaultBpmProcessInstance> getInstanceList(BaseQueryFilter baseQueryFilter) throws Exception;
	
}
