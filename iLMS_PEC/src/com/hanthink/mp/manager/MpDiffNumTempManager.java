package com.hanthink.mp.manager;

import java.util.List;

import com.hanthink.mp.model.MpDiffNumTempModel;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;
import com.hotent.base.manager.api.Manager;


/**
 * 
 * <pre> 
 * 描述：W+1,W+2生产计划 处理接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpDiffNumTempManager extends Manager<String, MpDiffNumTempModel>{

	 /**
     * 分页查询W+1,W+2生产计划
     * @param model
     * @param p
     * @return
     */
    PageList<MpDiffNumTempModel> queryMpDiffNumTempForPage(MpDiffNumTempModel model, DefaultPage p);
	
	/**
	 * 导出W+1,W+2生产计划
	 * @param model
	 * @return
	 */
	List<MpDiffNumTempModel> queryMpDiffNumTempByKey(MpDiffNumTempModel model);

	/**
	 * 获取调整计划USP_MP_ZSB_DIFF_PLAN
	 * <p>return: Integer</p>  
	 * <p>Description: MpDiffNumTempManager.java</p>  
	 * @author linzhuo  
	 * @date 2019年1月15日
	 * @version 1.0
	 */
	Integer getZsbDiffPlan(String curFactoryCode);

}
