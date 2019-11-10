package com.hanthink.gps.gacne.pub.service;

import java.util.List;

import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.pub.service.BaseService;

/**
 * @Title: PubOrderAlertService.java
 * @Package: com.hanthink.gps.gacne.pub.service
 * @Description: 广汽新能源_订购零件基本信息维护异常邮件提醒Service
 * @author dtp
 * @date 2019-2-14
 */
public interface PubOrderAlertService extends BaseService{

	/**
	 * 查询系统不存在订购零件基本信息生效记录
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPubOrderA(PubOrderVO vo);

	/**
	 * 取货物流没有维护对应的路线信息
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPubOrderB(PubOrderVO vo);

	/**
	 * BOM不存在此生效记录
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPubOrderC(PubOrderVO vo);

	/**
	 * 系统不存在零件属地信息生效记录
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPubOrderD(PubOrderVO vo);

	/**
	 * 系统不存在物料与供应商关系生效记录
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPartSupplier(PubOrderVO vo);

	/**
	 * 系统不存在卸货口、仓库生效记录
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPubOrderE(PubOrderVO vo);

	/**
	 * 物料与供应商关系失效
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryPartSupplierInvalid(PubOrderVO vo);

	/**
	 * 查询推算停止信息(30min)
	 * @param model
	 * @return
	 */
	List<PubOrderVO> queryReckonStopList(PubOrderVO model);

	/**
	 * 查询推算停止信息(5min)
	 * @param model
	 * @return
	 */
	List<PubOrderVO> queryReckonStopList_jis(PubOrderVO model);

	/**
	 * 查询拉动未组单零件需求
	 * @param vo
	 * @return
	 */
	List<PubOrderVO> queryNotGroupOrder(PubOrderVO vo);

	/**
	 * 查询流动数低于60的
	 * @param model
	 * @return
	 */
	List<PubOrderVO> queryPubPaoffToAft1onJob(PubOrderVO model);

	/**
	 * 修改邮件发送标识
	 * @param vo
	 * @return
	 */
	Integer updateEmailSendFlag(PubOrderVO vo);

	/**
	 * 根据MM_PUB_VEH_PASS判断是否发送低流动数邮件
	 * @param model
	 * @return
	 */
	List<PubOrderVO> queryIsSendEmail(PubOrderVO model);
	
}
