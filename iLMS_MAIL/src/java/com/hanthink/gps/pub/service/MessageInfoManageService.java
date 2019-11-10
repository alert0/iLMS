package com.hanthink.gps.pub.service;

import com.hanthink.gps.pub.vo.ExcelImportVO;
import com.hanthink.gps.pub.vo.MessageInfoVO;
import com.hanthink.gps.pub.vo.OrderInfoVO;
import com.hanthink.gps.pub.vo.PageObject;

/**
 * 公告信息
 * @author Administrator
 *
 */
public interface MessageInfoManageService extends BaseService {

	/**
	 * 分页查询公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-7
	 */
	PageObject queryMessageManageForPage(MessageInfoVO infoVO, int start,
			int limit);

	/**
	 * 新增公告信息
	 * @param infoVO
	 * @author zuosl 2016-4-7
	 */
	String addMessageInfo(MessageInfoVO infoVO, ExcelImportVO importVO);

	/**
	 * 修改公告信息
	 * @param infoVO
	 * @param importVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	String updateMessageInfo(MessageInfoVO infoVO, ExcelImportVO importVO);

	/**
	 * 删除公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	String deleteMessageInfo(MessageInfoVO infoVO);

	/**
	 * 查询首页公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-11
	 */
	PageObject queryHomePageMessageForPage(MessageInfoVO infoVO, int start,
			int limit);

	/**
	 * 更新供应商公告查看状态
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-12
	 */
	void updateSupplierViewStatus(MessageInfoVO infoVO);

	/**
	 * 根据公告ID查询公告信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-12
	 */
	MessageInfoVO queryMessageInfoByInfoId(String infoId);

	/**
	 * 更新公告供应商下载状态
	 * @param infoVO
	 * @author zuosl 2016-4-12
	 */
	void updateSupplierDownloadStatus(MessageInfoVO infoVO);

	/**
	 * 查询公告供应商查看记录
	 * @param infoId
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-12
	 */
	PageObject queryHomePageMsgQueryStatusForPage(String infoId, int start,
			int limit);
	
	/**
	 * 订单状态一览
	 * @param orderInfoVO
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageObject queryOrderInfo(OrderInfoVO orderInfoVO,int start, int limit);

}
