package com.hotent.restful.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmVariableDef;
import com.hotent.bpmx.natapi.task.NatTaskService;
import com.hotent.bpmx.persistence.manager.BpmTaskManager;
import com.hotent.bpmx.persistence.model.BpmTaskTransRecord;
import com.hotent.bpmx.persistence.model.DefaultBpmTask;
import com.hotent.bpmx.persistence.model.DefaultBpmTaskTurn;
import com.hotent.bpmx.webservice.api.IFlowService;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.BasicResult;
import com.hotent.bpmx.webservice.model.BpmTaskResult;
import com.hotent.org.api.service.IUserService;
import com.hotent.restful.params.AssignParamObject;
import com.hotent.restful.params.CommunicateParamObject;
import com.hotent.restful.params.ModifyExecutorsParamObject;
import com.hotent.restful.params.RestPageList;
import com.hotent.restful.params.TaskOrInstanQueryFilter;
import com.hotent.restful.params.TaskTransParamObject;
import com.hotent.restful.params.CommonResult;

/**
 * 获取任务数据
 * @author liangqf
 *
 */
@RestController
@RequestMapping("/restful/task/")
@Api(tags="TaskRestfulController")
public class TaskRestfulController extends RestfulBaseController {
	
	@Resource
	IFlowService iFlowService;
	@Resource
	NatTaskService natTaskService;
	@Resource
	BpmTaskManager bpmTaskManager;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor;
	@Resource
	IUserService userService;
	@Resource
	IProcessService iProcessService;
	
	
	/**
	 * 获取用户的待办事宜
	 * @param taskQueryFilterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getTodoList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "获取用户的待办事宜", httpMethod = "POST", notes = "获取用户的待办事宜")
	public RestPageList<DefaultBpmTask> getTodoList(HttpServletResponse response,@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter) throws Exception{
		PageList<DefaultBpmTask> todoList = iFlowService.getTodoList(taskOrInstanQueryFilter);
		RestPageList<DefaultBpmTask> restPageList = new RestPageList<DefaultBpmTask>(todoList);
		return restPageList;
	}
	
	/**
	 * 获取用户转办代理事宜
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "getDelegate", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "获取用户转办代理事宜", httpMethod = "POST", notes = "获取用户转办代理事宜")
	public RestPageList<DefaultBpmTaskTurn> getDelegate(
			@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,
			@ApiParam(name = "type", value = "类型：agent(代理) turn(转办)",allowableValues="agent,turn",required=false) @RequestParam(required=false) String type) throws Exception {
		PageList<DefaultBpmTaskTurn> delegate = iFlowService.getDelegate(taskOrInstanQueryFilter,type);
		RestPageList<DefaultBpmTaskTurn> restPageList = new RestPageList<DefaultBpmTaskTurn>(delegate);
		return restPageList;
	}
	
	/**
	 * 我的流转任务
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "getMyTrans", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "我的流转任务", httpMethod = "POST", notes = "我的流转任务")
	public RestPageList<BpmTaskTransRecord> getMyTrans(
			@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,
			HttpServletResponse response) throws Exception {
		PageList<BpmTaskTransRecord> myTrans = iFlowService.getMyTrans(taskOrInstanQueryFilter);
		RestPageList<BpmTaskTransRecord> restPageList = new RestPageList<BpmTaskTransRecord>(myTrans);
		return restPageList;
	}
	
	/**
	 * 任务转办
	 * @param assignParamObject
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "taskAssign", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "任务转办", httpMethod = "POST", notes = "任务转办")
	public BasicResult taskAssign(@ApiParam(required = true, name = "assignParamObject", value = "任务转办参数") @RequestBody AssignParamObject assignParamObject,HttpServletResponse response) throws Exception{
		return iFlowService.taskAssign(assignParamObject);
	}
	
	/**
	 * 任务沟通
	 * @param assignParamObject
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "communicate", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "任务沟通", httpMethod = "POST", notes = "任务沟通")
	public BasicResult communicate(@ApiParam(required = true, name = "communicateParamObject", value = "任务沟通参数") @RequestBody CommunicateParamObject communicateParamObject) throws Exception{
		return iFlowService.communicate(communicateParamObject);
	}
	
	/**
	 * 加签
	 * @param signParamObject
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "taskSignUsers", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "加签", httpMethod = "POST", notes = "加签")
	public BasicResult taskSignUsers(@ApiParam(required = true, name = "signParamObject", value = "任务加签参数") @RequestBody AssignParamObject signParamObject,HttpServletResponse response) throws Exception{
		return iFlowService.taskSignUsers(signParamObject);
	}
	

	/**
	 * 获取流程变量
	 */
	@GetMapping(value="getTaskVar",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "根据任务taskId, 获取流程变量(全局和节点)", httpMethod = "GET", notes = "根据任务id, 获取流程变量(全局和节点)")
	public Map<String,Object> getTaskVar(@ApiParam(name="taskId",required=true) @RequestParam String taskId){
		return natTaskService.getVariables(taskId);
	}
	
