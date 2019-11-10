package com.hanthink.mon.dao;


import java.util.List;
import java.util.Map;

import com.hanthink.mon.model.MonJITModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface MonJITDao extends Dao<String, MonJITModel>{

	PageList<MonJITModel> queryJITPage(MonJITModel model, DefaultPage p);

	PageList<MonJITModel> queryJITDetailPage(MonJITModel model, DefaultPage p);

	List<Map<String, Object>> queryKbBatch(Map<String, Object> param);

	PageList<Map<String, Object>> queryJitArrivePage(Map<String, Object> param, Page page);

	/**
	 *  查询拉动收货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月15日 下午2:26:47
	 */
	PageList<Map<String, Object>> queryJitArriveDetailPage(Map<String, Object> param, Page page);

	/**
	 * 查询拉动出货监控信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:04:08
	 */
	PageList<Map<String, Object>> queryJitDeliveryPage(Map<String, Object> param, Page page);

	/**
	 * 查询拉动出货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:04:57
	 */
	PageList<Map<String, Object>> queryJitDeliveryDetailPage(Map<String, Object> param, Page page);

	/**
	 * 查询拉动备货监控信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:11:29
	 */
	PageList<Map<String, Object>> queryJitPreparePage(Map<String, Object> param, Page page);

	/**
	 * 查询拉动备货监控明细信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:12:03
	 */
	PageList<Map<String, Object>> queryJitPrepareDetailPage(Map<String, Object> param, Page page);


}
