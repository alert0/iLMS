/**
 * 
 */
package com.hanthink.gps.print.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;


import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.print.service.G1PrintErrorService;
import com.hanthink.gps.print.vo.G1PrintErrorVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 自动打印异常邮件提示
 * @author chenyong
 * @date 2016-09-21
 */
public class G1PrintErrorJob extends BaseJob{
	
	private String FACTORY_CODE="G1";
	private String PRINT_EXCE_BEAN_NAME_PEC="G1PrintErrorServicePEC";
	private G1PrintErrorService service;
	public void jobRun(JobExecutionContext jobContext) {
        
		if(null == service){
			service = (G1PrintErrorService)SpringContextUtil.getBean(PRINT_EXCE_BEAN_NAME_PEC);
		}
		
		G1PrintErrorVO pVo = new G1PrintErrorVO();
		pVo.setFactory(FACTORY_CODE);
		List<G1PrintErrorVO> ErrorList = service.queryG1PrintErrorInfo(pVo);
		if(null == ErrorList || 0 >= ErrorList.size()){
			LogUtil.info("没有自动打印异常提示信息");
			return;
		}

		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收自动打印异常提示的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
	
		emailInfo.append("提示：GPS自动打印出现异常，异常信息是：</br>");
		for(int i=0;i<ErrorList.size();i++){
			emailInfo.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			emailInfo.append(ErrorList.get(i).getErrorMsg()+";</br>");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"GPS自动打印异常", 
				toArr, null, null, model, null, null);
		LogUtil.info("GPS自动打印异常,发送状态："+sendFlg);
		
	}
	
	//get 和  set 方法
	public G1PrintErrorService getService() {
		return service;
	}
	public void setService(G1PrintErrorService service) {
		this.service = service;
	}
	
	

}
