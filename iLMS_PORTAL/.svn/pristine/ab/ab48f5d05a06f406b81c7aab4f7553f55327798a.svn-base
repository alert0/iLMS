package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 任务转办/加签参数对象
 * @author liangqf
 *
 */
@ApiModel
public class AssignParamObject {
	
	@ApiModelProperty(name="account",notes="用户帐号",required=true,example="admin")
	private String account;
	
	@ApiModelProperty(name="taskId",notes="任务id",required=true)
	private String taskId;
	
	@ApiModelProperty(name="messageType",notes="消息通知类型 非必填，默认邮件，inner（内部消息），mail（邮件），sms（短信），多个之单使用英文逗号隔开",allowableValues="mail,inner,sms")
	private String messageType = "mail";
	
	@ApiModelProperty(name="opinion",notes="意见")
	private String opinion;
	
	@ApiModelProperty(name="userAccount",notes="转办/加签用户账号，多个账号之间用逗号分隔",required=true)
	private String userAccount;

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

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
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
}
