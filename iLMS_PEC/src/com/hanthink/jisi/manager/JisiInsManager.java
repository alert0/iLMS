package com.hanthink.jisi.manager;

import java.util.List;

import com.hanthink.jisi.model.JisiInsModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

public interface JisiInsManager extends Manager<String, JisiInsModel>{

	/**
	 * 
	 * @Description: 厂内同步票据查询，分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:17:43
	 */
	PageList<JisiInsModel> queryJisiInsForPage(JisiInsModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 厂内同步票据查询，明细查看
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:23:45
	 */
	PageList<JisiInsModel> queryJisiInsDetailForPage(JisiInsModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 厂内同步票据查询，导出
	 * @param @param model
	 * @param @return   
	 * @return List<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:25:40
	 */
	List<JisiInsModel> queryJisiInsByKey(JisiInsModel model);

	/**
	 * 
	 * @Description: 查询订单打印的数据
	 * @param @param model_q
	 * @param @return   
	 * @return List<JisoInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:49:02
	 */
	List<JisiInsModel> queryJisiInsDetailList(JisiInsModel model_q);

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param list_printInfo
	 * @param @param ipAddr
	 * @param @param insNoArr   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:49:22
	 */
	void updatePrintStatus(List<JisiInsModel> list_printInfo, String ipAddr, String[] insNoArr);

}
