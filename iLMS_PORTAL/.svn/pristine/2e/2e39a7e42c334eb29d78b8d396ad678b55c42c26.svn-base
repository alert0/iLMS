package com.hotent.mini.controller.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.engine.script.IUserScript;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.bpmx.persistence.manager.BpmSelectorDefManager;
import com.hotent.bpmx.persistence.model.BpmSelectorDef;
import com.hotent.form.persistence.manager.CustomDialogManager;
import com.hotent.form.persistence.model.CustomDialog;
import com.hotent.sys.api.model.IConditionScript;
import com.hotent.sys.persistence.manager.ConditionScriptManager;
import com.hotent.sys.persistence.model.ConditionScript;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * 描述：sys_multi_script管理
 * 构建组：x5-bpmx-platform
 * 作者:helh
 * 邮箱:helh@jee-soft.cn
 * 日期:2014-05-08 15:33:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/conditionScript/")
public class ConditionScriptController extends GenericController {
	@Resource
	private ConditionScriptManager conditionScriptManager;
	

	/**
	 * 条件脚本列表(分页条件查询)数据 TODO方法名称描述
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		String type=RequestUtil.getString(request, "type");
		queryFilter.addFilter("type_", type, QueryOP.EQUAL);
		PageList<ConditionScript> fileList = (PageList<ConditionScript>) conditionScriptManager.query(queryFilter);
		return new PageJson(fileList);
	}
	
	@RequestMapping("conditionScriptList")
	public ModelAndView conditionScriptList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		ModelAndView mv= getAutoView();
		mv.setViewName("/system/conditionScript/conditionScriptList");
		mv.addObject("type", "1");
		return mv;
	}
	
	@RequestMapping("userScriptList")
	public ModelAndView userScriptList(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("/system/conditionScript/conditionScriptList");
		mv.addObject("type", "2");
		return mv;
	}
	
	
	

	/**
	 * 编辑@名称：条件脚本信息页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("conditionScriptEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		//type=1条件脚本，type=2人员脚本
		Integer type = RequestUtil.getInt(request, "type");
		ConditionScript conditionScript = null;
		if (StringUtil.isNotEmpty(id)) {
			conditionScript = conditionScriptManager.get(id);
			type=conditionScript.getType();
		} else {
			conditionScript = new ConditionScript();
		}
		List<Class> implClasses=null;
		if(type==1){
			implClasses=AppUtil.getImplClass(IConditionScript.class);
		}else if(type==2){
			implClasses=AppUtil.getImplClass(IUserScript.class);
		}
		
		List<BpmSelectorDef> bpmSelectorDefs = AppUtil.getBean(BpmSelectorDefManager.class).getAll();//控件
		List<CustomDialog> customDialogs = AppUtil.getBean(CustomDialogManager.class).getAll();
		List<ParaCtOtion> controlBindList =new ArrayList<ParaCtOtion>();
		String optionXml="";
		if(bpmSelectorDefs.size()>0)
		{
		    optionXml+="<optgroup label=\"选择器组合控件\">";
		    for (BpmSelectorDef selector : bpmSelectorDefs) {
			    optionXml+="<option value=\"selector:"+selector.getAlias()+"\">"+selector.getName()+"</option>";
			    ParaCtOtion option=new ParaCtOtion();
			    option.id="selector:"+selector.getAlias();
			    option.option=selector.getGroupField();
			    controlBindList.add(option);
		    }
		    optionXml+="</optgroup>";
		}
		
		if(customDialogs.size()>0)
		{
		    optionXml+="<optgroup label=\"选择自定义对话框\">";
		    for (CustomDialog cus : customDialogs) {
			    optionXml+="<option value=\"cusdg:"+cus.getAlias()+"\">"+cus.getName()+"</option>";
			    
			    ParaCtOtion option=new ParaCtOtion();
			    option.id="cusdg:"+cus.getAlias();
			    if(StringUtil.isNotEmpty(cus.getResultfield())){
			    	option.option=cus.getResultfield().replace("\"field\":", "\"name\":").replace("\"comment\":", "\"key\":");
			    }
			    controlBindList.add(option);
		    }
		    optionXml+="</optgroup>";
		}
        if(optionXml !=""){
        	optionXml="<option value=''>请选择</option>"+optionXml;
        }
		//选择器的Json
		JSONArray  controlBindSourceJson= JSONArray .fromObject(controlBindList);
		String jsonStr= controlBindSourceJson.toString();
		return getAutoView().addObject("conditionScript", conditionScript)
				.addObject("implClasses", implClasses).addObject("optionXml", optionXml)
				.addObject("bpmSelectorDefs", bpmSelectorDefs).addObject("controlBindSourceJson", jsonStr)
				.addObject("returnUrl", preUrl);
	}

	
	public class ParaCtOtion
	{
		protected String id; //控件类型ID
		protected String option; //控件类型可以绑定的字段列表
	 
		public void setId(String id) {
			this.id = id;
		}
 		public String getId() {
			return this.id;
		}
 		public void setOption(String option) {
			this.option = option;
		}
 		public String getOption() {
			return this.option;
		}
	}
	/**
	 * 条件脚本明细页面 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("conditionScriptGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		ConditionScript conditionScript = null;
		if (StringUtil.isNotEmpty(id)) {
			conditionScript = conditionScriptManager.get(id);
		}
		return getAutoView().addObject("conditionScript", conditionScript).addObject("returnUrl", preUrl);
	}

	/**
	 * 根据属性获取对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ConditionScript
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("getObject")
	@ResponseBody
	public ConditionScript getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		ConditionScript ConditionScript = null;
		if (StringUtil.isNotEmpty(id)) {
			ConditionScript = conditionScriptManager.get(id);
		}
		return ConditionScript;
	}

	/**
	 * 保存别名脚本信息 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @param aliasScript
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, ConditionScript conditionScript) throws Exception {
		String resultMsg = null;
		String id = conditionScript.getId();
		try {
			if (StringUtil.isEmpty(id)) {
				conditionScript.setId(UniqueIdUtil.getSuid());
				conditionScriptManager.create(conditionScript);
				resultMsg = "添加脚本成功";
			} else {
				conditionScriptManager.update(conditionScript);
				resultMsg = "更新脚本成功";
			}

			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(), resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
	}

	/**
	 * 批量删除条件脚本(逻辑删除) TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			conditionScriptManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除@名称：SYS_MULTI_SCRIPT【脚本】成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除@名称：SYS_MULTI_SCRIPT【脚本】失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	/**
	 * 通过类名获取类的所有方法
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMethodsByClassName")
	@ResponseBody
	public String getMethodsByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String className = RequestUtil.getString(request, "className");
		String id = RequestUtil.getString(request, "id");
		Integer type=RequestUtil.getInt(request, "type");
		JSONObject jobject = new JSONObject();
		try {
			ConditionScript conditionScript = null;
			if (StringUtil.isNotEmpty(id)) {
				conditionScript = conditionScriptManager.get(id);
				type=conditionScript.getType();
			}
			JSONArray jarray = conditionScriptManager.getMethodsByClassName(className, conditionScript,type);
			jobject.accumulate("result", true).accumulate("methods", jarray);
		} catch (Exception ex) {
			jobject.accumulate("result", false).accumulate("message", ex.getMessage());
		}
		return jobject.toString();
	}

}
