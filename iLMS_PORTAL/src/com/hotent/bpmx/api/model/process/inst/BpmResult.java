package com.hotent.bpmx.api.model.process.inst;



import java.util.HashMap;
import java.util.Map;

import com.hotent.bpmx.api.constant.DataType;

/**
 * 流程执行结果。
 * <pre>
 * 这个类用于 com.hotent.bpmx.core.aspect.BpmAspect，业务代码计算结果。
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-6-5-上午10:56:12
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class BpmResult {

	/**
	 * 业务主键
	 */
	private String businessKey="";
	
	/**
	 * 业务主键类型
	 */
	private DataType dataType=DataType.STRING;

	/**
	 * 流程变量数据。
	 */
	private Map<String,Object> vars=new HashMap<String, Object>();

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	public Map<String, Object> getVars() {
		return vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}
	
	public void addVariable(String name,Object value){
		this.vars.put(name, value);
	}
	
	
	
}