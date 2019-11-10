package com.hotent.restful.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.api.model.process.inst.BpmProcessInstance;
import com.hotent.bpmx.persistence.model.CopyTo;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessInstance;
import com.hotent.bpmx.webservice.api.IFlowService;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.restful.params.BaseQueryFilter;
import com.hotent.restful.params.BpmImageParamObject;
import com.hotent.restful.params.RestPageList;
import com.hotent.restful.params.RevokeParamObject;
import com.hotent.restful.params.TaskOrInstanQueryFilter;
import com.hotent.restful.params.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

/**
 * 获取流程实例数据
 * @author liangqf
 *
 */
@RestController
@RequestMapping("/restful/instance/")
@Api(tags="InstanceRestfulController")
public class InstanceRestfulController extends RestfulBaseController {
	
	@Resource
	IFlowService iFlowService;
	@Resource
	IProcessService iProcessService;
	
	/**
	 * 获取用户的已办事宜
	 * @param taskOrInstanQueryFilter
	 * @param status 流程状态
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getDoneList", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "获取用户的已办事宜", httpMethod = "POST", notes = "获取用户的已办事宜，参数status表示流程状态，不填表示查询所有")
	public RestPageList<Map<String, Object>> getDoneList(
			@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,
			@ApiParam(name="status",value="流程状态",allowableValues="running,end,manualend,cancel,back,revoke,revokeToStart",required=false) @RequestParam(required=false) String status,
			HttpServletResponse response) throws Exception {
		PageList<Map<String,Object>> doneList = iFlowService.getDoneList(taskOrInstanQueryFilter,status);
		RestPageList<Map<String,Object>> restPageList = new RestPageList<Map<String,Object>>(doneList);
		return restPageList;
	}
	
	/**
	 * 获取用户的办结事宜
	 * @param taskOrInstanQueryFilter
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getMyCompletedList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "获取用户的办结事宜", httpMethod = "POST", notes = "获取用户的办结事宜")
	public RestPageList<DefaultBpmProcessInstance> getMyCompletedList(@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,HttpServletResponse response) throws Exception{
		PageList<DefaultBpmProcessInstance> myCompletedList = iFlowService.getMyCompletedList(taskOrInstanQueryFilter);
		RestPageList<DefaultBpmProcessInstance> restPageList = new RestPageList<DefaultBpmProcessInstance>(myCompletedList);
		return restPageList;
	}
	
	/**
	 * 我的请求
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "getMyRequestList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "我的请求", httpMethod = "POST", notes = "我的请求")
	public RestPageList<DefaultBpmProcessInstance> getMyRequestList(@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,HttpServletResponse response) throws Exception{
		PageList<DefaultBpmProcessInstance> myRequestList = iFlowService.getMyRequestList(taskOrInstanQueryFilter);
		RestPageList<DefaultBpmProcessInstance> restPageList = new RestPageList<DefaultBpmProcessInstance>(myRequestList);
		return restPageList;
	}
	
	/**
	 * 获取我的草稿列表
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "getMyDraftList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "获取我的草稿列表", httpMethod = "POST", notes = "获取我的草稿列表")
	public RestPageList<DefaultBpmProcessInstance> getMyDraftList(@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,HttpServletResponse response) throws Exception{
		PageList<DefaultBpmProcessInstance> myDraftList = iFlowService.getMyDraftList(taskOrInstanQueryFilter);
		RestPageList<DefaultBpmProcessInstance> restPageList = new RestPageList<DefaultBpmProcessInstance>(myDraftList);
		return restPageList;
	}
	
	/**
	 * 获取用户的抄送转发数据
	 * @param taskOrInstanQueryFilter
	 * @param type
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getReceiverCopyTo", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "获取用户的抄送转发数据", httpMethod = "POST", notes = "获取用户的抄送转发数据")
	public RestPageList<CopyTo> getReceiverCopyTo(@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,
			@ApiParam(name="type",value="类型：copyto(抄送) trans(转发)",allowableValues="copyto,trans",required=false) @RequestParam(required=false) String type,
			HttpServletResponse response) 
			throws Exception{
		PageList<CopyTo> receiverCopyTo = iFlowService.getReceiverCopyTo(taskOrInstanQueryFilter,type);
		RestPageList<CopyTo> restPageList = new RestPageList<CopyTo>(receiverCopyTo);
		return restPageList;
	}
	
	/**
	 * 根据id删除草稿
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "removeDraftById", method = RequestMethod.DELETE, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "根据id删除草稿", httpMethod = "DELETE", notes = "根据id删除草稿")
	public CommonResult<String> removeDraftById(@ApiParam(required = true, name = "id", value = "草稿id") @RequestParam String id) throws Exception{
		return iFlowService.removeDraftById(id);
	}
	
	/**
	 * 根据实例id撤回流程（撤销）
	 * @param revokeParamObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "revokeInstance", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据实例id撤回流程（撤销）", httpMethod = "POST", notes = "根据实例id撤回流程（撤销）")
	public CommonResult<String> revokeInstance(
			@ApiParam(required = true, name = "revokeParamObject", value = "流程撤销对象") @RequestBody RevokeParamObject revokeParamObject)
			throws Exception {
		return iFlowService.revokeInstance(revokeParamObject);
	}
	
	/**
	 * 根据taskId获取对应的流程运行对象
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getProcessRunByTaskId",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="根据taskId获取对应的流程运行对象",notes="根据taskId获取对应的流程运行对象",httpMethod="GET")
	public BpmProcessInstance getProcessRunByTaskId(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception{
		return iProcessService.getProcessRunByTaskId(taskId);
	}
	
	/**
	 * 通过businessKey获取运行实例
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInstancetByBusinessKey",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过businessKey获取运行实例",notes="通过businessKey获取运行实例",httpMethod="GET")
	public BpmProcessInstance getInstancetByBusinessKey(@ApiParam(name="businessKey",value="业务主键",required=true) @RequestParam String businessKey) throws Exception{
		return iProcessService.getInstancetByBusinessKey(businessKey);
	}
	
	/**
	 * 通过businessKey和sysCode获取运行实例
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInstancetByBizKeySysCode",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过businessKey获取运行实例",notes="通过businessKey获取运行实例",httpMethod="GET")
	public BpmProcessInstance getInstancetByBizKeySysCode(@ApiParam(name="businessKey",value="业务主键",required=true) @RequestParam String businessKey,@ApiParam(name="sysCode",value="业务系统编码",required=true) @RequestParam String sysCode) throws Exception{
		return iProcessService.getInstancetByBizKeySysCode(businessKey,sysCode);
	}
	
	/**
	 * 根据实例id获取实例对象
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInstanceByInstId",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="根据实例id获取实例对象",notes="根据实例id获取实例对象",httpMethod="GET")
	public BpmProcessInstance getInstanceByInstId(@ApiParam(name="instId",value="实例id",required=true) @RequestParam String instId) throws Exception{
		return iProcessService.getInstanceByInstId(instId);
	}
	
	/**
	 * 根据xml获取实例列表
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getInstanceListByXml", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据xml获取实例列表", notes = "根据xml获取实例列表，xml的根元素包含属性account（用户帐号）、subject（标题）、<br/>status（流程状态，draft草稿，pending挂起，running运行中，manualend人工结束，revokeToStart撤销到发起人，back驳回，end结束）、<br/>"
			+ "pageSize（分页大小，不填默认20）、currentPage（当前页，不填默认第一页）", httpMethod = "GET")
	public JSONObject getInstanceListByXml(
			@ApiParam(name = "xml", value = "xml", required = true) @RequestParam String xml) throws Exception {
		return iProcessService.getInstanceListByXml(xml);
	}

	/**
	 * 通过流程实例id挂起流程实例
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="forbiddenInstance",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过流程实例id挂起流程实例",notes="通过流程实例id挂起流程实例",httpMethod="GET")
	public CommonResult<String> forbiddenInstance(@ApiParam(name="instId",value="实例id",required=true) @RequestParam String instId) throws Exception{
		return iProcessService.forbiddenInstance(instId);
	}
	
	/**
	 * 通过流程实例id取消挂起流程实例
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="unForbiddenInstance",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过流程实例id取消挂起流程实例",notes="通过流程实例id取消挂起流程实例",httpMethod="GET")
	public CommonResult<String> unForbiddenInstance(@ApiParam(name="instId",value="实例id",required=true) @RequestParam String instId) throws Exception{
		return iProcessService.unForbiddenInstance(instId);
	}
	
	/**
	 * 根据父流程实例ID和节点定义ID查子流程实例
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getBpmProcessByParentIdAndSuperNodeId",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="根据父流程实例ID和节点定义ID查子流程实例",notes="根据父流程实例ID和节点定义ID查子流程实例",httpMethod="GET")
	public List<BpmProcessInstance> getBpmProcessByParentIdAndSuperNodeId(@ApiParam(name="parentInstId",value="父实例ID",required=true) @RequestParam String parentInstId,@ApiParam(name="superNodeId",value="节点ID",required=true) @RequestParam String superNodeId) throws Exception{
		return iProcessService.getBpmProcessByParentIdAndSuperNodeId(parentInstId,superNodeId);
	}
	
	/**
	 * 通过父流程实例ID和实例的状态获取实例列表
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInstancesByParentId",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过父流程实例ID和实例的状态获取实例列表",notes="通过父流程实例ID和实例的状态获取实例列表",httpMethod="GET")
	public List<DefaultBpmProcessInstance> getInstancesByParentId(@ApiParam(name="parentInstId",value="父实例ID",required=true) @RequestParam String parentInstId,@ApiParam(name="status",value="状态（draft：草稿，running：运行中，end：结束，manualend：人工结束，backToStart：驳回到发起人"
			+ "back：驳回，revoke：撤销，revokeToStart：撤销到发起人）",required=true) @RequestParam String status) throws Exception{
		return iProcessService.getInstancesByParentId(parentInstId,status);
	}
	
	/**
	 * 通过父流程实例ID和实例的状态获取实例列表
	 * @param businessKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getInstancesByDefId",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="通过流程定义id和实例的状态获取实例列表",notes="通过流程定义id和实例的状态获取实例列表",httpMethod="GET")
	public List<DefaultBpmProcessInstance> getInstancesByDefId(@ApiParam(name="defId",value="",required=true) @RequestParam String defId,@ApiParam(name="status",value="状态（draft：草稿，running：运行中，end：结束，manualend：人工结束，backToStart：驳回到发起人"
			+ "back：驳回，revoke：撤销，revokeToStart：撤销到发起人）",required=true) @RequestParam String status) throws Exception{
		return iProcessService.getInstancesByDefId(defId,status);
	}
	
	/**
	 * 根据流程实例ID查询顶级的流程实例。
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getTopBpmProcessInstance",method=RequestMethod.GET,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="根据流程实例ID查询顶级的流程实例（根据父实例向上查找，只到找到父实例为0的实例为止）",notes="根据流程实例ID查询顶级的流程实例（根据父实例向上查找，只到找到父实例为0的实例为止）",httpMethod="GET")
	public BpmProcessInstance getTopBpmProcessInstance(@ApiParam(name="instId",value="实例id",required=true) @RequestParam String instId) throws Exception{
		return iProcessService.getTopBpmProcessInstance(instId);
	}
	
	/**
	 * 我的所有请求（包括人工终止和结束状态的实例）
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "getMyRequestListAll", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "我的请求（包括人工终止和结束状态的实例）", httpMethod = "POST", notes = "我的请求（包括人工终止和结束状态的实例）")
	public RestPageList<DefaultBpmProcessInstance> getMyRequestListAll(@ApiParam(required = true, name = "taskOrInstanQueryFilter", value = "查询参数对象") @RequestBody TaskOrInstanQueryFilter taskOrInstanQueryFilter,HttpServletResponse response) throws Exception{
		PageList<DefaultBpmProcessInstance> myRequestListAll = iFlowService.getMyRequestListAll(taskOrInstanQueryFilter);
		RestPageList<DefaultBpmProcessInstance> restPageList = new RestPageList<DefaultBpmProcessInstance>(myRequestListAll);
		return restPageList;
	}
	
	/**
	 * 根据流程定义id或流程实例id或任务id或BPMN实例ID获取流程图。
	 * @param instId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getBpmImage",method=RequestMethod.POST,produces = {"application/json; charset=utf-8"})
	@ApiOperation(value="根据流程定义id或流程实例id或任务id或BPMN实例ID获取流程图。",notes="根据流程定义id或流程实例id或任务id或BPMN实例ID获取流程图。",httpMethod="POST")
	public String getBpmImage(@ApiParam(name="BpmImageParamObject",value="获取流程图（状态）参数",required=true)@RequestBody BpmImageParamObject bpmImageParamObject) throws Exception{
		return iProcessService.getBpmImage(bpmImageParamObject);
	}
	
	/**
	 * 查询流程实例列表
	 * @param taskQueryFilterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getInstanceList", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
	@ApiOperation(value = "查询流程实例列表", httpMethod = "POST", notes = "查询流程实例列表")
	public RestPageList<DefaultBpmProcessInstance> getInstanceList(HttpServletResponse response,@ApiParam(required = true, name = "baseQueryFilter", value = "查询参数对象") @RequestBody BaseQueryFilter baseQueryFilter) throws Exception{
		PageList<DefaultBpmProcessInstance> list = iFlowService.getInstanceList(baseQueryFilter);
		RestPageList<DefaultBpmProcessInstance> restPageList = new RestPageList<DefaultBpmProcessInstance>(list);
		return restPageList;
	}
}
