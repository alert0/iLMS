package com.hotent.mini.controller.form;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.db.model.Column;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.excel.util.ExcelUtil;
import com.hotent.base.core.util.AppUtil;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.util.time.TimeUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.api.IDynamicDatasource;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.CommonDao;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.table.util.SQLConst;
import com.hotent.bo.api.bodef.BoDefService;
import com.hotent.bo.api.instance.BoDataHandler;
import com.hotent.bo.api.model.BaseAttribute;
import com.hotent.bo.api.model.BaseBoDef;
import com.hotent.bo.api.model.BaseBoEnt;
import com.hotent.bo.api.model.BoData;
import com.hotent.bpmx.api.service.BoDataService;
import com.hotent.bpmx.core.util.BoDataUtil;
import com.hotent.bpmx.persistence.manager.BpmBusLinkManager;
import com.hotent.bpmx.persistence.model.BpmBusLink;
import com.hotent.form.persistence.manager.BpmDataTemplateManager;
import com.hotent.form.persistence.manager.BpmFormManager;
import com.hotent.form.persistence.manager.BpmFormRightManager;
import com.hotent.form.persistence.manager.BpmFormTemplateManager;
import com.hotent.form.persistence.model.BpmDataTemplate;


/**
 * 
 * <pre> 
 * 描述：业务数据模板 控制器类
 * 构建组：x5-bpmx-platform
 * 作者:liyg
 * 邮箱:liyg@jee-soft.cnt
 * 日期:2017-03-15 14:52:02
 * 版权：广州宏天软件有限公司
 * </pre>
 */
@Controller
@RequestMapping("/form/dataTemplate")
public class BpmDataTemplateController extends GenericController{
	@Resource
	BpmDataTemplateManager bpmDataTemplateManager;
	
	@Resource
	BpmFormTemplateManager bpmFormTemplateManager;
	
	@Resource
	BpmFormManager bpmFormManager;
	
	@Resource
	BpmFormRightManager bpmFormRightManager;
	@Resource
	BoDataService boDataService;
	@Resource
	BoDefService boDefService;
	
	@Resource(name="boDbHandlerImpl")
	BoDataHandler boDataHandler;
	@Resource
	IDynamicDatasource dynamicDatasource;
	@Resource
	BpmBusLinkManager bpmBusLinkManager;
	
	
	/**
	 * 业务数据模板列表(分页条件查询)数据
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("listJson/{templateId}")
	public @ResponseBody PageJson listJson(HttpServletRequest request,HttpServletResponse reponse,@PathVariable(value = "templateId") String templateId) throws Exception{
		DefaultQueryFilter queryFilter= (DefaultQueryFilter) getQueryFilter(request);
		String filterKey = RequestUtil.getString(request, BpmDataTemplate.PARAMS_KEY_FILTER_KEY, "");
		BpmDataTemplate template = bpmDataTemplateManager.get(templateId);
		List list = getList(template,queryFilter,filterKey);
		return new PageJson(list);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getList(BpmDataTemplate template,DefaultQueryFilter queryFilter,String filterKey){
		PageList<Map<String, Object>> returnData = new PageList<Map<String,Object>>();
		try {
			Map<String, Object> params = queryFilter.getParams();
			BaseBoDef boDef = boDefService.getByName(template.getBoDefAlias());
			BaseBoEnt boEnt = (BaseBoEnt)boDef.getBoEnt();
			String dsName = boEnt.getDsName();
			
			String showSql = bpmDataTemplateManager.getShowSql(template.getFilterField(),filterKey,dsName,boEnt.getTableName(),params);
	    	// 改变当前数据源s
	    	String dbType = dynamicDatasource.getDbTypeByAlias(dsName);
	    	DbContextHolder.setDataSource(dsName, dbType);// 转换这次进程的数据源
	    	//处理排序等其他条件
	    	queryFilter = getTemplateQueryFilter(queryFilter,template,boEnt);
	    	// 处理分页
			CommonDao commonDao = AppUtil.getBean(CommonDao.class);
			PageList<Map<String, Object>> list = commonDao.queryForListPage(showSql, queryFilter, params);
			DbContextHolder.clearDataSource();
			if(BeanUtils.isNotEmpty(list)){
				returnData.setPageResult(list.getPageResult());
				for (Map<String, Object> rowMap : list) {
					Map<String, Object> rtnMap = convertDbToData(boEnt, rowMap);
					rtnMap.put("isStartFlow", true);
					String pkKey = boEnt.getPkKey();
					if("0".equals(boEnt.getIsExternal().toString())){
						if(StringUtil.isNotEmpty(pkKey)){
							pkKey = pkKey.toUpperCase();
						}else{
							pkKey = "ID_";
						}
					}
					String businesKey = (String) rowMap.get(pkKey);
					if(StringUtil.isNotEmpty(businesKey)){
						BpmBusLink bblm = bpmBusLinkManager.getByBusinesKey(String.valueOf(rowMap.get(pkKey)), "number".equals(boEnt.getPkType()));
						if(BeanUtils.isEmpty(bblm)){
							rtnMap.put("isStartFlow", false);
						}
					}
					returnData.add(rtnMap);
				}
			}
		} catch (Exception e) {}
		return returnData;
	}

	/**
	 * 将从数据库读取的数据到实例数据。
	 * 
	 * @param boEnt
	 * @param map
	 * @return
	 */
	private Map<String, Object> convertDbToData(BaseBoEnt boEnt, Map<String, Object> map) {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		
		for (Entry<String, Object> ent : map.entrySet()) {
			String field = ent.getKey().toLowerCase();
			BaseAttribute attribute = boEnt.getAttrByField(field);
			if(BeanUtils.isNotEmpty(attribute)){
				// 处理日期。
				Object val = handValue(attribute, ent.getValue());
				rtnMap.put(attribute.getName(), val);
			}
		}
		return rtnMap;
	}
	
