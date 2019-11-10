package com.hanthink.jit.dao;

import java.util.List;

import com.hanthink.jit.model.JitInsModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInsDao
 * @Description: 配送单查询
 * @author dtp
 * @date 2018年10月8日
 */
public interface JitInsDao extends Dao<String, JitInsModel>{

	/**
	 * @Description: 配送单查询   
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	PageList<JitInsModel> queryJitInsPage(JitInsModel model, DefaultPage page);

	/**
	 * @Description: 配送单导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	List<JitInsModel> queryJitInsList(JitInsModel model);

	/**
	 * @Description: 配送单明细查询
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月8日
	 */
	PageList<JitInsModel> queryJitInsDetailPage(JitInsModel model, DefaultPage page);

	/**
	 * @Description: 查询配送单明细(配送单打印)
	 * @param: @param model_q
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	List<JitInsModel> queryJitInsDetailList(JitInsModel model_q);

	/**
	 * @Description: 更新配送单打印状态
	 * @param: @param list_printInfo    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	void updatePrintInfo(List<JitInsModel> list_printInfo);

	/**
	 * @Description: 更新配送单打印状态(单个)
	 * @param: @param jitInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月9日
	 */
	void updatePrintState(JitInsModel jitInsModel);

	/**
	 * @Description: 更新明细备货数量、备货次数  
	 * @param: @param jitInsModel    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年4月3日
	 */
	void updateDetailPrepareStatus(JitInsModel jitInsModel);

	/**
	 * @Description: 配送单明细导出
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月6日
	 */
	List<JitInsModel> downloadJitInsDetail(JitInsModel model);

	/**
	 * @Description: 根据配送单号查询是否有备件出库记录  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	List<JitInsModel> queryInvOutLogList(JitInsModel model);

	/**
	 * @Description: 获取指示票明细for更新invOutLog  
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	List<JitInsModel> queryJitInsDetailListForUpdateInvOutLog(JitInsModel model);

	/**
	 * @Description: insert mm_inv_out表  
	 * @param: @param model_invOut    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	void insertInvOutLog(JitInsModel model_invOut);

	/**
	 * @Description: insert mm_inv_out_detail表    
	 * @param: @param list_insDetail    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年5月12日
	 */
	void insertInvOutDetailLog(List<JitInsModel> list_insDetail);

	/**
	 * @Description: 根据INS_NO,LOCATION,PART_NO查询已出库记录  
	 * @param: @param model_detail
	 * @param: @return    
	 * @return: List<JitInsModel>   
	 * @author: dtp
	 * @date: 2019年5月13日
	 */
	List<JitInsModel> queryInvOutLogByModelDetail(JitInsModel model_detail);


}
