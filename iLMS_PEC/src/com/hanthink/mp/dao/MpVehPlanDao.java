package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpVehPlanModel;

import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：车辆计划 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpVehPlanDao extends Dao<String, MpVehPlanModel> {
	
	 /**
     * 分页查询车辆计划
     * @param model
     * @param p
     * @return
     */
    List<MpVehPlanModel> queryMpVehPlanForPage(MpVehPlanModel model, DefaultPage p);
	
	
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
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
	void removeAndLogByCalStatus(String factoryCode);


    /**
     * 获取生产计划
     * <p>return: Object</p>  
     * <p>Description: MpVehPlanDao.java</p>  
     * @author linzhuo  
     * @date 2018年9月20日
     * @version 1.0
     * @return 
     */
	Integer getVehPlan(String params);

	/**
	 * 批量删除车辆计划
	 * <p>return: void</p>  
	 * <p>Description: MpVehPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年10月30日
	 * @version 1.0
	 * @throws Exception 
	 */
	void deleteByIds(String[] aryIds) throws Exception;

}
