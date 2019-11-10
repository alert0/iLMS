package com.hotent.bpmx.core.engine.def.impl.update;

import org.dom4j.Element;

import com.hotent.bpmx.core.engine.def.DefXmlUpdate;

/**
 * 流程实例插件更新。
 * <pre> 
 * 构建组：x5-bpmx-core
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-4-上午8:58:49
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class PluginsDefXmlUpdate extends DefXmlUpdate {

	@Override
	public void update(Element oldEl, Element newEl) {
		String xParentPath="//ext:extProcess";
		String xPath="//ext:extProcess/ext:extPlugins";
		
		handCommon(oldEl, newEl, xParentPath, xPath);

	}

}
