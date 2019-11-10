package com.hotent.portal.persistence.manager.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.encrypt.Base64;
//import com.hotent.base.core.engine.freemark.FreemarkEngine;
import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;
import com.hotent.base.manager.impl.AbstractManagerImpl;
//import com.hotent.bpmx.core.util.BpmUtil;
//import com.hotent.form.persistence.manager.CustomQueryManager;
//import com.hotent.form.persistence.model.CustomQuery;
import com.hotent.org.api.model.IUser;
//import com.hotent.org.api.service.IOrgService;
import com.hotent.org.api.service.IUserService;
import com.hotent.org.persistence.dao.UserDao;
import com.hotent.org.persistence.manager.OrgAuthManager;
import com.hotent.org.persistence.manager.RoleManager;
import com.hotent.org.persistence.model.OrgAuth;
import com.hotent.portal.index.IndexTabList;
import com.hotent.portal.persistence.dao.SysIndexColumnDao;
import com.hotent.portal.persistence.manager.SysIndexColumnManager;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysObjRights;
//import com.hotent.portal.service.IndexService;
import com.hotent.portal.util.HttpClientUtil;
//import com.hotent.service.api.model.InvokeCmd;
//import com.hotent.service.api.model.InvokeResult;
//import com.hotent.service.model.impl.DefaultInvokeCmd;
//import com.hotent.service.ws.WebServiceClient;
import com.hotent.sys.api.curuser.CurrentUserService;
import com.hotent.sys.api.model.SysType;
import com.hotent.sys.constants.CategoryConstants;
import com.hotent.sys.persistence.manager.SysCategoryManager;
import com.hotent.sys.persistence.manager.SysTypeManager;
import com.hotent.sys.persistence.model.SysCategory;
import com.hotent.sys.util.ContextUtil;
import com.hotent.sys.util.SysDataSourceUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * <pre>
 * 对象功能:首页栏目 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:hugh
 * 创建时间:2015-03-18 11:22:46
 * </pre>
 */
@Service("sysIndexColumnService")
public class SysIndexColumnManagerImpl extends AbstractManagerImpl<String, SysIndexColumn> implements SysIndexColumnManager{
	private static Log logger = LogFactory.getLog(SysIndexColumnManagerImpl.class);
	@Resource
	private SysIndexColumnDao dao;
//	@Resource
//	private FreemarkEngine freemarkEngine;
	@Resource
	private IUserService sysUserService;
	@Resource
	private RoleManager roleManager;
//	@Resource
//	private IOrgService sysOrgService;
	@Resource
	private UserDao userDao;
	@Resource
	private SysTypeManager sysTypeManager;
//	@Resource
//	private PositionService positionService;
//	@Resource
//	private GlobalTypeService globalTypeService;
//	@Resource
//	private BpmFormQueryService bpmFormQueryService;

	@Resource
	private CurrentUserService currentUserService;
//	@Resource
//	private OrgSubUserService orgSubUserService;
	@Resource
	private GroovyScriptEngine groovyScriptEngine;

	@Resource
	private OrgAuthManager orgAuthManager;
//	@Resource
//	private IndexService indexService;
//	@Resource
//	CustomQueryManager customQueryManager;
//	@Resource
//	WebServiceClient webServiceClient;
	@Resource
	SysCategoryManager sysCategoryManager;
	

	public SysIndexColumnManagerImpl() {
	}

