package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpAdjPlanModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：调整计划 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjPlanManager extends Manager<String, MpAdjPlanModel>{

	 /**
     * 分页查询调整计划
     * @param model
     * @param p
     * @return
     */
    PageList<MpAdjPlanModel> queryMpAdjPlanForPage(MpAdjPlanModel model, DefaultPage p);
	
	/**
	 * 导出调整计划
	 * @param model
	 * @return
	 */
	List<MpAdjPlanModel> queryMpAdjPlanByKey(MpAdjPlanModel model);

	/**
	 * 获取调整计划
	 * <p>return: void</p>  
	 * <p>Description: MpAdjPlanManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 * @return 
	 */
	Integer getAdjPlan(String curFactoryCode, String adjDateStrStart, String adjDateStrEnd);

}
