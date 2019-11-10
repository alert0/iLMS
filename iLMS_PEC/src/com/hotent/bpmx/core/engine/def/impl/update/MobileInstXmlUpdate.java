package com.hotent.bpmx.core.engine.def.impl.update;

import org.dom4j.Element;

import com.hotent.bpmx.core.engine.def.DefXmlUpdate;

/**
 * 手机实例表单更新。
 * @author ray
 *
 */
public class MobileInstXmlUpdate extends DefXmlUpdate {

	@Override
	public void update(Element oldEl, Element newEl) {
		String xParentPath="//ext:extProcess";
		String xPath="//ext:extProcess/ext:mobileInstForm";
		
		handCommon(oldEl, newEl, xParentPath, xPath);
	}

}