	@Override
	protected Dao<String, SysIndexColumn> getDao() {
		return dao;
	}

	
	/**
	 * 获取有权限的栏目
	 * @param filter
	 * @param params
	 * @param isParse
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysIndexColumn> getHashRightColumnList(QueryFilter filter,
			Map<String, Object> params, Boolean isParse,short type) throws Exception {
		List<SysIndexColumn> list;
		IUser user = ContextUtil.getCurrentUser();
		if ("admin".equals(user.getAccount())) { // 不是超级管理员 filter //
			list = dao.query(filter);
		}else{
			list = getByUserIdFilter(type);
		}
		if (isParse)
			this.parseList(list, params);
		return list;
	}

	private void parseList(List<SysIndexColumn> list, Map<String, Object> params)
			throws Exception {
		if (BeanUtils.isEmpty(list))
			return;
		for (SysIndexColumn sysIndexColumn : list) {
			String templateHtml = parseTemplateHtml(sysIndexColumn, params);
			sysIndexColumn.setTemplateHtml(templateHtml);
		}
	}
//
//	/**
//	 * 获取当前组织有权限的栏目(个人)
//	 * 可以删除？
//	 * @param ctxPath
//	 * @return
//	 * @throws Exception
//	 */
//	public List<SysIndexColumn> getIndexColumnData(Map<String, Object> params)
//			throws Exception {
//		List<SysIndexColumn> list = new ArrayList<SysIndexColumn>();
//		if (ContextUtil.isSuperAdmin()) {
//			list = dao.getAll();
//		} else {
//			// 获取当前组织有权限的栏目
//			list = getByUserIdFilter();
//		}
//		this.parseList(list, params);
//		return list;
//
//	}


