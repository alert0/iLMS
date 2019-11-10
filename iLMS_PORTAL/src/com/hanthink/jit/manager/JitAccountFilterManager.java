package com.hanthink.jit.manager;

import java.util.List;

import com.hanthink.jit.model.JitAccountFilterModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JitAccountFilterManager
 * @Description: 拉动订单屏蔽结算维护
 * @author dtp
 * @date 2018年11月3日
 */
public interface JitAccountFilterManager extends Manager<String, JitAccountFilterModel>{

	/**
	 * @Description: 拉动订单结算屏蔽查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitAccountFilterModel>   
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
	 * @Description: 删除拉动订单结算屏蔽   
	 * @param: @param models
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月3日
	 */
	void deleteJitAccountFilter(JitAccountFilterModel[] models, String ipAddr);

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
