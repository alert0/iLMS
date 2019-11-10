package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.MpWeekPlanModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：周计划维护 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpWeekPlanManager extends Manager<String, MpWeekPlanModel>{

	 /**
     * 分页查询周计划维护
     * @param model
     * @param p
     * @return
     */
    PageList<MpWeekPlanModel> queryMpWeekPlanForPage(MpWeekPlanModel model, DefaultPage p);
	
	/**
	 * 更新数据并记录日志
	 * @param demoModel
	 * @param opeIp
	 * @author linzhuo	
	 * @DATE	2018年9月10日 上午10:54:40
	 */
	void updateAndLog(MpWeekPlanModel MpWeekPlanModel, String opeIp);

	/**
	 * 获取年份填充下拉框
	 * <p>return: List<DictVO></p>  
	 * <p>Description: MpWeekPlanManager.java</p>  
	 * @author linzhuo  
	 * @date 2018年12月15日
	 * @version 1.0
	 */
	List<DictVO> getYear();

}
