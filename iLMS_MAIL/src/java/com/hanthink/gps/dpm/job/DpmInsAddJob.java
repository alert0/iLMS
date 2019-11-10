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

public class DpmInsAddJob extends BaseJob{

	
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
		
		synchronized ("DPM_ADD") {
		//查询
		List<DpmInsVo> list = service.getDpmNotSubmit(map);
//		String []toAddArr = new String[1];
		
		for (int i = 0; i < list.size(); i++) {
			String[] toAddArr = {list.get(i).getMail()};
			model.put("count", list.get(i).getCount());

				if (Integer.parseInt(list.get(i).getCount()) > 0 && (!StringUtil.isNullOrEmpty(list.get(i).getMail())) ) {
					boolean sendFlg = MailUtil.sendMail("dpmAdd.ftl", "不良品未提交提醒", toAddArr, null, null, model, null, null);
					System.out.println(toAddArr[0]+"不良品未提交邮箱");
					LogUtil.info("发送不良品未提交条数,发送状态:"+sendFlg);
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
