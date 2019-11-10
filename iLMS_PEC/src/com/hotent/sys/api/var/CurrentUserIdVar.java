package com.hotent.sys.api.var;

import com.hotent.sys.util.ContextUtil;

public class CurrentUserIdVar implements IContextVar {

	@Override
	public String getTitle() {
		return "当前用户ID";
	}

	@Override
	public String getAlias() {
		return "curUserId";
	}

	@Override
	public String getValue() {
		return ContextUtil.getCurrentUserId();
	}

}
