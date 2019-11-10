package com.hotent.mini.controller.system;


import java.util.Date;
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
import com.hotent.sys.persistence.manager.SysPropertiesManager;
import com.hotent.sys.persistence.model.SysProperties;


/**
 * 
 * <pre> 
 * 描述：系统属性 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:ray
 * 邮箱:zhangyg@jee-soft.cn
 * 日期:2016-07-28 09:29:59
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/sys/properties/sysProperties")
public class SysPropertiesController extends GenericController{
	@Resource
	SysPropertiesManager sysPropertiesManager;
	
	/**
	 * 系统属性列表(分页条件查询)数据
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
		PageList<SysProperties> sysPropertiesList=(PageList<SysProperties>)sysPropertiesManager.query(queryFilter);
		return new PageJson(sysPropertiesList);
	}
	
	
	
	/**
	 * 系统属性明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysProperties getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		SysProperties sysProperties =new SysProperties();
		List<String> groups= sysPropertiesManager.getGroups();
		if(StringUtil.isEmpty(id)){
			sysProperties.setCategorys(groups);
			return sysProperties;
		}
		sysProperties=sysPropertiesManager.get(id);
		String val=sysProperties.getRealVal();
		sysProperties.setValue(val);
		sysProperties.setCategorys(groups);
		return sysProperties;
	}
	
	/**
	 * 保存系统属性信息
	 * @param request
	 * @param response
	 * @param sysProperties
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysProperties sysProperties) throws Exception{
		String resultMsg=null;
		
		boolean isExist=sysPropertiesManager.isExist(sysProperties);
		if(isExist){
			resultMsg="别名系统中已存在!";
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.FAIL);
			return;
		}
		
		String id=sysProperties.getId();
		
		sysProperties.setValByEncrypt();
		
		try {
			if(StringUtil.isEmpty(id)){
				sysProperties.setId(UniqueIdUtil.getSuid());
				sysProperties.setCreateTime(new Date());
				sysPropertiesManager.create(sysProperties);
				resultMsg="添加系统属性成功";
			}else{
				sysPropertiesManager.update(sysProperties);
				resultMsg="更新系统属性成功";
			}
			sysPropertiesManager.reloadProperty();
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对系统属性操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除系统属性记录
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
			sysPropertiesManager.removeByIds(aryIds);
			sysPropertiesManager.reloadProperty();
			message=new ResultMessage(ResultMessage.SUCCESS, "删除系统属性成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除系统属性失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
}
