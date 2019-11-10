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

}
