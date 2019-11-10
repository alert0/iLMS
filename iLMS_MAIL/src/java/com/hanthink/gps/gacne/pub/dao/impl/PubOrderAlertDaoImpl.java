package com.hanthink.gps.gacne.pub.dao.impl;

import java.util.List;

import com.hanthink.gps.gacne.pub.dao.PubOrderAlertDao;
import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;

/**
 * @Title: PubOrderAlertDao.java
 * @Package: com.hanthink.gps.gacne.pub.dao
 * @Description: 广汽新能源_订购零件基本信息维护异常邮件提醒DaoImpl
 * @author dtp
 * @date 2019-2-14
 */
public class PubOrderAlertDaoImpl  extends BaseDaoSupport implements PubOrderAlertDao {

	/**
	 * 查询系统不存在订购零件基本信息生效记录
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubOrderA(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPubOrderA", vo);
	}

	/**
	 * 取货物流没有维护对应的路线信息
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubOrderB(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPubOrderB", vo);
	}

	/**
	 * BOM不存在此生效记录
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubOrderC(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPubOrderC", vo);
	}

	/**
	 * 系统不存在零件属地信息生效记录
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubOrderD(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPubOrderD", vo);
	}

	/**
	 * 系统不存在物料与供应商关系生效记录
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPartSupplier(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPartSupplier", vo);
	}

	/**
	 * 系统不存在卸货口、仓库生效记录
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubOrderE(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryPubOrderE", vo);
	}

	/**
	 * 物料与供应商关系失效
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPartSupplierInvalid(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.select_queryPartSupplierInvalid", vo);
	}

	/**
	 * 查询推算停止信息
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryReckonStopList(PubOrderVO model) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryReckonStopList", model);
	}

	/**
	 * 查询推算停止信息(5min)
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryReckonStopList_jis(PubOrderVO model) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryReckonStopList_jis", model);
	}

	/**
	 * 查询拉动未组单零件需求
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryNotGroupOrder(PubOrderVO vo) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryNotGroupOrder", vo);
	}

	/**
	 * 查询流动数低于60的
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryPubPaoffToAft1onJob(PubOrderVO model) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryPubPaoffToAft1onJob", model);
	}

	/**
	 * 修改邮件发送标识
	 */
	@Override
	public Integer updateEmailSendFlag(PubOrderVO vo) {
		return this.executeUpdate("gacne_pub.updateEmailSendFlag", vo);
	}

	/**
	 * 根据MM_PUB_VEH_PASS判断是否发送低流动数邮件
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubOrderVO> queryIsSendEmail(PubOrderVO model) {
		return (List<PubOrderVO>) this.executeQueryForList("gacne_pub.queryIsSendEmail", model);
	}

}
