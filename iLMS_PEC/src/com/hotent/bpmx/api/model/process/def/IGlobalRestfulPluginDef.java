package com.hotent.bpmx.api.model.process.def;

import java.util.List;

public interface IGlobalRestfulPluginDef {
	
	List<Restful> getRestfulList();
	
	void setRestfulList(List<Restful> restfulList);
}
