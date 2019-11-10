package com.hotent.mini.controller.flow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.bpmx.api.def.BpmDefinitionAccessor;
import com.hotent.bpmx.api.model.process.def.BpmProcessDef;
import com.hotent.bpmx.api.model.process.def.BpmProcessDefExt;
import com.hotent.bpmx.api.model.process.nodedef.BpmNodeDef;
import com.hotent.bpmx.api.plugin.core.context.BpmPluginContext;
import com.hotent.bpmx.api.service.BpmDefinitionService;
import com.hotent.bpmx.core.engine.def.impl.handler.PluginsBpmDefXmlHandler;
import com.hotent.bpmx.persistence.model.DefaultBpmProcessDefExt;
import com.hotent.bpmx.plugin.execution.procnotify.context.ProcNotifyPluginContext;
import com.hotent.bpmx.plugin.task.reminders.context.RemindersPluginContext;
import com.hotent.sys.api.jms.MessageUtil;
import com.hotent.sys.api.jms.handler.JmsHandler;
import com.hotent.sys.api.jms.model.JmsVo;
/**
 * 
 * <pre> 
 * 描述：流程插件管理    
 * <br> 流程定义插件控制层
 * 构建组：x5-bpmx-platform
 * 作者:miao
 * 邮箱:miaojf@jee-soft.cn
 * 日期:2014-8-5
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/flow/plugins/")
public class PluginsController extends GenericController{
	@Resource
	BpmDefinitionService bpmDefinitionService;
	@Resource
	BpmDefinitionAccessor bpmDefinitionAccessor ;
	/**
	 * 编辑结束抄送
	 */
	@RequestMapping("procNotifyEdit")
	public ModelAndView procNotifyEdit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId = RequestUtil.getString(request, "defId");
		
		// 获取插件
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt= (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		
		ProcNotifyPluginContext procNotifyPluginContext = (ProcNotifyPluginContext) defExt.getBpmPluginContext(ProcNotifyPluginContext.class);
		String procNotifyJson = null;
		if(procNotifyPluginContext != null) {
			procNotifyJson = procNotifyPluginContext.getJson();
			procNotifyJson = procNotifyJson.replaceAll("\"", "\\\\\""); 
		}
		List<JmsHandler<JmsVo>> handlerList= MessageUtil.getHanlerList();
		
		return getAutoView()
				.addObject("defId",defId)
				.addObject("procNotifyJson", procNotifyJson)
				.addObject("handlerList",handlerList);
	}
	
	/**
	 * 保存结束抄送
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("procNotifySave")
	public void procNotifySave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		String defId = RequestUtil.getString(request, "defId");
		String procNotifyJson = RequestUtil.getString(request, "procNotifyJson");
		
		BpmProcessDef<BpmProcessDefExt> bpmProcessDefExt = bpmDefinitionAccessor.getBpmProcessDef(defId);
		DefaultBpmProcessDefExt defExt= (DefaultBpmProcessDefExt) bpmProcessDefExt.getProcessDefExt();
		try {  
			if(StringUtil.isNotEmpty(defId)){ 
				ProcNotifyPluginContext context = new ProcNotifyPluginContext();
				context.parse(procNotifyJson);
				List<BpmPluginContext> plugins = changeOnePluginContextForSave(defExt.getBpmPluginContexts(),context);
				
				PluginsBpmDefXmlHandler bpmDefXmlHandler = (PluginsBpmDefXmlHandler) AppUtil.getBean(PluginsBpmDefXmlHandler.class);
				bpmDefXmlHandler.saveNodeXml(defId, null, plugins);
				resultMsg="办结抄送添加成功！";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),"办结抄送设置失败！",e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 催办json
	 */
	@RequestMapping("remindersJson")
	@ResponseBody
	public Object remindersJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		
		BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
		RemindersPluginContext remindersPluginContext=nodeDef.getPluginContext(RemindersPluginContext.class);
		
		JSONObject json = new JSONObject();
		json.put("reminders",remindersPluginContext!= null?remindersPluginContext.getBpmPluginDef() : JSON.parse("{reminderList:[]}"));
		json.put("warnSetting",JSON.parse("[{name:'蓝色预警',color:'blue',level:51},{name:'黄色预警',color:'yellow',level:52},{name:'红色预警',color:'red',level:53}]"));
		return json;
	}
	
	/**
	 * 保存结束抄送
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("remindersSave")
	public void RemindersSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String defId = RequestUtil.getString(request, "defId");
		String nodeId = RequestUtil.getString(request, "nodeId");
		String remindersJson = RequestUtil.getString(request, "remindersJson");
		try {  
			BpmNodeDef nodeDef = bpmDefinitionAccessor.getBpmNodeDef(defId, nodeId);
			
			RemindersPluginContext context = new RemindersPluginContext();
			context.parse(remindersJson);
			List<BpmPluginContext> plugins = changeOnePluginContextForSave(nodeDef.getBpmPluginContexts(),context);
			
			PluginsBpmDefXmlHandler bpmDefXmlHandler = (PluginsBpmDefXmlHandler) AppUtil.getBean(PluginsBpmDefXmlHandler.class);
			bpmDefXmlHandler.saveNodeXml(defId, nodeId, plugins);
			response.getWriter().write("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(e.getMessage());
		}
	}
	
	/**
	 * 替换要保存的插件，
	 * @return List<BpmPluginContext>
	 */
	private List<BpmPluginContext> changeOnePluginContextForSave(List<BpmPluginContext> contexts,BpmPluginContext pluginContext){
		List<BpmPluginContext> bpmPluginContexts = new ArrayList<BpmPluginContext>();
		bpmPluginContexts.add(pluginContext);
		
		if(BeanUtils.isEmpty(contexts)) return bpmPluginContexts;
		
		for(BpmPluginContext context : contexts){
			if(!context.getClass().isAssignableFrom(pluginContext.getClass())){
				bpmPluginContexts.add(context);
			}
		}
		return bpmPluginContexts;
	}

}
