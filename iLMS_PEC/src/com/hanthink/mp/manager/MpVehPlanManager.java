package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.mp.model.MpVehPlanModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：车辆计划  处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpVehPlanManager extends Manager<String, MpVehPlanModel>{

	 /**
     * 分页查询车辆计划
     * @param model
     * @param p
     * @return
     */
    PageList<MpVehPlanModel> queryMpVehPlanForPage(MpVehPlanModel model, DefaultPage p);
	
	/**
	 * 删除数据并记录日志
	 * @param aryIds
	 * @param ipAddr
	 * @author linzhuo	
	 * @throws Exception 
	 * @DATE	2018年9月10日 上午11:00:04
	 */
	void removeAndLogByIds(String[] aryIds, String ipAddr) throws Exception;
	
	/**
	 * 导出车辆计划
	 * @param model
	 * @return
	 */
	List<MpVehPlanModel> queryMpVehPlanByKey(MpVehPlanModel model);

	/**
	 * 校验批量删除里面是否有已订购数据
	 * @param model
	 * @return
	 */
	Integer queryMpVehPlanCheck(List<String> listIds);

	/**
	 * 查询未订购数据的SortId用于记录日志
	 * @param model
	 * @return
	 */
	List<String> querySortIdAndLogByCalStatus();
	/**
	 * 直接删除未订购数据
	 * @param model
	 * @return
	 */
	void removeAndLogByCalStatus(String factoryCode, List<String> listIds,String ipAddr);

	/**
	 * 获取生产计划
	 * <p>return: void</p>  
	 * <p>Description: MpVehPlanManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月20日
	 * @version 1.0
	 * @param  
	 * @return 
	 */
	Integer getVehPlan(String params);
	

}
