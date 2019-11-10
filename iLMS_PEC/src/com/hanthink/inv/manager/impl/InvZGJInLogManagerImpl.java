package com.hanthink.inv.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.inv.dao.InvZGJInLogDao;
import com.hanthink.inv.manager.InvZGJInLogManager;
import com.hanthink.inv.model.InvInLogModel;
import com.hanthink.inv.model.InvZGJInLogModel;
import com.hanthink.pup.util.PupUtil;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

@Service("InvZGJInLogManager")
public class InvZGJInLogManagerImpl extends AbstractManagerImpl<String, InvZGJInLogModel> implements
		InvZGJInLogManager{

	@Resource
	private InvZGJInLogDao invZGJInLogDao;
	
	@Override
	protected Dao<String, InvZGJInLogModel> getDao() {
		return invZGJInLogDao;
	}
	
	/**
	 * 入库管理数据分页查询业务实现方法
	 * @param model
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@Override
	public PageList<InvInLogModel> queryInLogForPage(InvInLogModel model, Page page)throws Exception{
	
		model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
		model.setInTimeEnd(PupUtil.getTargetDate(model.getInTimeEnd(), "yyyy-MM-dd", 1));
		List<InvInLogModel>  list = invZGJInLogDao.queryInLogForPage(model,page);

		return new PageList<InvInLogModel>(list);
	}
	
	/**
	 *  入库数据导出业务实现方法
	 * @param model
	 * @param flag
	 * @return
	 * @throws Exception
	 * @author zmj
	 * @date 2018年10月9日
	 */
	@Override
	public List<InvInLogModel> queryInLogForExport(InvInLogModel model)throws Exception{
		
			model.setFactoryCode(ContextUtil.getCurrentUser().getCurFactoryCode());
			model.setInTimeEnd(PupUtil.getTargetDate(model.getInTimeEnd(), "yyyy-MM-dd", 1));
		return invZGJInLogDao.queryInLogForExport(model);
	}

}
