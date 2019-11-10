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
 * @Title: DataBaseTriggerExceptionJob.java
 * @Package: com.hanthink.gps.system.job
 * @Description: 触发器异常提醒
 * @author dtp
 * @date 2019-5-27
 */
public class DataBaseTriggerExceptionJob extends BaseJob{

	private DataBaseExceptionStopService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (DataBaseExceptionStopService) SpringContextUtil.getBean("DataBaseExceptionStopServicePEC");
		}
		//查询触发器异常
		ProErrorVO vo = new ProErrorVO();
		List<ProErrorVO> list = new ArrayList<ProErrorVO>();
		List<ProErrorVO> proErrorList = service.queryDBTriggerExceptionStop(vo);
		List<ProErrorVO> proErrorList_portal = service.queryDBTriggerExceptionStopPortal(vo);
		
		for (ProErrorVO proErrorVO : proErrorList) {
			list.add(proErrorVO);
		}
		
		for (ProErrorVO proErrorVO : proErrorList_portal) {
			list.add(proErrorVO);
		}
		
		if(null == list || list.size() <= 0){
			LogUtil.info("没有触发器异常提示信息");
			return;
		}
		
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收触发器异常提示的人员");
			return;
		}
		
		//空数据处理
		for (ProErrorVO evo : list) {
			if(null == evo.getDbName()){
				evo.setDbName("");
			}
			if(null == evo.getTriggerName()){
				evo.setTriggerName("");
			}
			if(null == evo.getTableName()){
				evo.setTableName("");
			}
			if(null == evo.getStatus()){
				evo.setStatus("");
			}
			if(null == evo.getTriggeringEvent()){
				evo.setTriggeringEvent("");
			}
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reportContent", list);
		MailUtil.sendMail("dbTrigger.ftl", "触发器异常", toArr, null, null, model, null, null);
		
	}

	
	
}
