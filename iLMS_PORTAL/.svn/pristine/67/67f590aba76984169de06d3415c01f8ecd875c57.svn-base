package com.hotent.bpmx.plugin.task.userassign.context;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.api.plugin.core.context.UserCalcPluginContext;

public class UserCalcContextProcessor implements JsonBeanProcessor {

	@Override
	public JSONObject processBean(Object obj, JsonConfig config) {
		if(!(obj instanceof UserCalcPluginContext)){  
			return new JSONObject(true);  
		}  
		UserCalcPluginContext context=(UserCalcPluginContext)obj;
		return JSONObject.fromObject(context.getBpmPluginDef(),config)
				.element("pluginType", ((PluginParse) context).getType())
				.element("description", context.getDescription());
	}

	

}
