package com.hotent.sys.api.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * 描述：日志操作类型
 * 构建组：x5-sys-api
 * 作者：ouxb
 * 邮箱：ouxb@jee-soft.cn
 * 日期：2014年10月29日15:59:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public enum LogOperatorCons {
	ADD("增加"), DEL("删除"), MODIFY("修改");
	private final String value;

	LogOperatorCons(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static LogOperatorCons fromValue(String v) {
		for (LogOperatorCons c : LogOperatorCons.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
	
	/**
	 * 获取value集合
	 * @return
	 */
	public static List<String> getValues(){
		List<String> list = new ArrayList<String>();
		for (LogOperatorCons c : LogOperatorCons.values()) {
			list.add(c.value);
		}
		return list;
	}
}
