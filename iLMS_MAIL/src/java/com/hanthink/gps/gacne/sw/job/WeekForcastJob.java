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

public class WeekForcastJob extends BaseJob {
private static final String LONG_ORDER_BEAN_NAME_PMC = "LongOrderServicePMC";
	
	private LongOrderService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if (null == service) {
			service = (LongOrderService) SpringContextUtil.getBean(LONG_ORDER_BEAN_NAME_PMC);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
		
		List<LongOrderVo> list = service.getWeekForecasetSupplier(map);

		// 查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if (null == toArr || 0 >= toArr.length) {
			LogUtil.info("没有接收供应商反馈NG提醒的人员");
			return;
		}
		if (null != list && list.size() > 0) {
			HashSet<String> hashSet = new HashSet<String>();
			StringBuilder info = new StringBuilder();
			for (LongOrderVo longOrderVo : list) {
				Thread.sleep(3000);
				
				hashSet.add(longOrderVo.getSupplierNo());
			}
			
			hashSet.remove("");
			hashSet.remove(null);
			Iterator <String> iterator= hashSet.iterator();
			while(iterator.hasNext()){
				info.append(iterator.next() + ",");
				}
			// 发送邮件
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("info", info);
//			model.put("date", (new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())));
			boolean sendFlg = MailUtil.sendMail("swWeekForecast.ftl", "周预测供应商反馈NG提醒", toArr, null, null, model, null, null);
			LogUtil.info("周预测供应商反馈NG提醒,发送状态：" + sendFlg);
			if (sendFlg) {
				service.updateWeekEmailFlag(map);
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
