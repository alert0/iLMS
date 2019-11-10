package com.hanthink.mp.dao;
import java.util.List;
import java.util.Map;

import com.hanthink.base.model.DictVO;
import com.hanthink.mp.model.ExcepOrderModel;
import com.hanthink.mp.model.MpWeekPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;
import com.hotent.base.db.mybatis.domain.PageList;


/**
 * 
 * <pre> 
 * 描述：剩余量主数据 DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface MpWeekPlanDao extends Dao<String, MpWeekPlanModel> {
	
	 /**
     * 分页查询零件剩余量主数据
     * @param model
     * @param p
     * @return
     */
    List<MpWeekPlanModel> queryMpWeekPlanForPage(MpWeekPlanModel model, DefaultPage p);

    /**
     * 获取年份下拉框
     * <p>return: List<DictVO></p>  
     * <p>Description: MpWeekPlanDao.java</p>  
     * @author linzhuo  
     * @date 2018年12月15日
     * @version 1.0
     */
	List<DictVO> getYear();
	
}
