package com.hotent.sys.api.event;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <pre>
 * 描述：日志模块管理 模块常量
 * 构建组：x5-sys-api
 * 作者：ouxb
 * 邮箱：ouxb@jee-soft.cn
 * 日期：2014年10月29日15:59:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public enum LogModuleCons {
	BO_MODULE("bo模块"), ORG_MODULE("组织模块"), USER_MODULE("用户模块"), BPM_MODULE(
			"流程模块");
	private final String value;

	LogModuleCons(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static LogModuleCons fromValue(String v) {
		for (LogModuleCons c : LogModuleCons.values()) {
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
		for (LogModuleCons c : LogModuleCons.values()) {
			list.add(c.value);
		}
		return list;
	}
	
}
