package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.base.model.TableColumnVO;
import com.hanthink.base.model.TableOpeLogVO;
import com.hanthink.jit.dao.JitAccountFilterDao;
import com.hanthink.jit.manager.JitAccountFilterManager;
import com.hanthink.jit.model.JitAccountFilterModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.sys.util.ContextUtil;

/**
 * @ClassName: JitAccountFilterManagerImpl
 * @Description: 拉动订单屏蔽结算维护
 * @author dtp
 * @date 2018年11月3日
 */
@Service("jitAccountFilterManager")
public class JitAccountFilterManagerImpl extends AbstractManagerImpl<String, JitAccountFilterModel> implements JitAccountFilterManager{

	@Resource
	private JitAccountFilterDao jitAccountFilterDao;
	
	@Override
	protected Dao<String, JitAccountFilterModel> getDao() {
		return jitAccountFilterDao;
	}

	/**
	 * @Description: 拉动订单结算屏蔽查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitAccountFilterModel>   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@Override
	public PageList<JitAccountFilterModel> queryJitAccountFilterPage(JitAccountFilterModel model, DefaultPage page) {
		return jitAccountFilterDao.queryJitAccountFilterPage(model, page);
	}

	/**
	 * @Description: 新增拉动订单结算屏蔽  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@Override
	public void insertJitAccountFilter(JitAccountFilterModel model) {
		jitAccountFilterDao.insertJitAccountFilter(model);
	}

	/**
	 * @Description: 删除拉动订单结算屏蔽   
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@Override
	public void deleteJitAccountFilter(JitAccountFilterModel[] models, String ipAddr) {
		for (int i = 0; i < models.length; i++) {
			TableOpeLogVO logVO = new TableOpeLogVO();
			logVO.setOpeUserName(ContextUtil.getCurrentUser().getAccount());
			logVO.setOpeIp(ipAddr); 
			logVO.setFromName("拉动订单结算屏蔽");
			logVO.setOpeType(TableOpeLogVO.OPE_TYPE_DELETE);
			logVO.setTableName("MM_JIT_ACCOUNT_FILTER");
			TableColumnVO pkColumnVO = new TableColumnVO();
			pkColumnVO.setColumnName("ID");
			pkColumnVO.setColumnVal(models[i].getId());
			this.tableDataLogDao.logOpeTableData(logVO, pkColumnVO);
			jitAccountFilterDao.deleteJitAccountFilter(models[i]);
			
		}
		
	}

	/**
	 * @Description: 查询是否存在 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitAccountFilterModel>   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@Override
	public List<JitAccountFilterModel> queryIsExists(JitAccountFilterModel model) {
		return jitAccountFilterDao.queryIsExists(model);
	}

}
