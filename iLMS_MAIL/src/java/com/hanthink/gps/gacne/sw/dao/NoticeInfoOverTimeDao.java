package com.hanthink.gps.gacne.sw.dao;

import java.util.List;

import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.gacne.sw.vo.ZCOrderVO;

public interface NoticeInfoOverTimeDao {

	/**
	 * 查询未查看公告的供应商信息
	 * @param infoId
	 * @return
	 * @author zhengwuchao 2018-11-14
	 */
	List<NoticeOverTimeData> queryUserNoticeInfo();
	
	
	/**
	 * 查询未查看公告的供应商数量
	 * @param infoId
	 * @return
	 * @author zhengwuchao 2018-11-14
	 */
	List<NoticeOverTimeData> queryNoticeOverTimeNum(String infoId);

	/**
	 * 查询已发布公告
	 * @param model
	 * @return
	 */
	List<NoticeOverTimeData> queryNotice(NoticeOverTimeData model);

	/**
	 * 更新公告邮件发送状态
	 * @param supplierVO
	 */
	void updateMailSendStatus(NoticeOverTimeData supplierVO);

	/**
	 * 查询资材订单
	 * @param vo 
	 * @return
	 */
	List<ZCOrderVO> queryZCOrder(ZCOrderVO vo);


	/**
	 * 查询资材已取消订单
	 * @param vo
	 * @return
	 */
	List<ZCOrderVO> queryZCOrderCancle(ZCOrderVO vo);

	/**
	 * 
	 * @Description: 查询公告信息
	 * @param @param model_query
	 * @param @return   
	 * @return List<NoticeOverTimeData>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午12:30:32
	 */
	List<NoticeOverTimeData> getNocitcList(NoticeOverTimeData model_query);

	/**
	 * 更新邮件发送状态
	 * @param vv
	 */
	void updateEmailSendStatus(ZCOrderVO vv);
	
}
