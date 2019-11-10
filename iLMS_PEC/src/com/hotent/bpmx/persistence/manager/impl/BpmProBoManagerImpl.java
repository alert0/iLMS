package com.hotent.bpmx.persistence.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.hotent.bpmx.persistence.dao.BpmProBoDao;
import com.hotent.bpmx.persistence.manager.BpmProBoManager;
import com.hotent.bpmx.persistence.model.BpmProBo;

@Service("bpmProBoManager")
public class BpmProBoManagerImpl extends AbstractManagerImpl<String, BpmProBo> implements BpmProBoManager{
	@Resource
	BpmProBoDao bpmProBoDao;

	@Override
	protected Dao<String, BpmProBo> getDao() {
		return bpmProBoDao;
	}

	@Override
	public void removeByProcessId(String processId)
	{
		if(StringUtil.isEmpty(processId)){
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processId", processId);
		bpmProBoDao.removeByProcess(params);
	}

	@Override
	public void removeByProcessKey(String processKey)
	{
		if(StringUtil.isEmpty(processKey)){
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processKey", processKey);
		bpmProBoDao.removeByProcess(params);
	}

	@Override
	public void removeByBoCode(String boCode)
	{
		if(StringUtil.isEmpty(boCode)){
			return;
		}
		bpmProBoDao.removeByBoCode(boCode);
	}

	@Override
	public List<BpmProBo> getByProcessId(String processId)
	{
		if(StringUtil.isEmpty(processId)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processId", processId);
		return bpmProBoDao.getByProcess(params);
	}

	@Override
	public List<BpmProBo> getByProcessKey(String processKey)
	{
		if(StringUtil.isEmpty(processKey)){
			return null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("processKey", processKey);
		return bpmProBoDao.getByProcess(params);
	}

	@Override
	public List<BpmProBo> getByBoCode(String boCode)
	{
		if(StringUtil.isEmpty(boCode)){
			return null;
		}
		return bpmProBoDao.getByBoCode(boCode);
	}

	@Override
	public void createByBpmProBoList(List<BpmProBo> bpmProBoList)
	{
		if(bpmProBoList==null||bpmProBoList.size()<=0){
			return;
		}
		for (BpmProBo bpmProBo : bpmProBoList){
			String btId =bpmProBo.getId();
			if(StringUtil.isEmpty(btId)){
				btId = UniqueIdUtil.getSuid();
				bpmProBo.setId(btId);
			}
			bpmProBoDao.create(bpmProBo);
		}
	}
	
	
	
	
}
