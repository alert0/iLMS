package com.hotent.base.core.util;

import java.util.List;

import com.hotent.base.core.util.BeanUtils;
import com.hotent.base.core.util.GsonUtil;
import com.hotent.base.db.model.DefaultQueryFilter;
import com.hotent.base.db.mybatis.domain.PageList;

import net.sf.json.JSONObject;

public class RestfulReturnUtil {
	
	
	/**
	 * 构建pagelist的返回json数据。
	 * @param result
	 * @param list
	 * @param queryFilter
	 */
	public static void buildListReuslt(JSONObject result,PageList list , DefaultQueryFilter queryFilter){
		result.put("totalCount", list.getPageResult().getTotalCount());
		result.put("pageSize", queryFilter.getPage().getPageSize());
		result.put("currentPage", queryFilter.getPage().getPageNo());
		result.put("size", list.size());
		result.put("state", "0");
		result.put("msg", "操作成功");
		String json=com.alibaba.fastjson.JSONObject.toJSONString(list);
		
		result.put("list", json);
	}
	
	
	/**
	 * 构建list 返回 json数据。
	 * @param result
	 * @param list
	 */
	public static void bulidListResult(JSONObject result,List list){
		result.put("totalCount", list.size());
		result.put("state", "0");
		result.put("msg", "操作成功");
		result.put("list", GsonUtil.toJsonTree(list).toString());
	}
	
	/**
	 * 构建消息。
	 * @param success
	 * @param message
	 * @param rtnObj
	 * @return
	 */
	public static String bulidMessage(boolean success,String message,Object rtnObj){
		JSONObject result = new JSONObject();
		result.put("state", success ? "0" : "-1");
		if (success)
			result.put("msg", "操作成功： " + message);
			if(BeanUtils.isNotEmpty(rtnObj)){
				result.accumulate("rtn", rtnObj);
			}
		if (!success)
			result.put("msg", "操作失败：" + message);
		return result.toString();
	}

}
