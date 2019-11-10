package com.hanthink.gps.jis.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.jis.service.JisExceService;
import com.hanthink.gps.jis.vo.JisPartVO;
import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * @Desc    : 一线厂外同步MTOC零件明细未维护提醒
 * @FileName: JisoPartMtocJob.java 
 * @CreateOn: 2016-7-27 下午03:11:53
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JisoPartMtocJob extends BaseJob {
	private static final String FACTORY_CODE = "G1";
	private static final String JIS_EXCE_BEAN_NAME_PEC = "jisExceServicePEC";
	
	private JisExceService service;

	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (JisExceService)SpringContextUtil.getBean(JIS_EXCE_BEAN_NAME_PEC);
		}
		
		//查询厂外同步MTOC零件明细未维护信息
		JisPartVO qvo = new JisPartVO();
		qvo.setFactoryCode(FACTORY_CODE);
		List<JisPartVO> partList = service.queryJisoMtocPartInfo(qvo);
		if(null == partList || 0 >= partList.size()){
			LogUtil.info("没有一线厂外同步MTOC零件明细未维护信息");
			return;
		}

		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收一线厂外同步MTOC零件明细未维护提醒的人员");
			return;
		}
		
		//发送邮件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reportContent", partList);
		boolean sendFlg = MailUtil.sendMail("jisoPartMtoc.ftl", "【异常确认】厂外同步MTOC未维护("+qvo.getFactoryCode()+")", toArr, null, null, model, null, null);
		LogUtil.info("发送一线厂外同步MTOC零件明细未维护信息,发送状态:"+sendFlg);
	}

	
	public JisExceService getService() {
		return service;
	}
	public void setService(JisExceService service) {
		this.service = service;
	}

}
