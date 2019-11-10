package com.hotent.sys.api.data.source;

import net.sf.json.JSONArray;

public interface SysDataSourceDefService {

	JSONArray getHasSetterFieldsJsonArray(String classPath);
	
}
