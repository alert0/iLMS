package com.hotent.form.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.core.util.JsonUtil;
import com.hotent.base.core.util.string.StringUtil;

public class FreeMakerUtil
{
	// 指令，如果为空则不添加
	public String getAttrs(String attrNames,Object f){
		StringBuffer sb = new StringBuffer();
		JSONObject field = JSONObject.fromObject(f);
		JSONObject option =JSONObject.fromObject(field.getJSONObject("option"));
		
		String [] attrs = attrNames.split(",");
		for (String attr : attrs) {
			
			String attrStr = "" ;
			if("ht-funcexp".equals(attr)){
				attrStr = JsonUtil.getString(option, "statFun");
			}
			// 校验
			else if("ht-validate".equals(attr)){
				JSONObject validate = field.getJSONObject("validRule");
				if(validate.containsKey("rules")){
					JSONArray array =validate.getJSONArray("rules");
					validate.remove("rules");
					for (int i = 0; i < array.size(); i++) {
						JSONObject	rule = (JSONObject) array.get(i);
						validate.put(rule.getString("text"),true);
					}
				}
				attrStr =validate.toString();
				
				if(validate.isEmpty())attrStr = "{}";
			}
			
			else if("selectquery".equals(attr)){
				attrStr =JsonUtil.getString(option,"jlsz");
			}
			
			else if("ht-date".equals(attr)){
				attrStr =JsonUtil.getString(option,"dataFormat");
			}
			//number 格式化
			else if("ht-number".equals(attr)){
				attrStr =JsonUtil.getString(option,"numberFormat");
				if(StringUtil.isEmpty(attrStr) && "number".equals(field.getString("type")))
					attrStr= "{}";
			}
			//日期计算
			else if("ht-datecalc".equals(attr)){
				attrStr =JsonUtil.getString(option,"datecalc");
			}
			// 编辑器
			else if("ht-editor".equals(attr)){
				if(field.containsKey("isEditor")&&field.getBoolean("isEditor")){
					attrStr = option.toString();
				}
			}
			
			else if("ht-office-plugin".equals(attr)){
				sb.append(" style='width:"+option.getString("width")+"px;height:"+option.getString("height")+"px' ");
				
				if(option.containsKey("doctype")) sb.append(" doctype='"+option.getString("doctype")+"'  ");
				
				sb.append(" ht-office-plugin ");
			}
			
			// eg: ht-number-format='{formatJson} ' 
			if(StringUtil.isNotEmpty(attrStr)){
				sb.append(attr).append("='").append(attrStr).append("' ");
			}
		}
		return sb.toString();
	}
	
	public String getCtrlDate(Object field){
		JSONObject tmp = JSONObject.fromObject(field);
		JSONObject option =JSONObject.fromObject(tmp.getJSONObject("option"));
		String attrStr =JsonUtil.getString(option,"dataFormat");
		if(StringUtil.isEmpty(attrStr)){
			return "mobiscrollDate=date'";
		}
		//{"dataFormat":"yyyy-MM-dd HH:mm:ss"}
		if("yyyy-MM-dd HH:mm:ss".equals(attrStr)){
			return "mobiscrollDate='datetime'";
		}
		
		if("HH:mm:ss".equals(attrStr)){
			return "mobiscrollDate='time'";
		}
		
		return "mobiscrollDate='date'";
	}
	
	
	
	/**
	 * 通过json字符串获取attr属性
	 * 
	 * @param json
	 * @param attr
	 * @return
	 */
	public String getJsonByPath(Object o, String path)
	{
		JSONObject jsonObject = JSONObject.fromObject(o);
		if (jsonObject.isEmpty())
			return "";
		String[] pathList = path.split("\\.");
		if (pathList.length > 1)
		{
			if (jsonObject.containsKey(pathList[0]))
			{
				String tempJson = jsonObject.getJSONObject(pathList[0]).toString();
				return getJsonByPath(tempJson, StringUtils.join(ArrayUtils.remove(pathList, 0), "."));
			}
		} else
		{
			if (jsonObject.containsKey(path))
			{
				return jsonObject.getString(path);
			}
		}
		return "";

	}

	public String getSubList(String jsonList, int begin, int end)
	{
		String[] array = jsonList.split(",");
		String rtn = "";
		for (int i = 0; i < array.length && (i >= begin && i <= end); i++)
		{
			rtn += array[i] + ",";
		}
		return rtn.substring(0, rtn.length() - 1);
	}



