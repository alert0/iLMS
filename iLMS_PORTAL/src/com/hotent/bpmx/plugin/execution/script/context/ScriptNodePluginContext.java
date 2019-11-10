package com.hotent.bpmx.plugin.execution.script.context;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.hotent.bpmx.api.constant.EventType;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.runtime.RunTimePlugin;
import com.hotent.bpmx.plugin.core.context.AbstractBpmExecutionPluginContext;
import com.hotent.bpmx.plugin.execution.script.def.ScriptNodePluginDef;
import com.hotent.bpmx.plugin.execution.script.plugin.ScriptNodePlugin;
import com.hotent.bpmx.plugin.task.userassign.context.PluginContextPluginTypeProcessor;
import com.jamesmurty.utils.XMLBuilder;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 * 脚本节点。
 * <pre> 
 * 构建组：x5-bpmx-plugin
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-4-24-下午2:55:44
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ScriptNodePluginContext extends AbstractBpmExecutionPluginContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5958682303600423597L;

	public List<EventType> getEventTypes() {
		List<EventType> list=new ArrayList<EventType>();
		list.add(EventType.AUTO_TASK_EVENT);
		list.add(EventType.START_POST_EVENT);
		list.add(EventType.END_POST_EVENT);
		return list;
	}

	public Class<? extends RunTimePlugin> getPluginClass() {
		return ScriptNodePlugin.class;
	}

	
	@Override
	public String getPluginXml() {
		
		ScriptNodePluginDef pluginDef=(ScriptNodePluginDef) this.getBpmPluginDef();
		try {
			XMLBuilder xmlBuilder = XMLBuilder.create("scriptNode")
					.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/execution/scriptNode");	
			
			xmlBuilder.cdata(pluginDef.getScript());

			return xmlBuilder.asString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public String getJson() {
		JsonConfig config=new JsonConfig();
		config.registerJsonBeanProcessor(this.getClass(),new PluginContextPluginTypeProcessor());
		JSON json=JSONSerializer.toJSON(this, config);
		return json.toString();
	}
	
	

	@Override
	protected BpmPluginDef parseJson(String pluginJson) {
		JSONObject jsonObject=JSONObject.fromObject(pluginJson);
		ScriptNodePluginDef def=new ScriptNodePluginDef();
		String script=jsonObject.getString("script");
		def.setScript(script);
		
		return def;
	}

	@Override
	protected BpmPluginDef parseElement(Element element) {
		ScriptNodePluginDef def=new ScriptNodePluginDef();
		String script=element.getTextContent();
		def.setScript(script);
		return def;
	}
	
//	public static void main(String[] args) throws TransformerException, ParserConfigurationException, FactoryConfigurationError {
//		ScriptNodePluginDef def=new ScriptNodePluginDef();
//		def.setScript("aaaa");
//		ScriptNodePluginContext context=new ScriptNodePluginContext();
//		context.setBpmPluginDef(def);
//		System.out.println(context.getJson());
//		
//		XMLBuilder xmlBuilder = XMLBuilder.create("scriptNode")
//				.a("xmlns", "http://www.jee-soft.cn/bpm/plugins/execution/scriptNode");	
//		
//		xmlBuilder.cdata(def.getScript());
//		
//		System.out.println(xmlBuilder.asString());
//	}

	@Override
	public String getTitle() {
		return "脚本";
	}

}
