package com.hotent.bpmx.plugin.task.userassign.context;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import com.hotent.bpmx.api.plugin.core.context.PluginContext;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;

/**
 * 序列化JSON是添加pluginType属性。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-23-下午2:10:07
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class PluginContextPluginTypeProcessor implements JsonBeanProcessor {

	@Override
	public JSONObject processBean(Object obj, JsonConfig config) {
		if(!(obj instanceof PluginParse)){  
			return new JSONObject(true);  
		}  
		PluginContext context=(PluginContext)obj;
		
		return JSONObject.fromObject(context.getBpmPluginDef(),config)
				.element("pluginType", ((PluginParse) context).getType());
		
	}

}
