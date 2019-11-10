package com.hanthink.gps.util.Msg;


import java.net.ConnectException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import com.esms.MOMsg;
import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.BusinessType;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTReport;
import com.esms.common.entity.MTResponse;
import com.esms.common.util.MediaUtil;
import com.esms.common.entity.MTPack.SendType;

public class PostMsgMain {
	
	@Test
	public void test(){
		try {
			extend(); //扩展接口范例
//			compatibility(); //兼容接口范例
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//扩展接口范例
	public static void extend() throws Exception{
		Account ac = new Account("xt2", "123456");	
		PostMsg pm = new PostMsg();
		//mos网关
//		pm.getCmHost().setHost("211.147.239.62", 9080);//设置网关的IP和port，用于发送信息
//		pm.getWsHost().setHost("211.147.239.62", 9070);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等
		pm.getCmHost().setHost("172.18.83.20", 8090);//设置网关的IP和port，用于发送信息
		pm.getWsHost().setHost("172.18.83.20", 8088);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等
//400网关
//		pm.getCmHost().setHost("211.147.239.62", 8450);//设置网关的IP和port，用于发送信息
//		pm.getWsHost().setHost("211.147.239.62", 8460);//设置网关的 IP和port，用于获取账号信息、上行、状态报告等等
//		/**代理上网设置,如果需要*/
//		HostInfo proxyHost = new HostInfo("192.168.0.47", 1080);
//		proxyHost.setType(HostInfo.ConnectionType.SOCKET4);  	//设置连接类型
//		proxyHost.setUsername("004");						//设置用户名
//		proxyHost.setPassword("123");						//设置密码
//		pm.getProxy().setProxy(proxyHost);					//设置代理			
		
		doSendSms(pm, ac); //短信下行
//		doSendMms(pm, ac); //彩信下行
		
		doGetAccountInfo(pm, ac); //获取账号信息
//		doModifyPwd(pm, ac); //修改密码
		
//		doFindResps(pm, ac); //查询提交报告
//		doFindReports(pm, ac); //查询状态报告
	
//		doGetMos(pm, ac); //获取上行信息
//		doGetResps(pm, ac); //获取提交报告
//		doGetReports(pm, ac); //获取状态报告
	}
	
	/**
	 * 短信下发范例
	 * @param pm
	 * @param ac
	 */
	public static void doSendSms(PostMsg pm, Account ac) throws Exception{
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());
		pack.setBatchName("短信测试批次");
		pack.setMsgType(MTPack.MsgType.SMS);
		pack.setBizType(0);
		pack.setDistinctFlag(false);
		pack.setSendType(SendType.GROUP);
		ArrayList<MessageData> msgs = new ArrayList<MessageData>();
		
//		/** 单发，一号码一内容 */
//		msgs.add(new MessageData("13430258111", "接口短信单发测试"));
		msgs.add(new MessageData("13655098693", "接口短信单发测试"));

		pack.setMsgs(msgs);
		
		/** 群发，多号码一内容 */
//		pack.setSendType(MTPack.SendType.MASS);
//		String content = "短信发送测试";
//		msgs.add(new MessageData("13430258111", content));
//		msgs.add(new MessageData("13430258222", content));
//		msgs.add(new MessageData("13430258333", content));
//		pack.setMsgs(msgs);
		
//		/** 组发，多号码多内容 */
//		pack.setSendType(SendType.GROUP);
//		msgs.add(new MessageData("13430258111", "短信组发测试111"));
//		msgs.add(new MessageData("13430258222", "短信组发测试222"));
//		msgs.add(new MessageData("13430258333", "短信组发测试333"));
//		pack.setMsgs(msgs);
		
		/** 设置模板编号(静态模板将以模板内容发送; 动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"},{"key":"变量名2","value":"变量值2"}]) */
		//pack.setTemplateNo("test");
		
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}
	
	/**
	 * 彩信下发范例
	 * @param pm
	 * @param ac
	 */
	public static void doSendMms(PostMsg pm, Account ac) throws Exception{
		MTPack pack = new MTPack();
		pack.setBatchID(UUID.randomUUID());
		pack.setBatchName("彩信测试批次");
		pack.setMsgType(MTPack.MsgType.MMS);
		pack.setBizType(1);
		pack.setDistinctFlag(false);
		ArrayList<MessageData> msgs = new ArrayList<MessageData>();

		String path = PostMsgMain.class.getClassLoader().getResource("mms_test").getPath();
		path = URLDecoder.decode(path, "utf-8");
		
		//设置公共彩信资源
		pack.setMedias(MediaUtil.getMediasFromFolder(path));
		
//		/** 单发，一号码一内容 */
//		msgs.add(new MessageData("13430258111", null));
//		pack.setMsgs(msgs);
		
		/** 群发，多号码一内容 */
		pack.setSendType(MTPack.SendType.MASS);
		msgs.add(new MessageData("13430258111", null));
		msgs.add(new MessageData("13430258222", null));
		msgs.add(new MessageData("13430258333", null));
		pack.setMsgs(msgs);
		
//		/** 组发，多号码多内容 */
//		pack.setSendType(MTPack.SendType.GROUP);
//		//设置私有彩信资源
//		MessageData msg1 = new MessageData("13430258111", null);
//		msg1.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg1"));
//		msgs.add(msg1);
//		MessageData msg2 = new MessageData("13430258222", null);
//		msg2.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg2"));
//		msgs.add(msg2);
//		MessageData msg3 = new MessageData("13430258333", null);
//		msg3.setMedias(MediaUtil.getMediasFromFolder("F:/mms_test/msg3"));
//		msgs.add(msg3);
//		pack.setMsgs(msgs);
		
		/** 设置模板编号(静态模板将以模板内容发送; 动态模板需要发送变量值，JSON格式：[{"key":"变量名1","value":"变量值1"},{"key":"变量名2","value":"变量值2"}]) */
		//pack.setTemplateNo("test");
		
		GsmsResponse resp = pm.post(ac, pack);
		System.out.println(resp);
	}
	
	/**
	 * 获取账号信息
	 * @param pm
	 * @param account
	 * @throws Exception
	 */
	public static void doGetAccountInfo(PostMsg pm, Account ac) throws Exception{
		System.out.println(pm.getAccountInfo(ac));   //获取账号详细信息
		
		BusinessType[] bizTypes = pm.getBizTypes(ac); //获取账号绑定业务类型
		if(bizTypes != null){
			for(BusinessType bizType : bizTypes){
				System.out.println(bizType);
			}
		}
	}
	
	/**
	 * 获取上行信息
	 * @param pm
	 * @param account
	 * @throws Exception
	 */
	public static void doGetMos(PostMsg pm, Account ac) throws Exception{
		MOMsg[] mos = pm.getMOMsgs(ac, 100);
		if(mos != null){
			for(MOMsg mo : mos){
				System.out.println(mo);
			}
		}
	}
	
	/**
	 * 查询提交报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doFindResps(PostMsg pm, Account ac) throws Exception{	
		UUID batchID = UUID.fromString("3e1f13f4-1677-41f1-b67d-702f2c01eafb"); //如果需要按批次ID来查询
		MTResponse[] foundMtResps = pm.findResps(ac, 1, batchID, null, 0);
		if(foundMtResps != null){
			for(MTResponse resp : foundMtResps){
				System.out.println(resp);
			}
		}
	}
	
	/**
	 * 获取提交报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doGetResps(PostMsg pm, Account ac) throws Exception{	
		MTResponse[] mtResps = pm.getResps(ac, 100);
		if(mtResps != null){
			for(MTResponse resp : mtResps){
				System.out.println(resp);
			}
		}
	}
	
	/**
	 * 查询状态报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doFindReports(PostMsg pm, Account ac) throws Exception {
		UUID batchID = UUID.fromString("3e1f13f4-1677-41f1-b67d-702f2c01eafb"); //如果需要按批次ID来查询
		MTReport[] foundMtReports = pm.findReports(ac, 1, batchID, null, 0);
		if(foundMtReports != null){
			for(MTReport report : foundMtReports){
				System.out.println(report);
			}
		}
	}
	
	/**
	 * 获取状态报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	public static void doGetReports(PostMsg pm, Account ac) throws Exception{	
		MTReport[] mtReports = pm.getReports(ac, 100);
		if(mtReports != null){
			for(MTReport report : mtReports){
				System.out.println(report);
			}
		}
	}
	
	/**
	 * 修改密码
	 * @param pm
	 * @param ac
	 * @throws Exception 
	 */
	public static void doModifyPwd(PostMsg pm, Account ac) throws Exception{	
		System.out.println(pm.modifyPassword(ac, "123456"));
	}
	
	//兼容接口范例
	public static void compatibility() throws ConnectException{
//		Account ac = new Account("It@gqcy", "Ree7618@");

		//设置部分
		PostMsg pm = new PostMsg("It@gqcy", "Ree7618@");		//创建实例时，需输入用户名与密码
		pm.getCmHost().setHost("172.18.83.20", 8090);  	//设置网关的IP和port
		pm.getWsHost().setHost("172.18.83.20", 8088);  //设置WebService的 IP和port
//		pm.getProxy().setProxy(ProxyServer.PROXY_TYPE_SOCKS4, "192.168.0.47", 1080); //设置代理
//		
//		
		int resp = -1;
		/** 单发，一号码一内容 */
		resp = pm.post("13655098693", "短信单发测试", "");
//		
//		/** 群发，多号码一内容 */
//		resp = pm.post(new String[]{"13430258111", "13430258222", "13430258333"}, "短信群发测试", "");
//		
//		/** 组发，多号码多内容 */
//		MessageData[] msgs = new MessageData[3];
//		msgs[0] = new MessageData("13430258111", "短信组发测试111");
//		msgs[1] = new MessageData("13430258222", "短信组发测试222");
//		msgs[2] = new MessageData("13430258333", "短信组发测试333");
//		resp = pm.post(msgs, "");
		
		System.out.println("响应：" + resp);
		
		//修改密码
//		System.out.println(pm.modifyPassword("123456"));
			
//		System.out.println(pm.getConfigInfo()); 					//获取用户详细信息
//		MOMsg[] momsgs = pm.getMOMsg();								//获取上行信息
//		if(momsgs != null){
//			for(MOMsg momsg : momsgs){
//				System.out.println(momsg);
//			}
//		}
	}
}

