package com.hanthink.gps.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContextHolder implements ApplicationContextAware {
	
	static ApplicationContext context;

	public void setApplicationContext(
			ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static void setAppContext(
			ApplicationContext applicationContext) {
		context = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

}
