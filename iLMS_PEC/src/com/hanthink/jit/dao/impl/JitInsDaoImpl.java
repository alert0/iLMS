package com.hanthink.jit.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hanthink.jit.dao.JitInsDao;
import com.hanthink.jit.model.JitInsModel;
import com.hotent.base.db.impl.MyBatisDaoImpl;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInsDaoImpl
 * @Description: 配送单查询
 * @author dtp
 * @date 2018年10月8日
 */
@Repository
public class JitInsDaoImpl extends MyBatisDaoImpl<String, JitInsModel> implements JitInsDao{

	@Override
	public String getNamespace() {
		return JitInsModel.class.getName();
	}

	/**
	 * @Description: 配送单查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitInsModel> queryJitInsPage(JitInsModel model, DefaultPage page) {
		return (PageList<JitInsModel>) this.getList("queryJitInsPage", model, page);
	}

	/**
	 * @Description: 配送单导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryJitInsList(JitInsModel model) {
		return (List<JitInsModel>) this.getList("queryJitInsPage", model);
	}

	/**
	 * @Description: 配送单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageList<JitInsModel> queryJitInsDetailPage(JitInsModel model, DefaultPage page) {
		return (PageList<JitInsModel>) this.getList("queryJitInsDetailPage", model, page);
	}

	/**
	 * @Description: 查询配送单明细(配送单打印)
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryJitInsDetailList(JitInsModel model_q) {
		return (List<JitInsModel>) this.getList("queryJitInsDetailList", model_q);
	}

	/**
	 * @Description: 更新配送单打印状态
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public void updatePrintInfo(List<JitInsModel> list_printInfo) {
		this.updateByKey("updatePrintInfo", list_printInfo);
	}

	/**
	 * @Description: 更新配送单打印状态(单个)
	 * @param: @param jitInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	@Override
	public void updatePrintState(JitInsModel jitInsModel) {
		this.updateByKey("updatePrintState", jitInsModel);
	}

	/**
	 * @Description: 更新明细备货数量、备货次数  
	 * @param: @param jitInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月3日
	 */
	@Override
	public void updateDetailPrepareStatus(JitInsModel jitInsModel) {
		this.updateByKey("updateDetailPrepareStatus", jitInsModel);
	}

	/**
	 * @Description: 配送单明细导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月6日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> downloadJitInsDetail(JitInsModel model) {
		return this.getListByKey("downloadJitInsDetail", model);
	}

	/**
	 * @Description: 根据配送单号查询是否有备件出库记录  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryInvOutLogList(JitInsModel model) {
		return this.getListByKey("queryInvOutLogList", model);
	}

	/**
	 * @Description: 获取指示票明细for更新invOutLog  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryJitInsDetailListForUpdateInvOutLog(JitInsModel model) {
		return this.getListByKey("queryJitInsDetailListForUpdateInvOutLog", model);
	}

	/**
	 * @Description: insert mm_inv_out表  
	 * @param: @param model_invOut    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	@Override
	public void insertInvOutLog(JitInsModel model_invOut) {
		this.insertByKey("insertInvOutLog", model_invOut);
	}

	/**
	 * @Description: insert mm_inv_out_detail表    
	 * @param: @param list_insDetail    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	@Override
	public void insertInvOutDetailLog(List<JitInsModel> list_insDetail) {
		for (JitInsModel jitInsModel : list_insDetail) {
			this.insertByKey("insertInvOutDetailLog", jitInsModel);
		}
	}

	/**
	 * @Description: 根据INS_NO,LOCATION,PART_NO查询已出库记录  
	 * @param: @param model_detail
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月13日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<JitInsModel> queryInvOutLogByModelDetail(JitInsModel model_detail) {
		return this.getListByKey("queryInvOutLogByModelDetail", model_detail);
	}

	

}