	/**
	 * 解析表单字段的option字段，生成ht-complex指令的配置json(selector类型)
	 * 
	 * @param json
	 * @return
	 */
	public String getHtSelector(Object option,Boolean tag)
	{
		JSONObject fromObject = JSONObject.fromObject(option);
		if(tag == null) tag=false;
		if (fromObject.isEmpty()) return "{}";
		
		JSONObject returnObj = new JSONObject();
		JSONObject selectorObj = GsonUtil.getValue(fromObject, "selector", null, JSONObject.class);
		if (BeanUtils.isNotEmpty(selectorObj))
		{
			returnObj.accumulate("isSingle", GsonUtil.getValue(selectorObj, "isSingle", false, Boolean.class));
			//returnObj.accumulate("display", GsonUtil.getValue(selectorObj, "display", "name", String.class)); 
			returnObj.accumulate("showCurrentUserName", GsonUtil.getValue(selectorObj, "showCurrentUserName", false, Boolean.class));
			returnObj.accumulate("showCurrentUserDeptName", GsonUtil.getValue(selectorObj, "showCurrentUserDeptName", false, Boolean.class));
			returnObj.accumulate("showCurrentUserPostName", GsonUtil.getValue(selectorObj, "showCurrentUserPostName", false, Boolean.class));
			JSONObject typeObj = GsonUtil.getValue(selectorObj, "type", null, JSONObject.class);
			if (BeanUtils.isNotEmpty(typeObj))
			{
				returnObj.accumulate("type", GsonUtil.getValue(typeObj, "alias", "", String.class));
			}
		}
		JSONArray bindAry = GsonUtil.getValue(fromObject, "bind", null, JSONArray.class);
		JSONObject bindObj = new JSONObject();
		if(BeanUtils.isNotEmpty(bindAry)){
			for (Object obj : bindAry)
			{
				JSONObject jobject = (JSONObject) obj;
				String key = GsonUtil.getValue(jobject, "key", "", String.class);
				JSONObject jsonObj = GsonUtil.getValue(jobject, "json", null, JSONObject.class);
				
				String path = "data.";
				if (BeanUtils.isNotEmpty(jsonObj))
				{
					String temPath = GsonUtil.getValue(jsonObj, "path", "", String.class);
					if (tag)
					{
						path = "item.";
					} else
					{
						path += temPath;
						path += ".";
					}

					path += GsonUtil.getValue(jsonObj, "name", "", String.class);

				}
				bindObj.accumulate(key, path);
			}
		}
		
		returnObj.accumulate("bind", bindObj);
		String returnStr = returnObj.toString();
		if (StringUtil.isEmpty(returnStr))
			returnStr = "";
		return returnStr.replaceAll("\"", "'");
	}



	/**
	 * 解析表单字段的option字段
	 * 
	 * @param json
	 * @return
	 */
	public String getSelectQuery(Object option ,Boolean isSub)
	{
		if(isSub == null) isSub=false;
		if (BeanUtils.isEmpty(option))
			return "{}"; 
		JSONObject returnObj = new JSONObject();
		JSONObject fromObject = JSONObject.fromObject(option);
		JSONObject customQuery = GsonUtil.getValue(fromObject, "customQuery", null, JSONObject.class);
		if (BeanUtils.isNotEmpty(customQuery))
		{
			returnObj.accumulate("alias", GsonUtil.getValue(customQuery, "alias", "", String.class));
			returnObj.accumulate("valueBind", GsonUtil.getValue(customQuery, "valueBind", "", String.class));
			returnObj.accumulate("labelBind", GsonUtil.getValue(customQuery, "labelBind", "", String.class));
		}
		JSONArray bindAry = GsonUtil.getValue(fromObject, "bind", null, JSONArray.class);
		JSONObject bindObj = new JSONObject();
		if(bindAry != null)
		for (Object obj : bindAry)
		{
			JSONObject jobject = (JSONObject) obj;
			JSONObject target = GsonUtil.getValue(jobject, "json", null, JSONObject.class);
			if (BeanUtils.isEmpty(target))
				continue;
			String key = GsonUtil.getValue(jobject, "field", "", String.class);
			String path = "data.";
			if(isSub){
				path = "item.";
			}
			else{
				path += GsonUtil.getValue(target, "path", "", String.class);
				path += ".";
			}
			
			path += GsonUtil.getValue(target, "name", "", String.class);
			bindObj.accumulate(key, path);
		}
		returnObj.accumulate("bind", bindObj);
		String returnStr = returnObj.toString();
		if (StringUtil.isEmpty(returnStr))
			returnStr = "";
		return returnStr.replaceAll("\"", "'");
	}
	
	/**
	 * 获取字段联动信息
	 * @param ptName
	 * @param gangedStr
	 * @return
	 */
	public String getFieldGanged(String ptName,String gangedStr){
		String returnStr = "";
		try {
			JSONArray returnArray = new JSONArray();
			JSONArray array = JSONArray.fromObject(gangedStr);
			for (Object object : array) {
				JSONObject obj = JSONObject.fromObject(object);
				JSONArray chooseFields = obj.getJSONArray("chooseField");
				for (Object chooseField : chooseFields) {
					JSONObject csField = JSONObject.fromObject(chooseField);
					if(ptName.equals(csField.getString("pathName"))){
						returnArray.add(obj);
					}
				}
				
			}
			if(BeanUtils.isNotEmpty(returnArray)){
				 
				returnStr = "ht-ganged=\""+returnArray.toString().replaceAll("\"", "'")+"\"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}
	
	public String getSeparator(String separator){
		String returnStr = "";
		try {
			if(StringUtil.isNotEmpty(separator)){
				returnStr = "group='"+separator+"'";
			}
		} catch (Exception e) {}
		return returnStr;
	}
}
