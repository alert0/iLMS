package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpAdjPlanModel;

import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：调整计划  DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpAdjPlanDao extends Dao<String, MpAdjPlanModel> {
	
	 /**
     * 分页查询调整计划
     * @param model
     * @param p
     * @return
     */
    List<MpAdjPlanModel> queryMpAdjPlanForPage(MpAdjPlanModel model, DefaultPage p);
	
	/**
	 * 导出数据的查询方法
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<MpAdjPlanModel> queryMpAdjPlanByKey(MpAdjPlanModel model);

	/**
	 * 获取计算队列
	 * @param model
	 * @author linzhuo	
	 * @DATE	2018年9月10日 下午4:18:34
	 */
	List<DictVO> getUnloadPort();

	/**
	 * 获取调整计划
	 * <p>return: Object</p>  
	 * <p>Description: MpAdjPlanDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月25日
	 * @version 1.0
	 * @return 
	 */
	Integer getAdjPlan(String curFactoryCode, String adjDateStrStart, String adjDateStrEnd);

}
