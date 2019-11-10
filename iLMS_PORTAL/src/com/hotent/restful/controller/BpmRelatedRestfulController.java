package com.hotent.restful.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotent.base.core.util.AppUtil;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.plugin.task.userassign.UserDefBpmDefXmlHandler;
import com.hotent.bpmx.webservice.api.IFlowService;
import com.hotent.bpmx.webservice.api.IProcessService;
import com.hotent.bpmx.webservice.model.BpmIdentityResult;
import com.hotent.restful.params.BpmAgentsettingParam;
import com.hotent.restful.params.CommonResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 流程相关的对象信息，如流程节点、流程人员等
 * @author liangqf
 *
 */
@RestController
@RequestMapping("/restful/bpmrelated/")
@Api(tags="BpmRelatedRestfulController")
public class BpmRelatedRestfulController extends RestfulBaseController {
	
	@Resource
	IFlowService iFlowService;
	@Resource
	IProcessService iProcessService;
	
	/**
	 * 根据流程定义key获取流程的所有节点信息
	 * @param defKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNodesByDefKey", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value = "根据流程定义key获取流程的所有节点信息", httpMethod = "GET", notes = "根据流程定义key获取流程的所有节点信息")
	public JSONArray getNodesByDefKey( @ApiParam(name="defKey",value="流程定义key",required=true) @RequestParam String defKey) throws Exception {
		return iFlowService.getNodesByDefKey(defKey);
	}
	
	/**
	 * 根据任务id获取下一环节处理人
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getNextTaskUsers", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8" })
	@ApiOperation(value="根据任务id获取下一环节处理人",notes="根据任务id获取下一环节处理人",httpMethod="GET")
	public Map<String, List<BpmIdentity>> getNextTaskUsers(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception {
		return iFlowService.getNextTaskUsers(taskId);
	}
	
	/**
	 * 根据任务id获取预先设置的审批用语列表
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getApprovalItems",method= RequestMethod.GET,produces={"application/json; charset=utf-8"})
	@ApiOperation(value="根据任务id获取预先设置的审批用语列表",notes="根据任务id获取预先设置的审批用语列表",httpMethod="GET")
	public List<String> getApprovalItems(@ApiParam(name="taskId",value="任务id",required=true) @RequestParam String taskId) throws Exception {
		return iProcessService.getApprovalItems(taskId);
	}
	
	/**
	 * 根据任务id获取流程实例某个节点上的执行人员
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getNodeUsers",method=RequestMethod.GET,produces={"application/json; charset=utf-8"})
	@ApiOperation(value="根据任务id获取流程实例某个节点上的执行人员",notes="根据任务id获取流程实例某个节点上的执行人员",httpMethod="GET")
	public List<BpmIdentityResult> getNodeUsers(@ApiParam(value="任务id",name="taskId",required=true) @RequestParam String taskId) throws Exception {
		return iProcessService.getNodeUsers(taskId);
	}
	
	/**
	 * 设置流程代理
	 * @param conditions
	 * @param agentDefList
	 * @param agentSetting
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="setAgent",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
	@ApiOperation(value="设置流程代理",notes="设置流程代理",httpMethod="POST")
	public CommonResult<String> setAgent(
			@ApiParam(value = "流程代理设置对象", name = "agentSetting", required = true) @RequestBody BpmAgentsettingParam agentSetting)
			throws Exception {
		return iFlowService.setAgent(agentSetting);
	}
	
	/**
	 * 根据实例id和节点id获取节点状态
	 * @param instId
	 * @param nodeId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getStatusByRunidNodeId",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ApiOperation(value="根据实例id和节点id获取节点状态",notes="根据实例id和节点id获取节点状态",httpMethod="GET")
	public String getStatusByRunidNodeId(@ApiParam(name="instId",value="实例id") @RequestParam String instId,@ApiParam(name="nodeId",value="节点id") @RequestParam String nodeId) throws Exception{
		return iProcessService.getStatusByRunidNodeId(instId, nodeId);
	}
	
	/**
	 * 根据任务id获取在线表单地址
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getDetailUrl",method=RequestMethod.GET,produces={"application/json;charset=utf-8"})
	@ApiOperation(value="根据任务id获取在线表单地址",notes="根据任务id获取在线表单地址",httpMethod="GET")
	public String getDetailUrl(@ApiParam(name="taskId",value="任务id") @RequestParam String taskId) throws Exception{
		return iProcessService.getDetailUrl(taskId);
	}
	
	/**
	 * 保存流程定义节点人员设置
	 * 
	 * @param defId
	 * @param parentFlowKey
	 * @param nodeUsers
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveNodes", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ApiOperation(value = "保存流程定义节点人员设置", notes = "保存流程定义节点人员设置", httpMethod = "POST")
	public CommonResult<String> saveNodes(@ApiParam(name = "map", value = "流程定义id与父流程定义key，{defId:\"\",parentFlowKey:\"\"}", required = true) @RequestBody Map<String,String> map,
			@ApiParam(name = "nodeUsers", value = "流程节点人员", required = true) @RequestParam String nodeUsers)
			throws Exception {
		// 保存人员
		String defId = map.get("defId").toString();
		String parentFlowKey = map.get("parentFlowKey").toString();
		JSONObject jsonObject = JSONObject.fromObject(nodeUsers);
		Set<String> nodeIds = jsonObject.keySet();
		for (String nodeId : nodeIds) {
			UserDefBpmDefXmlHandler userDefBpmDefXmlHandler = (UserDefBpmDefXmlHandler) AppUtil
					.getBean(UserDefBpmDefXmlHandler.class);
			userDefBpmDefXmlHandler.saveNodeXml(defId, nodeId, jsonObject.get(nodeId).toString(), parentFlowKey);
		}
		return new CommonResult<String>(true, "保存流程定义节点人员成功", "");
	}

}
