package com.hotent.restful.params;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程向下执行参数
 * @author liangqf
 *
 */
@ApiModel
public class DoNextParamObject {
	
	@ApiModelProperty(name="account",notes="审批人帐号",required=true,example="admin")
	private String account;
	
	@ApiModelProperty(name="taskId",notes="任务id",required=true)
	private String taskId;
	
	@ApiModelProperty(name="vars",notes="流程变量，变量名：变量值，如{\"var1\":\"val1\",\"var2\":\"val2\"...}")
	private Map<String,String> vars;
	
	@ApiModelProperty(name="actionName",required=true,
			notes="审批动作,agree（审批）abandon（弃权）oppose（反对）agreeTrans（同意流转）opposeTrans（反对流转）commu（沟通反馈）reject（驳回）backToStart（驳回指定节点）"
			,allowableValues="agree,abandon,oppose,agreeTrans,opposeTrans,commu,reject,backToStart")
	private String actionName;
	
	@ApiModelProperty(name="opinion",notes="意见")
	private String opinion;
	
	@ApiModelProperty(name="data",notes="bo业务数据，以base64加密后的密文")
	private String data;
	
	@ApiModelProperty(name="directHandlerSign",notes="会签时是否直接审批通过",allowableValues="true,false")
	private boolean directHandlerSign;
	
	@ApiModelProperty(name="backHandMode",notes="驳回模式 ,direct :直来直往,normal: 按照流程图方式驳回",allowableValues="direct,normal")
	private String backHandMode = "normal";
	
	@ApiModelProperty(name="jumpType",notes=" 跳转方式 free : 自由跳转 ,select : 选择跳转,reject :驳回",allowableValues="free,select,reject")
	private String jumpType;
	
	@ApiModelProperty(name="nodeUsers",notes="节点用户，以base64加密[{nodeId:\"userTask1\",executors:[{id:\"\",name:\"\"},..]}]后的数据")
	private String nodeUsers;
	
	@ApiModelProperty(name="destination",notes="跳转的目标节点，传入节点id")
	private String destination;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Map<String, String> getVars() {
		return vars;
	}

	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean getDirectHandlerSign() {
		return directHandlerSign;
	}

	public void setDirectHandlerSign(boolean directHandlerSign) {
		this.directHandlerSign = directHandlerSign;
	}

	public String getBackHandMode() {
		return backHandMode;
	}

	public void setBackHandMode(String backHandMode) {
		this.backHandMode = backHandMode;
	}

	public String getJumpType() {
		return jumpType;
	}

	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}

	public String getNodeUsers() {
		return nodeUsers;
	}

	public void setNodeUsers(String nodeUsers) {
		this.nodeUsers = nodeUsers;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
