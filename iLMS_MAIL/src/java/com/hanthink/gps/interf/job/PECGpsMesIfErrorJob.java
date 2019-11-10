/**
 * 
 */
package com.hanthink.gps.interf.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.interf.service.G1interfErrorService;
import com.hanthink.gps.interf.service.PECGpsMesIfErrorService;
import com.hanthink.gps.interf.vo.G1interfErrorVO;
import com.hanthink.gps.interf.vo.PECGpsMesIfErrorVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 描述：PECGPS和MES接口邮件异常Job
 * @author chenyong
 * @date   2016-10-18
 * 
 * 备注：此处描述写的为接口异常提醒，是因为前期按接口异常设计编写，
 * 后续优化改造已将该邮件封装为公用的系统警讯邮件提醒，即每个业务如果有异常，
 * 只要将异常信息写入到指定的系统警讯数据表，则系统就会发出当前编写的提醒邮件。
 * 因后续优化改造对前期的文件变量命名等涉及比较多修改困难，故还是保持原来接口异常的命名。
 */
public class PECGpsMesIfErrorJob extends BaseJob{

	private String PEC_GPS_MES_INFTERFACE_ERROR = "PECGpsMesIfErrorService";
	private PECGpsMesIfErrorService service;
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (PECGpsMesIfErrorService)SpringContextUtil.getBean(PEC_GPS_MES_INFTERFACE_ERROR);
		}
		
		
	    //获取A级异常信息
		List<PECGpsMesIfErrorVO> ErrorListA = service.queryPECGpsMesIfErrorA();
		
		System.out.println("A级异常查询成功"+":"+ErrorListA.size());
		//获得非A级异常信息
		List<PECGpsMesIfErrorVO> ErrorListNotA = service.queryPECGpsMesIfErrorNotA();
		System.out.println("非A级异常查询成功"+":"+ErrorListNotA.size());
		int listAnull = ErrorListA.size();
		int listNotAnull = ErrorListNotA.size();
		if(listAnull == 0 && listNotAnull == 0){
			LogUtil.info("PEC GPS MES 接口正常");
			return;
		}
		
		
		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收PEC GPS MES接口异常提示的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		emailInfo.append("提示：以下是GPS PEC警讯信息：</br>");
		emailInfo.append("<table><thead><tr><td colspan='12'>GPS PEC警讯信息</td></tr>");
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
	public PECGpsMesIfErrorService getService() {
		return service;
	}

	public void setService(PECGpsMesIfErrorService service) {
		this.service = service;
	}
   
	
}
