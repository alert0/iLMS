package com.hotent.bpmx.core.engine.def.impl.handler;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.hotent.base.core.util.Dom4jUtil;
import com.hotent.bpmx.api.model.process.def.BpmDefinition;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.core.engine.def.AbstractBpmDefXmlHandler;
import com.hotent.bpmx.core.engine.def.DefXmlHandlerUtil;

/**
 * 服务节点通用保存类。
 * <pre> 
 * 1.传入插件的JSON，该JSON包含pluginType属性。
 * 2.根据插件属性取得插件。
 * 3.调用插件解析json活的插件定义。
 * 4.根据插件定义获取插件的xml。
 * 5.解析获取流程定义的XML。
 * 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-21-下午11:28:31
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Service
public class ServiceNodeBpmDefXmlHandler extends AbstractBpmDefXmlHandler<String>{
	
	@Resource
	private PluginContextContainer pluginHandlerContainer;

	@Override
	protected String getXml(String defId, String nodeId, String  json) {
		JSONObject jsonObj=JSONObject.fromObject(json);
		String pluginType=jsonObj.getString("pluginType");
		//根据插件类型获取插件。
		PluginParse ctx= pluginHandlerContainer.getPluginParse(pluginType);
		ctx.parse(json);
		String pluginXml=ctx.getPluginXml();
		
		//规定插件的命令控件
		BpmDefinition bpmDef= bpmDefinitionManager.getById(defId);
		String defXml=bpmDef.getBpmnXml();
		
		Document doc=Dom4jUtil.loadXml(defXml);
		Element root=doc.getRootElement();
		String url="http://www.jee-soft.cn/bpm/plugins/execution/"+ pluginType;
		root.addNamespace("service", url);
		
		String xPath="//ext:*[@bpmnElement='"+nodeId+"']/ext:extPlugins/service:" +pluginType ;
		String xParentPath="//ext:*[@bpmnElement='"+nodeId+"']/ext:extPlugins" ;
		
		DefXmlHandlerUtil.handXmlDom(root, pluginXml, xParentPath, xPath);
		
		root.remove(new Namespace("service", url));
		
		return root.asXML();
		
		
	}
	
}
