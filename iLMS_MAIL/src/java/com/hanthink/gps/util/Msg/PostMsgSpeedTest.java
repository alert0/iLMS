package com.hanthink.gps.util.Msg;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.esms.common.entity.MTReport;
import com.esms.common.entity.MTResponse;

/**
 * 速度测试类
 */
public class PostMsgSpeedTest {

	private static AtomicLong totalReqCount = new AtomicLong(0); //请求次数
	private static AtomicLong totalSpendTime = new AtomicLong(0); //总共花费的时间
	private static AtomicLong totalMsgCount = new AtomicLong(0); //总记录数
	
	private static int perPackSize = 1;//每个下行包的大小
	private static int perGetSize = 500;//每次获取大小
	private static int findMethod = 0; //0-模糊结果，1-精确结果
	private static String[] phoneHeads = new String[]{"180", "135", "186"}; //号码段数组（其实这里也可以设置按运营商比例下发）
	private static String content = "性能测试短信"; //测试内容
	
	/**
	 * 详细测试用例
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	private void detailTest(PostMsg pm, Account ac) throws Exception {
		//无限循环发送，直到规定时间结束
		while(true) {
			doSendMsg(pm, ac);
		}
//		for(UUID uuid : getBatchIDs()){
//			doFindResp(pm, ac, uuid);
//		}
//		for(UUID uuid : getBatchIDs()){
//			doFindReport(pm, ac, uuid);
//		}		
//		doGetResp(pm, ac);
//		doGetReport(pm, ac);
	}
	
	@Test
	public void speedTest() throws Exception {
		UUID.randomUUID(); //使UUID先生成一个随机数生成器
		
		ExecutorService executor = Executors.newCachedThreadPool();
		final List<Account> acs = getAccounts();
		
		//根据用户创建线程
		for(final Account ac : acs){
			executor.execute(new Runnable(){
				public void run() {
					PostMsg pm = new PostMsg();
					pm.getCmHost().setHost("192.168.0.20", 8080);
					pm.getWsHost().setHost("192.168.0.20", 8088);
					try {
						detailTest(pm, ac);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		/** 打印结果线程 */
		executor.execute(new Runnable(){
			public void run() {
				while(true){
					try {
						Thread.sleep(1000);//打印时间间隔，毫秒
					} catch (InterruptedException e) {}
					
					long reqCount = totalReqCount.get();
					long spendTime = totalSpendTime.get();
					long msgCount = totalMsgCount.get();
					
					if(reqCount > 0){
						System.out.println("用户数:" + acCount +", 总请求数:" + reqCount + ", 平均响应(ms):" + (spendTime / reqCount) + ", 总记录数:" + msgCount);
					} else {
						System.out.println("总请求数:" + reqCount + "\t平均响应(ms):" + 0);
					}
				}
			}
		});
		
