package com.hanthink.jit.manager;

import java.util.List;

import com.hanthink.jit.model.JitPartLackModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;

public interface jitPartLackManager extends Manager<String, JitPartLackModel>{

	/**
	 * 
	 * @param page 
	 * @Description: 分也查询拉动零件缺件信息
	 * @param @param model
	 * @param @return   
	 * @return List<jitPartLackModel>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月15日 下午9:56:28
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
	 * @date 2019年4月16日 下午3:35:00
	 */
	List<JitPartLackModel> getJitPartLackDetialList(JitPartLackModel model, DefaultPage page);

	/**
	 * 
	 * @Description: 检查
	 * @param @param idArr
	 * @param @param model   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月16日 下午3:46:31
	 */
	void jitPartLackCheck(String[] idArr, JitPartLackModel model);

}
