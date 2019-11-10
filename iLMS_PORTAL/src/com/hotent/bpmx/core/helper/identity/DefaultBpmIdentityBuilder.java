package com.hotent.bpmx.core.helper.identity;

import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.helper.identity.BpmIdentityBuilder;
import com.hotent.bpmx.api.model.identity.BpmIdentity;
import com.hotent.bpmx.core.model.identity.DefaultBpmIdentity;

public class DefaultBpmIdentityBuilder implements BpmIdentityBuilder {
	public BpmIdentity buildUser(String id,String name){
		DefaultBpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setId(id);
		bpmIdentity.setName(name);
		bpmIdentity.setType(DefaultBpmIdentity.TYPE_USER);
		return bpmIdentity;
	}
	public BpmIdentity buildOrg(String id,String name){
		DefaultBpmIdentity bpmIdentity = new DefaultBpmIdentity();
		bpmIdentity.setId(id);
		bpmIdentity.setName(name);
		bpmIdentity.setType(DefaultBpmIdentity.TYPE_GROUP);
		return bpmIdentity;
	}
	@Override
	public void setExtractInfo(BpmIdentity bpmIdentity,ExtractType extractType){
		//如果该bpmIdentity没有设置抽取类型，或者抽取类型为不抽取，则设置，否则跳过。
		if(bpmIdentity!=null && 
				(bpmIdentity.getExtractType()==null 
					|| bpmIdentity.getExtractType().equals(ExtractType.EXACT_NOEXACT))){
			DefaultBpmIdentity newBpmIdentity = new DefaultBpmIdentity();
			newBpmIdentity.setId(bpmIdentity.getId());
			newBpmIdentity.setName(bpmIdentity.getName());
			newBpmIdentity.setType(bpmIdentity.getType());
			newBpmIdentity.setGroupType(bpmIdentity.getGroupType());
			newBpmIdentity.setExtractType(extractType);
			bpmIdentity = newBpmIdentity;			
		}
	}

}
