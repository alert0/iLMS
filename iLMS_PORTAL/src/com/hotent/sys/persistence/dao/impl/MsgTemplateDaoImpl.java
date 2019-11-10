package com.hotent.sys.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.sys.persistence.dao.MsgTemplateDao;
import com.hotent.sys.persistence.model.MsgTemplate;

@Repository
public class MsgTemplateDaoImpl extends MyBatisDaoImpl<String, MsgTemplate> implements MsgTemplateDao{

    @Override
    public String getNamespace() {
        return MsgTemplate.class.getName();
    }
	
	@Override
	public MsgTemplate getDefault(String typeKey) {
		List<MsgTemplate> msgList = this.getByKey("getDefault",buildMapBuilder().addParam("typeKey", typeKey).getParams());
		if(msgList.size()>0){
			return msgList.get(0);
		}
		return null;
	}

	@Override
	public MsgTemplate getByKey(String key) {
		return (MsgTemplate) this.getOne("getByKey", key);
	}
    
	
	
}
