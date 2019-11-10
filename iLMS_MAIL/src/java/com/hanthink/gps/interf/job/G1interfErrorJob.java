/**
 * 
 */
package com.hanthink.gps.interf.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.interf.service.G1interfErrorService;
import com.hanthink.gps.interf.vo.G1interfErrorVO;
import com.hanthink.gps.jis.service.JisExceService;
import com.hanthink.gps.jis.vo.JisPartVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 一线接口平台异常邮件提醒
 * @author chenyong
 * @date   2016-09-22
 */
public class G1interfErrorJob extends BaseJob{
	

	private String FACTORY_CODE="G1";
	private String INTERFACE_EXCE_BEAN_NAME_PMC="G2interfErrorServicePMC";
	private G1interfErrorService service;
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		if(null == service){
			service = (G1interfErrorService)SpringContextUtil.getBean(INTERFACE_EXCE_BEAN_NAME_PMC);
		}
		
		
	//	G2interfErrorVO qvo = new G2interfErrorVO();
	//	qvo.setFactory(FACTORY_CODE);
		List<G1interfErrorVO> ErrorList = service.queryG1interfErrorInfo();
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
			emailInfo.append("接口: "+ErrorList.get(i).getIfNameZH()+"【"+ErrorList.get(i).getIfCode()+"】 ");
			emailInfo.append("  任务:"+ErrorList.get(i).getJobName()+" 出现异常。最后一次执行时间是："+ErrorList.get(i).getLastProccessTime()+"</br>");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"G1PEC接口平台异常", 
				toArr, null, null, model, null, null);
		LogUtil.info("G1PEC接口平台异常,发送状态："+sendFlg);
	}
	
    
	//get set 方法
	public G1interfErrorService getService() {
		return service;
	}
	public void setService(G1interfErrorService service) {
		this.service = service;
	}
}
