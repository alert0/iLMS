package com.hotent.service.model.impl;

import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.service.api.model.InvokeResult;

/**
 * 调用服务的返回值对象
 * @author heyifan
 * @version 创建时间: 2014-8-18
 */
public class DefaultInvokeResult implements InvokeResult{
	private Object obj;			/*单个对象的返回值*/
	private List<Object> list;	/*列表类型的返回值*/
	private Exception ex;		/*调用服务时出现异常*/
	private String json;		/*以json格式返回结果*/

	public Object getObject() {
		return obj;
	}

	public void setObject(Object obj) {
		this.obj = obj;
	}
	
	public void setList(List<Object> list) {
		this.list = list;
	}
	
	public List<Object> getList(){
		return list;
	}

	public Exception getException() {
		return ex;
	}

	public void setException(Exception ex) {
		this.ex = ex;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Boolean isVoid() {
		return BeanUtils.isEmpty(obj)&&BeanUtils.isEmpty(list);
	}
	
	public Boolean isList(){
		return BeanUtils.isNotEmpty(list);
	}
	
	public Boolean isFault(){
		return BeanUtils.isNotEmpty(ex);
	}
}
