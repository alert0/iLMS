package com.hanthink.gps.gacne.sw.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.NoticeInfoOverTimeService;
import com.hanthink.gps.gacne.sw.service.PickupPlanService;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.gacne.sw.vo.PickupPlanVo;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 检查发布的公告信息，如果供应商未查看，提醒公告发布人员进行跟踪
 * @FileName: NoticeInfoCheckJob.java 
 * @CreateOn: 2016-6-29 上午11:10:00
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-6-29	V1.0		zuosl		新建
 * 
 *
 */
public class PickupPlanFreebackJob extends BaseJob{
	
	private PickupPlanService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		try {
			
			String infoId = null;
			this.service = (PickupPlanService)SpringContextUtil.getBean("swPickupPlanService");
			
			List<PickupPlanVo> supList = service.queryPickupPlanInfo();

			
			if (supList != null && 0 < supList.size() ) {
				for (PickupPlanVo supplierVO : supList) {
					infoId = supplierVO.getAccount();
					List<PickupPlanVo> numList = service.queryPickupPlanNum(infoId);
					if (numList != null && numList.size() > 0) {
						supplierVO.setPlanNum(numList.get(0).getPlanNum());
					}else {
						supplierVO.setPlanNum("0");
					}
					Map<String , Object> model = new HashMap<String, Object>();
					model.put("supplierVO", supplierVO);
					String[] mail = {supplierVO.getMail()};
					if (null != mail && mail.length > 0 && (!StringUtil.isNullOrEmpty(mail[0])) ) {
						MailUtil.sendMail("pickupPlanOverTime.ftl", "取货计划超时提醒", 
								mail, null, null, model, null, null);
					}					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public PickupPlanService getService() {
		return service;
	}
	public void setService(PickupPlanService service) {
		this.service = service;
	}
	
	
}
