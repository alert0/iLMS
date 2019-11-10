package com.hotent.bpmx.core.engine.def.impl.update;

import org.dom4j.Element;

import com.hotent.bpmx.core.engine.def.DefXmlUpdate;

public class GlobalFormDefXmlUpdate extends DefXmlUpdate {

	@Override
	public void update(Element oldEl, Element newEl) {
		String xParentPath="//ext:extProcess";
		String xPath="//ext:extProcess/ext:globalForm";
		
		handCommon(oldEl, newEl, xParentPath, xPath);
		

	}

}
