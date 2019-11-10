package com.hotent.bpmx.plugin.task.userassign.context;

import net.sf.json.processors.PropertyNameProcessor;

public class ProperNameProcessor implements PropertyNameProcessor {

	@Override
	public String processPropertyName(Class cls, String property) {
		if("calcPluginContextList".equals(property)){
			return "calcs";
		}
		return property;
	}

}
