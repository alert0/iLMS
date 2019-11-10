package com.hanthink.jit.dao;

import java.util.List;

import com.hanthink.jit.model.JitAccountFilterModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitAccountFilterDao
 * @Description: 拉动订单屏蔽结算维护
 * @author dtp
 * @date 2018年11月3日
 */
public interface JitAccountFilterDao extends Dao<String, JitAccountFilterModel>{

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
	PageList<JitAccountFilterModel> queryJitAccountFilterPage(JitAccountFilterModel model, DefaultPage page);

	/**
	 * @Description: 新增拉动订单结算屏蔽  
	 * @param: @param model    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	void insertJitAccountFilter(JitAccountFilterModel model);

	/**
	 * @Description: 删除拣货工程与打印机关系  
	 * @param: @param jitAccountFilterModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	void deleteJitAccountFilter(JitAccountFilterModel jitAccountFilterModel);

	/**
	 * @Description: 查询是否存在   
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitAccountFilterModel>   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	List<JitAccountFilterModel> queryIsExists(JitAccountFilterModel model);

}
