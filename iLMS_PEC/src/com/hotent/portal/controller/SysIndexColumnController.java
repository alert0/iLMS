package com.hotent.portal.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.ThreadMsgUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
//import com.hotent.bpmx.persistence.manager.BpmDefUserManager;
//import com.hotent.org.api.service.IOrgService;
import com.hotent.portal.persistence.manager.SysIndexColumnManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre>
 * 对象功能:首页栏目 控制器类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:hugh
 * 创建时间:2015-03-18 11:22:46
 * </pre>
 */
@Controller
@RequestMapping("/portal/sysIndexColumn/sysIndexColumn/")
public class SysIndexColumnController extends GenericController {
	@Resource
	private SysIndexColumnManager sysIndexColumnService;
//	@Resource
//	private IOrgService sysOrgService;
//	@Resource
//	private BpmDefUserManager bpmDefUserManager;
	/**
	 * 列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<SysIndexColumn> roleList = (PageList<SysIndexColumn>) sysIndexColumnService.query(queryFilter);
		return new PageJson(roleList);
	}
	
	/**
	 * 明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody SysIndexColumn getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		if (BeanUtils.isEmpty(id)) {
			return null;
		}
		SysIndexColumn sysIndexColumn = sysIndexColumnService.get(id);
		return sysIndexColumn;
	}
	
	
	
	/**
	 * 批量删除记录
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try { 
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			sysIndexColumnService.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS,ThreadMsgUtil.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			message = new ResultMessage(ResultMessage.FAIL, "删除栏目失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	

	/**
	 * 添加或更新首页栏目。
	 * 
	 * @param request
	 * @param response
	 * @param sysIndexColumn
	 *            添加或更新的实体
	 * @param bindResult
	 * @param viewName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SysIndexColumn sysIndexColumn) throws Exception {
		String resultMsg = null;
		try {
			String alias = sysIndexColumn.getAlias();
			// 判断别名是否存在。
			Boolean isExist = sysIndexColumnService.isExistAlias(sysIndexColumn.getAlias(), sysIndexColumn.getId());
			if (isExist) {
				resultMsg = "栏目别名：[" + alias + "]已存在";
				writeResultMessage(response.getWriter(), resultMsg,
						ResultMessage.FAIL);
				return;
			}
			if (!ContextUtil.getCurrentUser().isAdmin()) {// 把自己的组织设置进去
				String orgId = ContextUtil.getCurrentGroupId();
				if (BeanUtils.isNotEmpty(orgId))
					sysIndexColumn.setOrgId(orgId);
			}
			if (StringUtil.isZeroEmpty(sysIndexColumn.getId())) {
				sysIndexColumn.setId(UniqueIdUtil.getSuid());
				sysIndexColumn.setCreateTime(new Date());
				sysIndexColumnService.create(sysIndexColumn);
				resultMsg = "添加首页栏目成功";
			} else {
				sysIndexColumn.setUpdateTime(new Date());
				sysIndexColumnService.update(sysIndexColumn);
				resultMsg = "更新首页栏目成功";
			}
			writeResultMessage(response.getWriter(), resultMsg,
					ResultMessage.SUCCESS);
		} catch (Exception e) {
			writeResultMessage(response.getWriter(),
					resultMsg + "," + e.getMessage(), ResultMessage.FAIL);
		}
	}
	
	
	/**
	 * 获取授权信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ModelAndView
	 */
//	@RequestMapping("getColumnRights")
//	public @ResponseBody JSONArray getColumnRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String id = RequestUtil.getString(request, "id");
//		if (StringUtil.isEmpty(id)) {
//			return null;
//		}
//		return bpmDefUserManager.getRights(id,SysIndexColumn.INDEX_COLUMN);
//	}
	
	
	/**
	 * 保存授权信息
	 * @param request
	 * @param response
	 * @throws Exception void
	 * @exception
	 */
//	@RequestMapping("saveColumnRights")
//	public void saveColumnRights(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ResultMessage message = null;
//		try { 
//			String id = RequestUtil.getString(request, "id");
//			String ownerNameJson = RequestUtil.getString(request, "rightsData");
//			bpmDefUserManager.saveRights(id, SysIndexColumn.INDEX_COLUMN, ownerNameJson);
//			message = new ResultMessage(ResultMessage.SUCCESS,"权限保存成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//			message = new ResultMessage(ResultMessage.FAIL, "权限保存失败");
//		}
//		writeResultMessage(response.getWriter(), message);
//	}
	
//
//	/**
//	 * 添加或更新首页栏目。
//	 * 
//	 * @param request
//	 * @param response
//	 * @param sysIndexColumn
//	 *            添加或更新的实体
//	 * @param bindResult
//	 * @param viewName
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("saveOrg")
//	@Action(description = "添加或更新首页栏目")
//	public void saveOrg(HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String resultMsg = null;
//		Long id = RequestUtil.getLong(request, "id");
//		Long orgId = RequestUtil.getLong(request, "orgId", null);
//		try {
//			SysIndexColumn sysIndexColumn = sysIndexColumnService.getById(id);
//			sysIndexColumn.setOrgId(orgId);
//			sysIndexColumnService.update(sysIndexColumn);
//			resultMsg = getText("保存组织成功");
//			writeResultMessage(response.getWriter(), resultMsg,
//					ResultMessage.Success);
//		} catch (Exception e) {
//			writeResultMessage(response.getWriter(),
//					resultMsg + "," + e.getMessage(), ResultMessage.Fail);
//		}
//	}
//
//	/**
//	 * 取得首页栏目分页列表
//	 * 
//	 * @param request
//	 * @param response
//	 * @param page
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("list")
//	@Action(description = "查看首页栏目分页列表")
//	public ModelAndView list(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		QueryFilter filter = new QueryFilter(request, "sysIndexColumnItem");
//		boolean isSuperAdmin = ContextUtil.isSuperAdmin();
//		List<SysIndexColumn> hashRightList = sysIndexColumnService
//				.getHashRightColumnList(filter, null, false);
//		for (SysIndexColumn sysIndexColumn : hashRightList) {
//			if (BeanUtils.isNotIncZeroEmpty(sysIndexColumn.getOrgId())) {
//				SysOrg sysOrg = sysOrgService
//						.getById(sysIndexColumn.getOrgId());
//				if(sysOrg!=null)
//				sysIndexColumn.setOrgName(sysOrg.getOrgName());
//			}
//		}
//		SysOrgTactic sysOrgTactic = sysOrgTacticService.getOrgTactic();
//
//		return this.getAutoView()
//				.addObject("objType", SysObjRights.RIGHT_TYPE_INDEX_COLUMN)
//				.addObject("sysIndexColumnList", hashRightList)
//				.addObject("isSuperAdmin", isSuperAdmin)
//				.addObject("orgTactic", sysOrgTactic.getOrgTactic());
//	}
//
//	/**
//	 * 删除首页栏目
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("del")
//	@Action(description = "删除首页栏目")
//	public void del(HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String preUrl = RequestUtil.getPrePage(request);
//		ResultMessage message = null;
//		try {
//			Long[] lAryId = RequestUtil.getLongAryByStr(request, "id");
//			sysIndexColumnService.delByIds(lAryId);
//			message = new ResultMessage(ResultMessage.Success, "删除首页栏目成功!");
//		} catch (Exception ex) {
//			message = new ResultMessage(ResultMessage.Fail, "删除失败"
//					+ ex.getMessage());
//		}
//		addMessage(message, request);
//		response.sendRedirect(preUrl);
//	}
//
//	/**
//	 * 编辑首页栏目
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("edit")
//	@Action(description = "编辑首页栏目")
//	public ModelAndView edit(HttpServletRequest request) throws Exception {
//		Long id = RequestUtil.getLong(request, "id", 0L);
//		String returnUrl = RequestUtil.getPrePage(request);
//		SysIndexColumn sysIndexColumn = sysIndexColumnService.getById(id);
//		if (BeanUtils.isNotEmpty(sysIndexColumn)) {
//			if (BeanUtils.isNotIncZeroEmpty(sysIndexColumn.getOrgId()) ) {
//				SysOrg sysOrg = sysOrgService
//						.getById(sysIndexColumn.getOrgId());
//				sysIndexColumn.setOrgName(sysOrg.getOrgName());
//			}
//		}
//		List<GlobalType> catalogList = globalTypeService.getByCatKey(
//				GlobalType.CAT_INDEX_COLUMN, false);
//
//		return getAutoView().addObject("sysIndexColumn", sysIndexColumn)
//				.addObject("catalogList", catalogList)
//				.addObject("returnUrl", returnUrl);
//	}
//
//	/**
//	 * 取得首页栏目明细
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("get")
//	@Action(description = "查看首页栏目明细")
//	public ModelAndView get(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//		Long id = RequestUtil.getLong(request, "id");
//		SysIndexColumn sysIndexColumn = sysIndexColumnService.getById(id);
//		return getAutoView().addObject("sysIndexColumn", sysIndexColumn);
//	}

