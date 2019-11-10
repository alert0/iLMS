package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务沟通参数
 * @author heyifan
 */
@ApiModel(value="任务沟通参数")
public class CommunicateParamObject {
	@ApiModelProperty(name="account",notes="用户帐号",required=true,example="admin")
	private String account;
	@ApiModelProperty(name="taskId",notes="任务ID",required=true,example="100000000001")
	private String taskId;
	@ApiModelProperty(name="opinion",notes="发起沟通的原因", example="这份合同帮我看看，提提意见")
	private String opinion;
	@ApiModelProperty(name="userAccount",notes="沟通的用户账号(多个账号之间用英文逗号分隔)", required=true, example="zhangsan,lisi")
	private String userAccount;
	@ApiModelProperty(name="messageType",notes="消息通知类型 (非必填)，默认为邮件，inner（内部消息），mail（邮件），sms（短信），多个之间使用英文逗号隔开",allowableValues="mail,inner,sms")
	private String messageType = "mail";
	
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
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
