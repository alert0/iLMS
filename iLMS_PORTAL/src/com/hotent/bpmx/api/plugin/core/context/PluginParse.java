package com.hotent.bpmx.api.plugin.core.context;


public interface PluginParse {
	/**
	 * 根据插件定义构造插件xml
	 * @param bpmPluginDef
	 * @return String
	 */
	String getPluginXml();
	
	
	/**
	 * 解析插件定义。
	 * @param pluginDefJson
	 * @return 
	 * BpmPluginDef
	 */
	void parse(String pluginDefJson);
	
	/**
	 * 返回JSON 
	 * @return 
	 * String
	 */
	String getJson();
	
	
	/**
	 * 插件类型。
	 * @return 
	 * String
	 */
	String getType();
	
	
	
}
