package com.hotent.restful.params;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 流程启动参数
 * @author liangqf
 *
 */
@ApiModel
public class StartFlowParamObject {
	
	@ApiModelProperty(name="account",notes="发起人帐号",example="admin",required=true)
	private String account;
	
	@ApiModelProperty(name="defId",notes="流程定义id，流程定义id与流程key必填其中一个")
	private String defId;
	
	@ApiModelProperty(name="flowKey",notes="流程key，流程定义id与流程key必填其中一个")
	private String flowKey;
	
	@ApiModelProperty(name="subject",notes="流程标题，不填则按流程定义中设置的标题规则生成")
	private String subject;
	
	@ApiModelProperty(name="procInstId",notes="流程实例id")
	private String procInstId;
	
	@ApiModelProperty(name="vars",notes="流程变量，变量名：变量值，如{\"var1\":\"val1\",\"var2\":\"val2\"...}")
	private Map<String,String> vars;
	
	@ApiModelProperty(name="data",notes="bo业务数据，以base64加密后的密文")
	private String data;
	
	@ApiModelProperty(name="businessKey",notes="业务主键KEY，只对URL表单形式有效")
	private String businessKey;
	
	@ApiModelProperty(name="sysCode",notes="业务系统编码，只对URL表单形式有效")
	private String sysCode;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getFlowKey() {
		return flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProcInstId() {
		return procInstId;
	}

	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	public Map<String, String> getVars() {
		return vars;
	}

	public void setVars(Map<String, String> vars) {
		this.vars = vars;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

}
