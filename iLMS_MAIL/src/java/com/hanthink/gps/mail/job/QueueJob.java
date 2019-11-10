package com.hanthink.gps.mail.job;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;

import com.hanthink.gps.mail.service.QueueService;
import com.hanthink.gps.mail.vo.QueueVO;
import com.hanthink.gps.util.SpringContextUtil;
import com.hanthink.gps.util.logger.LogUtil;

/**
 * @Desc    : 消息队列Job,定时轮询数据库数据,处理未发送的数据
 * @FileName: QueueJob.java 
 * @CreateOn: 2016-7-3 下午05:11:24
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-3		V1.0		zuosl		新建
 * 
 *
 */
public class QueueJob extends BaseJob {
	
	private final static String QUEUE_SERVICE_BEAN_NAME = "queueService";

	@SuppressWarnings("unchecked")
	@Override
	public void jobRun(JobExecutionContext jobContext) {
		QueueService service = (QueueService) SpringContextUtil.getBean(QUEUE_SERVICE_BEAN_NAME);
		
		QueueVO conVO = new QueueVO();
		conVO.setSendFlg(QueueVO.SEND_FLG_NO);
		List<QueueVO> voList = service.queryQueueList(conVO);
		
		if(null != voList && 0 < voList.size()){
			List<QueueVO> updateList = new ArrayList<QueueVO>();
			for(QueueVO vo : voList){
				QueueVO upVO = new QueueVO();
				upVO.setPkId(vo.getPkId());
				upVO.setSendDealTime(new Date());
				
				if(QueueVO.EMAIL_TYPE_JOB_CLASS.equals(vo.getEmailType())){
					try {
						Class jobClass = Class.forName(vo.getClassName());
						Object obj = jobClass.newInstance();
						Method jobMethod = jobClass.getMethod("jobRun", JobExecutionContext.class, QueueVO.class);
						jobMethod.invoke(obj, jobContext, vo);
						upVO.setSendFlg(QueueVO.SEND_FLG_YES);
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.error(e);
						upVO.setSendFlg(QueueVO.SEND_FLG_ERR);
					}
				}
				
				updateList.add(upVO);
			}
			
			//更新发送状态
			service.updateSendQueue(updateList);
		}
		
		
		
	}


}
