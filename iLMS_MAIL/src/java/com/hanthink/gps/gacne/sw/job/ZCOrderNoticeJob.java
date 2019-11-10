package com.hanthink.gps.gacne.sw.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.NoticeInfoOverTimeService;
import com.hanthink.gps.gacne.sw.vo.ZCOrderVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: ZCOrderNoticeJob.java
 * @Package: com.hanthink.gps.gacne.sw.job
 * @Description: 资材订单提醒,提醒供应商资材订单打印
 * @author dtp
 * @date 2019-3-14
 */
public class ZCOrderNoticeJob extends BaseJob{
	private NoticeInfoOverTimeService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		this.service = (NoticeInfoOverTimeService)SpringContextUtil.getBean("swNoticeInfoOverTimeService");
		//查询资材订单
		ZCOrderVO vo = new ZCOrderVO();
		List<ZCOrderVO> list = service.queryZCOrder(vo);
		List<String> list_supplierNo = new ArrayList<String>();
		if(null != list && list.size() > 0){
			for (ZCOrderVO vv : list) {
				String[] mail = getToArr(vv);
				if(null != mail && mail.length > 0){
					try {
						if(list_supplierNo.contains(vv.getSupplierNo())){
							continue;
						}
						Thread.sleep(3000);
						Map<String , Object> model = new HashMap<String, Object>();
						model.put("supplierVO", vv);
						String[] zcCsArr = this.queryZCCSEmailAddressFun();
						MailUtil.sendMail("swZCOrder.ftl", "广汽新能源订购单提醒", mail, zcCsArr, null, model, null, null);
						System.out.println("资材订单通知提醒发送成功");
						//更新邮件发送状态
						service.updateEmailSendStatus(vv);
						list_supplierNo.add(vv.getSupplierNo());
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("资材订单取消提醒发送失败");
					}
				}else{
					System.out.println("资材邮件提醒收件人邮箱为空");
				}
			}
		}
		
	}

	/**
	 * 获取发送邮箱
	 * @return
	 */
	private String[] getToArr(ZCOrderVO model){
		Set<String> set = new HashSet<String>();
		/*if(!StringUtil.isNullOrEmpty(model.getEmail())){
			set.add(model.getEmail());
		}
		if(!StringUtil.isNullOrEmpty(model.getImportMail())){
			set.add(model.getImportMail());
		}
		if(!StringUtil.isNullOrEmpty(model.getPtMail())){
			set.add(model.getPtMail());
		}
		if(!StringUtil.isNullOrEmpty(model.getPtMailA())){
			set.add(model.getPtMailA());
		}*/
		if(!StringUtil.isNullOrEmpty(model.getMassMail())){
			set.add(model.getMassMail());
		}
		if(!StringUtil.isNullOrEmpty(model.getMassMailA())){
			set.add(model.getMassMailA());
		}
		if(null == set || 0 >= set.size()){
			return null;
		}
		String[] toArr = new String[set.size()];
		int i = 0;
		for (String str : set) {
			toArr[i] = str;
			i++;
		}
		return toArr;
	}
	
	/**
	 * 获取抄送邮箱
	 * @param vv
	 * @return
	 */
	private String[] getCCArr(ZCOrderVO model) {
		List<String> sendList = new ArrayList<String>();
		if(!StringUtil.isNullOrEmpty(model.getImportMail())){
			if(!sendList.contains(model.getImportMail())){
				sendList.add(model.getImportMail());
			}
		}
		if(!StringUtil.isNullOrEmpty(model.getPtMail())){
			if(!sendList.contains(model.getPtMail())){
				sendList.add(model.getPtMail());
			}
		}
		if(!StringUtil.isNullOrEmpty(model.getPtMailA())){
			if(!sendList.contains(model.getPtMailA())){
				sendList.add(model.getPtMailA());
			}
		}
		if(!StringUtil.isNullOrEmpty(model.getMassMail())){
			if(!sendList.contains(model.getMassMail())){
				sendList.add(model.getMassMail());
			}
		}
		if(null == sendList || 0 >= sendList.size()){
			return null;
		}
		String[] toArr = new String[sendList.size()];
		for(int i = 0; i < sendList.size(); i ++){
			toArr[i] = sendList.get(i);
		}
		return toArr;
	}


	public NoticeInfoOverTimeService getService() {
		return service;
	}

	public void setService(NoticeInfoOverTimeService service) {
		this.service = service;
	}

	
}
