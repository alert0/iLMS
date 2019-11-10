package com.hanthink.jit.dao;

import java.util.List;
import java.util.Map;

import com.hanthink.jit.model.JitInvenCompModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;

/**
 * @ClassName: JitInvenCompDao
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
public interface JitInvenCompDao extends Dao<String, JitInvenCompModel>{

	/**
	 * @Description: 导入数据到临时表
	 * @param: @param importList    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	void insertImportTempData(List<JitInvenCompModel> importList);

	/**
	 * @Description: 校验数据准确性
	 * @param: @param ckParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	void checkImportData(Map<String, String> ckParamMap);

	/**
	 * @Description: 查询excel导入数据  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	PageList<JitInvenCompModel> queryImportTempPage(JitInvenCompModel model, DefaultPage page);

	/**
	 * @Description: 执行拉动库存对比计算 
	 * @param: @param reckonParamMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	void isReckon(Map<String, String> reckonParamMap);

	/**
	 * @Description: 拉动库存对比计算excel导出 
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	List<JitInvenCompModel> queryImportTempList(JitInvenCompModel model);

	/**
	 * @Description: 拉动计划差异查询  
	 * @param: @param model
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2018年11月8日
	 */
	PageList<JitInvenCompModel> queryJitPlanDiff(JitInvenCompModel model, DefaultPage page);

}
