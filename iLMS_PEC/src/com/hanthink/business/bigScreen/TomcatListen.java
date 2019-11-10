package com.hanthink.business.bigScreen;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.adam.advantech.AdamReadTypes;
import com.adam.advantech.AdamTypes;
import com.adam.advantech.AdvantechConfiguration;
import com.adam.advantech.AdvantechConnectionStatus;
import com.adam.advantech.AdvantechReceiveListener;
import com.adam.advantech.AdvantechReceiveRecord;
import com.adam.advantech.AdvantechService;
import com.hanthink.mon.manager.MonEmptyReturnKbManager;
import com.hanthink.mon.model.EmptyReturnKbModel;

public class TomcatListen implements ServletContextListener {

	private MonEmptyReturnKbManager monEmptyReturnKbManager;

	public void AdamServicesControl(String[] IP, Integer initStatus, ServletContextEvent servletContextEvent) {
		if (null == IP || IP.length < 0) {

		} else {
			monEmptyReturnKbManager = WebApplicationContextUtils
					.getWebApplicationContext(servletContextEvent.getServletContext())
					.getBean(MonEmptyReturnKbManager.class);
			for (String combIp : IP) {
				// 指定IO模块IP
				AdvantechService service;
				String adamIP = combIp;
				if (initStatus != 88) {
					service = new AdvantechService(adamIP);
					service.setCacheCount(1);

					// IO模块连接配置（端口号、IO模块类型、读取类型、定时读取时间）
					AdvantechConfiguration config = new AdvantechConfiguration(502, AdamTypes.ADAM6250,
							AdamReadTypes.DI_DO, 100);
					// 连接超时时间，默认1000毫秒
					config.setConnectTimeout(1000);
					// 接收超时时间，默认1000毫秒
					config.setReceiveTimeout(1000);

					// 只有启动服务才能访问IO模块
					AdvantechConnectionStatus status = service.start(config);
					if (status != AdvantechConnectionStatus.START_SUCCESS) {
						switch (status) {
						case INIT_FAILURE:
							initStatus = 0;// 初始化失败
							break;
						case USED:
							initStatus = 1;// IO模块已在使用
							break;
						case TYPE_NOT_EXSITS:
							initStatus = 2;// IO类型未定义
							break;
						case START_FAILURE:
							initStatus = 3;// 启动失败
							break;
						default:
							break;
						}
						return;
					}
					if (status == AdvantechConnectionStatus.START_SUCCESS && initStatus == 5) {
						service.addReceiveListener(new AdvantechReceiveListener() {
							@Override
							public void receive(AdvantechReceiveRecord record) {
								if (null != record.getDIs()) {
									if (record.getDIs()[4]) {
										System.err.println(combIp);
										// 根据combIp找到与之对应的看板IP
										EmptyReturnKbModel model = monEmptyReturnKbManager.selectIpByCombIp(combIp);
										/**
										 * 根据IP取到站台
										 */
										Map<String, String> paramMap = new HashMap<String, String>();
										paramMap.put("ip", model.getIp());
										paramMap.put("factoryCode", model.getFactoryCode());
										paramMap.put("opeUser", "monKb");
										paramMap.put("retEmptyPlatform", model.getRetEmptyPlatform());
										// 根据站台修改DCS单的状态
										try {
											monEmptyReturnKbManager.updateDCS(paramMap);
											// 修改完成发送完成标识到前端重新获取数据
											List<String> list = new ArrayList<>();
											list.add("0");
											WebsocketByAnnotation.sendToUser(paramMap.get("ip"), list);
										} catch (Exception e) {
											e.printStackTrace();
										}
										
									}
								}
							}
						});
					} else {
						service.stop();
						AdvantechService.stopAll();
					}
				}
			}
		}
	}

	// tomcat服务开启
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext con = servletContextEvent.getServletContext();
		String projPath = con.getRealPath("/WEB-INF/classes");
		String filePath = "com/hanthink/business/bigScreen";
		String fileName = "CombIPConfig.properties";
		String fileRealPath = projPath + File.separator + filePath + File.separator + fileName;
		String[] IP = null;
		try {
			String propRes = getFecthIPAddrress(fileRealPath, "IP");
			IP = propRes.split(";");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.AdamServicesControl(IP, 5, servletContextEvent);
	}

	// tomcat服务关闭
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		String[] IP = null;
		this.AdamServicesControl(IP, 88, servletContextEvent);
	}

	private static String getFecthIPAddrress(String propFileName, String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(propFileName));
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
}