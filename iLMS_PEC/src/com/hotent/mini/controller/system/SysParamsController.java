package com.hotent.mini.controller.system;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.org.persistence.manager.SysParamsManager;
import com.hotent.org.persistence.model.SysParams;


/**
 * 
 * <pre> 
 * 描述：组织人员参数 控制器类
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cn
 * 日期:2016-10-31 14:50:48
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/sysParams/")
public class SysParamsController extends GenericController{
	@Resource
	SysParamsManager sysParamsManager;
	
	/**
	 * 组织人员参数列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse) throws Exception{
		QueryFilter queryFilter=getQueryFilter(request);
		PageList<SysParams> sysParamsList=(PageList<SysParams>)sysParamsManager.query(queryFilter);
		return new PageJson(sysParamsList);
	}
	
	
	
	/**
	 * 组织人员参数明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysParams getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return null;
		}
		SysParams sysParams=sysParamsManager.get(id);
		return sysParams;
	}
	
	/**
	 * 保存组织人员参数信息
	 * @param request
	 * @param response
	 * @param sysParams
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysParams sysParams) throws Exception{
		String resultMsg=null;
		String id=sysParams.getId();
		try {
			if(StringUtil.isEmpty(id)){
				SysParams isExist =  sysParamsManager.getByAlias(sysParams.getAlias());
				if(isExist!=null){
					resultMsg="参数key已经存在";
					writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
					return ;
				}
				sysParams.setId(UniqueIdUtil.getSuid());
				sysParamsManager.create(sysParams);
				resultMsg="添加组织人员参数成功";
			}else{
				sysParamsManager.update(sysParams);
				resultMsg="更新组织人员参数成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对组织人员参数操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除组织人员参数记录
	 * @param request
	 * @param response
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		try {
			String[] aryIds=RequestUtil.getStringAryByStr(request, "id");
			sysParamsManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除组织人员参数成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除组织人员参数失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 根据参数类型获取    
	 * @param type 1 用户属性 2 组织属性
	 * @return
	 */
	@RequestMapping("getByType")
	public @ResponseBody List<SysParams> getByType(String type){
		if(StringUtil.isEmpty(type)) return null;
		return sysParamsManager.getByType(type);
	}
}
