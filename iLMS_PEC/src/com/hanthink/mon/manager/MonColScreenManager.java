package com.hanthink.mon.manager;

import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonColScreenModel;
import com.hotent.base.manager.api.Manager;

public interface MonColScreenManager extends Manager<String, MonColScreenModel>{

	Map<String, List<MonColScreenModel>> getShowMessageMap()throws Exception;
	
}
