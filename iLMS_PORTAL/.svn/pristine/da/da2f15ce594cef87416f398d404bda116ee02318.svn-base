package com.hotent.bpmx.persistence.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.dao.CopyToDao;
import com.hotent.bpmx.persistence.model.CopyTo;

@Repository
public class CopyToDaoImpl extends MyBatisDaoImpl<String, CopyTo> implements CopyToDao{

    @Override
    public String getNamespace() {
        return CopyTo.class.getName();
    }

	@Override
	public void delByInstList(List<String> instList) {
		this.deleteByKey("delByInstList", instList);
		
	}

	@Override
	public PageList<CopyTo> getReceiverCopyTo(String userId, QueryFilter filter) {
		filter.addParamsFilter("userId", userId);
		return (PageList<CopyTo>) this.getByQueryFilter("getReceiverCopyTo", filter);
	}

	@Override
	public PageList<CopyTo> getMyCopyTo(String userId, QueryFilter filter) {
		filter.addParamsFilter("userId", userId);
		
		PageList<CopyTo> pageList= (PageList<CopyTo>) this.getByQueryFilter("getMyCopyTo", filter);
		return pageList;
	}
	
}
