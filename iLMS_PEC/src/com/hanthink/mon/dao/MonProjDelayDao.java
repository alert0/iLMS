package com.hanthink.mon.dao;

import java.util.List;

import com.hanthink.mon.model.MonProjDelayModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;

public interface MonProjDelayDao extends Dao<String, MonProjDelayModel>{

	List<MonProjDelayModel> queryProjDealyForPage(MonProjDelayModel model, Page page)throws Exception;

}
