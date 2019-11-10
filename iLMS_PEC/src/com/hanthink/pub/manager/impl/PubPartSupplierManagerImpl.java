package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubPartSupplierDao;
import com.hanthink.pub.manager.PubPartSupplierManager;
import com.hanthink.pub.model.PubPartSupplierModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

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
@Service("PubPartSupplierManager")
public class PubPartSupplierManagerImpl extends AbstractManagerImpl<String, PubPartSupplierModel> implements PubPartSupplierManager{
	@Resource
	PubPartSupplierDao pubPartSupplierDao;
	@Override
	protected Dao<String, PubPartSupplierModel> getDao() {
		return pubPartSupplierDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubPartSupplierModel> queryPubPartSupplierForPage(PubPartSupplierModel model, DefaultPage p) {
		return pubPartSupplierDao.queryPubPartSupplierForPage(model, p);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<PubPartSupplierModel> queryPubPartSupplierByKey(PubPartSupplierModel model) {
		return pubPartSupplierDao.queryPubPartSupplierByKey(model);
	}
	
}
