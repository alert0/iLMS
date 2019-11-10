package com.hotent.mini.controller.form;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.db.IViewOperator;
import com.hotent.base.api.db.model.Table;
import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.FileUtil;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.core.web.GenericController;
import com.hotent.base.core.web.RequestUtil;
import com.hotent.base.db.datasource.DbContextHolder;
import com.hotent.base.db.id.UniqueIdUtil;
import com.hotent.base.db.mybatis.Dialect;
import com.hotent.base.db.mybatis.domain.PageJson;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.db.table.BaseTableMeta;
import com.hotent.base.db.table.factory.DatabaseFactory;
import com.hotent.base.db.table.util.MetaDataUtil;
import com.hotent.form.persistence.manager.CustomQueryManager;
import com.hotent.form.persistence.model.CustomQuery;
import com.hotent.sys.util.SysDataSourceUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * <pre>
 * 描述：bpm_custom_query管理
 * 构建组：x5-bpmx-platform
 * 作者:liyj_aschs
 * 邮箱:liyj_aschs@jee-soft.cn
 * 日期:2014-1-10-下午3:29:34
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
@Controller
@RequestMapping("/form/customQuery/")
public class CustomQueryController extends GenericController {
	@Resource
	CustomQueryManager customQueryManager;
	@Resource
	JdbcTemplate jdbcTemplate;

