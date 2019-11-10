package com.hanthink.mon.manager.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.mon.dao.MonJITDao;
import com.hanthink.mon.manager.MonJITManager;
import com.hanthink.mon.model.MonJITModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

@Service("MonJITManager")
public class MonJITManagerImpl extends AbstractManagerImpl<String, MonJITModel>
implements MonJITManager{

	@Resource
	private MonJITDao dao;
	@Override
	protected Dao<String, MonJITModel> getDao() {
		
		return dao;
	}
	
	@Override
	public PageList<MonJITModel> queryJITPage(MonJITModel model, DefaultPage p) {
		return dao.queryJITPage(model,p);
	}
	@Override
	public PageList<MonJITModel> queryJITDetailPage(MonJITModel model, DefaultPage p) {
		return dao.queryJITDetailPage(model,p);
	}

	@Override
	public List<Map<String, Object>> queryKbBatch(Map<String, Object> param) {
		return dao.queryKbBatch(param);
	}

	@Override
	public PageList<Map<String, Object>> queryJitArrivePage(Map<String, Object> param, Page page) {
		return dao.queryJitArrivePage(param,page);
	}

	/**
	 * 查询拉动收货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月15日 下午2:25:48
	 */
	@Override
	public PageList<Map<String, Object>> queryJitArriveDetailPage(Map<String, Object> param, Page page) {
		return dao.queryJitArriveDetailPage(param, page);
	}

	/**
	 * 查询拉动出货监控信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:02:50
	 */
	@Override
	public PageList<Map<String, Object>> queryJitDeliveryPage(Map<String, Object> param, Page page) {
		return dao.queryJitDeliveryPage(param, page);
	}

	/**
	 * 查询拉动出货监控明细
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:03:02
	 */
	@Override
	public PageList<Map<String, Object>> queryJitDeliveryDetailPage(Map<String, Object> param, Page page) {
		return dao.queryJitDeliveryDetailPage(param, page);
	}

	/**
	 * 查询拉动监控备货信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:10:28
	 */
	@Override
	public PageList<Map<String, Object>> queryJitPreparePage(Map<String, Object> param, Page page) {
		return dao.queryJitPreparePage(param, page);
	}

	/**
	 * 查询拉动监控备货明细信息
	 * @param param
	 * @param page
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月20日 上午10:10:39
	 */
	@Override
	public PageList<Map<String, Object>> queryJitPrepareDetailPage(Map<String, Object> param, Page page) {
		return dao.queryJitPrepareDetailPage(param, page);
	}
	
	

	

	


}
