package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmProBoDao;
import com.hotent.bpmx.persistence.model.BpmProBo;


@Repository
public class BpmProBoDaoImpl extends MyBatisDaoImpl<String, BpmProBo> implements BpmProBoDao{

    @Override
    public String getNamespace() {
        return BpmProBo.class.getName();
    }

	@Override
	public void removeByProcess(Map<String, Object> params)
	{
		getByKey("removeByProcess", params);
	}

	@Override
	public void removeByBoCode(String boCode)
	{
		getByKey("removeByBoCode", boCode);
		
	}

	@Override
	public List<BpmProBo> getByProcess(Map<String, Object> params)
	{
		return getByKey("getByProcess", params);
	}

	@Override
	public List<BpmProBo> getByBoCode(String boCode)
	{
		return getByKey("getByBoCode", boCode);
	}


	
}

