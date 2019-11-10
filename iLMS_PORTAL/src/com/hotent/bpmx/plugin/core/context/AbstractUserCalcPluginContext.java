package com.hotent.bpmx.plugin.core.context;

import net.sf.json.JSONObject;

import org.w3c.dom.Element;

import com.hotent.base.core.util.string.StringUtil;
import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.LogicType;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.plugin.core.context.PluginParse;
import com.hotent.bpmx.api.plugin.core.context.UserCalcPluginContext;
import com.hotent.bpmx.api.plugin.core.def.BpmPluginDef;
import com.hotent.bpmx.api.plugin.core.def.BpmUserCalcPluginDef;
import com.hotent.bpmx.plugin.core.plugindef.AbstractUserCalcPluginDef;

/**
 * 用户运算逻辑抽象类。
 * <pre> 
 * 所有用户计算类都继承此类。
 * 构建组：x5-bpmx-plugin-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-21-下午10:00:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public abstract class AbstractUserCalcPluginContext  implements UserCalcPluginContext,PluginParse{
	private BpmPluginDef bpmPluginDef;

	@Override
	public BpmPluginDef getBpmPluginDef() {
		return bpmPluginDef;
	}

	public void setBpmPluginDef(BpmPluginDef bpmPluginDef) {
		this.bpmPluginDef = bpmPluginDef;
	}
	
	/**
	 * 解析插件定义。
	 * @param element
	 * @return BpmPluginDef
	 */
	protected abstract BpmPluginDef parseElement(Element element);

	/**
	 * 在父类设置逻辑类型和抽取类型。
	 */
	public BpmPluginDef parse(Element element) {
		String logicCal=element.getAttribute("logicCal");
		String extract=element.getAttribute("extract");

		BpmUserCalcPluginDef def=(BpmUserCalcPluginDef) parseElement(element);
		
		def.setExtract(ExtractType.fromKey(extract));
		def.setLogicCal(LogicType.fromKey(logicCal));
		
		setBpmPluginDef(def);
		
		return def;

	}
	
	@Override
	public String getType() {
		return StringUtil.lowerFirst(this.getClass().getSimpleName().replaceAll(BpmPluginContext.PLUGINCONTEXT, ""));
	}

	
	
	protected abstract BpmPluginDef parseJson(JSONObject pluginJson);
	
	
	
	@Override
	public void parse(String pluginDefJson) {
		JSONObject jsonObject=JSONObject.fromObject(pluginDefJson);
		AbstractUserCalcPluginDef bpmPluginDef=(AbstractUserCalcPluginDef) parseJson(jsonObject);
		
		String extract=jsonObject.getString("extract");
		String logicCal=jsonObject.getString("logicCal");
		
		bpmPluginDef.setExtract(ExtractType.fromKey(extract));
		bpmPluginDef.setLogicCal(LogicType.fromKey(logicCal));
		
		setBpmPluginDef(bpmPluginDef);
	}
	
	
	
	

	@Override
	public String getJson() {
		return "";
	}
	
}
