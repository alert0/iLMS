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

	/**
	 * @Description:  根据uuid删除导入临时数据 
	 * @param: @param uuid    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	void deleteImportTempDataByUUID(String uuid);

	/**
	 * @Description: 查询校验结果是否包含不通过  
	 * @param: @param uuid
	 * @param: @return    
	 * @return: int   
	 * @author: dtp
	 * @date: 2018年12月31日
	 */
	int queryIsExistsCheckResultFalse(String uuid);

	/**
	 * @Description: 更新推算状态
	 * @param: @param paramMap    
	 * @return: void   
	 * @author: dtp
	 * @date: 2019年2月26日
	 */
	void updateImportDataImpStatus(Map<String, String> paramMap);

	/**
	 * @Description: 根据车间获取最小最大批次
	 * @param: @param model
	 * @param: @return    
	 * @return: List<JitInvenCompModel>   
	 * @author: dtp
	 * @date: 2019年4月18日
	 */
	List<JitInvenCompModel> queryJitCurBatchByWorkcenter(JitInvenCompModel model);

	/**
	 * @Description: 获取计划差异  
	 * @param: @param param
	 * @param: @param page
	 * @param: @return    
	 * @return: PageList<Map<String,Object>>   
	 * @author: dtp
	 * @date: 2019年4月18日
	 */
	PageList<Map<String, Object>> queryJitPlanDiffPage(Map<String, Object> param, DefaultPage page);

	/**
	 * @param: @return 查询拉动计划差异导出excel信息 
	 * @return: List<Map<String,Object>>   
	 * @author: dtp
	 * @date: 2019年4月19日
	 */
	List<Map<String, Object>> queryJitPlanDiffList(Map<String, Object> param);

}
