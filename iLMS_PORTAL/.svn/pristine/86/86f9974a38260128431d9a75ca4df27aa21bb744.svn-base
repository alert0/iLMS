package com.hanthink.jit.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hanthink.base.manager.impl.AbstractManagerImpl;
import com.hanthink.jit.dao.JitInsDao;
import com.hanthink.jit.manager.JitInsManager;
import com.hanthink.jit.model.JitInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInsManagerImpl
 * @Description: 配送单查询
 * @author dtp
 * @date 2018年10月8日
 */
@Service("jitInsManager")
public class JitInsManagerImpl extends AbstractManagerImpl<String, JitInsModel> implements JitInsManager{

	@Resource 
	private JitInsDao jitInsDao;
	
	@Override
	protected Dao<String, JitInsModel> getDao() {
		return jitInsDao;
	}

	/**
	 * @Description: 配送单查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public PageList<JitInsModel> queryJitInsPage(JitInsModel model, DefaultPage page) {
		return jitInsDao.queryJitInsPage(model, page);
	}

	/**
	 * @Description: 配送单导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public List<JitInsModel> queryJitInsList(JitInsModel model) {
		return jitInsDao.queryJitInsList(model);
	}

	/**
	 * @Description: 配送单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@Override
	public PageList<JitInsModel> queryJitInsDetailPage(JitInsModel model, DefaultPage page) {
		return jitInsDao.queryJitInsDetailPage(model, page);
	}

	/**
	 * @Description: 查询配送单明细(配送单打印)
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public List<JitInsModel> queryJitInsDetailList(JitInsModel model_q) {
		return jitInsDao.queryJitInsDetailList(model_q);
	}

	/**
	 * @Description: 更新配送单打印状态
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public void updatePrintInfo(List<JitInsModel> list_printInfo) {
		//jitInsDao.updatePrintInfo(list_printInfo);
		for (int i = 0; i < list_printInfo.size(); i++) {
			jitInsDao.updatePrintState(list_printInfo.get(i));
		}
	}

}
