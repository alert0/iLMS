package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.pub.dao.PubModelBomDao;
import com.hanthink.pub.manager.PubModelBomManager;
import com.hanthink.pub.model.PubModelBomModel;

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
@Service("PubModelBomManager")
public class PubModelBomManagerImpl extends AbstractManagerImpl<String, PubModelBomModel> implements PubModelBomManager{
	@Resource
	PubModelBomDao pubModelBomDao;
	@Override
	protected Dao<String, PubModelBomModel> getDao() {
		return pubModelBomDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubModelBomModel> queryPubModelBomForPage(PubModelBomModel model, DefaultPage p) {
		return pubModelBomDao.queryPubModelBomForPage(model, p);
	}

	/**
	 * 导出数据查询方法
	 * @param uuid
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:17:56
	 */
	@Override
	public List<PubModelBomModel> queryPubModelBomByKey(PubModelBomModel model) {
		return pubModelBomDao.queryPubModelBomByKey(model);
	}
	
}
