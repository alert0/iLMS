package com.hotent.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.api.query.QueryOP;
import com.hotent.base.core.encrypt.Base64;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.org.api.service.IOrgService;
import com.hotent.portal.persistence.manager.SysIndexColumnManager;
import com.hotent.portal.persistence.manager.SysIndexLayoutManageManager;
import com.hotent.portal.persistence.manager.SysIndexLayoutManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexLayout;
import com.hotent.portal.persistence.model.SysIndexLayoutManage;
/**
 *<pre>
 * 对象功能:布局管理 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:hugh
 * 创建时间:2015-03-18 15:40:13
 *</pre>
 */
@Controller
@RequestMapping("/portal/sysIndexLayoutManage/sysIndexLayoutManage/")
public class SysIndexLayoutManageController extends GenericController
{
	@Resource
	private SysIndexLayoutManageManager sysIndexLayoutManageService;
	
	@Resource
	private SysIndexLayoutManager sysIndexLayoutService;
	
	@Resource
	private SysIndexColumnManager sysIndexColumnService;
//	@Resource
//	private IOrgService sysOrgService;
//	@Resource
//	BpmDefUserManager bpmDefUserManager;
//	/**
//	 * 添加或更新布局管理。
//	 * @param request
//	 * @param response
//	 * @param sysIndexLayoutManage 添加或更新的实体
//	 * @param bindResult
//	 * @param viewName
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("save")
//	@Action(description="添加或更新布局管理")
//	public void save(HttpServletRequest request, HttpServletResponse response,SysIndexLayoutManage sysIndexLayoutManage) throws Exception
//	{
//		String resultMsg=null;		
//		try{
//			if(sysIndexLayoutManage.getId()==null||sysIndexLayoutManage.getId()==0){
//				sysIndexLayoutManage.setId(UniqueIdUtil.genId());
//				sysIndexLayoutManageService.add(sysIndexLayoutManage);
//				resultMsg=getText("添加","布局管理");
//			}else{
//			    sysIndexLayoutManageService.update(sysIndexLayoutManage);
//				resultMsg=getText("更新","布局管理");
//			}
//			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
//		}catch(Exception e){
//			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
//		}
//	}
//	
//	
	/**
	 * 取得布局管理分页列表
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
		String orgId = RequestUtil.getString(request, "orgId");
		queryFilter.addFilter("ORG_ID", orgId, QueryOP.EQUAL);
		PageList<SysIndexLayoutManage> sysIndexLayoutManageList=(PageList<SysIndexLayoutManage>)sysIndexLayoutManageService.query(queryFilter);
		return new PageJson(sysIndexLayoutManageList);
	}
 	/**
 	 * 删除布局管理
 	 * @param request
 	 * @param response
 	 * @throws Exception
 	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResultMessage message = null;
		try{
			String[] aryIds =RequestUtil.getStringAryByStr(request, "id");
			sysIndexLayoutManageService.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除布局管理成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.FAIL, "删除失败" + ex.getMessage());
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 根据ID获取内容
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("changDefault")
	public void changDefault(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ResultMessage message=null;
		String id=RequestUtil.getString(request, "id");
		String action = RequestUtil.getString(request, "action");
		String orgId = RequestUtil.getString(request, "orgId");
		Short layoutType = RequestUtil.getShort(request, "layoutType");
		try {
			SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.get(id);
			if("set".equals(action)){
				sysIndexLayoutManageService.cancelOrgIsDef(orgId,layoutType);
				sysIndexLayoutManage.setIsDef(SysIndexLayoutManage.SET_DEF);
				message=new ResultMessage(ResultMessage.SUCCESS, "设置默认成功");
			}else{
				sysIndexLayoutManage.setIsDef(SysIndexLayoutManage.CANCEL_DEF);
				message=new ResultMessage(ResultMessage.SUCCESS, "取消默认成功");
			}
			sysIndexLayoutManageService.save(sysIndexLayoutManage,1);
		} catch (Exception e) {
			e.printStackTrace();
			if("set".equals(action)){
				message=new ResultMessage(ResultMessage.FAIL, "设置默认失败");
			}else{
				message=new ResultMessage(ResultMessage.FAIL, "取消默认失败");
			}
		}
		writeResultMessage(response.getWriter(), message);
	}
	/**
	 * 设计我的首页布局
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("design")
	public ModelAndView design(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id =  RequestUtil.getString(request, "id");
		String orgId = RequestUtil.getString(request, "orgId");
		//首页布局
		List<SysIndexLayout> layoutList = sysIndexLayoutService.getAll();
		DefaultQueryFilter filter = (DefaultQueryFilter) getQueryFilter(request);
		// 不限制取值
		DefaultPage page = (DefaultPage) filter.getPage();
		page.setLimit(Integer.MAX_VALUE);
		filter.setPage(page);
		Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request);
		filter.addFilter("IS_PUBLIC", SysIndexColumn.TYPE_PC, QueryOP.EQUAL);
		//首页栏目，取出来需要解析
		List<SysIndexColumn>  columnList = sysIndexColumnService.getHashRightColumnList(filter,params,true,SysIndexColumn.TYPE_PC);
		//获取展示的布局
		Map<String,List<SysIndexColumn>> columnMap = sysIndexColumnService.getColumnMap(columnList);
		//获取当前的布局
		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.getLayoutList(id,columnList,SysIndexLayoutManage.TYPE_PC);	
//		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.get(id);
		/*if(BeanUtils.isNotEmpty(sysIndexLayoutManage) && BeanUtils.isNotIncZeroEmpty(sysIndexLayoutManage.getOrgId())){
			SysOrg sysOrg =sysOrgService.getById(sysIndexLayoutManage.getOrgId());
			sysIndexLayoutManage.setOrgName(sysOrg.getOrgName());
		}*/
		
