/**
 * 
 */
package com.hanthink.gps.jis.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.jis.service.JisExceService;
import com.hanthink.gps.jis.vo.JisOutReckonStopVO;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 厂外同步推算服务停止提醒邮件 
 * @author chenyong
 * date 2016-09-21
 */
public class JisOutReckonStopJob extends BaseJob{

	private String FACTORY_CODE="G1";
	public static final String MM_JISO_CAL_LOCK = "MM_JISO_CAL_LOCK_";
	private JisExceService service;
	private String JIS_EXCE_BEAN_NAME_PEC = "jisExceServicePEC";
	
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		// TODO Auto-generated method stub
		if(null == service){
			service = (JisExceService)SpringContextUtil.getBean(JIS_EXCE_BEAN_NAME_PEC);
		}
		
		//参数传递对象
		JisOutReckonStopVO jvo=new JisOutReckonStopVO();
		jvo.setParamCode(MM_JISO_CAL_LOCK+FACTORY_CODE);
		
		//查询邮件提醒出发条件
		List<JisOutReckonStopVO>  list=service.queryJisOutReckonStopInfo(jvo);
		if(list.size()<=0){
			LogUtil.info("没有厂外同步推算服务停止异常");
			return;
		}
		
		//查询发送人信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有厂外同步推算服务停止异常提醒的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		emailInfo.append("提示： 厂外同步推算服务已停止了"+list.get(0).getStopTime()+"分钟。");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"G1厂外同步推算服务停止异常", 
				toArr, null, null, model, null, null);
		LogUtil.info("G1厂外同步推算服务停止异常,发送状态："+sendFlg);
	}

	public JisExceService getService() {
		return service;
	}

	public void setService(JisExceService service) {
		this.service = service;
	}

	
}
