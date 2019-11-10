package com.hanthink.gps.jit.service.impl;

import java.util.List;

import com.hanthink.gps.jit.dao.JitExceDao;
import com.hanthink.gps.jit.service.JitExceService;
import com.hanthink.gps.jit.vo.JitCalVO;
import com.hanthink.gps.jit.vo.JitPartOneToMoreVO;
import com.hanthink.gps.jit.vo.JitPartVO;
import com.hanthink.gps.pub.service.impl.BaseServiceImpl;
import com.hanthink.gps.pub.vo.PubPlanCodeVO;

/**
 * @Desc    : 拉动异常信息提醒Service
 * @FileName: JitExceServiceImpl.java 
 * @CreateOn: 2016-7-27 下午04:58:24
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public class JitExceServiceImpl extends BaseServiceImpl implements
		JitExceService {
	
	private JitExceDao dao;

	public JitExceDao getDao() {
		return dao;
	}
	public void setDao(JitExceDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<JitPartVO> queryJitBaseInfoExce(JitPartVO qvo) {
		return dao.queryJitBaseInfoExce(qvo);
	}
	@Override
	public List<JitPartVO> queryJitLocCarExce(JitPartVO qvo) {
		return dao.queryJitLocCarExce(qvo);
	}
	@Override
	public List<JitPartVO> queryJitPartLocExce(JitPartVO qvo) {
		return dao.queryJitPartLocExce(qvo);
	}
	@Override
	public List<JitCalVO> queryJitCalStopInfo(JitCalVO calVO) {
		return dao.queryJitCalStopInfo(calVO);
	}
	
	/*add by chenyong 2016-09-20 厂外拉动一点零件多个供应商信息查询*/
	@Override
	public List<JitPartOneToMoreVO> queryJitPartOneToMoreInfo(JitPartOneToMoreVO jitpVO) {
		
		return dao.queryJitPartOneToMoreInfo(jitpVO);
	}
	@Override
	public List<JitPartVO> queryPartRemainOpeAlertList(JitPartVO jpvo) {
		return dao.queryPartRemainOpeAlertList(jpvo);
	}
	@Override
	public List<JitPartVO> queryNotRecGoodsList(JitPartVO jpvo) {
		return dao.queryNotRecGoodsList(jpvo);
	}
	@Override
	public JitPartVO queryKbBatch(JitPartVO af) {
		return dao.queryKbBatch(af);
	}
	@Override
	public List<JitPartOneToMoreVO> queryJitPartSupplierInvalid(JitPartOneToMoreVO supVO) {
		return dao.queryJitPartSupplierInvalid(supVO);
	}
	@Override
	public List<JitPartOneToMoreVO> querySupInvalidAfterThreeDayList(
			JitPartOneToMoreVO jitPartOneToMoreVO) {
		return dao.querySupInvalidAfterThreeDayList(jitPartOneToMoreVO);
	}
	/**
	 * 查询拉动推算服务停止数据信息----广汽新能源
	 * @date 2018-11-6
	 * @author dtp
	 * @param vo
	 * @return
	 */
	@Override
	public List<PubPlanCodeVO> queryJitReckonStopJob(PubPlanCodeVO vo) {
		return dao.queryJitReckonStopJob(vo);
	}

	
	
}
