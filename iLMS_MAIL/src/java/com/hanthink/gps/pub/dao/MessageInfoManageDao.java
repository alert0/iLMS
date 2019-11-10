package com.hanthink.gps.pub.dao;

import java.util.List;

import com.hanthink.gps.pub.vo.MessageInfoVO;
import com.hanthink.gps.pub.vo.OrderInfoVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;

/**
 * 公告信息
 * @author Administrator
 *
 */
public interface MessageInfoManageDao {

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
	void addMessageInfo(MessageInfoVO infoVO);

	/**
	 * 新增公告与供应商组关系
	 * @param groupVOs
	 * @author zuosl 2016-4-8
	 */
	void insertMessageInfoSupGroupId(List<SuppGroupVO> groupVOs);

	/**
	 * 根据公告ID查询公告信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-8
	 */
	MessageInfoVO queryMessageInfoByInfoId(String infoId);

	/**
	 * 根据公告ID删除该公告的供应商组信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-8
	 */
	int deleteMessageInfoSupGroupIdByInfoId(String infoId);

	/**
	 * 修改公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	int updateMessageInfo(MessageInfoVO infoVO);

	/**
	 * 删除公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	int deleteMessageInfo(MessageInfoVO infoVO);

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
	
	/**
	 * 查询角色订单权限
	 * @param orderInfoVO
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<String> queryPrivilegeInfo(OrderInfoVO orderInfoVO);

}
