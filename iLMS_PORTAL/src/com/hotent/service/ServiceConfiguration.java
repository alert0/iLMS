package com.hotent.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.hotent.base.core.util.BeanUtils;

public class ServiceConfiguration {
	private static ServiceConfiguration instance;

	private ApplicationContext cxt;

	private static String[] springContextXml = { "x5-service-resources.xml" };

	private static Logger logger = Logger.getLogger(ServiceConfiguration.class);

	public ServiceConfiguration() {
		logger.info("Welcome to x5 service!");
	}

	public static ServiceConfiguration getInstance() {
		if (BeanUtils.isEmpty(instance)) {
			instance = new ServiceConfiguration();
			// 载入Spring
			ApplicationContext cxt = new ClassPathXmlApplicationContext(springContextXml);
			instance.setCxt(cxt);
		}
		return instance;
	}

	public ApplicationContext getCxt() {
		return cxt;
	}

	public void setCxt(ApplicationContext cxt) {
		this.cxt = cxt;
	}

	public <T> T getBean(Class<T> beanClass) {
		return this.cxt.getBean(beanClass);
	}
}
