package com.hanthink.jit.manager;

import java.util.List;

import com.hanthink.jit.model.JitVehScrapModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：供应商主数据 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface JitVehScrapManager extends Manager<String, JitVehScrapModel>{

	/**
	 * 执行分页查询
	 * <p>return: PageList<JitVehScrapModel></p>  
	 * <p>Description: JitVehScrapManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<JitVehScrapModel> queryJitVehScrapForPage(JitVehScrapModel model, DefaultPage p);

	/**
	 * 供应商主数据查询
	 * <p>return: List<JitVehScrapModel></p>  
	 * <p>Description: JitVehScrapManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月30日
	 * @version 1.0
	 */
	List<JitVehScrapModel> queryJitVehScrapByKey(JitVehScrapModel model);

	/**
	 * @Description: 手工补看板 
	 * @param: @param list
	 * @param: @param ipAddr    
	 * @return: void   
	 * @author: dtp
	 * @date: 2018年10月28日
	 */
	void updateAdjustKb(List<JitVehScrapModel> list, String ipAddr);
}
