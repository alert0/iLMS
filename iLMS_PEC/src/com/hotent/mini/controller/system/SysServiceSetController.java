package com.hotent.mini.controller.system;

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

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.mini.controller.system.manager.SysServiceSetManager;
//import com.hotent.service.api.model.InvokeResult;
//import com.hotent.service.api.model.ServiceBean;
//import com.hotent.service.api.parse.ServiceParse;
import com.hotent.sys.persistence.model.SysServiceParam;
import com.hotent.sys.persistence.model.SysServiceSet;

import net.sf.json.JSONObject;

/**
 * 
 * <pre> 
 * 描述：通用服务调用设置表管理
 * 构建组：x5-bpmx-platform
 * 作者:heyifan
 * 邮箱:heyf@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/system/serviceSet/")
public class SysServiceSetController extends GenericController{
	@Resource
	SysServiceSetManager sysServiceSetManager;
//	@Resource
//	ServiceParse serviceParse;
	
	/**
	 * 通用服务调用设置表列表(分页条件查询)数据
	 * TODO方法名称描述
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<SysServiceSet> sysServiceSetList=(PageList<SysServiceSet>)sysServiceSetManager.query(queryFilter);
		return new PageJson(sysServiceSetList);
	}
	
	/**
	 * 解析WebService
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("serviceInfo")
//	@ResponseBody
//	public Object serviceInfo(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
//		String url = RequestUtil.getString(request, "url");
//		ServiceBean serviceBean = serviceParse.parse(url);
//		return serviceBean;
//	}
	
	/**
	 * 调用服务
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("invokeService")
//	@ResponseBody
//	public Object invokeService(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
//		String serviceSetId = RequestUtil.getString(request, "serviceSetId");
//		String params = RequestUtil.getString(request, "params");
//		InvokeResult result = sysServiceSetManager.invokeService(serviceSetId, params);
//		JSONObject jsonObject = new JSONObject();
//		if(result.isFault()){
//			jsonObject.accumulate("type", 1)
//					  .accumulate("message", result.getException().getMessage());
//			return jsonObject;
//		}
//		if(result.isVoid()){
//			jsonObject.accumulate("type", 2);
//		}
//		else{
//			jsonObject.accumulate("type", 3)
//	  		  		  .accumulate("message", result.getJson());
//		}
//		return jsonObject;
//	}
	
	/**
	 * 编辑通用服务调用设置表信息页面
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("serviceSetEdit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String preUrl= RequestUtil.getPrePage(request);
		String id=RequestUtil.getString(request, "id");
		return getAutoView().addObject("serviceSetId", id).addObject("returnUrl", preUrl);
	}
	
	/**
	 * 通用服务调用设置表明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("serviceSetGet")
	@ResponseBody
	public Object get(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		String alias = RequestUtil.getString(request, "alias");
		Map<String,Object> map=new HashMap<String, Object>(); 
		SysServiceSet sysServiceSet=null;
		if(StringUtil.isNotEmpty(id)){
			sysServiceSet=sysServiceSetManager.get(id);
		}if(StringUtil.isNotEmpty(alias)){
			sysServiceSet=sysServiceSetManager.getByAlias(alias);
		}
		map.put("result", true);
		map.put("serviceSet", sysServiceSet);
		return map;
	}
	
	/**
	 * 获取参数列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getParams")
	@ResponseBody
	public Object getParams(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "serviceSetId");
		Map<String,Object> map=new HashMap<String, Object>(); 
		if(StringUtil.isNotEmpty(id)){
			List<SysServiceParam> list = sysServiceSetManager.getParams(id);
			map.put("result", true);
			map.put("params", list);
		}
		return map;
	}
	
	/**
	 * 保存通用服务调用设置表信息
	 * TODO方法名称描述
	 * @param request
	 * @param response
	 * @param sysServiceSet
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("save")
	@ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String service = RequestUtil.getString(request, "service");
		JSONObject jobject = new JSONObject();
		try{
			SysServiceSet serviceSet = GsonUtil.toBean(service, SysServiceSet.class);
			if(BeanUtils.isNotEmpty(serviceSet.getId())){
				sysServiceSetManager.update(serviceSet);
			}
			else{
				serviceSet.setId(UniqueIdUtil.getSuid());
				sysServiceSetManager.create(serviceSet);
			}
			return jobject.accumulate("result", true);
		}
		catch(Exception e){
			e.printStackTrace();
			return jobject.accumulate("result", false).accumulate("message", e.getMessage());
		}
	}
	
	/**
	 * 批量删除通用服务调用设置表记录
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
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			sysServiceSetManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除通用服务调用设置表成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除通用服务调用设置表失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
