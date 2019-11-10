package com.hanthink.gps.gacne.sw.service.impl;

import java.util.List;

import com.hanthink.gps.gacne.sw.dao.NoticeInfoOverTimeDao;
import com.hanthink.gps.gacne.sw.service.NoticeInfoOverTimeService;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.gacne.sw.vo.ZCOrderVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

public class NoticeInfoOverTimeServiceImpl extends BaseServiceImpl implements NoticeInfoOverTimeService {
	
	private NoticeInfoOverTimeDao dao;
	
	@Override
	public List<NoticeOverTimeData> queryUserNoticeInfo() {
		return dao.queryUserNoticeInfo();
	}

	@Override
	public List<NoticeOverTimeData> queryNoticeOverTimeNum(String infoId) {
		return dao.queryNoticeOverTimeNum(infoId);
	}

	public NoticeInfoOverTimeDao getDao() {
		return dao;
	}

	public void setDao(NoticeInfoOverTimeDao dao) {
		this.dao = dao;
	}

	/**
	 * 查询已发布公告
	 * @param model
	 * @return
	 */
	@Override
	public List<NoticeOverTimeData> queryNotice(NoticeOverTimeData model) {
		return dao.queryNotice(model);
	}

	/**
	 * 更新公告邮件发送状态
	 * @param supplierVO
	 */
	@Override
	public void updateMailSendStatus(NoticeOverTimeData supplierVO) {
		dao.updateMailSendStatus(supplierVO);
	}

	/**
	 * 查询资材订单
	 * @param vo 
	 * @return
	 */
	@Override
	public List<ZCOrderVO> queryZCOrder(ZCOrderVO vo) {
		return dao.queryZCOrder(vo);
	}

	/**
	 * 查询资材已取消订单
	 * @param vo
	 * @return
	 */
	@Override
	public List<ZCOrderVO> queryZCOrderCancle(ZCOrderVO vo) {
		return dao.queryZCOrderCancle(vo);
	}

	@Override
	public List<NoticeOverTimeData> getNocitcList(NoticeOverTimeData model_query) {
		
		return dao.getNocitcList(model_query);
	}

	/**
	 * 更新邮件发送状态
	 * @param vv
	 */
	@Override
	public void updateEmailSendStatus(ZCOrderVO vv) {
		dao.updateEmailSendStatus(vv);
	}

	
}
