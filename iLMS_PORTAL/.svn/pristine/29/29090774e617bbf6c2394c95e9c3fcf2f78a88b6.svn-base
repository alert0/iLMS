package com.hanthink.inv.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvEmptyDao;
import com.hanthink.inv.manager.InvEmptyManager;
import com.hanthink.inv.model.InvEmptyModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;
/**
 * <pre> 
 * 功能描述:空容器查询业务层实现类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年10月17日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
@Service("empty")
public class InvEmptyManagerImpl extends AbstractManagerImpl<String, InvEmptyModel>
			implements InvEmptyManager{
	@Resource
	private InvEmptyDao emptyDao;
	@Override
	protected Dao<String, InvEmptyModel> getDao() {
		return emptyDao;
	}
	/**
	 * 空容器分页查询业务实现方法
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public PageList<InvEmptyModel> queryEmptyForPage(InvEmptyModel model, Page page) throws Exception {
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		List<InvEmptyModel> list = emptyDao.queryEmptyForPage(model,page);
		return new PageList<InvEmptyModel>(list);
	}
	/**
	 * 修改空容器数据信息业务实现方法
	 * @param model
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月17日
	 */
	@Override
	public void updateForEmpty(InvEmptyModel model) throws Exception {
		emptyDao.updateForEmpty(model);
	}
}
