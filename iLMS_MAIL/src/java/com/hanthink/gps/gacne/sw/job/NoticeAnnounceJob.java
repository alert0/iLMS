package com.hanthink.gps.gacne.sw.job;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.NoticeInfoOverTimeService;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: NoticeAnnounceJob.java
 * @Package: com.hanthink.gps.gacne.sw.job
 * @Description: 发布公告邮件提醒
 * @author dtp
 * @date 2019-2-16
 */
public class NoticeAnnounceJob extends BaseJob {

	private NoticeInfoOverTimeService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		this.service = (NoticeInfoOverTimeService) SpringContextUtil.getBean("swNoticeInfoOverTimeService");
		// 查询已发布公告
		NoticeOverTimeData model_query = new NoticeOverTimeData();
		List<NoticeOverTimeData> list = service.queryNotice(model_query); // 查询邮件接收人信息
		List<NoticeOverTimeData> l = service.getNocitcList(model_query); // 查询公告信息
		
		if (null != l && l.size() > 0) { // 提醒厂内公告发布人
			for (int i = 0; i < l.size(); i++) {
				if (!StringUtil.isNullOrEmpty(list.get(i).getPublishtEmail())) {
					String mail[] = { list.get(i).getPublishtEmail() };
//					map = new HashMap<String, Object>();
//					model = new NoticeOverTimeData();
					Map<String, Object> map = new HashMap<String, Object>();
					NoticeOverTimeData model = new NoticeOverTimeData();
					model.setNoticeUser("");
					model.setNoticeName(list.get(i).getNoticeName());
					map.put("supplierVO", model);

					MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", mail, null, null, map, null, null);
				}
			}

			for (NoticeOverTimeData supplierVO : list) {
//				if(!StringUtil.isNullOrEmpty(supplierVO.getMail())){
					try {
						Thread.sleep(20000);
						Map<String , Object> model = new HashMap<String, Object>();
						model.put("supplierVO", supplierVO);
						
						HashSet<String> hashSet = new HashSet<String>();
						hashSet.add(supplierVO.getMail());
						hashSet.add(supplierVO.getImportMail());
						hashSet.add(supplierVO.getImportMailA());
						hashSet.add(supplierVO.getPtMail());
						hashSet.add(supplierVO.getPtMailA());
						hashSet.add(supplierVO.getMassMail());
						hashSet.add(supplierVO.getMassMailA());
						hashSet.add(supplierVO.getExcepMailA());
						hashSet.add(supplierVO.getExcepMailB());
						hashSet.add(supplierVO.getMassLogisticeMail());
						hashSet.add(supplierVO.getMassLogisticeMailA());
						hashSet.add(supplierVO.getPtLogisticsMail());
						hashSet.add(supplierVO.getPtLogisticsMailA());
						hashSet.add(supplierVO.getDeviceMail());
						hashSet.add(supplierVO.getDeviceMailA());
//						hashSet.add("xianqin.luo@hanthink.com");
//						hashSet.add(supplierVO.getPublishtEmail());
						hashSet.remove(null);
						hashSet.remove("");
						////
						if (null != hashSet && !hashSet.isEmpty()) {
							String[] mail =  new String[hashSet.size()];
							Iterator <String> iterator= hashSet.iterator();
							int i = 0;
							StringBuffer sbf = new StringBuffer();
							while(iterator.hasNext()){
								mail[i] = iterator.next();
								sbf.append(","+mail[i]);
								i++;
							}
							
							boolean flag = MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", 
									mail, null, null, model, null, null);
							if(flag) {
								LogUtil.info(supplierVO.getSupplierNo() + ",公告邮件发送成功：【" + sbf.toString()+"】");
							}else {
								LogUtil.info(supplierVO.getSupplierNo() + ",公告邮件发送失败：【" + sbf.toString()+"】");
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.error(e);
						LogUtil.info(supplierVO.getSupplierNo() + "，邮件发送异常");
					}
			}
			
			// 查询发送邮件人信息
			String[] toArr = this.queryTimerEmailAddress();
//			if (null == toArr || 0 >= toArr.length) {
//				LogUtil.info("g公告提醒的厂内人员");
//				return;
//			}
			if (null != toArr && toArr.length >0) {
				if (null != l && l.size()>0) {
					for (int i = 0; i < l.size(); i++) {
						Map<String, Object> map = new HashMap<String, Object>();
						NoticeOverTimeData model = new NoticeOverTimeData();
						model.setNoticeUser("");
						model.setNoticeName(list.get(i).getNoticeName());
						map.put("supplierVO", model);
						MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", 
								toArr, null, null, map, null, null);
					}
				}
				
			}

//			HashSet<String> hashSet = new HashSet<String>();
//			for (NoticeOverTimeData supplierVO : list) {
//				if(!StringUtil.isNullOrEmpty(supplierVO.getMail())){
//				try {
//					hashSet.add(supplierVO.getMail());
//					hashSet.add(supplierVO.getImportMail());
//					hashSet.add(supplierVO.getImportMailA());
//					hashSet.add(supplierVO.getPtMail());
//					hashSet.add(supplierVO.getPtMailA());
//					hashSet.add(supplierVO.getMassMail());
//					hashSet.add(supplierVO.getMassMailA());
//					hashSet.add(supplierVO.getExcepMailA());
//					hashSet.add(supplierVO.getExcepMailB());
//					hashSet.add(supplierVO.getMassLogisticeMail());
//					hashSet.add(supplierVO.getMassLogisticeMailA());
//					hashSet.add(supplierVO.getPtLogisticsMail());
//					hashSet.add(supplierVO.getPtLogisticsMailA());
//					hashSet.add(supplierVO.getDeviceMail());
//					hashSet.add(supplierVO.getDeviceMailA());
//					hashSet.add("xianqin.luo@hanthink.com");
//						hashSet.add(supplierVO.getPublishtEmail());
//					hashSet.remove(null);
//					hashSet.remove("");
					////

//				} catch (Exception e) {
//					e.printStackTrace();
//					LogUtil.error(e);
//					LogUtil.info(supplierVO.getSupplierNo() + "，邮件发送异常");
//				}
//			}

//			try {

//				if (null != hashSet && !hashSet.isEmpty()) {
//					String[] mail = new String[hashSet.size()];
//					Iterator<String> iterator = hashSet.iterator();
//					int i = 0;
//					StringBuffer sbf = new StringBuffer();
//					while (iterator.hasNext()) {
//						mail[i] = iterator.next();
//						sbf.append("," + mail[i]);
//						i++;
//					}
					
					
//					map.put("supplierVO", model);
//					if (mail.length <=200) {
//						boolean flag = MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", null, null, null, map, mail,
//								null);
//					}
					
//					if (mail.length > 200) {
//						double doubleCount = i/200;
//						int intCount = (int) Math.ceil(doubleCount);
//						int k = 1;
//						String[] mail_i = new String[200];
//						for (int j = 0; j < mail.length; j++) {
//							
//						mail_i[j] = mail[j];
//						if (j == 200*k - 1) {
//							
//							boolean flag = MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", null, null, null, map, mail,
//									null);
//							mail_i = null;
//							k ++;
//						}
//						if (j < 200*k-1 && j == mail.length - 1) {
//							boolean flag = MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", null, null, null, map, mail,
//									null);
//						}
//					}
//						
//						
//					}
//					Map<String, Object> model = new HashMap<String, Object>();
//					NoticeOverTimeData supplierVO = new NoticeOverTimeData();
//					model.put("supplierVO", supplierVO);
//					boolean flag = MailUtil.sendMail("swNoticeAnnounce.ftl", "公告通知提醒", null, null, null, map, mail,
//							null);
//					if (flag) {
//						// LogUtil.info(supplierVO.getSupplierNo() + ",公告邮件发送成功：【" +
//						// sbf.toString()+"】");
//					} else {
//						// LogUtil.info(supplierVO.getSupplierNo() + ",公告邮件发送失败：【" +
//						// sbf.toString()+"】");
//					}
//					Thread.sleep(30000);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			for (int i = 0; i < l.size(); i++) {
				NoticeOverTimeData supplierVO = new NoticeOverTimeData();
				supplierVO.setNoticeId(list.get(i).getNoticeId());
				// 更新公告邮件发送状态
				service.updateMailSendStatus(supplierVO);
			}
			}
				
//			NoticeOverTimeData supplierVO = new NoticeOverTimeData();
			
		}


	public NoticeInfoOverTimeService getService() {
		return service;
	}

	public void setService(NoticeInfoOverTimeService service) {
		this.service = service;
	}

}
