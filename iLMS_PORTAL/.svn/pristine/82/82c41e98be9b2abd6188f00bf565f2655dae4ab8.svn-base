package com.hanthink.mon.manager;


import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonJITModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface MonJITManager extends Manager<String, MonJITModel>{

	PageList<MonJITModel> queryJITPage(MonJITModel model, DefaultPage p);


	PageList<MonJITModel> queryJITDetailPage(MonJITModel model, DefaultPage p);


	List<Map<String, Object>> queryKbBatch(Map<String, Object> param);


	PageList<Map<String, Object>> queryJitArrivePage(Map<String, Object> param, Page page);


	/**
	 * 查询拉动收货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月15日 下午2:25:48
	 */
	PageList<Map<String, Object>> queryJitArriveDetailPage(Map<String, Object> param, Page page);


	/**
	 * 查询拉动出货监控信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:02:04
	 */
	PageList<Map<String, Object>> queryJitDeliveryPage(Map<String, Object> param, Page page);


	/**
	 * 查询拉动出货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:02:24
	 */
	PageList<Map<String, Object>> queryJitDeliveryDetailPage(Map<String, Object> param, Page page);


	/**
	 * 查询拉动监控备货信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:09:08
	 */
	PageList<Map<String, Object>> queryJitPreparePage(Map<String, Object> param, Page page);


	/**
	 * 查询拉动监控备货明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:09:36
	 */
	PageList<Map<String, Object>> queryJitPrepareDetailPage(Map<String, Object> param, Page page);

}
