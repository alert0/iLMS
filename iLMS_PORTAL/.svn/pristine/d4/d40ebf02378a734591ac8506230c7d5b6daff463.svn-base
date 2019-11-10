package com.hotent.portal.persistence.manager.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.hotent.base.core.engine.script.GroovyScriptEngine;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.mybatis.domain.PageResult;
import com.hotent.base.manager.impl.AbstractManagerImpl;
//import com.hotent.bpmx.core.util.BpmUtil;
//import com.hotent.form.persistence.manager.CustomQueryManager;
import com.hotent.form.persistence.model.CustomQuery;
import com.hotent.portal.index.IndexTabList;
import com.hotent.portal.persistence.dao.SysIndexToolsDao;
import com.hotent.portal.persistence.model.SysIndexColumn;
import com.hotent.portal.persistence.model.SysIndexTools;
//import com.hotent.portal.service.IndexService;
import com.hotent.portal.util.HttpClientUtil;
//import com.hotent.service.api.model.InvokeCmd;
//import com.hotent.service.api.model.InvokeResult;
//import com.hotent.service.model.impl.DefaultInvokeCmd;
//import com.hotent.service.ws.WebServiceClient;
import com.hotent.sys.persistence.manager.SysCategoryManager;
import com.hotent.sys.util.SysDataSourceUtil;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hotent.portal.persistence.manager.SysIndexToolsManager;

/**
 * 
 * <pre> 
 * 描述：首页工具 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:mouhb
 * 邮箱:mouhb@jee-soft.cn
 * 日期:2017-08-06 12:45:45
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Service("sysIndexToolsManager")
public class SysIndexToolsManagerImpl extends AbstractManagerImpl<String, SysIndexTools> implements SysIndexToolsManager{
	@Resource
	SysIndexToolsDao sysIndexToolsDao;
//	@Resource
//	private IndexService indexService;
	@Resource
	private GroovyScriptEngine groovyScriptEngine;
//	@Resource
//	CustomQueryManager customQueryManager;
//	@Resource
//	WebServiceClient webServiceClient;
	@Resource
	SysCategoryManager sysCategoryManager;
	@Override
	protected Dao<String, SysIndexTools> getDao() {
		return sysIndexToolsDao;
	}
	
	@Override
	public List<SysIndexTools> getToolsByIds(List<String> toolsIds) {
		return getByIds(toolsIds, "ID_");
	}
	
	
	/**
	 * 获取栏目的模版的HTML
	 * 
	 * @param sysIndexTools
	 * @param ctxPath
	 * @return
	 * @throws Exception
	 */
	public JSONObject parseTemplateJSON(SysIndexTools sysIndexTools,
			Map<String, Object> params) throws Exception {
		String dataFrom = sysIndexTools.getCounting();
		String html = "";
		Short colType = 0;
		short dataMode = sysIndexTools.getCountMode();

		String ctxPath = params.get("__ctx").toString();
		String dataParam = sysIndexTools.getCountParam();
		Object data = null;
		// 获取具体栏目的数据。
		try {
			Class<?>[] parameterTypes = getParameterTypes(dataParam, params);
			Object[] param = getDataParam(dataParam, params);
			if (1 == dataMode) {// service方式
				data = getModelByHandler(dataFrom, param, parameterTypes);
			}/* else if (2 == dataMode) { // 自定义查询方式
				String alias = dataFrom;
				data = getQueryPage(alias);
			}else if(SysIndexColumn.DATA_MODE_WEBSERVICE == dataMode){//WebServices 方法
				data = callWebService(dataFrom, getArrayParams(dataParam,param,parameterTypes));
			}else if(SysIndexColumn.DATA_MODE_RESTFUL == dataMode){//restful 接口
				JSONObject jsonObj = HttpClientUtil.postHttp(dataFrom, getArrayParams(dataParam,param,parameterTypes));
				if(BeanUtils.isNotEmpty(jsonObj)){
					Map<String, Object> map = new HashMap<String, Object>();
					Iterator it = jsonObj.keys();
					while(it.hasNext()){ 
						 String key = it.next().toString();  
						map.put(key, jsonObj.optString(key));
			        }
					data = map;
				}
			}*/
		} catch (Exception e) {
			// 出异常不影响其它数据
			e.printStackTrace();
		}
		/*Long height = BeanUtils.isEmpty(sysIndexTools.getColHeight()) ? 320
				: sysIndexTools.getColHeight();*/
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> model = new HashMap<String, Object>();
		//model.put("alias", sysIndexTools.getAlias());
		model.put("title", sysIndexTools.getName());
		model.put("url", sysIndexTools.getUrl());
		//model.put("height", height);
		map.put("model", model);// 栏目
		map.put("data", data); // 获取的数据
		map.put("ctx", ctxPath);// 上下文目录
//		map.put("service", indexService);// 把service注入
		PageResult pageBean = null;
		/*if (sysIndexTools.getNeedPage() == 1) // 进行分页
			pageBean = handerPageBean(data);*/
		//map.put("pageBean", pageBean); // 获取的数据

		html = "<#setting number_format=\"#\">" + html;
		try {
			html = parseByStringTemplate(map, html);
		} catch (Exception e) {
			System.out.println("解析模板出错："+e.getMessage());
		}
		//html = parserHtml(html, sysIndexTools, pageBean, params);
		
		JSONObject json = new JSONObject();
		// 如果是图表则返回数据
		if (SysIndexColumn.COLUMN_TYPE_CHART == colType
				|| SysIndexColumn.COLUMN_TYPE_CALENDAR == colType) // 加载图表的数据
			json.accumulate("option", data);

		// 这些数据前台解析
		/*json.accumulate("isRefresh", sysIndexTools.getSupportRefesh())
				.accumulate("refreshTime", sysIndexTools.getRefeshTime())
				.accumulate("show", sysIndexTools.getShowEffect())
				.accumulate("type", colType).accumulate("height", height)
				.accumulate("html", html);*/
		return json;
	}
	
	
	public Class<?>[] getParameterTypes(String dataParam,
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
	
	public Object[] getDataParam(String dataParam, Map<String, Object> params) {
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
	public Object getModelByHandler(String handler, Object[] args,
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
	
	private PageResult getIndexTabPageBean(IndexTabList indexTablist) {
		if (BeanUtils.isEmpty(indexTablist))
			return null;
		return indexTablist.getPageBean();
	}
}
