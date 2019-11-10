/**
 * 
 */
package com.hanthink.gps.system.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.job.BaseJob;
import com.hanthink.gps.system.service.DataBaseBlockErrorService;
import com.hanthink.gps.system.vo.DataBaseBlockErrorVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;
import com.hanthink.gps.util.mail.MailUtil;

/**
 * 描述：数据库死锁异常邮件提醒 
 * @author chenyong
 * @date   2016-10-09
 */
public class DataBaseBlockErrorJob extends BaseJob{

	private String DB_BLOCK_ERROR_NAME = "dataBaseBlockErrorService";
	private DataBaseBlockErrorService service;
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		
		if(null == service){
			service = (DataBaseBlockErrorService) SpringContextUtil.getBean(DB_BLOCK_ERROR_NAME);
		}
		
		//获取数据库表死锁异常信息
		List<DataBaseBlockErrorVO> listInfo=service.queryDataBaseBlockErrorInfo();
		List<DataBaseBlockErrorVO> listInfo_PORTAL=service.queryDataBaseBlockErrorInfo_PORTAL();
		if((null==listInfo||0>=listInfo.size()) &&( null == listInfo_PORTAL || listInfo_PORTAL.size() <= 0)){
			LogUtil.info("没有数据库表死锁异常提示信息");
			return;
		}
		//查询需要发送邮件的人员信息
		String[] toArr = this.queryTimerEmailAddress();
		if(null == toArr || 0 >= toArr.length){
			LogUtil.info("没有接收数据库表死锁异常提示信息的人员");
			return;
		}
		
		//拼接邮件信息
		StringBuffer emailInfo = new StringBuffer();
		
		if(null != listInfo && listInfo.size() > 0){
			emailInfo.append("厂内数据库死锁");
			
			emailInfo.append("</br>");
			for(DataBaseBlockErrorVO vo:listInfo){
				emailInfo.append("表名："+vo.getTableName()+"</br>");
				emailInfo.append("sql语句："+vo.getSql()+"</br>");
			}
		}
		
		if(null != listInfo_PORTAL && listInfo_PORTAL.size() > 0){
			emailInfo.append("信息共享平台数据库死锁");
			emailInfo.append("</br>");
			for(DataBaseBlockErrorVO vo:listInfo_PORTAL){
				emailInfo.append("表名："+vo.getTableName()+"</br>");
				emailInfo.append("sql语句："+vo.getSql()+"</br>");
			}
		}
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("info", emailInfo.toString());
		boolean sendFlg = MailUtil.sendMail("common.ftl", 
				"数据库死锁", 
				toArr, null, null, model, null, null);
		LogUtil.info("数据库死锁异常,发送状态："+sendFlg);
		
	}
	
	
	
	//get  和     set方法
	public DataBaseBlockErrorService getService() {
		return service;
	}
	public void setService(DataBaseBlockErrorService service) {
		this.service = service;
	}

	
}
