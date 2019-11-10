package com.hanthink.mes.common.constants;

import java.io.IOException;
import java.util.Properties;


public class DBInfo {

	// database driver class name
	public static String DRIVER_CLASS_NAME = "";
	// database url
	public static String URL = "";
	//user name
	public static String USER_NAME = "";
	//password
	public static String PASSWORD = "";
	
	static {
		Properties properties = new Properties();
		try {
			properties.load(DBInfo.class.getResourceAsStream("/dbconfig.properties"));
			
			DRIVER_CLASS_NAME = properties.getProperty("drivername");
			
			URL = properties.getProperty("url");
			
			USER_NAME = properties.getProperty("username");
			
			PASSWORD = properties.getProperty("password");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
