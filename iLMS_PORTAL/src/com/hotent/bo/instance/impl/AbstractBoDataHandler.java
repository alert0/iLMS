package com.hotent.bo.instance.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BOEnt;

public abstract class AbstractBoDataHandler implements BoDataHandler {

	@Resource
	BODefManager boDefManager;
	
	public void setBoCodeCode(List<BoResult> resultList,String bodefCode){
		for(BoResult result:resultList){
			result.setBoCode(bodefCode);
		}
	}
	

	@Override
	public BoData getByBoDefCode(String bodefCode) {
		BODef boDef= boDefManager.getByDefName(bodefCode);

		BoData boData=new BoData();
		
		boData.setBoDef(boDef);
		BOEnt boEnt=(BOEnt) boDef.getBoEnt();
		
		Map<String,Object> row= getMapByBOEnt( boEnt);
		
		boData.setData(row);
		
		getCascadeByEnt(boEnt,boData);
		
		return boData;
	}
	
	
	/**
	 * 获取默认的数据。
	 * @param boEnt
	 * @return
	 */
	public  Map<String,Object> getMapByBOEnt(BaseBoEnt boEnt){
		Map<String,Object> map=new HashMap<String, Object>();
		
		List<BaseAttribute> list= boEnt.getBoAttrList();
		for(BaseAttribute attr:list){
			String val=attr.getDefaultValue();
			if(StringUtil.isEmpty(val)) {
				val="";
			}
			map.put(attr.getName(), val);
		}
		return map;
	}
	


	/**
	 * 递归获取初始数据。
	 * @param boEnt
	 * @param boData
	 */
	private void getCascadeByEnt(BOEnt boEnt,BoData boData){
		//子表处理
		List<BaseBoEnt> childEntList= boEnt.getChildEntList();
		
		if(BeanUtils.isEmpty(childEntList)) return;
		/**
		 * 子表处理。
		 */
		for(BaseBoEnt childEnt:childEntList){
			String key=childEnt.getName();
			//递归
			getCascadeByEnt((BOEnt)childEnt,boData);
			//初始化数据。
			boData.addInitDataMap(key, childEnt.getInitData());
			//添加子表空数据。
			boData.setSubList(key,new ArrayList<BoData>());
		}

	}
}
