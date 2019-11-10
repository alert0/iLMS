package com.hotent.mini.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.persistence.manager.SubsystemManager;
import com.hotent.base.persistence.model.Subsystem;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
import com.hotent.mini.web.context.SubSystemUtil;
import com.hotent.org.api.model.IGroup;
import com.hotent.org.persistence.manager.OrgManager;
import com.hotent.org.persistence.model.Org;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.manager.SysIndexMyLayoutManager;
import com.hotent.portal.persistence.manager.SysLayoutSettingManager;
import com.hotent.portal.persistence.manager.UserSettingManager;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;
import com.hotent.portal.persistence.model.SysIndexMyLayout;
import com.hotent.portal.persistence.model.SysLayoutSetting;
import com.hotent.portal.persistence.model.UserSetting;
import com.hotent.portal.util.PortalUtil;
import com.hotent.sys.api.system.PropertyService;
import com.hotent.sys.persistence.manager.SysMessageManager;
import com.hotent.sys.util.ContextUtil;

@Controller
@RequestMapping("/main/")
public class MainController {
	@Resource
	SubsystemManager subsystemManager;
	@Resource
	OrgManager orgManager;
	@Resource
	PropertyService propertyService;
	@Resource
	UserSettingManager userSettingManager;
	@Resource
	SysMessageManager sysMessageManager;
	@Resource
	SysIndexMyLayoutManager sysIndexMylayoutService;
//	@Resource
//	BpmDefUserManager bpmDefUserManager;
	@Resource
	SysIndexLayoutManageManager sysIndexlayoutManageService;
	@Resource
	SysLayoutSettingManager sysLayoutSettingManager;

	@RequestMapping("home")
	public ModelAndView home(HttpServletRequest request,HttpServletResponse response){
		List<Subsystem> subsystemList= subsystemManager.getCuurentUserSystem();
		String mainViewName = "home";
		// 风格
		String indexName = PortalUtil.getCurrentUserSetting().getIndexName();
		try {
			if(("portal").equals(indexName)){
				request.getRequestDispatcher("/main/homePortal").forward(request, response);
				return null;
			}else if(("other".equals(indexName))){
				request.getRequestDispatcher("/main/homeOther").forward(request, response);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView mv = new  ModelAndView(mainViewName);
		if(BeanUtils.isEmpty(subsystemList)){
			mv.addObject("noRightsMsg", "你没有该应用任何资源权限，请联系管理员，或者切换其他用户登录！");
			return mv;
		}
		
		String systemId = SubSystemUtil.getSystemId(request);
		Subsystem currentSystem = null ;
		//获取当前系统
		if(StringUtil.isNotEmpty(systemId)){
			for (Subsystem system : subsystemList) {
				if(system.getId().equals(systemId)){
					currentSystem = system;
					break;
				}
			}
		}else{
			//获取默认系统
			currentSystem = subsystemManager.getDefaultSystem(ContextUtil.getCurrentUserId());
		}
		
		//没有之前使用的系统
		if(currentSystem == null){
			currentSystem = subsystemList.get(0);
		}
		SubSystemUtil.setSystemId(request, response,currentSystem.getId());
		
		IGroup group = ContextUtil.getCurrentGroup();
		List<Org> orgList = orgManager.getOrgListByUserId(ContextUtil.getCurrentUserId());
		portalMain(mv);
		//获取当前系统版本号，默认为5.0
		String sysVersion = propertyService.getByAlias("sys.version", "5.0");
		mv.addObject("subsystemList", subsystemList);
		mv.addObject("currentSystem", currentSystem);
		mv.addObject("currentOrg", group);
		mv.addObject("orgList", orgList);
		mv.addObject("sysVersion", sysVersion);
		return mv;
	}

	private void portalMain(ModelAndView mv){
		// 获得当前用户有权限的布局
//		List<String> indexManageIds = bpmDefUserManager.getAuthorizeIdsByUserMap(SysIndexLayoutManage.INDEX_MANAGE);
		List<SysIndexLayoutManage> layoutManageList = new ArrayList<SysIndexLayoutManage>();
//		for (String indexManageId : indexManageIds) {
//			SysIndexLayoutManage sysIndexLayoutManage = sysIndexlayoutManageService.getByIdAndType(indexManageId,SysIndexLayoutManage.TYPE_PC);
//			if(sysIndexLayoutManage != null){
//				layoutManageList.add(sysIndexLayoutManage);
//			}
//		}
		// 获取我的首页布局
		SysIndexMyLayout sysIndexMylayout =sysIndexMylayoutService.getByUser(ContextUtil.getCurrentUserId());
		if(sysIndexMylayout != null){
			SysIndexLayoutManage myLayout = new SysIndexLayoutManage();
			myLayout.setId(sysIndexMylayout.getId());
			myLayout.setName(SysIndexMyLayout.MY_LAYOUT_NAME);
			myLayout.setMemo(SysIndexMyLayout.MY_LAYOUT);
			layoutManageList.add(myLayout);
		}
		PortalUtil.setCurrentUserSetting(null);
		UserSetting userSetting = PortalUtil.getCurrentUserSetting();
		// 判断当前布局是不是属于授权以及我的布局中
		boolean flagUserSetting = true;
		for (SysIndexLayoutManage sysLayout : layoutManageList) {
			if(sysLayout.getId().equals(userSetting.getLayoutId())){
				flagUserSetting = false;
			}
		}
		SysLayoutSetting sysLayoutSetting = sysLayoutSettingManager.getByLayoutId(userSetting.getLayoutId());
		mv.addObject("layoutManageList", layoutManageList);
		mv.addObject("userSetting", userSetting);
		mv.addObject("sysLayoutSetting", sysLayoutSetting);
		mv.addObject("flagUserSetting", flagUserSetting);
	}
	@RequestMapping("changeSystem")
	public void changeSystem(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id = RequestUtil.getString(request, "id");
		SubSystemUtil.setSystemId(request, response,id);
		
		response.sendRedirect(request.getContextPath()+"/main/home");
	}
	
	@RequestMapping("changeOrg")
	public void changeOrg(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String id = RequestUtil.getString(request, "id");
		Org org = orgManager.get(id);
		ContextUtil.setCurrentOrg(org);
		
		response.sendRedirect(request.getContextPath()+"/main/home");
	}
	
}