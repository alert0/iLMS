package com.hotent.bpmx.webservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 启动流程的结果
 * @author heyifan
 */
@ApiModel(value="启动流程的结果",parent=BasicResult.class)
public class StartResult extends BasicResult{
	@ApiModelProperty(name="message",notes="流程实例ID",example="10000000000001",required=true)
	private String instId;
	
	public StartResult(String instId){
		this.instId = instId;
	}
	
	public StartResult(String message, String instId){
		super(message);
		this.instId = instId;
	}
	
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
}
