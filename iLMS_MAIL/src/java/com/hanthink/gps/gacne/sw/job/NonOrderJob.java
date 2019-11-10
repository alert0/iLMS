package com.hanthink.gps.gacne.sw.job;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.LongOrderService;
import com.hanthink.gps.gacne.sw.vo.LongOrderVo;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

public class NonOrderJob extends BaseJob{
	private static final String LONG_ORDER_BEAN_NAME_PEMC = "LongOrderServicePMC";

	private LongOrderService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if (null == service) {
			service = (LongOrderService) SpringContextUtil.getBean(LONG_ORDER_BEAN_NAME_PEMC);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			// 查询发送邮件人信息
			String[] toArr = this.queryTimerEmailAddress();
			List<LongOrderVo> list = service.getNonOrderSupplier(map);
			service.updateNonOrderMail(map);
			boolean sendFlg = false;
			if (null != list && list.size() > 0) {
				StringBuilder info = new StringBuilder();
				for (LongOrderVo nonOrderVo : list) {
					Thread.sleep(20000);

					HashSet<String> hashSet = new HashSet<String>();
					hashSet.add(nonOrderVo.getMail());
					hashSet.add(nonOrderVo.getImportMail());
					hashSet.add(nonOrderVo.getImportMailA());
					hashSet.add(nonOrderVo.getPtMail());
					hashSet.add(nonOrderVo.getPtMailA());
					hashSet.add(nonOrderVo.getMassMail());
					hashSet.add(nonOrderVo.getMassMailA());
					hashSet.add(nonOrderVo.getExcepMailA());
					hashSet.add(nonOrderVo.getExcepMailB());
					hashSet.add(nonOrderVo.getMassLogisticeMail());
					hashSet.add(nonOrderVo.getMassLogisticeMailA());
					hashSet.add(nonOrderVo.getPtLogisticsMail());
					hashSet.add(nonOrderVo.getPtLogisticsMailA());
					hashSet.add(nonOrderVo.getDeviceMail());
					hashSet.add(nonOrderVo.getDeviceMailA());
//					hashSet.add("1078636478@qq.com");
					hashSet.remove(null);
					hashSet.remove("");
					////
					if (null != hashSet && !hashSet.isEmpty() && Integer.parseInt(nonOrderVo.getCount()) > 0) {
						String[] mail = new String[hashSet.size()];
						Iterator<String> iterator = hashSet.iterator();
						int i = 0;
						while (iterator.hasNext()) {
							mail[i] = iterator.next();
							i++;
						}
						// 发送邮件
						Map<String, Object> model = new HashMap<String, Object>();
						model.put("count", nonOrderVo.getCount());
						model.put("name", "尊敬的供应商");
//			model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
					    sendFlg = MailUtil.sendMail("swExcepOrder.ftl", "定制化订单接收提醒", mail, toArr, null,
								model, null, null);
						LogUtil.info("定制化订单接受提醒,发送状态：" + sendFlg);
						
					}
				}
			}
			
			// 查询发送邮件人信息
//			String[] toArr = this.queryTimerEmailAddress();
//			if (null != toArr && toArr.length >0) {
//				if (null != list && list.size()>0) {
//					int count = 0;
//					for (int i = 0; i < list.size(); i++) {
//						count = Integer.parseInt(list.get(i).getCount()) + count;
//					}
//					Map<String, Object> map1 = new HashMap<String, Object>();
//					map1.put("count", count);
//					map1.put("name", "您好");
//					MailUtil.sendMail("swExcepOrder.ftl", "定制化订单接收提醒", 
//							toArr, null, null, map1, null, null);
//					LogUtil.info("定制化订单接受提醒(厂内用户),发送状态：" + sendFlg);
//				}
//				
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LongOrderService getService() {
		return service;
	}

	public void setService(LongOrderService service) {
		this.service = service;
	}
}
