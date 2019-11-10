package com.hanthink.gps.system.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.system.service.SysAlertService;
import com.hanthink.gps.system.vo.SysAlertVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 描述：系统警讯信息提醒
 * @author chenyong
 * @date   2016-10-18
 * 
 */
public class SysAlertJob extends BaseJob{

	private String SYS_ALERT_SERVICE_BEANNAME = "sysAlertService";
	private SysAlertService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (SysAlertService)SpringContextUtil.getBean(SYS_ALERT_SERVICE_BEANNAME);
		}
		
		
	    //获取A级异常信息
		List<SysAlertVO> ErrorListA = service.queryPECGpsMesIfErrorA();
		
//		System.out.println("A级异常查询成功"+":"+ErrorListA.size());
		//获得非A级异常信息
		List<SysAlertVO> ErrorListNotA = service.queryPECGpsMesIfErrorNotA();
//		System.out.println("非A级异常查询成功"+":"+ErrorListNotA.size());
		int listAnull = ErrorListA.size();
		int listNotAnull = ErrorListNotA.size();
		if(listAnull == 0 && listNotAnull == 0){
			
			return;
		}
		
		
		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收系统警讯信息异常提示的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		emailInfo.append("提示：以下是系统警讯信息：</br>");
		emailInfo.append("<table><thead><tr><td colspan='12'>警讯信息</td></tr>");
		emailInfo.append("<tr><td>工厂</td><td>警讯级别</td><td>警讯信息</td><td>创建时间</td></tr></thead>");
		emailInfo.append("<tbody>");
		if(listAnull != 0){
		  for(int i=0;i<ErrorListA.size();i++){
			  emailInfo.append("<tr>");
			  emailInfo.append("<td>"+ErrorListA.get(i).getFactory()+"</td>");
			  emailInfo.append("<td class="+"levelA"+">"+ErrorListA.get(i).getAlertLevel()+"</td>");
			  emailInfo.append("<td>"+ErrorListA.get(i).getAlertInfo()+"</td>");
			  emailInfo.append("<td>"+ErrorListA.get(i).getCreationTime()+"</td>");
			  emailInfo.append("</tr>");
		 }
		}
		if(listNotAnull != 0){
			for(int j=0;j<ErrorListNotA.size();j++){
				  emailInfo.append("<tr>");
				  emailInfo.append("<td>"+ErrorListNotA.get(j).getFactory()+"</td>");
				  emailInfo.append("<td>"+ErrorListNotA.get(j).getAlertLevel()+"</td>");
				  emailInfo.append("<td>"+ErrorListNotA.get(j).getAlertInfo()+"</td>");
				  emailInfo.append("<td>"+ErrorListNotA.get(j).getCreationTime()+"</td>");
				  emailInfo.append("</tr>");
			 }
		}
		emailInfo.append("</tbody>");
		emailInfo.append("</table>");
		
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("pecGpsMesIf.ftl", 
				"G1PEC 警讯信息提醒", 
				toArr, null, null, model, null, null);
		LogUtil.info("G1PEC警讯信息提醒,发送状态："+sendFlg);
		
		//修改非A级别的数据为已处理
		service.updateNotAIsHandle();
		
	}

	//get 和 set 方法
	public SysAlertService getService() {
		return service;
	}

	public void setService(SysAlertService service) {
		this.service = service;
	}
   
	
}
