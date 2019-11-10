package com.hotent.bo.instance.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.instance.BoInstanceFactory;


/**
 * 获取BoDataHandler 工厂类实现。
 * @author ray
 */
public class BoInstanceFactoryImpl implements BoInstanceFactory {
	
	private Map<String, BoDataHandler> handlerMap=new HashMap<String, BoDataHandler>();
	
	
	/**
	 * handlerList 属性。
	 * @param list
	 */
	public void setHandlerList(List<BoDataHandler> list){
		for(BoDataHandler handler:list){
			handlerMap.put(handler.saveType(), handler);
		}
	}

	/**
	 * 获取保存类型。
	 */
	@Override
	public BoDataHandler getBySaveType(String saveType) {
		return handlerMap.get(saveType);
	}

}
