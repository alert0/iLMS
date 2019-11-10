package com.hotent.restful.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 撤销流程参数
 * @author liangqf
 *
 */
@ApiModel
public class RevokeParamObject {
	
	@ApiModelProperty(name="account",notes="发起人帐号",example="admin",required=true)
	private String account;
	@ApiModelProperty(name="instanceId",notes="流程实例id",required=true)
	private String instanceId;
	
	@ApiModelProperty(name="messageType",notes="消息通知类型，默认邮件通知，inner（内部消息），mail（邮件），sms（短信），多个之单使用英文逗号隔开",allowableValues="mail,inner,sms",required=true)
	private String messageType;
	
	@ApiModelProperty(name="cause",notes="原因")
	private String cause;
	
	@ApiModelProperty(name="isHandRevoke",notes="是否是从已办中撤回",required=true)
	private Boolean isHandRevoke;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Boolean getIsHandRevoke() {
		return isHandRevoke;
	}

	public void setIsHandRevoke(Boolean isHandRevoke) {
		this.isHandRevoke = isHandRevoke;
	}

}
