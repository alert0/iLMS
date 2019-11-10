package com.hotent.mini.controller.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryOP;
//import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.excel.util.ExcelUtil;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.FreeMarkerDebugger;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.MapUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestContext;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageListUtil;
import com.hotent.base.db.mybatis.domain.PageResult;
//import com.hotent.form.persistence.manager.BpmFormTemplateManager;
//import com.hotent.form.persistence.model.BpmFormTemplate;
import com.hotent.sys.persistence.manager.QueryMetafieldManager;
import com.hotent.sys.persistence.manager.QuerySqldefManager;
import com.hotent.sys.persistence.manager.QueryViewManager;
import com.hotent.sys.persistence.manager.SysDataSourceManager;
import com.hotent.sys.persistence.model.QuerySqldef;
import com.hotent.sys.persistence.model.QueryView;

import freemarker.template.TemplateException;

/**
 * 
 * <pre>
 * 描述：自定义SQL视图 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:Aschs
 * 邮箱:6322665042@qq.com
 * 日期:2016-06-17 17:46:52
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/system/query/queryView")
public class QueryViewController extends GenericController {
	@Resource
	QueryViewManager queryViewManager;
//	@Resource
//	BpmFormTemplateManager bpmFormTemplateManager;
	@Resource
	QuerySqldefManager querySqldefManager;
//	@Resource
//	FreemarkEngine freemarkEngine;
	@Resource
	SysDataSourceManager sysDataSourceManager;
	@Resource
	CommonDao commonDao;
	@Resource
	QueryMetafieldManager queryMetafieldManager;
	
	/**
	 * 自定义SQL视图列表(分页条件查询)数据
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 */
	@RequestMapping("listJson")
	public @ResponseBody PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		DefaultQueryFilter queryFilter = (DefaultQueryFilter) getQueryFilter(request);
		queryFilter.addFilter("SQL_ALIAS_", RequestUtil.getString(request, "SQL_ALIAS_"), QueryOP.EQUAL);
		PageList<QueryView> queryViewList = (PageList<QueryView>) queryViewManager.query(queryFilter);
		return new PageJson(queryViewList);
	}

	/**
	 * 自定义SQL视图明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody QueryView getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String sqlAlias = RequestUtil.getString(request, "sqlAlias");
		String alias = RequestUtil.getString(request, "alias");
		QueryView queryView = null;
		if (StringUtil.isNotEmpty(id)) {
			queryView = queryViewManager.get(id);
		}
		if (StringUtil.isNotEmpty(sqlAlias)&&StringUtil.isNotEmpty(alias)) {
			queryView = queryViewManager.getBySqlAliasAndAlias(sqlAlias, alias);
		}
		return queryView;
	}

	/**
	 * 保存自定义SQL视图信息
	 * 
	 * @param request
	 * @param response
	 * @param queryView
	 * @throws Exception
	 *             void
	 * @exception
	 */
