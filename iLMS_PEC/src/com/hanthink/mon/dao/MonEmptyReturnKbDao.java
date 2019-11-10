package com.hanthink.mon.dao;


import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.EmptyReturnKbModel;
import com.hotent.base.db.api.Dao;

/**
 * @ClassName: MonKbDao
 * @Description:物流监控看板
 * @author luocc
 * @date 2018年11月3日
 */
public interface MonEmptyReturnKbDao extends Dao<String, EmptyReturnKbModel>{

	/**
	 * 查询DCS单信息
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @param workCenter 
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectDCS(String retEmptyPlatform, String workCenter) throws Exception;

	/**
	 * 查询DCS单明细
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectDCSDetail(EmptyReturnKbModel model) throws Exception;

	/**
	 * 若为取货订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoBySw(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 若为拉动订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoByJit(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 若为同步订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月5日
	 * @version 1.0
	 * @throws Exception 
	 */
	List<EmptyReturnKbModel> selectOrderNoByJiso(EmptyReturnKbModel emptyReturnKbModel) throws Exception;

	/**
	 * 根据IP获取站台
	 * <p>return: MonKbModel</p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月11日
	 * @version 1.0
	 * @throws Exception 
	 */
	EmptyReturnKbModel getShowMessage(Map<String, String> paramMap) throws Exception;

	/**
	 * 查询订单
	 * <p>return: List<EmptyReturnKbModel></p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年3月28日
	 * @version 1.0
	 */
	List<EmptyReturnKbModel> selectOrderNoByNull();

	/**
	 * 根据combIp查询看板IP
	 * <p>return: String</p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月20日
	 * @version 1.0
	 */
	EmptyReturnKbModel selectIpByCombIp(String combIp);

	/**
	 * 更新DCS单状态
	 * <p>return: void</p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月20日
	 * @version 1.0
	 * @param model 
	 */
	void updateDCS(EmptyReturnKbModel model);

	/**
	 * 获取各种时间
	 * <p>return: void</p>  
	 * <p>Description: MonEmptyReturnKbDao.java</p>  
	 * @author linzhuo  
	 * @date 2019年4月24日
	 * @version 1.0
	 * @return 
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

	/**
	 * 用于拉绳更新
	 * @param string
	 * @return
	 */
	List<EmptyReturnKbModel> selectDCSUp(String string);

	
}
