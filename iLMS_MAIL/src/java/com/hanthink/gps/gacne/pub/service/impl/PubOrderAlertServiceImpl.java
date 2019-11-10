package com.hanthink.gps.gacne.pub.service.impl;

import java.util.List;

import com.hanthink.gps.gacne.pub.dao.PubOrderAlertDao;
import com.hanthink.gps.gacne.pub.service.PubOrderAlertService;
import com.hanthink.gps.gacne.pub.vo.PubOrderVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;

/**
 * @Title: PubOrderAlertServiceImpl.java
 * @Package: com.hanthink.gps.gacne.pub.service.impl
 * @Description: 广汽新能源_订购零件基本信息维护异常邮件提醒ServiceImpl
 * @author dtp
 * @date 2019-2-14
 */
public class PubOrderAlertServiceImpl extends BaseServiceImpl implements PubOrderAlertService{

	private PubOrderAlertDao dao;

	public PubOrderAlertDao getDao() {
		return dao;
	}

	public void setDao(PubOrderAlertDao dao) {
		this.dao = dao;
	}

	/**
	 * 查询系统不存在订购零件基本信息生效记录
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubOrderA(PubOrderVO vo) {
		return dao.queryPubOrderA(vo);
	}

	/**
	 * 取货物流没有维护对应的路线信息
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubOrderB(PubOrderVO vo) {
		return dao.queryPubOrderB(vo);
	}

	/**
	 * BOM不存在此生效记录
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubOrderC(PubOrderVO vo) {
		return dao.queryPubOrderC(vo);
	}

	/**
	 * 系统不存在零件属地信息生效记录
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubOrderD(PubOrderVO vo) {
		return dao.queryPubOrderD(vo);
	}

	/**
	 * 系统不存在物料与供应商关系生效记录
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPartSupplier(PubOrderVO vo) {
		return dao.queryPartSupplier(vo);
	}

	/**
	 * 系统不存在卸货口、仓库生效记录
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubOrderE(PubOrderVO vo) {
		return dao.queryPubOrderE(vo);
	}

	/**
	 * 物料与供应商关系失效
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPartSupplierInvalid(PubOrderVO vo) {
		return dao.queryPartSupplierInvalid(vo);
	}

	/**
	 * 查询推算停止信息
	 * @param model
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryReckonStopList(PubOrderVO model) {
		return dao.queryReckonStopList(model);
	}

	/**
	 * 查询推算停止信息(5min)
	 * @param model
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryReckonStopList_jis(PubOrderVO model) {
		return dao.queryReckonStopList_jis(model);
	}

	/**
	 * 查询拉动未组单零件需求
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryNotGroupOrder(PubOrderVO vo) {
		return dao.queryNotGroupOrder(vo);
	}

	/**
	 * 查询流动数低于60的
	 * @param model
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryPubPaoffToAft1onJob(PubOrderVO model) {
		return dao.queryPubPaoffToAft1onJob(model);
	}

	/**
	 * 修改邮件发送标识
	 * @param vo
	 * @return
	 */
	@Override
	public Integer updateEmailSendFlag(PubOrderVO vo) {
		return dao.updateEmailSendFlag(vo);
	}

	/**
	 * 根据MM_PUB_VEH_PASS判断是否发送低流动数邮件
	 * @param model
	 * @return
	 */
	@Override
	public List<PubOrderVO> queryIsSendEmail(PubOrderVO model) {
		return dao.queryIsSendEmail(model);
	}
	
}