	/**
	 * 预览模版
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getTemp")
	public ModelAndView getTemp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		Short isPublic = RequestUtil.getShort(request, "isPublic");
		String html = "";
		try {
			SysIndexColumn sysIndexColumn = sysIndexColumnService.get(id);
			if(BeanUtils.isNotEmpty(sysIndexColumn)){
				html = "<div template-alias=\"" + sysIndexColumn.getAlias()
						+ "\"></div>";
				if(sysIndexColumn.getShowEffect()==1){//走马灯效果
					html = "<marquee><div template-alias=\"" + sysIndexColumn.getAlias()
							+ "\"></div></marquee>";
				}else if(sysIndexColumn.getShowEffect()==2){//幻灯片效果
					html = "<div class='widget-layerslider' template-alias=\"" + sysIndexColumn.getAlias()
							+ "\"></div>";
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnGetTemp";
		if(isPublic.equals(SysIndexColumn.TYPE_MOBILE)){
			url = "/portal/sysIndexColumn/sysIndexColumn/sysIndexColumnGetMobileTemp";
		}
		return getAssignView(url).addObject("html", html);
	}

//	/**
//	 * 取得首页栏目明细
//	 * 
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
	@RequestMapping("getData")
	@ResponseBody
	public String getData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		Map<String, Object> params = getParameterValueMap(request);
		String data = "";
		try {
			data = sysIndexColumnService.getHtmlByColumnAlias(alias, params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return data;
	}

	private Map<String, Object> getParameterValueMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("__ctx", request.getContextPath());
		String params = RequestUtil.getString(request, "params");
		if (BeanUtils.isEmpty(params))
			return map;
		JSONObject json = JSONObject.fromObject(params);
		Iterator<?> it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = json.get(key);
			map.put(key, value);
		}
		return map;
	}

//	/**
//	 * 初始化模板信息
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("init")
//	@Action(description = "初始化模板", detail = "初始化模板")
//	public void init(HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String preUrl = RequestUtil.getPrePage(request);
//		ResultMessage message = null;
//		try {
//			sysIndexColumnService.initIndexColumn();
//			message = new ResultMessage(ResultMessage.Success, "初始化模板成功!");
//		} catch (Exception ex) {
//			message = new ResultMessage(ResultMessage.Fail, "初始化表模板失败:"
//					+ ex.getMessage());
//		}
//		addMessage(message, request);
//		response.sendRedirect(preUrl);
//	}

}
