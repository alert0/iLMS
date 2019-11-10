package com.hanthink.gps.jit.service;

import java.util.List;

import com.hanthink.gps.jit.vo.JitCalVO;
import com.hanthink.gps.jit.vo.JitPartOneToMoreVO;
import com.hanthink.gps.jit.vo.JitPartVO;
import com.hanthink.gps.pub.service.BaseService;
import com.hanthink.gps.pub.vo.PubPlanCodeVO;

/**
 * @Desc    : 拉动异常信息提醒Service
 * @FileName: JitExceService.java 
 * @CreateOn: 2016-7-27 下午04:57:31
 * @author  : zuosl  
 *
 * @ChangeList
 * Date			Version		Editor		ChangeReasons
 * 2016-7-27	V1.0		zuosl		新建
 * 
 *
 */
public interface JitExceService extends BaseService {

	/**
	 * 查询拉动配送基本信息未维护数据
	 * @param qvo
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JitPartVO> queryJitBaseInfoExce(JitPartVO qvo);

	/**
	 * 查询拉动零件落点未维护数据
	 * @param qvo
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JitPartVO> queryJitPartLocExce(JitPartVO qvo);

	/**
	 * 查询拉动落点车体数未维护数据
	 * @param qvo
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JitPartVO> queryJitLocCarExce(JitPartVO qvo);

	/**
	 * 查询拉动推算服务停止数据信息
	 * @param calVO
	 * @return
	 * @author zuosl 2016-7-27
	 */
	List<JitCalVO> queryJitCalStopInfo(JitCalVO calVO);
	
	/**
	 * 厂外拉动一点零件多个供应商异常信息
	 * @param 
	 * @return
	 * @author chenyong 2016-9-20 
	 */
	List<JitPartOneToMoreVO>  queryJitPartOneToMoreInfo(JitPartOneToMoreVO jitpVO);

	/**
	 * 统计最近二小时修改的余量日志
	 * @param jpvo
	 * @return
	 */
	List<JitPartVO> queryPartRemainOpeAlertList(JitPartVO jpvo);

	/**
	 * 统计未收货数据
	 * @param jpvo
	 * @return
	 */
	List<JitPartVO> queryNotRecGoodsList(JitPartVO jpvo);

	/**
	 * 根据"工厂代码,工作中心,看板代码"查询当前看板批次进度
	 * @param af
	 * @return
	 */
	JitPartVO queryKbBatch(JitPartVO af);

	/**
	 * 厂外拉动零件并且bom生效但物料供应商关系数据失效或者没有维护物料供应商关系数据
	 * @param supVO
	 * @return
	 */
	List<JitPartOneToMoreVO> queryJitPartSupplierInvalid(JitPartOneToMoreVO supVO);

	/***
	 * 厂外拉动零件并且bom生效但物料供应商关系数据在3天后即将失效的 
	 * @param jitPartOneToMoreVO
	 * @return
	 */
	List<JitPartOneToMoreVO> querySupInvalidAfterThreeDayList(
			JitPartOneToMoreVO jitPartOneToMoreVO);

	/**
	 * 查询拉动推算服务停止数据信息----广汽新能源
	 * @date 2018-11-6
	 * @author dtp
	 * @param vo
	 * @return
	 */
	List<PubPlanCodeVO> queryJitReckonStopJob(PubPlanCodeVO vo);


}
