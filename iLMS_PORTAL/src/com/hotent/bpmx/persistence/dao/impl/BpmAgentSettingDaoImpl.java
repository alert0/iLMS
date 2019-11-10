package com.hotent.bpmx.persistence.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmAgentSettingDao;
import com.hotent.bpmx.persistence.model.BpmAgentSetting;


@Repository

public class BpmAgentSettingDaoImpl extends MyBatisDaoImpl<String, BpmAgentSetting> implements BpmAgentSettingDao{

    @Override
    public String getNamespace() {
        return BpmAgentSetting.class.getName();
    }

	@Override
	public BpmAgentSetting getSettingByFlowAndAuthidAndDate(String authId,
			String flowKey) {
		Map<String,Object> params=buildMapBuilder().addParam("authid", authId)
				.addParam("flowkey", flowKey)
				.addParam("date", new Date())
				.getParams();
		List<BpmAgentSetting> list=this.getByKey("getSettingByFlowAndAuthidAndDate", params);
		if(BeanUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer getByAuthAndDate(String settingId,String authId, Date beginDate,Date endDate) {
		if(StringUtil.isEmpty(settingId))
			settingId = null;
		Map<String,Object> params=buildMapBuilder()
				.addParam("settingId", settingId)
				.addParam("authid", authId)
				.addParam("startDate", beginDate)
				.addParam("endDate", endDate)
				.getParams();
		Integer rtn=(Integer) this.getOne("getByAuthAndDate", params);
		return rtn;
	}

	@Override
	public Integer getByAuthDateFlowKey(String settingId, String authId, Date beginDate,
			Date endDate, String flowKey) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("settingId", settingId)
				.addParam("authid", authId)
				.addParam("startDate", beginDate)
				.addParam("endDate", endDate)
				.addParam("flowKey", flowKey)
				.getParams();
		Integer rtn=(Integer) this.getOne("getByAuthDateFlowKey", params);
		return rtn;
	}

	

	@Override
	public Integer getForCondition(String settingId, String authId, Date beginDate, Date endDate,
			String flowKey) {
		Map<String,Object> params=buildMapBuilder()
				.addParam("settingId", settingId)
				.addParam("authid", authId)
				.addParam("startDate", beginDate)
				.addParam("endDate", endDate)
				.addParam("flowKey", flowKey)
				.getParams();
		
		Integer rtn=(Integer) this.getOne("getForCondition", params);
		return rtn;
	}

	
	
	
	
	
	
	
}

