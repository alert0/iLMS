package com.hanthink.jit.manager;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hanthink.jit.model.JitInvenCompModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;

/**
 * @ClassName: JitInvenCompManager
 * @Description: 拉动库存对比
 * @author dtp
 * @date 2018年11月3日
 */
public interface JitInvenCompManager extends Manager<String, JitInvenCompModel>{

	/**
	 * @Description: 拉动库存对比excel批量导入 
	 * @param: @param file
	 * @param: @param uuid
	 * @param: @param ipAddr
	 * @param: @return    
	 * @return: Map<String,Object>   
	 * @author: dtp
	 * @date: 2018年11月5日
	 */
	Map<String, Object> importJitInvenComp(MultipartFile file, String uuid, String ipAddr);

	/**
	 * @Description: 查询导入excel数据
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
