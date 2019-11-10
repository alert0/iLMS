package com.hanthink.gps.system.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.system.service.DataBaseExceptionStopService;
import com.hanthink.gps.system.vo.ProErrorVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: DataBaseIFExceptionJob.java
 * @Package: com.hanthink.gps.system.job
 * @Description: 接口job异常 
 * @author dtp
 * @date 2019-5-27
 */
public class DataBaseIFExceptionJob extends BaseJob{

	private DataBaseExceptionStopService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (DataBaseExceptionStopService) SpringContextUtil.getBean("DataBaseExceptionStopServicePEC");
		}
		//查询接口异常
		ProErrorVO vo = new ProErrorVO();
		List<ProErrorVO> proErrorList = service.queryIFExceptionList(vo);
		
		if(null == proErrorList || proErrorList.size() <= 0){
			LogUtil.info("没有接口job异常提示信息");
			return;
		}
		
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收接口job异常提示的人员");
			return;
		}
		
		//空数据处理
		for (ProErrorVO evo : proErrorList) {
			if(null == evo.getJobCode()){
				evo.setJobCode("");
			}
			if(null == evo.getJobDesc()){
				evo.setJobDesc("");
			}
			if(null == evo.getLastRunTime()){
				evo.setLastRunTime("");
			}
			if(null == evo.getTriggerValue()){
				evo.setTriggerValue("");
			}
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reportContent", proErrorList);
		MailUtil.sendMail("dbInterface.ftl", "接口job异常", toArr, null, null, model, null, null);
		
	}

	public DataBaseExceptionStopService getService() {
		return service;
	}

	public void setService(DataBaseExceptionStopService service) {
		this.service = service;
	}
	
	

}
