package com.hanthink.jiso.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jiso.dao.JisoInsDao;
import com.hanthink.jiso.model.JisoInsModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JisoInsDaoImpl
 * @Description: 厂外同步指示票daoImpl
 * @author dtp
 * @date 2018年9月18日
 */
@Repository
public class JisoInsDaoImpl extends MyBatisDaoImpl<String, JisoInsModel> implements JisoInsDao{

	@Override
	public String getNamespace() {
		return JisoInsModel.class.getName();
	}

	/**
	 * @Description: 查询厂外同步指示票  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月18日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoInsModel> queryJisoInsPage(JisoInsModel model, DefaultPage page) {
		return (PageList<JisoInsModel>) this.getList("queryJisoInsPage", model, page);
	}

	/**
	 * @Description: 根据指示票号查询厂外同步指示票明细   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JisoInsModel> queryJisoInsDetailPageByInsNo(JisoInsModel model, DefaultPage page) {
		return (PageList<JisoInsModel>) this.getList("queryJisoInsDetailPageByInsNo", model, page);
	}

	/**
	 * @Description: 导出厂外同步指示票excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> queryJisoInsList(JisoInsModel model) {
		return (List<JisoInsModel>) this.getList("queryJisoInsPage", model);
	}

	/**
	 * @Description: 导出厂外同步指示票明细excel
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月19日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> downloadJisoInsDetail(JisoInsModel model) {
		return (List<JisoInsModel>) this.getList("getDownloadJisoInsDetail", model);
	}

	/**
	 * @Description:  查询厂外同步指示票明细 
	 * @param: @param list
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月20日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> queryJisoInsDetailList(List<JisoInsModel> list) {
		return (List<JisoInsModel>) this.getList("queryJisoInsDetailList", list);
	}

	/**
	 * @Description: 查询厂外同步指示票明细(指示票打印) 
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JisoInsModel>   
	 * @author: dtp
	 * @date: 2018年9月24日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JisoInsModel> queryJisoInsDetailList(JisoInsModel model_q) {
		return (List<JisoInsModel>) this.getList("queryJisoInsDetailList_by_model", model_q);
	}

	/**
	 * @Description: 更新打印状态 
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月29日
	 */
	@Override
	public void updatePrintStatus(List<JisoInsModel> list_printInfo) {
		this.updateByKey("updatePrintStatus", list_printInfo);
	}

	/**
	 * @Description: 更新打印状态
	 * @param: @param jisoInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月16日
	 */
	@Override
	public void updatePrintStatusModel(JisoInsModel jisoInsModel) {
		this.updateByKey("updatePrintStatusModel", jisoInsModel);
	}

}
