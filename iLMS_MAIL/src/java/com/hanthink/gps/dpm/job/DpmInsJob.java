package com.hanthink.gps.dpm.job;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.dpm.service.DpmInsService;
import com.hanthink.gps.dpm.vo.DpmInsVo;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

public class DpmInsJob extends BaseJob{
	
		private static final String FACTORY_CODE = "2000";
		private static final String DPM_BEAN_NAME_PEC = "dpmInsServicePEC";
		
		private DpmInsService service;

		@Override
		public void jobRun(JobExecutionContext jobContext) {
			
			if(null == service){
				service = (DpmInsService)SpringContextUtil.getBean(DPM_BEAN_NAME_PEC);
			}
			//发送邮件
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String , Object> map = new HashMap<String, Object>();
			map.put("factoryCode", FACTORY_CODE);
			
			synchronized ("DPM") {
			List<DpmInsVo> list = service.getDpmUserMail(map);
			String []toArr = new String[1];
			
			for (int i = 0; i < list.size(); i++) {
				toArr[0] = list.get(i).getMail();
				model.put("count", list.get(i).getCount());
					if (Integer.parseInt(list.get(i).getCount()) > 0 && (!StringUtil.isNullOrEmpty(list.get(i).getMail())) ) {
						System.out.println(list.get(i).getMail()+"不良品未审核邮箱================="+list.get(i).getCount());
						boolean sendFlg = MailUtil.sendMail("dpmCheck.ftl", "不良品未审核提醒", toArr, null, null, model, null, null);
						System.out.println(toArr[0]+"不良品未审核邮箱");
						LogUtil.info("发送不良品未审核条数,发送状态:"+sendFlg);
					}
					
				}

			}

		}

		
		public DpmInsService getService() {
			return service;
		}
		public void setService(DpmInsService service) {
			this.service = service;
		}

	}

