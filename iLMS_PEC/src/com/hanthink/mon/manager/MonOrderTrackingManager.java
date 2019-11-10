package com.hanthink.mon.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mon.model.MonOrderTrackingModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface MonOrderTrackingManager extends Manager<String,MonOrderTrackingModel>{

	PageList<MonOrderTrackingModel> queryOrderTrackingForPage(MonOrderTrackingModel model, Page page)throws Exception;

	List<DictVO> queryForOrderType()throws Exception;

	List<MonOrderTrackingModel> exportForExcel(MonOrderTrackingModel model)throws Exception;

}
