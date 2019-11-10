package com.hotent.bpmx.api.plugin.core.def;

import com.hotent.bpmx.api.constant.ExtractType;
import com.hotent.bpmx.api.constant.LogicType;

public interface BpmUserCalcPluginDef extends BpmPluginDef{
	
	/**
	 * 获取抽取类型。
	 * @return 
	 * ExtractType
	 */
	ExtractType getExtract();
	
	
	/**
	 * 设置抽取类型。
	 * @param type 
	 * void
	 */
	void setExtract(ExtractType type);
	
	/**
	 * 逻辑类型。
	 * @return 
	 * LogicType
	 */
	LogicType getLogicCal();

	/**
	 * 设置逻辑类型
	 * @param logicType 
	 * void
	 */
	void setLogicCal(LogicType logicType);
	
	
	
	
	
	
}