	public List<SysIndexColumn> getByUserIdFilter(short type) {
		// 这一步很关键 用伪代码来表达map 的内容就是
		// 类型 然后 接着一堆对应的ID eg：
		// user : 1,2,3,4...
		// org : 1,2,3,4...
		// 然后 根据上面的对应ID 和 权限配置的ID进行比较，看满不满足权限条件
		
		Map<String, Set<String>> map = currentUserService
				.getUserRightMap();
		
		//添加分级管理与，当栏目设置了组织。
		List<OrgAuth> orgAuthList =  orgAuthManager.getLayoutOrgAuth(ContextUtil.getCurrentUserId());
		Set<String> authOrgIdList = new HashSet<String>();
		if(BeanUtils.isNotEmpty(orgAuthList)){
			for(OrgAuth orgAuth:orgAuthList){
				authOrgIdList.add(orgAuth.getOrgId());
			}
			map.put("auth_org", authOrgIdList);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("relationMap", map);
		params.put("objType", SysObjRights.RIGHT_TYPE_INDEX_COLUMN);
		params.put("isPublic", type);
		return dao.getByUserIdFilter(params);
	}
	/**
	 * 获取栏目的模版的HTML
	 * 
	 * @param sysIndexColumn
	 * @param ctxPath
	 * @return
	 * @throws Exception
	 */
	public String parseTemplateHtml(SysIndexColumn sysIndexColumn,
			Map<String, Object> params) throws Exception {
		JSONObject json = parseTemplateJSON(sysIndexColumn, params);
		return json.getString("html");
	}


//
//	/**
//	 * 获取栏目的模版的HTML
//	 * 
//	 * @param sysIndexColumn
//	 * @param ctxPath
//	 * @return
//	 * @throws Exception
//	 */
//	public String parseTemplateHtmlJSON(SysIndexColumn sysIndexColumn,
//			Map<String, Object> params) throws Exception {
//		JSONObject json = parseTemplateJSON(sysIndexColumn, params);
//		return json.toString();
//	}
//
	
//	/**
//	 * 处理分页数据
//	 * 
//	 * @param data
//	 * @return
//	 */
//	private PageBean handerPageBean(Object data) {
//		PageBean pageBean = null;
//		try {
//			if (data instanceof PageList<?>) {
//				PageList<?> pageList = (PageList<?>) data;
//				pageBean = pageList.getPageBean(); // 获取分页的数据
//			} else if (data instanceof IndexTabList) {
//				IndexTabList indexTablist = (IndexTabList) data;
//				pageBean = getIndexTabPageBean(indexTablist); // 获取分页的数据
//			}
//		} catch (Exception e) {
//
//		}
//		return pageBean;
//	}
//
//	private PageBean getIndexTabPageBean(IndexTabList indexTablist) {
//		if (BeanUtils.isEmpty(indexTablist))
//			return null;
//		return indexTablist.getPageBean();
//	}

	
	

	private Class<?> getParameterTypes(String type) {
		Class<?> claz = null;
		try {
			if (type.equalsIgnoreCase("string")) {
				claz = String.class;
			} else if (type.equalsIgnoreCase("int")) {
				claz = Integer.class;
			} else if (type.equalsIgnoreCase("float")) {
				claz = Float.class;
			} else if (type.equalsIgnoreCase("double")) {
				claz = Double.class;
			} else if (type.equalsIgnoreCase("byte")) {
				claz = Byte.class;
			} else if (type.equalsIgnoreCase("short")) {
				claz = Short.class;
			} else if (type.equalsIgnoreCase("long")) {
				claz = Long.class;
			} else if (type.equalsIgnoreCase("boolean")) {
				claz = Boolean.class;
			} else if (type.equalsIgnoreCase("date")) {
				claz = Date.class;
			} else {
				claz = String.class;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return claz;
	}

	/**
	 * 给栏目添加个别名
	 * 
	 * @param html
	 * @param sysIndexColumn
	 * @param pageBean
	 * @param params
	 * @return
	 */
	private String parserHtml(String html, SysIndexColumn sysIndexColumn,
			DefaultPage pageBean, Map<String, Object> params) {

		if (StringUtil.isEmpty(html))
			return "";
		Document doc = Jsoup.parseBodyFragment(html);
		Elements els = doc.body().children();
		if (BeanUtils.isEmpty(els))
			return doc.body().html();
		Element el = els.get(0);
		el.attr("template-alias", sysIndexColumn.getAlias());
		JSONObject json = new JSONObject();
		// params.remove("__ctx");
		for (String key : params.keySet()) {
			json.accumulate(key, params.get(key));
		}
		if (BeanUtils.isNotEmpty(pageBean)) {
			json.element("currentPage", pageBean.getPageNo())
//					.element("totalPage", pageBean.getTotalPage())
					.element("pageSize", pageBean.getPageSize());
		}
		el.attr("template-params", json.toString());
		html = doc.body().html();
		return html;

	}

	
//
//	public String getHtmlById(Long id, Map<String, Object> params)
//			throws Exception {
//		SysIndexColumn sysIndexColumn = this.getById(id);
//		return "<div template-alias=\"" + sysIndexColumn.getAlias()
//				+ "\"></div>";
//	}
//
//	/**
//	 * 通过别名获取栏目
//	 * 
//	 * @param columnAlias
//	 * @return
//	 */
//	public SysIndexColumn getByColumnAlias(String alias) {
//		return dao.getByColumnAlias(alias);
//
//	}
//
//	public String getHtmlByColumnAlias(String alias, Map<String, Object> params)
//			throws Exception {
//		SysIndexColumn sysIndexColumn = this.getByColumnAlias(alias);
//		if (BeanUtils.isEmpty(sysIndexColumn))
//			return "";
//		return parseTemplateHtmlJSON(sysIndexColumn, params);
//	}
//

	/**
	 * 获取栏目的模版的HTML
	 * 
	 * @param sysIndexColumn
	 * @param ctxPath
	 * @return
	 * @throws Exception
	 */
	public String parseTemplateHtmlJSON(SysIndexColumn sysIndexColumn,
			Map<String, Object> params) throws Exception {
		JSONObject json = parseTemplateJSON(sysIndexColumn, params);
		return json.toString();
	}

	/**
	 * 获取栏目的模版的HTML
	 * 
	 * @param sysIndexColumn
	 * @param ctxPath
	 * @return
	 * @throws Exception
	 */
	public JSONObject parseTemplateJSON(SysIndexColumn sysIndexColumn,
			Map<String, Object> params) throws Exception {
		String dataFrom = sysIndexColumn.getDataFrom();
		String html = sysIndexColumn.getTemplateHtml();
		if (StringUtil.isNotEmpty(html)) {
			html = Base64.getFromBase64(html);
		}		
		Short colType = sysIndexColumn.getColType();
		short dataMode = sysIndexColumn.getDataMode();

		String ctxPath = params.get("__ctx").toString();
		String dataParam = sysIndexColumn.getDataParam();
		Object data = null;
		// 获取具体栏目的数据。
		try {
			Class<?>[] parameterTypes = getParameterTypes(dataParam, params);
			Object[] param = getDataParam(dataParam, params);
			if (SysIndexColumn.DATA_MODE_SERVICE == dataMode) {// service方式
				data = getModelByHandler(dataFrom, param, parameterTypes);
			} 
//			else if (SysIndexColumn.DATA_MODE_QUERY == dataMode) { // 自定义查询方式
//				String alias = sysIndexColumn.getDataFrom();
//				data = getQueryPage(alias);
//			}
//			else if(SysIndexColumn.DATA_MODE_WEBSERVICE == dataMode){//WebServices 方法
//				data = callWebService(dataFrom, getArrayParams(dataParam,param,parameterTypes));
//			}
			else if(SysIndexColumn.DATA_MODE_RESTFUL == dataMode){//restful 接口
				JSONObject jsonObj = HttpClientUtil.postHttp(dataFrom, getArrayParams(dataParam,param,parameterTypes));
				if(BeanUtils.isNotEmpty(jsonObj)){
					data = jsonObj;
				}
			}
		} catch (Exception e) {
			// 出异常不影响其它数据
			e.printStackTrace();
		}
		Long height = BeanUtils.isEmpty(sysIndexColumn.getColHeight()) ? 320
				: sysIndexColumn.getColHeight();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("alias", sysIndexColumn.getAlias());
		model.put("title", sysIndexColumn.getName());
		model.put("url", sysIndexColumn.getColUrl());
		model.put("height", height);
		map.put("model", model);// 栏目
		map.put("data", data); // 获取的数据
		map.put("ctx", ctxPath);// 上下文目录
//		map.put("service", indexService);// 把service注入
		PageResult pageBean = null;
		if (sysIndexColumn.getNeedPage() == 1) // 进行分页
			pageBean = handerPageBean(data);
		map.put("pageBean", pageBean); // 获取的数据

		html = "<#setting number_format=\"#\">" + html;
		
		try {
			html = parseByStringTemplate(map, html);
		} catch (Exception e) {
			System.out.println("解析模板出错："+e.getMessage());
		}
		html = parserHtml(html, sysIndexColumn, pageBean, params);
		
		JSONObject json = new JSONObject();
		// 如果是图表则返回数据
		if (SysIndexColumn.COLUMN_TYPE_CHART == colType
				|| SysIndexColumn.COLUMN_TYPE_CALENDAR == colType) // 加载图表的数据
			json.accumulate("option", data);

		// 这些数据前台解析
		json.accumulate("isRefresh", sysIndexColumn.getSupportRefesh())
				.accumulate("refreshTime", sysIndexColumn.getRefeshTime())
				.accumulate("show", sysIndexColumn.getShowEffect())
				.accumulate("type", colType).accumulate("height", height)
				.accumulate("html", html);
		return json;
	}
	
	
	private JSONArray getArrayParams(String dataParam, Object[] param, Class<?>[] parameterTypes){
		JSONArray array = new JSONArray();
		if(StringUtil.isNotEmpty(dataParam)){
			try {
				JSONArray paramArray = JSONArray.fromObject(dataParam);
				for(int i=0;i<paramArray.size();i++){
					JSONObject obj = JSONObject.fromObject(paramArray.get(i));
					obj.put("value", param[i]);
					obj.put("type", parameterTypes[i].getCanonicalName());
					array.add(obj);
				}
			} catch (Exception e) {}
		}
		return array;
	}
	
	/**
	 * 获取自定义查询列表
	 * @param alias
	 * @return
	 */
//	private  PageList getQueryPage(String alias){
//		PageList pageList = new PageList<>();
//		try {
//			CustomQuery customQuery = customQueryManager.getByAlias(alias);
//			if (customQuery==null) {
//				return null;
//			}
//			String dbType= SysDataSourceUtil. getDbType(customQuery.getDsalias());
//			customQuery.setNeedPage(0);
//			// 切换这次进程的数据源
//			DbContextHolder.setDataSource(customQuery.getDsalias(),dbType);
//			pageList = (PageList)customQueryManager.getData(customQuery, null,dbType, 1, 20);
//		} catch (Exception e) {}
//		return pageList;
//	}
	
	
	
	/**
	 * 根据字符串模版解析出内容
	 * @param obj 需要解析的对象。
	 * @param templateSource	字符串模版。
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	private  String parseByStringTemplate(Object obj,String templateSource) throws TemplateException, IOException
	{
		Configuration cfg = new Configuration();
		cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
		StringTemplateLoader loader = new StringTemplateLoader();
		cfg.setTemplateLoader(loader);
		cfg.setClassicCompatible(true);
		loader.putTemplate("freemaker", templateSource);
                Template template = cfg.getTemplate("freemaker");   
                StringWriter writer = new StringWriter();   
                template.process(obj, writer); 
		return writer.toString();
		
	}

	/**
	 * 处理分页数据
	 * 
	 * @param data
	 * @return
	 */
	private PageResult handerPageBean(Object data) {
		PageResult pageBean = null;
		try {
			if (data instanceof PageList<?>) {
				PageList<?> pageList = (PageList<?>) data;
				pageBean = pageList.getPageResult(); // 获取分页的数据
			} else if (data instanceof IndexTabList) {
				IndexTabList indexTablist = (IndexTabList) data;
				pageBean = getIndexTabPageBean(indexTablist); // 获取分页的数据
			}
		} catch (Exception e) {

		}
		return pageBean;
	}

	private PageResult getIndexTabPageBean(IndexTabList indexTablist) {
		if (BeanUtils.isEmpty(indexTablist))
			return null;
		return indexTablist.getPageBean();
	}

	private Class<?>[] getParameterTypes(String dataParam,
			Map<String, Object> params) {
		if (JsonUtil.isEmpty(dataParam) || StringUtil.isEmpty(dataParam))
			return new Class<?>[0];
		JSONArray jary = JSONArray.fromObject(dataParam);
		Class<?>[] parameterTypes = new Class<?>[jary.size()];
		for (int i = 0; i < jary.size(); i++) {
			JSONObject json = jary.getJSONObject(i);
			String type = (String) json.get("type");

			parameterTypes[i] = getParameterTypes(type);
		}
		return parameterTypes;
	}

	private Object[] getDataParam(String dataParam, Map<String, Object> params) {
		if (JsonUtil.isEmpty(dataParam) || StringUtil.isEmpty(dataParam))
			return null;
		JSONArray jary = JSONArray.fromObject(dataParam);
		Object[] args = new Object[jary.size()];
		for (int i = 0; i < jary.size(); i++) {
			JSONObject json = jary.getJSONObject(i);
			String name = (String) json.get("name");
			String type = (String) json.get("type");
			String mode = (String) json.get("mode");
			String value = json.optString("value");

			Object o = value;
			if (mode.equalsIgnoreCase("1")) {// 页面传入的
				o = params.get(name);
				if (JsonUtil.isEmpty(o) && BeanUtils.isNotEmpty(value))// 如果空值则取默认值
					o = value;
			} else if (mode.equalsIgnoreCase("2")) {// 脚本传入
				o = groovyScriptEngine.executeString(value, params);
			}
			Object val = StringUtil.parserObject(o, type);

			args[i] = val;
		}
		return args;
	}


	/**
	 * 给栏目添加个别名
	 * 
	 * @param html
	 * @param sysIndexColumn
	 * @param pageBean
	 * @param params
	 * @return
	 */
	private String parserHtml(String html, SysIndexColumn sysIndexColumn,
			PageResult pageBean, Map<String, Object> params) {

		if (StringUtil.isEmpty(html))
			return "";
		Document doc = Jsoup.parseBodyFragment(html);
		Elements els = doc.body().children();
		if (BeanUtils.isEmpty(els))
			return doc.body().html();
		Element el = els.get(0);
		el.attr("template-alias", sysIndexColumn.getAlias());
		JSONObject json = new JSONObject();
		// params.remove("__ctx");
		for (String key : params.keySet()) {
			json.accumulate(key, params.get(key));
		}
		if (BeanUtils.isNotEmpty(pageBean)) {
			json.element("currentPage", pageBean.getPage())
					.element("totalPage", pageBean.getTotalPages())
					.element("pageSize", pageBean.getLimit());
		}
		el.attr("template-params", json.toString());
		html = doc.body().html();
		return html;

	}

	/**
	 * 根据handler取得数据。
	 * 
	 * <pre>
	 * handler 为 service +"." + 方法名称。
	 * </pre>
	 * 
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	private Object getModelByHandler(String handler, Object[] args,
			Class<?>[] parameterTypes) throws Exception {
		Object model = null;
		if (StringUtil.isEmpty(handler))
			return model;
//		int rtn = BpmUtil.isHandlerValidNoCmd(handler, parameterTypes);
//		if (rtn != 0)
//			return model;
		String[] aryHandler = handler.split("[.]");
		if (aryHandler == null)
			return model;
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		// 触发该Bean下的业务方法
		Object serviceBean = AppUtil.getBean(beanId);
		// 如果配置数据来源的方法带有参数的时候

		if (serviceBean == null)
			return model;
		if (args == null || args.length <= 0) {
			parameterTypes = new Class<?>[0];
		}
		Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method,
				parameterTypes);

		model = invokeMethod.invoke(serviceBean, args);
		if (BeanUtils.isEmpty(model))
			model = null;
		return model;
	}


	/**
	 * 通过别名获取栏目
	 * 
	 * @param columnAlias
	 * @return
	 */
	public SysIndexColumn getByColumnAlias(String alias) {
		return dao.getByColumnAlias(alias);

	}

	public String getHtmlByColumnAlias(String alias, Map<String, Object> params)
			throws Exception {
		SysIndexColumn sysIndexColumn = this.getByColumnAlias(alias);
		if (BeanUtils.isEmpty(sysIndexColumn))
			return "";
		return parseTemplateHtmlJSON(sysIndexColumn, params);
	}

	/**
	 * 解析设计模版的html
	 * 
	 * @param designHtml
	 *            设计的html
	 * @param columnList
	 *            有权限栏目列表
	 * @return
	 */
	public String parserDesignHtml(String designHtml,
			List<SysIndexColumn> columnList) {
		if (StringUtil.isEmpty(designHtml))
			return null;
		Document doc = Jsoup.parseBodyFragment(designHtml);
		Elements els = doc.select("[template-alias]");
		for (Iterator<Element> it = els.iterator(); it.hasNext();) {
			Element el = it.next();
			String value = el.attr("template-alias");
			String h = getSysIndexColumn(value, columnList);
			Element parent = el.parent();
			el.remove();
			parent.append(h);
		}
		designHtml = doc.body().html();
		return designHtml;
	}

	/**
	 * 通过别名获取模版
	 * 
	 * @param alias
	 * @param columnList
	 * @return
	 */
	private String getSysIndexColumn(String alias,
			List<SysIndexColumn> columnList) {
		for (SysIndexColumn sysIndexColumn : columnList) {
			if (alias.equals(sysIndexColumn.getAlias()))
				return sysIndexColumn.getTemplateHtml();
		}
		return "";
	}
	
	

	/**
	 * 取得类型下的列表Map
	 * 
	 * @param columnList
	 * @return
	 * @throws Exception 
	 */
	@Override
	public Map<String, List<SysIndexColumn>> getColumnMap(
			List<SysIndexColumn> columnList) throws Exception {
		
		SysCategory sysCategory = sysCategoryManager.getByTypeKey(CategoryConstants.CAT_INDEX_COLUMN.key());
		// 根节点parentId = -1； 标记
		SysType type = new SysType();
		type.setId(sysCategory.getId());
		type.setName(sysCategory.getName());
		type.setParentId("-1");
		type.setTypeKey(sysCategory.getGroupKey());
		
		List<SysType> sysTypeList = sysTypeManager.getByGroupKey(
				CategoryConstants.CAT_INDEX_COLUMN.key(),ContextUtil.getCurrentUserId());
		sysTypeList.add(type);
		
		Map<Long, List<SysIndexColumn>> map = new LinkedHashMap<Long, List<SysIndexColumn>>();
		Long nullCatalog = 0L;
		for (SysIndexColumn sysIndexColumn : columnList) {
			
			Long catalog = sysIndexColumn.getCatalog();
			if (BeanUtils.isEmpty(catalog))
				catalog = nullCatalog;
			List<SysIndexColumn> list = map.get(catalog);

			if (BeanUtils.isEmpty(list))
				list = new ArrayList<SysIndexColumn>();
			list.add(sysIndexColumn);
			map.put(catalog, list);
		}

		Map<String, List<SysIndexColumn>> map1 = new LinkedHashMap<String, List<SysIndexColumn>>();
		for (SysType sysType : sysTypeList) {
			Long typeId = Long.parseLong(sysType.getId());
			String name = sysType.getName();
			List<SysIndexColumn> list = map.get(typeId);
			if (BeanUtils.isNotEmpty(list))
				map1.put(name, list);
		}
		// 添加无类型的分类
		List<SysIndexColumn> list = map.get(nullCatalog);
		if (BeanUtils.isNotEmpty(list))
			map1.put("默认栏目", list);
		return map1;
	}

	@Override
	public Boolean isExistAlias(String alias, String id) {
		if (id == null || "0".equals(id))
			id = null;
		return dao.isExistAlias(alias, id);
	}

	/**
	 * 初始化桌面栏目，添加桌面栏目到数据库
	 */
//	public static void initIndex() {
//		SysIndexColumnManagerImpl sysIndexColumnService = (SysIndexColumnManagerImpl) AppUtil
//				.getBean(SysIndexColumnManagerImpl.class);
//		try {
//			sysIndexColumnService.init();
//		} catch (Exception e) {
//			logger.debug(e.getMessage());
//		}
//
//	}

	/**
	 * 初始化首页栏目的数据</br> --如果没有数据才初始化数据
	 * 
	 * @throws Exception
	 * 
	 */
//	public void init() throws Exception {
//		Integer amount = dao.getCounts();
//		if (amount == 0)
//			initIndexColumn();
//	}

	/**
	 * 初始化首页栏目的数据
	 * 
	 * @throws Exception
	 */
//	public void initIndexColumn() throws Exception {
//		String templatePath = PortalUtil.getIndexTemplatePath();
//		String path = templatePath + "index.xml";
//		InputStream is = new BufferedInputStream(new FileInputStream(path));
//		IndexColumns indexColumns = ((IndexColumns) XmlBeanUtil.unmarshall(is,
//				ObjectFactory.class));
//		List<IndexColumn> list = indexColumns.getIndexColumn();
//		for (int i = 0; i < list.size(); i++) {
//			IndexColumn indexColumn = list.get(i);
//			SysIndexColumn sysIndexColumn = new SysIndexColumn();
//			BeanUtils.copyNotNullProperties(sysIndexColumn, indexColumn);
//			sysIndexColumn.setId((long) i + 1);
//			String fileName = indexColumn.getAlias() + ".ftl";
//			String templateHtml = FileUtil.readFile(templatePath + "templates"
//					+ File.separator + fileName);
//			sysIndexColumn.setTemplateHtml(templateHtml);
//			SysIndexColumn column = dao.getByColumnAlias(sysIndexColumn
//					.getAlias());
//			if (BeanUtils.isNotEmpty(column))
//				dao.delById(column.getId());
//			dao.add(sysIndexColumn);
//		}
//
//	}
//
//	public List<SysIndexColumn> getDefaultList() {
//		return dao.getBySqlKey("defaultList");
//	}
	
	
//	public Object callWebService(String url,JSONArray params) throws Exception {
//    	try {
//    		String[] urlParams = url.split("\\?");
//    		InvokeCmd invokeCmd = new DefaultInvokeCmd();
//    		JSONObject paramObj = new JSONObject();
//    		invokeCmd.setAddress(urlParams[0]);
//    		if(BeanUtils.isNotEmpty(params)){
//            	for (Object object : params) {
//    				JSONObject jsonObj = JSONObject.fromObject(object);
//    				if("namespace".equals(jsonObj.getString("name"))){
//    					invokeCmd.setOperatorNamespace(jsonObj.getString("value"));
//    				}else{
//    					paramObj.accumulate("json", "\""+jsonObj.getJSONObject("value")+"\"");
//            			invokeCmd.setJsonParam(paramObj.toString());
//    				}
//            	}
//    		}
//    		invokeCmd.setOperatorName(urlParams[1]);
//    		InvokeResult result = webServiceClient.invoke(invokeCmd);
//    		JSONObject jsonObject = new JSONObject();
//    		if(StringUtil.isNotEmpty(result.getJson())){
//    			jsonObject = JSONObject.fromObject(result.getJson());
//    			try {
//    				return jsonObject.getJSONArray("list");
//				} catch (Exception e) {
//					return jsonObject.getString("list");
//				}
//    			
//    		}
//    		return jsonObject;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JSONObject();
//		}
//	}

}
