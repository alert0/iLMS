package com.hotent.sys.api.var;

import com.hotent.sys.util.ContextUtil;

public class CurrentUserAccountVar implements IContextVar {

	@Override
	public String getTitle() {
		return "当前用户账号";
	}

	@Override
	public String getAlias() {
		return "curUserAccount";
	}

	@Override
	public String getValue() {
		return ContextUtil.getCurrentUser().getAccount();
	}

}
