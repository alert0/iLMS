package com.hotent.bpmx.api.service;

import java.util.List;
import java.util.Map;

/**
 * 流程导入导出接口。
 * <pre> 
 * 构建组：x5-bpmx-api
 * 作者：ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2014-7-20-上午9:42:07
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public interface BpmDefTransform {
	
	/**
	 * 流程导出接口。
	 * @param defList		流程定义ID列表。
	 * @return  String		导出流程成字符串。
	 */
	Map<String,String> exportDef(List<String> defList);
	
	/**
	 * 导入流程。 
	 * @param 解压后文件的位置。
	 * void
	 */
	void importDef(String unZipFilePath);

}
