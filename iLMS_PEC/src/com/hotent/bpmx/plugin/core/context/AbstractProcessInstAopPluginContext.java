/**
 * 描述：TODO
 * 包名：com.hotent.bpmx.plugin.core.context
 * 文件名：AbstractProcessInstAopPluginContext.java
 * 作者：win-mailto:chensx@jee-soft.cn
 * 日期2014-4-3-下午7:00:52
 *  2014广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.bpmx.plugin.core.context;

import com.hotent.bpmx.api.plugin.core.context.ProcessInstAopPluginContext;
import com.hotent.bpmx.api.plugin.core.def.ProcessInstAopPluginDef;

/**
 * <pre> 
 * 描述：TODO
 * 构建组：x5-bpmx-plugin-core
 * 作者：Winston Yan
 * 邮箱：yancm@jee-soft.cn
 * 日期：2014-4-3-下午7:00:52
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public abstract class AbstractProcessInstAopPluginContext implements ProcessInstAopPluginContext{
	private ProcessInstAopPluginDef processInstAopPluginDef;

	public ProcessInstAopPluginDef getProcessInstAopPluginDef() {
		return processInstAopPluginDef;
	}

	public void setProcessInstAopPluginDef(
			ProcessInstAopPluginDef processInstAopPluginDef) {
		this.processInstAopPluginDef = processInstAopPluginDef;
	}
	
}
