package com.hanthink.gps.system.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.pub.vo.SystemParamVO;
import com.hanthink.gps.system.service.SysErrorService;
import com.hanthink.gps.system.vo.ProErrorVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 存储过程执行异常 检查Job 
 * @FileName: ProErrorJob.java 
 * @CreateOn: 2016-7-12 下午06:15:59
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-12	V1.0		zuosl		新建
 * 
 *
 */
public class ProErrorJob extends BaseJob {
	
//	private static final String PRO_ERR_MAX_SEND_COUNT = "PRO_ERR_MAX_SEND_COUNT";
	private static final String SYS_ERROR_SERVICE_BEAN_NAME = "sysErrorService";
	
	private SysErrorService service;
	
	/** 计数器清零起始时间HH:mm:ss */
	private static final String RESET_COUNT_START_TIME = "05:00:00";
	/** 计数器清零截止时间HH:mm:ss */
	private static final String RESET_COUNT_END_TIME = "06:00:00";
	/** 计数器是否被定时清零过 */
	private static boolean IS_TIMER_RESET = false;
	
	/** 邮件发送次数 */
	private static int SEND_COUNT = 0;
	/** 最后异常时间 */
//	private static Date LAST_ERROR_DATE = null;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		sendCountClear();//在设定时间段内计数器清零
		
		//定时清零时间段内不执行任务
		if(IS_TIMER_RESET){
			return;
		}
		
		if(null == service){
			service = (SysErrorService)SpringContextUtil.getBean(SYS_ERROR_SERVICE_BEAN_NAME);
		}
		
		//获取最大发送次数,若未维护默认为2
		//String maxSendCountStr = "2"; //PubSysParamServer.querySysParam(PRO_ERR_MAX_SEND_COUNT);
		SystemParamVO vo = new SystemParamVO();
		vo.setParamCode("PRO_ERR_MAX_SEND_COUNT");
		vo.setFactory(DailyCheckInfoJob.FACTORY_CODE);
		SystemParamVO paramVO = service.queryParamByParamCode(vo);
		Integer maxSendCount = 1000;
		if(null != paramVO && !StringUtil.isNullOrEmpty(paramVO.getParamVal())){
			maxSendCount = Integer.valueOf(paramVO.getParamVal());
		}
		
		// 查询存储过程执行错误的日志信息
		List<ProErrorVO> proErrList = service.queryProErrorInfoList();
		
		//没有待处理的错误信息时清零计数器
		if(null == proErrList || 0 >= proErrList.size()){
			SEND_COUNT = 0;
			return;
		}
		
		//发送邮件
		if( SEND_COUNT < maxSendCount.intValue() ){
			
			//查询收件人信息
			String[] toArr = this.queryTimerEmailAddress();
			if(null == toArr || 0 >= toArr.length){
				LogUtil.info("没有接收PMC存储过程执行异常信息的人员");
				return;
			}
			
			sendEmail(proErrList, toArr);  //发送邮件
			
			SEND_COUNT ++ ;  //发送次数递增
			
		}else{
			LogUtil.info("存储过程执行异常定时邮件超过最大发送次数【"+maxSendCount+"】，未发送邮件。");
		}
		
	}

	/**
	 * 发送邮件
	 * @param proErrList
	 * @param sendUserList
	 * @author zuosl 2016-7-12
	 */
	private void sendEmail(List<ProErrorVO> proErrList,
			String[] toArr) {
		
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("存储过程执行异常无收信人信息,未发送邮件");
			return;
		}
		
		//空数据处理
		for(ProErrorVO evo : proErrList){
			if(null == evo.getAlertType()){
				evo.setAlertType("");
			}
			if(null == evo.getErrorName()){
				evo.setErrorName("");
			}
			if(null == evo.getErrorDesc()){
				evo.setErrorDesc("");
			}
			if(null == evo.getKeyName()){
				evo.setKeyName("");
			}
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
//		model.put("userInfo", user);
		model.put("reportContent", proErrList);
		MailUtil.sendMail("proError.ftl", "存储过程执行异常", toArr, null, null, model, null, null);
	}

	/**
	 * 发送次数定时清零
	 * @author zuosl 2016-7-11
	 */
	private void sendCountClear() {
		try {
			Date curDate = new Date();
			String ymd = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = sdf.parse(ymd + " " + RESET_COUNT_START_TIME);
			Date endDate = sdf.parse(ymd + " " + RESET_COUNT_END_TIME);
			if(curDate.getTime() <= endDate.getTime()
					&& curDate.getTime() >= startDate.getTime() ){
				
				SEND_COUNT = 0;
				IS_TIMER_RESET = true;
			}else{
				IS_TIMER_RESET = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			LogUtil.info("清零失败");
		}
	}

	public SysErrorService getService() {
		return service;
	}
	public void setService(SysErrorService service) {
		this.service = service;
	}

	public static void main(String[] args) {
		
		
	}
}
