package com.hotent.bo.instance.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bo.api.constant.BodefConstants;
import com.hotent.bo.api.instance.BoUtil;
import com.hotent.bo.api.instance.DataTransform;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bo.api.model.BoResult;
import com.hotent.bo.persistence.dao.BoInstDao;
import com.hotent.bo.persistence.manager.BODefManager;
import com.hotent.bo.persistence.model.BODef;
import com.hotent.bo.persistence.model.BoInst;



public class BoInstHandler extends AbstractBoDataHandler {
	
	@Resource
	BoInstDao boInstDao;
	@Resource
	DataTransform dataTransform;
	@Resource
	BODefManager boDefManager;
	
	private void setId( BoData curData,boolean isAdd){
		if(isAdd){
			curData.set("uuid_", UUID.randomUUID().toString());
		}
		else{
			if(!curData.containKey("uuid_")){
				curData.set("uuid_", UUID.randomUUID().toString());
			}
		}
	}

	@Override
	public List<BoResult> save(String id,String defId, BoData curData) {
		
		curData.removeByKey("initData");
		boolean isAdd=StringUtil.isEmpty(id);
		//设置唯一ID
		setId(curData,isAdd);
		Map<String, List<BoData>> map=curData.getSubMap();
		for (Map.Entry<String, List<BoData>> ent : map.entrySet()){
			List<BoData> list=ent.getValue();
			for(BoData bo:list){
				setId(bo,isAdd);
			}
		}
		
		
		String json=dataTransform.getByData(curData,false);
		
		
		BoInst inst=null;
		
		if(isAdd){
			String pk=UniqueIdUtil.getSuid();
			inst=new BoInst();
			inst.setId(pk);
			inst.setDefId(defId);
			inst.setInstData(json);
			inst.setCreateTime(new Date());
			boInstDao.create(inst);
		}
		else{
			inst=boInstDao.get(id);
			inst.setInstData(json);
			boInstDao.update(inst);
		}
		
		List<BoResult> list=new ArrayList<BoResult>();
		BoResult result=new BoResult();
		
		String action=isAdd?BoResult.ACTION_TYPE.ADD:BoResult.ACTION_TYPE.UPDATE;
		result.setAction(action);
		result.setPk(inst.getId());
		result.setBoEnt(curData.getBoEnt());
		
		
		list.add(result);
		
		setBoCodeCode(list,curData.getBoDef().getAlias());
		
		return list;
	}

	@Override
	public BoData getById(Object id, String bodefCode) {
		
		BODef boDef= boDefManager.getByDefName(bodefCode);
		BaseBoEnt boEnt= boDef.getBoEnt();
		BoInst inst=boInstDao.get((String)id);
		String json=inst.getInstData();
		JSONObject jsonObj=JSONObject.parseObject(json);
		
		BoData data=BoUtil.transJSON(jsonObj);
		data.setBoDef(boDef);
		data.setBoEnt(boEnt);
		//设置初始化值。
		setInitData(data,boEnt);
		
		return data;
	}
	
	private void setInitData(BoData data,BaseBoEnt boEnt){
		List<BaseBoEnt> list=boEnt.getChildEntList();
		if(BeanUtils.isEmpty(list)) return;
		for(BaseBoEnt ent:list){
//			if(data.getSubMap().containsKey(ent.getName())) continue;
			data.addInitDataMap(ent.getName(), ent.getInitData());
			setInitData(data, ent);
		}
	}
	

	@Override
	public BoData getResById(Object id, String bodefCode) {
		return getById( id,  bodefCode);
	}

	@Override
	public String saveType() {
		return BodefConstants.SAVE_MODE_BOOBJECT;
	}

	@Override
	public void removeBoData(String boCode, String[] aryIds) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Map<String,Object>> getList(String boCode, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageList<Map<String, Object>> getList(String boCode,
			QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
