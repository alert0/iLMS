package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.jitPartLackDao;
import com.hanthink.jit.model.JitPartLackModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;

@Repository
public class jitPartLackDaoImpl extends MyBatisDaoImpl<String, JitPartLackModel> implements jitPartLackDao{

	@Override
	public String getNamespace() {
 
		return JitPartLackModel.class.getName();
	}

	@Override
	public List<JitPartLackModel> getJitPartLackList(JitPartLackModel model,DefaultPage page) {
		return this.getByKey("getJitPartLackList", model, page);
	}

	@Override
	public List<JitPartLackModel> getJitPartLackDetialList(JitPartLackModel model,DefaultPage page) {
		
		return this.getByKey("getJitPartLackDetialList", model,page);
	}

	@Override
	public void updateCheckFlag(JitPartLackModel model) {
		this.updateByKey("updateCheckFlag",model);
	}

	@Override
	public void insertLackDeal(JitPartLackModel model) {
		this.insertByKey("insertLackDeal", model);
	}

}