//	@RequestMapping("save")
//	public void save(HttpServletRequest request, HttpServletResponse response, @RequestBody QueryView queryView) throws Exception {
//		String resultMsg = null;
//		String id = queryView.getId();
//		try {
//			if (queryView.getRebuildTemp() == (short) 1) {
//				handleTemplate(queryView);
//			}
//			if (StringUtil.isEmpty(id)) {
//				queryView.setId(UniqueIdUtil.getSuid());
//				queryViewManager.create(queryView);
//				resultMsg = "添加自定义SQL视图成功";
//			} else {
//				queryViewManager.update(queryView);
//				resultMsg = "更新自定义SQL视图成功";
//			}
//			writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMsg = "对自定义SQL视图操作失败";
//			writeResultMessage(response.getWriter(), resultMsg, e.getMessage(), ResultMessage.FAIL);
//		}
//	}

	/**
	 * 批量删除自定义SQL视图记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			queryViewManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除自定义SQL视图成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除自定义SQL视图失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sqlAlias = RequestUtil.getString(request, "sqlAlias");
		QuerySqldef sqldef= querySqldefManager.getByAlias(sqlAlias);
		sqldef.setMetafields(queryMetafieldManager.getBySqlId(sqldef.getId()));
		ModelAndView mv = getAutoView();
		mv.setViewName("/system/query/queryViewEdit");
		mv.addObject("sqldef", sqldef);
		mv.addObject("comVarList", AppUtil.getBean("queryViewComVarList"));
		return mv;
	}
	
	@RequestMapping("{sqlAlias}/{alias}")
	public ModelAndView toShowPage(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "sqlAlias") String sqlAlias, @PathVariable(value = "alias") String alias) throws Exception {
		Map params = RequestUtil.getParameterValueMap(request, false, true);
		ModelAndView mv = new ModelAndView();
		QueryView queryView = queryViewManager.getBySqlAliasAndAlias(sqlAlias, alias);
		mv.addObject("queryView", queryView);
		mv.setViewName("/system/query/queryViewShow");
		mv.addObject("postData", JSONObject.fromObject(params).toString());
		return mv;
	}

	@RequestMapping("data_{sqlAlias}/{alias}")
	@ResponseBody
	public Map<String, Object> getShowData(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "sqlAlias") String sqlAlias, @PathVariable(value = "alias") String alias) throws Exception {
		List list = getShowData(sqlAlias, alias, request, false);
		if (list instanceof PageList) {
			return PageListUtil.toJqGridMap(((PageList) list));
		}else{
			return MapUtil.buildMap("rows", list);
		}
	}

	/**
	 * 获取所有模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
//	@RequestMapping("getTempList")
//	public @ResponseBody Object getTempList(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		return bpmFormTemplateManager.getQueryDataTemplate();
//	}

	/**
	 * 导出数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("export")
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sqlAlias = RequestUtil.getString(request, "sqlAlias");
		String alias = RequestUtil.getString(request, "alias");
		QueryView queryView = queryViewManager.getBySqlAliasAndAlias(sqlAlias, alias);

		String getType = RequestUtil.getString(request, "getType", "page");
		try {
			List list = getShowData(sqlAlias, alias, request, getType.equals("all"));

			// 拼装exprotMaps
			Map<String, String> exportMaps = new LinkedHashMap<String, String>();
			JSONArray showJA = JSONArray.fromObject(queryView.getShows());
			JSONObject showJO = JsonUtil.arrayToObject(showJA, "fieldName");
			for (String str : RequestUtil.getStringAryByStr(request, "expField")) {
				exportMaps.put(str, showJO.getJSONObject(str).getString("fieldDesc"));
			}
			HSSFWorkbook book = ExcelUtil.exportExcel(queryView.getName(), 24, exportMaps, list);
			ExcelUtil.downloadExcel(book, queryView.getName(), response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping("isExist")
	public @ResponseBody boolean isExist(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String alias = RequestUtil.getString(request, "key");
		String sqlAlias = RequestUtil.getString(request, "sqlAlias");
		if (StringUtil.isNotEmpty(alias)) {
			QueryView temp = queryViewManager.getBySqlAliasAndAlias(sqlAlias, alias);
			if (temp == null) {
				return false;
			}
			return !temp.getId().equals(id);// 如果id跟数据库中用这个别名的对象一样就返回false，反之true
		}
		return false;
	}
	
	// 下面的方法都不是controller了
	/**
	 * 处理模板
	 * 
	 * @param queryView
	 *            void
	 * @throws IOException
	 * @throws TemplateException
	 * @exception
	 * @since 1.0.0
	 */
