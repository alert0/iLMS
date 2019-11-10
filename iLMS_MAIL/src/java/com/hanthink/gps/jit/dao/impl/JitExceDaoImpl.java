package com.hanthink.gps.jit.dao.impl;

import java.util.List;

import com.hanthink.gps.jit.dao.JitExceDao;
import com.hanthink.gps.jit.vo.JitCalVO;
import com.hanthink.gps.jit.vo.JitPartOneToMoreVO;
import com.hanthink.gps.jit.vo.JitPartVO;
import com.hanthink.gps.pub.dao.BaseDaoSupport;
import com.hanthink.gps.pub.vo.PubPlanCodeVO;

/**
 * @Desc    : 拉动异常提醒DAO
 * @FileName: JitExceDaoImpl.java 
 * @CreateOn: 2016-7-27 下午05:00:24
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JitExceDaoImpl extends BaseDaoSupport implements JitExceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartVO> queryJitBaseInfoExce(JitPartVO qvo) {
		return (List<JitPartVO>)this.executeQueryForList("jit.select_queryJitBaseInfoExce", qvo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartVO> queryJitLocCarExce(JitPartVO qvo) {
		return (List<JitPartVO>)this.executeQueryForList("jit.select_queryJitLocCarExce", qvo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartVO> queryJitPartLocExce(JitPartVO qvo) {
		return (List<JitPartVO>)this.executeQueryForList("jit.select_queryJitPartLocExce", qvo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitCalVO> queryJitCalStopInfo(JitCalVO calVO) {
		return (List<JitCalVO>)this.executeQueryForList("jit.select_queryJitCalStopInfo", calVO);
	}

	/**
	 * add by chenyong 016-09-20 新增厂外拉动一点零件多个供应商异常信息查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartOneToMoreVO> queryJitPartOneToMoreInfo(JitPartOneToMoreVO jitpVO) {
		return (List<JitPartOneToMoreVO>) this.executeQueryForList("jit.select_queryJitPartOneToMoreInfo",jitpVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartVO> queryPartRemainOpeAlertList(JitPartVO jpvo) {
		return (List<JitPartVO>) this.executeQueryForList("jit.select_queryPartRemainOpeAlertList", jpvo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartVO> queryNotRecGoodsList(JitPartVO jpvo) {
		return (List<JitPartVO>) this.executeQueryForList("jit.select_queryNotRecGoodsList", jpvo);
	}

	@Override
	public JitPartVO queryKbBatch(JitPartVO af) {
		return (JitPartVO) this.executeQueryForObject("jit.select_queryKbBatch", af);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartOneToMoreVO> queryJitPartSupplierInvalid(JitPartOneToMoreVO supVO) {
		return (List<JitPartOneToMoreVO>) this.executeQueryForList("jit.select_queryJitPartSupplierInvalid", supVO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JitPartOneToMoreVO> querySupInvalidAfterThreeDayList(
			JitPartOneToMoreVO jitPartOneToMoreVO) {
		return (List<JitPartOneToMoreVO>) this.executeQueryForList("jit.select_querySupInvalidAfterThreeDayList", jitPartOneToMoreVO);
	}

	/**
	 * 查询拉动推算服务停止数据信息----广汽新能源
	 * @date 2018-11-6
	 * @author dtp
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PubPlanCodeVO> queryJitReckonStopJob(PubPlanCodeVO vo) {
		return (List<PubPlanCodeVO>) this.executeQueryForList("jit.select_queryJitReckonStopJob", vo);
	}

}
