package com.hanthink.gps.gacne.sw.dao.impl;

import java.util.List;

import com.hanthink.gps.gacne.sw.dao.NoticeInfoOverTimeDao;
import com.hanthink.gps.gacne.sw.vo.NoticeOverTimeData;
import com.hanthink.gps.gacne.sw.vo.ZCOrderVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

public class NoticeInfoOverTimeDaoImpl extends BaseDaoSupport implements NoticeInfoOverTimeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeOverTimeData> queryUserNoticeInfo() {
		return (List<NoticeOverTimeData>) this.executeQueryForList("gacne_sw.select_queryUserNoticeInfo");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeOverTimeData> queryNoticeOverTimeNum(String infoId) {
		return (List<NoticeOverTimeData>) this.executeQueryForList("gacne_sw.select_queryNoticeOverTimeNum", infoId);
	}

	/**
	 * 查询已发布公告
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<NoticeOverTimeData> queryNotice(NoticeOverTimeData model) {
		return (List<NoticeOverTimeData>) this.executeQueryForList("gacne_sw.select_queryNotice", model);
	}

	/**
	 * 更新公告邮件发送状态
	 * @param supplierVO
	 */
	@Override
	public void updateMailSendStatus(NoticeOverTimeData supplierVO) {
		this.executeUpdate("gacne_sw.update_updateMailSendStatus", supplierVO);
	}

	/**
	 * 查询资材订单
	 * @param vo 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ZCOrderVO> queryZCOrder(ZCOrderVO vo) {
		return (List<ZCOrderVO>) this.executeQueryForList("gacne_sw.select_queryZCOrder", vo);
	}

	/**
	 * 查询资材已取消订单
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ZCOrderVO> queryZCOrderCancle(ZCOrderVO vo) {
		return (List<ZCOrderVO>) this.executeQueryForList("gacne_sw.select_queryZCOrderCancle", vo);
	}

	@Override
	public List<NoticeOverTimeData> getNocitcList(NoticeOverTimeData model_query) {
		
		return (List<NoticeOverTimeData>) this.executeQueryForList("gacne_sw.getNocitcList", model_query);
	}

	/**
	 * 更新邮件发送状态
	 * @param vv
	 */
	@Override
	public void updateEmailSendStatus(ZCOrderVO vv) {
		this.executeUpdate("gacne_sw.updateEmailSendStatus", vv);
	}

	
}
