package com.hotent.mini.ext.handler;

import java.util.Iterator;


import org.springframework.stereotype.Service;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.bpmx.api.cmd.ActionCmd;

@Service
public class BusDataHandler {
	
	/**
	 * 设置前置处理器或后置处理器， 用于在保存前改变表单的数据
	 * @param processCmd
	 */
	public void changeBoData(ActionCmd processCmd){
		//表单数据
		String busData=processCmd.getBusData();
		String defCode = "ygydddy";
		String field = "xm";
		String value = "通过前后置处理器修改表单数据";
		busData = updateBusData(busData,defCode,field,value);
		processCmd.setBusData(busData);

	}
	
	private String updateBusData(String busData,String defCode,String field,Object value){
		try {
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(busData);
			Iterator iterator = jsonObject.keys();
			while(iterator.hasNext()){
			     String key = (String) iterator.next();
			     if(key.equals(defCode)){
			    	 net.sf.json.JSONObject ywdxObj = jsonObject.getJSONObject(key);
			    	 if(BeanUtils.isNotEmpty(ywdxObj)){
			    		 Iterator fieldIterator = ywdxObj.keys();
			    		 while(fieldIterator.hasNext()){
			    			 String fieldKey = (String) fieldIterator.next();
			    			 if(fieldKey.equals(field)){
			    				 ywdxObj.put(field, value);
			    				 busData = jsonObject.toString();
			    			 }
			    		 }
			    	 }
			     }
			}
		} catch (Exception e) {}
		return busData;
	}

}