	/**
	 * bpm_custom_query列表(分页条件查询)数据 TODO方法名称描述
	 * 
	 * @param request
	 * @param reponse
	 * @return
	 * @throws Exception
	 *             PageJson
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("listJson")
	public @ResponseBody
	PageJson listJson(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		PageList<CustomQuery> customQueryList = (PageList<CustomQuery>) customQueryManager.query(queryFilter);
		return new PageJson(customQueryList);
	}
	
	/**
	 * 通过别名获取自定义查询信息
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("getByAlias")
	@ResponseBody
	public CustomQuery getByAlias(HttpServletRequest request, HttpServletResponse reponse){
		String alias = RequestUtil.getString(request, "alias");
		return customQueryManager.getByAlias(alias);
	}

	/**
	 * 编辑bpm_custom_query信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("customQueryEdit")
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String preUrl = RequestUtil.getPrePage(request);
		return getAutoView().addObject("returnUrl", preUrl);
	}

	/**
	 * bpm_custom_query明细页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             ModelAndView
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("customQueryGet")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return getAutoView().addObject("returnUrl", RequestUtil.getPrePage(request));
	}

	/**
	 * 保存bpm_custom_query信息 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @param customQuery
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String json = FileUtil.inputStream2String(request.getInputStream());
		
		CustomQuery customQuery = GsonUtil.toBean(json, CustomQuery.class);

		String resultMsg = null;
		try {
			if (StringUtil.isEmpty(customQuery.getId())) {
				if(customQueryManager.getByAlias(customQuery.getAlias())!=null){
					writeResultMessage(response.getWriter(), customQuery.getAlias()+"，已存在",ResultMessage.FAIL);
					return;
				}
				customQuery.setId(UniqueIdUtil.getSuid());
				customQueryManager.create(customQuery);
				resultMsg = "添加成功";
			} else {
				customQueryManager.update(customQuery);
				resultMsg = "更新成功";
			}
		} catch (Exception e) {
			resultMsg = "保存错误";
			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
			return;
		}

		writeResultMessage(response.getWriter(), resultMsg, ResultMessage.SUCCESS);
	}

	/**
	 * 批量删除bpm_custom_query记录 TODO方法名称描述
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 *             void
	 * @exception
	 * @since 1.0.0
	 */
	@RequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResultMessage message = null;
		try {
			String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
			customQueryManager.removeByIds(aryIds);
			message = new ResultMessage(ResultMessage.SUCCESS, "删除bpm_custom_query成功");
		} catch (Exception e) {
			message = new ResultMessage(ResultMessage.FAIL, "删除bpm_custom_query失败");
		}
		writeResultMessage(response.getWriter(), message);
	}

	@RequestMapping("getByDsObjectName")
	@ResponseBody
	public JSONArray getByDsObjectName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dsalias = RequestUtil.getString(request, "dsalias");// 数据源别名
		String isTable = RequestUtil.getString(request, "isTable");
		String objName = RequestUtil.getString(request, "objName");
		
		String dbType= SysDataSourceUtil. getDbType(dsalias);
		
		DbContextHolder.setDataSource(dsalias,dbType);// 转换这次进程的数据源
		

		if ("1".equals(isTable)) {
			BaseTableMeta baseTableMeta = MetaDataUtil.getBaseTableMetaAfterSetDT(dbType);// 获取表操作元

			Map<String, String> map = baseTableMeta.getTablesByName(objName);

			JSONArray jsonArray = new JSONArray();
			for (String key : map.keySet()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("name", key.toString());
				jsonObject.accumulate("comment", map.get(key).toString());
				jsonArray.add(jsonObject);
			}
			return jsonArray;

		} else {
			IViewOperator iViewOperator = MetaDataUtil.getIViewOperatorAfterSetDT(dbType);

			List<String> viewNames = iViewOperator.getViews(objName);
			JSONArray jsonArray = new JSONArray();
			for (String name : viewNames) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("name", name);
				jsonObject.accumulate("comment", name);
				jsonArray.add(jsonObject);
			}
			return jsonArray;
		}

	}

	@RequestMapping("getTable")
	@ResponseBody
	public JSONObject getTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");

		String dsalias = "";
		String objectName = "";
		String isTable = "";

		JSONObject result = new JSONObject();

		if (StringUtil.isEmpty(id)) {
			dsalias = RequestUtil.getString(request, "dsalias");
			isTable = RequestUtil.getString(request, "isTable");
			objectName = RequestUtil.getString(request, "objName");
		}
		
		String dbType= SysDataSourceUtil. getDbType(dsalias);
		DbContextHolder.setDataSource(dsalias, dbType);// 转换这次进程的数据源

		Table table = null;
		// 表
		if (isTable.equals("1")) {
			BaseTableMeta baseTableMeta = MetaDataUtil.getBaseTableMetaAfterSetDT(dbType);// 获取表操作元

			table = baseTableMeta.getTableByName(objectName);
		} else {
			IViewOperator iViewOperator = MetaDataUtil.getIViewOperatorAfterSetDT(dbType);
			table = iViewOperator.getModelByViewName(objectName);
		}

		result.accumulate("table", table);

		return result;
	}

	/**
	 * 按照id返回对象
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             BpmDefExtProperties
	 */
	@RequestMapping("getById")
	@ResponseBody
	public CustomQuery getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		CustomQuery customQuery = null;
		if (StringUtil.isNotEmpty(id)) {
			customQuery = customQueryManager.get(id);
		}
		return customQuery;
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("doQuery")
	@ResponseBody
	public PageJson doQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String alias = RequestUtil.getString(request, "alias");
		String needPage = RequestUtil.getString(request, "needPage","");
		String queryData = RequestUtil.getString(request, "querydata");
		int page = RequestUtil.getInt(request, "page", 1);
		
		CustomQuery customQuery = customQueryManager.getByAlias(alias);
		if (customQuery==null) {
			return null;
		}
		int pageSize = RequestUtil.getInt(request, "pageSize", customQuery.getPageSize());
		
		String dbType= SysDataSourceUtil. getDbType(customQuery.getDsalias());
		
		if(StringUtil.isNotEmpty(needPage)){
			customQuery.setNeedPage("false".equals(needPage)?0:1);
		}
		
		// 切换这次进程的数据源
		DbContextHolder.setDataSource(customQuery.getDsalias(),dbType);
		PageList pageList = (PageList)customQueryManager.getData(customQuery, queryData,dbType, page, pageSize);
		
		return new PageJson(pageList);
	}
	
	@RequestMapping("getAll")
	@ResponseBody
	public List<CustomQuery> getAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<CustomQuery> customQuerys = customQueryManager.getAll();
		return customQuerys;
	}
	
	

	
}
