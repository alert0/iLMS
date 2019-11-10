package com.hanthink.pup.dao;

import java.util.List;

import com.hanthink.pup.model.PupLockPlanModel;
import com.hotent.base.api.Page;
import com.hotent.base.db.api.Dao;
/**
 * <pre> 
 * 功能描述:锁定取货计划维护Dao层接口类
 * 构建组:x5-bpmx-platform
 * 作者:zmj
 * 日期:2018年9月25日
 * 版权:汉思信息技术有限公司
 * </pre>
 */
public interface PupLockPlanDao extends Dao<String, PupLockPlanModel>{
	/**
	 * 锁定取货计划维护查询Dao层接口
	 * @param model 查询参数
	 * @param page 页面参数
	 * @return 与查询结果匹配的计划数据
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	List<PupLockPlanModel> queryLockPlanModelsForPage(PupLockPlanModel model,Page page)throws Exception;
	/**
	 * 锁定取货计划维护数据删除Dao层接口
	 * @param ids 一个/多个数据id
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月25日
	 */
	void deleteLockPlanByIds(String[] ids)throws Exception;
	/**
	 * 锁定取货计划维护删除Dao层接口
	 * @param id 需要删除的数据的ID
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	List<PupLockPlanModel> queryLockPlanForExport(PupLockPlanModel model)throws Exception;
	/**
	 * 删除上一版本数据Dao层接口
	 * @param factoryCode
	 * @throws Exception
	 * @author zmj
	 * @date 2018年9月26日
	 */
	void deleteLockPlan(String factoryCode)throws Exception;
	/**
	 * 锁定取货计划维护数据导入Dao层接口
	 * @param file Excel文件
	 * @param ipAddr 用户IP地址
	 * @return 操作结果集
	 * @throws Exception 异常
	 * @author zmj
	 * @date 2018年9月26日
	 */
	void insertExcelDataToTale(List<PupLockPlanModel> importList)throws Exception;
}
