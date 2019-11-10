package com.hanthink.jisi.dao;

import java.util.List;

import com.hanthink.jisi.model.JisiInsModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

public interface JisiInsDao extends Dao<String, JisiInsModel>{

	/**
	 * 
	 * @Description: 厂内同步票据查询，分页查询
	 * @param @param model
	 * @param @param p
	 * @param @return   
	 * @return PageList<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:22:21
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
	 * @date 2018年11月10日 上午10:24:24
	 */
	PageList<JisiInsModel> queryJisiInsDetailForPage(JisiInsModel model, DefaultPage p);

	/**
	 * 
	 * @Description: 厂内同步票决查询，导出
	 * @param @param model
	 * @param @return   
	 * @return List<JisiInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2018年11月10日 上午10:26:36
	 */
	List<JisiInsModel> queryJisiInsByKey(JisiInsModel model);

	/**
	 * 
	 * @Description: 查询订单打印数据
	 * @param @param model_q
	 * @param @return   
	 * @return List<JisoInsModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:51:06
	 */
	List<JisiInsModel> queryJisiInsDetailList(JisiInsModel model_q);

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param jisiInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:53:57
	 */
	void updatePrintStatusModel(JisiInsModel jisiInsModel);

}
