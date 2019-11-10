package com.hotent.bpmx.persistence.dao.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.PropertyUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.bpmx.persistence.dao.BpmBusLinkDao;
import com.hotent.bpmx.persistence.model.BpmBusLink;


@Repository
public class BpmBusLinkDaoImpl extends MyBatisDaoImpl<String, BpmBusLink> implements BpmBusLinkDao{

    @Override
    public String getNamespace() {
        return BpmBusLink.class.getName();
    }
    
	@Override
	public BpmBusLink getByBusinesKey(String businessKey, String formIdentity,
			boolean isNumber) {
		
		Map<String, Object> params=getParams(businessKey, formIdentity, isNumber);
		
		BpmBusLink bpmBusLink= this.getUnique("getByBusinesKey", params);
		
		return bpmBusLink;
	}
	@Override
	public BpmBusLink getByBusinesKey(String businessKey, boolean isNumber) {
		
		Map<String, Object> params=getParams(businessKey,null, isNumber);
		
		BpmBusLink bpmBusLink= this.getUnique("getByBusinesKey", params);
		
		return bpmBusLink;
	}
	@Override
	public void delByBusinesKey(String businessKey, String formIdentity,
			boolean isNumber) {
		Map<String, Object> params=getParams(businessKey, formIdentity, isNumber);
		
		this.deleteByKey("delByBusinesKey", params);
	}
	
	private Map<String, Object> getParams(String businessKey, String formIdentity,
			boolean isNumber){
		Map<String, Object> params=new HashMap<String, Object>();
		
		if(isNumber){
			params.put("businessKey", Long.parseLong(businessKey));
		}
		else{
			params.put("businessKey", businessKey);
		}
		
		if(StringUtil.isNotEmpty(formIdentity)){
			params.put("formIdentity", formIdentity);
		}
		
		params.put("isNumber", isNumber);
		
		return params;
	}

	@Override
	public List<BpmBusLink> getByInstId(String instId) {
		return this.getByKey("getByInstId", instId);
	}

	@Override
	public String getMysqlVersion() {
		return (String) this.getOne("getMysqlVersion", null);
	}
	
	@Override
	public void create(BpmBusLink entity) {
		if(StringUtil.isEmpty(entity.getFormIdentify())) entity.setFormIdentify(BpmBusLink.TABLE_UNCREATED);
		
		if(BpmBusLink.isSupportPartition()) createPartitionIfNotExit(entity.getFormIdentify());
		
		 super.create(entity);
	}
	
	/**
	 * 分区表名字 大写。值不大写
	 * **/
	private static Set<String> partitions = new HashSet<String>();
	private void createPartitionIfNotExit(String formIdentify) {
		String partitionName ="P_"+ formIdentify.toUpperCase();
		if(partitions.contains(partitionName)) return;
		String dataType = PropertyUtil.getJdbcType();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("partitionName", partitionName);
			map.put("formIdentify", formIdentify); 
//			Long count = (Long) getOne("isExsitPartition_"+dataType, map);
//			if(count == 0) getOne("createPartition_"+dataType, map);
//			partitions.add(partitionName);
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据库类型："+dataType+" BPM_BUS_LINK 表，操作分区失败！");
		}
	}

	@Override
	public List<BpmBusLink> getByDefId(String defId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("defId", defId);
		return this.getList("getByDefId", params);
	}
}