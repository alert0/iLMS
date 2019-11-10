package com.hanthink.pub.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.pub.dao.PubSupplierDao;
import com.hanthink.pub.manager.PubSupplierManager;
import com.hanthink.pub.model.PubSupplierModel;

/**
 * 根据表名实现的类
 */

import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.sys.util.ContextUtil;

/**
 * 
 * <pre> 
 * 描述：供应商主数据查询 处理实现类
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
@Service("PubSupplierManager")
public class PubSupplierManagerImpl extends AbstractManagerImpl<String, PubSupplierModel> implements PubSupplierManager{
	@Resource
	PubSupplierDao pubSupplierDao;
	@Override
	protected Dao<String, PubSupplierModel> getDao() {
		return pubSupplierDao;
	}
	
	/**
	 * 执行分页查询的方法
	 */
	@Override
	public List<PubSupplierModel> queryPubSupplierForPage(PubSupplierModel model, DefaultPage p) {
		return pubSupplierDao.queryPubSupplierForPage(model, p);
	}

	/**
	 * 供应商主数据查询
	 */
	@Override
	public List<PubSupplierModel> queryPubSupplierByKey(PubSupplierModel model) {
		return pubSupplierDao.queryPubSupplierByKey(model);
	}

	/**
	 * 通过供应商代码查询供应商信息
	 * @param model
	 * @return
	 * @author ZUOSL	
	 * @DATE	2018年11月28日 上午9:59:04
	 */
	@Override
	public PubSupplierModel querySupplierBySupplierNo(PubSupplierModel model) {
		return pubSupplierDao.qeurySupplierBySupplierNo(model);
	}
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param ipAddr
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:52
	 */
	@Override
	public void updateAndLog(PubSupplierModel pubSupplierModel, String ipAddr){
		
		TableOpeLogVO logVO = new TableOpeLogVO();
		logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
		logVO.setOpeIp(ipAddr); 
		logVO.setFromName("界面更新");
		logVO.setOpeType(TableOpeLogVO.OPE_TYPE_MODIFY);
		logVO.setTableName("MM_PUB_SUPPLIER");
		TableColumnVO pkColumnVO = new TableColumnVO();
		pkColumnVO.setColumnName("SUPPLIER_NO");
		pkColumnVO.setColumnVal(pubSupplierModel.getSupFactory());
		this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
		
		pubSupplierDao.update(pubSupplierModel);
		
	}
	
}
