package com.hotent.sys.persistence.manager;

import java.util.List;
import java.util.Map;

import com.hotent.base.manager.api.Manager;
import com.hotent.sys.persistence.model.DefaultFile;
 

public interface FileManager extends Manager<String, DefaultFile>{

	List<DefaultFile> getAllByExt(String[] allowFiles);

	String getFileSuffixInParams(Map<String, String> map);
	
}