	/**
	 * 获获取流程任务节点的变量
	 */
	@GetMapping(value="getTaskVarLocal",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "根据任务taskId,获取流程任务节点的变量", httpMethod = "GET", notes = "根据任务id,获取流程任务节点的变量")
	public Map<String,Object> getTaskVarLocal(@ApiParam(name="taskId",required=true) @RequestParam String taskId){
		return natTaskService.getVariablesLocal(taskId);
	}
	
	/**
	 * 根据流程定义ID或流程定义KEY获取流程变量
	 * 
	 * @param json  {defId:"流程定义ID",defKey:"流程定义key"}
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value="getWorkflowVar",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "根据流程定义ID或流程定义KEY获取流程变量", httpMethod = "GET", notes = "json: {defId:\"流程定义ID\",defKey:\"流程定义key\"}")
	public List<BpmVariableDef> getWorkflowVar(@ApiParam(name="json",required=true) @RequestParam String json) throws Exception{
		return iFlowService.getWorkflowVar(json);
	}
	
	
	/**
	 * 设置全局的流程变量
	 * @param taskId
	 * @param variables
	 */
	@PostMapping(value="setTaskVar",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "根据任务taskId,设置流程变量", httpMethod = "POST", 
	notes = "根据任务taskId,设置流程变量 <br/> variables: {\"gloableVarA\":\"A\",\"gloableVarB\":\"B\"}  ")
	public CommonResult<String> setTaskVar(@ApiParam(name="taskId",required=true)  @RequestParam String taskId,
			@ApiParam(name="variables",required=true)   @RequestBody Map<String,Object> variables) throws Exception{
		return iProcessService.setTaskVar(taskId, variables);
	}
	
	/**
	 * 设置任务节点本地变量
	 * @param taskId
	 * @param variables
	 */
	@PostMapping(value="setTaskVarLocal",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "根据任务taskId,设置任务节点本地变量", httpMethod = "POST", 
	notes = "根据任务taskId,设置任务节点本地变量 <br/>variables: {\"userTaskVarA\":\"A\",\"userTaskVarB\":\"B\"}  ")
	public CommonResult<String> setTaskVarLocal(@ApiParam(name="taskId",required=true)  @RequestParam String taskId,
			@ApiParam(name="variables",required=true)   @RequestBody Map<String,Object> variables) throws Exception{
		return iProcessService.setTaskVarLocal(taskId, variables);
	}
	
	
	/**
	 * 判断用户是否有添加会签权限
	 * @param json  {taskId:"123456",userId:"",account:""}
	 * @return
	 */
	@PostMapping(value="isAllowAddSign",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "判断用户是否有添加会签权限", httpMethod = "POST", 
	notes = "{\"taskId\":\"123456\",\"userId\":\"\",\"account\":\"\"}")
	public Boolean isAllowAddSign(@RequestBody@ApiParam(name="json",required=true)Map<String,Object> json) throws Exception {
		return iFlowService.isAllowAddSign(json);
	}
	
