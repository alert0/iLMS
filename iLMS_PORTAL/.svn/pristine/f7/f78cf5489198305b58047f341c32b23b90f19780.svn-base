package com.hanthink.jiso.manager;

import java.util.List;

import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JisoInsManager
 * @Description: 厂外同步指示票manager
 * @author dtp
 * @date 2018年9月18日
 */
public interface JisoInsManager extends Manager<String, JisoInsModel>{

	/**
	 * @Description: 查询厂外同步指示票  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	PageList<JisoInsModel> queryJisoInsPage(JisoInsModel model, DefaultPage page);

	/**
	 * @Description: 根据指示票号查询厂外同步指示票明细   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	PageList<JisoInsModel> queryJisoInsDetailPageByInsNo(JisoInsModel model, DefaultPage page);

	/**
	 * @Description: 导出厂外同步指示票excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	List<JisoInsModel> queryJisoInsList(JisoInsModel model);

	/**
	 * @Description: 导出厂外同步指示票明细excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	List<JisoInsModel> downloadJisoInsDetail(JisoInsModel model);

	/**
	 * @Description:  查询厂外同步指示票明细 
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月20日
	 */
	List<JisoInsModel> queryJisoInsDetailList(List<JisoInsModel> list);

	/**
	 * @Description: 查询厂外同步指示票明细(指示票打印) 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	List<JisoInsModel> queryJisoInsDetailList(JisoInsModel model_q);

	/**
	 * @Description: 厂外同步指示票更新打印状态 
	 * @param: @param list_printInfo
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月29日
	 */
	void updatePrintStatus(List<JisoInsModel> list_printInfo, String ipAddr, String[] insNoArr);

}
