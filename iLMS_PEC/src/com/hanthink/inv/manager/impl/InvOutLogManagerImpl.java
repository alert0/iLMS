package com.hanthink.inv.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvOutLogDao;
import com.hanthink.inv.manager.InvOutLogManager;
import com.hanthink.inv.model.InvOutLogModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * <pre> 
 * 功能描述:出库查询业务层实现方法
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月11日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("outLog")
public class InvOutLogManagerImpl extends AbstractManagerImpl<String, InvOutLogModel>
					implements InvOutLogManager{
	
	@Resource
	private InvOutLogDao outLogDao;
	
	@Override
	protected Dao<String, InvOutLogModel> getDao() {
		return outLogDao;
	}
	/**
	 * 分页数据查询业务实现方法
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@Override
	public PageList<InvOutLogModel> queryOutLogForPage(InvOutLogModel model,Page page) throws Exception {
		
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		
		return new PageList<InvOutLogModel>(outLogDao.queryOutLogForPage(model,page));
	}
	/**
	 * 查询数据导出业务实现方法
	 * @param model
	 * @param flag
	 * @return
	 * @author zmj
	 * @date 2018年10月11日
	 */
	@Override
	public List<InvOutLogModel> queryOutLogForExport(InvOutLogModel model) {

		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());

		return outLogDao.queryOutLogForExport(model);
	}
	/**
	 * 加载出库类型数据下拉框
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2019年1月7日
	 */
	@Override
	public List<InvOutLogModel> queryOutType() throws Exception {
		String factoryCode = ContextUtil.getCurrentUser().getCurFactoryCode();
		return outLogDao.queryOutType(factoryCode);
	}
}
