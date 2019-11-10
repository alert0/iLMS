package com.hotent.bpmx.webservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 处理结果
 * @author heyifan
 */
@ApiModel(value="处理结果")
public class BasicResult {
	@ApiModelProperty(name="result",notes="处理结果，true为成功",example="true",required=true)
	private Boolean result = true;
	@ApiModelProperty(name="message",notes="结果消息",example="处理成功",required=true)
	private String message = "处理成功";
	
	public BasicResult(){}
	
	public BasicResult(String message){
		this.message = message;
	}
	
	public BasicResult(Boolean result,String message){
		this.result = result;
		this.message = message;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
