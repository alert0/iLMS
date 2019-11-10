package com.hanthink.pup.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.SparePartMonModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface SparePartMonDao extends Dao<String, SparePartMonModel>{

	List<SparePartMonModel> querySparePartForPage(SparePartMonModel model, Page page)throws Exception;

	List<SparePartMonModel> queryPartCheck(Map<String, String> paramMap, Page page)throws Exception;

}
