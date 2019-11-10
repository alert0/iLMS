package com.hanthink.mon.dao.impl;



import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hanthink.mon.dao.MonJITDao;
import com.hanthink.mon.model.MonJITModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


@Repository
public class MonJITDaoImpl extends MyBatisDaoImpl<String, MonJITModel>
implements MonJITDao{

	@Override
	public String getNamespace() {
		
		return MonJITModel.class.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonJITModel> queryJITPage(MonJITModel model, DefaultPage p) {
		return (PageList<MonJITModel>) this.getList("queryJITPage", model, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<MonJITModel> queryJITDetailPage(MonJITModel model, DefaultPage p) {
		return (PageList<MonJITModel>) this.getList("queryJITDetailPage", model, p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryKbBatch(Map<String, Object> param) {
		return this.getList("queryKbBatch", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitArrivePage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitArrData", param, page);
	}

	/**
	 *  查询拉动收货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月15日 下午2:26:47
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitArriveDetailPage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitArriveDetailPage", param, page);
	}

	/**
	 * 查询拉动监控出货信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:12:57
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitDeliveryPage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitMonDelivery", param, page);
	}

	/**
	 * 查询拉动出货监控明细信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:13:59
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitDeliveryDetailPage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitMonDeliveryDetail", param, page);
	}

	/**
	 * 查询拉动监控备货信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:14:03
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitPreparePage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitMonPrepare", param, page);
	}

	/**
	 * 查询拉动备货监控明细信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:14:08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<Map<String, Object>> queryJitPrepareDetailPage(Map<String, Object> param, Page page) {
		return this.getByKey("queryJitMonPrepareDetail", param, page);
	}

	
	

}
