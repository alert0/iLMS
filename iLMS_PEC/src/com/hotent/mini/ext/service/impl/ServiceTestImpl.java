package com.hotent.mini.ext.service.impl;

import com.hotent.mini.ext.service.ISerivceTest;

public class ServiceTestImpl implements ISerivceTest {

	@Override
	public String sayHello(String name) {
		return "hello:" + name;
	}

}
