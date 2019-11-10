package com.hanthink.gps.util.logger;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class SystemMap {

	private Properties property;
	private static SystemMap map;
	private List<String> orderSeriseRuleLst = new ArrayList<String>();
	private List<String> orderTypeRuleLst = new ArrayList<String>();
	private List<String> orderTypeLst = new ArrayList<String>();

	private SystemMap() {
		InputStream inputStream = SystemMap.class
				.getResourceAsStream("/System.properties");
		property = new Properties();
		try {
			property.load(inputStream);
			// 解析订单规则
			String orderRuleNum = getNameValue("orderRuleNum");
			if (orderRuleNum == null)
				return;
			int orderRuleCount = Integer.parseInt(orderRuleNum);
			String orderRule = "";
			for (int i = 1; i <= orderRuleCount; i++) {
				orderRule = getNameValue("orderRuleNo" + i);
				if (orderRule != null) {
					StringTokenizer st = new StringTokenizer(orderRule, ":");
					for (int j = 0; st.hasMoreTokens(); j++) {
						switch (j) {
						case 0:orderSeriseRuleLst.add(st.nextToken());
							break;
						case 1:
							orderTypeRuleLst.add(st.nextToken());
							break;
						case 2:
							orderTypeLst.add(st.nextToken());
							break;
						default:
						}
					}
				}
			}
		} catch (IOException e) {
			LogUtil.error("read System.properties error ");
			throw new RuntimeException(e);
		}
	}

	public static SystemMap getInstance() {
		if (map == null) {
			map = new SystemMap();
		}
		return map;
	}

	public String getNameValue(String name) {
		String value = property.getProperty(name);
		return (value == "") ? name : value;
	}
	
	public List<String> getOrderSeriseRuleLst(){
		return orderSeriseRuleLst;
	}

	public List<String> getOrderTypeRuleLst() {
		return orderTypeRuleLst;
	}

	public void setOrderTypeRuleLst(List<String> orderTypeRuleLst) {
		this.orderTypeRuleLst = orderTypeRuleLst;
	}

	public List<String> getOrderTypeLst() {
		return orderTypeLst;
	}

	public void setOrderTypeLst(List<String> orderTypeLst) {
		this.orderTypeLst = orderTypeLst;
	}
}
