package com.hanthink.gps.jit.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.jit.service.JitExceService;
import com.hanthink.gps.jit.vo.JitCalVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 拉动推算服务停止提醒
 * @FileName: JitCalStopJob.java 
 * @CreateOn: 2016-7-27 下午07:21:28
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JitCalStopJob extends BaseJob{
	
	private static final String FACTORY_CODE = "G1";
	private static final String JIT_EXCE_BEAN_NAME_PEC = "jitExceServicePEC";
	
	private JitExceService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (JitExceService)SpringContextUtil.getBean(JIT_EXCE_BEAN_NAME_PEC);
		}
		
		//数据查询条件
		JitCalVO qvo = new JitCalVO();
		qvo.setParamCode(JitCalVO.MM_JIT_CAL_LOCK + FACTORY_CODE);
		
		//查询拉动推算服务停止数据信息
		List<JitCalVO> stopList = service.queryJitCalStopInfo(qvo);
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
		for(JitCalVO vo : stopList){
			emailInfo.append("提示：【");
			emailInfo.append(vo.getJitPlanCode());
			emailInfo.append("】拉动推算服务状态已停止&nbsp;");
			emailInfo.append(vo.getStopTime());
			emailInfo.append("&nbsp;分钟。</br>");
		}
		
		//发送邮件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"拉动推算服务停止提醒("+FACTORY_CODE+")", 
				toArr, null, null, model, null, null);
		LogUtil.info("发送拉动推算服务停止提醒,发送状态："+sendFlg);
		
	}

	public JitExceService getService() {
		return service;
	}
	public void setService(JitExceService service) {
		this.service = service;
	}

}