		Thread.sleep(60 * 1000);
	}
	
	private static int acCount = 0;
	
	/**
	 * 获取多账号，每一账号将会是与一个线程绑定
	 * @return
	 */
	private List<Account> getAccounts(){
		List<Account> acs = new ArrayList<Account>();
		
		//根据需要设置多个账号
//		acs.add(new Account("test01", "test"));
//		acs.add(new Account("test02", "test"));
//		acs.add(new Account("test03", "test"));
//		acs.add(new Account("test04", "test"));
//		acs.add(new Account("test05", "test"));
//		acs.add(new Account("test06", "test"));
//		acs.add(new Account("test07", "test"));
//		acs.add(new Account("test08", "test"));
//		acs.add(new Account("test09", "test"));
//		acs.add(new Account("test10", "test"));
//		acs.add(new Account("test11", "test"));
//		acs.add(new Account("test12", "test"));
//		acs.add(new Account("test13", "test"));
//		acs.add(new Account("test14", "test"));
//		acs.add(new Account("test15", "test"));
//		acs.add(new Account("test16", "test"));
//		acs.add(new Account("test17", "test"));
//		acs.add(new Account("test18", "test"));
//		acs.add(new Account("test19", "test"));
//		acs.add(new Account("test20", "test"));
//		acs.add(new Account("test21", "test"));
//		acs.add(new Account("test22", "test"));
//		acs.add(new Account("test23", "test"));
//		acs.add(new Account("test24", "test"));
//		acs.add(new Account("test25", "test"));
//		acs.add(new Account("test26", "test"));
//		acs.add(new Account("test27", "test"));
//		acs.add(new Account("test28", "test"));
//		acs.add(new Account("test29", "test"));
//		acs.add(new Account("test30", "test"));
		acCount = acs.size();
		return acs;
	}
	
	/**
	 * 获取批次列表，用于查询状态报告或提交报告，
	 * 考虑到查询接口的特殊性，必须将要查询的批次ID列举出来
	 * @return
	 */
	private List<UUID> getBatchIDs(){
		List<UUID> batchIDs = new ArrayList<UUID>();
		batchIDs.add(UUID.fromString("00cf9673-c8c8-48e4-904c-081f6f73ed61"));
//		batchIDs.add(UUID.fromString("011e2401-d29c-427f-afec-63f39a119c20"));
//		batchIDs.add(UUID.fromString("01d95f6e-d847-436f-bb23-9026f9613fa6"));
//		batchIDs.add(UUID.fromString("02d72dcf-8002-4b4c-8783-bcab108450e0"));
//		batchIDs.add(UUID.fromString("0341b05a-e4f2-4d39-a6ac-9c58a6151c7a"));
//		batchIDs.add(UUID.fromString("0386de47-b6d2-4a67-83e4-dcaffbee4bb5"));
		return batchIDs;
	}
	
	/**
	 * 查询当前批次所有提交报告
	 * @param pm
	 * @param ac
	 * @param batchID 当前批次 
	 * @throws Exception
	 */
	private void doFindResp(PostMsg pm, Account ac, UUID batchID) throws Exception {
		long startTime = 0;
		int pageIndex = 0;
		MTResponse[] resps;
		do{
			startTime = System.currentTimeMillis();
			resps = pm.findResps(ac, pageIndex, batchID, "", findMethod);
			totalSpendTime.addAndGet(System.currentTimeMillis() - startTime);
			totalReqCount.incrementAndGet();
			if(resps != null && resps.length > 0){
				totalMsgCount.addAndGet(resps.length);
			}
			pageIndex ++;
		} while(resps != null && resps.length == 100);
	}
	
	/**
	 * 查询当前批次所有状态报告
	 * @param pm
	 * @param ac
	 * @param batchID 当前批次
	 * @throws Exception
	 */
	private void doFindReport(PostMsg pm, Account ac, UUID batchID) throws Exception {
		long startTime = 0;
		int pageIndex = 0;
		MTReport[] reports;
		do{
			startTime = System.currentTimeMillis();
			reports = pm.findReports(ac, pageIndex, batchID, "", findMethod);
			totalSpendTime.addAndGet(System.currentTimeMillis() - startTime);
			totalReqCount.incrementAndGet();
			if(reports != null && reports.length > 0){
				totalMsgCount.addAndGet(reports.length);
			}
			pageIndex ++;
		} while(reports != null && reports.length == 100);
	}
	
	/**
	 * 获取所有提交报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	private void doGetResp(PostMsg pm, Account ac) throws Exception {
		long startTime = 0;
		MTResponse[] resps;
		do{
			startTime = System.currentTimeMillis();
			resps = pm.getResps(ac, perGetSize);
			totalSpendTime.addAndGet(System.currentTimeMillis() - startTime);
			totalReqCount.incrementAndGet();
			if(resps != null && resps.length > 0){
				totalMsgCount.addAndGet(resps.length);
			}
		} while(resps != null && resps.length == perGetSize);
	}
	
	/**
	 * 获取所有状态报告
	 * @param pm
	 * @param ac
	 * @throws Exception
	 */
	private void doGetReport(PostMsg pm, Account ac) throws Exception {
		long startTime = 0;
		MTReport[] reports;
		do{
			startTime = System.currentTimeMillis();
			reports = pm.getReports(ac, perGetSize);
			totalSpendTime.addAndGet(System.currentTimeMillis() - startTime);
			totalReqCount.incrementAndGet();
			if(reports != null && reports.length > 0){
				totalMsgCount.addAndGet(reports.length);
			}
		} while(reports != null && reports.length == perGetSize);
	}
	
	/**
	 * 下行信息
	 * @param pm
	 * @param ac
	 * @throws InterruptedException 
	 */
	private void doSendMsg(PostMsg pm, Account ac) throws Exception {
		UUID uuid = UUID.randomUUID();
		//构建包的耗时，不计算在内
		MTPack pack = getPack(true, phoneHeads, perPackSize, uuid);
		long startTime = System.currentTimeMillis();
		GsmsResponse resp = pm.post(ac, pack);
		if(resp != null && resp.getResult() == 0){
			totalMsgCount.addAndGet(pack.getMsgs().size());
		}
//		System.out.println(resp);
		totalSpendTime.addAndGet(System.currentTimeMillis() - startTime);
		totalReqCount.incrementAndGet();
	}
	
	/**
	 * 生成下行包
	 * @param isMass 是否为群发
	 * @param mobileHead 号码段
	 * @param msgCount 数量
	 * @param batchID 业务批次序号 
	 * @return 下行包
	 * @throws UnsupportedEncodingException
	 */
	private MTPack getPack(boolean isMass, String[] phoneHeads, int msgCount, UUID batchID) {
		MTPack pack = new MTPack();
		pack.setBatchID(batchID);
//		pack.setBatchName("性能测试批次");
		pack.setMsgType(MsgType.SMS);
		pack.setSendType(isMass ? SendType.MASS : SendType.GROUP);
		pack.setBizType(1);
		
		pack.setMsgs(msgsGen(isMass, phoneHeads, msgCount));
//		pack.setDistinctFlag(true);
//		pack.setScheduleTime(System.currentTimeMillis() + 180000);
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.HOUR_OF_DAY, 1);
//		pack.setScheduleTime(cal.getTimeInMillis());
//		pack.setCustomNum("");
		return pack;
	}
	
	/**
	 * 批量生成短信
	 * @param isMass 是否群发
	 * @param phoneHeads 号码段，前3位
	 * @param msgCount 短信量
	 * @return 一批短信
	 */
	private List<MessageData> msgsGen(boolean isMass, String[] phoneHeads, int msgCount) {
		ArrayList<MessageData> msgs = new ArrayList<MessageData>();
		if(msgCount < 1){
			return msgs;
		}
		
		int headSize = phoneHeads.length;
		int init = 10000000;
		for(int i = 0; i < msgCount; i ++){
			msgs.add(new MessageData(phoneHeads[i%headSize] + (init + i), (isMass ? content : content + i)));
		}
		return msgs;
	}
	
	private String getSms(int length){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i ++){
			sb.append(i%10);
		}
		return sb.toString();
	}
}
