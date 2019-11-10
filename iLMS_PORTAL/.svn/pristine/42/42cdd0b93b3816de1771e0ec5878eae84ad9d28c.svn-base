package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitAccountFilterDao;
import com.hanthink.jit.model.JitAccountFilterModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitAccountFilterDaoImpl
 * @Description: 拉动订单屏蔽结算维护
 * @author dtp
 * @date 2018年11月3日
 */
@Repository
public class JitAccountFilterDaoImpl extends MyBatisDaoImpl<String, JitAccountFilterModel> implements JitAccountFilterDao{

	@Override
	public String getNamespace() {
		return JitAccountFilterModel.class.getName();
	}

	/**
	 * @Description: 拉动订单结算屏蔽查询  
	 * @param: @param request
	 * @param: @param response
	 * @param: @param model
	 * @param: @return    
	 * @return: PageJson   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitAccountFilterModel> queryJitAccountFilterPage(JitAccountFilterModel model, DefaultPage page) {
		return (PageList<JitAccountFilterModel>) this.getList("queryJitAccountFilterPage", model, page);
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
		this.insertByKey("insertJitAccountFilter", model);
	}

	/**
	 * @Description: 删除拣货工程与打印机关系  
	 * @param: @param jitAccountFilterModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@Override
	public void deleteJitAccountFilter(JitAccountFilterModel jitAccountFilterModel) {
		this.deleteByKey("deleteJitAccountFilter", jitAccountFilterModel);
	}

	/**
	 * @Description: 查询是否存在   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitAccountFilterModel>   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitAccountFilterModel> queryIsExists(JitAccountFilterModel model) {
		return (List<JitAccountFilterModel>) this.getList("queryIsExists", model);
	}

}
