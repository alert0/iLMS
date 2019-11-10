package com.hotent.sys.persistence.manager;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.MsgTemplate;

public interface MsgTemplateManager extends Manager<String, MsgTemplate>{
	
	public MsgTemplate getByKey(String templateKey);
	
	public MsgTemplate getDefault(String typeKey);
}
