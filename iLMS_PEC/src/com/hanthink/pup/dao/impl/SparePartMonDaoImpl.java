package com.hanthink.pup.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.pup.dao.SparePartMonDao;
import com.hanthink.pup.model.SparePartMonModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;

@Repository
public class SparePartMonDaoImpl extends MyBatisDaoImpl<String, SparePartMonModel>
					implements SparePartMonDao{

	@Override
	public String getNamespace() {
		return SparePartMonModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SparePartMonModel> querySparePartForPage(SparePartMonModel model, Page page) throws Exception {
		return (List<SparePartMonModel>) this.getList("querySparePartForPage", model, page);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SparePartMonModel> queryPartCheck(Map<String, String> paramMap, Page page) throws Exception {
		return this.getListByKey("queryPartCheck", paramMap, page);
	}

}
