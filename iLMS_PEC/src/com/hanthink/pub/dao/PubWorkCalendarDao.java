package com.hanthink.pub.dao;
import java.util.List;

import com.hanthink.pub.model.PubWorkCalendarModel;
import com.hotent.base.db.api.Dao;
import com.hotent.base.db.mybatis.domain.DefaultPage;


/**
 * 
 * <pre> 
 * 描述：生产日历查询DAO接口
 * 构建组：x5-bpmx-platform
 * 作者:linzhuo
 * 邮箱:zhuo.lin@hotent.com
 * 日期:2018-09-10 10:39:09
 * 版权：汉思信息技术有限公司
 * </pre>
 */
public interface PubWorkCalendarDao extends Dao<String, PubWorkCalendarModel> {
	/**
	 * 分页查询
	 * <p>return: PageList<PubWorkCalendarModel></p>  
	 * <p>Description: PubWorkCalendarDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 */
	List<PubWorkCalendarModel> queryPubWorkCalendarForPage(PubWorkCalendarModel model, DefaultPage p);
	/**
	 * 右侧栏位显示查询结果
	 * <p>return: List<PubWorkCalendarModel></p>  
	 * <p>Description: PubWorkCalendarDao.java</p>  
	 * @author linzhuo  
	 * @date 2018年9月28日
	 * @version 1.0
	 * @param p 
	 */
	List<PubWorkCalendarModel> getRowClick(String userName, DefaultPage p);
	
	/**
	 * 查询某一天的工作时间
	 * @param model
	 * @return
	 */
    List<PubWorkCalendarModel> queryWorkTime(PubWorkCalendarModel model);
    
    /**
     * 查询某一天对应的休息时间
     * @param model
     * @return
     */
    List<PubWorkCalendarModel> queryRestTime(PubWorkCalendarModel model);
    
    /**
     * 查询是否工作日
     * @param pubWorkCalendarModel
     * @return
     */
    Integer queryIsWorkDay(PubWorkCalendarModel pubWorkCalendarModel);
    
    /**
     * 查询所有日期是否工作日
     * @param newPubWorkCalendarModels
     * @return
     */
    List<PubWorkCalendarModel> queryIsWorkDayForList(List<PubWorkCalendarModel> newPubWorkCalendarModels);
    
}
