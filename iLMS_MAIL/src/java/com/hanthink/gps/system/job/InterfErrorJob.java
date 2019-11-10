/**
 * 
 */
package com.hanthink.gps.system.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.system.service.InterfErrorService;
import com.hanthink.gps.system.vo.InterfErrorVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 接口平台异常邮件提醒
 * @author chenyong
 * @date   2016-09-22
 */
public class InterfErrorJob extends BaseJob{
	

//	private String FACTORY_CODE="G1";
	private String INTERFACE_EXCE_BEAN_NAME = "interfErrorService";
	private InterfErrorService service;
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (InterfErrorService)SpringContextUtil.getBean(INTERFACE_EXCE_BEAN_NAME);
		}
		
		
		List<InterfErrorVO> ErrorList = service.queryG1interfErrorInfo();
		if(null == ErrorList || 0 >= ErrorList.size()){
			LogUtil.info("没有接口异常提示信息");
			return;
		}

		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收接口异常提示的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		emailInfo.append("提示：以下接口异常信息：</br>");
		for(int i=0;i<ErrorList.size();i++){
			emailInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			emailInfo.append("接口: "+ErrorList.get(i).getIfCode());
			emailInfo.append("  异常信息:"+ErrorList.get(i).getJobName()+";执行时间是："+ErrorList.get(i).getLastProccessTime()+"</br>");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"接口平台异常", 
				toArr, null, null, model, null, null);
		LogUtil.info("接口平台异常,发送状态："+sendFlg);
	}
	
    
	//get set 方法
	public InterfErrorService getService() {
		return service;
	}
	public void setService(InterfErrorService service) {
		this.service = service;
	}
}
