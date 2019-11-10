package com.hotent.sys.service.impl.permission;

import java.util.Map;
import java.util.Set;

import com.hotent.sys.api.permission.IPermission;

public class EveryOnePermission implements IPermission {

	@Override
	public String getTitle() {
		return "所有人";
	}

	@Override
	public String getType() {
		return "everyone";
	}

	

	@Override
	public Set<String> getCurrentProfile() {
		return null;
	}

	@Override
	public boolean hasRight(String json, Map<String, Set<String>> currentMap) {
		return true;
	}

}