	/**
	 * 修改任务执行人
	 * @param modifyExecutorsParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "setTaskExecutors", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "修改任务执行人", httpMethod = "POST", notes = "修改任务执行人")
	public CommonResult<String> setTaskExecutors(
			@ApiParam(required = true, name = "modifyExecutorsParamObject", value = "修改执行人对象") @RequestBody ModifyExecutorsParamObject modifyExecutorsParamObject)
			throws Exception {
		return iFlowService.setTaskExecutors(modifyExecutorsParamObject);
	}
	
	/**
	 * 保存流转信息（增加流转）
	 * @param taskTransParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="taskToTrans",method=RequestMethod.POST,produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="保存流转信息（增加流转）",httpMethod="POST",notes="保存流转信息（增加流转）")
	public CommonResult<String> taskToTrans(@ApiParam(name="taskTransParamObject",value="流转参数对象",required=true) @RequestBody TaskTransParamObject taskTransParamObject) throws Exception {
		return iFlowService.taskToTrans(taskTransParamObject);
	}
	
	/**
	 * 通过任务id获取任务对象
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getTaskByTaskId",method=RequestMethod.GET,produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="通过任务id获取任务对象",httpMethod="GET",notes="通过任务id获取任务对象")
	public BpmTaskResult getTaskByTaskId(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception{
		return iProcessService.getTaskByTaskId(taskId);
	}
	
	/**
	 * 通过任务id获取任务名称
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getTaskNameByTaskId",method=RequestMethod.GET,produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="通过任务id获取任务名称",httpMethod="GET",notes="通过任务id获取任务名称")
	public String getTaskNameByTaskId(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception{
		return iProcessService.getTaskNameByTaskId(taskId);
	}
	
	/**
	 * 通过实例id获取任务列表
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getTasksByInstId",method=RequestMethod.GET,produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="通过实例id获取任务列表",httpMethod="GET",notes="通过实例id获取任务列表")
	public JSONObject getTasksByInstId(@ApiParam(name="instId",value="实例id",required=true) @RequestParam String instId) throws Exception{
		return iProcessService.getTasksByInstId(instId);
	}
	
	/**
	 * 通过任务id获取任务的后续节点
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getTaskOutNodes",method=RequestMethod.GET,produces = {
	"application/json; charset=utf-8" })
	@ApiOperation(value="通过任务id获取任务的后续节点",httpMethod="GET",notes="通过任务id获取任务的后续节点")
	public JSONArray getTaskOutNodes(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception{
		return iProcessService.getTaskOutNodes(taskId);
	}
	

	/**
	 * 获取任务表单  （url表单）
	 *  先 获取节点表单地址， 节点表单没有配置，获取全局表单地址， 都没有则返回 ""
	 * 
	 * @param taskId
	 * @param formType pc mobile
	 */
	@GetMapping(value="getUrlFormByTaskId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "获取任务的在线表单地址", httpMethod = "GET", 
	notes = "formType参数值pc/mobile<br/>获取任务的url表单地址 <br/>先 获取节点表单地址， 节点表单没有配置，获取全局表单地址， 都没有则返回 \"\"  ")
	public String getUrlFormByTaskId(@ApiParam(name="taskId",required=true)  @RequestParam String taskId,@ApiParam(name="formType",required=true,defaultValue="pc")  @RequestParam String formType){
		return iFlowService.getUrlFormByTaskId( taskId,formType );
	}
	
	/**
	 * 获取实例表单
	 * 若nodeId不为空则获取节点的实例表单地址，
	 * 否则获取全局的实例表单地址
	 * 都没有获取到则返回 \"\"
	 * @param proInstId
	 * @param nodeId
	 * @param formType pc/mobile
	 * @return
	 */
	@GetMapping(value="getInstUrlForm",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "获取实例在线表单", httpMethod = "GET", 
	notes = "获取实例表单<br/>nodeId不为空则获取节点的实例表单地址<br/>否则获取全局的实例表单地址<br/>都没有获取到则返回 \"\", formType:pc/mobile")
	public String getInstUrlForm(@ApiParam(name="proInstId",required=true) @RequestParam String proInstId, @ApiParam(name="nodeId",required=false) @RequestParam(required=false) String nodeId,@ApiParam(name="formType",required=true,defaultValue="pc")  @RequestParam  String formType ){
		return iFlowService.getInstUrlForm(proInstId,nodeId,formType);
	}
	
	
}
