package com.hanthink.gps.gacne.pub.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;

import com.hanthink.gps.gacne.pub.service.PubOrderAlertService;
import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Title: PubReckonStopJob.java
 * @Package: com.hanthink.gps.gacne.pub.job
 * @Description: 推算停止提醒
 * @author dtp
 * @date 2019-4-5
 */
public class PubReckonStopJob extends BaseJob{

	private final static String FACTORY_CODE = "2000";
	
	private PubOrderAlertService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (PubOrderAlertService)SpringContextUtil.getBean("pubOrderAlertService");
		}
		//查询条件
		PubOrderVO model = new PubOrderVO();
		model.setFactoryCode(FACTORY_CODE);
		
		//查询推算服务停止信息
		//拉动推算服务
		List<PubOrderVO> stopList = service.queryReckonStopList(model);
		//同步推算服务
		List<PubOrderVO> stopList_jis = service.queryReckonStopList_jis(model);
		
		List<PubOrderVO> stop_list = new ArrayList<PubOrderVO>();
		
		//拉动推算服务
		for (PubOrderVO pubOrderVO : stopList) {
			stop_list.add(pubOrderVO);
		}
		//同步推算服务
		for (PubOrderVO pubOrderVO : stopList_jis) {
			stop_list.add(pubOrderVO);
		}
		
		
		if(null == stop_list || 0 >= stop_list.size()){
			LogUtil.info("没有推算服务停止提醒信息");
			return;
		}
		
		//查询发送邮件人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收推算服务停止提醒的人员");
			return;
		}
		
		//拼接邮件发送信息
		StringBuffer emailInfo = new StringBuffer();
		for(PubOrderVO ppc : stop_list){
			emailInfo.append("提示：【");
			emailInfo.append(ppc.getPlanCodeDesc());
			//emailInfo.append("】拉动推算服务状态已停止&nbsp;");
			emailInfo.append("】");
			if(StringUtils.isNotEmpty(ppc.getPlanCode())){
				if(ppc.getPlanCode().contains("JIT")){
					emailInfo.append("拉动");
				}else if(ppc.getPlanCode().contains("JIS")){
					emailInfo.append("同步");
				} 
			}
			emailInfo.append("推算服务状态已停止&nbsp;");
			emailInfo.append(ppc.getStopTime());
			emailInfo.append("&nbsp;分钟。</br>");
		}
		//发送邮件
		Map<String, Object> model_s = new HashMap<String, Object>();
		model_s.put("info", emailInfo.toString());
		String[] csArr = this.queryCSEmailAddress();
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"推算服务停止提醒", 
				toArr, csArr, null, model_s, null, null);
		LogUtil.info("发送推算服务停止提醒,发送状态："+sendFlg);
		
	}
}
