package com.hotent.sys.service.impl.permission;

import java.util.Map;
import java.util.Set;

import com.hotent.sys.api.permission.IPermission;

public class NonePermission implements IPermission {

	@Override
	public String getTitle() {
		
		return "无";
	}

	@Override
	public String getType() {
		return "none";
	}

	

	@Override
	public boolean hasRight(String json, Map<String, Set<String>> currentMap) {
		return false;
	}

	@Override
	public Set<String> getCurrentProfile() {
		return null;
	}

}
