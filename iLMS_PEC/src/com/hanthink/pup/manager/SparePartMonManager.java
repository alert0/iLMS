package com.hanthink.pup.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.pup.model.SparePartMonModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SparePartMonManager extends Manager<String, SparePartMonModel>{

	PageList<SparePartMonModel> querySparePartForPage(SparePartMonModel model, Page page)throws Exception;

	List<SparePartMonModel> queryPartCheck(Map<String, String> paramMap, Page page)throws Exception;
	
}
