package com.hanthink.gps.mail.job;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import com.hanthink.gps.mail.service.MainPoolService;
import com.hanthink.gps.mail.vo.MailMsgGroupUserVo;
import com.hanthink.gps.mail.vo.MailMsgTimerVo;
import com.hanthink.gps.util.Constants;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.StringUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 子Job继承本类,更新定时器执行情况
 * @FileName: BaseJob.java 
 * @CreateOn: 2016-6-29 上午10:54:19
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-6-29	V1.0		zuosl		新建
 * 2016-7-28	V1.1		zuosl		修改、增加查询该定时器的收件人地址信息
 *
 */
public abstract class BaseJob implements StatefulJob {
	
	private final static String MAIN_SERVICE_BNAME = "mainPoolService";
	protected MainPoolService mainPoolService;
	
	/** 每个定时器的唯一ID值,与数据库ID对应,会根据该ID更新定时器执行时间 */
	protected Integer timerId;

	@Override
	public void execute(JobExecutionContext jobContext) {
		
		try {
			
			this.timerId = (Integer)jobContext.getJobDetail().getJobDataMap().get(Constants.TIMER_JOB_ID);
			LogUtil.info("定时器执行开始,ID:"+this.timerId);
			
			if(null == mainPoolService){
				mainPoolService = (MainPoolService)SpringContextUtil.getBean(MAIN_SERVICE_BNAME);
			}
			
			this.jobRun(jobContext);
			
			//更新定时器 
			if(null != timerId){
				MailMsgTimerVo	mailTimerVo = new MailMsgTimerVo();
				mailTimerVo.setId(this.timerId);
				mailTimerVo.setNextRunTime(new Timestamp(jobContext.getNextFireTime().getTime()));
				mailTimerVo.setLastRunTime(new Timestamp(new Date().getTime()));
				mainPoolService.updateMailTimer(mailTimerVo);
				LogUtil.info("定时器执行,更新定时器,ID:"+this.timerId);
			}else{
				LogUtil.info("定时器执行,ID为空,更新定时器失败");
			}
			LogUtil.info("定时器执行结束,ID:"+this.timerId);
		} catch (Exception e) {
			LogUtil.info("定时任务执行失败,ID:"+this.timerId);
			LogUtil.error(e);
		}
		
	}
	
	/**
	 * 执行任务
	 * @param arg0
	 * @author zuosl 2016-6-29
	 */
	public abstract void jobRun(JobExecutionContext jobContext);
	
	
	/**
	 * 查询当前定时器所配置的邮件接收地址信息
	 * @return
	 * @author zuosl 2016-7-28
	 */
	public String[] queryTimerEmailAddress(){
		if(null == this.timerId){
			return null;
		}
		List<MailMsgGroupUserVo> timerUserList = this.queryTimerRecUserInfo();
		if(null == timerUserList || 0 >= timerUserList.size()){
			return null;
		}
		List<String> sendList = new ArrayList<String>();
		for(MailMsgGroupUserVo groupUser : timerUserList){
			String addr = MailUtil.getEmailAddress(groupUser.getUserCName(), groupUser.getEmail());
			if(!StringUtil.isNullOrEmpty(addr)){
				sendList.add(addr);
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
	

	/**
	 * 查询当前定时器的收件人信息
	 * @return
	 * @author zuosl 2016-7-28
	 */
	public List<MailMsgGroupUserVo> queryTimerRecUserInfo(){
		if(null == this.timerId){
			return null;
		}
		return this.mainPoolService.queryTimerSendUserByTimerId(this.timerId);
		
	}
	
	/**
	 * 查询抄送组
	 * @return
	 */
	public String[] queryCSEmailAddress(){
		MailMsgGroupUserVo model = new MailMsgGroupUserVo();
		List<MailMsgGroupUserVo> timerUserList = this.queryCSEmailUserInfo(model);
		if(null == timerUserList || 0 >= timerUserList.size()){
			return null;
		}
		List<String> sendList = new ArrayList<String>();
		for(MailMsgGroupUserVo groupUser : timerUserList){
			String addr = MailUtil.getEmailAddress(groupUser.getUserCName(), groupUser.getEmail());
			if(!StringUtil.isNullOrEmpty(addr)){
				sendList.add(addr);
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
	
	
	/**
	 * 查询资材抄送组
	 * @return
	 */
	public String[] queryZCCSEmailAddressFun(){
		MailMsgGroupUserVo model = new MailMsgGroupUserVo();
		List<MailMsgGroupUserVo> timerUserList = this.queryZCCSEmailAddress(model);
		if(null == timerUserList || 0 >= timerUserList.size()){
			return null;
		}
		List<String> sendList = new ArrayList<String>();
		for(MailMsgGroupUserVo groupUser : timerUserList){
			String addr = MailUtil.getEmailAddress(groupUser.getUserCName(), groupUser.getEmail());
			if(!StringUtil.isNullOrEmpty(addr)){
				sendList.add(addr);
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
	
	/**
	 * 资材抄送组
	 * @param model
	 * @return
	 */
	private List<MailMsgGroupUserVo> queryZCCSEmailAddress(MailMsgGroupUserVo model) {
		return this.mainPoolService.queryZCCSEmailAddress(model);
	}

	/**
	 * 查询抄送邮箱
	 * @param model 
	 * @return
	 */
	private List<MailMsgGroupUserVo> queryCSEmailUserInfo(MailMsgGroupUserVo model) {
		return this.mainPoolService.queryCSEmailUserInfo(model);
	}

	public MainPoolService getMainPoolService() {
		return mainPoolService;
	}
	public void setMainPoolService(MainPoolService mainPoolService) {
		this.mainPoolService = mainPoolService;
	}

}
