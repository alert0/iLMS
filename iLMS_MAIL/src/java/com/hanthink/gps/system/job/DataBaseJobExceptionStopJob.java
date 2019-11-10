package com.hanthink.gps.system.job;

import java.util.ArrayList;
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
 * @Title: DataBaseExceptionStopJob.java
 * @Package: com.hanthink.gps.system.job
 * @Description: 数据库job异常停止
 * @author dtp
 * @date 2019-5-27
 */
public class DataBaseJobExceptionStopJob extends BaseJob{
	
	private DataBaseExceptionStopService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (DataBaseExceptionStopService) SpringContextUtil.getBean("DataBaseExceptionStopServicePEC");
		}
		
		//查询数据库JOB异常
		ProErrorVO vo = new ProErrorVO();
		List<ProErrorVO> list = new ArrayList<ProErrorVO>();
		List<ProErrorVO> proErrorList = service.queryDBJobExceptionStop(vo);
		List<ProErrorVO> proErrorList_portal = service.queryDBJobExceptionStopPortal(vo);
		
		for (ProErrorVO proErrorVO : proErrorList) {
			list.add(proErrorVO);
		}
		for (ProErrorVO proErrorVO : proErrorList_portal) {
			list.add(proErrorVO);
		}
		
		if(null == list || list.size() <= 0){
			LogUtil.info("没有job异常提示信息");
			return;
		}
		
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收job异常提示的人员");
			return;
		}
		
		//空数据处理
		for (ProErrorVO evo : list) {
			if(null == evo.getDbName()){
				evo.setDbName("");
			}
			if(null == evo.getJob()){
				evo.setJob("");
			}
			if(null == evo.getWhat()){
				evo.setWhat("");
			}
			if(null == evo.getLastDate()){
				evo.setLastDate("");
			}
			if(null == evo.getNextDate()){
				evo.setNextDate("");
			}
			if(null == evo.getInterval()){
				evo.setInterval("");
			}
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reportContent", list);
		MailUtil.sendMail("dbJobStop.ftl", "数据库JOB异常", toArr, null, null, model, null, null);
		
	}

	
	public DataBaseExceptionStopService getService() {
		return service;
	}

	public void setService(DataBaseExceptionStopService service) {
		this.service = service;
	}
	
}
