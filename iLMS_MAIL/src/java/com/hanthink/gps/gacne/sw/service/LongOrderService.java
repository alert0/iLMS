package com.hanthink.gps.gacne.sw.service;

import java.util.List;
import java.util.Map;

import com.hanthink.gps.gacne.sw.vo.LongOrderVo;

public interface LongOrderService {

	/**
	 * 
	 * @Description: 查询反馈NG的供应商信息
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月2日 下午12:12:54
	 */
	List<LongOrderVo> getLongOrderSupplier(Map<String, Object> map);

	/**
	 * 
	 * @Description: 修改邮件发送标识
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月2日 下午12:52:08
	 */
	void updateEmailFlag(Map<String, Object> map);

	/**
	 * 
	 * @Description: 周预测查询反馈NG的供应商
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月2日 下午2:00:21
	 */
	List<LongOrderVo> getWeekForecasetSupplier(Map<String, Object> map);

	/**
	 * 
	 * @Description: 月预测查询反馈NG的供应商
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月2日 下午2:00:50
	 */
	List<LongOrderVo> getMonthForecasetSupplier(Map<String, Object> map);

	void updateMonthEmailFlag(Map<String, Object> map);

	void updateWeekEmailFlag(Map<String, Object> map);

	/**
	 * 
	 * @Description: 查询新的例外订单和对应的供应商信息
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 上午11:49:01
	 */
	List<LongOrderVo> getExcepOrderSupplier(Map<String, Object> map);

	/**
	 * 
	 * @Description: 修改邮件发送状态
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 下午12:02:20
	 */
	void updateExcepOrderMail(Map<String, Object> map);

	/**
	 * 
	 * @Description: 查询新的售后订单和对应的供应商信息
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 下午12:09:49
	 */
	List<LongOrderVo> getAfterOrderSupplier(Map<String, Object> map);

	/**
	 * 
	 * @Description: 修改售后订单邮件标识
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 下午12:25:09
	 */
	void updateAfterOrderMail(Map<String, Object> map);

	/**
	 * 
	 * @Description: 修改定制化订单邮件标识
	 * @param @param map   
	 * @return void  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 下午2:58:30
	 */
	void updateNonOrderMail(Map<String, Object> map);

	/**
	 * 
	 * @Description: 查询新的定制化信息
	 * @param @param map
	 * @param @return   
	 * @return List<LongOrderVo>  
	 * @throws  
	 * @author luoxq
	 * @date 2019年4月3日 下午2:58:14
	 */
	List<LongOrderVo> getNonOrderSupplier(Map<String, Object> map);

}
