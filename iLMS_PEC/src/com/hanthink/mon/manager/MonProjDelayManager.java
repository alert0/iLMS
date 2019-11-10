package com.hanthink.mon.manager;

import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface MonProjDelayManager extends Manager<String, MonProjDelayModel>{

	PageList<MonProjDelayModel> queryProjDealyForPage(MonProjDelayModel model, Page page)throws Exception;

}
