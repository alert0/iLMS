package com.hanthink.sw.manager;

import com.hanthink.sw.model.SwLogictiscOrderModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface SwLogictiscOrderManager extends Manager<SwLogictiscOrderModel, String>{

	PageList<SwLogictiscOrderModel> queryLogistiscsOrderForPage(SwLogictiscOrderModel model, Page page)throws Exception;

}