		// 解码   columnMap 在sysIndexColumnManager.getColumnMap 中解码
		for (SysIndexLayout sysIndexLayout : layoutList) {
			sysIndexLayout.setTemplateHtml(Base64.getFromBase64(sysIndexLayout.getTemplateHtml()));
		}
		
		return getAutoView().addObject("layoutList",layoutList)
				.addObject("columnMap",columnMap)
				.addObject("sysIndexLayoutManage", sysIndexLayoutManage)
				.addObject("orgId", orgId);
	}
	/**
	 * 设计我的首页布局
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("designMobile")
	public ModelAndView designMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id =  RequestUtil.getString(request, "id");
		String orgId = RequestUtil.getString(request, "orgId");
		//首页布局
		List<SysIndexLayout> layoutList = sysIndexLayoutService.getAll();
		DefaultQueryFilter filter = (DefaultQueryFilter) getQueryFilter(request);
		// 不限制取值
		DefaultPage page = (DefaultPage) filter.getPage();
		page.setLimit(Integer.MAX_VALUE);
		filter.setPage(page);
		filter.addFilter("IS_PUBLIC", SysIndexColumn.TYPE_MOBILE, QueryOP.EQUAL);
		Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request);
		// 首页栏目，true 取出来是已经解析了
		List<SysIndexColumn>  columnList = sysIndexColumnService.getHashRightColumnList(filter,params,true,SysIndexColumn.TYPE_MOBILE);
		//获取展示的布局
		Map<String,List<SysIndexColumn>> columnMap = sysIndexColumnService.getColumnMap(columnList);
		//获取当前的布局
		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.getLayoutList(id,columnList,SysIndexLayoutManage.TYPE_MOBILE);	
//		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.get(id);
		/*if(BeanUtils.isNotEmpty(sysIndexLayoutManage) && BeanUtils.isNotIncZeroEmpty(sysIndexLayoutManage.getOrgId())){
			SysOrg sysOrg =sysOrgService.getById(sysIndexLayoutManage.getOrgId());
			sysIndexLayoutManage.setOrgName(sysOrg.getOrgName());
		}*/
		
		// 解码   columnMap 在sysIndexColumnManager.getColumnMap 中解码
		for (SysIndexLayout sysIndexLayout : layoutList) {
			sysIndexLayout.setTemplateHtml(Base64.getFromBase64(sysIndexLayout.getTemplateHtml()));
		}
		return getAutoView().addObject("layoutList",layoutList)
				.addObject("columnMap",columnMap)
				.addObject("sysIndexLayoutManage", sysIndexLayoutManage)
				.addObject("orgId", orgId);
	}
	
	/**
	 * 保存首页布局
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("saveLayout")
	public void saveLayout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id =  RequestUtil.getString(request, "id",null);
		String name =  RequestUtil.getString(request, "name");
		String memo =  RequestUtil.getString(request, "memo");
		Short isDef =  RequestUtil.getShort(request, "isDef");
		String orgId =  RequestUtil.getString(request, "orgId");
		String html =  RequestUtil.getString(request, "html");
		String designHtml =  RequestUtil.getString(request, "designHtml");
		Short layoutType =  RequestUtil.getShort(request, "layoutType");
		
		ResultMessage resultObj = null;
		
		
			
		try {
			// 判断别名是否存在。
				int type = 0;
				SysIndexLayoutManage sysIndexLayoutManage = new SysIndexLayoutManage();
				if (BeanUtils.isEmpty(id)) {
					Boolean isExist = sysIndexLayoutManageService.isExistName(name);
					if (isExist) {
						String msg = "布局名称：[" + name + "]已存在";
						resultObj = new ResultMessage(ResultMessage.FAIL, msg);
						response.getWriter().print(resultObj);
						return;
					}
					if(isDef.equals(SysIndexLayoutManage.SET_DEF)){
						SysIndexLayoutManage byOrgIdAndLayoutType = sysIndexLayoutManageService.getByOrgIdAndLayoutType(orgId, layoutType);
						if(BeanUtils.isNotEmpty(byOrgIdAndLayoutType)){
							String layoutTypeName = "PC端";
							if(layoutType.equals(SysIndexLayoutManage.TYPE_MOBILE)){
								layoutTypeName = "手机端";
							}
							String msg = layoutTypeName+"默认布局已存在";
							resultObj = new ResultMessage(ResultMessage.FAIL, msg);
							response.getWriter().print(resultObj);
							return;
						}
					}
					id = UniqueIdUtil.getSuid();
					sysIndexLayoutManage.setId(id);
				} else {
					type = 1;
					sysIndexLayoutManage = sysIndexLayoutManageService.get(id);
				}
				sysIndexLayoutManage.setName(name);
				sysIndexLayoutManage.setMemo(memo);
				sysIndexLayoutManage.setIsDef(isDef);
				sysIndexLayoutManage.setOrgId(orgId);
				sysIndexLayoutManage.setDesignHtml(designHtml);
				sysIndexLayoutManage.setTemplateHtml(html);
				sysIndexLayoutManage.setLayoutType(layoutType);
				sysIndexLayoutManageService.save(sysIndexLayoutManage,type);
	
			resultObj = new ResultMessage(ResultMessage.SUCCESS,id.toString());	
		} catch (Exception e) {
			resultObj = new ResultMessage(ResultMessage.FAIL, e.getMessage());	
		}
		 response.getWriter().print(resultObj);
	}

//	/**
//	 * 取得布局管理分页列表
//	 * @param request
//	 * @param response
//	 * @param page
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("list")
//	@Action(description="查看布局管理分页列表")
//	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
//	{	
//		QueryFilter filter =new QueryFilter(request, "sysIndexLayoutManageItem");
//		boolean isSuperAdmin = ContextUtil.isSuperAdmin();
//		if(!isSuperAdmin){	//不是超级管理员 filter  //TODO  保留接口
//		    List<Long> orgIds =	orgSubUserService.getByCurUser(ServiceUtil.getCurrentUser());
//			filter.addFilter("orgIds", StringUtils.join(orgIds,","));
//		}
//		List<SysIndexLayoutManage> list=sysIndexLayoutManageService.getAll(filter);
//		for (SysIndexLayoutManage sysIndexLayoutManage : list) {
//			if(BeanUtils.isNotIncZeroEmpty(sysIndexLayoutManage.getOrgId()) ){
//				SysOrg sysOrg =sysOrgService.getById(sysIndexLayoutManage.getOrgId());
//				sysIndexLayoutManage.setOrgName(sysOrg.getOrgName());
//			}
//		}
//		SysOrgTactic sysOrgTactic = sysOrgTacticService.getOrgTactic();
//		return this.getAutoView()
//				.addObject("objType",SysObjRights.RIGHT_TYPE_INDEX_MANAGE)
//				.addObject("isSuperAdmin",isSuperAdmin)
//				.addObject("sysIndexLayoutManageList",list)
//				.addObject("orgTactic",sysOrgTactic.getOrgTactic());
//	}
//	
//	
//	/**
//	 * 删除布局管理
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("del")
//	@Action(description="删除布局管理")
//	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		String preUrl= RequestUtil.getPrePage(request);
//		ResultMessage message=null;
//		try{
//			Long[] lAryId =RequestUtil.getLongAryByStr(request, "id");
//			sysIndexLayoutManageService.delByIds(lAryId);
//			message=new ResultMessage(ResultMessage.Success, "删除布局管理成功!");
//		}catch(Exception ex){
//			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
//		}
//		addMessage(message, request);
//		response.sendRedirect(preUrl);
//	}
//	
//	/**
//	 * 	编辑布局管理
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("edit")
//	@Action(description="编辑布局管理")
//	public ModelAndView edit(HttpServletRequest request) throws Exception
//	{
//		Long id=RequestUtil.getLong(request,"id",0L);
//		String returnUrl=RequestUtil.getPrePage(request);
//		SysIndexLayoutManage sysIndexLayoutManage=sysIndexLayoutManageService.getById(id);
//		
//		return getAutoView().addObject("sysIndexLayoutManage",sysIndexLayoutManage)
//							.addObject("returnUrl",returnUrl);
//	}
//
//	/**
//	 * 取得布局管理明细
//	 * @param request   
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("get")
//	@Action(description="查看布局管理明细")
//	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		Long id=RequestUtil.getLong(request,"id");
//		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.getById(id);	
//		return getAutoView().addObject("sysIndexLayoutManage", sysIndexLayoutManage);
//	}
//	
//	/**
//	 * 设计我的首页布局
//	 * @param request   
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("design")
//	@Action(description="设计我的首页布局")
//	public ModelAndView design(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Long id =  RequestUtil.getLong(request, "id");
//		//首页布局
//		List<SysIndexLayout> layoutList = sysIndexLayoutService.getAll();
//		QueryFilter filter = new QueryFilter(request,false);
//		filter.addFilter("id",null);
//		filter.setPageBean(null);
//		Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request);
//		//首页栏目，取出来需要解析
//		List<SysIndexColumn>  columnList = sysIndexColumnService.getHashRightColumnList(filter,params,true);
//		//获取展示的布局
//		Map<String,List<SysIndexColumn>> columnMap = sysIndexColumnService.getColumnMap(columnList);
//		//获取当前的布局
//		SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.getLayoutList(id,columnList);	
//		if(BeanUtils.isNotEmpty(sysIndexLayoutManage) && BeanUtils.isNotIncZeroEmpty(sysIndexLayoutManage.getOrgId())){
//			SysOrg sysOrg =sysOrgService.getById(sysIndexLayoutManage.getOrgId());
//			sysIndexLayoutManage.setOrgName(sysOrg.getOrgName());
//		}
//		return getAutoView().addObject("layoutList",layoutList)
//				.addObject("columnMap",columnMap)
//				.addObject("sysIndexLayoutManage", sysIndexLayoutManage);
//	}
//	
//
//	/**
//	 * 保存首页布局
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("saveLayout")
//	@Action(description="保存首页布局")
//	public void saveLayout(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Long id =  RequestUtil.getLong(request, "id",null);
//		String name =  RequestUtil.getString(request, "name");
//		String memo =  RequestUtil.getString(request, "memo");
//		Short isDef =  RequestUtil.getShort(request, "isDef");
//		String html =  RequestUtil.getString(request, "html");
//		String designHtml =  RequestUtil.getString(request, "designHtml");
//		
//		ResultMessage resultObj = null;
//		
//		
//			
//		try {
//				int type = 0;
//				SysIndexLayoutManage sysIndexLayoutManage = new SysIndexLayoutManage();
//				if (BeanUtils.isEmpty(id)) {
//					id = UniqueIdUtil.genId();
//					sysIndexLayoutManage.setId(id);
//				} else {
//					type = 1;
//					sysIndexLayoutManage = sysIndexLayoutManageService.getById(id);
//				}
//				if(! ContextUtil.isSuperAdmin()){//把自己的组织设置进去
//					Long companyId  =ContextUtil.getCurrentCompanyId();
//					sysIndexLayoutManage.setOrgId(companyId);
//				}
//				sysIndexLayoutManage.setName(name);
//				sysIndexLayoutManage.setMemo(memo);
//				sysIndexLayoutManage.setIsDef(isDef);
//				sysIndexLayoutManage.setDesignHtml(designHtml);
//				sysIndexLayoutManage.setTemplateHtml(html);
//				sysIndexLayoutManageService.save(sysIndexLayoutManage,type);
//	
//			resultObj = new ResultMessage(ResultMessage.Success,id.toString());	
//		} catch (Exception e) {
//			resultObj = new ResultMessage(ResultMessage.Fail, e.getMessage());	
//		}
//		 response.getWriter().print(resultObj);
//	}
//	
	/**
	 * 选择首页布局模版
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dialog")
	public ModelAndView dialog(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
//		QueryFilter filter =getQueryFilter(request);
	//	Map<String,Object>  params  =  RequestUtil.getParameterValueMap(request);
		//首页栏目，取出来需要解析
	//	List<SysIndexColumn>  columnList = sysIndexColumnService.getHashRightColumnList(filter,params,true);
//		List<SysIndexLayoutManage> list=sysIndexLayoutManageManager.getList(filter);
		List<SysIndexLayoutManage> list= new ArrayList<SysIndexLayoutManage>();
//		for (SysIndexLayoutManage sysIndexLayoutManage : list) {
//			sysIndexLayoutManage.setDesignHtml(sysIndexColumnService.parserDesignHtml(sysIndexLayoutManage.getDesignHtml(), columnList));
//		}
		return this.getAutoView()
				.addObject("sysIndexLayoutManageList",list);
	}
	
//	
//	/**
//	 * 保存组织
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("saveOrg")
//	@Action(description = "保存组织")
//	public void saveOrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String resultMsg = null;
//		Long id = RequestUtil.getLong(request, "id");
//		Long orgId = RequestUtil.getLong(request, "orgId",null);
//		try {
//			SysIndexLayoutManage  sysIndexLayoutManage = sysIndexLayoutManageService.getById(id);
//			sysIndexLayoutManage.setOrgId(orgId);
//			sysIndexLayoutManageService.update(sysIndexLayoutManage);
//			resultMsg = getText("保存组织成功");
//			writeResultMessage(response.getWriter(), resultMsg,
//					ResultMessage.Success);
//		} catch (Exception e) {
//			writeResultMessage(response.getWriter(),
//					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
//		}
//	}
	
	/**
	 * 获取授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
//	@RequestMapping("getRights")
//	public @ResponseBody JSONArray getRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String id = RequestUtil.getString(request, "id");
//		if (StringUtil.isEmpty(id)) {
//			return null;
//		}
//		return bpmDefUserManager.getRights(id,SysIndexLayoutManage.INDEX_MANAGE);
//	}
	
	
	/**
	 * 保存授权信息
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
//	@RequestMapping("saveRights")
//	public void saveRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ResultMessage message = null;
//		try { 
//			String id = RequestUtil.getString(request, "id");
//			String ownerNameJson = RequestUtil.getString(request, "rightsData");
//			bpmDefUserManager.saveRights(id, SysIndexLayoutManage.INDEX_MANAGE, ownerNameJson);
//			message = new ResultMessage(ResultMessage.SUCCESS,"权限保存成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			message = new ResultMessage(ResultMessage.FAIL, "权限保存失败");
//		}
//		writeResultMessage(response.getWriter(), message);
//	}
	/**
	 * 根据ID获取内容
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getLayoutDef")
	public void getLayoutDef(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String orgId=RequestUtil.getString(request, "orgId");
		Short layoutType=RequestUtil.getShort(request, "layoutType");
		int isMaster = ResultMessage.FAIL;
		if (StringUtil.isNotEmpty(orgId)) {
			SysIndexLayoutManage sysIndexLayoutManage = sysIndexLayoutManageService.getByOrgIdAndLayoutType(orgId,layoutType);
			if(BeanUtils.isNotEmpty(sysIndexLayoutManage)){
				isMaster = ResultMessage.SUCCESS;
			}
		}
		writeResultMessage(response.getWriter(),"", isMaster);
	}
}
