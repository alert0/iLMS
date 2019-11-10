package com.hanthink.gps.gacne.sw.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.sw.service.NoticeInfoOverTimeService;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
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
public class NoticeInfoCheckJob extends BaseJob{
	
	private NoticeInfoOverTimeService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		try {
			this.service = (NoticeInfoOverTimeService)SpringContextUtil.getBean("swNoticeInfoOverTimeService");
			//查询存在未反馈公告
			List<NoticeOverTimeData> supList = service.queryUserNoticeInfo();
			if (null != supList && 0 < supList.size() ) {
				for (NoticeOverTimeData supplierVO : supList) {
					//判断邮件是否为空
					if(null != supplierVO.getViewNum() && Integer.valueOf(supplierVO.getViewNum()) > 0){
						if(!StringUtil.isNullOrEmpty(supplierVO.getMail())){
							Map<String , Object> model = new HashMap<String, Object>();
							model.put("supplierVO", supplierVO);
							String[] mail = {supplierVO.getMail()};
							MailUtil.sendMail("supMessagePublish.ftl", "公告未反馈提醒", mail, null, null, model, null, null);
						}else{
							System.out.println("公告发布人邮件为空");
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public NoticeInfoOverTimeService getService() {
		return service;
	}
	public void setService(NoticeInfoOverTimeService service) {
		this.service = service;
	}
	
	
}
