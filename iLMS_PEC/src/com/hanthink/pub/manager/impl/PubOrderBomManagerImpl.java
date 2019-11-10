package com.hanthink.pub.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubOrderBomDao;
import com.hanthink.pub.manager.PubOrderBomManager;
import com.hanthink.pub.model.PubOrderBomModel;

/**
 * 根据表名实现的类
 */

import com.hanthink.pub.model.PubProPlanModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * 
 * <pre>
 * 描述：物料与供应商关系查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubOrderBomManager")
public class PubOrderBomManagerImpl extends
		AbstractManagerImpl<String, PubOrderBomModel> implements
		PubOrderBomManager {
	@Resource
	PubOrderBomDao pubOrderBomDao;

	@Override
	protected Dao<String, PubOrderBomModel> getDao() {
		return pubOrderBomDao;
	}

	/**
	 * 执行分页查询的方法
	 */
	@Override
	public PageList<PubOrderBomModel> queryPubOrderBomForPage(
			PubOrderBomModel model, DefaultPage p) {
		if (model.getOrderNo() != null && !"".equals(model.getOrderNo())) {
			String[] orderNos = model.getOrderNo().split(",");
			if (orderNos.length > 0) {
				model.setOrderNos(orderNos);
			}
		}
		PageList<PubOrderBomModel> orderBomModels = null;
		// 判断model的是否汇总值
		if (model.getIsSummary() == null || "".equals(model.getIsSummary())
				|| "0".equals(model.getIsSummary())) {
			//如果是这三种情况按照不汇总查询
			orderBomModels = pubOrderBomDao.queryNotSummaryOrderBom(model, p);
		}else if("1".equals(model.getIsSummary())){
			orderBomModels = pubOrderBomDao.querySummaryOrderBom(model, p);
		}
		return orderBomModels;
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<PubOrderBomModel> queryPubOrderBomByKey(PubOrderBomModel model) {
		if (model.getOrderNo() != null && !"".equals(model.getOrderNo())) {
			String[] orderNos = model.getOrderNo().split(",");
			if (orderNos.length > 0) {
				model.setOrderNos(orderNos);
			}
		}
		List<PubOrderBomModel> orderBomModels = null;
		// 判断model的是否汇总值
		if (model.getIsSummary() == null || "".equals(model.getIsSummary())
				|| "0".equals(model.getIsSummary())) {
			//如果是这三种情况按照不汇总查询
			orderBomModels = pubOrderBomDao.queryNotSummaryOrderBom(model);
		}else if("1".equals(model.getIsSummary())){
			orderBomModels = pubOrderBomDao.querySummaryOrderBom(model);
		}
		return orderBomModels;
	}

}
