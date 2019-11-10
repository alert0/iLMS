package com.hanthink.jit.dao;

import java.util.List;

import com.hanthink.jit.model.JitPartLackModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;

public interface jitPartLackDao extends Dao<String, JitPartLackModel>{

	/**
	 * 
	 * @param page 
	 * @Description: 分也查询拉动零件缺件信息
	 * @param @param model
	 * @param @return   
	 * @return List<jitPartLackModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月15日 下午9:57:13
	 */
	List<JitPartLackModel> getJitPartLackList(JitPartLackModel model, DefaultPage page);

	/**
	 * 
	 * @param page 
	 * @Description: 查询明细
	 * @param @param model
	 * @param @return   
	 * @return List<JitPartLackModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:35:30
	 */
	List<JitPartLackModel> getJitPartLackDetialList(JitPartLackModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 修改主表中检查标识
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:50:43
	 */
	void updateCheckFlag(JitPartLackModel model);

	/**
	 * 
	 * @Description: 数据写入到检查表中
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:50:57
	 */
	void insertLackDeal(JitPartLackModel model);

}