	/**
	 * 数据根据bo属性处理。
	 * 
	 * @param attr
	 * @param val
	 * @return
	 */
	private Object handValue(BaseAttribute attr, Object val) {
		if (BeanUtils.isEmpty(val))
			return val;
		if (Column.COLUMN_TYPE_DATE.equals(attr.getDataType())) {
			String format = attr.getFormat();
			return TimeUtil.getDateTimeString((Date) val, format);
		}
		return val;
	}
	
	/**
	 * 数据列表  第二次解析模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("dataList_{alias}")
	public ModelAndView dataList(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "alias") String alias) throws Exception {
		//取得当前页URL,如有参数则带参数
		String url = "dataList_" + alias + ".ht";
		String toReplaceUrl = "getDisplay_" + alias + ".ht";
		String __baseURL = request.getRequestURI().replace(url, toReplaceUrl);
		QueryFilter queryFilter = getQueryFilter(request);
		//取得传入参数ID
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("__baseURL", __baseURL);
		params.put(BpmDataTemplate.PARAMS_KEY_CTX, request.getContextPath());
		params.put("__tic", "bpmDataTemplate");
		params.put(BpmDataTemplate.PARAMS_KEY_ALIAS, alias);
		String html = bpmDataTemplateManager.getDisplay(alias, params, queryFilter.getParams());
 		return this.getAssignView("/form/dataTemplate/bpmDataTemplatePreview").addObject("html", html);
	}
	
	/**
	 * 业务数据模板明细页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@RequestMapping("bpmDataTemplateEdit")
	public ModelAndView getJson(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String formKey = RequestUtil.getString(request, "formKey");
		JSONObject byFormKey = bpmDataTemplateManager.getByFormKey(formKey);
		return this.getAutoView().addObject("jsonObject", byFormKey);
				
	}

	/**
	 * 保存业务数据模板信息
	 * @param request
	 * @param response
	 * @param bpmDataTemplate
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String resultMsg=null;
		ResultMessage message=null;
		BpmDataTemplate bpmDataTemplate = getFormObject(request);
		
		//是否初始化模板（是在编辑情况下，是否再初始化模板）
		String json = RequestUtil.getString(request, "json", false);
		boolean resetTemp=false;
		if (!StringUtil.isEmpty(json)){
			JSONObject obj = JSONObject.fromObject(json);
			String str = obj.getString("resetTemp");
			if(str.equals("1")){
				resetTemp=true;
			}
		}
		
		boolean flag = StringUtil.isEmpty(bpmDataTemplate.getId())?true:false;
		try {
			bpmDataTemplateManager.save(bpmDataTemplate,resetTemp);//在这个过程中进行了第一次模板解释，然后复制到templateHtml上
			resultMsg = flag ? "添加业务数据模板成功" : "更新业务数据模板成功";
			message=new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		} catch (Exception e) {
			resultMsg = "保存失败";
			message=new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		}finally{
			writeResultMessage(response.getWriter(), message);
		}

	}
	
	/**
	 * 批量删除业务数据模板记录
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
			bpmDataTemplateManager.removeByIds(aryIds);
			message=new ResultMessage(ResultMessage.SUCCESS, "删除业务数据模板成功");
		} catch (Exception e) {
			message=new ResultMessage(ResultMessage.FAIL, "删除业务数据模板失败");
		}
		writeResultMessage(response.getWriter(), message);
	}
	
	/**
	 * 根据表单Key, boAlias 获取表单html， 权限， bo数据结构
	 * @param request
	 * @param response
	 * @param formKey
	 * @param boAlias
	 * @return
	 */
	@RequestMapping("getForm/{formKey}/{boAlias}")
	public @ResponseBody Object getForm(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "formKey") String formKey,@PathVariable(value = "boAlias") String boAlias){
		String id = RequestUtil.getString(request, "id", "");
		int type = "get".equals(RequestUtil.getString(request, "action",""))?2:1;
		Map<String,Object> map = bpmDataTemplateManager.getFormByFormKey(formKey);
		if("formEmpty".equals(map.get("result"))){
			return map;
		}
		// 表单权限
		map.put("permission", bpmFormRightManager.getPermission(formKey, "", "", "", type));
		// 表单数据
		List<BoData> boDatas =  new ArrayList<BoData>();
		if(StringUtil.isNotEmpty(id)){
			// 获取编辑数据
			BoData boData = boDataHandler.getById(id, boAlias);
			boDatas = Arrays.asList(boData);
		}else{
			boDatas = boDataService.getDataByBoKeys(Arrays.asList(boAlias));
		}
		// 表单数据
		map.put("data", BoDataUtil.hanlerData(boDatas));
		
		return map;
	}
	
	@RequestMapping("boSave/{boAlias}")
	public void boSave(HttpServletRequest request, HttpServletResponse response,@RequestBody Object params,@PathVariable(value="boAlias") String boAlias) throws IOException{
		String resultMsg = "保存成功";
		ResultMessage message=null;
		com.alibaba.fastjson.JSONObject jsonObject =  (com.alibaba.fastjson.JSONObject) params;
		try {
			bpmDataTemplateManager.boSave(jsonObject,boAlias);
			message=new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		} catch (Exception e) {
			resultMsg = "保存失败";
			message=new ResultMessage(ResultMessage.FAIL, resultMsg);
		}finally{
			writeResultMessage(response.getWriter(), message);
		} 
	}
	
	@RequestMapping("boDel/{boAlias}")
	public void boDel(HttpServletRequest request, HttpServletResponse response,@PathVariable(value="boAlias") String boAlias) throws IOException{
		String resultMsg = "删除成功";
		ResultMessage message=null;
		
		// 获取主键
		BaseBoDef boDef = boDefService.getByName(boAlias);
		BaseBoEnt boEnt = (BaseBoEnt) boDef.getBoEnt();
		
		String[] ids = RequestUtil.getStringAryByStr(request, boEnt.getPkKey());
		try {
			bpmDataTemplateManager.boDel(ids,boAlias);
			message=new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		} catch (Exception e) {
			resultMsg = "删除失败";
			message=new ResultMessage(ResultMessage.FAIL, resultMsg);
		}finally{
			writeResultMessage(response.getWriter(), message);
		} 
	}
	
	@RequestMapping("editTemplate")
	public ModelAndView templateHtmlEdit(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = RequestUtil.getString(request, "id");
		BpmDataTemplate bpmDataTemplate = bpmDataTemplateManager.get(id);
		return getAssignView("/form/dataTemplate/templateHtmlEdit").addObject("bpmDataTemplate", bpmDataTemplate);
	}
	
	@RequestMapping("saveTemplate")
	public void saveTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String resultMsg = "保存成功";
		ResultMessage message=null;
		String id = RequestUtil.getString(request, "id");
		String  templateHtml = RequestUtil.getString(request, "templateHtml","",false);
		if(StringUtil.isEmpty(id)){
			message=new ResultMessage(ResultMessage.SUCCESS, "不能为空");
			return ;
		}
		try{
			BpmDataTemplate template = bpmDataTemplateManager.get(id);
			template.setTemplateHtml(templateHtml);
			bpmDataTemplateManager.update(template);
			message=new ResultMessage(ResultMessage.SUCCESS, resultMsg);
		}catch(Exception e){
			resultMsg = "保存失败";
			message=new ResultMessage(ResultMessage.FAIL, resultMsg);
		}finally{
			writeResultMessage(response.getWriter(), message);
		}
	
		
		
	}
	/**
	 * 取得 BpmDataTemplate 实体
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected BpmDataTemplate getFormObject(HttpServletRequest request) throws Exception {

		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));

		String json = RequestUtil.getString(request, "json", false);
		if (StringUtil.isEmpty(json))
			return null;
		JSONObject obj = JSONObject.fromObject(json);

		String displayField = obj.getString("displayField");
		String conditionField = obj.getString("conditionField");
		String sortField = obj.getString("sortField");
		String filterField = obj.getString("filterField");
		String manageField = obj.getString("manageField");
		String exportField = obj.getString("exportField");

		obj.remove("displayField");
		obj.remove("conditionField");
		obj.remove("sortField");
		obj.remove("filterField");
		obj.remove("manageField");

		BpmDataTemplate bpmDataTemplate = (BpmDataTemplate) JSONObject.toBean(obj, BpmDataTemplate.class);

		bpmDataTemplate.setDisplayField(displayField);
		bpmDataTemplate.setConditionField(conditionField);
		bpmDataTemplate.setSortField(sortField);
		bpmDataTemplate.setFilterField(filterField);
		bpmDataTemplate.setManageField(manageField);
		bpmDataTemplate.setExportField(exportField);

		return bpmDataTemplate;
	}
	
	
	/**
	 * 获取业务数据模板数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 */
	@RequestMapping("getJson")
	public @ResponseBody BpmDataTemplate getJsonData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String formKey = RequestUtil.getString(request, "formKey");
		BpmDataTemplate bpmDataTemplate = null;
		if (StringUtil.isNotEmpty(id)) {
			bpmDataTemplate = bpmDataTemplateManager.get(id);
		}else if (StringUtil.isNotEmpty(formKey)) {
			bpmDataTemplate = bpmDataTemplateManager.getExportDisplay(formKey);
		}
		return bpmDataTemplate;
	}
	
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
		String formKey = RequestUtil.getString(request, "formKey");
		BpmDataTemplate template = bpmDataTemplateManager.getTemplateByFormKey(formKey);

		String getType = RequestUtil.getString(request, "getType", "page");
		try {
			DefaultQueryFilter queryFilter= (DefaultQueryFilter) getQueryFilter(request);
			DefaultPage page = (DefaultPage) queryFilter.getPage();
			page.setLimit(Integer.MAX_VALUE-1);
			if("page".equals(getType)){
				if(template.getNeedPage().toString().equals("1")){
					page.setLimit(Integer.valueOf(template.getPageSize().toString()));
				}
			}
			queryFilter.setPage(page);
			String filterKey = RequestUtil.getString(request, BpmDataTemplate.PARAMS_KEY_FILTER_KEY, "");
		
			List<Map<String, Object>> pageList = getList(template,queryFilter,filterKey);
			
			// 拼装exprotMaps
			Map<String, String> exportMaps = new LinkedHashMap<String, String>();
			JSONArray showJA = JSONArray.fromObject(template.getDisplayField());
			JSONObject showJO = JsonUtil.arrayToObject(showJA, "name");
			for (String str : RequestUtil.getStringAryByStr(request, "expField")) {
				exportMaps.put(str, showJO.getJSONObject(str).getString("desc"));
			}
			HSSFWorkbook book = ExcelUtil.exportExcel(template.getName(), 24, exportMaps, pageList);
			ExcelUtil.downloadExcel(book, template.getName(), response);
			DbContextHolder.clearDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private DefaultQueryFilter getTemplateQueryFilter(DefaultQueryFilter queryFilter,BpmDataTemplate bpmDataTemplate,BaseBoEnt boEnt){
		if(BeanUtils.isNotEmpty(bpmDataTemplate)){
			try {
				// 是否分页
				if(0 == bpmDataTemplate.getNeedPage()){
					DefaultPage page = (DefaultPage) queryFilter.getPage();
					page.setLimit(Integer.MAX_VALUE-1);
					queryFilter.setPage(page);
				} 
				
				// 排序
				String sortField = bpmDataTemplate.getSortField();
				if(StringUtil.isNotZeroEmpty(sortField)){
					//获取字段前缀
					String colPrefix = boEnt.isExternal()?"":SQLConst.CUSTOMER_COLUMN_PREFIX;
					JSONArray array = JSONArray.fromObject(sortField);
					for(int i=0;i<array.size();i++){
						JSONObject obj = (JSONObject) array.get(i);
						queryFilter.getFieldSortList().add(new DefaultFieldSort(colPrefix+obj.getString("name"), Direction.fromString(obj.getString("sort"))));
					}
				}
				
			} catch (Exception e) {}
		}
		return queryFilter;
	}
	
}
