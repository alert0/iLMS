package com.hanthink.mon.manager;


import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hotent.base.manager.api.Manager;
/**
 * @ClassName: MonKbManager
 * @Description: 物流看板信息
 * @author luocc
 * @date 2018年11月3日
 */
public interface MonEmptyReturnKbManager extends Manager<String,EmptyReturnKbModel> {
	/**
	 * 查询DCS单信息
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @param workCenter 
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectDCS(String retEmptyPlatform, String workCenter) throws Exception;

	/**
	 * 查询DCS明细信息
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectDCSDetail(EmptyReturnKbModel model) throws Exception;

	/**
	 * 若为取货订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoBySw(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 若为拉动订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoByJit(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 若为同步订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoByJiso(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 根据IP获取站台
	 * <p>return: MonKbModel</p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月11日
	 * @version 1.0
	 * @throws Exception 
	 */
	EmptyReturnKbModel getShowMessage(Map<String, String> paramMap) throws Exception;

	/**
	 * 查询订单号
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月28日
	 * @version 1.0
	 */
	List<EmptyReturnKbModel> selectOrderNoByNull();

	EmptyReturnKbModel selectIpByCombIp(String combIp);

	/**
	 * 更新DCS单状态
	 * <p>return: void</p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月20日
	 * @version 1.0
	 * @param paramMap 
	 * @throws Exception 
	 */
	void updateDCS(Map<String, String> paramMap) throws Exception;

	/**
	 * 获取各种时间
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月24日
	 * @version 1.0
	 */
	List<EmptyReturnKbModel> selectPubSysParam();

	/**
	 * 根据IP获取车间
	 * @param ip
	 * @return
	 */
	String selectWorkCenterByIp(String ip);

	/**
	 * 查询DCS单头信息
	 * @param retEmptyPlatform
	 * @param workCenter 
	 * @return
	 */
	List<EmptyReturnKbModel> selectDCSTop(String retEmptyPlatform, String workCenter);


}
