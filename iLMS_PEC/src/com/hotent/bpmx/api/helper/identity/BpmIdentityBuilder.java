package com.hotent.bpmx.api.helper.identity;

import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.model.identity.BpmIdentity;

public interface BpmIdentityBuilder {
	public BpmIdentity buildUser(String id,String name);
	public BpmIdentity buildOrg(String id,String name);
	public void setExtractInfo(BpmIdentity bpmIdentity,ExtractType extractType);
}
