//package com.hotent.mini.web.util;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import net.sf.json.JSONObject;
//import com.hotent.base.core.util.BeanUtils;
//import com.hotent.bo.api.model.DataObject;
//
///**
// * DataObject的帮助类
// * @author heyifan
// * @version 创建时间: 2014-12-17
// */
//public class DataObjectUtil {
//	/**
//	 * 通过传入的权限处理DataObject(移除权限为隐藏的BO数据)
//	 * @param permission 权限信息
//	 * @param dataObjects DataObject列表
//	 */
//	@SuppressWarnings("unchecked")
//	public static void handlerBOData(String permission, List<DataObject> dataObjects){
//		if(BeanUtils.isEmpty(dataObjects))return;
//		JSONObject permissionObj = JSONObject.fromObject(permission);
//		Map<String,DataObject> dataObjectsMap = new HashMap<String, DataObject>();
////		for(DataObject dataObject:dataObjects){
////			dataObjectsMap.put(dataObject.getBoDef().getName(), dataObject);
////		}
//		//处理字段
//		Object fieldObj = permissionObj.get("field");
//		if(BeanUtils.isNotEmpty(fieldObj)){
//			JSONObject fieldJObject = JSONObject.fromObject(fieldObj);
//			Iterator<String> keys = fieldJObject.keys();
//			while(keys.hasNext()){
//				String key = keys.next();
//				String val = fieldJObject.getString(key);
//				if(!"y".equals(val))continue;
//				removeValueFromBO(key, dataObjectsMap);
//			}
//		}
//		//处理属性
//		Object tableObj = permissionObj.get("table");
//		if(BeanUtils.isNotEmpty(tableObj)){
//			JSONObject tableJObject = JSONObject.fromObject(tableObj);
//			Iterator<String> keys = tableJObject.keys();
//			while(keys.hasNext()){
//				String key = keys.next();
//				String val = tableJObject.getString(key);
//				if(!"y".equals(val))continue;
//				removeValueFromBO(key, dataObjectsMap);
//			}
//		}
//	}
//	
//	//TODO 需要修改
//	private static void removeValueFromBO(String path, Map<String,DataObject> dataObjectsMap){
//		Pattern regex = Pattern.compile("^(\\w*)\\$\\$.*");
//		Matcher regexMatcher = regex.matcher(path);
//		if(regexMatcher.matches()){
//			String key = regexMatcher.group(1);
//			DataObject dataObject = dataObjectsMap.get(key);
//			if(BeanUtils.isNotEmpty(dataObject)){
//				String dataObjectKey = path.replaceAll("\\$\\$", ".");
//				
//			}
//		}
//	}
//}
