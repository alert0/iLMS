package com.hanthink.jisi.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jisi.dao.JisiInsDao;
import com.hanthink.jisi.model.JisiInsModel;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

import sun.nio.cs.ext.TIS_620;
@Repository
public class JisiInsDaoImpl extends MyBatisDaoImpl<String, JisiInsModel> implements JisiInsDao{

	@Override
	public String getNamespace() {
		
		return JisiInsModel.class.getName();
	}

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
	@Override
	public PageList<JisiInsModel> queryJisiInsForPage(JisiInsModel model, DefaultPage p) {
		
		return (PageList<JisiInsModel>) this.getByKey("queryJisiInsForPage", model, p);
	}

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
	@Override
	public PageList<JisiInsModel> queryJisiInsDetailForPage(JisiInsModel model, DefaultPage p) {
		
		return (PageList<JisiInsModel>) this.getByKey("queryJisiInsDetailForPage", model, p);
	}

	@Override
	public List<JisiInsModel> queryJisiInsByKey(JisiInsModel model) {
		
		return this.getByKey("queryJisiInsForPage", model);
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public List<JisiInsModel> queryJisiInsDetailList(JisiInsModel model_q) {
		
		return (List<JisiInsModel>) this.getList("queryJisiInsDetailList", model_q);
	}

	/**
	 * 
	 * @Description: 更新打印状态
	 * @param @param jisiInsModel   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年2月21日 下午6:53:57
	 */
	@Override
	public void updatePrintStatusModel(JisiInsModel jisiInsModel) {
		this.updateByKey("updatePrintStatusModel", jisiInsModel);
	}

}
