package com.hanthink.gps.gacne.sw.job;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.LongOrderService;
import com.hanthink.gps.gacne.sw.vo.LongOrderVo;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

public class ExcepOrderJob extends BaseJob {
	private static final String LONG_ORDER_BEAN_NAME_PEMC = "LongOrderServicePMC";

	private LongOrderService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if (null == service) {
			service = (LongOrderService) SpringContextUtil.getBean(LONG_ORDER_BEAN_NAME_PEMC);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			
			List<LongOrderVo> list = service.getExcepOrderSupplier(map);
			service.updateExcepOrderMail(map);
			boolean sendFlg = false;
			if (null != list && list.size() > 0) {
				StringBuilder info = new StringBuilder();
				for (LongOrderVo excepOrderVo : list) {
					Thread.sleep(20000);

					HashSet<String> hashSet = new HashSet<String>();
					hashSet.add(excepOrderVo.getMail());
					hashSet.add(excepOrderVo.getImportMail());
					hashSet.add(excepOrderVo.getImportMailA());
					hashSet.add(excepOrderVo.getPtMail());
					hashSet.add(excepOrderVo.getPtMailA());
					hashSet.add(excepOrderVo.getMassMail());
					hashSet.add(excepOrderVo.getMassMailA());
					hashSet.add(excepOrderVo.getExcepMailA());
					hashSet.add(excepOrderVo.getExcepMailB());
					hashSet.add(excepOrderVo.getMassLogisticeMail());
					hashSet.add(excepOrderVo.getMassLogisticeMailA());
					hashSet.add(excepOrderVo.getPtLogisticsMail());
					hashSet.add(excepOrderVo.getPtLogisticsMailA());
					hashSet.add(excepOrderVo.getDeviceMail());
					hashSet.add(excepOrderVo.getDeviceMailA());
					hashSet.remove(null);
					hashSet.remove("");
					//邮件不为空或者订单数量大于0则发送邮件
					if (null != hashSet && !hashSet.isEmpty() && Integer.parseInt(excepOrderVo.getCount()) > 0) {
						String[] mail = new String[hashSet.size()];
						Iterator<String> iterator = hashSet.iterator();
						int i = 0;
						while (iterator.hasNext()) {
							mail[i] = iterator.next();
							i++;
						}
						// 发送邮件
						Map<String, Object> model = new HashMap<String, Object>();
						model.put("count", excepOrderVo.getCount());
						model.put("name", "尊敬的供应商");
//			model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
						sendFlg = MailUtil.sendMail("swExcepOrder.ftl", "例外订单接收提醒", mail, null, null,
								model, null, null);
						LogUtil.info("例外订单接受提醒,发送状态：" + sendFlg);
						
					}
				}
				
//				model.put("count", excepOrderVo.getCount());
////				model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
//							sendFlg = MailUtil.sendMail("swExcepOrder.ftl", "例外订单接收提醒", mail, null, null,
//									model, null, null);
			}
			
			// 查询发送邮件人信息
			String[] toArr = this.queryTimerEmailAddress();

			if (null != toArr && toArr.length >0) {
				if (null != list && list.size()>0) {
					int count = 0;
					for (int i = 0; i < list.size(); i++) {
						count = Integer.parseInt(list.get(i).getCount()) + count;
					}
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("count", count);
					map1.put("name", "您好");
					MailUtil.sendMail("swExcepOrder.ftl", "例外订单接收提醒", 
							toArr, null, null, map1, null, null);
					LogUtil.info("例外订单接受提醒(厂内用户),发送状态：" + sendFlg);
				}
				
			}
			

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
