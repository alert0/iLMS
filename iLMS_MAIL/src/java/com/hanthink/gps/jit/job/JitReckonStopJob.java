package com.hanthink.gps.jit.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.jit.service.JitExceService;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.pub.vo.PubPlanCodeVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: JitReckonStopJob.java
 * @Package: com.hanthink.gps.jit.job
 * @Description: 拉动推算服务停止提醒---广汽新能源
 * @author dtp
 * @date 2018-11-6
 */
public class JitReckonStopJob extends BaseJob{
	
	private static final String FACTORY_CODE = "2000";
	private static final String PLAN_CODE_TYPE = "JIT";
	private static final String JIT_EXCE_BEAN_NAME_PEC = "jitExceServicePEC";
	private JitExceService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
		}
		
		//查询条件
		PubPlanCodeVO vo = new PubPlanCodeVO();
		vo.setFactoryCode(FACTORY_CODE);
		vo.setPlanCodeType(PLAN_CODE_TYPE);
		
		//查询拉动推算服务停止数据信息
		List<PubPlanCodeVO> stopList = service.queryJitReckonStopJob(vo);
		if(null == stopList || 0 >= stopList.size()){
			LogUtil.info("没有拉动推算服务停止提醒信息");
			return;
		}
		
		//查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收拉动推算服务停止提醒的人员");
			return;
		}
		
		//拼接邮件发送信息
		StringBuffer emailInfo = new StringBuffer();
		for(PubPlanCodeVO ppc : stopList){
			emailInfo.append("提示：【");
			emailInfo.append(ppc.getPlanCodeDesc());
			emailInfo.append("】拉动推算服务状态已停止&nbsp;");
			emailInfo.append(ppc.getStopTime());
			emailInfo.append("&nbsp;分钟。</br>");
		}
		
		//发送邮件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		String[] csArr = this.queryCSEmailAddress();
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"拉动推算服务停止提醒", 
				toArr, csArr, null, model, null, null);
		LogUtil.info("发送拉动推算服务停止提醒,发送状态："+sendFlg);
		
	}

	public JitExceService getService() {
		return service;
	}
	public void setService(JitExceService service) {
		this.service = service;
	}
}
