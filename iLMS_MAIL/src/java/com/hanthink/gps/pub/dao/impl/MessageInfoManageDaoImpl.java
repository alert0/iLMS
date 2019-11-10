package com.hanthink.gps.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.dao.MessageInfoManageDao;
import com.hanthink.gps.pub.vo.MessageInfoVO;
import com.hanthink.gps.pub.vo.OrderInfoVO;
import com.hanthink.gps.pub.vo.PageObject;
import com.hanthink.gps.pub.vo.SuppGroupVO;
import com.hanthink.gps.util.StringUtil;

/**
 * 公告信息
 * @author Administrator
 *
 */
public class MessageInfoManageDaoImpl extends BaseDaoSupport implements MessageInfoManageDao {

	/**
	 * 分页查询公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-7
	 */
	public PageObject queryMessageManageForPage(MessageInfoVO infoVO,
			int start, int limit) {
		return this.executeQueryForPage("pub.select_querySystemMessageInfo", infoVO, start, limit);
	}

	/**
	 * 新增公告信息
	 * @param infoVO
	 * @author zuosl 2016-4-7
	 */
	public void addMessageInfo(MessageInfoVO infoVO) {
		this.executeInsert("pub.insert_insertSysMessageInfo", infoVO);
	}

	/**
	 * 新增公告与供应商组关系
	 * @param groupVOs
	 * @author zuosl 2016-4-8
	 */
	public void insertMessageInfoSupGroupId(List<SuppGroupVO> groupVOs) {
		this.executeBatchInsert("pub.insert_insertMessageInfoSupGroupId", groupVOs);
	}

	/**
	 * 根据公告ID查询公告信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public MessageInfoVO queryMessageInfoByInfoId(String infoId) {
		return (MessageInfoVO) this.executeQueryForObject("pub.select_queryMessageInfoByInfoId", infoId);
	}

	/**
	 * 根据公告ID删除该公告的供应商组信息
	 * @param infoId
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public int deleteMessageInfoSupGroupIdByInfoId(String infoId) {
		
		return this.executeDelete("pub.delete_deleteMessageInfoSupGroupIdByInfoId", infoId);
	}

	/**
	 * 修改公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public int updateMessageInfo(MessageInfoVO infoVO) {
		return this.executeUpdate("pub.update_updateMessageInfo", infoVO);
	}

	/**
	 * 删除公告信息
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-8
	 */
	public int deleteMessageInfo(MessageInfoVO infoVO) {
		return this.executeDelete("pub.delete_deleteMessageInfo", infoVO);
	}

	/**
	 * 查询首页公告信息
	 * @param infoVO
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-11
	 */
	public PageObject queryHomePageMessageForPage(MessageInfoVO infoVO,
			int start, int limit) {
		
		if(!StringUtil.isNullOrEmpty(infoVO.getLoginUserName())
				&& !StringUtil.isNullOrEmpty(infoVO.getFactory() )){
			//广乘用户查询
			return this.executeQueryForPage("pub.select_queryHomePageMessageListGamc", infoVO, start, limit);
		}else if (!StringUtil.isNullOrEmpty(infoVO.getLoginSupplierNo() )){
			//供应商用户查询
			return this.executeQueryForPage("pub.select_queryHomePageMessageListSup", infoVO, start, limit);
		}else{
			return null;
		}
	}

	/**
	 * 更新供应商公告查看状态
	 * @param infoVO
	 * @return
	 * @author zuosl 2016-4-12
	 */
	public void updateSupplierViewStatus(MessageInfoVO infoVO) {
		this.executeInsert("pub.update_updateSupplierMgsInfoViewStatus", infoVO);
	}

	/**
	 * 更新公告供应商下载状态
	 * @param infoVO
	 * @author zuosl 2016-4-12
	 */
	public void updateSupplierDownloadStatus(MessageInfoVO infoVO) {
		this.executeInsert("pub.update_updateSupplierMgsInfoDownloadStatus", infoVO);
	}

	/**
	 * 查询公告供应商查看记录
	 * @param infoId
	 * @param start
	 * @param limit
	 * @return
	 * @author zuosl 2016-4-12
	 */
	public PageObject queryHomePageMsgQueryStatusForPage(String infoId,
			int start, int limit) {
		return this.executeQueryForPage("pub.select_queryHomePageMsgQueryStatusList", infoId, start, limit);
	}

	public PageObject queryOrderInfo(OrderInfoVO orderInfoVO, int start,
			int limit) {
		return this.executeQueryForPage("pub.queryOrderInfoIndex", orderInfoVO,start,limit);
	}

	@SuppressWarnings("unchecked")
	public List<String> queryPrivilegeInfo(OrderInfoVO orderInfoVO) {
		return (List<String>) this.executeQueryForList("pub.queryRoleOrderType",orderInfoVO);
	}

}
