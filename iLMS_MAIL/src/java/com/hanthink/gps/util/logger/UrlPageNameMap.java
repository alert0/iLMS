package com.hanthink.gps.util.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UrlPageNameMap {

	private Properties property;
	private static UrlPageNameMap map;

	private UrlPageNameMap() {
		InputStream inputStream = UrlPageNameMap.class.getResourceAsStream("/UrlPageName.properties");
		property = new Properties();
		try {
			property.load(inputStream);
		} catch (IOException e) {
			LogUtil.error("read UrlPageName.properties error ");
			throw new RuntimeException(e);
		}
	}

	public static UrlPageNameMap getInstance() {
		if (map == null) {
			map = new UrlPageNameMap();
		}
		return map;
	}

	public String getPageName(String url) {
		String pageName = property.getProperty(url);
		return (pageName == "") ? url : pageName;
	}
}
