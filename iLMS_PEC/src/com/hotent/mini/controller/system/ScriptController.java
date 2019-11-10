package com.hotent.mini.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.api.IdGenerator;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.persistence.manager.ScriptManager;
import com.hotent.sys.persistence.model.Script;
import com.hotent.sys.persistence.model.TreeEntity;

/**
 * 
 * <pre> 
 * 描述：sys_script管理
 * 构建组：x5-bpmx-platform
 * 作者:helh
 * 邮箱:helh@jee-soft.cn
 * 日期:2014-05-08 14:47:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/script/")
public class ScriptController extends GenericController{

	@Resource
	private ScriptManager scriptManager;
	@Resource
	private IdGenerator idGenerator;
	@Resource
	GroovyScriptEngine groovyScriptEngine;
	/**
	 * 系统脚本列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<Script> fileList=(PageList<Script>)scriptManager.query(queryFilter);
		return new PageJson(fileList);
	}
	
	/**
	 * 编辑@名称：系统脚本信息页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("scriptEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		Script script = null;
		if(StringUtil.isNotEmpty(id)){
			script = scriptManager.get(id);
		}else{
			script = new Script();
		}
		List<String> categoryList = scriptManager.getDistinctCategory();
		return getAutoView().addObject("script", script)
				.addObject("categoryList", categoryList)
				.addObject("returnUrl", preUrl);
	}
	
	/**
	 * 系统脚本明细页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("scriptGet")
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl = RequestUtil.getPrePage(request);
		String id = RequestUtil.getString(request, "id");
		Script script = null;
		if(StringUtil.isNotEmpty(id)){
			script = scriptManager.get(id);
		}
		return getAutoView().addObject("script", script).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 保存系统脚本信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param script
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,Script script) throws Exception{
		String resultMsg = null;
		String id = script.getId();
		try {
			if(StringUtil.isEmpty(id)){
				script.setId(idGenerator.getSuid());
				scriptManager.create(script);
				resultMsg = "添加系统脚本成功";
			}else{
				scriptManager.update(script);
				resultMsg = "更新系统脚本成功";
			}
			
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除系统脚本(逻辑删除)
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			scriptManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除@名称：SYS_SCRIPT【系统脚本】成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除@名称：SYS_SCRIPT【系统脚本】失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("scriptDialog")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<String> categoryList = scriptManager.getDistinctCategory();
		return getAutoView().addObject("categoryList", categoryList);
	}
	
	@RequestMapping("scriptList")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<String> categoryList = scriptManager.getDistinctCategory();
		return getAutoView().addObject("categoryList", categoryList);
	}
	@RequestMapping("executeScript")
	@ResponseBody
	public Object executeScript(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String script = RequestUtil.getString(request, "script");
		String key = RequestUtil.getString(request, "key");
		Map<String, Object> map=new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();
		try {
			Object obj= groovyScriptEngine.executeObject(script,map);
			jsonObject.put("val", obj);
		} catch (Exception e) {
			jsonObject.put("val", "出错"+e.getMessage());
		}
		jsonObject.put("key", key);
		jsonObject.put("script", script);
		return jsonObject;
	}
	
	@RequestMapping("getScriptTreeData")
	@ResponseBody
	public List<TreeEntity> getScriptTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<TreeEntity> treeList = getTree();
		if (BeanUtils.isEmpty(treeList))
			treeList = new ArrayList<TreeEntity>();
		return treeList;
	}
	
	private List<TreeEntity> getTree() {
		List<TreeEntity> listResult = new ArrayList<TreeEntity>();
		List<String> categoryList = scriptManager.getDistinctCategory();
		for (String category : categoryList) {
			TreeEntity entity = new TreeEntity(category,category,category,"",TreeEntity.ICON_COMORG);
			listResult.add(entity);
			 
		}
		List<Script> list= scriptManager.getAll();
		for (Script script : list) {
			TreeEntity entity = new TreeEntity(script.getName(),script.getScript(),script.getId(),script.getCategory(),TreeEntity.ICON_COMORG);
			listResult.add(entity);
		}
		return listResult;
	}
}
