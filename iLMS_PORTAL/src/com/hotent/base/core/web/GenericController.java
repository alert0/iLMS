package com.hotent.base.core.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.hotent.base.api.model.ResultMessage;
import com.hotent.base.api.query.Direction;
import com.hotent.base.api.query.FieldLogic;
import com.hotent.base.api.query.FieldSort;
import com.hotent.base.api.query.QueryFilter;
import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.string.StringUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.DefaultFieldSort;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;

public class GenericController {
	
	public ModelAndView getAutoView() throws Exception
	{
		HttpServletRequest request = RequestContext.getHttpServletRequest();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		int cxtIndex = requestURI.indexOf(contextPath);
		if (cxtIndex != -1)
		{
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}
		return new ModelAndView(requestURI);
	}
	
	/**
	 * 通过Request的URL对应转成对应的JSP文件展示
	 * 
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getAssignView(String view) throws Exception
	{
		
		HttpServletRequest request = RequestContext.getHttpServletRequest();
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		int cxtIndex = requestURI.indexOf(contextPath);
		if (cxtIndex != -1)
		{
			requestURI = requestURI.substring(cxtIndex + contextPath.length());
		}
		String jspPath = null;

		jspPath =   StringUtil.upperFirst(view) ;
		
		return new ModelAndView(jspPath);
	}
	
	/**
	 * 返回出错或成功的信息。
	 * 
	 * @param writer
	 * @param resultMsg
	 * @param successFail
	 */
	protected void writeResultMessage(PrintWriter writer, String resultMsg, int successFail)
	{
		ResultMessage resultMessage = new ResultMessage(successFail, resultMsg);
		writeResultMessage(writer, resultMessage);
	}

	/**
	 * 返回出错或成功的信息。
	 * 
	 * @param writer
	 * @param resultMsg
	 * @param successFail
	 */
	protected void writeResultMessage(PrintWriter writer, String resultMsg, String cause, int successFail)
	{
		ResultMessage resultMessage = new ResultMessage(successFail, resultMsg, cause);
		writeResultMessage(writer, resultMessage);
	}
	
	/**
	 * 返回错误或成功信息（增加返回数据）
	 * @param writer
	 * @param resultMsg
	 * @param cause
	 * @param data
	 * @param successFail
	 * @author ZUOSL	
	 * @DATE	2018年10月14日 下午9:21:04
	 */
	protected void writeResultMessage(PrintWriter writer, String resultMsg, String cause, JSONObject data, int successFail){
		ResultMessage resultMessage = new ResultMessage(successFail, resultMsg, cause, data);
		writeResultMessage(writer, resultMessage);
	}

	/**
	 * 返回出错或成功的信息。
	 * 
	 * @param writer
	 * @param resultMessage
	 */
	protected void writeResultMessage(PrintWriter writer, ResultMessage resultMessage)
	{
		writer.print(resultMessageToString(resultMessage));
	}
	
	private String resultMessageToString(ResultMessage resultMessage)
	{
		JSONStringer stringer = new JSONStringer();
		stringer.object();
		stringer.key("result");
		stringer.value(resultMessage.getResult());
		stringer.key("message");
		stringer.value(resultMessage.getMessage());
		stringer.key("cause");
		stringer.value(resultMessage.getCause());
		stringer.key("data");
		stringer.value(resultMessage.getData());
		stringer.endObject();
		return stringer.toString();
	}
	
	/**
	 * 返回构建的QueryFilter
	 * 
	 * @param request
	 * @return QueryFilter
	 * @exception
	 * @since 1.0.0
	 */
	protected QueryFilter getQueryFilter(HttpServletRequest request){
		DefaultQueryFilter queryFilter = new DefaultQueryFilter();
		try{
			String defaultWhere= request.getParameter("defaultWhere");
			if(StringUtil.isNotEmpty(defaultWhere))queryFilter.addParamsFilter("defaultWhere", defaultWhere);
			
			String pageSize = request.getParameter("page");
			// 页大小
			String rows = (String) request.getParameter("rows");
			// 设置查询字段
			FieldLogic andFieldLogic = RequestUtil.getFieldLogic(request);
			queryFilter.setFieldLogic(andFieldLogic);
			// 设置分页
			if (StringUtil.isNotEmpty(pageSize) && StringUtil.isNotEmpty(rows)){
				DefaultPage page = new DefaultPage();
				page.setPage(Integer.parseInt(pageSize));
				page.setLimit(Integer.parseInt(rows));
				queryFilter.setPage(page);
			}
			// 设置排序字段
			String sort = request.getParameter("sort");
			//sort = ConvertSortName(request, sort);
			String order = request.getParameter("order");
			if (StringUtil.isNotEmpty(sort) && StringUtil.isNotEmpty(order)){
				List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
				fieldSorts.add(new DefaultFieldSort(sort, Direction.fromString(order)));
				queryFilter.setFieldSortList(fieldSorts);
			}
		} catch (Exception e)
		{
		}
		return queryFilter;
	}

	
	/**
	 * 返回
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String,Object> getQueryResult(List list){
		Map<String, Object> returnMap = getResult(true);
		returnMap.put("list", list);
		returnMap.put("pageResult", ((PageList)list).getPageResult());
		return returnMap;
	}
	
	/**
	 * 获取Map中指定key的字符串值
	 * @param map
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	protected String getString(Map<String,Object> map,String key,String defaultVal){
		Object object = map.get(key);
		if(BeanUtils.isEmpty(object)){
			return defaultVal;
		}
		return object.toString().trim();
	}
	
	/**
	 * 获取Map中指定key的整型值
	 * @param map
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	protected Integer getInt(Map<String,Object> map,String key,Integer defaultVal){
		String str = getString(map, key, "");
		if(StringUtil.isEmpty(str)){
			return defaultVal;
		}
		return Integer.parseInt(str);
	}
	
	/**
	 * 获取要返回的结果
	 * @param result
	 * @return
	 */
	protected Map<String,Object> getResult(Boolean result){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	/**
	 * 获取要返回的结果
	 * @param result
	 * @param message
	 * @return
	 */
	protected Map<String,Object> getResult(Boolean result,String message){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("message", message);
		return map;
	}
	
	protected String buildResult(String id,boolean isAdd){
		JSONObject obj=new JSONObject();
		obj.put("id", id);
		obj.put("add", isAdd);
		return obj.toString();
	}
	
	
}