//	private void handleTemplate(QueryView queryView) throws Exception {
//		String ctx = RequestContext.getHttpServletRequest().getContextPath();
//		BpmFormTemplate template = bpmFormTemplateManager.getByTemplateAlias(queryView.getTemplateAlias());
//
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//
//		JSONArray conditionsJA = JSONArray.fromObject(queryView.getConditions());
//		JSONArray showsJA = JSONArray.fromObject(queryView.getShows());
//
//		// 分离出页头和行内按钮
//		JSONArray buttonsJA = JSONArray.fromObject(queryView.getButtons());
//		JSONArray navButtons = new JSONArray();// 页头
//		JSONArray rowButtons = new JSONArray();// 行内
//		for (int i = 0; i < buttonsJA.size(); i++) {
//			JSONObject temp = buttonsJA.getJSONObject(i);
//			if (JsonUtil.getString(temp, "hidden", "0").equals("1")) {
//				continue;
//			}
//			if (JsonUtil.getString(temp, "inRow", "0").equals("0")) {
//				navButtons.add(temp);
//			} else {
//				rowButtons.add(temp);
//			}
//		}
//
//		// 获取默认排序
//		String defSortField = "";// 默认排序字段
//		String defSortSeq = "";// 默认排序方式
//		for (int i = 0; i < showsJA.size(); i++) {
//			JSONObject temp = showsJA.getJSONObject(i);
//			if (temp.getString("defaultSort").equals("1")) {
//				defSortField = JsonUtil.getString(temp, "fieldName");
//				defSortSeq = JsonUtil.getString(temp, "sortSeq");
//				break;
//			}
//		}
//		
//		paramMap.put("debugger",new FreeMarkerDebugger());
//		paramMap.put("util", new BeanUtils());
//		paramMap.put("queryView", queryView);
//		paramMap.put("conditions", conditionsJA);
//		paramMap.put("showList", showsJA);
//		paramMap.put("showMap", JsonUtil.arrayToObject(showsJA, "fieldName"));
//		paramMap.put("navButtons", navButtons);
//		paramMap.put("rowButtons", rowButtons);
//
//		paramMap.put("ctx", ctx);
//
//		paramMap.put("sortField", defSortField);
//		paramMap.put("sortSeq", defSortSeq);
//
//		String html = freemarkEngine.parseByStringTemplate(template.getHtml(), paramMap);
//		queryView.setTemplate(html);
//	}

	/**
	 * <pre>
	 * 抽出来的根据request来获取数据的方法，主要是因为展示页和导出页都需要用到以下方法，
	 * 所以就抽出来了
	 * </pre>
	 * 
	 * @param sqlAlias
	 * @param alias
	 * @param request
	 * @param getAll
	 *            ：是否强行获取全部数据(就算视图配置了分页也会强行获取全部数据，为了导出全部数据用)
	 * @return List
	 * @exception
	 * @since 1.0.0
	 */
	private List getShowData(String sqlAlias, String alias, HttpServletRequest request, boolean getAll) {
		QueryView queryView = queryViewManager.getBySqlAliasAndAlias(sqlAlias, alias);

		QuerySqldef querySqldef = querySqldefManager.getByAlias(sqlAlias);
		Map<String, Object> queryParams = RequestUtil.getParameterValueMap(request, false, false);

		int pageNo = RequestUtil.getInt(request, "page", 1);
		int pageSize = RequestUtil.getInt(request, "pageSize", queryView.getPageSize());
		String isSearch = RequestUtil.getString(request, "initSearch", "true");

		List list = new PageList(new PageResult(0, 0, 0));
		if ("true".equals(isSearch)) {
			String sql = queryViewManager.getShowSql(queryView,queryParams);
			sysDataSourceManager.setDbContextDataSource(querySqldef.getDsName());// 切换数据源
			if (queryView.getNeedPage() == (short) 0 || getAll) {// 不分页
				list = commonDao.query(sql);
			} else {
				list = commonDao.query(sql, new DefaultPage(pageNo, pageSize));
			}
			queryViewManager.handleShowData(queryView, list);
		}
		
		return list;
	}
}
