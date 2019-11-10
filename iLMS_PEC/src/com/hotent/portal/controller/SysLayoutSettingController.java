package com.hotent.portal.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.portal.persistence.manager.SysLayoutSettingManager;
import com.hotent.portal.persistence.model.SysLayoutSetting;


/**
 * 
 * <pre> 
 * 描述：布局设置 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-07 16:18:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysLayoutSetting/sysLayoutSetting")
public class SysLayoutSettingController extends GenericController{
	@Resource
	SysLayoutSettingManager sysLayoutSettingManager;
	
	/**
	 * 布局设置列表(分页条件查询)数据
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
		PageList<SysLayoutSetting> sysLayoutSettingList=(PageList<SysLayoutSetting>)sysLayoutSettingManager.query(queryFilter);
		return new PageJson(sysLayoutSettingList);
	}
	
	
	
	/**
	 * 布局设置明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysLayoutSetting getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String id=RequestUtil.getString(request, "id");
		if(StringUtil.isEmpty(id)){
			return new SysLayoutSetting();
		}
		SysLayoutSetting sysLayoutSetting=sysLayoutSettingManager.get(id);
		return sysLayoutSetting;
	}
	
	/**
	 * 保存布局设置信息
	 * @param request
	 * @param response
	 * @param sysLayoutSetting
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response,@RequestBody SysLayoutSetting sysLayoutSetting) throws Exception{
		String resultMsg=null;
		try {
			SysLayoutSetting setting = sysLayoutSettingManager.getUnique("LAYOUT_ID_", sysLayoutSetting.getLayoutId());
			if(setting == null){
				sysLayoutSetting.setId(UniqueIdUtil.getSuid());
				sysLayoutSettingManager.create(sysLayoutSetting);
				resultMsg="添加布局设置成功";
			}else{
				BeanUtils.copyNotNullProperties(setting, sysLayoutSetting);
				sysLayoutSettingManager.update(setting);
				resultMsg="更新布局设置成功";
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
		} catch (Exception e) {
			resultMsg="对布局设置操作失败";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
		}
	}
	
	/**
	 * 批量删除布局设置记录
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
			sysLayoutSettingManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除布局设置成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除布局设置失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	@RequestMapping("getByLayout")
	public @ResponseBody SysLayoutSetting getByLayout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String layoutId=RequestUtil.getString(request, "layoutId");
		if(StringUtil.isEmpty(layoutId)){
			return new SysLayoutSetting();
		}
		SysLayoutSetting sysLayoutSetting=sysLayoutSettingManager.getByLayoutId(layoutId);
		return sysLayoutSetting;
	}
	
}
