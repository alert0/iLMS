package com.hotent.base.db.mybatis.domain;

import java.util.HashMap;
import java.util.Map;


public class PageListUtil {
	public static Map<String, Object> toJqGridMap(PageList<?> pageList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", pageList);
		map.put("records", pageList.getPageResult().getTotalCount());
		map.put("page", pageList.getPageResult().getPage());
		map.put("total", pageList.getPageResult().getTotalPages());
		return map;
	}
}
